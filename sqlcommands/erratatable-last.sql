DROP TABLE IF EXISTS CardErrata;

CREATE TABLE CardErrata (
    card_id INT NOT NULL,
    errata_sequence INT NOT NULL,
    errata_date DATE NOT NULL,
    description TEXT NOT NULL,
    PRIMARY KEY (card_id, errata_sequence),
    FOREIGN KEY (card_id) REFERENCES Cards(card_id) ON DELETE CASCADE
);

-- REVISED Sample data for CardErrata
-- You will need to re-run your full master_setup.sql or manually drop and recreate
-- this table before inserting this data.
-- Each card_id will have its own sequence starting from 1.
SET @village_card_id = (SELECT card_id FROM Cards WHERE name = 'Village');
SET @market_card_id = (SELECT card_id FROM Cards WHERE name = 'Market');
SET @moat_card_id = (SELECT card_id FROM Cards WHERE name = 'Moat');

INSERT INTO CardErrata (card_id, errata_sequence, errata_date, description, source_link) VALUES
(@village_card_id, 1, '2010-06-01', 'Clarification: Village cannot be played if you have no Actions left. (+1 Card, +2 Actions are gained upon playing, not before).', 'https://boardgamegeek.com/thread/1234567'),
(@market_card_id, 1, '2012-01-15', 'Update: Market now gives +1 Buy in addition to other effects.', 'https://dominion.games/rules/updates'),
(@moat_card_id, 1, '2015-03-20', 'Clarification: Moat only defends against Attack cards, not cards that make you discard without the "Attack" type.', NULL),
(@village_card_id, 2, '2018-09-01', 'Minor wording update for consistency with newer card sets.', NULL); -- Second errata for Village