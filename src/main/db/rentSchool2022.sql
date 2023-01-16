-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: rentSchool2022
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

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
-- Table structure for table `Accounts`
--

DROP TABLE IF EXISTS `Accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Accounts` (
  `id_account` int NOT NULL AUTO_INCREMENT,
  `login` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id_account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
INSERT INTO `Accounts` VALUES (1,'jiwaii','test123');
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Articles`
--

DROP TABLE IF EXISTS `Articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Articles` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `id_category` int NOT NULL,
  `articleName` varchar(100) NOT NULL,
  `ref_sn` varchar(100) DEFAULT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `state` enum('available','rental','disabled','lost') NOT NULL,
  PRIMARY KEY (`id_article`),
  KEY `Articles_FK` (`id_category`),
  CONSTRAINT `Articles_FK` FOREIGN KEY (`id_category`) REFERENCES `Categories` (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Articles`
--

LOCK TABLES `Articles` WRITE;
/*!40000 ALTER TABLE `Articles` DISABLE KEYS */;
INSERT INTO `Articles` VALUES (1,1,'hp probook 445 G7','C7RJ',NULL,'available');
/*!40000 ALTER TABLE `Articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Articles_Rentals`
--

DROP TABLE IF EXISTS `Articles_Rentals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Articles_Rentals` (
  `id_article` int NOT NULL,
  `id_rental` int NOT NULL,
  `qty` int NOT NULL,
  `id_Articles_Rentals` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_Articles_Rentals`),
  KEY `Articles_Rentals_FKRental` (`id_rental`),
  KEY `Articles_Rentals_FK` (`id_article`),
  CONSTRAINT `Articles_Rentals_FK_Article` FOREIGN KEY (`id_article`) REFERENCES `Articles` (`id_article`),
  CONSTRAINT `Articles_Rentals_FKRental` FOREIGN KEY (`id_rental`) REFERENCES `Rentals` (`id_rental`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Articles_Rentals`
--

LOCK TABLES `Articles_Rentals` WRITE;
/*!40000 ALTER TABLE `Articles_Rentals` DISABLE KEYS */;
/*!40000 ALTER TABLE `Articles_Rentals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Categories`
--

DROP TABLE IF EXISTS `Categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Categories` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(100) NOT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Categories`
--

LOCK TABLES `Categories` WRITE;
/*!40000 ALTER TABLE `Categories` DISABLE KEYS */;
INSERT INTO `Categories` VALUES (1,'ordinateur portable'),(2,'câble'),(3,'projecteur video');
/*!40000 ALTER TABLE `Categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cities`
--

DROP TABLE IF EXISTS `Cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cities` (
  `id_city` int NOT NULL AUTO_INCREMENT,
  `cityName` varchar(100) NOT NULL,
  `postalCode` int NOT NULL,
  `id_country` int NOT NULL,
  PRIMARY KEY (`id_city`),
  KEY `Cities_FK` (`id_country`),
  CONSTRAINT `Cities_FK` FOREIGN KEY (`id_country`) REFERENCES `Countries` (`id_country`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cities`
--

LOCK TABLES `Cities` WRITE;
/*!40000 ALTER TABLE `Cities` DISABLE KEYS */;
INSERT INTO `Cities` VALUES (1,'namur',5000,1);
/*!40000 ALTER TABLE `Cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Countries`
--

DROP TABLE IF EXISTS `Countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Countries` (
  `id_country` int NOT NULL AUTO_INCREMENT,
  `countryName` varchar(100) NOT NULL,
  `codeAlpha2` varchar(2) NOT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Countries`
--

LOCK TABLES `Countries` WRITE;
/*!40000 ALTER TABLE `Countries` DISABLE KEYS */;
INSERT INTO `Countries` VALUES (1,'belgique','be');
/*!40000 ALTER TABLE `Countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Permissions` (
  `id_permission` int NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(100) NOT NULL,
  `summary` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id_permission`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permissions`
--

LOCK TABLES `Permissions` WRITE;
/*!40000 ALTER TABLE `Permissions` DISABLE KEYS */;
INSERT INTO `Permissions` VALUES (1,'administrateur','contrôle total sur l\'application');
/*!40000 ALTER TABLE `Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reminders`
--

DROP TABLE IF EXISTS `Reminders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Reminders` (
  `id_reminder` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_rental` int NOT NULL,
  `reminderDate` date NOT NULL,
  `message` varchar(250) NOT NULL,
  PRIMARY KEY (`id_reminder`),
  KEY `Reminders_FK` (`id_user`),
  KEY `Reminders_FK_1` (`id_rental`),
  CONSTRAINT `Reminders_FK` FOREIGN KEY (`id_user`) REFERENCES `Users` (`id_user`),
  CONSTRAINT `Reminders_FK_1` FOREIGN KEY (`id_rental`) REFERENCES `Rentals` (`id_rental`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reminders`
--

LOCK TABLES `Reminders` WRITE;
/*!40000 ALTER TABLE `Reminders` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reminders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rentals`
--

DROP TABLE IF EXISTS `Rentals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Rentals` (
  `id_rental` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `dateBegin` date NOT NULL,
  `dateEnd` date NOT NULL,
  `id_userRent` int NOT NULL,
  PRIMARY KEY (`id_rental`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rentals`
--

LOCK TABLES `Rentals` WRITE;
/*!40000 ALTER TABLE `Rentals` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rentals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Roles` (
  `id_role` int NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles`
--

LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles` DISABLE KEYS */;
INSERT INTO `Roles` VALUES (1,'administrateur');
/*!40000 ALTER TABLE `Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles_Permissions`
--

DROP TABLE IF EXISTS `Roles_Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Roles_Permissions` (
  `id_permission` int NOT NULL,
  `id_role` int NOT NULL,
  `id_Roles_Permissions` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_Roles_Permissions`),
  KEY `Roles_Permissions_FK` (`id_permission`),
  KEY `Roles_Permissions_FK_1` (`id_role`),
  CONSTRAINT `Roles_Permissions_FK` FOREIGN KEY (`id_permission`) REFERENCES `Permissions` (`id_permission`),
  CONSTRAINT `Roles_Permissions_FK_1` FOREIGN KEY (`id_role`) REFERENCES `Roles` (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles_Permissions`
--

LOCK TABLES `Roles_Permissions` WRITE;
/*!40000 ALTER TABLE `Roles_Permissions` DISABLE KEYS */;
INSERT INTO `Roles_Permissions` VALUES (1,1,1);
/*!40000 ALTER TABLE `Roles_Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `id_role` int DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `responsibleType` enum('student','teacher','secretariat') NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `id_city` int NOT NULL,
  `id_account` int DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `fk_Accounts_idx` (`id_account`),
  KEY `fk_Roles_idx` (`id_role`),
  KEY `Users_FK` (`id_city`),
  CONSTRAINT `fk_Accounts` FOREIGN KEY (`id_account`) REFERENCES `Accounts` (`id_account`),
  CONSTRAINT `fk_Roles` FOREIGN KEY (`id_role`) REFERENCES `Roles` (`id_role`),
  CONSTRAINT `Users_FK` FOREIGN KEY (`id_city`) REFERENCES `Cities` (`id_city`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (2,1,'rue des admins','jeanyves.laurent@promsocatc.net','secretariat','jean-yves','laurent','IS00001',1,1);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users_Rentals`
--

DROP TABLE IF EXISTS `Users_Rentals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users_Rentals` (
  `id_user` int NOT NULL,
  `id_rental` int NOT NULL,
  `id_Users_Rentals` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id_Users_Rentals`),
  KEY `Users_Rentals_FK` (`id_user`),
  KEY `Users_Rentals_FK_1` (`id_rental`),
  CONSTRAINT `Users_Rentals_FK` FOREIGN KEY (`id_user`) REFERENCES `Users` (`id_user`),
  CONSTRAINT `Users_Rentals_FK_1` FOREIGN KEY (`id_rental`) REFERENCES `Rentals` (`id_rental`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users_Rentals`
--

LOCK TABLES `Users_Rentals` WRITE;
/*!40000 ALTER TABLE `Users_Rentals` DISABLE KEYS */;
/*!40000 ALTER TABLE `Users_Rentals` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-11 12:28:51
