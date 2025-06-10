/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author esena
 */
public class Player {
    private int playerId;
    private String username;
    private String realName;
    private String email;
    
    public Player(int playerId, String username,
            String realName, String email) {
        this.playerId = playerId;
        this.username = username;
        this.realName = realName;
        this.email = email;
    }
    
    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public String getRealName() { return realName; }
    public String getEmail() { return email; }
    
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public void setUsername(String username) { this.username = username; }
    public void setRealName(String realName) { this.realName = realName; }
    public void setEmail(String email) { this.email = email; }

        @Override
    public String toString() {
        return username;
    }
}
