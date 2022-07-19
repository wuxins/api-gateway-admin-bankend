/*
 Navicat Premium Data Transfer

 Source Server         : admin-java
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : 101.34.231.195:3306
 Source Schema         : admin-java

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 25/02/2022 13:53:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_sys_conf
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_conf`;
CREATE TABLE `base_sys_conf` (
  `cKey` varchar(255) NOT NULL COMMENT '配置键',
  `cValue` text NOT NULL COMMENT '值',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actable_uni_cKey` (`cKey`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='系统配置表';

-- ----------------------------
-- Records of base_sys_conf
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_conf` VALUES ('logKeep', '31', 1, '2021-02-25 14:23:27', '2021-02-25 14:23:27');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_department
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_department`;
CREATE TABLE `base_sys_department` (
  `name` varchar(255) NOT NULL COMMENT '部门名称',
  `parentId` bigint DEFAULT NULL COMMENT '上级部门ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `orderNum` int DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='系统部门';

-- ----------------------------
-- Records of base_sys_department
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_department` VALUES ('COOL', NULL, 1, '2021-02-24 21:17:12', '2021-02-24 21:17:16', 0);
INSERT INTO `base_sys_department` VALUES ('开发', 1, 11, '2021-02-26 14:17:07', '2021-02-26 14:17:07', 0);
INSERT INTO `base_sys_department` VALUES ('测试', 1, 12, '2021-02-26 14:17:12', '2021-02-26 14:17:12', 0);
INSERT INTO `base_sys_department` VALUES ('游客', 1, 13, '2021-02-26 14:29:00', '2021-02-26 14:29:00', 0);
COMMIT;

-- ----------------------------
-- Table structure for base_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_log`;
CREATE TABLE `base_sys_log` (
  `userId` bigint DEFAULT NULL COMMENT '用户ID',
  `action` varchar(1000) DEFAULT NULL COMMENT '行为',
  `ip` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'IP',
  `ipAddr` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'ip地址',
  `params` text CHARACTER SET utf8mb4 COMMENT '参数',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1496776513817715787 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- ----------------------------
-- Records of base_sys_log
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_log` VALUES (1, '/admin/base/sys/log/clear', '127.0.0.1', NULL, '{\"body\":\"\"}', 1496776513817715784, '2022-02-25 13:52:46', '2022-02-25 13:52:46');
INSERT INTO `base_sys_log` VALUES (1, '/admin/base/sys/log/page', '127.0.0.1', NULL, '{\"sort\":\"desc\",\"body\":\"{\\\"page\\\":1,\\\"size\\\":20,\\\"sort\\\":\\\"desc\\\",\\\"order\\\":\\\"createTime\\\"}\",\"size\":20,\"page\":1,\"order\":\"createTime\"}', 1496776513817715785, '2022-02-25 13:52:46', '2022-02-25 13:52:46');
INSERT INTO `base_sys_log` VALUES (1, '/admin/base/sys/log/page', '127.0.0.1', NULL, '{\"sort\":\"desc\",\"body\":\"{\\\"page\\\":1,\\\"size\\\":20,\\\"keyWord\\\":null,\\\"sort\\\":\\\"desc\\\",\\\"order\\\":\\\"createTime\\\"}\",\"keyWord\":null,\"size\":20,\"page\":1,\"order\":\"createTime\"}', 1496776513817715786, '2022-02-25 13:52:48', '2022-02-25 13:52:48');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_menu`;
CREATE TABLE `base_sys_menu` (
  `parentId` bigint DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '菜单名称',
  `perms` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '权限',
  `type` tinyint DEFAULT '0' COMMENT '类型 0：目录 1：菜单 2：按钮',
  `icon` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '图标',
  `orderNum` int DEFAULT '0' COMMENT '排序',
  `viewPath` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '视图地址',
  `keepAlive` bit(1) DEFAULT b'1' COMMENT '路由缓存',
  `isShow` bit(1) DEFAULT b'1' COMMENT '是否显示',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `router` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_parentId` (`parentId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- Records of base_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_menu` VALUES (NULL, '工作台', NULL, 0, 'icon-workbench', 1, NULL, NULL, b'1', 1, '2019-09-11 11:14:44', '2022-02-24 15:40:27', NULL);
INSERT INTO `base_sys_menu` VALUES (NULL, '系统管理', NULL, 0, 'icon-system', 2, NULL, NULL, b'1', 2, '2019-09-11 11:14:47', '2022-02-24 15:40:24', NULL);
INSERT INTO `base_sys_menu` VALUES (27, '菜单列表', NULL, 1, 'icon-menu', 2, 'cool/modules/base/views/menu.vue', b'1', b'1', 8, '1900-01-20 23:19:57', '2021-03-08 22:59:12', '/sys/menu');
INSERT INTO `base_sys_menu` VALUES (8, '新增', 'base:sys:menu:add', 2, NULL, 1, NULL, b'0', b'1', 10, '1900-01-20 00:19:27', '1900-01-20 00:19:27', NULL);
INSERT INTO `base_sys_menu` VALUES (8, '删除', 'base:sys:menu:delete', 2, NULL, 2, NULL, b'0', b'1', 11, '1900-01-20 00:19:51', '1900-01-20 00:19:51', NULL);
INSERT INTO `base_sys_menu` VALUES (8, '修改', 'base:sys:menu:update', 2, NULL, 3, NULL, b'0', b'1', 12, '1900-01-20 00:20:05', '1900-01-20 00:20:05', NULL);
INSERT INTO `base_sys_menu` VALUES (8, '查询', 'base:sys:menu:page,base:sys:menu:list,base:sys:menu:info', 2, NULL, 4, NULL, b'0', b'1', 13, '1900-01-20 00:20:19', '1900-01-20 00:20:19', NULL);
INSERT INTO `base_sys_menu` VALUES (27, '角色列表', NULL, 1, 'icon-common', 3, 'cool/modules/base/views/role.vue', b'1', b'1', 22, '2019-09-12 00:34:01', '2021-03-08 22:59:23', '/sys/role');
INSERT INTO `base_sys_menu` VALUES (22, '新增', 'base:sys:role:add', 2, NULL, 1, NULL, b'0', b'1', 23, '1900-01-20 00:34:23', '1900-01-20 00:34:23', NULL);
INSERT INTO `base_sys_menu` VALUES (22, '删除', 'base:sys:role:delete', 2, NULL, 2, NULL, b'0', b'1', 24, '1900-01-20 00:34:41', '1900-01-20 00:34:41', NULL);
INSERT INTO `base_sys_menu` VALUES (22, '修改', 'base:sys:role:update', 2, NULL, 3, NULL, b'0', b'1', 25, '1900-01-20 00:34:53', '1900-01-20 00:34:53', NULL);
INSERT INTO `base_sys_menu` VALUES (22, '查询', 'base:sys:role:page,base:sys:role:list,base:sys:role:info', 2, NULL, 4, NULL, b'0', b'1', 26, '1900-01-20 00:35:05', '1900-01-20 00:35:05', NULL);
INSERT INTO `base_sys_menu` VALUES (2, '权限管理', NULL, 0, 'icon-auth', 1, NULL, b'0', b'1', 27, '2019-09-12 15:52:44', '2019-09-15 22:11:56', NULL);
INSERT INTO `base_sys_menu` VALUES (105, '请求日志', NULL, 1, 'icon-log', 1, 'cool/modules/base/views/log.vue', b'1', b'1', 29, '2019-09-12 17:35:51', '2021-03-08 23:01:39', '/sys/log');
INSERT INTO `base_sys_menu` VALUES (29, '权限', 'base:sys:log:page,base:sys:log:clear,base:sys:log:getKeep,base:sys:log:setKeep', 2, NULL, 1, NULL, b'0', b'1', 30, '2019-09-12 17:37:03', '2021-03-03 10:16:26', NULL);
INSERT INTO `base_sys_menu` VALUES (45, 'crud 示例', NULL, 1, 'icon-favor', 1, 'cool/modules/demo/views/crud.vue', b'1', b'1', 43, '2019-11-07 14:22:34', '2021-03-08 23:02:51', '/crud');
INSERT INTO `base_sys_menu` VALUES (1, '组件库', NULL, 0, 'icon-common', 2, NULL, b'1', b'1', 45, '2019-11-07 22:36:57', '2019-11-11 15:21:10', '/ui-lib');
INSERT INTO `base_sys_menu` VALUES (NULL, '框架教程', NULL, 0, 'icon-task', 4, NULL, b'1', b'1', 47, '2019-11-08 09:35:08', '2021-02-27 17:16:35', '/tutorial');
INSERT INTO `base_sys_menu` VALUES (47, '文档', NULL, 1, 'icon-log', 0, 'https://admin.cool-js.com', b'1', b'1', 48, '2019-11-08 09:35:53', '2021-03-03 11:03:21', '/tutorial/doc');
INSERT INTO `base_sys_menu` VALUES (45, 'quill 富文本编辑器', NULL, 1, 'icon-favor', 2, 'cool/modules/demo/views/editor-quill.vue', b'1', b'1', 49, '2019-11-09 22:11:13', '2021-03-09 09:50:46', '/editor-quill');
INSERT INTO `base_sys_menu` VALUES (97, '部门列表', 'base:sys:department:list', 2, NULL, 0, NULL, b'1', b'1', 59, '2019-11-18 16:50:27', '2019-11-18 16:50:27', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '新增部门', 'base:sys:department:add', 2, NULL, 0, NULL, b'1', b'1', 60, '2019-11-18 16:50:45', '2019-11-18 16:50:45', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '更新部门', 'base:sys:department:update', 2, NULL, 0, NULL, b'1', b'1', 61, '2019-11-18 16:50:59', '2019-11-18 16:50:59', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '删除部门', 'base:sys:department:delete', 2, NULL, 0, NULL, b'1', b'1', 62, '2019-11-18 16:51:13', '2019-11-18 16:51:13', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '部门排序', 'base:sys:department:order', 2, NULL, 0, NULL, b'1', b'1', 63, '2019-11-18 17:49:35', '2019-11-18 17:49:35', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '用户转移', 'base:sys:user:move', 2, NULL, 0, NULL, b'1', b'1', 65, '2019-11-18 23:59:21', '2019-11-18 23:59:21', NULL);
INSERT INTO `base_sys_menu` VALUES (2, '参数配置', NULL, 0, 'icon-common', 4, NULL, b'1', b'1', 78, '2019-12-10 13:27:56', '2021-02-27 17:08:53', NULL);
INSERT INTO `base_sys_menu` VALUES (78, '参数列表', NULL, 1, 'icon-menu', 0, 'cool/modules/base/views/param.vue', b'1', b'1', 79, '1900-01-20 13:29:33', '2021-03-08 23:01:48', '/sys/param');
INSERT INTO `base_sys_menu` VALUES (79, '新增', 'base:sys:param:add', 2, NULL, 0, NULL, b'1', b'1', 80, '1900-01-20 13:29:50', '1900-01-20 13:29:50', NULL);
INSERT INTO `base_sys_menu` VALUES (79, '修改', 'base:sys:param:info,base:sys:param:update', 2, NULL, 0, NULL, b'1', b'1', 81, '1900-01-20 13:30:10', '1900-01-20 13:30:10', NULL);
INSERT INTO `base_sys_menu` VALUES (79, '删除', 'base:sys:param:delete', 2, NULL, 0, NULL, b'1', b'1', 82, '1900-01-20 13:30:26', '1900-01-20 13:30:26', NULL);
INSERT INTO `base_sys_menu` VALUES (79, '查看', 'base:sys:param:page,base:sys:param:list,base:sys:param:info', 2, NULL, 0, NULL, b'1', b'1', 83, '1900-01-20 13:30:40', '1900-01-20 13:30:40', NULL);
INSERT INTO `base_sys_menu` VALUES (NULL, '通用', NULL, 0, 'icon-radioboxfill', 99, NULL, b'1', b'0', 84, '2020-07-25 16:21:30', '2020-07-25 16:21:30', NULL);
INSERT INTO `base_sys_menu` VALUES (84, '图片上传', 'space:info:page,space:info:list,space:info:info,space:info:add,space:info:delete,space:info:update,space:type:page,space:type:list,space:type:info,space:type:add,space:type:delete,space:type:update', 2, NULL, 1, NULL, b'1', b'1', 85, '2020-07-25 16:22:14', '2021-03-03 10:36:00', NULL);
INSERT INTO `base_sys_menu` VALUES (45, '文件上传', NULL, 1, 'icon-favor', 3, 'cool/modules/demo/views/upload.vue', b'1', b'1', 86, '2020-08-12 09:56:27', '2021-03-08 23:03:03', '/upload');
INSERT INTO `base_sys_menu` VALUES (84, '客服聊天', 'base:app:im:message:read,base:app:im:message:page,base:app:im:session:page,base:app:im:session:list,base:app:im:session:unreadCount,base:app:im:session:delete', 2, NULL, 0, NULL, b'1', b'1', 90, '1900-01-20 10:26:59', '1900-01-20 10:26:59', NULL);
INSERT INTO `base_sys_menu` VALUES (27, '用户列表', NULL, 1, 'icon-user', 0, 'cool/modules/base/views/user.vue', b'1', b'1', 97, '1900-01-20 14:14:02', '2021-03-09 11:03:09', '/sys/user');
INSERT INTO `base_sys_menu` VALUES (97, '新增', 'base:sys:user:add', 2, NULL, 0, NULL, b'1', b'1', 98, '1900-01-20 14:14:14', '1900-01-20 14:14:14', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '删除', 'base:sys:user:delete', 2, NULL, 0, NULL, b'1', b'1', 99, '1900-01-20 14:14:23', '1900-01-20 14:14:23', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '修改', 'base:sys:user:delete,base:sys:user:update', 2, NULL, 0, NULL, b'1', b'1', 100, '1900-01-20 14:14:34', '1900-01-20 14:14:34', NULL);
INSERT INTO `base_sys_menu` VALUES (97, '查询', 'base:sys:user:page,base:sys:user:list,base:sys:user:info', 2, NULL, 0, NULL, b'1', b'1', 101, '2021-01-12 14:14:51', '2021-01-12 14:14:51', NULL);
INSERT INTO `base_sys_menu` VALUES (2, '监控管理', NULL, 0, 'icon-rank', 6, NULL, b'1', b'1', 105, '2021-01-21 10:42:55', '2021-01-21 10:42:55', NULL);
INSERT INTO `base_sys_menu` VALUES (NULL, '任务管理', NULL, 0, 'icon-activity', 5, NULL, b'1', b'1', 117, '2021-03-05 10:58:25', '2021-03-05 10:58:25', NULL);
INSERT INTO `base_sys_menu` VALUES (117, '任务列表', NULL, 1, 'icon-menu', 0, 'cool/modules/task/views/task.vue', b'1', b'1', 118, '2021-03-05 10:59:42', '2021-03-05 10:59:42', '/task');
INSERT INTO `base_sys_menu` VALUES (118, '权限', 'task:info:page,task:info:list,task:info:info,task:info:add,task:info:delete,task:info:update,task:info:stop,task:info:start,task:info:once,task:info:log', 2, NULL, 0, NULL, b'1', b'1', 119, '2021-03-05 11:00:00', '2021-03-05 11:00:00', NULL);
INSERT INTO `base_sys_menu` VALUES (NULL, '字典管理', NULL, 0, 'icon-favor', 3, NULL, b'1', b'1', 143, '2022-07-18 17:05:25', '2022-07-18 17:05:25', NULL);
INSERT INTO `base_sys_menu` VALUES (143, '字典列表', NULL, 1, 'icon-menu', 0, 'modules/dict/views/list.vue', b'1', b'1', 144, '2022-07-18 17:05:47', '2022-07-18 17:05:47', '/dict/list');
INSERT INTO `base_sys_menu` VALUES (144, '新增', 'dict:info:add', 2, NULL, 0, NULL, b'1', b'1', 145, '2022-07-18 17:06:01', '2022-07-18 17:06:01', NULL);
INSERT INTO `base_sys_menu` VALUES (144, '修改', 'dict:info:info,dict:info:update', 2, NULL, 0, NULL, b'1', b'1', 146, '2022-07-18 17:06:11', '2022-07-18 17:06:11', NULL);
INSERT INTO `base_sys_menu` VALUES (144, '删除', 'dict:info:delete', 2, NULL, 0, NULL, b'1', b'1', 147, '2022-07-18 17:06:22', '2022-07-18 17:06:22', NULL);
INSERT INTO `base_sys_menu` VALUES (144, '查看', 'dict:info:list,dict:info:page,dict:info:info,dict:info:data,dict:info:add', 2, NULL, 0, NULL, b'1', b'1', 148, '2022-07-18 17:06:37', '2022-07-18 17:06:37', NULL);
INSERT INTO `base_sys_menu` VALUES (144, '分组', 'dict:type:list,dict:type:page,dict:type:info,dict:type:update,dict:type:delete,dict:type:add', 2, NULL, 0, NULL, b'1', b'1', 149, '2022-07-18 17:06:53', '2022-07-18 17:06:53', NULL);
COMMIT;

-- ----------------------------
-- Table structure for base_sys_param
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_param`;
CREATE TABLE `base_sys_param` (
  `keyName` varchar(255) NOT NULL COMMENT '键',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `data` text COMMENT '数据',
  `dataType` tinyint DEFAULT '0' COMMENT '数据类型 0:字符串 1:数组 2:键值对',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `actable_idx_keyName` (`keyName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='系统参数配置';

-- ----------------------------
-- Records of base_sys_param
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_param` VALUES ('text', '富文本参数', '<p><strong class=\"ql-size-huge\">111xxxxx2222<span class=\"ql-cursor\">﻿﻿﻿</span></strong></p>', 0, NULL, 1, '2021-02-26 13:53:05', '2022-02-17 15:03:56');
INSERT INTO `base_sys_param` VALUES ('json', 'JSON参数', '{\n    code: 111\n}', 0, '', 2, '2021-02-26 13:53:18', '2022-02-20 17:50:35');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_role`;
CREATE TABLE `base_sys_role` (
  `userId` bigint NOT NULL COMMENT '用户ID',
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `label` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色标签',
  `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `relevance` int DEFAULT '1' COMMENT '数据权限是否关联上下级',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `actable_uni_label` (`label`) USING BTREE,
  KEY `actable_idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- Records of base_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_role` VALUES (1, '超管', 'admin', '最高权限的角色', 1, 1, '2021-02-24 21:18:40', '2021-02-24 21:18:40');
INSERT INTO `base_sys_role` VALUES (1, '测试', 'test', '测试备注', 1, 17, '2022-02-19 17:47:03', '2022-02-19 19:37:39');
INSERT INTO `base_sys_role` VALUES (29, '测试昵称2', 'test2', '测试昵称2备注', 1, 18, '2022-02-19 19:38:40', '2022-02-19 19:48:23');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_role_department
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_role_department`;
CREATE TABLE `base_sys_role_department` (
  `roleId` bigint DEFAULT NULL COMMENT '角色ID',
  `departmentId` bigint DEFAULT NULL COMMENT '部门ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COMMENT='系统角色部门';

-- ----------------------------
-- Records of base_sys_role_department
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_role_department` VALUES (17, 11, 34, '2022-02-19 19:37:43', '2022-02-19 19:37:43');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_role_menu`;
CREATE TABLE `base_sys_role_menu` (
  `menuId` bigint DEFAULT NULL COMMENT '菜单',
  `roleId` bigint DEFAULT NULL COMMENT '角色ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=827 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色菜单表';

-- ----------------------------
-- Records of base_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_role_menu` VALUES (2, 17, 756, '2022-02-19 19:37:39', '2022-02-19 19:37:39');
INSERT INTO `base_sys_role_menu` VALUES (27, 17, 757, '2022-02-19 19:37:39', '2022-02-19 19:37:39');
INSERT INTO `base_sys_role_menu` VALUES (97, 17, 758, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (100, 17, 759, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (63, 17, 760, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (65, 17, 761, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (98, 17, 762, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (99, 17, 763, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (101, 17, 764, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (61, 17, 765, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (60, 17, 766, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (59, 17, 767, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (62, 17, 768, '2022-02-19 19:37:40', '2022-02-19 19:37:40');
INSERT INTO `base_sys_role_menu` VALUES (8, 17, 769, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (10, 17, 770, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (11, 17, 771, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (12, 17, 772, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (13, 17, 773, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (22, 17, 774, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (23, 17, 775, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (24, 17, 776, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (25, 17, 777, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (26, 17, 778, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (78, 17, 779, '2022-02-19 19:37:41', '2022-02-19 19:37:41');
INSERT INTO `base_sys_role_menu` VALUES (79, 17, 780, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (80, 17, 781, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (81, 17, 782, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (82, 17, 783, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (83, 17, 784, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (105, 17, 785, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (29, 17, 786, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (30, 17, 787, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (117, 17, 788, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (118, 17, 789, '2022-02-19 19:37:42', '2022-02-19 19:37:42');
INSERT INTO `base_sys_role_menu` VALUES (119, 17, 790, '2022-02-19 19:37:43', '2022-02-19 19:37:43');
INSERT INTO `base_sys_role_menu` VALUES (27, 18, 798, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (97, 18, 799, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (100, 18, 800, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (63, 18, 801, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (65, 18, 802, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (98, 18, 803, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (99, 18, 804, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (101, 18, 805, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (61, 18, 806, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (60, 18, 807, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (59, 18, 808, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (62, 18, 809, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (8, 18, 810, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (10, 18, 811, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (11, 18, 812, '2022-02-19 19:48:24', '2022-02-19 19:48:24');
INSERT INTO `base_sys_role_menu` VALUES (12, 18, 813, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (13, 18, 814, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (22, 18, 815, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (23, 18, 816, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (24, 18, 817, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (25, 18, 818, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (26, 18, 819, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (78, 18, 820, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (79, 18, 821, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (80, 18, 822, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (81, 18, 823, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (82, 18, 824, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (83, 18, 825, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
INSERT INTO `base_sys_role_menu` VALUES (2, 18, 826, '2022-02-19 19:48:25', '2022-02-19 19:48:25');
COMMIT;

-- ----------------------------
-- Table structure for base_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_user`;
CREATE TABLE `base_sys_user` (
  `departmentId` bigint DEFAULT NULL COMMENT '部门ID',
  `name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '姓名',
  `username` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `passwordV` int DEFAULT '1' COMMENT '密码版本',
  `nickName` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '昵称',
  `headImg` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `status` tinyint DEFAULT '1' COMMENT '状态 0:禁用 1：启用',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `userId` bigint NOT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `actable_uni_username` (`username`) USING BTREE,
  KEY `actable_idx_departmentId` (`departmentId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- Records of base_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_user` VALUES (1, '超级管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 3, '管理员', 'https://cool-admin-pro.oss-cn-shanghai.aliyuncs.com/app/c8128c24-d0e9-4e07-9c0d-6f65446e105b.png', '18000000000', 'team@cool-js.com', '拥有最高权限的用户', 1, 1, '2021-02-24 21:16:42', '2022-02-20 18:02:15', 1);
INSERT INTO `base_sys_user` VALUES (11, '测试', 'test', 'e10adc3949ba59abbe56e057f20f883e', 2, '测试昵称', NULL, '', NULL, '', 1, 29, '2022-02-19 17:49:10', '2022-02-24 15:40:12', 1);
COMMIT;

-- ----------------------------
-- Table structure for base_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `base_sys_user_role`;
CREATE TABLE `base_sys_user_role` (
  `userId` bigint DEFAULT NULL COMMENT '用户ID',
  `roleId` bigint DEFAULT NULL COMMENT '角色ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1496751799426719747 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色表';

-- ----------------------------
-- Records of base_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `base_sys_user_role` VALUES (1, 1, 1, '2021-02-24 22:03:12', '2021-02-24 22:03:12');
INSERT INTO `base_sys_user_role` VALUES (29, 17, 1496751799426719746, '2022-02-24 15:40:12', '2022-02-24 15:40:12');
COMMIT;

-- ----------------------------
-- Table structure for demo_goods_entity
-- ----------------------------
DROP TABLE IF EXISTS `demo_goods_entity`;
CREATE TABLE `demo_goods_entity` (
  `title` varchar(255) NOT NULL COMMENT '标题',
  `price` double(10,2) NOT NULL COMMENT '价格',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='商品demo';

-- ----------------------------
-- Records of demo_goods_entity
-- ----------------------------
BEGIN;
INSERT INTO `demo_goods_entity` VALUES ('11', 22.00, 1, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for space_info
-- ----------------------------
DROP TABLE IF EXISTS `space_info`;
CREATE TABLE `space_info` (
  `url` varchar(255) NOT NULL COMMENT '地址',
  `type` varchar(255) NOT NULL COMMENT '类型',
  `classifyId` tinyint DEFAULT NULL COMMENT '分类ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='文件空间信息';

-- ----------------------------
-- Table structure for space_type
-- ----------------------------
DROP TABLE IF EXISTS `space_type`;
CREATE TABLE `space_type` (
  `name` varchar(255) NOT NULL COMMENT '类别名称',
  `parentId` tinyint DEFAULT NULL COMMENT '父分类ID',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='图片空间信息分类';

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
  `name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `jobId` varchar(255) DEFAULT NULL COMMENT '任务ID',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:停止 1：运行',
  `service` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT '服务实例名称',
  `taskType` int DEFAULT '0' COMMENT '状态 0:cron 1：时间间隔',
  `data` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '任务数据',
  `remark` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注',
  `cron` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'cron',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `type` tinyint DEFAULT '0' COMMENT '状态 0:系统 1：用户',
  `nextRunTime` datetime DEFAULT NULL COMMENT '下一次执行时间',
  `startDate` datetime DEFAULT NULL COMMENT '开始时间',
  `endDate` datetime DEFAULT NULL COMMENT '结束时间',
  `every` int DEFAULT NULL COMMENT '每间隔多少毫秒执行一次 如果cron设置了 这项设置就无效',
  `repeatcount` int DEFAULT NULL COMMENT '最大执行次数 不传为无限次',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='任务信息';

-- ----------------------------
-- Records of task_info
-- ----------------------------
BEGIN;
INSERT INTO `task_info` VALUES ('定时清理日志', '11', 1, 'baseSysLogServiceImpl.autoClear()', 0, NULL, '每天1点执行', '0 0 1 * * ?', 11, '2022-02-25 09:21:14', '2022-02-25 13:50:37', 0, '2022-02-26 01:00:00', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for task_log
-- ----------------------------
DROP TABLE IF EXISTS `task_log`;
CREATE TABLE `task_log` (
  `taskId` bigint NOT NULL COMMENT '任务ID',
  `status` int DEFAULT '0' COMMENT '状态 0：失败 1：成功',
  `detail` text CHARACTER SET utf8mb4 COMMENT '详情',
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `actable_idx_taskId` (`taskId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1496751203160268970 DEFAULT CHARSET=utf8mb4 COMMENT='任务日志';

-- ----------------------------
-- Records of task_log
-- ----------------------------
BEGIN;
INSERT INTO `task_log` VALUES (11, 1, '任务执行完毕，任务ID：11  总共耗时：89毫秒', 1496751203160268969, '2022-02-25 09:22:19', '2022-02-25 09:22:19');
COMMIT;


DROP TABLE IF EXISTS `dict_info`;
CREATE TABLE `dict_info` (
                             `typeId` bigint NOT NULL COMMENT '类型ID',
                             `parentId` bigint DEFAULT NULL COMMENT '父ID',
                             `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                             `orderNum` int DEFAULT '0' COMMENT '排序',
                             `remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                             `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典信息';

-- ----------------------------
-- Records of dict_info
-- ----------------------------
BEGIN;
INSERT INTO `dict_info` VALUES (1, NULL, '鞋子', 1, NULL, 1, '2022-07-18 16:10:31', '2022-07-18 16:10:31');
INSERT INTO `dict_info` VALUES (1, NULL, '衣服', 1, NULL, 2, '2022-07-18 16:10:38', '2022-07-18 16:10:38');
INSERT INTO `dict_info` VALUES (1, NULL, '裤子', 1, NULL, 3, '2022-07-18 16:10:43', '2022-07-18 16:10:43');
INSERT INTO `dict_info` VALUES (2, NULL, '闪酷', 1, NULL, 4, '2022-07-18 16:11:16', '2022-07-18 16:11:16');
INSERT INTO `dict_info` VALUES (2, 4, 'cool-admin', 1, NULL, 5, '2022-07-18 16:11:24', '2022-07-18 16:11:24');
INSERT INTO `dict_info` VALUES (2, 4, 'cool-uni', 1, NULL, 6, '2022-07-18 16:11:31', '2022-07-18 16:11:31');
COMMIT;

-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
                             `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                             `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                             `keyName` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型';

-- ----------------------------
-- Records of dict_type
-- ----------------------------
BEGIN;
INSERT INTO `dict_type` VALUES ('商品类别', 1, NULL, '2022-07-18 16:09:31', 'goodsType');
INSERT INTO `dict_type` VALUES ('品牌', 2, '2022-07-18 16:11:09', '2022-07-18 16:11:09', 'brand');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
