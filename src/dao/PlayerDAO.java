/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Player;
import util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author esena
 */
public class PlayerDAO {
    public Player getPlayerById(int playerId){
        String sql = "SELECT player_id, username, real_name, email FROM Players WHERE player_id = ?";
        Player player = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);

            try (ResultSet rs = pstmt.executeQuery()) { 
                if (rs.next()) {
                    player = new Player( 
                        rs.getInt("player_id"),
                        rs.getString("username"),
                        rs.getString("real_name"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching player by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return player;
    }
    
    public Player getPlayerByUsername(String username) {
        String sql = "SELECT player_id, username, real_name, email FROM Players WHERE username = ?";
        Player player = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    player = new Player(
                        rs.getInt("player_id"),
                        rs.getString("username"),
                        rs.getString("real_name"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching player by username: " + e.getMessage());
            e.printStackTrace();
        }
        return player;
    }
    
     public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT player_id, username, real_name, email FROM Players";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                players.add(new Player(
                    rs.getInt("player_id"),
                    rs.getString("username"),
                    rs.getString("real_name"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all players: " + e.getMessage());
            e.printStackTrace();
        }
        return players;
    }
     
     public int addPlayer(Player player) {
         String sql = "{? = call fn_addPlayer(?, ?, ?)}";
        int generatedId = -1;

        try (Connection conn = DatabaseConnector.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.registerOutParameter(1, Types.INTEGER);

            cstmt.setString(2, player.getUsername());
            cstmt.setString(3, player.getRealName());
            cstmt.setString(4, player.getEmail());

            cstmt.execute();

            generatedId = cstmt.getInt(1);
            player.setPlayerId(generatedId);
        } catch (SQLException e) {
            System.err.println("Error adding player: " + e.getMessage());
            e.printStackTrace();
        }
        return generatedId;
    }
     
     public boolean updatePlayer(Player player) {
        String sql = "UPDATE Players SET username = ?, real_name = ?, email = ? WHERE player_id = ?";
        boolean updated = false;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, player.getUsername());
            pstmt.setString(2, player.getRealName());
            pstmt.setString(3, player.getEmail());
            pstmt.setInt(4, player.getPlayerId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Player ID " + player.getPlayerId() + " updated successfully.");
            } else {
                System.out.println("No player found with ID " + player.getPlayerId() + " to update.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating player: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }
     
      public boolean deletePlayer(int playerId) {
        String sql = "DELETE FROM Players WHERE player_id = ?";
        boolean deleted = false;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Player with ID " + playerId + " deleted successfully.");
            } else {
                System.out.println("No player found with ID " + playerId + " to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting player: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }
}
