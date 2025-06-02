/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author esena
 */
import java.time.LocalDateTime;
import java.util.List;

public class GameSummaryDTO {
    private int gameId;
    private LocalDateTime gameDate;
    private Integer durationMinutes;
    private String notes;
    private String winnerUsername; // From Players table, null if no winner
    private List<GameParticipantDTO> participants; // List of nested DTOs
    private List<GameSupplyCardDTO> kingdomCards; // List of nested DTOs (supply cards)

    public GameSummaryDTO(int gameId, LocalDateTime gameDate, Integer durationMinutes, String notes,
                          String winnerUsername, List<GameParticipantDTO> participants,
                          List<GameSupplyCardDTO> kingdomCards) {
        this.gameId = gameId;
        this.gameDate = gameDate;
        this.durationMinutes = durationMinutes;
        this.notes = notes;
        this.winnerUsername = winnerUsername;
        this.participants = participants;
        this.kingdomCards = kingdomCards;
    }

    // Getters
    public int getGameId() { return gameId; }
    public LocalDateTime getGameDate() { return gameDate; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public String getNotes() { return notes; }
    public String getWinnerUsername() { return winnerUsername; }
    public List<GameParticipantDTO> getParticipants() { return participants; }
    public List<GameSupplyCardDTO> getKingdomCards() { return kingdomCards; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game ID: ").append(gameId)
          .append(", Date: ").append(gameDate.toLocalDate())
          .append(", Winner: ").append(winnerUsername != null ? winnerUsername : "N/A")
          .append("\nParticipants: ").append(participants) // Will use GameParticipantDTO's toString
          .append("\nSupply: ").append(kingdomCards);      // Will use GameSupplyCardDTO's toString
        return sb.toString();
    }
}