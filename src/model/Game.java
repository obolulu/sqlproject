/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author esena
 */
import java.time.LocalDateTime; // Use java.time for modern date/time handling

public class Game {
    private int gameId;
    private LocalDateTime gameDate;
    private Integer durationMinutes; // Use Integer for nullable int
    private String notes;
    private Integer winnerPlayerId; // FK to Players, can be null

    public Game(int gameId, LocalDateTime gameDate, Integer durationMinutes, String notes, Integer winnerPlayerId) {
        this.gameId = gameId;
        this.gameDate = gameDate;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
        this.winnerPlayerId = winnerPlayerId;
    }

    public int getGameId() { return gameId; }
    public LocalDateTime getGameDate() { return gameDate; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public String getNotes() { return notes; }
    public Integer getWinnerPlayerId() { return winnerPlayerId; }

    public void setGameId(int gameId) { this.gameId = gameId; }
    public void setGameDate(LocalDateTime gameDate) { this.gameDate = gameDate; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setWinnerPlayerId(Integer winnerPlayerId) { this.winnerPlayerId = winnerPlayerId; }

    @Override
    public String toString() {
        String winner = (winnerPlayerId != null) ? "Winner Player ID: " + winnerPlayerId : "No winner";
        return "Game [ID=" + gameId + ", Date=" + gameDate.toLocalDate() + ", " + winner + "]";
    }
}