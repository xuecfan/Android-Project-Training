/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : chaofanteaching

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-08-12 14:45:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `user` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) DEFAULT NULL,
  `role` int(255) DEFAULT NULL,
  `photoaddress` varchar(255) DEFAULT 'C:/img/default.png',
  `landingStatus` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('myl', '123', '0', 'C:UsersAdministratoreclipse-workspace.metadata.pluginsorg.eclipse.wst.server.core	mp0wtpwebappsChaoFanTeachingimgmyl.png', '1', '30');
INSERT INTO `account` VALUES ('laoshi', '123', '1', 'C:UsersAdministratoreclipse-workspace.metadata.pluginsorg.eclipse.wst.server.core	mp0wtpwebappsChaoFanTeachingimglaoshi.png', '0', '31');
INSERT INTO `account` VALUES ('xuels', '111', '1', 'C:UsersAdministratoreclipse-workspace.metadata.pluginsorg.eclipse.wst.server.core	mp0wtpwebappsChaoFanTeachingimgxuels.png', '1', '32');
INSERT INTO `account` VALUES ('fxw', 'fxw', '1', 'C:/img/fxw.png', '0', '33');
INSERT INTO `account` VALUES ('xct', 'xcf', '0', 'C:/img/default.png', '0', '34');
INSERT INTO `account` VALUES ('fc', 'fc', '0', 'C:/img/default.png', '1', '35');
INSERT INTO `account` VALUES ('yxt', 'yxt', '1', 'C:UsersAdministratoreclipse-workspace.metadata.pluginsorg.eclipse.wst.server.core	mp0wtpwebappsChaoFanTeachingimgyxt.png', '0', '36');
INSERT INTO `account` VALUES ('000', '123', '1', 'C:/img/000.png', '0', '43');
INSERT INTO `account` VALUES ('test', '000', '0', 'C:/img/default.png', '0', '44');
INSERT INTO `account` VALUES ('999', '99', '0', 'C:/img/999.png', '1', '45');
INSERT INTO `account` VALUES ('xuejz', '111', '0', 'C:/img/xuejz.png', '0', '46');
INSERT INTO `account` VALUES ('xuejz2', '111', '0', 'C:/img/default.png', '1', '47');
INSERT INTO `account` VALUES ('xuejz3', '111', '0', 'C:/img/default.png', '1', '48');
INSERT INTO `account` VALUES ('xuejz4', '111', '0', 'C:/img/default.png', '1', '49');
INSERT INTO `account` VALUES ('xuejz5', '111', '0', 'C:/img/default.png', '0', '50');
INSERT INTO `account` VALUES ('xuejz6', '111', '0', 'C:/img/default.png', '0', '51');
INSERT INTO `account` VALUES ('xuejz7', '111', '0', 'C:/img/default.png', '1', '52');
INSERT INTO `account` VALUES ('xuejz8', '111', '0', 'C:/img/default.png', '0', '53');
INSERT INTO `account` VALUES ('xuejz9', '111', '0', 'C:/img/default.png', '0', '54');
INSERT INTO `account` VALUES ('xuejz10', '111', '0', 'C:/img/default.png', '0', '55');
INSERT INTO `account` VALUES ('xuejz11', '111', '0', 'C:/img/default.png', '0', '56');
INSERT INTO `account` VALUES ('xuels2', '111', '1', 'C:/img/default.png', '0', '57');
INSERT INTO `account` VALUES ('xuejz12', '111', '0', 'C:/img/default.png', '0', '58');
INSERT INTO `account` VALUES ('18588980387', 'c12345', '0', 'C:/img/default.png', '1', '59');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `objuser` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `quality` varchar(255) DEFAULT NULL,
  `messageId` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('50', 'xuejz', 'xuels', '老师教的很棒，孩子成绩有提高，感谢', '5.0', '5.0', '2029203283');
