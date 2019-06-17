-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: syhthems
-- ------------------------------------------------------
-- Server version	5.7.26-log

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
-- Table structure for table `data_point`
--

DROP TABLE IF EXISTS `data_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_point` (
  `data_point_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据点ID',
  `data_point_code` varchar(64) NOT NULL COMMENT '数据点编码',
  `data_point_data` varchar(64) NOT NULL COMMENT '数据点数据',
  `data_point_timestamp` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据点时间戳',
  `data_stream_id` bigint(20) unsigned NOT NULL COMMENT '数据流ID',
  `device_id` bigint(20) unsigned NOT NULL COMMENT '设备ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`data_point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_stream`
--

DROP TABLE IF EXISTS `data_stream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_stream` (
  `data_stream_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据流ID',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '产品id',
  `data_stream_code` varchar(32) NOT NULL COMMENT '数据流编码,即设备上传数据的参数名',
  `unit` varchar(16) NOT NULL COMMENT '数据流的单位',
  `unit_symbol` varchar(16) NOT NULL COMMENT '数据流的单位',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`data_stream_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='数据流表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `device_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `device_secret` varchar(256) NOT NULL COMMENT '设备密钥',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '设备关联的产品ID',
  `code` varchar(64) NOT NULL COMMENT '设备编号',
  `name` varchar(64) NOT NULL COMMENT '设备名称',
  `description` varchar(256) DEFAULT NULL COMMENT '设备简介',
  `tags` varchar(256) DEFAULT NULL COMMENT '设备标签，逗号分隔的字符串',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `USER_DEVICE_U1` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_data_stream`
--

DROP TABLE IF EXISTS `device_data_stream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_data_stream` (
  `dds_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` bigint(20) unsigned NOT NULL COMMENT '设备id',
  `data_stream_id` bigint(20) unsigned NOT NULL COMMENT '数据流id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`dds_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备和数据流关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '产品关联的用户ID',
  `name` varchar(64) NOT NULL COMMENT '产品名称',
  `description` varchar(256) DEFAULT NULL COMMENT '产品简介',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮id',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父菜单id,默认为0,即根菜单',
  `menu_code` varchar(64) NOT NULL COMMENT '菜单/按钮代码',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(128) NOT NULL COMMENT '菜单/按钮路径',
  `permission` varchar(128) NOT NULL DEFAULT 'ROLE_USER' COMMENT '菜单的权限描述，默认为ROLE_USER,即用户角色',
  `menu_component` varchar(64) DEFAULT NULL COMMENT '菜单前端组件名称,只有菜单有，按钮无',
  `icon` varchar(64) DEFAULT NULL COMMENT '菜单图标',
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '菜单的类型；0-菜单；1-按钮',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `SYS_MENU_U1` (`menu_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_code` varchar(32) NOT NULL COMMENT '角色编码，必须符合SpringSecurity的标准，即类似于ROLE_USER',
  `role_name` varchar(128) NOT NULL COMMENT '角色名称',
  `role_description` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `enable_flag` varchar(1) DEFAULT 'Y' COMMENT '启用标记,Y-启用,N-禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `SYS_ROLE_U1` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu` (
  `srm_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`srm_id`),
  UNIQUE KEY `SYS_ROLE_MENU_U1` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `user_password` varchar(128) DEFAULT NULL COMMENT '加密过的密码',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话号码',
  `header` varchar(256) DEFAULT NULL COMMENT '用户头像',
  `user_status` varchar(8) DEFAULT NULL COMMENT '状态',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `first_login` varchar(1) NOT NULL DEFAULT 'Y' COMMENT '是否第一次登录',
  `user_description` varchar(256) DEFAULT NULL COMMENT '用户简介',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `SYS_USER_U1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `sur_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_by` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后更新的用户id,默认为0，即系统更改',
  PRIMARY KEY (`sur_id`),
  UNIQUE KEY `SYS_USER_ROLE_U1` (`role_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'syhthems'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-08 21:00:48
