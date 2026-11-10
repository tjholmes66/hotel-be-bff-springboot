CREATE DATABASE  IF NOT EXISTS `hotel_bff_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotel_bff_db`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hotel_bff_db
-- ------------------------------------------------------
-- Server version	8.4.0

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
-- Table structure for table `bed_types`
--

DROP TABLE IF EXISTS `bed_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bed_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bed_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed_types`
--

LOCK TABLES `bed_types` WRITE;
/*!40000 ALTER TABLE `bed_types` DISABLE KEYS */;
INSERT INTO `bed_types` VALUES (1,'Twin/Single'),(2,'Twin XL'),(3,'Double / Full'),(4,'Double Full'),(5,'Single Queen'),(6,'Double Queen'),(7,'Single King'),(8,'Double King'),(9,'California King');
/*!40000 ALTER TABLE `bed_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','Existing database baseline','BASELINE','Existing database baseline',NULL,'hotel_bff_user','2026-10-15 17:52:43',0,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `guest_email` varchar(45) NOT NULL,
  `guest_phone` varchar(45) NOT NULL,
  `guest_adut` tinyint NOT NULL DEFAULT '1',
  `created_by` bigint NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint NOT NULL DEFAULT '1',
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `stripe_id` varchar(145) DEFAULT NULL,
  `default_payment_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1334 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (1,'Tom','Holmes','tom@tomholmes.net','781-123-4567',1,1,'2026-10-20 12:34:56',1,'2026-10-20 12:34:56',NULL,NULL),(2,'Kevin','Folan','duncan20s@msn.com','617--111-2222',1,1,'2026-10-20 12:34:56',1,'2026-10-20 12:34:56',NULL,NULL);
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rates`
--

DROP TABLE IF EXISTS `rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rates` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NOT NULL,
  `season_id` bigint NOT NULL,
  `rate` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_room_season_idx` (`room_id`,`season_id`),
  KEY `fk_room_idx` (`room_id`),
  KEY `fk_season_idx` (`season_id`),
  CONSTRAINT `fk_rate_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `fk_rate_seasons` FOREIGN KEY (`season_id`) REFERENCES `seasons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rates`
--

LOCK TABLES `rates` WRITE;
/*!40000 ALTER TABLE `rates` DISABLE KEYS */;
INSERT INTO `rates` VALUES (1,1,1,100.00),(2,1,2,120.00),(3,1,3,200.00),(4,1,4,150.00),(5,1,5,110.00),(6,1,6,130.00),(7,1,7,210.00),(8,1,8,160.00),(9,2,1,100.00),(10,2,2,120.00),(11,2,3,200.00),(12,2,4,150.00),(13,2,5,110.00),(14,2,6,130.00),(15,2,7,210.00),(16,2,8,160.00);
/*!40000 ALTER TABLE `rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation_status`
--

DROP TABLE IF EXISTS `reservation_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status_code` varchar(12) NOT NULL,
  `status_name` varchar(45) NOT NULL,
  `status_description` varchar(145) NOT NULL,
  `created_by` bigint NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint NOT NULL DEFAULT '1',
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation_status`
--

LOCK TABLES `reservation_status` WRITE;
/*!40000 ALTER TABLE `reservation_status` DISABLE KEYS */;
INSERT INTO `reservation_status` VALUES (1,'ACTIVE','Active','Active',1,'2026-10-26 18:13:32',1,'2026-10-26 18:13:32'),(2,'CANCELLED','Cancelled','Cancelled',1,'2026-10-26 18:13:32',1,'2026-10-26 18:13:32'),(3,'ON-HOLD','On-Hold','On-Hold',1,'2026-10-26 18:13:32',1,'2026-10-26 18:13:32'),(4,'PENDING','Pending','Pending',1,'2026-10-26 18:13:32',1,'2026-10-26 18:13:32'),(5,'PAID','Paid','Paid In Full',1,'2026-10-26 18:13:32',1,'2026-10-26 18:13:32');
/*!40000 ALTER TABLE `reservation_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `primary_guest_id` bigint NOT NULL,
  `rate_id` bigint NOT NULL,
  `reservation_status_id` bigint NOT NULL,
  `checkin_date` datetime NOT NULL,
  `checkout_date` datetime NOT NULL,
  `number_adults` int NOT NULL DEFAULT '1',
  `number_children` int NOT NULL DEFAULT '0',
  `reservation_number` varchar(15) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `actual_checkin_by` bigint DEFAULT NULL,
  `actual_checkin` datetime DEFAULT NULL,
  `actual_checkout_by` bigint DEFAULT NULL,
  `actual_checkout` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_reservation_number` (`reservation_number`) /*!80000 INVISIBLE */,
  UNIQUE KEY `uq_guest_room_dates` (`primary_guest_id`,`rate_id`,`checkin_date`,`checkout_date`),
  KEY `fk_guest_idx` (`primary_guest_id`),
  KEY `fk_reservation_status_idx` (`reservation_status_id`),
  KEY `fk_reservation_rate_idx` (`rate_id`),
  CONSTRAINT `fk_guest` FOREIGN KEY (`primary_guest_id`) REFERENCES `guests` (`id`),
  CONSTRAINT `fk_reservation_rate` FOREIGN KEY (`rate_id`) REFERENCES `rates` (`id`),
  CONSTRAINT `fk_reservation_status` FOREIGN KEY (`reservation_status_id`) REFERENCES `reservation_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=619 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,1,1,1,'2026-11-13 00:00:00','2026-11-15 00:00:00',2,0,'ABCDE-12345','2026-10-01 00:00:00','2026-10-01 00:00:00',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_status`
