/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author esena
 */
public class CardType {
    private int typeId;
    private String typeName;

    public CardType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() { return typeId; }
    public String getTypeName() { return typeName; }

    public void setTypeId(int typeId) { this.typeId = typeId; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    @Override
    public String toString() {
        return "CardType [ID=" + typeId + ", Name=" + typeName + "]";
    }
}