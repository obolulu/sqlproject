USE dominion;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE GameSupply;
TRUNCATE TABLE GameParticipants;
TRUNCATE TABLE Games;
TRUNCATE TABLE ExpansionRegionPrices;
TRUNCATE TABLE Card_CardType;
TRUNCATE TABLE Cards;
TRUNCATE TABLE Expansions;
TRUNCATE TABLE CardTypes;
TRUNCATE TABLE Regions;
TRUNCATE TABLE Players;
TRUNCATE TABLE playerexpansions;

INSERT INTO Players (username, real_name, email) VALUES
('a', 'a text full', 'a@example.com'),
('b', 'b', 'b@example.com');

INSERT IGNORE INTO Expansions (name, release_date) VALUES
('Base', '2000-01-01'), -- Using a dummy date as exact is not critical for sample
('Intrigue', '2000-02-01'),
('Seaside', '2000-03-01'),
('Alchemy', '2000-03-15'),
('Prosperity', '2000-04-01');

INSERT INTO CardTypes (type_name) VALUES
('Action'), ('Treasure'), ('Victory'), ('Attack'),
('Reaction'), ('Duration'), ('Curse'), ('Reserve'),
('Traveller'), ('Gathering'), ('Ruins'), ('Landmark'),
('Event'), ('Project'), ('Way'), ('Augment');

INSERT INTO Regions (region_name, currency) VALUES
('USA', 'USD'),
('Europe', 'EUR'),
('Canada', 'CAD'),
('Australia', 'AUD');


SET @base_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Base');
SET @intrigue_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Intrigue');
SET @seaside_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Seaside');
SET @alchemy_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Alchemy');
SET @prosperity_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Prosperity');


INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, text_description, expansion_id) VALUES
('Copper', 0, 0, 0, '+1 Coin', @base_exp_id),
('Silver', 3, 0, 0, '+2 Coins', @base_exp_id),
('Gold', 6, 0, 0, '+3 Coins', @base_exp_id),
('Estate', 2, 0, 0, '1 Victory Point', @base_exp_id),
('Duchy', 5, 0, 0, '3 Victory Points', @base_exp_id),
('Province', 8, 0, 0, '6 Victory Points', @base_exp_id),
('Curse', 0, 0, 0, '-1 Victory Point', @base_exp_id),
('Cellar', 2, 0, 0, '+1 Action. Discard any number of cards, then draw that many.', @base_exp_id),
('Market', 5, 0, 0, '+1 Card, +1 Action, +1 Buy, +1 Coin', @base_exp_id),
('Chapel', 2, 0, 0, 'Trash up to 4 cards from your hand.', @base_exp_id),
('Council Room', 5, 0, 0, '+4 Cards, +1 Buy. Each other player draws a card.', @base_exp_id),
('Festival', 5, 0, 0, '+1 Action, +1 Buy, +2 Coins', @base_exp_id),
('Gardens', 4, 0, 0, 'Worth 1 Victory Point per 10 cards in your deck (rounded down).', @base_exp_id),
('Laboratory', 5, 0, 0, '+2 Cards, +1 Action', @base_exp_id),
('Library', 5, 0, 0, 'Draw until you have 7 cards in hand. You may set aside any Action cards drawn this way, discarding them after you are done drawing.', @base_exp_id),
('Militia', 4, 0, 0, '+2 Coins. Each other player discards down to 3 cards in hand.', @base_exp_id),
('Mine', 5, 0, 0, 'You may trash a Treasure card from your hand. Gain a Treasure card to your hand costing up to 3 Coins more than it.', @base_exp_id),
('Moat', 2, 0, 0, '+2 Cards. When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack.', @base_exp_id),
('Remodel', 4, 0, 0, 'Trash a card from your hand. Gain a card costing up to 2 Coins more than it.', @base_exp_id),
('Smithy', 4, 0, 0, '+3 Cards', @base_exp_id),
('Village', 3, 0, 0, '+1 Card, +2 Actions', @base_exp_id),
('Witch', 5, 0, 0, '+2 Cards. Each other player gains a Curse.', @base_exp_id),
('Workshop', 3, 0, 0, 'Gain a card costing up to 4 Coins.', @base_exp_id);

INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, text_description, expansion_id) VALUES
('Baron', 4, 0, 0, '+1 Buy. You may discard an Estate. If you do, +4 Coins. Otherwise, gain an Estate.', @intrigue_exp_id),
('Bridge', 4, 0, 0, '+1 Buy, +1 Coin. Cards cost 1 Coin less this turn, but not less than 0.', @intrigue_exp_id),
('Conspirator', 4, 0, 0, '+2 Coins. If you have played 3 or more Action cards this turn (this card included), +1 Card, +1 Action.', @intrigue_exp_id),
('Coppersmith', 4, 0, 0, 'While this is in play, when you play a Copper, it produces an additional +1 Coin.', @intrigue_exp_id),
('Courtyard', 2, 0, 0, '+3 Cards. Put a card from your hand onto your deck.', @intrigue_exp_id),
('Duke', 5, 0, 0, 'Worth 1 Victory Point per Duchy you have.', @intrigue_exp_id),
('Great Hall', 3, 0, 0, '+1 Card, +1 Action. 1 Victory Point.', @intrigue_exp_id),
('Harem', 6, 0, 0, '+2 Coins. 2 Victory Points.', @intrigue_exp_id),
('Ironworks', 4, 0, 0, 'Gain a card costing up to 4 Coins. If it is an Action card, +1 Action. If it is a Treasure card, +1 Coin. If it is a Victory card, +1 Card.', @intrigue_exp_id),
('Masquerade', 3, 0, 0, '+2 Cards. Each player passes a card from their hand to the player on their left. Then you may trash a card from your hand.', @intrigue_exp_id),
('Minion', 5, 0, 0, '+1 Action. Choose one: +2 Coins; or discard your hand, then draw 4 cards.', @intrigue_exp_id),
('Nobles', 6, 0, 0, 'Choose one: +3 Cards; or +2 Actions. 2 Victory Points.', @intrigue_exp_id),
('Pawn', 2, 0, 0, 'Choose two: +1 Card; +1 Action; +1 Buy; +1 Coin. (The choices must be different.)', @intrigue_exp_id),
('Saboteur', 5, 0, 0, 'Each other player trashes a card from their hand costing 3 Coins or more, then gains a card costing up to 2 Coins less than it.', @intrigue_exp_id),
('Scout', 4, 0, 0, '+1 Action. Reveal the top 4 cards of your deck. Put any Victory cards revealed into your hand. Put the rest back on top in any order.', @intrigue_exp_id),
('Shanty Town', 3, 0, 0, '+2 Actions. If you have no Action cards in hand, +2 Cards.', @intrigue_exp_id),
('Steward', 3, 0, 0, 'Choose one: +2 Cards; or +2 Coins; or trash 2 cards from your hand.', @intrigue_exp_id),
('Swindler', 3, 0, 0, '+2 Coins. Each other player trashes the top card of their deck and gains a card with the same cost that you choose.', @intrigue_exp_id),
('Torturer', 5, 0, 0, '+3 Cards. Each other player chooses one: discards 2 cards; or gains a Curse to their hand.', @intrigue_exp_id),
('Tradepost', 5, 0, 0, 'Trash 2 cards from your hand. Gain a Silver.', @intrigue_exp_id),
('Tribute', 5, 0, 0, '+1 Action. The player to your left reveals their hand. For each differently named card they reveal, +1 Card and +1 Action this turn.', @intrigue_exp_id),
('Wishing Well', 3, 0, 0, '+1 Card, +1 Action. Name a card. Reveal the top card of your deck. If it is the named card, put it into your hand.', @intrigue_exp_id);

