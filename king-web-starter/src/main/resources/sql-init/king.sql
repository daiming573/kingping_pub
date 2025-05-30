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
DROP TABLE IF EXISTS `sy_catalog_node`;
CREATE TABLE `sy_catalog_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) DEFAULT NULL,
  `catalog_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级菜单ID',
  `is_valid` varchar(5) DEFAULT NULL COMMENT '是否有效，Y-合法，N-非法',
  `code` varchar(30) DEFAULT NULL COMMENT '菜单英文编号',
  `name` varchar(64) DEFAULT NULL COMMENT '菜单编号',
  `label` varchar(5) DEFAULT NULL COMMENT '菜单中文名称',
  `has_child` varchar(2) DEFAULT NULL COMMENT '是否有子节点，Y-有子节点，N-无子节点',
  `type_name` varchar(64) DEFAULT NULL COMMENT '类型，MENU-菜单',
  `icon_name` varchar(64) DEFAULT NULL COMMENT 'icon名称',
  `disp_order` int(11) DEFAULT NULL COMMENT '排序',
  `link_catalog` varchar(64) DEFAULT NULL,
  `outer_catalog` varchar(64) DEFAULT NULL,
  `do_redirect` varchar(200) DEFAULT NULL,
  `do_invoke` varchar(64) DEFAULT NULL COMMENT '跳转链接',
  `do_introduce` varchar(100) DEFAULT NULL COMMENT '介绍动作',
  `create_user_id` int(11) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_catalog_node
-- ----------------------------
INSERT INTO `sy_catalog_node` VALUES ('1', null, '1', '0', 'Y', 'sy_menu', 'sy_menu', '系统管理', 'Y', 'MENU', 'com-config', '2', '', '', '', 'sy/main/center.do', 'sy/main/goIntroduce.do', '1', '', '2019-11-17 15:01:40');
INSERT INTO `sy_catalog_node` VALUES ('2', null, '2', '0', 'Y', 'bus_menu', 'bus_menu', '业务管理', 'Y', 'MENU', 'catalog-table', '1', '', '', '', 'sy/main/center.do', 'sy/main/goIntroduce.do', '1', '', '2019-11-17 15:15:47');
INSERT INTO `sy_catalog_node` VALUES ('3', null, '3', '1', 'Y', 'user_menu', 'user_menu', '用户管理', 'Y', 'MENU', 'form-config', '1', '', '', '', '', '', '1', '', '2019-11-17 15:27:17');
INSERT INTO `sy_catalog_node` VALUES ('4', null, '4', '3', 'Y', 'sy_user_list', 'sy_user_list', '用户列表', 'N', 'MENU', 'catalog-user', '1', '', '', '', 'sy/user/goMain.do', '', '1', '', '2019-11-17 15:32:12');
INSERT INTO `sy_catalog_node` VALUES ('5', null, '5', '3', 'Y', 'sy_role_list', 'sy_role_list', '角色列表', 'N', 'MENU', 'catalog-role', '2', '', '', '', 'sy/role/goMain.do', '', '1', '', '2019-11-17 15:32:24');
INSERT INTO `sy_catalog_node` VALUES ('6', null, '6', '3', 'Y', 'menu_list', 'menu_list', '菜单目录', 'N', 'MENU', 'catalog-module', '3', '', '', '', 'sy/catalogNode/goSyCatalogTree.do', '', '1', '', '2019-11-17 15:35:40');
INSERT INTO `sy_catalog_node` VALUES ('7', null, '7', '3', 'Y', 'role_power', 'role_power', '角色权限', 'N', 'MENU', 'catalog-org', '4', '', '', '', 'sy/CatalogRole/goRoleForm.do', '', '1', '', '2019-11-17 15:35:58');
INSERT INTO `sy_catalog_node` VALUES ('8', '5b77e4a1-c9be-4829-a3c6-7ab11cdd1631', null, '2', 'Y', 'king_pig_menu', 'king_pig_menu', '金猪管理', 'Y', 'MENU', 'catalog-table', '1', '', '', '', '', '', '1', '', '2019-11-17 16:09:10');
INSERT INTO `sy_catalog_node` VALUES ('9', '106c65a2-888b-43c1-bfc0-69d20c3a4400', null, '8', 'Y', 'user_menu', 'user_menu', '用户管理', 'N', 'MENU', 'form-config', '1', '', '', '', 'king/user/goMain.do', '', '1', '', '2019-11-17 16:09:48');

