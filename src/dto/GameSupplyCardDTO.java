/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author esena
 */
public class GameSupplyCardDTO {
    private int cardId;
    private String cardName; // From Cards table
    private int startingQuantity;

    public GameSupplyCardDTO(int cardId, String cardName, int startingQuantity) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.startingQuantity = startingQuantity;
    }

    // Getters
    public int getCardId() { return cardId; }
    public String getCardName() { return cardName; }
    public int getStartingQuantity() { return startingQuantity; }

    @Override
    public String toString() {
        return cardName + " (Qty: " + startingQuantity + ")";
    }
}