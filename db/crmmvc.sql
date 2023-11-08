-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: crmmvc
-- ------------------------------------------------------
-- Server version	5.6.27-log

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

USE crmmvc;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `imagepath` varchar(765) DEFAULT NULL,
  `content` text,
  `publishtodate` datetime DEFAULT NULL,
  `status` varchar(11) DEFAULT NULL,
  `owner` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`,`title`),
  KEY `fk_owner_id_idx` (`owner`),
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'Hello World!!','/upload/aero.PNG','<p><em>Ispum Satu</em></p>',NULL,'publish',NULL),(2,'System dot out dot println',NULL,'<p>Ispum Dua</p>',NULL,NULL,NULL),(3,'tester4',NULL,'<p style=\"text-align: center;\">wqeqweqwe</p>','2018-03-26 02:21:00','draft',1),(4,'qewqewqe2','/upload/hg-blockshift-left3-ff.jpg','<div>wqeqwewewqe</div>','2018-03-21 20:50:00','ready',2),(5,'Test posting 123456 by editor ','/upload/DISM_final_web_sm.gif','<p style=\"text-align: right;\">ewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdew</p>\r\n<p style=\"text-align: right;\">4qewqewqewqewqsdafdsfsdfsd</p>','2018-03-25 21:57:00','ready',1),(12,'Test posting 123456 by editor ',NULL,'',NULL,'ready',NULL);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(11) DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','yongxian@mail.com','admin',1),(2,'editor','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','editor@test.com','editor',1),(12,'test','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','test@test.com','editor',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'crmmvc'
--

--
-- Dumping routines for database 'crmmvc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-27 10:26:23