-- ----------------------------
-- Table structure for `sy_catalog_role`
-- ----------------------------
DROP TABLE IF EXISTS `sy_catalog_role`;
CREATE TABLE `sy_catalog_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_valid` varchar(5) DEFAULT NULL COMMENT '是否有效，Y-合法，N-非法',
  `node_ids` varchar(200) DEFAULT NULL COMMENT '菜单节点ID',
  `power_node_ids` varchar(200) DEFAULT NULL COMMENT '有权限的菜单节点ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_catalog_role
-- ----------------------------
INSERT INTO `sy_catalog_role` VALUES ('1', 'Y', '1,2,3,4,5,6,7,8,9', '1,2,3,4,5,6,7,8,9', '1', '1', '2019-11-17 15:04:38');

-- ----------------------------
-- Table structure for `sy_role`
-- ----------------------------
DROP TABLE IF EXISTS `sy_role`;
CREATE TABLE `sy_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) DEFAULT NULL,
  `is_valid` varchar(5) DEFAULT NULL COMMENT '是否有效，Y-合法，N-非法',
  `code` varchar(64) DEFAULT NULL COMMENT '角色编号',
  `type` varchar(30) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '角色英文名称',
  `label` varchar(50) DEFAULT NULL COMMENT '角色中文名称',
  `icon_name` varchar(100) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role
-- ----------------------------
INSERT INTO `sy_role` VALUES ('1', null, 'Y', 'admin', null, 'sysadmin', '系统管理员', '', '系统管理员角色', '2019-11-17 15:00:08');
INSERT INTO `sy_role` VALUES ('2', '401dfbdd3aae4d648be97fc8747304a8', 'Y', 'user', null, 'user', '普通用户', '', '', '2019-11-17 16:03:15');

-- ----------------------------
-- Table structure for `sy_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sy_role_user`;
CREATE TABLE `sy_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `status` varchar(10) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_role_user
-- ----------------------------
INSERT INTO `sy_role_user` VALUES ('1', null, '1', '1', null, null, '1', '2019-11-17 14:59:41');
INSERT INTO `sy_role_user` VALUES ('2', 'cdbab81029ed4c949a08800f9bec508e', '1', '2', null, null, '1', '2019-11-17 16:22:06');

-- ----------------------------
-- Table structure for `sy_user`
-- ----------------------------
DROP TABLE IF EXISTS `sy_user`;
CREATE TABLE `sy_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '登录帐号',
  `password` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(50) DEFAULT NULL COMMENT '中文名字',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `status` varchar(10) DEFAULT NULL COMMENT '1 启用 2 不启用',
  `id_number` varchar(20) DEFAULT NULL COMMENT '证件',
  `sex_cd` varchar(10) DEFAULT NULL COMMENT '男Male: M;女Female:F',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `note` varchar(100) DEFAULT NULL,
  `msg_status` varchar(10) DEFAULT NULL COMMENT '短信发送状态',
  `user_id` varchar(64) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  `token_time` timestamp NULL DEFAULT NULL,
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sy_user
-- ----------------------------
INSERT INTO `sy_user` VALUES ('1', 'admin', '6764f5a92b22776ae130bd2bcfab9a7b', '系统管理员', '', '1', '', 'M', null, '系统管理员，请勿删除', null, null, '11', '2019-11-19 14:22:48', null, '2019-11-17 14:22:44');

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
