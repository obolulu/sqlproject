/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author esena
 */

public class Expansion {
    private int expansionId;
    private String name;
    private LocalDate releaseDate;

    public Expansion(int expansionId, String name, LocalDate releaseDate) {
        this.expansionId = expansionId;
        this.name = name;
        this.releaseDate = releaseDate;
    }
    
    public int getExpansionId() { return expansionId; }
    public String getName() { return name; }
    public LocalDate getReleaseDate() { return releaseDate; }
    
    public void setExpansionId(int expansionId) { this.expansionId = expansionId; }
    public void setName(String name) { this.name = name; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    @Override
    public String toString() {
        return "Expansion [ID=" + expansionId + ", Name=" + name + ", ReleaseDate=" + releaseDate + "]";
    }
}