--

DROP TABLE IF EXISTS `room_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_status_code` varchar(45) NOT NULL,
  `room_status_name` varchar(45) NOT NULL,
  `room_status_description` varchar(45) NOT NULL,
  `created_by` bigint NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint NOT NULL DEFAULT '1',
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_status`
--

LOCK TABLES `room_status` WRITE;
/*!40000 ALTER TABLE `room_status` DISABLE KEYS */;
INSERT INTO `room_status` VALUES (1,'AVAILABLE','Available','Available',1,'2026-10-15 17:02:48',1,'2026-10-15 17:02:48'),(2,'TURNOVER','Turn Over','Room being prepped for new guests',1,'2026-10-15 17:02:48',1,'2026-10-15 17:02:48'),(3,'CLOSED','Closed','Room is Closed',1,'2026-10-15 17:02:48',1,'2026-10-15 17:02:48'),(4,'MAINTENANCE','Maintenance','Room is undergoing Maintenance',1,'2026-10-15 17:02:48',1,'2026-10-15 17:02:48');
/*!40000 ALTER TABLE `room_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `floor` int NOT NULL,
  `room_number` int NOT NULL,
  `room_name` varchar(45) DEFAULT NULL,
  `room_description` text,
  `room_status` bigint NOT NULL,
  `occupants` int NOT NULL,
  `bed_type` bigint NOT NULL,
  `created_by` bigint NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` bigint NOT NULL DEFAULT '1',
  `udpated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_bed_types_idx` (`bed_type`),
  KEY `fk_room_status_idx` (`room_status`),
  CONSTRAINT `fk_bed_types` FOREIGN KEY (`bed_type`) REFERENCES `bed_types` (`id`),
  CONSTRAINT `fk_room_status` FOREIGN KEY (`room_status`) REFERENCES `room_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,1,101,NULL,NULL,1,2,1,1,'2026-10-21 11:39:00',1,'2026-10-21 11:39:00'),(2,1,102,NULL,NULL,1,2,2,1,'2026-10-21 11:39:22',1,'2026-10-21 11:39:22'),(3,1,103,NULL,NULL,1,2,3,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(4,2,201,NULL,NULL,1,2,4,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(5,2,202,NULL,NULL,1,2,5,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(6,2,203,NULL,NULL,1,2,6,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(7,3,301,NULL,NULL,1,2,7,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(8,3,302,NULL,NULL,1,2,8,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32'),(9,3,303,NULL,NULL,1,2,9,1,'2026-10-21 11:41:32',1,'2026-10-21 11:41:32');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seasons`
--

DROP TABLE IF EXISTS `seasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seasons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `season_code` varchar(15) NOT NULL,
  `season_name` varchar(45) NOT NULL,
  `season_description` varchar(145) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seasons`
--

LOCK TABLES `seasons` WRITE;
/*!40000 ALTER TABLE `seasons` DISABLE KEYS */;
INSERT INTO `seasons` VALUES (1,'2026_WINTER','2026 Winter','2026 Winter Season','2026-01-01 00:00:00','2026-03-31 00:00:00'),(2,'2026_SPRING','2026 Spring','2026 Spring Season','2026-04-01 00:00:00','2026-06-30 00:00:00'),(3,'2026_SUMMER','2026 Summer','2026 Summer Season','2026-07-01 00:00:00','2026-09-30 00:00:00'),(4,'2026_FALL','2026 Fall','2026 Autumn Season','2026-10-01 00:00:00','2026-12-31 00:00:00'),(5,'2027_WINTER','2027 Winter','2027 Winter Season','2027-01-01 00:00:00','2027-03-31 00:00:00'),(6,'2027_SPRING','2027 Spring','2027 Spring Season','2027-04-01 00:00:00','2027-06-30 00:00:00'),(7,'2027_SUMMER','2027 Summer','2027 Summer Season','2027-07-01 00:00:00','2027-09-30 00:00:00'),(8,'2027_FALL','2027 Fall','2027 Autumn Season','2027-10-01 00:00:00','2027-12-31 00:00:00');
/*!40000 ALTER TABLE `seasons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `cell_phone` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `created_by` bigint NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL,
  `updated_by` bigint NOT NULL DEFAULT '1',
  `updated_date` datetime NOT NULL,
  `keycloak_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_unique` (`keycloak_id`)
) ENGINE=InnoDB AUTO_INCREMENT=485 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'tom@tomholmes.net','tom@tomholmes.net','781-111-1234','Thomas','Holmes',1,'2026-10-15 12:34:56',1,'2026-11-03 16:49:20','3c42807f-f1ac-4baa-9e02-444523b2cfeb'),(2,1,'duncan20s@msn.com','duncan20s@msn.com','781-222-1234','Kevin','Folan',1,'2026-10-15 12:34:56',2,'2026-10-30 13:42:17','fec16eb4-e621-4dc2-8b42-653cabce4950');
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

-- Dump completed on 2026-11-09 21:41:32
