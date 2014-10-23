CREATE DATABASE  IF NOT EXISTS `curves` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `curves`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: curves
-- ------------------------------------------------------
-- Server version	5.5.27

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
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorities` (
  `username` varchar(6) DEFAULT NULL,
  `authority` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('000000','ROLE_ADMIN'),('111111','ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pj`
--

DROP TABLE IF EXISTS `pj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pj` (
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date` date DEFAULT NULL,
  `club_id` int(6) DEFAULT NULL,
  `working_days` float DEFAULT NULL,
  `work_outs` int(11) DEFAULT NULL,
  `new_sales` int(11) DEFAULT NULL,
  `pjcol` int(11) DEFAULT NULL,
  `adjusments` int(11) DEFAULT NULL,
  `products` int(11) DEFAULT NULL,
  `dues_drafts` int(11) DEFAULT NULL,
  `other_revenue` int(11) DEFAULT NULL,
  `incoming_calls` int(11) DEFAULT NULL,
  `incoming_apo` int(11) DEFAULT NULL,
  `outgoing_calls` int(11) DEFAULT NULL,
  `outgoing_apo` int(11) DEFAULT NULL,
  `br_own_ref` int(11) DEFAULT NULL,
  `br_others_ref` int(11) DEFAULT NULL,
  `branded_newspaper` int(11) DEFAULT NULL,
  `branded_tv` int(11) DEFAULT NULL,
  `branded_internet` int(11) DEFAULT NULL,
  `branded_sign` int(11) DEFAULT NULL,
  `branded_mate` int(11) DEFAULT NULL,
  `branded_others` int(11) DEFAULT NULL,
  `agp_in_direct_mail` int(11) DEFAULT NULL,
  `agp_in_mail_flyer` int(11) DEFAULT NULL,
  `agp_in_hand_flyer` int(11) DEFAULT NULL,
  `agp_in_cp` int(11) DEFAULT NULL,
  `agp_out_apo_out` int(11) DEFAULT NULL,
  `agp_out_apo_in` int(11) DEFAULT NULL,
  `agp_out_apo_blog` int(11) DEFAULT NULL,
  `agp_out_apo_bag` int(11) DEFAULT NULL,
  `enroll_ach` int(11) DEFAULT NULL,
  `enroll_monthly` int(11) DEFAULT NULL,
  `enroll_all_prepay` int(11) DEFAULT NULL,
  `cancels` int(11) DEFAULT NULL,
  PRIMARY KEY (`last_modified`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pj`
--

LOCK TABLES `pj` WRITE;
/*!40000 ALTER TABLE `pj` DISABLE KEYS */;
INSERT INTO `pj` VALUES ('2014-10-15 16:00:00','2014-10-15',111111,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1),('2014-10-16 08:28:26','2014-10-01',111111,1,122,13,3,41,6,1,1,1,41,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1);
/*!40000 ALTER TABLE `pj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(6) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('000000','000000',''),('111111','111111','');
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

-- Dump completed on 2014-10-16 16:34:19
