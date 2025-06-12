package dao;

import model.CardErrata;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardErrataDAO {
    public List<CardErrata> getErrataForCard(int cardId) {
        List<CardErrata> errataList = new ArrayList<>();
        String sql = "SELECT card_id, errata_sequence, errata_date, description, source_link FROM CardErrata WHERE card_id = ? ORDER BY errata_sequence ASC";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate errataDate = rs.getDate("errata_date") != null ? rs.getDate("errata_date").toLocalDate() : null;
                    errataList.add(new CardErrata(
                            rs.getInt("card_id"),
                            rs.getInt("errata_sequence"), // Read errata_sequence
                            errataDate,
                            rs.getString("description"),
                            rs.getString("source_link")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching errata for card ID " + cardId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return errataList;
    }
    public void addErrata(CardErrata errata) {
        String sql = "INSERT INTO CardErrata (card_id, errata_date, description, source_link) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, errata.getCardId());
            pstmt.setDate(2, Date.valueOf(errata.getErrataDate()));
            pstmt.setString(3, errata.getDescription());
            pstmt.setString(4, errata.getSourceLink());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        errata.setErrataId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding card errata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateErrata(CardErrata errata) {
        String sql = "UPDATE CardErrata SET card_id = ?, errata_date = ?, description = ?, source_link = ? WHERE errata_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, errata.getCardId());
            pstmt.setDate(2, Date.valueOf(errata.getErrataDate()));
            pstmt.setString(3, errata.getDescription());
            pstmt.setString(4, errata.getSourceLink());
            pstmt.setInt(5, errata.getErrataId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating card errata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteErrata(int errataId) {
        String sql = "DELETE FROM CardErrata WHERE errata_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, errataId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting card errata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
