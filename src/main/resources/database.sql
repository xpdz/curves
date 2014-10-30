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
  `username` varchar(6) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('118090','ROLE_USER'),('118091','ROLE_USER'),('118092','ROLE_USER'),('118093','ROLE_USER'),('118094','ROLE_USER'),('118095','ROLE_USER'),('118709','ROLE_USER'),('118710','ROLE_USER'),('118814','ROLE_USER'),('118827','ROLE_USER'),('118828','ROLE_USER'),('118951','ROLE_USER'),('119200','ROLE_USER'),('120932','ROLE_USER'),('120933','ROLE_USER'),('120934','ROLE_USER'),('121216','ROLE_USER'),('121300','ROLE_USER'),('121318','ROLE_USER'),('121596','ROLE_USER'),('121597','ROLE_USER'),('121661','ROLE_USER'),('121891','ROLE_USER'),('121901','ROLE_USER'),('122152','ROLE_USER'),('122252','ROLE_USER'),('122383','ROLE_USER'),('122384','ROLE_USER'),('122385','ROLE_USER'),('122426','ROLE_USER'),('122427','ROLE_USER'),('122478','ROLE_USER'),('122479','ROLE_USER'),('122563','ROLE_USER'),('122564','ROLE_USER'),('122587','ROLE_USER'),('122640','ROLE_USER'),('122703','ROLE_USER'),('122704','ROLE_USER'),('122782','ROLE_USER'),('122795','ROLE_USER'),('122796','ROLE_USER'),('122797','ROLE_USER'),('122945','ROLE_USER'),('123084','ROLE_USER'),('123085','ROLE_USER'),('123112','ROLE_USER'),('123331','ROLE_USER'),('123332','ROLE_USER'),('123333','ROLE_USER'),('123334','ROLE_USER'),('123498','ROLE_USER'),('123499','ROLE_USER'),('123540','ROLE_USER'),('123579','ROLE_USER'),('123580','ROLE_USER'),('123581','ROLE_USER'),('123582','ROLE_USER'),('123583','ROLE_USER'),('123584','ROLE_USER'),('123585','ROLE_USER'),('123586','ROLE_USER'),('123587','ROLE_USER'),('123806','ROLE_USER'),('123807','ROLE_USER'),('123813','ROLE_USER'),('123820','ROLE_USER'),('123870','ROLE_USER'),('123871','ROLE_USER'),('123918','ROLE_USER'),('124028','ROLE_USER'),('124029','ROLE_USER'),('124030','ROLE_USER'),('124031','ROLE_USER'),('124032','ROLE_USER'),('124033','ROLE_USER'),('124034','ROLE_USER'),('124035','ROLE_USER'),('124111','ROLE_USER'),('124112','ROLE_USER'),('124140','ROLE_USER'),('124267','ROLE_USER'),('124268','ROLE_USER'),('124331','ROLE_USER'),('124384','ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ca`
--

DROP TABLE IF EXISTS `ca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `club_id` int(3) DEFAULT NULL,
  `year` int(2) DEFAULT NULL,
  `month` int(1) DEFAULT NULL,
  `col` varchar(7) DEFAULT NULL,
  `goals_tm` int(2) DEFAULT NULL,
  `goals_last_tm` int(2) DEFAULT NULL,
  `goals_last_active` int(2) DEFAULT NULL,
  `goals_last_show_ratio` varchar(5) DEFAULT NULL,
  `goals_last_sales_ratio` varchar(5) DEFAULT NULL,
  `goals_exits_ratio` varchar(3) DEFAULT NULL,
  `goals_new_sales` int(2) DEFAULT NULL,
  `goals_appoints` int(2) DEFAULT NULL,
  `service_tm` int(2) DEFAULT NULL,
  `service_shift` int(2) DEFAULT NULL,
  `service_hold` int(2) DEFAULT NULL,
  `service_active` int(2) DEFAULT NULL,
  `service_hold_ratio` varchar(5) DEFAULT NULL,
  `service_total_wo` int(2) DEFAULT NULL,
  `service_avg_wo` int(2) DEFAULT NULL,
  `service_max_wo` int(2) DEFAULT NULL,
  `service_exits` int(2) DEFAULT NULL,
  `service_exits_ratio` varchar(5) DEFAULT NULL,
  `service_measure` int(2) DEFAULT NULL,
  `service_measure_ratio` varchar(5) DEFAULT NULL,
  `service12` int(2) DEFAULT NULL,
  `service8to11` int(2) DEFAULT NULL,
  `service4to7` int(2) DEFAULT NULL,
  `service1to3` int(2) DEFAULT NULL,
  `service0` int(2) DEFAULT NULL,
  `service3more` int(11) DEFAULT NULL,
  `service_inactive` int(2) DEFAULT NULL,
  `service_fwo_review` int(2) DEFAULT NULL,
  `service_interview` int(2) DEFAULT NULL,
  `service_thanks` int(2) DEFAULT NULL,
  `service3c` int(2) DEFAULT NULL,
  `service_reward` int(2) DEFAULT NULL,
  `service_loyal` int(2) DEFAULT NULL,
  `cm_lead_mail_flyer` int(2) DEFAULT NULL,
  `cm_lead_hand_flyer` int(2) DEFAULT NULL,
  `cm_lead_out` int(2) DEFAULT NULL,
  `cm_lead_cp` int(2) DEFAULT NULL,
  `cm_lead_out_got` int(2) DEFAULT NULL,
  `cm_lead_in_got` int(2) DEFAULT NULL,
  `cm_lead_blog_got` int(2) DEFAULT NULL,
  `cm_lead_bag_got` int(2) DEFAULT NULL,
  `cm_lead_total` int(2) DEFAULT NULL,
  `cm_calls_total` int(2) DEFAULT NULL,
  `cm_calls_out_got` int(2) DEFAULT NULL,
  `cm_calls_in_got` int(2) DEFAULT NULL,
  `cm_calls_blog_got` int(2) DEFAULT NULL,
  `cm_calls_bag_got` int(2) DEFAULT NULL,
  `cm_own_refs` int(2) DEFAULT NULL,
  `cm_other_refs` int(2) DEFAULT NULL,
  `cm_newspaper` int(2) DEFAULT NULL,
  `cm_tv` int(2) DEFAULT NULL,
  `cm_internet` int(2) DEFAULT NULL,
  `cm_sign` int(2) DEFAULT NULL,
  `cm_mate` int(2) DEFAULT NULL,
  `cm_others` int(2) DEFAULT NULL,
  `cm_agp_in_direct_mail` int(2) DEFAULT NULL,
  `cm_agp_in_mail_flyer` int(2) DEFAULT NULL,
  `cm_agp_in_hand_flyer` int(2) DEFAULT NULL,
  `cm_agp_in_cp` int(2) DEFAULT NULL,
  `cm_agp_out_apo_out` int(2) DEFAULT NULL,
  `cm_agp_out_apo_in` int(2) DEFAULT NULL,
  `cm_agp_out_apo_blog` int(2) DEFAULT NULL,
  `cm_agp_out_apo_bag` int(2) DEFAULT NULL,
  `cm_apo_total` int(2) DEFAULT NULL,
  `cm_in_apo_ratio` varchar(5) DEFAULT NULL,
  `cm_out_apo_ratio` varchar(5) DEFAULT NULL,
  `cm_mail_per_apo` int(2) DEFAULT NULL,
  `cm_hand_per_apo` int(2) DEFAULT NULL,
  `cm_out_per_apo` int(2) DEFAULT NULL,
  `cm_br_agp_ratio` varchar(5) DEFAULT NULL,
  `cm_fa_sum` int(2) DEFAULT NULL,
  `cm_show_ratio` varchar(5) DEFAULT NULL,
  `cm_training` int(2) DEFAULT NULL,
  `cm_got3` int(2) DEFAULT NULL,
  `cm_invitation` int(2) DEFAULT NULL,
  `sales_ach` int(2) DEFAULT NULL,
  `sales_monthly` int(2) DEFAULT NULL,
  `sales_all_prepay` int(2) DEFAULT NULL,
  `sales_total` int(2) DEFAULT NULL,
  `sales_ratio` varchar(5) DEFAULT NULL,
  `sales_ach_app_ratio` varchar(5) DEFAULT NULL,
  `sales_fa_review` int(2) DEFAULT NULL,
  `sales_price_review` int(2) DEFAULT NULL,
  `sales_ack` int(2) DEFAULT NULL,
  `sales_target` int(2) DEFAULT NULL,
  `sales_motivation` int(2) DEFAULT NULL,
  `sales_obstacle` int(2) DEFAULT NULL,
  `mgmt_meeting` int(2) DEFAULT NULL,
  `mgmt_ca` int(2) DEFAULT NULL,
  `mgmt_gp` int(2) DEFAULT NULL,
  `mgmt_learn` int(2) DEFAULT NULL,
  `mgmt_sheet` int(2) DEFAULT NULL,
  `mgmt_policy` int(2) DEFAULT NULL,
  `mgmt_compiant_sales` int(2) DEFAULT NULL,
  `mgmt_compiant_method` int(2) DEFAULT NULL,
  `mgmt_compiant_product` int(2) DEFAULT NULL,
  `mgmt_compiant_ad` int(2) DEFAULT NULL,
  `mgmt_training` int(2) DEFAULT NULL,
  `mgmt_report` int(2) DEFAULT NULL,
  `mgmt_plan` int(2) DEFAULT NULL,
  `mgmt_maintain` int(2) DEFAULT NULL,
  `mgmt_face2face` int(2) DEFAULT NULL,
  `club_sales_ratio` varchar(5) DEFAULT NULL,
  `club_ach_app_ratio` varchar(5) DEFAULT NULL,
  `club_ach` int(2) DEFAULT NULL,
  `club_mm` int(2) DEFAULT NULL,
  `club_app` int(2) DEFAULT NULL,
  `club_ns` int(2) DEFAULT NULL,
  `club_lx` int(2) DEFAULT NULL,
  `this_plan` varchar(4096) DEFAULT NULL,
  `next_plan` varchar(4096) DEFAULT NULL,
  `staff1` int(11) DEFAULT NULL,
  `staff2` int(11) DEFAULT NULL,
  `staff3` int(11) DEFAULT NULL,
  `staff4` int(11) DEFAULT NULL,
  `staff5` int(11) DEFAULT NULL,
  `staff6` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ca`
--

LOCK TABLES `ca` WRITE;
/*!40000 ALTER TABLE `ca` DISABLE KEYS */;
/*!40000 ALTER TABLE `ca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `club` (
  `club_id` int(6) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `owner` varchar(45) DEFAULT NULL,
  `manager` varchar(45) DEFAULT NULL,
  `open_date` date DEFAULT NULL,
  `name_en` varchar(45) DEFAULT NULL,
  `owner_en` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`club_id`),
  UNIQUE KEY `id_UNIQUE` (`club_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES (118090,'天母店','渡邊崇之',NULL,NULL,NULL,NULL),(118091,'復興北路店','顏胤琦',NULL,NULL,NULL,NULL),(118092,'古亭店','渡邊崇之',NULL,NULL,NULL,NULL),(118093,'板橋江子翠店','謝麗娟',NULL,NULL,NULL,NULL),(118094,'新竹科園店','林昌斌',NULL,NULL,NULL,NULL),(118095,'永和福和店','林宏遠',NULL,NULL,NULL,NULL),(118709,'信義永康店','胡夢庸',NULL,NULL,NULL,NULL),(118710,'南京三民店','沈鳳俊',NULL,NULL,NULL,NULL),(118814,'新店七張店','石娟娟',NULL,NULL,NULL,NULL),(118827,'永和永安店','傅聖堯',NULL,NULL,NULL,NULL),(118828,'信義通化店','邵曉柔',NULL,NULL,NULL,NULL),(118951,'士林店','許硯清',NULL,NULL,NULL,NULL),(119200,'新竹光復店','潘彥君',NULL,NULL,NULL,NULL),(120932,'新竹中山店','黃種智',NULL,NULL,NULL,NULL),(120933,'石牌店','馬曉玲',NULL,NULL,NULL,NULL),(120934,'板橋文化店','廖如彤',NULL,NULL,NULL,NULL),(121216,'桃園藝文店','頡秀嫚',NULL,NULL,NULL,NULL),(121300,'板橋府中店','謝麗娟',NULL,NULL,NULL,NULL),(121318,'忠孝敦化店','呂玫齡',NULL,NULL,NULL,NULL),(121596,'嘉義體育店','黃種智',NULL,NULL,NULL,NULL),(121597,'新店光明店','黃種智',NULL,NULL,NULL,NULL),(121661,'中山錦州店','郭珈佑',NULL,NULL,NULL,NULL),(121891,'南京小巨蛋店','顏胤琦',NULL,NULL,NULL,NULL),(121901,'中壢環北店','李芝娟',NULL,NULL,NULL,NULL),(122152,'竹北文信店','廖明美',NULL,NULL,NULL,NULL),(122252,'蘆洲徐匯店','林慧如',NULL,NULL,NULL,NULL),(122383,'新莊新泰店','顏明志',NULL,NULL,NULL,NULL),(122384,'桃園南崁店','李美珍',NULL,NULL,NULL,NULL),(122385,'桃園國際店','楊舒晴',NULL,NULL,NULL,NULL),(122426,'高雄河堤店','洪士勛',NULL,NULL,NULL,NULL),(122427,'汐止中興店','吳文隆',NULL,NULL,NULL,NULL),(122478,'和平建國店','王明雄',NULL,NULL,NULL,NULL),(122479,'台南孔廟店','陳秀薌',NULL,NULL,NULL,NULL),(122563,'樹林店','周幼珍',NULL,NULL,NULL,NULL),(122564,'高雄瑞豐店','黃種智',NULL,NULL,NULL,NULL),(122587,'中和南勢角店','翁茂涵',NULL,NULL,NULL,NULL),(122640,'嘉義噴水池店','郭育麟',NULL,NULL,NULL,NULL),(122703,'建國長春店','林怡君',NULL,NULL,NULL,NULL),(122704,'基隆信一店','陳婉瓏',NULL,NULL,NULL,NULL),(122782,'桃園站前店','邱晏群',NULL,NULL,NULL,NULL),(122795,'中壢高中店','劉又誠',NULL,NULL,NULL,NULL),(122796,'景美店','蔡沛瑾',NULL,NULL,NULL,NULL),(122797,'新竹民生店','朱慧真',NULL,NULL,NULL,NULL),(122945,'內壢環中店','江俊毅',NULL,NULL,NULL,NULL),(123084,'林口長庚店','劉鳳娥',NULL,NULL,NULL,NULL),(123085,'台中大墩店','林巧茹',NULL,NULL,NULL,NULL),(123112,'桃園大有店','頡秀嫚',NULL,NULL,NULL,NULL),(123331,'台中崇德店','同欣潔',NULL,NULL,NULL,NULL),(123332,'內湖成功店','黃如玉',NULL,NULL,NULL,NULL),(123333,'北投店','高瑞憶',NULL,NULL,NULL,NULL),(123334,'南西承德店','張曦方',NULL,NULL,NULL,NULL),(123498,'內湖西湖店','楊美玲',NULL,NULL,NULL,NULL),(123499,'土城中央店','傅聖堯',NULL,NULL,NULL,NULL),(123540,'民權龍江店','郭珈佑',NULL,NULL,NULL,NULL),(123579,'忠孝新生店','胡夢庸',NULL,NULL,NULL,NULL),(123580,'新莊富國店','邱小蘋',NULL,NULL,NULL,NULL),(123581,'蘆洲光榮店','陳似瑄',NULL,NULL,NULL,NULL),(123582,'鳳山青年店','陳盈儒',NULL,NULL,NULL,NULL),(123583,'豐原SOGO店','許月季',NULL,NULL,NULL,NULL),(123584,'圓山花博店','余文洲',NULL,NULL,NULL,NULL),(123585,'西門漢中店','鄧琇文',NULL,NULL,NULL,NULL),(123586,'台南永康店','陳明傳',NULL,NULL,NULL,NULL),(123587,'重新正義店','謝麗娟',NULL,NULL,NULL,NULL),(123806,'桃園大湳店','楊筑妃',NULL,NULL,NULL,NULL),(123807,'永和秀山店','林志遠',NULL,NULL,NULL,NULL),(123813,'淡水中山店','林正裕',NULL,NULL,NULL,NULL),(123820,'竹圍捷運店','高美妤',NULL,NULL,NULL,NULL),(123870,'高雄文化中心店','李憶芳',NULL,NULL,NULL,NULL),(123871,'民生圓環店','洪士勛',NULL,NULL,NULL,NULL),(123918,'高雄大昌店','郭明怡',NULL,NULL,NULL,NULL),(124028,'中和園區店','楊璧綺',NULL,NULL,NULL,NULL),(124029,'台大公館店','劉智興',NULL,NULL,NULL,NULL),(124030,'台中河南店','許維倫',NULL,NULL,NULL,NULL),(124031,'新莊中港店','唐為甫',NULL,NULL,NULL,NULL),(124032,'台中漢口店','葉文生',NULL,NULL,NULL,NULL),(124033,'中和員山店','蘇燕娥',NULL,NULL,NULL,NULL),(124034,'苗栗頭份店','李冠儀',NULL,NULL,NULL,NULL),(124035,'中壢中原店','凌馨霞',NULL,NULL,NULL,NULL),(124111,'屏東店','徐瑞成',NULL,NULL,NULL,NULL),(124112,'左營高鐵店','梁欣惠',NULL,NULL,NULL,NULL),(124140,'文山保儀店','甯淑芬',NULL,NULL,NULL,NULL),(124267,'新店中正店','高佳伶',NULL,NULL,NULL,NULL),(124268,'泰山明志店','王雅欣',NULL,NULL,NULL,NULL),(124331,'竹北福興店','廖明美',NULL,NULL,NULL,NULL),(124384,'土城海山店','謝麗娟',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
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
  `pj_date` date DEFAULT NULL,
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
  UNIQUE KEY `pj_sum_id_pj_date` (`pj_sum_id`,`pj_date`),
  KEY `fk_pj_sum_id_idx` (`pj_sum_id`),
  CONSTRAINT `fk_pj_sum_id` FOREIGN KEY (`pj_sum_id`) REFERENCES `pj_sum` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pj`
--

LOCK TABLES `pj` WRITE;
/*!40000 ALTER TABLE `pj` DISABLE KEYS */;
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
  `leaves` int(11) DEFAULT NULL,
  `valids` int(11) DEFAULT NULL,
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
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `clubId_year_month` (`club_id`,`year`,`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pj_sum`
--

LOCK TABLES `pj_sum` WRITE;
/*!40000 ALTER TABLE `pj_sum` DISABLE KEYS */;
/*!40000 ALTER TABLE `pj_sum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff_eval`
--

DROP TABLE IF EXISTS `staff_eval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_eval` (
  `id` int(11) NOT NULL,
  `ca_id` int(11) DEFAULT NULL,
  `sales_ratio` varchar(5) DEFAULT NULL,
  `ach_app_ratio` varchar(5) DEFAULT NULL,
  `ach` int(2) DEFAULT NULL,
  `mm` int(2) DEFAULT NULL,
  `app` int(2) DEFAULT NULL,
  `ns` int(2) DEFAULT NULL,
  `lx` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_ca_id_idx` (`ca_id`),
  CONSTRAINT `fk_ca_id` FOREIGN KEY (`ca_id`) REFERENCES `ca` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_eval`
--

LOCK TABLES `staff_eval` WRITE;
/*!40000 ALTER TABLE `staff_eval` DISABLE KEYS */;
/*!40000 ALTER TABLE `staff_eval` ENABLE KEYS */;
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
INSERT INTO `users` VALUES ('118090','118090',''),('118091','118091',''),('118092','118092',''),('118093','118093',''),('118094','118094',''),('118095','118095',''),('118709','118709',''),('118710','118710',''),('118814','118814',''),('118827','118827',''),('118828','118828',''),('118951','118951',''),('119200','119200',''),('120932','120932',''),('120933','120933',''),('120934','120934',''),('121216','121216',''),('121300','121300',''),('121318','121318',''),('121596','121596',''),('121597','121597',''),('121661','121661',''),('121891','121891',''),('121901','121901',''),('122152','122152',''),('122252','122252',''),('122383','122383',''),('122384','122384',''),('122385','122385',''),('122426','122426',''),('122427','122427',''),('122478','122478',''),('122479','122479',''),('122563','122563',''),('122564','122564',''),('122587','122587',''),('122640','122640',''),('122703','122703',''),('122704','122704',''),('122782','122782',''),('122795','122795',''),('122796','122796',''),('122797','122797',''),('122945','122945',''),('123084','123084',''),('123085','123085',''),('123112','123112',''),('123331','123331',''),('123332','123332',''),('123333','123333',''),('123334','123334',''),('123498','123498',''),('123499','123499',''),('123540','123540',''),('123579','123579',''),('123580','123580',''),('123581','123581',''),('123582','123582',''),('123583','123583',''),('123584','123584',''),('123585','123585',''),('123586','123586',''),('123587','123587',''),('123806','123806',''),('123807','123807',''),('123813','123813',''),('123820','123820',''),('123870','123870',''),('123871','123871',''),('123918','123918',''),('124028','124028',''),('124029','124029',''),('124030','124030',''),('124031','124031',''),('124032','124032',''),('124033','124033',''),('124034','124034',''),('124035','124035',''),('124111','124111',''),('124112','124112',''),('124140','124140',''),('124267','124267',''),('124268','124268',''),('124331','124331',''),('124384','124384','');
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

-- Dump completed on 2014-10-30  9:51:25
