CREATE DATABASE  IF NOT EXISTS `periodicals` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `periodicals`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: periodicals
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credentials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `password_hash` varchar(200) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `u_logins_fk_idx` (`user_id`),
  CONSTRAINT `u_credentials_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (1,'stolser','3fde6bb0541387e4ebdadf7c2ff31123',1),(2,'subscriber','3fde6bb0541387e4ebdadf7c2ff31123',2),(3,'gosling','3fde6bb0541387e4ebdadf7c2ff31123',4),(4,'duke','3fde6bb0541387e4ebdadf7c2ff31123',5);
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `periodical_id` bigint(20) NOT NULL,
  `period` int(11) DEFAULT NULL,
  `total_sum` bigint(20) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_date` timestamp NULL DEFAULT NULL,
  `status` enum('new','paid') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `u_invoice_fk_idx` (`user_id`),
  KEY `p_invoice_fk_idx` (`periodical_id`),
  CONSTRAINT `p_invoice_fk` FOREIGN KEY (`periodical_id`) REFERENCES `periodicals` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `u_invoice_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,2,1,5,125,'2016-12-06 20:58:30','2016-12-08 09:17:19','paid'),(5,1,7,1,55,'2016-12-06 21:33:42','2016-12-07 13:50:10','paid'),(7,1,7,10,550,'2016-12-07 08:30:03','2016-12-08 11:22:47','paid'),(12,2,1,10,250,'2016-12-08 12:56:37','2016-12-08 13:31:46','paid'),(13,2,1,1,25,'2016-12-08 13:38:50','2016-12-08 14:27:35','paid'),(28,2,26,1,0,'2016-12-12 13:34:22','2016-12-12 13:34:27','paid'),(37,1,6,1,30,'2016-12-13 20:25:47','2016-12-13 20:26:22','paid'),(38,1,6,1,30,'2016-12-13 20:37:02','2016-12-13 20:38:01','paid'),(39,1,6,1,30,'2016-12-13 20:37:15','2016-12-13 20:37:55','paid'),(40,1,26,1,0,'2016-12-13 20:37:29','2016-12-13 20:37:41','paid'),(46,2,17,1,10,'2016-12-15 08:32:34','2016-12-15 08:34:46','paid'),(47,2,1,3,75,'2016-12-15 13:51:50','2016-12-15 13:58:38','paid'),(48,2,17,1,10,'2016-12-15 20:51:48','2016-12-15 20:55:40','paid'),(49,2,17,1,10,'2016-12-15 20:55:53','2016-12-15 21:52:12','paid'),(50,2,7,1,55,'2016-12-15 21:51:48','2016-12-15 21:52:08','paid'),(52,2,17,1,10,'2016-12-16 22:06:32','2016-12-16 22:06:38','paid'),(56,1,6,10,300,'2016-12-17 14:45:34','2016-12-17 16:30:29','paid'),(59,1,26,10,0,'2016-12-17 16:28:12','2016-12-17 19:43:33','paid'),(60,2,26,1,45,'2016-12-17 16:55:03',NULL,'new'),(61,1,1,1,25,'2016-12-18 20:11:57','2016-12-18 20:13:33','paid'),(62,1,30,1,0,'2016-12-18 20:12:27','2016-12-18 20:12:51','paid'),(63,1,32,1,39,'2016-12-30 16:09:03','2016-12-30 16:29:51','paid'),(64,1,30,1,15,'2017-01-03 17:58:11','2017-01-04 11:58:24','paid'),(65,1,30,1,22,'2017-01-04 11:58:16','2017-01-04 11:58:32','paid');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodicals`
--

DROP TABLE IF EXISTS `periodicals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodicals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `category` varchar(45) DEFAULT NULL,
  `publisher` varchar(45) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `one_month_cost` bigint(20) DEFAULT NULL,
  `status` enum('active','inactive','discarded') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodicals`
--

