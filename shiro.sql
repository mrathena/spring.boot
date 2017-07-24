/*
Navicat MySQL Data Transfer

Source Server         : [120.77.35.131]
Source Server Version : 50173
Source Host           : 120.77.35.131:3306
Source Database       : shiro

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-07-18 17:55:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT '资源类型',
  `name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `ico` varchar(255) DEFAULT NULL COMMENT '资源图标, 菜单类资源专用',
  `url` varchar(255) DEFAULT NULL COMMENT '资源url, 菜单类资源专用',
  `priority` int(10) DEFAULT NULL COMMENT '显示顺序',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限字符串',
  `description` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `parentId` bigint(10) DEFAULT NULL COMMENT '父编号',
  `available` tinyint(1) DEFAULT '1' COMMENT '是否可用. 1.true.可用, 0.false.不可用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('11', 'navigation', '4', '', '#', '4', '', '', '0', '1');
INSERT INTO `sys_resource` VALUES ('1', 'navigation', '系统管理', 'glyphicon glyphicon-cog', '#', '1', 'navigation:system:*', '菜单-系统管理', '0', '1');
INSERT INTO `sys_resource` VALUES ('2', 'navigation', '资源管理', '', 'sys-resource.jsp', '1', 'navigation:system:resource', '菜单-资源管理', '1', '1');
INSERT INTO `sys_resource` VALUES ('3', 'navigation', '角色管理', '', 'sys-role.jsp', '2', 'navigation:system:role', '菜单-角色管理', '1', '1');
INSERT INTO `sys_resource` VALUES ('4', 'navigation', '用户管理', '', 'sys-user.jsp', '3', 'navigation:system:user', '菜单-用户管理', '1', '1');
INSERT INTO `sys_resource` VALUES ('9', 'navigation', '2', '', '#', '2', '', '', '0', '1');
INSERT INTO `sys_resource` VALUES ('10', 'navigation', '3', '', '#', '3', '', '', '0', '1');
INSERT INTO `sys_resource` VALUES ('13', 'navigation', '2.1', '', '#', '1', '', '', '9', '1');
INSERT INTO `sys_resource` VALUES ('14', 'navigation', '2.1.1', '', '', '1', '', '', '13', '1');
INSERT INTO `sys_resource` VALUES ('15', 'navigation', '2.2', '', '#', '2', '', '', '9', '1');
INSERT INTO `sys_resource` VALUES ('16', 'navigation', '2.1.2', '', '', '2', '', '', '13', '1');
INSERT INTO `sys_resource` VALUES ('17', 'navigation', '2.1.3', '', '', '3', '', '', '13', '1');
INSERT INTO `sys_resource` VALUES ('18', 'navigation', '2.2.1', '', '', '1', '', '', '15', '1');
INSERT INTO `sys_resource` VALUES ('19', 'navigation', '2.2.2', '', '', '2', '', '', '15', '1');
INSERT INTO `sys_resource` VALUES ('20', 'navigation', '3.1', '', '', '1', '', '', '10', '1');
INSERT INTO `sys_resource` VALUES ('21', 'navigation', '4.1', '', '', '1', '', '', '11', '1');
INSERT INTO `sys_resource` VALUES ('23', 'navigation', '3.2', '', '', '2', '', '', '10', '1');
INSERT INTO `sys_resource` VALUES ('24', 'navigation', '4.2', '', '', '2', '', '', '11', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `resourceIds` varchar(1024) DEFAULT NULL COMMENT '授权的资源列表',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用. 1.true.可用, 0.false.不可用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'administrator', '超级管理员', '1,2,3,4', '1');
INSERT INTO `sys_role` VALUES ('16', '4', '测试', '11,21,24', '1');
INSERT INTO `sys_role` VALUES ('10', '2', '测试', '9,13,14,16,17,15,18,19', '1');
INSERT INTO `sys_role` VALUES ('15', '3', '测试', '10,20,23', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '随机码(密码安全)',
  `roleIds` varchar(1024) DEFAULT NULL COMMENT '角色Ids, 用[,]分割',
  `includeIds` varchar(1024) DEFAULT NULL COMMENT '除了角色绑定的授权资源外额外拥有的授权资源列表, 用[,]分割',
  `excludeIds` varchar(1024) DEFAULT NULL COMMENT '最终一定要移除授权的资源列表, 用[,]分割',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否可用. 1.true.可用, 0.false.不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0', 'mrathena', 'a659042e792b5b8e2b59a68f94d47eb9', '7336df0333ea8f1738f7c120733f8905', '1', '', '', '2017-07-07 15:14:01', '1');
INSERT INTO `sys_user` VALUES ('98', '2', '385710e7ccaf25c4b70949a543197f4c', '015a627f32b9b17cb549ec410dcdcc9b', '10', '', '', '2017-07-14 14:47:35', '1');
INSERT INTO `sys_user` VALUES ('99', '3', 'a590bef53c2ea63341bbd78da6913e44', 'b9b6f72282a7d3baaf1dee5934bb2cf3', '15', '', '', '2017-07-17 13:55:50', '1');
INSERT INTO `sys_user` VALUES ('100', '4', 'da237f5c9dbe751d54dd3328e8a46774', '6bc28fa1303cc373196bdc235e5a1eee', '16', '', '', '2017-07-18 14:28:25', '1');
