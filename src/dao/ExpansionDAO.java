/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.CardDetailsDTO;
import model.Expansion;
import util.DatabaseConnector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author esena
 */
public class ExpansionDAO {
    public Expansion getExpansionById(int expansionId) {
        String sql = "SELECT expansion_id, name, release_date FROM Expansions WHERE expansion_id = ?";
        Expansion expansion = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expansionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Convert java.sql.Date to java.time.LocalDate
                    LocalDate releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
                    expansion = new Expansion(
                        rs.getInt("expansion_id"),
                        rs.getString("name"),
                        releaseDate
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching expansion by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return expansion;
    }
    public List<Expansion> getAllExpansions() {
    List<Expansion> expansions = new ArrayList<>();
    String sql = "SELECT expansion_id, name, release_date FROM Expansions ORDER BY release_date ASC";
 try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                LocalDate releaseDate = rs.getDate("release_date") != null ? rs.getDate("release_date").toLocalDate() : null;
                expansions.add(new Expansion(
                    rs.getInt("expansion_id"),
                    rs.getString("name"),
                    releaseDate
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all expansions: " + e.getMessage());
            e.printStackTrace();
        }
        return expansions;
    }



    public int addExpansion(Expansion expansion) {
        String sql = "INSERT INTO Expansions (name, release_date) VALUES (?, ?)";
        int generatedId = -1;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, expansion.getName());
            // Convert LocalDate to java.sql.Date
            pstmt.setDate(2, expansion.getReleaseDate() != null ? Date.valueOf(expansion.getReleaseDate()) : null);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        expansion.setExpansionId(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding expansion: " + e.getMessage());
            e.printStackTrace();
        }
        return generatedId;
    }
    public boolean updateExpansion(Expansion expansion) {
        String sql = "UPDATE Expansions SET name = ?, release_date = ? WHERE expansion_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expansion.getName());
            // Convert LocalDate to java.sql.Date
            pstmt.setDate(2, expansion.getReleaseDate() != null ? Date.valueOf(expansion.getReleaseDate()) : null);
            pstmt.setInt(3, expansion.getExpansionId());
            updated = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating expansion: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }
    public boolean deleteExpansion(int expansionId) {
        String sql = "DELETE FROM Expansions WHERE expansion_id = ?";
        boolean deleted = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expansionId);
            deleted = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting expansion: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }
    public CardDetailsDTO getCardDetails(int cardId) {
        CardDetailsDTO cardDetails = null;
        String cardSql = "SELECT c.card_id, c.name, c.cost_coins, c.cost_potions, c.cost_debt, " +
                         "c.text_description, e.name AS expansion_name " +
                         "FROM Cards c JOIN Expansions e ON c.expansion_id = e.expansion_id " +
                         "WHERE c.card_id = ?";
        List<String> cardTypeNames = new ArrayList<>();
        String typesSql = "SELECT ct.type_name FROM CardTypes ct " +
                          "JOIN Card_CardType cct ON ct.type_id = cct.type_id " +
                          "WHERE cct.card_id = ? ORDER BY ct.type_name";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement cardPstmt = conn.prepareStatement(cardSql);
             PreparedStatement typesPstmt = conn.prepareStatement(typesSql)) {

            cardPstmt.setInt(1, cardId);
            try (ResultSet cardRs = cardPstmt.executeQuery()) {
                if (cardRs.next()) {
                    // Execute types query for this specific card
                    typesPstmt.setInt(1, cardId);
                    try (ResultSet typesRs = typesPstmt.executeQuery()) {
                        while (typesRs.next()) {
                            cardTypeNames.add(typesRs.getString("type_name"));
                        }
                    }

                    // Construct the DTO using data from both result sets
                    cardDetails = new CardDetailsDTO(
                        cardRs.getInt("card_id"),
                        cardRs.getString("name"),
                        cardRs.getInt("cost_coins"),
                        cardRs.getInt("cost_potions"),
                        cardRs.getInt("cost_debt"),
                        cardRs.getString("text_description"),
                        cardRs.getString("expansion_name"),
                        cardTypeNames
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching card details: " + e.getMessage());
            e.printStackTrace();
        }
        return cardDetails;
    }
    
    // pasted from ai yet, read n understand pls ty future ozan
    public List<CardDetailsDTO> getCardsByExpansionDetails(int expansionId) {
        List<CardDetailsDTO> cardDetailsList = new ArrayList<>();
        // Query to get basic card info + expansion name, filtered by expansion ID
        String cardsSql = "SELECT c.card_id, c.name, c.cost_coins, c.cost_potions, c.cost_debt, " +
                          "c.text_description, c.image_path, e.name AS expansion_name " +
                          "FROM Cards c JOIN Expansions e ON c.expansion_id = e.expansion_id " +
                          "WHERE c.expansion_id = ? ORDER BY c.name"; // Order for consistent display

        // Query to get types for a single card (will be executed for each card found)
        String typesSql = "SELECT ct.type_name FROM CardTypes ct " +
                          "JOIN Card_CardType cct ON ct.type_id = cct.type_id " +
                          "WHERE cct.card_id = ? ORDER BY ct.type_name";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement cardsPstmt = conn.prepareStatement(cardsSql);
             PreparedStatement typesPstmt = conn.prepareStatement(typesSql)) {

            cardsPstmt.setInt(1, expansionId); // Set the expansion ID filter
            try (ResultSet cardsRs = cardsPstmt.executeQuery()) {
                while (cardsRs.next()) {
                    List<String> cardTypeNames = new ArrayList<>();
                    int currentCardId = cardsRs.getInt("card_id");

                    // Execute types query for the current card found
                    typesPstmt.setInt(1, currentCardId);
                    try (ResultSet typesRs = typesPstmt.executeQuery()) {
                        while (typesRs.next()) {
                            cardTypeNames.add(typesRs.getString("type_name"));
                        }
                    }

                    // Add the DTO to the list
                    cardDetailsList.add(new CardDetailsDTO(
                        currentCardId,
                        cardsRs.getString("name"),
                        cardsRs.getInt("cost_coins"),
                        cardsRs.getInt("cost_potions"),
                        cardsRs.getInt("cost_debt"),
                        cardsRs.getString("text_description"),
                        cardsRs.getString("expansion_name"), // This will be the current expansion's name
                        cardTypeNames
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching cards by expansion details: " + e.getMessage());
            e.printStackTrace();
        }
        return cardDetailsList;
    }
}
