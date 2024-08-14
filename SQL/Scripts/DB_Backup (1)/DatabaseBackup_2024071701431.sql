CREATE DATABASE  IF NOT EXISTS `arcadeapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `arcadeapp`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: arcadeapp
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `forumcategories`
--

DROP TABLE IF EXISTS `forumcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forumcategories` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(50) NOT NULL,
  `Description` text,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CategoryID`),
  UNIQUE KEY `CategoryName` (`CategoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forumcategories`
--

LOCK TABLES `forumcategories` WRITE;
/*!40000 ALTER TABLE `forumcategories` DISABLE KEYS */;
/*!40000 ALTER TABLE `forumcategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forumcomments`
--

DROP TABLE IF EXISTS `forumcomments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forumcomments` (
  `CommentID` int NOT NULL AUTO_INCREMENT,
  `PostID` int NOT NULL,
  `UserID` int NOT NULL,
  `Content` text NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CommentID`),
  KEY `PostID` (`PostID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `forumcomments_ibfk_1` FOREIGN KEY (`PostID`) REFERENCES `forumposts` (`PostID`),
  CONSTRAINT `forumcomments_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forumcomments`
--

LOCK TABLES `forumcomments` WRITE;
/*!40000 ALTER TABLE `forumcomments` DISABLE KEYS */;
/*!40000 ALTER TABLE `forumcomments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forumposts`
--

DROP TABLE IF EXISTS `forumposts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forumposts` (
  `PostID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `CategoryID` int NOT NULL,
  `Title` varchar(100) NOT NULL,
  `Content` text NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PostID`),
  KEY `UserID` (`UserID`),
  KEY `CategoryID` (`CategoryID`),
  CONSTRAINT `forumposts_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`userid`),
  CONSTRAINT `forumposts_ibfk_2` FOREIGN KEY (`CategoryID`) REFERENCES `forumcategories` (`CategoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forumposts`
--

LOCK TABLES `forumposts` WRITE;
/*!40000 ALTER TABLE `forumposts` DISABLE KEYS */;
/*!40000 ALTER TABLE `forumposts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `GameID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(100) NOT NULL,
  `Description` text,
  `Developer` varchar(100) DEFAULT NULL,
  `ReleaseDate` date DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ImagePath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`GameID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,'Game Title 1','Description of Game 1','Developer 1','2023-01-01',19.99,'2024-07-07 17:37:22','/games/img/game1.jpg'),(2,'Game Title 2','Description of Game 2','Developer 2','2023-02-15',29.99,'2024-07-07 17:37:22','/games/img/game2.jpg'),(3,'Game Title 3','Description of Game 3','Developer 3','2023-03-20',39.99,'2024-07-07 17:37:22','/games/img/game3.jpg'),(4,'Game Title 4','Description of Game 4','Developer 4','2023-04-25',49.99,'2024-07-07 17:37:22','/games/img/game4.jpg'),(5,'Game Title 5','Description of Game 5','Developer 5','2023-05-30',59.99,'2024-07-07 17:37:22','/games/img/game5.jpg'),(6,'Game Title 6','Description of Game 6','Developer 6','2023-06-10',9.99,'2024-07-07 17:37:22','/games/img/game6.jpg'),(7,'Game Title 7','Description of Game 7','Developer 7','2023-07-15',14.99,'2024-07-07 17:37:22','/games/img/game7.jpg'),(8,'Game Title 8','Description of Game 8','Developer 8','2023-08-20',24.99,'2024-07-07 17:37:22','/games/img/game8.jpg'),(9,'Game Title 9','Description of Game 9','Developer 9','2023-09-25',34.99,'2024-07-07 17:37:22','/games/img/game9.jpg'),(10,'Game Title 10','Description of Game 10','Developer 10','2023-10-30',44.99,'2024-07-07 17:37:22','/games/img/game10.jpg');
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `OrderItemID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `ItemID` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`OrderItemID`),
  KEY `OrderID` (`OrderID`),
  KEY `ItemID` (`ItemID`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`),
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `storeitems` (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `OrderDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `TotalAmount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ownedgames`
--

DROP TABLE IF EXISTS `ownedgames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ownedgames` (
  `UserID` int NOT NULL,
  `GameID` int NOT NULL,
  `PurchaseDate` date NOT NULL,
  `PlayTime` int DEFAULT '0',
  `LastPlayedDate` date DEFAULT NULL,
  `AchievementsUnlocked` int DEFAULT '0',
  `Score` int NOT NULL,
  `Rating` decimal(2,1) DEFAULT NULL,
  `Review` text,
  `IsFavorite` tinyint(1) DEFAULT '0',
  `GameVersion` varchar(20) DEFAULT NULL,
  `CompletionStatus` enum('Not Started','In Progress','Completed') DEFAULT 'Not Started',
  KEY `UserID` (`UserID`),
  KEY `GameID` (`GameID`),
  CONSTRAINT `ownedgames_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`userid`),
  CONSTRAINT `ownedgames_ibfk_2` FOREIGN KEY (`GameID`) REFERENCES `games` (`GameID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ownedgames`
--

LOCK TABLES `ownedgames` WRITE;
/*!40000 ALTER TABLE `ownedgames` DISABLE KEYS */;
INSERT INTO `ownedgames` VALUES (1,1,'2024-01-01',120,'2024-06-30',5,8500,4.5,'Great game!',1,'1.0','Completed'),(1,2,'2024-02-15',80,'2024-06-29',3,7200,4.0,'Enjoyable.',0,'1.1','In Progress'),(2,3,'2024-03-20',200,'2024-07-01',7,9800,5.0,'Outstanding!',1,'1.0','Completed'),(2,4,'2024-04-25',50,'2024-06-28',1,6400,3.5,'Pretty good.',0,'1.2','In Progress'),(3,5,'2024-05-30',150,'2024-06-27',6,8700,4.8,'Loved it!',1,'1.0','Completed'),(3,6,'2024-06-10',10,'2024-06-26',0,2000,2.5,'Not my type.',0,'1.0','Not Started'),(4,7,'2024-07-15',90,'2024-06-25',4,7500,4.2,'Fun to play.',0,'1.1','In Progress'),(4,8,'2024-08-20',70,'2024-06-24',2,6200,3.8,'Decent.',1,'1.0','In Progress'),(5,9,'2024-09-25',130,'2024-06-23',5,8400,4.5,'Very engaging.',1,'1.0','Completed'),(5,10,'2024-10-30',60,'2024-06-22',1,5500,3.0,'Okay.',0,'1.1','In Progress'),(6,1,'2024-01-01',120,'2024-06-30',5,8500,4.5,'Great game!',1,'1.0','Completed'),(7,2,'2024-02-15',80,'2024-06-29',3,7200,4.0,'Enjoyable.',0,'1.1','In Progress'),(8,3,'2024-03-20',200,'2024-07-01',7,9800,5.0,'Outstanding!',1,'1.0','Completed'),(9,4,'2024-04-25',50,'2024-06-28',1,6400,3.5,'Pretty good.',0,'1.2','In Progress'),(10,5,'2024-05-30',150,'2024-06-27',6,8700,4.8,'Loved it!',1,'1.0','Completed');
/*!40000 ALTER TABLE `ownedgames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeitems`
--

DROP TABLE IF EXISTS `storeitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storeitems` (
  `ItemID` int NOT NULL AUTO_INCREMENT,
  `ItemType` varchar(50) NOT NULL,
  `ItemName` varchar(100) NOT NULL,
  `Description` text,
  `Price` decimal(10,2) NOT NULL,
  `CreatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeitems`
--

LOCK TABLES `storeitems` WRITE;
/*!40000 ALTER TABLE `storeitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `storeitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `SubscriptionID` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `ItemID` int NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  PRIMARY KEY (`SubscriptionID`),
  KEY `UserID` (`UserID`),
  KEY `ItemID` (`ItemID`),
  CONSTRAINT `subscriptions_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `users` (`userid`),
  CONSTRAINT `subscriptions_ibfk_2` FOREIGN KEY (`ItemID`) REFERENCES `storeitems` (`ItemID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  `createdat` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user01','John','Doe','john.doe@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','admin','2024-06-18 21:51:18'),(2,'user02','Jane','Doe','jane.doe@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(3,'user03','Jim','Beam','jim.beam@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(4,'user04','Jack','Daniels','jack.daniels@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(5,'user05','Johnny','Walker','johnny.walker@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(6,'user06','Jose','Cuervo','jose.cuervo@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(7,'user07','Captain','Morgan','captain.morgan@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(8,'user08','Evan','Williams','evan.williams@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(9,'user09','Jim','Taylor','jim.taylor@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18'),(10,'user10','Jack','White','jack.white@example.com','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','user','2024-06-18 21:51:18');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-17 14:32:45
