# ncu_ctf_demo
crud...

1.1需求分析
用户、管理员、题库、通知栏、题解、留言区
● 用户：用户可以答题，并参与实时排名，拥有头像、昵称、地址、电话号码、email等信息，通过答题可累计积分。可以在题解下评论区评论。
● 管理员：分为超级管理员、 社区管理员、 用户管理员、 题库管理员四种。拥有拥有头像、昵称、地址、电话号码、email等信息。社区管理员可以删除评论，发布通知。用户管理员可以修改用户信息。超级管理员拥有所有权限，可以添加各类管理员。题库管理员可以发布题单，开放题解
● 题库：题库拥有各类题型，题目链接，题目描述，题目描述简写，题目积分，创建时间，更新时间，题目点赞数。
● 通知栏：社区管理员发布的通知。
● 题解：所有用户可参与讨论，拥有评论区。

1.3对每个实体定义的属性如下
管理员 manager：{id_manager,username,password,email,headImage,level,phone}
用户 user：{id_user,username,password,email,headImage,scores,address,phone}
留言commond:  {id_commond,content}
通知 notifications :{id_notification,content}
题目 challenges :{id_challenge,category,score,visibility,url,introduction,content,createTime,updataTime}
题解 solutions：{id_solution,title,details}
关系表
管理员_通知表 M-N 1-n ：{id_manager,id_notification,createTime,updataTime}
管理员_题解表 M-S 1-n: 	{id_manager,id_solution,createTime,updataTime}
用户_留言表 U-Cm 1-n: 	{id_user，id_commond，createTime}
用户_题目表 U-Cg m-n: 	{id_user,id_challenge,ansTime,Passed}

数据库docker_project

![](D:\Code\1666699896056-624bf6b9-c6f5-47e8-a8e4-dcaf7bb2b9ec.png)

```sql
/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.30 : Database - docker_project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`docker_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `docker_project`;

/*Table structure for table `challenges` */

DROP TABLE IF EXISTS `challenges`;

CREATE TABLE `challenges` (
  `id_challenge` bigint NOT NULL AUTO_INCREMENT,
  `category` char(255) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `visibility` tinyint(1) DEFAULT NULL,
  `introduction` text,
  `content` text,
  `url` char(255) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `updataTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_challenge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `challenges` */

/*Table structure for table `commond` */

DROP TABLE IF EXISTS `commond`;

CREATE TABLE `commond` (
  `id_commond` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  PRIMARY KEY (`id_commond`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `commond` */

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `id_manager` bigint NOT NULL AUTO_INCREMENT,
  `username` char(255) NOT NULL,
  `password` char(64) NOT NULL,
  `email` char(32) DEFAULT NULL,
  `headImage` char(255) DEFAULT NULL,
  `level` int DEFAULT NULL,
  `phone` char(32) DEFAULT NULL,
  PRIMARY KEY (`id_manager`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `manager` */

/*Table structure for table `manager_notifications` */

DROP TABLE IF EXISTS `manager_notifications`;

CREATE TABLE `manager_notifications` (
  `id_manager` bigint DEFAULT NULL,
  `id_notification` bigint DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `updataTime` timestamp NULL DEFAULT NULL,
  KEY `id_manager` (`id_manager`),
  KEY `id_notification` (`id_notification`),
  CONSTRAINT `manager_notifications_ibfk_1` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manager_notifications_ibfk_2` FOREIGN KEY (`id_notification`) REFERENCES `notifications` (`id_notification`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `manager_notifications` */

/*Table structure for table `manager_solutions` */

DROP TABLE IF EXISTS `manager_solutions`;

CREATE TABLE `manager_solutions` (
  `id_manager` bigint DEFAULT NULL,
  `id_solution` bigint DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `updataTime` timestamp NULL DEFAULT NULL,
  KEY `id_manager` (`id_manager`),
  KEY `id_solution` (`id_solution`),
  CONSTRAINT `manager_solutions_ibfk_1` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `manager_solutions_ibfk_2` FOREIGN KEY (`id_solution`) REFERENCES `solutions` (`id_solution`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `manager_solutions` */

/*Table structure for table `notifications` */

DROP TABLE IF EXISTS `notifications`;

CREATE TABLE `notifications` (
  `id_notification` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  PRIMARY KEY (`id_notification`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `notifications` */

/*Table structure for table `solutions` */

DROP TABLE IF EXISTS `solutions`;

CREATE TABLE `solutions` (
  `id_solution` bigint NOT NULL AUTO_INCREMENT,
  `title` char(255) DEFAULT NULL,
  `details` text,
  PRIMARY KEY (`id_solution`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `solutions` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id_user` bigint NOT NULL AUTO_INCREMENT,
  `username` char(255) NOT NULL,
  `password` char(255) DEFAULT NULL,
  `email` char(255) DEFAULT NULL,
  `headImage` char(255) DEFAULT NULL,
  `scores` bigint DEFAULT NULL,
  `address` char(255) DEFAULT NULL,
  `phone` char(255) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=1584916010857680899 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id_user`,`username`,`password`,`email`,`headImage`,`scores`,`address`,`phone`) values 
(1584916010857680898,'图图','25f9e794323b453885f5181f1b624d0b','2312387123@qq.com','null',100,'翻斗花园','1783218973');

/*Table structure for table `user_challenges` */

DROP TABLE IF EXISTS `user_challenges`;

CREATE TABLE `user_challenges` (
  `id_user` bigint DEFAULT NULL,
  `id_challenge` bigint DEFAULT NULL,
  `ansTime` timestamp NULL DEFAULT NULL,
  `Passed` tinyint(1) DEFAULT NULL,
  KEY `id_user` (`id_user`),
  KEY `id_challenge` (`id_challenge`),
  CONSTRAINT `user_challenges_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_challenges_ibfk_2` FOREIGN KEY (`id_challenge`) REFERENCES `challenges` (`id_challenge`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_challenges` */

/*Table structure for table `user_commond` */

DROP TABLE IF EXISTS `user_commond`;

CREATE TABLE `user_commond` (
  `id_user` bigint DEFAULT NULL,
  `id_commond` bigint DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  KEY `id_user` (`id_user`),
  KEY `id_commond` (`id_commond`),
  CONSTRAINT `user_commond_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_commond_ibfk_2` FOREIGN KEY (`id_commond`) REFERENCES `commond` (`id_commond`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_commond` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
