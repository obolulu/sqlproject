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
import model.CardType;
import util.DatabaseConnector;
/**
 *
 * @author esena
 */
public class CardTypeDAO {
    public CardType getCardTypeById(int typeId) {
        String sql = "SELECT type_id, type_name FROM CardTypes WHERE type_id = ?";
        CardType cartype = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, typeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cartype = new CardType(rs.getInt("type_id"), rs.getString("type_name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching card type by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return cartype;
    }
    public int addCardType(CardType cardtype){
        String sql = "INSERT INTO CardTypes (type_name) VALUES (?)";
        int ID = -1;
        try(Connection conn = DatabaseConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement
        (sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cardtype.getTypeName());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
            try(ResultSet rs = pstmt.getGeneratedKeys()) {
                if(rs.next()) {
                ID = rs.getInt(1);
                cardtype.setTypeId(ID);
                }
            }
        }
        }
        catch (SQLException e) {
            System.err.println("Error adding card type: " + e.getMessage());
            e.printStackTrace();
        }
        return ID;
    }
        public boolean updateCardType(CardType cardType) {
        String sql = "UPDATE CardTypes SET type_name = ? WHERE type_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardType.getTypeName());
            pstmt.setInt(2, cardType.getTypeId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("CardType ID " + cardType.getTypeId() + " updated successfully.");
            } else {
                System.out.println("No card type found with ID " + cardType.getTypeId() + " to update.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating card type: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }
        public boolean deleteCardType(int typeId) {
        String sql = "DELETE FROM CardTypes WHERE type_id = ?";
        boolean deleted = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, typeId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("CardType with ID " + typeId + " deleted successfully.");
            } else {
                System.out.println("No card type found with ID " + typeId + " to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting card type: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }
    public boolean removeCardTypeFromCard(int cardId, int typeId) {
        String sql = "DELETE FROM Card_CardType WHERE card_id = ? AND type_id = ?";
        boolean removed = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            pstmt.setInt(2, typeId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                removed = true;
                System.out.println("Link for Card " + cardId + " and CardType " + typeId + " removed.");
            } else {
                System.out.println("No link found for Card " + cardId + " and CardType " + typeId + " to remove.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing card type link: " + e.getMessage());
            e.printStackTrace();
        }
        return removed;
    }
    public List<CardType> getCardTypesForCard(int cardId) {
        List<CardType> cardTypes = new ArrayList<>();
        String sql = "SELECT ct.type_id, ct.type_name FROM CardTypes ct " +
                "JOIN Card_CardType cct ON ct.type_id = cct.type_id " +
                "WHERE cct.card_id = ? ORDER BY ct.type_name";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cardTypes.add(new CardType(rs.getInt("type_id"), rs.getString("type_name")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching card types for card: " + e.getMessage());
            e.printStackTrace();
        }
        return cardTypes;
    }



}
