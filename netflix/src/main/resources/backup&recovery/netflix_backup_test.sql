-- MySQL dump 10.13  Distrib 9.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: netflix
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `account_id` int unsigned NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `subscription` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `trial_start_date` date NOT NULL,
  `discount` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `episode`
--

DROP TABLE IF EXISTS `episode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `episode` (
  `episode_id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `duration` time DEFAULT '00:00:00',
  `series_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`episode_id`),
  KEY `FK_episode_series` (`series_id`),
  CONSTRAINT `FK6hj3n90dblxp7xd9fc1urj8ko` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `episode`
--

LOCK TABLES `episode` WRITE;
/*!40000 ALTER TABLE `episode` DISABLE KEYS */;
/*!40000 ALTER TABLE `episode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `genre_id` int unsigned NOT NULL,
  `genre_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Horror'),(2,'Comedy'),(3,'Romance'),(4,'Melodrama'),(5,'Tearjerker'),(7,'fantasy'),(8,'sci-fi'),(17,'Adventure'),(18,'triler'),(19,'biography'),(20,'History'),(21,'musical'),(29,'War');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genreformovie`
--

DROP TABLE IF EXISTS `genreformovie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genreformovie` (
  `genre_id` int unsigned NOT NULL,
  `movie_id` int unsigned NOT NULL,
  PRIMARY KEY (`genre_id`,`movie_id`),
  KEY `movie_id` (`movie_id`),
  CONSTRAINT `FK9k0bup58wyigpu52ap88luwm3` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`),
  CONSTRAINT `FKcooxhml1jwh4bnp2o9n63ka3k` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genreformovie`
--

LOCK TABLES `genreformovie` WRITE;
/*!40000 ALTER TABLE `genreformovie` DISABLE KEYS */;
INSERT INTO `genreformovie` VALUES (2,1),(5,1),(7,2),(3,3),(8,3);
/*!40000 ALTER TABLE `genreformovie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genreforseries`
--

DROP TABLE IF EXISTS `genreforseries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genreforseries` (
  `genre_id` int unsigned NOT NULL,
  `series_id` int unsigned NOT NULL,
  PRIMARY KEY (`genre_id`,`series_id`),
  KEY `series_id` (`series_id`),
  CONSTRAINT `FK46gf3ps13sggr3a79227kltsn` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`),
  CONSTRAINT `FKn3exg02r09uca4mdojqte8dly` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genreforseries`
--

LOCK TABLES `genreforseries` WRITE;
/*!40000 ALTER TABLE `genreforseries` DISABLE KEYS */;
/*!40000 ALTER TABLE `genreforseries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genreforuser`
--

DROP TABLE IF EXISTS `genreforuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genreforuser` (
  `genre_id` int unsigned NOT NULL,
  `account_id` int unsigned NOT NULL,
  PRIMARY KEY (`genre_id`,`account_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `FKrh2w4n7xtka8f2svniqwoghq1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genreforuser`
--

LOCK TABLES `genreforuser` WRITE;
/*!40000 ALTER TABLE `genreforuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `genreforuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitation`
--

DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `inviter_id` bigint NOT NULL,
  `invitee_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `inviter_id` (`inviter_id`),
  KEY `invitee_id` (`invitee_id`),
  CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`inviter_id`) REFERENCES `user` (`account_id`),
  CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`invitee_id`) REFERENCES `user` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitation`
--

LOCK TABLES `invitation` WRITE;
/*!40000 ALTER TABLE `invitation` DISABLE KEYS */;
INSERT INTO `invitation` VALUES (1,18,26,'2025-01-04 09:39:41'),(2,19,24,'2025-01-04 10:13:18');
/*!40000 ALTER TABLE `invitation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language` (
  `language_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (1,'english'),(2,'latvian'),(3,'lithuanian'),(4,'dutch'),(5,'spanish'),(6,'ukranian');
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `movie_id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `duration` time NOT NULL DEFAULT '00:00:00',
  `sd_available` bit(1) DEFAULT b'1',
  `hd_available` bit(1) DEFAULT b'0',
  `uhd_available` bit(1) DEFAULT b'0',
  `minimum_age` int NOT NULL DEFAULT '12',
  PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'womp-womp, funny','01:32:00',_binary '',_binary '',_binary '\0',14),(2,'lordoftherings','03:30:52',_binary '',_binary '',_binary '\0',12),(3,'star wars','00:31:16',_binary '',_binary '',_binary '\0',12);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moviesprofilewatchlist`
--

DROP TABLE IF EXISTS `moviesprofilewatchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moviesprofilewatchlist` (
  `profile_id` int unsigned NOT NULL,
  `movie_id` int unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moviesprofilewatchlist`
--

LOCK TABLES `moviesprofilewatchlist` WRITE;
/*!40000 ALTER TABLE `moviesprofilewatchlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `moviesprofilewatchlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movieviewcount`
--

DROP TABLE IF EXISTS `movieviewcount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movieviewcount` (
  `account_id` int unsigned NOT NULL,
  `movie_id` int unsigned NOT NULL,
  `number` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movieviewcount`
--

LOCK TABLES `movieviewcount` WRITE;
/*!40000 ALTER TABLE `movieviewcount` DISABLE KEYS */;
INSERT INTO `movieviewcount` VALUES (1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2),(1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2),(1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2),(1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2),(1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2),(1,1,3),(1,2,2),(1,3,1),(3,1,1),(5,2,2);
/*!40000 ALTER TABLE `movieviewcount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `is_discount_applied` bit(1) DEFAULT b'0',
  `is_paid` bit(1) DEFAULT b'0',
  `payment_date` datetime DEFAULT NULL,
  `subscription_type` enum('SD','HD','UHD') COLLATE utf8mb4_general_ci DEFAULT 'SD',
  `payment_amount` double NOT NULL,
  `next_billing_date` datetime DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `payments_ibfk_1` (`account_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,18,_binary '\0',_binary '','2025-01-03 20:53:03','SD',7.99,'2025-02-03 20:53:03'),(2,19,_binary '',_binary '','2025-01-05 11:42:52','SD',7.99,'2025-02-05 11:42:52'),(3,24,_binary '',_binary '','2025-01-04 11:13:18','SD',5.99,'2025-02-04 11:13:18');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `paymentstatus`
--

DROP TABLE IF EXISTS `paymentstatus`;
/*!50001 DROP VIEW IF EXISTS `paymentstatus`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `paymentstatus` AS SELECT 
 1 AS `payment_id`,
 1 AS `account_id`,
 1 AS `email`,
 1 AS `subscription_type`,
 1 AS `payment_amount`,
 1 AS `is_paid`,
 1 AS `is_discount_applied`,
 1 AS `payment_date`,
 1 AS `next_billing_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `profile_id` int unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int unsigned DEFAULT NULL,
  `profile_image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int NOT NULL DEFAULT '0',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (1,2,'pizdiets.png',16,'krutoy patsan'),(2,2,'abcdefg.png',12,'tupoy loshara'),(3,19,'http://example.com/image.jpg',30,'JimmyJ');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series`
--

DROP TABLE IF EXISTS `series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `series` (
  `series_id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `minimum_age` int NOT NULL DEFAULT '12',
  PRIMARY KEY (`series_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series`
--

LOCK TABLES `series` WRITE;
/*!40000 ALTER TABLE `series` DISABLE KEYS */;
INSERT INTO `series` VALUES (1,'Sex education',18);
/*!40000 ALTER TABLE `series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seriesprofilewatchlist`
--

DROP TABLE IF EXISTS `seriesprofilewatchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seriesprofilewatchlist` (
  `profile_id` int unsigned NOT NULL,
  `series_id` int unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seriesprofilewatchlist`
--

LOCK TABLES `seriesprofilewatchlist` WRITE;
/*!40000 ALTER TABLE `seriesprofilewatchlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `seriesprofilewatchlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seriesviewcount`
--

DROP TABLE IF EXISTS `seriesviewcount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seriesviewcount` (
  `account_id` int unsigned NOT NULL,
  `series_id` int unsigned NOT NULL,
  `episode_id` int unsigned NOT NULL,
  `number` int DEFAULT NULL,
  `last_viewed` datetime DEFAULT NULL,
  KEY `FKeh1b2xgu8esqripye7l4o90rq` (`episode_id`),
  KEY `FKmqydqyoqjwehbedxfhl2htgxk` (`series_id`),
  CONSTRAINT `FKeh1b2xgu8esqripye7l4o90rq` FOREIGN KEY (`episode_id`) REFERENCES `episode` (`episode_id`),
  CONSTRAINT `FKmqydqyoqjwehbedxfhl2htgxk` FOREIGN KEY (`series_id`) REFERENCES `series` (`series_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seriesviewcount`
--

LOCK TABLES `seriesviewcount` WRITE;
/*!40000 ALTER TABLE `seriesviewcount` DISABLE KEYS */;
/*!40000 ALTER TABLE `seriesviewcount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `subscriptioncosts`
--

DROP TABLE IF EXISTS `subscriptioncosts`;
/*!50001 DROP VIEW IF EXISTS `subscriptioncosts`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `subscriptioncosts` AS SELECT 
 1 AS `UserID`,
 1 AS `Email`,
 1 AS `SubscriptionType`,
 1 AS `SubscriptionCost`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `payment_method` varchar(255) COLLATE utf8mb4_general_ci DEFAULT 'Credit Card',
  `active` bit(1) DEFAULT b'0',
  `blocked` bit(1) DEFAULT b'0',
  `subscription` enum('SD','HD','UHD') COLLATE utf8mb4_general_ci DEFAULT 'SD',
  `trial_start_date` datetime DEFAULT NULL,
  `trial_end_date` datetime DEFAULT NULL,
  `language_id` int unsigned DEFAULT NULL,
  `role` enum('JUNIOR','MEDIOR','SENIOR') COLLATE utf8mb4_general_ci DEFAULT 'JUNIOR',
  `failed_attempts` int DEFAULT '0',
  `lock_time` datetime DEFAULT NULL,
  `discount` bit(1) DEFAULT b'0',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `email` (`email`),
  KEY `account_id` (`account_id`),
  KEY `FKj9k2portqypgs993xn20pywtr` (`language_id`),
  CONSTRAINT `FKj9k2portqypgs993xn20pywtr` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'fjodor.smorodins@gmail.com','$2a$10$hszeHDUNOv4lnd24ZS9sOeOkOJUYo5zSi2H2makEPti1uznr4s5P2','abc',_binary '\0',_binary '\0','SD','2024-12-07 14:32:59','2024-12-14 14:32:59',4,'SENIOR',0,NULL,_binary '\0'),(2,'fjodorsm@gmail.com','$2a$10$2QlecdJ25ELwT/avANQAUelbxtS9tysiRO5LSE0omLATaWhdAPfZC','Credit Card',_binary '\0',_binary '\0','SD','2024-12-07 14:33:33','2024-12-14 14:33:33',1,'JUNIOR',0,NULL,_binary '\0'),(3,'smorodins@gmail.com','$2a$10$KhhGnFeK2q32DYG7/fMhNe/GEzf1dDJVkQqq5isK1vwuIO9h0zor.','CrC',_binary '\0',_binary '\0','SD','2024-12-16 18:54:30','2024-12-23 18:54:30',3,'JUNIOR',0,NULL,_binary '\0'),(5,'fjodors@hello.com','$2a$10$hKsRL99MRpKUr.vrJPqsfuG3qhGkDjXQEYDxHytWFBYgW7HZJ/54W','golden bars',_binary '\0',_binary '\0','SD','2024-12-20 16:19:25','2024-12-27 16:19:25',2,'JUNIOR',0,NULL,_binary '\0'),(6,'artjoms.grishajevs@hello.com','$2a$10$NboUZOHniHtnfHhFFECcF.dA64uJsp.8/OnD0B0NEuMvTyvIfN7we','children',_binary '\0',_binary '\0','SD','2024-12-20 16:24:58','2024-12-27 16:24:58',1,'JUNIOR',0,NULL,_binary '\0'),(7,'somebody@hello.com','$2a$10$4H41Ugw1ho9ga4DfTV1rwegl.uxbZcTbEu3/SBeklNzsHnXoYliTe','money',_binary '\0',_binary '\0','SD','2024-12-20 17:08:59','2024-12-27 17:08:59',1,'JUNIOR',0,NULL,_binary '\0'),(9,'somepersonwhatever@hello.com','$2a$10$DhZSCWySz9rypM/jM8mR6.yzaCPIpugVlITMSWx9whkmEp1ciPK42','something',_binary '\0',_binary '\0','SD','2024-12-20 17:24:39','2024-12-27 17:24:39',2,'JUNIOR',0,NULL,_binary '\0'),(10,'iamsteve@hello.com','$2a$10$92qxixAWTf94z9sK.Lf2iebtyLdBV9ckOx.xfzGLv4enlX5gdsis6','mastercard',_binary '\0',_binary '\0','SD','2024-12-20 17:58:22','2024-12-27 17:58:22',3,'JUNIOR',0,NULL,_binary '\0'),(15,'test1@.com','$2a$10$aP97IvFmxH8yLGuL1012Xe4sfLd6s1SdokAAKOhG3.tvWCTkmfD2.','some method',_binary '\0',_binary '\0','SD','2024-12-20 22:46:29','2024-12-27 22:46:29',3,'JUNIOR',1,NULL,_binary '\0'),(17,'medior.fjodor@g.com','$2a$10$gQuhxuEegp0Ypg.IrGiL8.bmQwV4sdMzXirKh7N0N4KbOXAq4xwFi','some money transfer method',_binary '\0',_binary '\0','SD','2024-12-23 17:55:18','2024-12-30 17:55:18',3,'JUNIOR',0,NULL,_binary '\0'),(18,'billyJ@outlook.com','$2a$10$3','IDEAL',_binary '',_binary '\0','SD','2024-12-23 18:00:00','2024-12-30 18:00:00',3,'JUNIOR',0,NULL,_binary ''),(19,'Jimmy@outlook.com','$2a$10$sGS0BA23.2I/xMtFymB7/u9dSby.ns4NLRi/GmBav5buxQepuN7bK','IDEAL',_binary '',_binary '\0','SD','2025-01-03 20:56:05','2025-01-10 20:56:05',1,'JUNIOR',0,NULL,_binary ''),(20,'Jimmy1@outlook.com','$2a$10$U55MhBVEeqssTezCU/QabO2ptqXaXpL4dzBgELih7drbyUiHLXq2a','IDEAL',_binary '',_binary '\0','SD','2025-01-03 21:40:57','2025-01-10 21:40:57',1,'JUNIOR',0,NULL,_binary '\0'),(21,'Jimmy2@outlook.com','$2a$10$FrjmHGLogSLSAhCWUojtwumz1HDTjSVt8/SWq1xR7/dtVeCvBHGLC','IDEAL',_binary '\0',_binary '\0','SD','2025-01-03 21:45:20','2025-01-10 21:45:20',1,'JUNIOR',0,NULL,_binary '\0'),(22,'anonymous@outlook.com','$2a$10$QBTLo6XTv5UmR9Ykt6bPa..CfAlpSJyG3dYCgGWKuWGQ1uY8NlS4O','IDEAL',_binary '\0',_binary '\0','SD','2025-01-03 21:47:34','2025-01-10 21:47:34',1,'JUNIOR',0,NULL,_binary '\0'),(23,'anonymous1@outlook.com','$2a$10$oGQ4WY/z99sneQzFafD/5.mWuPXE4cGZqMT/gU3N7aheX68QhQ4rK','IDEAL',_binary '',_binary '\0','SD','2025-01-03 21:50:59','2025-01-10 21:50:59',1,'JUNIOR',0,NULL,_binary '\0'),(24,'anonymous2@outlook.com','$2a$10$edLkze9hRX/WMU90NbQhUu0GiaaZ51tpTEJhsDf2MQ/0mIGRfD5YW','IDEAL',_binary '',_binary '\0','SD','2025-01-03 21:53:04','2025-01-10 21:53:04',1,'JUNIOR',0,NULL,_binary ''),(25,'anonymous3@outlook.com','$2a$10$TDzCNyTKeHKcCI9W/mJEdOdJIGD08oGcQzhMuUTD51Dqw4IygQQUy','IDEAL',_binary '\0',_binary '\0','SD','2025-01-03 21:57:40','2025-01-10 21:57:40',1,'JUNIOR',0,NULL,_binary '\0'),(26,'anonymous4@outlook.com','$2a$10$J414xqetMAM8uJzXT3kCpuDXY4ZYFOCxEqPKZ77Vz12T0BYgmVrvK','IDEAL',_binary '',_binary '\0','SD','2025-01-03 22:00:12','2025-01-10 22:00:12',1,'JUNIOR',0,NULL,_binary ''),(27,'anonymous5@outlook.com','$2a$10$NMuCKRCPxJKiZhLJo2f.POIZT4184tH5zd577kvj4arwEabe0a3tm','IDEAL',_binary '\0',_binary '\0','SD','2025-01-05 11:37:26','2025-01-12 11:37:26',1,'JUNIOR',0,NULL,_binary '\0'),(29,'anonymous6@outlook.com','$2a$10$/FbxxTne5IU1amo2xGzhhubW7k75MLrBVkQ5Ydp3JJFWMOcNOGCpa','IDEAL',_binary '',_binary '\0','SD','2025-01-05 11:40:50','2025-01-12 11:40:50',1,'JUNIOR',0,NULL,_binary '\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `user_genre_count`
--

DROP TABLE IF EXISTS `user_genre_count`;
/*!50001 DROP VIEW IF EXISTS `user_genre_count`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_genre_count` AS SELECT 
 1 AS `user_id`,
 1 AS `genre_id`,
 1 AS `genre_name`,
 1 AS `total_views`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `paymentstatus`
--

/*!50001 DROP VIEW IF EXISTS `paymentstatus`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `paymentstatus` AS select `p`.`payment_id` AS `payment_id`,`u`.`account_id` AS `account_id`,`u`.`email` AS `email`,`p`.`subscription_type` AS `subscription_type`,`p`.`payment_amount` AS `payment_amount`,`p`.`is_paid` AS `is_paid`,`p`.`is_discount_applied` AS `is_discount_applied`,`p`.`payment_date` AS `payment_date`,`p`.`next_billing_date` AS `next_billing_date` from (`payments` `p` join `user` `u` on((`p`.`account_id` = `u`.`account_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `subscriptioncosts`
--

/*!50001 DROP VIEW IF EXISTS `subscriptioncosts`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `subscriptioncosts` AS select `u`.`account_id` AS `UserID`,`u`.`email` AS `Email`,`u`.`subscription` AS `SubscriptionType`,(case when ((to_days(curdate()) - to_days(`u`.`trial_start_date`)) <= 7) then 0 else ((case when (`u`.`subscription` = 'SD') then 7.99 when (`u`.`subscription` = 'HD') then 10.99 when (`u`.`subscription` = 'UHD') then 13.99 else 0 end) - (case when (`u`.`discount` = 1) then 2 else 0 end)) end) AS `SubscriptionCost` from `user` `u` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_genre_count`
--

/*!50001 DROP VIEW IF EXISTS `user_genre_count`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_genre_count` AS select `mvc`.`account_id` AS `user_id`,`g`.`genre_id` AS `genre_id`,`g`.`genre_name` AS `genre_name`,sum(`mvc`.`number`) AS `total_views` from (((`movieviewcount` `mvc` join `movie` `m` on((`mvc`.`movie_id` = `m`.`movie_id`))) join `genreformovie` `mg` on((`m`.`movie_id` = `mg`.`movie_id`))) join `genre` `g` on((`mg`.`genre_id` = `g`.`genre_id`))) group by `mvc`.`account_id`,`g`.`genre_id` order by `mvc`.`account_id`,sum(`mvc`.`number`) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-05 17:03:04
