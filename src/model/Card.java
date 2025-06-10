/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author esena
 */
public class Card {
    private int cardId;
    private String name;
    private int costCoins;
    private int costPotions;
    private int costDebt;
    private String textDescription;
    private int expansionId; // FK to Expansions

    public Card(int cardId, String name, int costCoins, int costPotions, int costDebt,
                String textDescription, int expansionId) {
        this.cardId = cardId;
        this.name = name;
        this.costCoins = costCoins;
        this.costPotions = costPotions;
        this.costDebt = costDebt;
        this.textDescription = textDescription;
        this.expansionId = expansionId;
    }

    // Getters
    public int getCardId() { return cardId; }
    public String getName() { return name; }
    public int getCostCoins() { return costCoins; }
    public int getCostPotions() { return costPotions; }
    public int getCostDebt() { return costDebt; }
    public String getTextDescription() { return textDescription; }
    public int getExpansionId() { return expansionId; }

    // Setters
    public void setCardId(int cardId) { this.cardId = cardId; }
    public void setName(String name) { this.name = name; }
    public void setCostCoins(int costCoins) { this.costCoins = costCoins; }
    public void setCostPotions(int costPotions) { this.costPotions = costPotions; }
    public void setCostDebt(int costDebt) { this.costDebt = costDebt; }
    public void setTextDescription(String textDescription) { this.textDescription = textDescription; }
    public void setExpansionId(int expansionId) { this.expansionId = expansionId; }

    @Override
    public String toString() {
        return name;
    }
}
