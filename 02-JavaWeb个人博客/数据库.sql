/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.3-m13 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `blog`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `username` varchar(32) COLLATE utf8_bin NOT NULL,
  `password` varchar(32) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_article` */

DROP TABLE IF EXISTS `t_article`;

CREATE TABLE `t_article` (
  `aid` varchar(32) COLLATE utf8_bin NOT NULL,
  `date` varchar(25) COLLATE utf8_bin NOT NULL,
  `click` int(11) NOT NULL,
  `title` varchar(25) COLLATE utf8_bin NOT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_category` */

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `cid` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `order` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) NOT NULL,
  `comment` varchar(500) NOT NULL,
  `date` varchar(20) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Table structure for table `t_index` */

DROP TABLE IF EXISTS `t_index`;

CREATE TABLE `t_index` (
  `content` text COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `t_project` */

DROP TABLE IF EXISTS `t_project`;

CREATE TABLE `t_project` (
  `pid` varchar(32) NOT NULL,
  `title` varchar(30) NOT NULL,
  `description` text NOT NULL,
  `imagepath` varchar(250) NOT NULL,
  `date` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `click` double NOT NULL,
  `link` varchar(500) NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_roaditem` */

DROP TABLE IF EXISTS `t_roaditem`;

CREATE TABLE `t_roaditem` (
  `rid` varchar(32) COLLATE utf8_bin NOT NULL,
  `title` varchar(100) COLLATE utf8_bin NOT NULL,
  `content` varchar(600) COLLATE utf8_bin NOT NULL,
  `date` date NOT NULL,
  `year` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
