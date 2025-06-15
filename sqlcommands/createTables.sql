CREATE TABLE Players (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    real_name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE Expansions (
    expansion_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    release_date DATE
    -- Note: retail_price is now handled by ExpansionRegionPrices
);

CREATE TABLE CardTypes (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Regions (
    region_id INT AUTO_INCREMENT PRIMARY KEY,
    region_name VARCHAR(100) NOT NULL UNIQUE,
    currency VARCHAR(10) NOT NULL -- e.g., 'USD', 'EUR', 'CAD'
);


CREATE TABLE Cards (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    cost_coins INT NOT NULL,
    cost_potions INT DEFAULT 0,
    cost_debt INT DEFAULT 0,
    text_description TEXT,
    expansion_id INT NOT NULL,
    FOREIGN KEY (expansion_id) REFERENCES Expansions(expansion_id)
);

CREATE TABLE Card_CardType (
    card_id INT NOT NULL,
    type_id INT NOT NULL,
    PRIMARY KEY (card_id, type_id), -- Composite primary key
    FOREIGN KEY (card_id) REFERENCES Cards(card_id) ON DELETE CASCADE,
    FOREIGN KEY (type_id) REFERENCES CardTypes(type_id) ON DELETE CASCADE
);

CREATE TABLE ExpansionRegionPrices (
    expansion_id INT NOT NULL,
    region_id INT NOT NULL,
    price DECIMAL(5,2) NOT NULL, -- Use DECIMAL for precise monetary values
    PRIMARY KEY (expansion_id, region_id), -- Composite primary key
    FOREIGN KEY (expansion_id) REFERENCES Expansions(expansion_id) ON DELETE CASCADE,
    FOREIGN KEY (region_id) REFERENCES Regions(region_id) ON DELETE CASCADE
);

CREATE TABLE Games (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    winner_player_id INT, -- Can be NULL if game is incomplete or no winner
    FOREIGN KEY (winner_player_id) REFERENCES Players(player_id)
);

-- Table 9: GameParticipants
-- Links Games to Players, recording who played in which game and their score
CREATE TABLE GameParticipants (
    game_id INT NOT NULL,
    player_id INT NOT NULL,
    final_score INT,
    turn_order INT, -- e.g., 1st, 2nd, 3rd player
    PRIMARY KEY (game_id, player_id), -- Composite primary key
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES Players(player_id) ON DELETE CASCADE
);

-- Table 10: GameSupply
-- Records which Kingdom cards (and base cards) were part of a specific game's supply
CREATE TABLE GameSupply (
    game_id INT NOT NULL,
    card_id INT NOT NULL,
    starting_quantity INT NOT NULL DEFAULT 10, -- e.g., 10 for most kingdom cards, 8 for 2-player game (VPs)
    PRIMARY KEY (game_id, card_id), -- Composite primary key
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES Cards(card_id) ON DELETE CASCADE
);
CREATE TABLE PlayerExpansions (
    player_id INT NOT NULL,
    expansion_id INT NOT NULL,
    PRIMARY KEY (player_id, expansion_id), -- Composite primary key to ensure uniqueness of player-expansion pair
    FOREIGN KEY (player_id) REFERENCES Players(player_id) ON DELETE CASCADE,
    FOREIGN KEY (expansion_id) REFERENCES Expansions(expansion_id) ON DELETE CASCADE
);


