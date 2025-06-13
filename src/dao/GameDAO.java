package dao;

import dto.GameParticipantDTO;
import dto.GameSummaryDTO;
import dto.GameSupplyCardDTO;
import model.Game;
import model.Player;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
public class GameDAO {
    private PlayerDAO playerDAO;
    private CardDAO cardDAO;

    public GameDAO(){
        playerDAO = new PlayerDAO();
        cardDAO = new CardDAO();
    }
    public Game getGameById(int gameId) {
        String sql = "SELECT game_id, winner_player_id FROM Games WHERE game_id = ?";
        Game game = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    game = new Game(
                            rs.getInt("game_id"),
                            rs.getObject("winner_player_id", Integer.class)
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching game by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return game;
    }
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT game_id, game_date, winner_player_id FROM Games ORDER BY game_date DESC";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                games.add(new Game(
                        rs.getInt("game_id"),
                        rs.getObject("winner_player_id", Integer.class)
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all games: " + e.getMessage());
            e.printStackTrace();
        }
        return games;
    }
    public int addGame(Game game) {
        // game_date is NOT NULL, but duration and winner are nullable
        String sql = "INSERT INTO Games (winner_player_id) VALUES ( ?)";
        int generatedId = -1;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //should use setobject if theyre nullable!!!
            pstmt.setObject(1, game.getWinnerPlayerId(), java.sql.Types.INTEGER);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        game.setGameId(generatedId); // Update the Game object with its new ID
                        System.out.println("Game added successfully with ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding game: " + e.getMessage());
            e.printStackTrace();
        }
        return generatedId;
    }
    public boolean updateGame(Game game) {
        String sql = "UPDATE Games SET game_date = ?, duration_minutes = ?, notes = ?, winner_player_id = ? WHERE game_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(game.getGameDate()));
            pstmt.setObject(2, game.getDurationMinutes(), java.sql.Types.INTEGER);
            pstmt.setString(3, game.getNotes());
            pstmt.setObject(4, game.getWinnerPlayerId(), java.sql.Types.INTEGER);
            pstmt.setInt(5, game.getGameId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Game ID " + game.getGameId() + " updated successfully.");
            } else {
                System.out.println("No game found with ID " + game.getGameId() + " to update.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating game: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }
    public boolean deleteGame(int gameId) {
        String sql = "DELETE FROM Games WHERE game_id = ?";
        boolean deleted = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                deleted = true;
                System.out.println("Game with ID " + gameId + " deleted successfully.");
            } else {
                System.out.println("No game found with ID " + gameId + " to delete.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting game: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }
    public boolean addGameParticipant(int gameId, int playerId, Integer finalScore, Integer turnOrder) {
        // Using INSERT IGNORE to prevent adding the same participant multiple times
        String sql = "INSERT IGNORE INTO GameParticipants (game_id, player_id, final_score, turn_order) VALUES (?, ?, ?, ?)";
        boolean added = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, playerId);
            pstmt.setObject(3, finalScore, java.sql.Types.INTEGER); // Use setObject for nullable Integer
            pstmt.setObject(4, turnOrder, java.sql.Types.INTEGER);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) { // A new row was inserted
                added = true;
                System.out.println("Participant Player ID " + playerId + " added to Game ID " + gameId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error adding game participant: " + e.getMessage());
            e.printStackTrace();
        }
        return added;
    }

    public void addGameSupplyCard(int gameId, int cardId, int startingQuantity) {
        String sql = "INSERT INTO GameSupply (game_id, card_id, starting_quantity) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, cardId);
            pstmt.setInt(3, startingQuantity);
            pstmt.executeUpdate();
            System.out.println("Supply card added to game successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding supply card to game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<GameSupplyCardDTO> getGameSupplyCards(int gameId) {
        List<GameSupplyCardDTO> supplyCards = new ArrayList<>();
        String sql = "SELECT gs.card_id, c.name AS card_name, gs.starting_quantity " +
                "FROM GameSupply gs JOIN Cards c ON gs.card_id = c.card_id " +
                "WHERE gs.game_id = ? ORDER BY c.name";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    supplyCards.add(new GameSupplyCardDTO(
                            rs.getInt("card_id"),
                            rs.getString("card_name"),
                            rs.getInt("starting_quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching game supply cards: " + e.getMessage());
            e.printStackTrace();
        }
        return supplyCards;
    }

    public boolean setWinner(int gameId, int playerId) {
        String sql = "UPDATE Games SET winner_player_id = ? WHERE game_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, gameId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Winner for Game ID " + gameId + " set to Player ID " + playerId + ".");
            } else {
                System.out.println("No game found with ID " + gameId + " to update winner.");
            }
        } catch (SQLException e) {
            System.err.println("Error setting game winner: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }

    public boolean assignTurnOrder(int gameId, int playerId, Integer turnOrder) {
        String sql = "UPDATE GameParticipants SET turn_order = ? WHERE game_id = ? AND player_id = ?";
        boolean updated = false;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, turnOrder, java.sql.Types.INTEGER);
            pstmt.setInt(2, gameId);
            pstmt.setInt(3, playerId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                updated = true;
                System.out.println("Turn order for Player ID " + playerId + " in Game ID " + gameId + " updated to " + turnOrder + ".");
            } else {
                System.out.println("No participant found for Player ID " + playerId + " in Game ID " + gameId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error updating turn order: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }

    public List<GameParticipantDTO> getGameParticipants(int gameId) {
        List<GameParticipantDTO> participants = new ArrayList<>();
        String sql = "SELECT gp.player_id, p.username, p.real_name, gp.final_score, gp.turn_order " +
                "FROM gameparticipants gp JOIN Players p ON gp.player_id = p.player_id " +
                "WHERE gp.game_id = ? ORDER BY gp.turn_order";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    participants.add(new GameParticipantDTO(
                            rs.getInt("player_id"),
                            rs.getString("username"),
                            rs.getString("real_name"),
                            rs.getObject("turn_order", Integer.class)
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching game participants: " + e.getMessage());
            e.printStackTrace();
        }
        return participants;
    }
    public GameSummaryDTO getGameSummary(int gameId) {
    Game game = getGameById(gameId);
    if (game == null) {
        System.err.println("Game with ID " + gameId + " not found.");
        return null;
    }
    List<GameParticipantDTO> participants = getGameParticipants(gameId);
    String winnerUsername = null;
    if (game.getWinnerPlayerId() != null) {
        Player winner = playerDAO.getPlayerById(game.getWinnerPlayerId());
        if (winner != null) {
            winnerUsername = winner.getUsername();
        }
    }
    List<GameSupplyCardDTO> kingdomCards = getGameSupplyCards(gameId);
    return new GameSummaryDTO(
            game.getGameId(),
            winnerUsername,
            participants,
            kingdomCards
    );
    }
}
