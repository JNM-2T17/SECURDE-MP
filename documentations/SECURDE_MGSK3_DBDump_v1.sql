CREATE DATABASE  IF NOT EXISTS `db_talaria` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_talaria`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: db_talaria
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_activity`
--

LOCK TABLES `tl_activity` WRITE;
/*!40000 ALTER TABLE `tl_activity` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_item`
--

LOCK TABLES `tl_item` WRITE;
/*!40000 ALTER TABLE `tl_item` DISABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `userfk_1` (`role`),
  CONSTRAINT `userfk_1` FOREIGN KEY (`role`) REFERENCES `tl_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tl_user`
--

LOCK TABLES `tl_user` WRITE;
/*!40000 ALTER TABLE `tl_user` DISABLE KEYS */;
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

-- Dump completed on 2016-07-08  1:40:30