-- Seaside Cards
INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, text_description, expansion_id) VALUES
('Ambassador', 3, 0, 0, 'Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then gain a copy of it from the Supply.', @seaside_exp_id),
('Caravan', 4, 0, 0, '+1 Action. At the start of your next turn, +1 Card.', @seaside_exp_id),
('Cutpurse', 4, 0, 0, '+2 Coins. Each other player discards a Copper or reveals a hand with no Copper.', @seaside_exp_id),
('Explorer', 5, 0, 0, 'You may reveal a Province from your hand. If you do, gain a Gold to your hand. Otherwise, gain a Silver to your hand.', @seaside_exp_id),
('Fishing Village', 3, 0, 0, '+1 Card, +2 Actions. At the start of your next turn, +1 Coin, +1 Action.', @seaside_exp_id),
('Ghost Ship', 5, 0, 0, '+2 Cards. Each other player with 4 or more cards in hand puts cards from their hand onto their deck until they have 3 cards in hand.', @seaside_exp_id),
('Haven', 2, 0, 0, '+1 Card, +1 Action. Set aside a card from your hand face down. At the start of your next turn, put it into your hand.', @seaside_exp_id),
('Island', 4, 0, 0, 'Put this card and another card from your hand onto your Island mat. 2 Victory Points.', @seaside_exp_id),
('Lighthouse', 2, 0, 0, '+1 Action. While this is in play, +1 Coin at the start of your turns. You are unaffected by Attack cards.', @seaside_exp_id),
('Lookout', 3, 0, 0, '+1 Action. Reveal the top 3 cards of your deck. Trash one of them. Discard one of them. Put the other one on top of your deck.', @seaside_exp_id),
('Merchant Ship', 5, 0, 0, '+2 Coins. At the start of your next turn, +2 Coins.', @seaside_exp_id),
('Native Village', 2, 0, 0, '+2 Actions. Choose one: Put the top card of your deck onto your Native Village mat; or take all cards from your Native Village mat into your hand.', @seaside_exp_id),
('Navigator', 4, 0, 0, '+2 Coins. Look at the top 5 cards of your deck. Discard any of them. Put the rest back on top in any order.', @seaside_exp_id),
('Outpost', 5, 0, 0, 'You may play an additional turn after this one. If you do, you draw 3 cards instead of 5 in that turn''s Clean-up phase.', @seaside_exp_id),
('Pearl Diver', 2, 0, 0, '+1 Card, +1 Action. Look at the bottom card of your deck. You may put it on top.', @seaside_exp_id),
('Pirate Ship', 4, 0, 0, 'Choose one: Each other player reveals the top card of their deck, trashes it if it''s a Treasure, and discards the rest; or +1 Coin per coin token you have.', @seaside_exp_id),
('Salvager', 4, 0, 0, '+1 Buy. You may trash a card from your hand. If you do, +Coins equal to its cost.', @seaside_exp_id),
('Sea Hag', 5, 0, 0, 'Each other player gains a Curse to their deck.', @seaside_exp_id),
('Smugglers', 3, 0, 0, 'Gain a card costing up to 6 Coins that the player to your right gained on their last turn.', @seaside_exp_id),
('Spyglass', 2, 0, 0, '+1 Card, +1 Action. Reveal the top card of your deck. You may discard it or put it into your hand.', @seaside_exp_id),
('Tactician', 5, 0, 0, 'Discard your hand. If you discarded at least 3 cards, then at the start of your next turn, +5 Cards, +1 Action, +1 Buy.', @seaside_exp_id),
('Treasury', 5, 0, 0, '+1 Card, +1 Action, +1 Coin. When you discard this from play, if you did not buy a Victory card this turn, you may put this onto your deck.', @seaside_exp_id),
('Wharf', 5, 0, 0, '+2 Cards, +1 Buy. At the start of your next turn, +2 Cards, +1 Buy.', @seaside_exp_id);

-- Alchemy Cards
INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, text_description, expansion_id) VALUES
('Apothecary', 2, 1, 0, '+1 Card, +1 Action. Reveal the top 4 cards of your deck. Put any Coppers revealed into your hand. Put the rest back on top in any order.', @alchemy_exp_id),
('Apprentice', 5, 0, 0, '+1 Action. Trash a card from your hand. +Coins equal to its cost. +Cards equal to its cost divided by 2 (rounded down).', @alchemy_exp_id),
('Familiar', 3, 1, 0, '+1 Card, +1 Action. Each other player gains a Curse.', @alchemy_exp_id),
('Golem', 4, 1, 0, 'Reveal cards from your deck until you reveal 2 Action cards. Put those 2 into your hand. Discard the rest.', @alchemy_exp_id),
('Herbalist', 2, 0, 0, '+1 Buy, +1 Coin. When you discard this from play, you may put a Treasure card from your discard pile on top of your deck.', @alchemy_exp_id),
('Philosopher''s Stone', 3, 1, 0, 'When you gain this, gain a Potion. When you play this, worth 1 Coin per 5 cards in your deck (rounded down).', @alchemy_exp_id),
('Potion', 4, 0, 0, '+1 Potion (used for buying cards)', @alchemy_exp_id),
('Scrying Pool', 2, 1, 0, '+1 Action. Each player (including you) reveals the top card of their deck. If it is an Action card, they put it into their hand. Otherwise, they discard it. For each card put into a player''s hand, +1 Card.', @alchemy_exp_id),
('Transmute', 0, 1, 0, 'Trash a card from your hand. If it is an Action card, gain a Gold. If it is a Treasure card, gain a Duchy. If it is a Victory card, gain a Province.', @alchemy_exp_id),
('University', 2, 1, 0, '+2 Actions. Gain an Action card costing up to 5 Coins.', @alchemy_exp_id),
('Vineyard', 0, 1, 0, 'Worth 1 Victory Point per 3 Action cards you have.', @alchemy_exp_id);