INSERT INTO `comment` VALUES ('51', 'xuels', 'xuejz', '谢谢', '0.0', '0.0', '2029203283');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(255) NOT NULL,
  `user` varchar(255) DEFAULT NULL,
  `objuser` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `length` varchar(255) DEFAULT NULL,
  `locate` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `pay` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `other` varchar(255) DEFAULT NULL,
  `nowtime` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT '待确认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('2029193978', '999', 'xuels', '薛老师', '年级', '科目', '2020-6-3', '30分钟', '北京工商大学嘉华学院附近', '8:00', '33', '63', '', '2020-6-3 19:39:35', '待评价');
INSERT INTO `message` VALUES ('2029203283', 'xuels', 'xuejz', '薛家长', '小学', '美术', '2020-6-4', '1小时', '获取位置', '8:00', '70', '19930511535', '很棒', '2020-6-3 20:33:11', '已完成');

-- ----------------------------
-- Table structure for `mydata`
-- ----------------------------
DROP TABLE IF EXISTS `mydata`;
CREATE TABLE `mydata` (
  `name` varchar(255) DEFAULT '未填写',
  `sex` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT '未填写',
  `locate` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT '未填写',
  `email` varchar(255) DEFAULT '未填写',
  `user` varchar(255) NOT NULL DEFAULT '',
  `authentication` varchar(255) DEFAULT '0',
  `jgid` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mydata
-- ----------------------------
INSERT INTO `mydata` VALUES ('马云龙', '男', '17800314770', '37.91389826837918,114.46715375111516', '河北省石家庄市元氏县青银高速与红旗大街交汇处西南角碧桂园', '1781023404@qq.com', 'myl', '0', '1a0018970ac9da6df50', '17');
INSERT INTO `mydata` VALUES ('小马', '男', '17800314770', '37.91389826837918,114.46715375111516', '河北省石家庄市元氏县青银高速与红旗大街交汇处西南角碧桂园', '1781023404@qq.com', 'laoshi', '0', '140fe1da9e2ce443ce7', '19');
INSERT INTO `mydata` VALUES ('薛老师', '男', '19930511535', '38.005486,114.515233', '河北省石家庄市裕华区建通街道石栾路51号', '1339901870@qq.com', 'xuels', '1', '1507bfd3f743d73bb76', '20');
INSERT INTO `mydata` VALUES (null, null, null, null, null, null, 'fxw', '0', '160a3797c884a9665dd', '21');
INSERT INTO `mydata` VALUES (null, null, null, null, null, null, 'xct', '0', null, '22');
INSERT INTO `mydata` VALUES (null, null, null, null, null, null, 'fc', '0', '160a3797c884a9665dd', '23');
INSERT INTO `mydata` VALUES ('杨锡涛', '男', '123767', '37.91442,114.463559', '', '707840960', 'yxt', '0', '1a0018970a3e7789d30', '24');
INSERT INTO `mydata` VALUES ('一瞬间', '男', '12736', '37.91442,114.463559', '', '就不上班', '000', '0', '1a0018970a3e7789d30', '32');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', '37.91442,114.463559', '', '未填写', 'test', '0', '1a0018970ac9da6df50', '33');
INSERT INTO `mydata` VALUES ('薛球球', '男', '1379', '39.935733469998965,116.73223148271597', '北京工商大学嘉华学院附近', '7074856', '999', '0', '1a0018970a3e7789d30', '34');
INSERT INTO `mydata` VALUES ('薛家长', '男', '1999', '38.005486,114.515233', '河北省石家庄市裕华区建通街道石栾路51号', '133', 'xuejz', '0', '18071adc038c130fe2f', '35');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz2', '0', '1104a89792301f2ed3b', '36');
INSERT INTO `mydata` VALUES ('薛家长3', '男', '19930511535', '38.005486,114.515233', '河北省石家庄市裕华区建通街道石栾路51号', '133', 'xuejz3', '0', '1104a89792301f2ed3b', '37');
INSERT INTO `mydata` VALUES ('薛家长4', '男', '199', '37.421998,-122.084', 'ited StatesCaliforniaMountain View', '133', 'xuejz4', '0', '18071adc038c130fe2f', '38');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz5', '0', '18071adc038c130fe2f', '39');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz6', '0', null, '40');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz7', '0', null, '41');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz8', '0', null, '42');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz9', '0', '18071adc038c130fe2f', '43');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz10', '0', null, '44');
INSERT INTO `mydata` VALUES ('薛家长11', '男', '333', '37.421998,-122.084', 'ited StatesCaliforniaMountain View', '88', 'xuejz11', '0', '18071adc038c130fe2f', '45');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuels2', '0', '18071adc038c130fe2f', '46');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', 'xuejz12', '0', '1104a89792301f2ed3b', '47');
INSERT INTO `mydata` VALUES ('未填写', null, '未填写', null, '未填写', '未填写', '18588980387', '0', '190e35f7e0ce57b0d82', '48');

