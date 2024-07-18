DROP SCHEMA IF EXISTS arcadeapp;
CREATE SCHEMA arcadeApp; 
USE arcadeApp;
DROP TABLE IF EXISTS users; 
CREATE TABLE users
    (UserID INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(50) NOT NULL UNIQUE,
	FirstName VARCHAR(50), 
    LastName VARCHAR(50),
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    UserType varchar(50),
    CreateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ImageName VARCHAR(255),
    Image LONGBLOB
    );
CREATE TABLE Games (
    GameID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(100) NOT NULL,
    Description TEXT,
    Developer VARCHAR(100),
    ReleaseDate DATE,
    Price DECIMAL(10, 2),
    Image LONGBLOB,
    ImageName VARCHAR(255),
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE ForumCategories (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(50) NOT NULL UNIQUE,
    Description TEXT,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE ForumPosts (
    PostID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    CategoryID INT NOT NULL,
    Title VARCHAR(100) NOT NULL,
    Content TEXT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CategoryID) REFERENCES ForumCategories(CategoryID)
);
CREATE TABLE ForumComments (
    CommentID INT AUTO_INCREMENT PRIMARY KEY,
    PostID INT NOT NULL,
    UserID INT NOT NULL,
    Content TEXT NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (PostID) REFERENCES ForumPosts(PostID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
CREATE TABLE StoreItems (
    ItemID INT AUTO_INCREMENT PRIMARY KEY,
    ItemType VARCHAR(50) NOT NULL,  -- 'game', 'subscription', etc.
    ItemName VARCHAR(100) NOT NULL,
    Description TEXT,
    Price DECIMAL(10, 2) NOT NULL,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    OrderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
CREATE TABLE OrderItems (
    OrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NOT NULL,
    ItemID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (ItemID) REFERENCES StoreItems(ItemID)
);
CREATE TABLE Subscriptions (
    SubscriptionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    ItemID INT NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ItemID) REFERENCES StoreItems(ItemID)
);

CREATE TABLE OwnedGames (
    UserID INT NOT NULL,
    GameID INT NOT NULL,
    PurchaseDate DATE NOT NULL,
    PlayTime INT DEFAULT 0,
    LastPlayedDate DATE,
    AchievementsUnlocked INT DEFAULT 0,
    Score INT NOT NULL,
    HighScore INT,
    Rating DECIMAL(2, 1),
    Review TEXT,
    IsFavorite BOOLEAN DEFAULT FALSE,
    GameVersion VARCHAR(20),
    CompletionStatus ENUM('Not Started', 'In Progress', 'Completed') DEFAULT 'Not Started',
    PRIMARY KEY (UserID, GameID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (GameID) REFERENCES Games(GameID)
);


