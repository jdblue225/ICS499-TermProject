USE arcadeApp;

INSERT INTO users (username, firstname, lastname, email, password, usertype) VALUES
('user01', 'John', 'Doe', 'john.doe@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin'),
('user02', 'Jane', 'Doe', 'jane.doe@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user03', 'Jim', 'Beam', 'jim.beam@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user04', 'Jack', 'Daniels', 'jack.daniels@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user05', 'Johnny', 'Walker', 'johnny.walker@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user06', 'Jose', 'Cuervo', 'jose.cuervo@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user07', 'Captain', 'Morgan', 'captain.morgan@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user08', 'Evan', 'Williams', 'evan.williams@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user09', 'Jim', 'Taylor', 'jim.taylor@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user'),
('user10', 'Jack', 'White', 'jack.white@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user');

INSERT INTO Games (Title, Description, Developer, ReleaseDate, Price, ImagePath)
VALUES 
('Game Title 1', 'Description of Game 1', 'Developer 1', '2023-01-01', 19.99, '/games/img/game1.jpg'),
('Game Title 2', 'Description of Game 2', 'Developer 2', '2023-02-15', 29.99, '/games/img/game2.jpg'),
('Game Title 3', 'Description of Game 3', 'Developer 3', '2023-03-20', 39.99, '/games/img/game3.jpg'),
('Game Title 4', 'Description of Game 4', 'Developer 4', '2023-04-25', 49.99, '/games/img/game4.jpg'),
('Game Title 5', 'Description of Game 5', 'Developer 5', '2023-05-30', 59.99, '/games/img/game5.jpg'),
('Game Title 6', 'Description of Game 6', 'Developer 6', '2023-06-10', 9.99, '/games/img/game6.jpg'),
('Game Title 7', 'Description of Game 7', 'Developer 7', '2023-07-15', 14.99, '/games/img/game7.jpg'),
('Game Title 8', 'Description of Game 8', 'Developer 8', '2023-08-20', 24.99, '/games/img/game8.jpg'),
('Game Title 9', 'Description of Game 9', 'Developer 9', '2023-09-25', 34.99, '/games/img/game9.jpg'),
('Game Title 10', 'Description of Game 10', 'Developer 10', '2023-10-30', 44.99, '/games/img/game10.jpg');

INSERT INTO OwnedGames (UserID, GameID, PurchaseDate, PlayTime, LastPlayedDate, AchievementsUnlocked, Score, Rating, Review, IsFavorite, GameVersion, CompletionStatus)
VALUES
(1, 1, '2024-01-01', 120, '2024-06-30', 5, 8500, 4.5, 'Great game!', TRUE, '1.0', 'Completed'),
(1, 2, '2024-02-15', 80, '2024-06-29', 3, 7200, 4.0, 'Enjoyable.', FALSE, '1.1', 'In Progress'),
(2, 3, '2024-03-20', 200, '2024-07-01', 7, 9800, 5.0, 'Outstanding!', TRUE, '1.0', 'Completed'),
(2, 4, '2024-04-25', 50, '2024-06-28', 1, 6400, 3.5, 'Pretty good.', FALSE, '1.2', 'In Progress'),
(3, 5, '2024-05-30', 150, '2024-06-27', 6, 8700, 4.8, 'Loved it!', TRUE, '1.0', 'Completed'),
(3, 6, '2024-06-10', 10, '2024-06-26', 0, 2000, 2.5, 'Not my type.', FALSE, '1.0', 'Not Started'),
(4, 7, '2024-07-15', 90, '2024-06-25', 4, 7500, 4.2, 'Fun to play.', FALSE, '1.1', 'In Progress'),
(4, 8, '2024-08-20', 70, '2024-06-24', 2, 6200, 3.8, 'Decent.', TRUE, '1.0', 'In Progress'),
(5, 9, '2024-09-25', 130, '2024-06-23', 5, 8400, 4.5, 'Very engaging.', TRUE, '1.0', 'Completed'),
(5, 10, '2024-10-30', 60, '2024-06-22', 1, 5500, 3.0, 'Okay.', FALSE, '1.1', 'In Progress'),
(6, 1, '2024-01-01', 120, '2024-06-30', 5, 8500, 4.5, 'Great game!', TRUE, '1.0', 'Completed'),
(7, 2, '2024-02-15', 80, '2024-06-29', 3, 7200, 4.0, 'Enjoyable.', FALSE, '1.1', 'In Progress'),
(8, 3, '2024-03-20', 200, '2024-07-01', 7, 9800, 5.0, 'Outstanding!', TRUE, '1.0', 'Completed'),
(9, 4, '2024-04-25', 50, '2024-06-28', 1, 6400, 3.5, 'Pretty good.', FALSE, '1.2', 'In Progress'),
(10, 5, '2024-05-30', 150, '2024-06-27', 6, 8700, 4.8, 'Loved it!', TRUE, '1.0', 'Completed');



