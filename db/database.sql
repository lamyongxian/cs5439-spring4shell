USE `crmmvc`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(11) DEFAULT NULL,
  `active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT INTO `user` VALUES (1,'admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','admin@mail.com','admin',1),(2,'editor','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','editor@test.com','editor',1),(12,'test','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','test@test.com','editor',0);

CREATE TABLE IF NOT EXISTS `post` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `imagepath` varchar(765) DEFAULT NULL,
  `content` text,
  `publishtodate` datetime DEFAULT NULL,
  `status` varchar(11) DEFAULT NULL,
  `owner` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`,`title`),
  KEY `fk_owner_id_idx` (`owner`),
  CONSTRAINT `fk_owner_id` FOREIGN KEY (`owner`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

INSERT INTO `post` VALUES (1,'Hello World!!','/upload/aero.PNG','<p><em>Ispum Satu</em></p>',NULL,'publish',NULL),(2,'System dot out dot println',NULL,'<p>Ispum Dua</p>',NULL,NULL,NULL),(3,'tester4',NULL,'<p style=\"text-align: center;\">wqeqweqwe</p>','2018-03-26 02:21:00','draft',1),(4,'qewqewqe2','/upload/hg-blockshift-left3-ff.jpg','<div>wqeqwewewqe &lt;script&gt;alert(\"test\");&lt;/script&gt;</div>','2018-03-21 20:50:00','ready',2),(5,'Test posting 123456 by editor ','/upload/DISM_final_web_sm.gif','<p style=\"text-align: right;\">ewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdewqewqewqewqewqsdafdsfsdfsdew</p>\r\n<p style=\"text-align: right;\">4qewqewqewqewqsdafdsfsdfsd</p>','2018-03-25 21:57:00','ready',1),(12,'Test posting 123456 by editor ',NULL,'',NULL,'ready',NULL);
