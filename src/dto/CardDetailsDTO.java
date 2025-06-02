/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.List;

/**
 *
 * @author esena
 */
public class CardDetailsDTO {
    private int cardId;
    private String name;
    private int costCoins;
    private int costPotions;
    private int costDebt;
    private String textDescription;
    private String expansionName; // From Expansions table
    private List<String> cardTypes; // From Card_CardType and CardTypes tables

    public CardDetailsDTO(int cardId, String name, int costCoins, int costPotions, int costDebt,
                          String textDescription, String expansionName, List<String> cardTypes) {
        this.cardId = cardId;
        this.name = name;
        this.costCoins = costCoins;
        this.costPotions = costPotions;
        this.costDebt = costDebt;
        this.textDescription = textDescription;
        this.expansionName = expansionName;
        this.cardTypes = cardTypes;
    }
    public int getCardId() { return cardId; }
    public String getName() { return name; }
    public int getCostCoins() { return costCoins; }
    public int getCostPotions() { return costPotions; }
    public int getCostDebt() { return costDebt; }
    public String getTextDescription() { return textDescription; }
    public String getExpansionName() { return expansionName; }
    public List<String> getCardTypes() { return cardTypes; }

    @Override
    public String toString() {
        return name + " (" + expansionName + ") - Cost: " + costCoins +
               (costPotions > 0 ? " P: " + costPotions : "") +
               (costDebt > 0 ? " D: " + costDebt : "") +
               " - Types: " + (cardTypes != null ? String.join(", ", cardTypes) : "None");
    }
}