-- Prosperity Cards
INSERT INTO Cards (name, cost_coins, cost_potions, cost_debt, text_description, expansion_id) VALUES
('Bank', 7, 0, 0, 'When you play this, +1 Coin per Treasure card you have in play (including this).', @prosperity_exp_id),
('Bishop', 4, 0, 0, '+1 Coin. You may trash a card from your hand. If you do, +1 Coin per 1 Coin it cost. Each other player gains 1 Victory Point.', @prosperity_exp_id),
('City', 5, 0, 0, '+1 Card, +2 Actions. If there are no empty Supply piles, +1 Card, +1 Action, +1 Buy.', @prosperity_exp_id),
('Colony', 11, 0, 0, '10 Victory Points', @prosperity_exp_id),
('Contraband', 5, 0, 0, '+3 Coins, +1 Buy. Name a card. This turn, you cannot buy named cards. The player to your left gains a Curse.', @prosperity_exp_id),
('Counting House', 5, 0, 0, 'Look through your discard pile. Take all Coppers from it into your hand.', @prosperity_exp_id),
('Expand', 7, 0, 0, 'Trash a card from your hand. Gain a card costing up to 3 Coins more than it.', @prosperity_exp_id),
('Forge', 7, 0, 0, 'Trash any number of cards from your hand. Gain a card with cost exactly equal to the total cost of the trashed cards.', @prosperity_exp_id),
('Goons', 6, 0, 0, '+1 Buy, +2 Coins. While this is in play, when you buy a card, +1 Victory Token.', @prosperity_exp_id),
('Grand Market', 6, 0, 0, '+1 Card, +1 Action, +1 Buy, +1 Coin. You cannot buy this if you have any Copper in play.', @prosperity_exp_id),
('King''s Court', 7, 0, 0, 'You may choose an Action card from your hand. Play it three times.', @prosperity_exp_id),
('Mint', 5, 0, 0, 'You may trash a Treasure card from your hand. If you do, gain a copy of it. When you buy this, you may trash all Treasure cards from your hand.', @prosperity_exp_id),
('Mountebank', 5, 0, 0, '+2 Coins. Each other player gains a Curse and discards a Copper or reveals a hand with no Copper.', @prosperity_exp_id),
('Peddler', 8, 0, 0, '+1 Card, +1 Action, +1 Coin. This costs 2 Coins less per Action card you have in play, but not less than 0.', @prosperity_exp_id),
('Platinum', 9, 0, 0, '+5 Coins', @prosperity_exp_id),
('Quarry', 4, 0, 0, '+1 Coin. While this is in play, Action cards cost 2 Coins less, but not less than 0.', @prosperity_exp_id),
('Rabble', 5, 0, 0, '+3 Cards. Each other player reveals the top 3 cards of their deck. They discard the Action and Treasure cards and put the rest back on top in any order.', @prosperity_exp_id),
('Royal Seal', 5, 0, 0, '+1 Coin. While this is in play, when you gain a card, you may put it on top of your deck.', @prosperity_exp_id),
('Talisman', 4, 0, 0, '+1 Coin. While this is in play, when you buy a card costing 4 Coins or less (and not a Victory card), gain a copy of it.', @prosperity_exp_id),
('Vault', 5, 0, 0, '+2 Coins. You may discard any number of cards. For each card discarded, +1 Coin. Each other player may discard 2 cards to gain a Gold.', @prosperity_exp_id),
('Venture', 3, 0, 0, 'Reveal cards from your deck until you reveal a Treasure. Put that Treasure into your hand. Discard the other revealed cards.', @prosperity_exp_id);


-- 6. Insert into Games
-- This uses IDs from Players table

SET @alice_id = (SELECT player_id FROM Players WHERE username = 'alice_plays');
SET @bob_id = (SELECT player_id FROM Players WHERE username = 'bob_builder');
SET @charlie_id = (SELECT player_id FROM Players WHERE username = 'charlie_chaplin');
SET @diana_id = (SELECT player_id FROM Players WHERE username = 'diana_prince');

INSERT INTO Games (winner_player_id) VALUES
(@alice_id),
(@bob_id),
(@diana_id);

-- ---------------------------------------------------
-- Section 3: Insert Data into Junction Tables
-- (These tables link multiple other tables)
-- ---------------------------------------------------

-- 7. Insert into Card_CardType

-- 1. Declare and set all type_id variables (MySQL syntax)
-- Ensure your CardTypes table has these entries.
SET @action_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Action');
SET @treasure_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Treasure');
SET @victory_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Victory');
SET @attack_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Attack');
SET @reaction_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Reaction');
SET @duration_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Duration');
SET @curse_type = (SELECT type_id FROM CardTypes WHERE type_name = 'Curse');

