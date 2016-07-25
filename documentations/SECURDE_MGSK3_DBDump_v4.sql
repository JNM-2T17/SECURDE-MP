CREATE DATABASE  IF NOT EXISTS `db_talaria` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_talaria`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: db_talaria
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tl_activity`
--

DROP TABLE IF EXISTS `tl_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity` text NOT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_activity`
--

LOCK TABLES `tl_activity` WRITE;
/*!40000 ALTER TABLE `tl_activity` DISABLE KEYS */;
INSERT INTO `tl_activity` VALUES (1,'Anonymous user logged in.','2016-07-21 04:59:40',1),(2,'Anonymous user added product Pegasus Boots.','2016-07-21 05:00:26',1),(3,'User with id#2 - LannisterPM logged in.','2016-07-21 05:06:27',1),(4,'User with id#2 - LannisterPM logged in.','2016-07-21 05:07:27',1),(5,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:10:59',1),(6,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:12:37',1),(7,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:12:46',1),(8,'User with id#2 - LannisterPM deleted item 1.','2016-07-21 05:12:46',1),(9,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:13:13',1),(10,'User with id#2 - LannisterPM deleted item 1.','2016-07-21 05:13:13',1),(11,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:15:17',1),(12,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:15:19',1),(13,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:15:30',1),(14,'User with id#2 - LannisterPM added product Pegasus Boots.','2016-07-21 05:15:30',1),(15,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:15:30',1),(16,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:15:32',1),(17,'User with id#2 - LannisterPM deleted item 1.','2016-07-21 05:15:33',1),(18,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:16:11',1),(19,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:16:14',1),(20,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:16:16',1),(21,'User with id#2 - LannisterPM deleted item 1.','2016-07-21 05:16:16',1),(22,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:17:23',1),(23,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:17:24',1),(24,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:18:33',1),(25,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:18:36',1),(26,'User with id#2 - LannisterPM deleted item 1.','2016-07-21 05:18:37',1),(27,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:20:14',1),(28,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:28:43',1),(29,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:29:26',1),(30,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:29:56',1),(31,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:29:56',1),(32,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:35:48',1),(33,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:36:16',1),(34,'User with id#2 - LannisterPM added product Running Shoes.','2016-07-21 05:36:17',1),(35,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:36:17',1),(36,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:37:02',1),(37,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:37:10',1),(38,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:37:13',1),(39,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:37:19',1),(40,'User with id#2 - LannisterPM logged in.','2016-07-21 05:39:27',1),(41,'User with id#2 - LannisterPM added product Hermes\' Sandals.','2016-07-21 05:43:09',1),(42,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:48:06',1),(43,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:48:11',1),(44,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:48:17',1),(45,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:48:30',1),(46,'User with id#2 - LannisterPM edited item 5.','2016-07-21 05:48:30',1),(47,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:48:30',1),(48,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:52:44',1),(49,'User with id#2 - LannisterPM edited item 5.','2016-07-21 05:52:44',1),(50,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:52:44',1),(51,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:56:03',1),(52,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:56:14',1),(53,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:58:23',1),(54,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 05:59:14',1),(55,'User with id#2 - LannisterPM logged in.','2016-07-21 06:02:26',1),(56,'User with id#2 - LannisterPM refreshed their session.','2016-07-21 06:02:50',1),(57,'User with id#2 - LannisterPM logged in.','2016-07-21 06:06:33',1),(58,'User with id#2 - LannisterPM logged out.','2016-07-21 06:06:37',1),(59,'User with id#2 - LannisterPM logged in.','2016-07-21 07:48:08',1),(60,'User with id#2 - LannisterPM edited item 3.','2016-07-21 07:48:21',1),(61,'User with id#2 - LannisterPM logged out.','2016-07-21 07:48:31',1),(62,'User with id#2 - LannisterPM logged in.','2016-07-21 08:02:18',1),(63,'User with id#2 - LannisterPM logged out.','2016-07-21 08:02:47',1),(64,'User with id#2 - LannisterPM logged in.','2016-07-21 08:31:23',1),(65,'User with id#2 - LannisterPM logged out.','2016-07-21 08:31:25',1),(66,'User with id#3 - LannisterAM logged in.','2016-07-21 08:31:34',1),(67,'User with id#3 - LannisterAM logged out.','2016-07-21 08:31:37',1),(68,'User with id#1 - LannisterCustomer logged in.','2016-07-21 08:32:01',1),(69,'User with id#1 - LannisterCustomer logged out.','2016-07-21 08:32:09',1),(70,'User with id#4 - LannisterAdmin logged in.','2016-07-21 10:46:54',1),(71,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:48:26',1),(72,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:50:43',1),(73,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:52:02',1),(74,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:52:11',1),(75,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:53:57',1),(76,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 10:54:28',1),(77,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:10:27',1),(78,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:11:21',1),(79,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:11:57',1),(80,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:14:05',1),(81,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:15:22',1),(82,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:15:22',1),(83,'User with id#4 - LannisterAdmin logged in.','2016-07-21 11:17:17',1),(84,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:19:34',1),(85,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:21:31',1),(86,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:22:23',1),(87,'User with id#4 - LannisterAdmin created user AldsTM.','2016-07-21 11:22:31',1),(88,'User with id#4 - LannisterAdmin created user AldsTM.','2016-07-21 11:23:05',1),(89,'User with id#4 - LannisterAdmin logged out.','2016-07-21 11:24:35',1),(90,'User with id#5 - AldsTM logged in.','2016-07-21 11:25:36',1),(91,'User with id#5 - AldsTM logged out.','2016-07-21 11:25:41',1),(92,'User with id#4 - LannisterAdmin logged in.','2016-07-21 11:25:52',1),(93,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:27:55',1),(94,'User with id#4 - LannisterAdmin created user clareeseAM.','2016-07-21 11:28:27',1),(95,'User with id#4 - LannisterAdmin created user AldsAM.','2016-07-21 11:29:34',1),(96,'User with id#4 - LannisterAdmin created user clareesePM.','2016-07-21 11:30:22',1),(97,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:37:10',1),(98,'User with id#4 - LannisterAdmin changed their password.','2016-07-21 11:38:33',1),(99,'User with id#4 - LannisterAdmin logged out.','2016-07-21 11:38:37',1),(100,'User with id#4 - LannisterAdmin logged in.','2016-07-21 11:38:44',1),(101,'User with id#4 - LannisterAdmin changed their password.','2016-07-21 11:38:58',1),(102,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:39:56',1),(103,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:39:56',1),(104,'User with id#4 - LannisterAdmin changed their password.','2016-07-21 11:40:09',1),(105,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:48:34',1),(106,'User with id#4 - LannisterAdmin created user JonahPM.','2016-07-21 11:48:59',1),(107,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 11:53:33',1),(108,'User with id#4 - LannisterAdmin logged out.','2016-07-21 11:53:33',1),(109,'User with id#9 - JonahPM logged in.','2016-07-21 11:57:23',1),(110,'User with id#9 - JonahPM changed their password.','2016-07-21 11:57:33',1),(111,'User with id#9 - JonahPM refreshed their session.','2016-07-21 12:00:06',1),(112,'User with id#9 - JonahPM logged out.','2016-07-21 12:00:06',1),(113,'User with id#4 - LannisterAdmin logged in.','2016-07-21 12:00:59',1),(114,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:08:45',1),(115,'User with id#4 - LannisterAdmin logged out.','2016-07-21 12:08:45',1),(116,'User with id#4 - LannisterAdmin logged in.','2016-07-21 12:08:53',1),(117,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:13:48',1),(118,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:14:48',1),(119,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:16:03',1),(120,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:17:15',1),(121,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-21 12:25:14',1),(122,'User with id#4 - LannisterAdmin logged out.','2016-07-21 12:30:35',1),(123,'User with id#4 - LannisterAdmin logged in.','2016-07-21 12:30:54',1),(124,'User with id#4 - LannisterAdmin created user JonahAM.','2016-07-21 12:31:45',1),(125,'Anonymous user tried to access editAccount and failed.','2016-07-22 01:12:18',1),(126,'User with id#8 - clareesePM logged in.','2016-07-22 01:17:41',1),(127,'User with id#8 - clareesePM refreshed their session.','2016-07-22 01:23:04',1),(128,'User with id#8 - clareesePM \'s session expired.','2016-07-22 01:28:13',1),(129,'User with id#8 - clareesePM logged out.','2016-07-22 01:28:13',1),(130,'User with id#4 - LannisterAdmin logged in.','2016-07-22 01:29:29',1),(131,'User with id#4 - LannisterAdmin refreshed their session.','2016-07-22 01:46:58',1),(132,'User with id#4 - LannisterAdmin logged out.','2016-07-22 01:46:58',1),(133,'Anonymous user ran into the error Column \'dateAdded\' in field list is ambiguous.','2016-07-22 01:48:12',1),(134,'Anonymous user ran into the error Column \'dateAdded\' in field list is ambiguous.','2016-07-22 01:48:37',1),(135,'User with id#2 - LannisterPM logged in.','2016-07-22 01:48:50',1),(136,'User with id#2 - LannisterPM ran into the error null.','2016-07-22 01:49:12',1),(137,'User with id#2 - LannisterPM ran into the error null.','2016-07-22 01:49:21',1),(138,'User with id#2 - LannisterPM refreshed their session.','2016-07-22 01:51:33',1),(139,'User with id#2 - LannisterPM changed their password.','2016-07-22 01:51:34',1),(140,'User with id#2 - LannisterPM logged in.','2016-07-22 03:12:58',1),(141,'User with id#2 - LannisterPM edited item 5.','2016-07-22 03:13:22',1),(142,'User with id#2 - LannisterPM \'s session expired.','2016-07-22 03:36:10',1),(143,'User with id#2 - LannisterPM logged out.','2016-07-22 03:36:10',1),(144,'User with id#2 - LannisterPM logged in.','2016-07-22 14:04:04',1),(145,'User with id#2 - LannisterPM edited item 5.','2016-07-22 14:05:49',1),(146,'User with id#7 - AldsAM logged in.','2016-07-25 03:14:35',1),(147,'User with id#7 - AldsAM logged out.','2016-07-25 03:15:38',1),(148,'User with id#4 - LannisterAdmin logged in.','2016-07-25 03:17:03',1),(149,'User with id#4 - LannisterAdmin created user ChengPM.','2016-07-25 03:17:51',1),(150,'User with id#4 - LannisterAdmin logged out.','2016-07-25 03:18:15',1),(151,'User with id#11 - ChengPM logged in.','2016-07-25 03:18:21',1),(152,'User with id#11 - ChengPM changed their password.','2016-07-25 03:18:57',1),(153,'User with id#11 - ChengPM edited item 5.','2016-07-25 03:20:04',1),(154,'User with id#11 - ChengPM \'s session expired.','2016-07-25 03:29:58',1),(155,'User with id#11 - ChengPM logged out.','2016-07-25 03:29:58',1),(156,'Anonymous user tried to access editProduct and failed.','2016-07-25 03:29:58',1),(157,'User with id#11 - ChengPM logged in.','2016-07-25 03:30:13',1),(158,'User with id#11 - ChengPM edited item 5.','2016-07-25 03:30:29',1),(159,'User with id#11 - ChengPM \'s session expired.','2016-07-25 03:55:10',1),(160,'User with id#11 - ChengPM logged out.','2016-07-25 03:55:11',1),(161,'Anonymous user tried to access createAccount and failed.','2016-07-25 03:55:11',1),(162,'User with id#2 - LannisterPM logged in.','2016-07-25 03:55:26',1),(163,'User with id#2 - LannisterPM refreshed their session.','2016-07-25 03:58:25',1),(164,'User with id#2 - LannisterPM refreshed their session.','2016-07-25 04:00:28',1),(165,'User with id#2 - LannisterPM tried to access createAccount and failed.','2016-07-25 04:00:28',1),(166,'User with id#2 - LannisterPM logged out.','2016-07-25 04:00:59',1),(167,'User with id#4 - LannisterAdmin logged in.','2016-07-25 04:01:07',1),(168,'User with id#4 - LannisterAdmin tried to access addProduct and failed.','2016-07-25 04:01:16',1);
/*!40000 ALTER TABLE `tl_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_item`
--

DROP TABLE IF EXISTS `tl_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemtype` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` text,
  `price` double NOT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateEdited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `itemfk_1` (`itemtype`),
  CONSTRAINT `itemfk_1` FOREIGN KEY (`itemtype`) REFERENCES `tl_item_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_item`
