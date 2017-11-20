/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.3-m13 : Database - db_cms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_cms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_cms`;

/*Table structure for table `t_arctype` */

DROP TABLE IF EXISTS `t_arctype`;

CREATE TABLE `t_arctype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(100) DEFAULT NULL,
  `sortNo` int(11) DEFAULT NULL,
  `keywords` varchar(500) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_arctype` */

insert  into `t_arctype`(`id`,`typeName`,`sortNo`,`keywords`,`description`) values (1,'Java基础',1,NULL,NULL),(2,'网页基础',2,'121','s '),(4,'Java建站111',3,NULL,NULL),(5,'Java爬虫111',4,NULL,NULL),(6,'网站SEO',5,NULL,NULL),(7,'广告联盟',6,NULL,NULL);

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `publishDate` datetime DEFAULT NULL,
  `summary` varchar(500) DEFAULT NULL,
  `content` text,
  `titleColor` varchar(100) DEFAULT NULL,
  `click` int(11) DEFAULT NULL,
  `isRecommend` tinyint(4) DEFAULT NULL,
  `isSlide` int(11) DEFAULT NULL,
  `slideImage` varchar(1000) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `keyWords` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `typeId` (`typeId`),
  CONSTRAINT `t_article_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `t_arctype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

/*Data for the table `t_article` */

insert  into `t_article`(`id`,`title`,`publishDate`,`summary`,`content`,`titleColor`,`click`,`isRecommend`,`isSlide`,`slideImage`,`typeId`,`keyWords`) values (1,'测试java基础1','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',11,1,0,NULL,1,NULL),(2,'测试java基础1','2016-01-04 00:00:00','测试摘要','测试java基础 内容','black',15,0,1,'1.jpg',1,NULL),(3,'测试java基础2','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,0,0,NULL,1,NULL),(4,'测试java基础3','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,0,0,NULL,1,NULL),(5,'测试java基础4','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,0,0,NULL,1,NULL),(6,'测试java基础5','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,1,0,NULL,1,NULL),(7,'测试java基础6','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,0,0,NULL,1,NULL),(8,'测试java基础7','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,0,0,NULL,1,NULL),(9,'测试java基础8','2016-01-02 00:00:00','测试摘要','测试java基础 内容','black',10,1,0,NULL,1,NULL),(10,'测试网页基础1','2016-01-02 00:00:00','测试摘要','测试网页基础 内容','black',12,0,0,NULL,2,NULL),(11,'测试网页基础2','2016-01-02 00:00:00','测试摘要','测试网页基础 内容','black',12,0,0,NULL,2,NULL),(12,'测试网页基础3','2016-01-02 00:00:00','测试摘要','测试网页基础 内容','black',11,1,0,NULL,2,NULL),(13,'测试网页基础4','2016-03-02 02:01:30','测试摘要','测试网页基础 内容','black',49,0,1,'2.jpg',2,'1 s是'),(14,'测试网页基础5','2016-01-02 00:00:00','测试摘要','测试网页基础 内容','black',10,1,0,NULL,2,NULL),(15,'测试网页基础6','2016-01-02 00:00:00','测试摘要','测试网页基础 内容','black',10,0,0,NULL,2,NULL),(16,'红页测试','2016-08-02 00:00:00','测试摘要','测试网页基础 内容 s是滴死读书是滴是滴是滴11111','red',15,1,0,NULL,2,NULL),(17,'蓝页测试','2017-01-02 00:00:00','测试摘要','测试网页基础 内容','blue',16,0,0,NULL,2,NULL),(18,'网页Java建站基础1','2016-04-02 00:00:00','测试摘要','网页Java建站基础 内容','black',16,1,0,NULL,4,NULL),(19,'网页Java建站基础4','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',10,0,0,NULL,4,NULL),(20,'网页Java建站基础3','2016-01-04 00:00:00','测试摘要','网页Java建站基础 内容','black',10,1,0,NULL,4,NULL),(21,'网页Java建站基础4','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',11,0,1,'4.jpg',4,NULL),(22,'网页Java建站基础5','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',10,1,0,NULL,4,NULL),(23,'网页Java建站基础6','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',10,0,0,NULL,4,NULL),(24,'网页Java建站基础7','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',10,1,0,NULL,4,NULL),(25,'网页Java建站基础8','2016-01-02 00:00:00','测试摘要','网页Java建站基础 内容','black',10,0,0,NULL,4,NULL),(26,'Java爬虫基础1','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',10,0,0,NULL,5,NULL),(27,'Java爬虫基础5','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,0,NULL,5,NULL),(28,'Java爬虫基础3','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,0,NULL,5,NULL),(29,'Java爬虫基础5','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',12,0,0,NULL,5,NULL),(30,'Java爬虫基础5','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,0,NULL,5,NULL),(31,'Java爬虫基础6','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,0,NULL,5,NULL),(32,'Java爬虫基础7','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,1,'3.jpg',5,NULL),(33,'Java爬虫基础8','2016-01-02 00:00:00','测试摘要','Java爬虫基础 内容','black',11,0,0,NULL,5,NULL),(34,'网站SEO1','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(35,'网站SEO6','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(36,'网站SEO3','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(37,'网站SEO6','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,1,'5.jpg',6,NULL),(38,'网站SEO6','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(39,'网站SEO6','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(40,'网站SEO7','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(41,'网站SEO8','2016-01-02 00:00:00','测试摘要','网站SEO 内容','black',11,0,0,NULL,6,NULL),(42,'广告联盟1','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',11,0,0,NULL,7,NULL),(43,'广告联盟7','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(44,'广告联盟3','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(45,'广告联盟7','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(46,'广告联盟7','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(47,'广告联盟7','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(48,'广告联盟7','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(49,'广告联盟8','2016-01-02 00:00:00','测试摘要','广告联盟 内容','black',10,0,0,NULL,7,NULL),(50,'21',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(51,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(52,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(53,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(54,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(55,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(56,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),(57,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(58,'11',NULL,'21','<p>21321<br/></p>','',0,0,0,NULL,1,''),(59,'32',NULL,'dsafda','<p>21321<br/></p>','#FF0000',0,1,1,'20161029085937.jpg',1,'ha ha');

/*Table structure for table `t_link` */

DROP TABLE IF EXISTS `t_link`;

CREATE TABLE `t_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `sortNo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_link` */

insert  into `t_link`(`id`,`name`,`url`,`sortNo`) values (1,'测试1','http://www.java1234.com',1),(2,'测试2','http://www.java1234.com',2),(3,'测试3','http://www.java1234.com',3),(4,'测试4','http://www.java1234.com',4),(5,'测试2',NULL,5),(6,'测试k',NULL,66),(7,'测试666',NULL,7),(8,'测试2',NULL,8);

/*Table structure for table `t_manager` */

DROP TABLE IF EXISTS `t_manager`;

CREATE TABLE `t_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_manager` */

insert  into `t_manager`(`id`,`username`,`password`) values (1,'admin','91d99e8b91f4cfab7914201f7aa8f485');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