-- 2. Declare and set card_id variables for ALL cards (MySQL syntax)
-- Base Game Cards
SET @copper_card_id = (SELECT card_id FROM Cards WHERE name = 'Copper');
SET @silver_card_id = (SELECT card_id FROM Cards WHERE name = 'Silver');
SET @gold_card_id = (SELECT card_id FROM Cards WHERE name = 'Gold');
SET @estate_card_id = (SELECT card_id FROM Cards WHERE name = 'Estate');
SET @duchy_card_id = (SELECT card_id FROM Cards WHERE name = 'Duchy');
SET @province_card_id = (SELECT card_id FROM Cards WHERE name = 'Province');
SET @curse_card_id = (SELECT card_id FROM Cards WHERE name = 'Curse');
SET @cellar_card_id = (SELECT card_id FROM Cards WHERE name = 'Cellar');
SET @market_card_id = (SELECT card_id FROM Cards WHERE name = 'Market');
SET @chapel_card_id = (SELECT card_id FROM Cards WHERE name = 'Chapel');
SEt @council_room_card_id = (SELECT card_id FROM Cards WHERE name = 'Council Room');
SET @festival_card_id = (SELECT card_id FROM Cards WHERE name = 'Festival');
SET @gardens_card_id = (SELECT card_id FROM Cards WHERE name = 'Gardens');
SET @laboratory_card_id = (SELECT card_id FROM Cards WHERE name = 'Laboratory');
SET @library_card_id = (SELECT card_id FROM Cards WHERE name = 'Library');
SET @militia_card_id = (SELECT card_id FROM Cards WHERE name = 'Militia');
SET @mine_card_id = (SELECT card_id FROM Cards WHERE name = 'Mine');
SET @moat_card_id = (SELECT card_id FROM Cards WHERE name = 'Moat');
SET @remodel_card_id = (SELECT card_id FROM Cards WHERE name = 'Remodel');
SET @smithy_card_id = (SELECT card_id FROM Cards WHERE name = 'Smithy');
SET @village_card_id = (SELECT card_id FROM Cards WHERE name = 'Village');
SET @witch_card_id = (SELECT card_id FROM Cards WHERE name = 'Witch');
SET @workshop_card_id = (SELECT card_id FROM Cards WHERE name = 'Workshop');

-- Intrigue Cards
SET @baron_card_id = (SELECT card_id FROM Cards WHERE name = 'Baron');
SET @bridge_card_id = (SELECT card_id FROM Cards WHERE name = 'Bridge');
SET @conspirator_card_id = (SELECT card_id FROM Cards WHERE name = 'Conspirator');
SET @coppersmith_card_id = (SELECT card_id FROM Cards WHERE name = 'Coppersmith');
SET @courtyard_card_id = (SELECT card_id FROM Cards WHERE name = 'Courtyard');
SET @duke_card_id = (SELECT card_id FROM Cards WHERE name = 'Duke');
SET @great_hall_card_id = (SELECT card_id FROM Cards WHERE name = 'Great Hall');
SET @harem_card_id = (SELECT card_id FROM Cards WHERE name = 'Harem');
SET @ironworks_card_id = (SELECT card_id FROM Cards WHERE name = 'Ironworks');
SET @masquerade_card_id = (SELECT card_id FROM Cards WHERE name = 'Masquerade');
SET @minion_card_id = (SELECT card_id FROM Cards WHERE name = 'Minion');
SET @nobles_card_id = (SELECT card_id FROM Cards WHERE name = 'Nobles');
SET @pawn_card_id = (SELECT card_id FROM Cards WHERE name = 'Pawn');
SET @saboteur_card_id = (SELECT card_id FROM Cards WHERE name = 'Saboteur');
SET @scout_card_id = (SELECT card_id FROM Cards WHERE name = 'Scout');
SET @shanty_town_card_id = (SELECT card_id FROM Cards WHERE name = 'Shanty Town');
SET @steward_card_id = (SELECT card_id FROM Cards WHERE name = 'Steward');
SET @swindler_card_id = (SELECT card_id FROM Cards WHERE name = 'Swindler');
SET @torturer_card_id = (SELECT card_id FROM Cards WHERE name = 'Torturer');
SET @tradepost_card_id = (SELECT card_id FROM Cards WHERE name = 'Tradepost');
SET @tribute_card_id = (SELECT card_id FROM Cards WHERE name = 'Tribute');
SET @wishing_well_card_id = (SELECT card_id FROM Cards WHERE name = 'Wishing Well');

