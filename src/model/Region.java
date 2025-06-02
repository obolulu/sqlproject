/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author esena
 */
public class Region {
    private int regionId;
    private String regionName;
    private String currency;

    public Region(int regionId, String regionName, String currency) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.currency = currency;
    }

    // Getters
    public int getRegionId() { return regionId; }
    public String getRegionName() { return regionName; }
    public String getCurrency() { return currency; }

    // Setters
    public void setRegionId(int regionId) { this.regionId = regionId; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    public void setCurrency(String currency) { this.currency = currency; }

    @Override
    public String toString() {
        return "Region [ID=" + regionId + ", Name=" + regionName + ", Currency=" + currency + "]";
    }
}