--

LOCK TABLES `tl_item` WRITE;
/*!40000 ALTER TABLE `tl_item` DISABLE KEYS */;
INSERT INTO `tl_item` VALUES (1,1,'Pegasus Boots','Link\'s Boots',1000,'2016-07-21 05:15:30','2016-07-21 05:15:30',1),(3,2,'Running Shoes','For running in the Pokeworld. Hold B to use',500,'2016-07-21 05:36:16','2016-07-21 05:36:16',1),(5,3,'Hermes\' Sandals','Gives you speed of the messenger god!',8999,'2016-07-21 05:43:09','2016-07-21 05:43:09',1);
/*!40000 ALTER TABLE `tl_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_item_type`
--

DROP TABLE IF EXISTS `tl_item_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_item_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_item_type`
--

LOCK TABLES `tl_item_type` WRITE;
/*!40000 ALTER TABLE `tl_item_type` DISABLE KEYS */;
INSERT INTO `tl_item_type` VALUES (1,'Boots','2016-07-07 17:37:13',1),(2,'Shoes','2016-07-07 17:37:13',1),(3,'Sandals','2016-07-07 17:37:13',1),(4,'Slippers','2016-07-07 17:37:13',1);
/*!40000 ALTER TABLE `tl_item_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_purchase`
--

DROP TABLE IF EXISTS `tl_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `itemId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `purchasefk_1` (`userId`),
  KEY `purchasefk_2` (`itemId`),
  CONSTRAINT `purchasefk_1` FOREIGN KEY (`userId`) REFERENCES `tl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchasefk_2` FOREIGN KEY (`itemId`) REFERENCES `tl_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_purchase`
--

LOCK TABLES `tl_purchase` WRITE;
/*!40000 ALTER TABLE `tl_purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `tl_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_review`
--

DROP TABLE IF EXISTS `tl_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_review` (
  `userID` int(11) NOT NULL,
  `itemID` int(11) NOT NULL,
  `review` text NOT NULL,
  `rating` int(11) NOT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`,`itemID`),
  KEY `reviewfk_2` (`itemID`),
  CONSTRAINT `reviewfk_1` FOREIGN KEY (`userID`) REFERENCES `tl_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reviewfk_2` FOREIGN KEY (`itemID`) REFERENCES `tl_item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_review`
--

LOCK TABLES `tl_review` WRITE;
/*!40000 ALTER TABLE `tl_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `tl_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_role`
--

DROP TABLE IF EXISTS `tl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(25) NOT NULL,
  `searchProduct` tinyint(1) NOT NULL DEFAULT '0',
  `purchaseProduct` tinyint(1) NOT NULL DEFAULT '0',
  `reviewProduct` tinyint(1) NOT NULL DEFAULT '0',
  `addProduct` tinyint(1) NOT NULL DEFAULT '0',
  `editProduct` tinyint(1) NOT NULL DEFAULT '0',
  `deleteProduct` tinyint(1) NOT NULL DEFAULT '0',
  `viewRecords` tinyint(1) NOT NULL DEFAULT '0',
  `createAccount` tinyint(1) NOT NULL DEFAULT '0',
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleName` (`roleName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_role`
--

LOCK TABLES `tl_role` WRITE;
/*!40000 ALTER TABLE `tl_role` DISABLE KEYS */;
INSERT INTO `tl_role` VALUES (1,'Customer',1,1,1,0,0,0,0,0,'2016-07-07 17:37:59',1),(2,'Product Manager',0,0,0,1,1,1,0,0,'2016-07-07 17:37:59',1),(3,'Accounting Manager',0,0,0,0,0,0,1,0,'2016-07-07 17:37:59',1),(4,'Admin',0,0,0,0,0,0,0,1,'2016-07-07 17:37:59',1);
/*!40000 ALTER TABLE `tl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tl_user`
--

DROP TABLE IF EXISTS `tl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `fName` varchar(45) NOT NULL,
  `mi` char(3) DEFAULT NULL,
  `lName` varchar(45) NOT NULL,
  `emailAddress` varchar(45) NOT NULL,
  `billHouseNo` varchar(10) DEFAULT NULL,
  `billStreet` varchar(45) DEFAULT NULL,
  `billSubd` varchar(45) DEFAULT NULL,
  `billCity` varchar(45) DEFAULT NULL,
  `billPostCode` varchar(10) DEFAULT NULL,
  `billCountry` varchar(45) DEFAULT NULL,
  `shipHouseNo` varchar(10) DEFAULT NULL,
  `shipStreet` varchar(45) DEFAULT NULL,
  `shipSubd` varchar(45) DEFAULT NULL,
  `shipCity` varchar(45) DEFAULT NULL,
  `shipPostCode` varchar(10) DEFAULT NULL,
  `shipCountry` varchar(45) DEFAULT NULL,
  `dateAdded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateEdited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `expiresOn` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `userfk_1` (`role`),
  CONSTRAINT `userfk_1` FOREIGN KEY (`role`) REFERENCES `tl_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_user`
--

LOCK TABLES `tl_user` WRITE;
/*!40000 ALTER TABLE `tl_user` DISABLE KEYS */;
INSERT INTO `tl_user` VALUES (1,1,'LannisterCustomer','$2a$10$pSMRnvZixVjqH7EvEsRUcOm.kD5kKqK0KVOMC/07Q4Og5pCwmgx.m','Ryan Austin','','Fernandez','ryanaustinf@yahoo.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 01:13:16','2016-07-21 01:13:16',1,NULL),(2,2,'LannisterPM','$2a$10$0rSDKei1IP3MNg0wuDocm./NR.LP.npxaQGoJwEmyO76fWskNvLNW','Ryan Austin','','Fernandez','ryanaustinf@yahoo.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 01:13:17','2016-07-22 01:51:34',1,'2016-10-25 11:55:26'),(3,3,'LannisterAM','$2a$10$IYZFE8cLrgBqFxhBs8tulufNGlhxGvgmKmTcwLS9QEV5N4TJBpZZ2','Ryan Austin','','Fernandez','ryanaustinf@yahoo.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 01:13:17','2016-07-21 01:13:17',1,NULL),(4,4,'LannisterAdmin','$2a$10$isMBY5dA7CrfgOcRf1cfeOPbBEkFcm7OJmRQMPin4ilz.ONwVSuWO','Ryan Austin','','Fernandez','ryanaustinf@yahoo.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 01:13:18','2016-07-21 11:40:09',1,'2016-10-25 12:01:07'),(5,2,'AldsTM','$2a$10$CXiK.82RSyoht9hrSjDAHOWSqd7BhtGxpruhos2jaj6E.DFB7YDJC','Alden','R.','Hade','alden@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 11:22:31','2016-07-21 11:22:31',1,NULL),(6,3,'clareeseAM','$2a$10$UcT4Hx6IcathRBfKbPH/uubLHv24x5uOSRjyxq6ZD0nyE2U2gfWmG','Clarisse Felicia','M.','Poblete','clarisse_poblete@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 11:28:27','2016-07-21 11:28:27',1,NULL),(7,3,'AldsAM','$2a$10$Lpy1Sfq3KdxmRYfOrahVYutUnZ9H9YZZYam7oEpdqL4jy5yvHT68a','Alden','R.','Hade','alden_hade@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 11:29:34','2016-07-21 11:29:34',1,NULL),(8,2,'clareesePM','$2a$10$E1P4iST.LYSRsBz6YjoznecfVyO/YR3qEaaKmPag0Phk.0KrflFKu','Clarisse Felicia','M.','Poblete','clarisse_poblete@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 11:30:21','2016-07-21 11:30:21',1,'2016-10-22 09:17:41'),(9,2,'JonahPM','$2a$10$xrCPWrTKqXjqgePDz082suLUwRvJRZm1gzPauJf785TqJbn/v3gg.','Jonah','E.','Syfu','jonah_syfu@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 11:48:59','2016-07-21 11:57:33',1,NULL),(10,3,'JonahAM','$2a$10$i.xj/nRzQfTzFt.pwMLllucO8AxSOOAaM/dIB9kQtxrwdnXc5AKJ2','Jonah','E.','Syfu','jonah_syfu@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-21 12:31:45','2016-07-21 12:31:45',1,'2016-07-22 20:31:45'),(11,2,'ChengPM','$2a$12$nWNJq/MlAuHPbBP2GE3X3eIUDO0kl.isoWQ3qTePrvzHx5pYHXZcq','Jan Kristoffer','D.','Cheng','jan_cheng@dlsu.edu.ph',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-25 03:17:51','2016-07-25 03:18:56',1,'2016-10-25 11:30:13');
/*!40000 ALTER TABLE `tl_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-25 12:09:59
