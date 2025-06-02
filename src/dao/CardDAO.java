/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.CardDetailsDTO;
import model.Card;
import util.DatabaseConnector;
/**
 *
 * @author esena
 */
public class CardDAO {
    public Card getcardById(int cardId) {
        String sql = "SELECT card_id, name, cost_coins, cost_potions, cost_debt, " +
                "text_description, expansion_id FROM Cards WHERE card_id = ?";
        Card card = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    card = new Card(
                            rs.getInt("card_id"),
                            rs.getString("name"),
                            rs.getInt("cost_coins"),
                            rs.getInt("cost_potions"),
                            rs.getInt("cost_debt"),
                            rs.getString("text_description"),
                            rs.getInt("expansion_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching card by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return card;
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT card_id, name, cost_coins, cost_potions, cost_debt, " +
                "text_description, expansion_id FROM Cards ORDER BY cost_coins";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                cards.add(new Card(
                        rs.getInt("card_id"),
                        rs.getString("name"),
                        rs.getInt("cost_coins"),
                        rs.getInt("cost_potions"),
                        rs.getInt("cost_debt"),
                        rs.getString("text_description"),
                        rs.getInt("expansion_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all cards: " + e.getMessage());
            e.printStackTrace();
        }
        return cards;
    }

    public List<Card> getExpansionCards(int expansionId) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT card_id, name, cost_coins, cost_potions, cost_debt, " +
                "text_description, expansion_id FROM Cards "
                + "WHERE expansion_id = ? ORDER BY cost_coins";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expansionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cards.add(new Card(
                            rs.getInt("card_id"),
                            rs.getString("name"),
                            rs.getInt("cost_coins"),
                            rs.getInt("cost_potions"),
                            rs.getInt("cost_debt"),
                            rs.getString("text_description"),
                            rs.getInt("expansion_id")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all cards: " + e.getMessage());
            e.printStackTrace();
        }
        return cards;
    }

    public int addCard(Card card) {
        String sql = "INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, " +
                "text_description, expansion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, card.getName());
            pstmt.setInt(2, card.getCostCoins());
            pstmt.setInt(3, card.getCostPotions());
            pstmt.setInt(4, card.getCostDebt());
            pstmt.setString(5, card.getTextDescription());
            pstmt.setInt(6, card.getExpansionId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        card.setCardId(generatedId); // Update the Card object with its new ID
                        System.out.println("Card '" + card.getName() + "' added successfully with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding card: " + e.getMessage());
            e.printStackTrace();
        }
        return generatedId;
    }

    public boolean updateCard(Card card) {
        String sql = "UPDATE Cards SET name = ?, cost_coins = ?, cost_potions = ?, " +
                "cost_debt = ?, text_description = ?, expansion_id = ? " +
                "WHERE card_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, card.getName());
            pstmt.setInt(2, card.getCostCoins());
            pstmt.setInt(3, card.getCostPotions());
            pstmt.setInt(4, card.getCostDebt());
            pstmt.setString(5, card.getTextDescription());
            pstmt.setInt(6, card.getExpansionId());
            pstmt.setInt(7, card.getCardId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Card ID " + card.getCardId() + " updated successfully.");
            } else {
                System.out.println("No card found with ID " + card.getCardId() + " to update.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating card: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }

    public boolean deleteCard(int cardId) {
        String sql = "DELETE FROM Cards WHERE card_id = ?";
        boolean deleted = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Card with ID " + cardId + " deleted successfully.");
            } else {
                System.out.println("No card found with ID " + cardId + " to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting card: " + e.getMessage());
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
                    typesPstmt.setInt(1, cardId);
                    try (ResultSet typesRs = typesPstmt.executeQuery()) {
                        while (typesRs.next()) {
                            cardTypeNames.add(typesRs.getString("type_name"));
                        }
                    }

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

    public List<CardDetailsDTO> getAllCardDetails() {
        List<CardDetailsDTO> cardDetailsList = new ArrayList<>();
        List<Card> allCards = getAllCards(); // Get all basic card objects first

        for (Card card : allCards) {
            CardDetailsDTO details = getCardDetails(card.getCardId()); // Reuse the single card details method
            if (details != null) {
                cardDetailsList.add(details);
            }
        }
        return cardDetailsList;
    }

    public List<CardDetailsDTO> getCardsByExpansionDetails(int expansionId) {
        List<CardDetailsDTO> cardDetailsList = new ArrayList<>();
        String cardsSql = "SELECT c.card_id, c.name, c.cost_coins, c.cost_potions, c.cost_debt, " +
                "c.text_description, e.name AS expansion_name " +
                "FROM Cards c JOIN Expansions e ON c.expansion_id = e.expansion_id " +
                "WHERE c.expansion_id = ? ORDER BY c.name";

        String typesSql = "SELECT ct.type_name FROM CardTypes ct " +
                "JOIN Card_CardType cct ON ct.type_id = cct.type_id " +
                "WHERE cct.card_id = ? ORDER BY ct.type_name";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement cardsPstmt = conn.prepareStatement(cardsSql);
             PreparedStatement typesPstmt = conn.prepareStatement(typesSql)) {

            cardsPstmt.setInt(1, expansionId);
            try (ResultSet cardsRs = cardsPstmt.executeQuery()) {
                while (cardsRs.next()) {
                    List<String> cardTypeNames = new ArrayList<>();
                    int currentCardId = cardsRs.getInt("card_id");

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
                            cardsRs.getString("expansion_name"),
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