-- Seaside Cards
SET @ambassador_card_id = (SELECT card_id FROM Cards WHERE name = 'Ambassador');
SET @caravan_card_id = (SELECT card_id FROM Cards WHERE name = 'Caravan');
SET @cutpurse_card_id = (SELECT card_id FROM Cards WHERE name = 'Cutpurse');
SET @explorer_card_id = (SELECT card_id FROM Cards WHERE name = 'Explorer');
SET @fishing_village_card_id = (SELECT card_id FROM Cards WHERE name = 'Fishing Village');
SET @ghost_ship_card_id = (SELECT card_id FROM Cards WHERE name = 'Ghost Ship');
SET @haven_card_id = (SELECT card_id FROM Cards WHERE name = 'Haven');
SET @island_card_id = (SELECT card_id FROM Cards WHERE name = 'Island');
SET @lighthouse_card_id = (SELECT card_id FROM Cards WHERE name = 'Lighthouse');
SET @lookout_card_id = (SELECT card_id FROM Cards WHERE name = 'Lookout');
SET @merchant_ship_card_id = (SELECT card_id FROM Cards WHERE name = 'Merchant Ship');
SET @native_village_card_id = (SELECT card_id FROM Cards WHERE name = 'Native Village');
SET @navigator_card_id = (SELECT card_id FROM Cards WHERE name = 'Navigator');
SET @outpost_card_id = (SELECT card_id FROM Cards WHERE name = 'Outpost');
SET @pearl_diver_card_id = (SELECT card_id FROM Cards WHERE name = 'Pearl Diver');
SET @pirate_ship_card_id = (SELECT card_id FROM Cards WHERE name = 'Pirate Ship');
SET @salvager_card_id = (SELECT card_id FROM Cards WHERE name = 'Salvager');
SET @sea_hag_card_id = (SELECT card_id FROM Cards WHERE name = 'Sea Hag');
SET @smugglers_card_id = (SELECT card_id FROM Cards WHERE name = 'Smugglers');
SET @spyglass_card_id = (SELECT card_id FROM Cards WHERE name = 'Spyglass');
SET @tactician_card_id = (SELECT card_id FROM Cards WHERE name = 'Tactician');
SET @treasury_card_id = (SELECT card_id FROM Cards WHERE name = 'Treasury');
SET @wharf_card_id = (SELECT card_id FROM Cards WHERE name = 'Wharf');

-- Alchemy Cards
SET @apothecary_card_id = (SELECT card_id FROM Cards WHERE name = 'Apothecary');
SET @apprentice_card_id = (SELECT card_id FROM Cards WHERE name = 'Apprentice');
SET @familiar_card_id = (SELECT card_id FROM Cards WHERE name = 'Familiar');
SET @golem_card_id = (SELECT card_id FROM Cards WHERE name = 'Golem');
SET @herbalist_card_id = (SELECT card_id FROM Cards WHERE name = 'Herbalist');
SET @philosophers_stone_card_id = (SELECT card_id FROM Cards WHERE name = 'Philosopher''s Stone');
SET @potion_card_id = (SELECT card_id FROM Cards WHERE name = 'Potion');
SET @scrying_pool_card_id = (SELECT card_id FROM Cards WHERE name = 'Scrying Pool');
SET @transmute_card_id = (SELECT card_id FROM Cards WHERE name = 'Transmute');
SET @university_card_id = (SELECT card_id FROM Cards WHERE name = 'University');
SET @vineyard_card_id = (SELECT card_id FROM Cards WHERE name = 'Vineyard');

-- Prosperity Cards
SET @bank_card_id = (SELECT card_id FROM Cards WHERE name = 'Bank');
SET @bishop_card_id = (SELECT card_id FROM Cards WHERE name = 'Bishop');
SET @city_card_id = (SELECT card_id FROM Cards WHERE name = 'City');
SET @colony_card_id = (SELECT card_id FROM Cards WHERE name = 'Colony');
SET @contraband_card_id = (SELECT card_id FROM Cards WHERE name = 'Contraband');
SET @counting_house_card_id = (SELECT card_id FROM Cards WHERE name = 'Counting House');
SET @expand_card_id = (SELECT card_id FROM Cards WHERE name = 'Expand');
SET @forge_card_id = (SELECT card_id FROM Cards WHERE name = 'Forge');
SET @goons_card_id = (SELECT card_id FROM Cards WHERE name = 'Goons');
SET @grand_market_card_id = (SELECT card_id FROM Cards WHERE name = 'Grand Market');
SET @kings_court_card_id = (SELECT card_id FROM Cards WHERE name = 'King''s Court');
SET @mint_card_id = (SELECT card_id FROM Cards WHERE name = 'Mint');
SET @mountebank_card_id = (SELECT card_id FROM Cards WHERE name = 'Mountebank');
SET @peddler_card_id = (SELECT card_id FROM Cards WHERE name = 'Peddler');
SET @platinum_card_id = (SELECT card_id FROM Cards WHERE name = 'Platinum');
SET @quarry_card_id = (SELECT card_id FROM Cards WHERE name = 'Quarry');
SET @rabble_card_id = (SELECT card_id FROM Cards WHERE name = 'Rabble');
SET @royal_seal_card_id = (SELECT card_id FROM Cards WHERE name = 'Royal Seal');
SET @talisman_card_id = (SELECT card_id FROM Cards WHERE name = 'Talisman');
SET @vault_card_id = (SELECT card_id FROM Cards WHERE name = 'Vault');
SET @venture_card_id = (SELECT card_id FROM Cards WHERE name = 'Venture');


