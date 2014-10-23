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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pj_sum_id` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `working_days` float DEFAULT NULL,
  `work_outs` int(11) DEFAULT NULL,
  `new_sales_revenue` int(11) DEFAULT NULL,
  `products_revenue` int(11) DEFAULT NULL,
  `dues_drafts_revenue` int(11) DEFAULT NULL,
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
  `fa` int(11) DEFAULT NULL,
  `enroll_ach` int(11) DEFAULT NULL,
  `enroll_monthly` int(11) DEFAULT NULL,
  `enroll_all_prepay` int(11) DEFAULT NULL,
  `exits` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_pj_sum_id_idx` (`pj_sum_id`),
  CONSTRAINT `fk_pj_sum_id` FOREIGN KEY (`pj_sum_id`) REFERENCES `pj_sum` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pj`
--

LOCK TABLES `pj` WRITE;
/*!40000 ALTER TABLE `pj` DISABLE KEYS */;
INSERT INTO `pj` VALUES (1,'2014-10-18 14:20:06',1,'2014-10-15',2,1,1,1,1,1,1,1,1,1,1,1,1,1,54,8,1,3,9,1,3,4,1,1,23,1,44,1,22,44,0),(2,'2014-10-18 14:20:06',1,'2014-10-01',3,122,13,6,1,1,1,41,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1,3,1,33,1,1,1,1),(3,'2014-09-16 08:28:26',2,'2014-09-01',3,6,6,5,4,6,7,7,65,5,7,8,7,6,6,7,7,67,6,7,7,6,65,7,7,6,5,7,7,56,6);
/*!40000 ALTER TABLE `pj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pj_sum`
--

DROP TABLE IF EXISTS `pj_sum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pj_sum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `club_id` int(6) DEFAULT NULL,
  `year` int(4) DEFAULT NULL,
  `month` int(2) DEFAULT NULL,
  `new_sales` int(11) DEFAULT NULL,
  `shift_in` int(11) DEFAULT NULL,
  `shift_out` int(11) DEFAULT NULL,
  `increment` int(11) DEFAULT NULL,
  `revenue` int(11) DEFAULT NULL,
  `enrolled` int(11) DEFAULT NULL,
  `leave` int(11) DEFAULT NULL,
  `sales_ratio` varchar(6) DEFAULT NULL,
  `exit_ratio` varchar(6) DEFAULT NULL,
  `leave_ratio` varchar(6) DEFAULT NULL,
  `working_days` float DEFAULT NULL,
  `max_work_outs` int(11) DEFAULT NULL,
  `new_sales_revenue` int(11) DEFAULT NULL,
  `products_revenue` int(11) DEFAULT NULL,
  `dues_drafts_revenue` int(11) DEFAULT NULL,
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
  `fa_sum` int(11) DEFAULT NULL,
  `enroll_ach` int(11) DEFAULT NULL,
  `enroll_monthly` int(11) DEFAULT NULL,
  `enroll_all_prepay` int(11) DEFAULT NULL,
  `exits` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pj_sum`
--

LOCK TABLES `pj_sum` WRITE;
/*!40000 ALTER TABLE `pj_sum` DISABLE KEYS */;
INSERT INTO `pj_sum` VALUES (1,'2014-10-18 15:57:25',111111,2014,10,10,2,3,3,387,32,3,'50%','66%','77%',5.5,5,5,5,5,5,5,56,6,7,7,7,7,7,7,7,8,8,7,7,7,7,7,7,7,7,7,6,5,4,34),(2,'2014-10-18 14:21:09',111111,2014,9,6,4,3,6,7,5,4,'30%','20%','7%',7.1,5,4,3,5,6,7,6,5,4,3,54,6,7,7,6,5,4,3,4,56,67,7,6,5,4,4,5,6,7,7);
/*!40000 ALTER TABLE `pj_sum` ENABLE KEYS */;
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

-- Dump completed on 2014-10-20 16:29:57
