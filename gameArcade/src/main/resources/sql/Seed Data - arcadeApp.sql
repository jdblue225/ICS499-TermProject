USE arcadeApp;

-- Seed data for Users table
INSERT INTO Users (username, firstname, lastname, email, password, usertype, ImageName, Image) VALUES
('user01', 'John', 'Doe', 'john.doe@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'admin', NULL, NULL),
('user02', 'Jane', 'Doe', 'jane.doe@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user03', 'Jim', 'Beam', 'jim.beam@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user04', 'Jack', 'Daniels', 'jack.daniels@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user05', 'Johnny', 'Walker', 'johnny.walker@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user06', 'Jose', 'Cuervo', 'jose.cuervo@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user07', 'Captain', 'Morgan', 'captain.morgan@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user08', 'Evan', 'Williams', 'evan.williams@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user09', 'Jim', 'Taylor', 'jim.taylor@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL),
('user10', 'Jack', 'White', 'jack.white@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'user', NULL, NULL);

-- Seed data for Games table
INSERT INTO Games (Title, Description, Developer, ReleaseDate, Price, ImageName, AverageRating) VALUES
('Pong', 'Pong is one of the earliest arcade video games, featuring a simple two-dimensional table tennis game. Players control paddles to hit a ball back and forth, aiming to score points by getting the ball past the opponent\'s paddle.', 'Atari Inc.', '1972-06-01', 5.99, 'pong.jpg', 4.50),
('Minesweeper', 'Minesweeper is a classic puzzle game where players must clear a grid of hidden mines without detonating any. By revealing squares and using numerical hints, players can strategically mark and avoid mines to win the game', 'Microsoft', '1989-10-10', 3.99, 'minesweeper.jpg', 5.00),
('Snake', 'Snake is a popular arcade game where players control a growing snake that must navigate a confined space, consuming food items to grow longer. The challenge increases as the snake grows, requiring careful maneuvering to avoid collisions with itself and the walls.', 'Nokia', '1997-10-10', 1.99, 'snake.jpg', 3.20 ),
('Frogger', 'Frogger is a classic arcade game where players guide a frog across a busy road and a treacherous river. The goal is to reach the frog\'s home on the other side while avoiding traffic, jumping on logs, and dodging various obstacles.', 'Konami', '1981-06-05', 6.99, 'frogger.jpg', 4.20),
('Words', 'Words is a word puzzle game where players form words from a random selection of letters. The objective is to create as many valid words as possible within a given time limit, testing players\' vocabulary and quick thinking.', 'Lexicon', '2009-03-03', 4.99, 'words.jpg', 4.20),
('Tic-Tac-Toe', 'Tic Tac Toe is a classic two-player game where players take turns marking spaces in a 3x3 grid with X or O. The goal is to be the first to get three of their marks in a row, either horizontally, vertically, or diagonally.', 'Classic Games Studio','2010-09-10', 0.00, 'tictactoe.jpg', 2.10),
('Cardz', 'Cardz is a strategic card game where players draw and play cards from a deck to achieve specific objectives. The game involves elements of chance and strategy, with players aiming to outmaneuver their opponents through skillful card play.', 'CardMaster Inc.','2015-02-14', 2.99, 'cardz.jpg', 1.50),
('Dice', 'Dice is a game of chance where players roll one or more dice to achieve certain outcomes or scores. It can involve various rule sets and objectives, such as achieving specific number combinations, high scores, or completing sequences.', 'RollEm Games', '2012-07-21', 2.49, 'dice.jpg', 1.60),
('Invaderz', 'Invaderz is an action-packed arcade game inspired by classic space shooter games. Players control a spaceship, moving horizontally to shoot down waves of descending alien invaders while avoiding enemy fire. The goal is to clear all invaders and achieve the highest score possible.', 'Space Shooter Studios', '2018-11-25', 7.99, 'invaderz.jpg', 5.00),
('Cookie', 'Cookie Game is a fun and addictive game where players collect and manage cookies. The objective is to gather as many cookies as possible by clicking on them and using various upgrades and tools to automate and increase cookie production over time.', 'Cookie Coders', '2024-08-01', 4.99, 'cookie.jpg', 3.50);

-- Seed data for OwnedGames table
INSERT INTO OwnedGames (UserID, GameID, PurchaseDate, PlayTime, LastPlayedDate, AchievementsUnlocked, Score, HighScore, Rating, Review, IsFavorite, GameVersion, CompletionStatus) VALUES
(1, 1, '2024-01-01', 120, '2024-06-30', 5, 8500, 9000, 4.5, 'Great game!', TRUE, '1.0', 'Completed'),
(1, 2, '2024-02-15', 80, '2024-06-29', 3, 7200, 8000, 4.0, 'Enjoyable.', FALSE, '1.1', 'In Progress'),
(2, 3, '2024-03-20', 200, '2024-07-01', 7, 9800, 10000, 5.0, 'Outstanding!', TRUE, '1.0', 'Completed'),
(2, 4, '2024-04-25', 50, '2024-06-28', 1, 6400, 7000, 3.5, 'Pretty good.', FALSE, '1.2', 'In Progress'),
(3, 5, '2024-05-30', 150, '2024-06-27', 6, 8700, 9500, 4.8, 'Loved it!', TRUE, '1.0', 'Completed'),
(3, 6, '2024-06-10', 10, '2024-06-26', 0, 2000, 2500, 2.5, 'Not my type.', FALSE, '1.0', 'Not Started'),
(4, 7, '2024-07-15', 90, '2024-06-25', 4, 7500, 8500, 4.2, 'Fun to play.', FALSE, '1.1', 'In Progress'),
(4, 8, '2024-08-20', 70, '2024-06-24', 2, 6200, 7000, 3.8, 'Decent.', TRUE, '1.0', 'In Progress'),
(5, 9, '2024-09-25', 130, '2024-06-23', 5, 8400, 9500, 4.5, 'Very engaging.', TRUE, '1.0', 'Completed'),
(5, 10, '2024-10-30', 60, '2024-06-22', 1, 5500, 6000, 3.0, 'Okay.', FALSE, '1.1', 'In Progress'),
(6, 1, '2024-01-01', 120, '2024-06-30', 5, 8500, 9000, 4.5, 'Great game!', TRUE, '1.0', 'Completed'),
(7, 2, '2024-02-15', 80, '2024-06-29', 3, 7200, 8000, 4.0, 'Enjoyable.', FALSE, '1.1', 'In Progress'),
(8, 3, '2024-03-20', 200, '2024-07-01', 7, 9800, 10000, 5.0, 'Outstanding!', TRUE, '1.0', 'Completed'),
(9, 4, '2024-04-25', 50, '2024-06-28', 1, 6400, 7000, 3.5, 'Pretty good.', FALSE, '1.2', 'In Progress'),
(10, 5, '2024-05-30', 150, '2024-06-27', 6, 8700, 9500, 4.8, 'Loved it!', TRUE, '1.0', 'Completed');

INSERT INTO ForumCategories (CategoryID, CategoryName, Description, CreatedAt) VALUES
(1, "General Discussion", "A place for general topics and casual conversation.", "2024-07-18 08:57:49"),
(2, "Game Help", "Ask for help and share tips about games.", "2024-07-18 08:57:49"),
(3, "Announcements", "Official announcements and updates.", "2024-07-18 08:57:49");

INSERT INTO ForumPosts(PostID, UserID, CategoryID, Title, Content, CreatedAt) VALUES
(1, 1, 1, "Welcome to the Forum!", "Hello everyone, welcome to our new forum. Feel free to introduce yourselves!", "2024-07-18 08:57:49"),
(2, 2, 2, "Need help with Level 5", "I am stuck on level 5 of the game. Any tips?", "2024-07-18 08:57:49"),
(3, 3, 3, "New Update Released!", "We are excited to announce the release of a new update for our game.", "2024-07-18 08:57:49"),
(9, 1, 1, "Test1", "Test1 details", "2024-07-18 20:20:36");

INSERT INTO ForumComments(CommentID, PostID, UserID, Content, CreatedAt) VALUES
(1, 1, 2, "Thanks for the welcome! Looking forward to participating.", "2024-07-18 08:57:49"),
(2, 2, 1, "Try using the blue key on the door. It worked for me.", "2024-07-18 08:57:49"),
(3, 3, 1, "Great news! Can’t wait to try the new features.", "2024-07-18 08:57:49");