-- 3. Insert into Card_CardType for all cards
-- Using INSERT IGNORE to prevent errors if a card_id/type_id pair already exists
INSERT IGNORE INTO Card_CardType (card_id, type_id) VALUES
-- Base Game
(@copper_card_id, @treasure_type),
(@silver_card_id, @treasure_type),
(@gold_card_id, @treasure_type),
(@estate_card_id, @victory_type),
(@duchy_card_id, @victory_type),
(@province_card_id, @victory_type),
(@curse_card_id, @curse_type),
(@cellar_card_id, @action_type),
(@market_card_id, @action_type),
(@market_card_id, @treasure_type), -- Market is Action-Treasure
(@chapel_card_id, @action_type),
(@council_room_card_id, @action_type),
(@festival_card_id, @action_type),
(@gardens_card_id, @victory_type),
(@laboratory_card_id, @action_type),
(@library_card_id, @action_type),
(@militia_card_id, @action_type),
(@militia_card_id, @attack_type), -- Militia is Action-Attack
(@mine_card_id, @action_type),
(@moat_card_id, @action_type),
(@moat_card_id, @reaction_type), -- Moat is Action-Reaction
(@remodel_card_id, @action_type),
(@smithy_card_id, @action_type),
(@village_card_id, @action_type),
(@witch_card_id, @action_type),
(@witch_card_id, @attack_type), -- Witch is Action-Attack
(@workshop_card_id, @action_type),

-- Intrigue Cards
(@baron_card_id, @action_type),
(@bridge_card_id, @action_type),
(@conspirator_card_id, @action_type),
(@coppersmith_card_id, @action_type),
(@courtyard_card_id, @action_type),
(@duke_card_id, @victory_type),
(@great_hall_card_id, @action_type),
(@great_hall_card_id, @victory_type), -- Great Hall is Action-Victory
(@harem_card_id, @treasure_type),
(@harem_card_id, @victory_type), -- Harem is Treasure-Victory
(@ironworks_card_id, @action_type),
(@masquerade_card_id, @action_type),
(@minion_card_id, @action_type),
(@minion_card_id, @attack_type), -- Minion is Action-Attack
(@nobles_card_id, @action_type),
(@nobles_card_id, @victory_type), -- Nobles is Action-Victory
(@pawn_card_id, @action_type),
(@saboteur_card_id, @action_type),
(@saboteur_card_id, @attack_type), -- Saboteur is Action-Attack
(@scout_card_id, @action_type),
(@shanty_town_card_id, @action_type),
(@steward_card_id, @action_type),
(@swindler_card_id, @action_type),
(@swindler_card_id, @attack_type), -- Swindler is Action-Attack
(@torturer_card_id, @action_type),
(@torturer_card_id, @attack_type), -- Torturer is Action-Attack
(@tradepost_card_id, @action_type),
(@tribute_card_id, @action_type),
(@wishing_well_card_id, @action_type),

-- Seaside Cards
(@ambassador_card_id, @action_type),
(@ambassador_card_id, @attack_type), -- Ambassador is Action-Attack
(@caravan_card_id, @action_type),
(@caravan_card_id, @duration_type), -- Caravan is Action-Duration
(@cutpurse_card_id, @action_type),
(@cutpurse_card_id, @attack_type), -- Cutpurse is Action-Attack
(@explorer_card_id, @action_type),
(@fishing_village_card_id, @action_type),
(@fishing_village_card_id, @duration_type), -- Fishing Village is Action-Duration
(@ghost_ship_card_id, @action_type),
(@ghost_ship_card_id, @attack_type), -- Ghost Ship is Action-Attack
(@haven_card_id, @action_type),
(@haven_card_id, @duration_type), -- Haven is Action-Duration
(@island_card_id, @action_type),
(@island_card_id, @victory_type), -- Island is Action-Victory
(@lighthouse_card_id, @action_type),
(@lighthouse_card_id, @duration_type),
(@lighthouse_card_id, @reaction_type), -- Lighthouse is Action-Duration-Reaction
(@lookout_card_id, @action_type),
(@merchant_ship_card_id, @action_type),
(@merchant_ship_card_id, @duration_type), -- Merchant Ship is Action-Duration
(@native_village_card_id, @action_type),
(@navigator_card_id, @action_type),
(@navigator_card_id, @treasure_type), -- Navigator is Action-Treasure
(@outpost_card_id, @action_type),
(@outpost_card_id, @duration_type), -- Outpost is Action-Duration
(@pearl_diver_card_id, @action_type),
(@pirate_ship_card_id, @action_type),
(@pirate_ship_card_id, @attack_type),
(@pirate_ship_card_id, @duration_type), -- Pirate Ship is Action-Attack-Duration
(@salvager_card_id, @action_type),
(@salvager_card_id, @treasure_type), -- Salvager is Action-Treasure
(@sea_hag_card_id, @action_type),
(@sea_hag_card_id, @attack_type), -- Sea Hag is Action-Attack
(@smugglers_card_id, @action_type),
(@smugglers_card_id, @reaction_type), -- Smugglers is Action-Reaction
(@spyglass_card_id, @action_type),
(@spyglass_card_id, @treasure_type), -- Spyglass is Action-Treasure
(@tactician_card_id, @action_type),
(@tactician_card_id, @duration_type), -- Tactician is Action-Duration
(@treasury_card_id, @action_type),
(@treasury_card_id, @treasure_type), -- Treasury is Action-Treasure
(@wharf_card_id, @action_type),
(@wharf_card_id, @duration_type), -- Wharf is Action-Duration

