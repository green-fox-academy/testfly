DELETE
FROM troop;

DELETE
FROM building;

DELETE
FROM resource;

DELETE
FROM application_user;

DELETE
FROM kingdom;

-- testUser1 password: pass1
-- testUser2 password: pass2
-- testUser3 password: pass3
INSERT INTO kingdom(id, name)
VALUES (1, 'testKingdom1'),
       (2, 'testKingdom2'),
       (3, 'testKingdom3');

INSERT INTO application_user(id, password, user_email, username, kingdom_id)
VALUES (1, '$2a$10$3A7YK9hDUpHN4plBoCphYOzk426CebJwnaFMk0kN4qEXoWUTiwejC', 'test1@test.test', 'testUser1', 1),
       (2, '$2a$10$y1WkKt52SH8eDm6zvy63v.B0EstAaevqAgfo7plk8v9UuigsMcqxi', 'test2@test.test', 'testUser2', 2),
       (3, '$2a$10$N.4V.83hs.5X2bI0qY0Tme2PYceHtDf2Suzh0QHEcYVZxeS0YhJL6', 'test3@test.test', 'testUser3', 3);

INSERT INTO troop(troop_type, id, level, HP, attack, defense, started_at, finished_at, kingdom_id)
VALUES ('SingleTroop', 1, 1, 1, 1, 1, '2019-02-04 03:00:00', '2019-02-04 03:12:00', 1),
       ('SingleTroop', 2, 5, 5, 5, 5, '2019-02-05 13:00:00', '2019-02-05 15:00:00', 2),
       ('SingleTroop', 3, 10, 10, 10, 10, '2019-02-06 22:30:00', '2019-02-10 22:30:00',3 );

INSERT INTO building(building_type, id, level, HP, started_at, finished_at, kingdom_id)
VALUES ('Mine', 1, 1, 1, '2019-02-04 03:00:00', '2019-02-04 03:12:00', 1),
       ('Barracks', 2, 5, 5, '2019-02-05 13:00:00', '2019-02-05 15:00:00', 2),
       ('TownHall', 3, 10, 10, '2019-02-06 22:30:00', '2019-02-10 22:30:00', 3);

INSERT INTO resource(resource_type, id, amount, updated_at, kingdom_id)
VALUES ('Gold', 1, 0, '2019-02-04 08:00:00', 1),
       ('Food', 2, 10, '2019-02-10 16:00:00', 2),
       ('Gold', 3, 500, '2019-02-11 10:00:00', 3);
