/*
Navicat MySQL Data Transfer

Source Server         : datawood
Source Server Version : 50722
Source Host           : 192.168.86.163:3306
Source Database       : yhe

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-07-31 17:44:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for docs_post
-- ----------------------------
DROP TABLE IF EXISTS `docs_post`;
CREATE TABLE `docs_post` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `last_modified_at` datetime NOT NULL,
  `last_modified_by` varchar(255) NOT NULL,
  `cate_id` bigint(20) NOT NULL,
  `content` varchar(20000) DEFAULT NULL,
  `is_open` bit(1) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_post
-- ----------------------------
INSERT INTO `docs_post` VALUES ('603902294005645312', '2019-07-25 10:52:07', 'dev', '2019-07-25 10:52:07', 'dev', '603901905961222144', '修改之后的内容', '', 'Java基础');
INSERT INTO `docs_post` VALUES ('603902294760620032', '2019-07-25 10:52:07', 'dev', '2019-07-25 10:52:07', 'dev', '603901905961222144', 'ArrayList和HashMap是最常用的集合类', '', 'List和Map详解');

-- ----------------------------
-- Table structure for docs_post_cate
-- ----------------------------
DROP TABLE IF EXISTS `docs_post_cate`;
CREATE TABLE `docs_post_cate` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `last_modified_at` datetime NOT NULL,
  `last_modified_by` varchar(255) NOT NULL,
  `cate_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rtiv49tg9njlf9ahn0bm17v` (`cate_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_post_cate
-- ----------------------------
INSERT INTO `docs_post_cate` VALUES ('603901905961222144', '2019-07-25 10:50:34', 'dev', '2019-07-25 10:50:34', 'dev', 'Java');

-- ----------------------------
-- Table structure for docs_post_tag
-- ----------------------------
DROP TABLE IF EXISTS `docs_post_tag`;
CREATE TABLE `docs_post_tag` (
  `post_id` bigint(20) NOT NULL,
  `tag_name` varchar(255) NOT NULL,
  PRIMARY KEY (`post_id`,`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_post_tag
-- ----------------------------
INSERT INTO `docs_post_tag` VALUES ('603902294005645312', 'Java');
INSERT INTO `docs_post_tag` VALUES ('603902294005645312', '反射');
INSERT INTO `docs_post_tag` VALUES ('603902294005645312', '集合');
INSERT INTO `docs_post_tag` VALUES ('603902294760620032', '数据结构');
INSERT INTO `docs_post_tag` VALUES ('603902294760620032', '集合');

-- ----------------------------
-- Table structure for docs_role
-- ----------------------------
DROP TABLE IF EXISTS `docs_role`;
CREATE TABLE `docs_role` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `last_modified_at` datetime NOT NULL,
  `last_modified_by` varchar(255) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bn8s1ulfmjbo7vlq4bv1ufgjg` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_role
-- ----------------------------
INSERT INTO `docs_role` VALUES ('603625905130045440', '2019-07-24 16:33:51', 'dev', '2019-07-24 16:33:51', 'dev', 'admin');
INSERT INTO `docs_role` VALUES ('603625906098929664', '2019-07-24 16:33:51', 'dev', '2019-07-24 16:33:51', 'dev', 'user');

-- ----------------------------
-- Table structure for docs_user
-- ----------------------------
DROP TABLE IF EXISTS `docs_user`;
CREATE TABLE `docs_user` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `last_modified_at` datetime NOT NULL,
  `last_modified_by` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `salt` varchar(36) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nnlotyggvi8d2u6reww83fgyg` (`username`),
  UNIQUE KEY `UK_53lkq8tiwoeuvgycu7asm1vse` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_user
-- ----------------------------
INSERT INTO `docs_user` VALUES ('603901573583601664', '2019-07-25 10:49:15', 'dev', '2019-07-25 10:49:15', 'dev', 'qq@qq.com', '7008e9b0c561cde5b2eade07ce3cb6e1f01d060c00509f2672424533a0994798', '21232f29-7a57-35a7-8389-4a0e4a801fc3', 'admin');
INSERT INTO `docs_user` VALUES ('603901573818482688', '2019-07-25 10:49:15', 'dev', '2019-07-25 10:49:15', 'dev', '163@163.com', '7a1aec00a77bc219b7ddeda8293f3a4a2dbc02fb3bad6e19c0f9b5612b5ea985', 'ee11cbb1-9052-340b-87aa-c0ca060c23ee', 'user');

-- ----------------------------
-- Table structure for docs_user_role
-- ----------------------------
DROP TABLE IF EXISTS `docs_user_role`;
CREATE TABLE `docs_user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of docs_user_role
-- ----------------------------
INSERT INTO `docs_user_role` VALUES ('603625905130045440', '603901573583601664');
INSERT INTO `docs_user_role` VALUES ('603625906098929664', '603901573583601664');
INSERT INTO `docs_user_role` VALUES ('603625906098929664', '603901573818482688');