-- Alchemy Cards
(@apothecary_card_id, @action_type),
(@apprentice_card_id, @action_type),
(@familiar_card_id, @action_type),
(@familiar_card_id, @attack_type), -- Familiar is Action-Attack
(@golem_card_id, @action_type),
(@herbalist_card_id, @action_type),
(@philosophers_stone_card_id, @treasure_type), -- Philosopher's Stone is Treasure
(@potion_card_id, @treasure_type), -- Potion is Treasure
(@scrying_pool_card_id, @action_type),
(@scrying_pool_card_id, @attack_type), -- Scrying Pool is Action-Attack
(@transmute_card_id, @action_type),
(@university_card_id, @action_type),
(@vineyard_card_id, @victory_type), -- Vineyard is Victory

-- Prosperity Cards
(@bank_card_id, @treasure_type),
(@bishop_card_id, @action_type),
(@city_card_id, @action_type),
(@colony_card_id, @victory_type),
(@contraband_card_id, @treasure_type),
(@counting_house_card_id, @action_type),
(@expand_card_id, @action_type),
(@forge_card_id, @action_type),
(@goons_card_id, @action_type),
(@goons_card_id, @attack_type), -- Goons is Action-Attack
(@grand_market_card_id, @action_type),
(@grand_market_card_id, @treasure_type), -- Grand Market is Action-Treasure
(@kings_court_card_id, @action_type),
(@mint_card_id, @action_type),
(@mountebank_card_id, @action_type),
(@mountebank_card_id, @attack_type), -- Mountebank is Action-Attack
(@peddler_card_id, @action_type),
(@peddler_card_id, @treasure_type), -- Peddler is Action-Treasure
(@platinum_card_id, @treasure_type),
(@quarry_card_id, @treasure_type),
(@rabble_card_id, @action_type),
(@rabble_card_id, @attack_type), -- Rabble is Action-Attack
(@royal_seal_card_id, @treasure_type),
(@royal_seal_card_id, @reaction_type), -- Royal Seal is Treasure-Reaction
(@talisman_card_id, @treasure_type),
(@talisman_card_id, @reaction_type), -- Talisman is Treasure-Reaction
(@vault_card_id, @action_type),
(@vault_card_id, @treasure_type), -- Vault is Action-Treasure
(@venture_card_id, @treasure_type);




-- 8. Insert into ExpansionRegionPrices
-- Links expansions to regions with their specific prices
SET @usa_region_id = (SELECT region_id FROM Regions WHERE region_name = 'USA');
SET @europe_region_id = (SELECT region_id FROM Regions WHERE region_name = 'Europe');
SET @canada_region_id = (SELECT region_id FROM Regions WHERE region_name = 'Canada');
SET @australia_region_id = (SELECT region_id FROM Regions WHERE region_name = 'Australia');

SET @base_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Base');
SET @intrigue_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Intrigue');
SET @seaside_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Seaside');
SET @prosperity_exp_id = (SELECT expansion_id FROM Expansions WHERE name = 'Prosperity');

INSERT INTO ExpansionRegionPrices (expansion_id, region_id, price) VALUES
(@base_exp_id, @usa_region_id, 45.00),
(@base_exp_id, @europe_region_id, 39.99),
(@base_exp_id, @canada_region_id, 55.00),
(@intrigue_exp_id, @usa_region_id, 39.99),
(@intrigue_exp_id, @europe_region_id, 34.99),
(@seaside_exp_id, @usa_region_id, 39.99),
(@prosperity_exp_id, @usa_region_id, 49.99),
(@prosperity_exp_id, @australia_region_id, 65.00);

-- Add some sample data for PlayerExpansions (after Players and Expansions are populated)
SET @a_id = (SELECT player_id FROM Players WHERE username = 'a');

INSERT INTO PlayerExpansions (player_id, expansion_id) VALUES
(@a_id, @base_exp_id),
(@a_id, @intrigue_exp_id);