-- ----------------------------
-- Table structure for `parentsinfo`
-- ----------------------------
DROP TABLE IF EXISTS `parentsinfo`;
CREATE TABLE `parentsinfo` (
  `user` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `week` varchar(255) DEFAULT NULL,
  `hour` varchar(255) DEFAULT NULL,
  `length` varchar(255) DEFAULT NULL,
  `pay` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `requirement` varchar(255) DEFAULT NULL,
  `locate` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `authentication` varchar(255) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parentsinfo
-- ----------------------------
INSERT INTO `parentsinfo` VALUES ('myl', '马爸爸', '男', '初三', '化学', '星期六', '9:30', '2', '50', '17800314770', '男生', '37.914853,114.463643', '8', '0');
INSERT INTO `parentsinfo` VALUES ('999', '去去去', '女', '初三', '语文', '星期一', '8:00', '50', '50', '15512345678', '无', '39.95078,116.793099', '12', '0');
INSERT INTO `parentsinfo` VALUES ('xuejz', '薛家长', '女', '小学', '美术', '周日', '8:00', '2', '70', '13399660885', '希望专业点', '38.005962,114.514768', '25', '0');

-- ----------------------------
-- Table structure for `renzheng`
-- ----------------------------
DROP TABLE IF EXISTS `renzheng`;
CREATE TABLE `renzheng` (
  `studentNo` int(255) DEFAULT NULL,
  `user` varchar(255) NOT NULL DEFAULT '',
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of renzheng
-- ----------------------------

-- ----------------------------
-- Table structure for `star`
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
  `user` varchar(255) NOT NULL DEFAULT '',
  `starname` varchar(255) DEFAULT NULL,
  `staruser` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of star
-- ----------------------------
INSERT INTO `star` VALUES ('xuejz', '马云龙', 'laoshi', '31');
INSERT INTO `star` VALUES ('xuejz', '薛老师', 'xuels', '38');

-- ----------------------------
-- Table structure for `studentsinfo`
-- ----------------------------
DROP TABLE IF EXISTS `studentsinfo`;
CREATE TABLE `studentsinfo` (
  `user` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `university` varchar(255) DEFAULT NULL,
  `college` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `week` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `pay` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `authentication` varchar(255) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentsinfo
-- ----------------------------
INSERT INTO `studentsinfo` VALUES ('laoshi', '马云龙', '男', '河北师范大学', '马克思主义学院', '哲学', '大一', '政治', '星期一', '上午', '有经验', '123', '50', '9', '0');
INSERT INTO `studentsinfo` VALUES ('fxw', '方晓伟', '男', '河北师范大学', '马克思主义学院', '马克思', '大四', '生物', '星期一', '下午', '有经验', '马克思主义学院青年', '30', '11', '0');
INSERT INTO `studentsinfo` VALUES ('yxt', '冯聪', '女', '河北师范大学', '马克思主义学院', '电子竞技', '大一', '物理', '星期一', '上午', '有经验', 'dicker', '1', '13', '0');
INSERT INTO `studentsinfo` VALUES ('xuels', '薛老师', '男', '河北科技大学', '音乐学院', '软件工程', '大一', '数学', '每周日', '上午', '无经验', '呵呵', '33', '16', '1');

-- ----------------------------
-- Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `id` int(255) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