LOCK TABLES `periodicals` WRITE;
/*!40000 ALTER TABLE `periodicals` DISABLE KEYS */;
INSERT INTO `periodicals` VALUES (1,'The New York Times','news','Arthur Ochs Sulzberger Jr','The New York Times (sometimes abbreviated to NYT) is an American daily newspaper, founded and continuously published in New York City since September 18, 1851, by The New York Times Company.',22,'active'),(3,'National Geographic','nature','the National Geographic Society','A great magazien about nature.',49,'inactive'),(6,'Mens Health','news','Rodale Press','Men\'s Health (MH), published by Rodale Inc. in Emmaus, Pennsylvania, United States, is the world\'s largest men\'s magazine brand, with 40 editions in 47 countries. It is also the best-selling men\'s magazine on U.S. newsstands.',30,'inactive'),(7,'Forbes','business','Forbes Inc','Forbes is an American business magazine. Published bi-weekly, it features original articles on finance, industry, investing, and marketing topics. Forbes also reports on related subjects such as technology, communications, science, and law. Its headquarters is located in Jersey City, New Jersey.',55,'inactive'),(17,'Газета Футбол','sports','Футбол','',10,'inactive'),(26,'Test Periodical Name - 2','news','Test Publisher 5','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin feugiat molestie congue. Nullam at dapibus dolor. Sed finibus consequat nisl, in condimentum urna eleifend sed. Aliquam sollicitudin sollicitudin libero ornare varius. Pellentesque vel dictum risus. Nulla convallis porttitor nulla, non varius risus pellentesque at. Fusce eget viverra massa, quis auctor.',0,'inactive'),(30,'Test Periodical Name - 3','science_and_engineering','Test Publisher 5','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin feugiat molestie congue. Nullam at dapibus dolor. Sed finibus consequat nisl, in condimentum urna eleifend sed. Aliquam sollicitudin sollicitudin libero ornare varius. Pellentesque vel dictum risus. Nulla convallis porttitor nulla, non varius risus pellentesque at. Fusce eget viverra massa, quis auctor.',22,'inactive'),(31,'Тестове періодичне видання \"Номер п\'ять\"','news','Тестовий видавець','ლორემ იფსუმ დოლორ სით ამეთ, ჰაბეო ირაცუნდია ეთ ველ. ყუი დოლორე ცაუსაე რეგიონე ათ. მეის ერიფუით იდ ჰის, ეთ ვიმ ცონსულ ყუიდამ ნუსყუამ, ეა სუმო ესსე ფრო. ინ ველ ფოსსე.',199,'inactive'),(32,'Test Periodical Name - 5','science_and_engineering','Test Publisher','',39,'active'),(36,'Test Periodical Name - 9','business','Test Publisher','',77,'active'),(37,'Test Periodical Name - 7','business','Test Publisher','',0,'active'),(38,'Test Periodical Name - 4','business','Test Publisher','',125,'active');
/*!40000 ALTER TABLE `periodicals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscriptions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `periodical_id` bigint(20) NOT NULL,
  `delivery_address` varchar(100) DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `status` enum('active','inactive') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `periodicalId_fk_idx` (`periodical_id`),
  KEY `userId_fk_idx` (`user_id`),
  CONSTRAINT `periodicalId_fk` FOREIGN KEY (`periodical_id`) REFERENCES `periodicals` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `userId_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES (1,1,7,'Lomonosova Str, 24, Kiev, Ukraine','2016-10-18 01:47:23','inactive'),(4,2,1,'Bloch Ave, 10F, California, USA','2018-01-12 18:33:10','inactive'),(11,2,26,'Bloch Ave, 10F, California, USA','2017-02-12 17:34:27','active'),(14,1,26,'Lomonosova Str, 24, Kiev, Ukraine','2019-03-14 03:33:36','active'),(24,1,6,'Lomonosova Str, 24, Kiev, Ukraine','2017-10-17 18:30:29','active'),(25,1,30,'Lomonosova Str, 24, Kiev, Ukraine','2017-03-19 02:12:51','active'),(26,1,1,'Lomonosova Str, 24, Kiev, Ukraine','2017-01-18 22:13:33','active'),(27,1,32,'Lomonosova Str, 24, Kiev, Ukraine','2017-01-30 18:29:51','active');
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`,`name`),
  CONSTRAINT `u_roles_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'admin'),(1,'subscriber'),(2,'subscriber'),(4,'admin'),(5,'subscriber');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `status` enum('active','blocked') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Oleg','Stoliarov','1985-03-05','stolser@gmail.com','Lomonosova Str, 24, Kiev, Ukraine','active'),(2,'Joshua','Bloch','1961-08-28','bloch@gmail.com','Bloch Ave, 10F, California, USA','active'),(4,'James','Gosling','1955-05-19','gosling@nighthacks.com','San Francisco Bay Area, California, U.S.','active'),(5,'Duke','Java-Mascot','1995-05-23','duke@gmail.com','The whole world','active');
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

-- Dump completed on 2017-01-06 16:33:15
