/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50728
Source Host           : localhost:3306
Source Database       : king

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2019-11-18 23:48:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sy_catalog_node`
-- ----------------------------
-- Table structure for `t_kid_account_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_child_account_record`;
CREATE TABLE `t_child_account_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kid_id` int(11) DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL COMMENT '类型，income-收入，pay-支出，task-任务，reward-奖励',
  `account_in` int(11) DEFAULT '0' COMMENT '账户收入，单位分',
  `account_out` int(11) DEFAULT '0' COMMENT '账户支出，单位分',
  `status` varchar(10) DEFAULT 'finish' COMMENT '任务状态，undo-未完成，finish-已完成',
  `notes` varchar(200) DEFAULT NULL COMMENT '说明',
  `current_date` date DEFAULT NULL COMMENT '当前日期',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_child_account_record
-- ----------------------------

-- ----------------------------
-- Table structure for `t_king_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_king_user`;
CREATE TABLE `t_king_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户UID',
  `union_id` varchar(64) DEFAULT NULL,
  `open_id` varchar(64) DEFAULT NULL,
  `parent_type` varchar(10) DEFAULT NULL COMMENT '身份，F-父亲，M-母亲',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_king_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_parent_child_map`
-- ----------------------------
DROP TABLE IF EXISTS `t_child_parent_child_map`;
CREATE TABLE `t_child_parent_child_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `child_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_child_parent_child_map
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_child`
-- ----------------------------
DROP TABLE IF EXISTS `t_king_user_child`;
CREATE TABLE `t_king_user_child` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` int(11) NOT NULL COMMENT '父母ID',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别，F-女性，M-男性',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `account_total` int(11) DEFAULT '0' COMMENT '账户总金额，单位分',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_king_user_child
-- ----------------------------

-- ----------------------------
-- Table structure for `t_wx_env`
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_env`;
CREATE TABLE `t_wx_env` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `env_key` varchar(255) DEFAULT NULL COMMENT '配置变量key',
  `env_value` varchar(255) DEFAULT NULL COMMENT '变量值',
  `invalid_time` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `wx_env_unique_key` (`env_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_wx_env
-- ----------------------------
