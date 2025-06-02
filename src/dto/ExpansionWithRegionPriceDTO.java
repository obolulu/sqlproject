/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author esena
 */

import java.math.BigDecimal;
import java.time.LocalDate;


public class ExpansionWithRegionPriceDTO {
    private int expansionId;
    private String expansionName;
    private LocalDate releaseDate;

    private int regionId;
    private String regionName;
    private String currency;

    private BigDecimal price; // The actual price for this expansion in this region

    public ExpansionWithRegionPriceDTO(int expansionId, String expansionName, LocalDate releaseDate,
                                       int regionId, String regionName, String currency, BigDecimal price) {
        this.expansionId = expansionId;
        this.expansionName = expansionName;
        this.releaseDate = releaseDate;
        this.regionId = regionId;
        this.regionName = regionName;
        this.currency = currency;
        this.price = price;
    }

    // Getters
    public int getExpansionId() { return expansionId; }
    public String getExpansionName() { return expansionName; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public int getRegionId() { return regionId; }
    public String getRegionName() { return regionName; }
    public String getCurrency() { return currency; }
    public BigDecimal getPrice() { return price; }

    @Override
    public String toString() {
        return expansionName + " (" + regionName + "): " + currency + " " + price.toPlainString();
    }
}