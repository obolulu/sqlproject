DELIMITER $$

-- Get game by ID
DROP PROCEDURE IF EXISTS sp_getGameById$$
CREATE PROCEDURE sp_getGameById(IN p_gameId INT)
BEGIN
    SELECT
        game_id,
        winner_player_id
    FROM
        Games
    WHERE
        game_id = p_gameId;
END$$

-- Get all games
DROP PROCEDURE IF EXISTS sp_getAllGames$$
CREATE PROCEDURE sp_getAllGames()
BEGIN
    SELECT
        game_id,
        winner_player_id
    FROM
        Games
    ORDER BY
        game_id DESC;
END$$

-- Add a player
DROP FUNCTION IF EXISTS fn_addPlayer$$
CREATE FUNCTION fn_addPlayer(
    p_username VARCHAR(255),
    p_real_name VARCHAR(255),
    p_email VARCHAR(255)
)
RETURNS INT
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    DECLARE new_player_id INT;
    INSERT INTO Players (username, real_name, email) VALUES (p_username, p_real_name, p_email);
    SET new_player_id = LAST_INSERT_ID();
    RETURN new_player_id;
END$$

-- Trigger for GameErrata, delete when the card is deleted

DROP TRIGGER IF EXISTS trg_before_card_delete$$

CREATE TRIGGER trg_before_card_delete
BEFORE DELETE ON Cards
FOR EACH ROW
BEGIN
    DELETE FROM CardErrata WHERE card_id = OLD.card_id;
END$$


DELIMITER ;


DELIMITER //

-- Drops the function if it already exists to allow re-running the script
DROP FUNCTION IF EXISTS CARD_NAME_MATCHES_REGEX //

-- Creates a function that returns TRUE if a cardName matches a given regex pattern
CREATE FUNCTION CARD_NAME_MATCHES_REGEX(cardName VARCHAR(100), searchPattern VARCHAR(255))
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    -- MySQL's REGEXP is case-insensitive by default in most collations for ASCII characters,
    -- but (?i) ensures explicit case-insensitivity which is good practice for regex.
    RETURN (cardName REGEXP searchPattern);
END //

DELIMITER ;

SELECT 'MySQL function CARD_NAME_MATCHES_REGEX created successfully!' AS Message;






