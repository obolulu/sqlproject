package dao;

import model.Expansion;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class PlayerExpansionDAO {

    public List<Expansion> getExpansionsOwnedByPlayer(int playerId) {
        List<Expansion> expansions = new ArrayList<>();
        String sql = "SELECT e.expansion_id, e.name, e.release_date " +
                "FROM Expansions e JOIN PlayerExpansions pe ON e.expansion_id = pe.expansion_id " +
                "WHERE pe.player_id = ? ORDER BY e.name";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
                    expansions.add(new Expansion(
                            rs.getInt("expansion_id"),
                            rs.getString("name"),
                            releaseDate
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expansions owned by player: " + e.getMessage());
            e.printStackTrace();
        }
        return expansions;
    }

    public boolean addPlayerExpansion(int playerId, int expansionId) {
        String sql = "INSERT IGNORE INTO PlayerExpansions (player_id, expansion_id) VALUES (?, ?)";
        boolean added = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, expansionId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) { // If a new row was inserted
                added = true;
                System.out.println("Player " + playerId + " now owns Expansion " + expansionId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error adding player expansion: " + e.getMessage());
            e.printStackTrace();
        }
        return added;
    }
    public boolean removePlayerExpansion(int playerId, int expansionId) {
        String sql = "DELETE FROM PlayerExpansions WHERE player_id = ? AND expansion_id = ?";
        boolean removed = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, expansionId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                removed = true;
                System.out.println("Player " + playerId + " no longer owns Expansion " + expansionId + ".");
            } else {
                System.out.println("Player " + playerId + " did not own Expansion " + expansionId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error removing player expansion: " + e.getMessage());
            e.printStackTrace();
        }
        return removed;
    }

}
