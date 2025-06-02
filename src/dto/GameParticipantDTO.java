/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author esena
 */
public class GameParticipantDTO {
    private int playerId;
    private String username; // From Players table
    private String realName; // From Players table
    private int finalScore;
    private int turnOrder;

    public GameParticipantDTO(int playerId, String username, String realName, int finalScore, int turnOrder) {
        this.playerId = playerId;
        this.username = username;
        this.realName = realName;
        this.finalScore = finalScore;
        this.turnOrder = turnOrder;
    }

    // Getters
    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public String getRealName() { return realName; }
    public int getFinalScore() { return finalScore; }
    public int getTurnOrder() { return turnOrder; }

    @Override
    public String toString() {
        return username + " (Score: " + finalScore + ", Turn: " + turnOrder + ")";
    }
}