/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : ssmproject

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 12/06/2021 17:40:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dept_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES (1, '大江山');
INSERT INTO `dept` VALUES (2, '离岛');
INSERT INTO `dept` VALUES (3, '荒川');
INSERT INTO `dept` VALUES (4, '七角山');
INSERT INTO `dept` VALUES (5, '逢魔之原');
INSERT INTO `dept` VALUES (6, '冥界');
INSERT INTO `dept` VALUES (7, '源氏');
INSERT INTO `dept` VALUES (8, '黑夜山');
INSERT INTO `dept` VALUES (9, '铃鹿山');

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deptId` int NULL DEFAULT NULL,
  `info` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `emp_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (1, '酒吞童子', '男', 1, 'hahah');
INSERT INTO `emp` VALUES (2, '茨木童子', '男', 1, NULL);
INSERT INTO `emp` VALUES (3, '鬼切', '男', 1, NULL);
INSERT INTO `emp` VALUES (4, '星熊童子', '男', 1, NULL);
INSERT INTO `emp` VALUES (6, '不知火', '女', 2, NULL);
INSERT INTO `emp` VALUES (7, '骁浪荒川之主', '男', 3, NULL);
INSERT INTO `emp` VALUES (8, '荒川之主', '男', 3, NULL);
INSERT INTO `emp` VALUES (9, '聆海金鱼姬', '女', 3, NULL);
INSERT INTO `emp` VALUES (10, '金鱼姬', '女', 3, NULL);
INSERT INTO `emp` VALUES (11, '惠比寿', '男', 3, NULL);
INSERT INTO `emp` VALUES (12, '鲤鱼精', '女', 3, NULL);
INSERT INTO `emp` VALUES (13, '河童', '男', 3, NULL);
INSERT INTO `emp` VALUES (14, '椒图', '女', 3, NULL);
INSERT INTO `emp` VALUES (15, '苍风一目连', '男', 4, NULL);
INSERT INTO `emp` VALUES (16, '一目连', '男', 4, NULL);
INSERT INTO `emp` VALUES (17, '初翎山风', '男', 4, NULL);
INSERT INTO `emp` VALUES (18, '山风', '男', 4, NULL);
INSERT INTO `emp` VALUES (19, '虫师', '男', 4, NULL);
INSERT INTO `emp` VALUES (20, '古笼火', '女', 4, NULL);
INSERT INTO `emp` VALUES (21, '熏', '女', 4, NULL);
INSERT INTO `emp` VALUES (22, '小鹿男', '男', 4, NULL);
INSERT INTO `emp` VALUES (23, '烬天玉藻前', '男', 5, NULL);
INSERT INTO `emp` VALUES (24, '玉藻前', '男', 5, NULL);
INSERT INTO `emp` VALUES (25, '阎魔', '女', 6, NULL);
INSERT INTO `emp` VALUES (26, '鬼使黑', '男', 6, NULL);
INSERT INTO `emp` VALUES (27, '鬼使白', '男', 6, NULL);
INSERT INTO `emp` VALUES (28, '黑童子', '男', 6, NULL);
INSERT INTO `emp` VALUES (29, '白童子', '男', 6, NULL);
INSERT INTO `emp` VALUES (30, '彼岸花', '女', 6, NULL);
INSERT INTO `emp` VALUES (31, '夜溟彼岸花', '女', 6, NULL);
INSERT INTO `emp` VALUES (32, '天剑韧心鬼切', '男', 7, NULL);
INSERT INTO `emp` VALUES (33, '鬼切', '男', 7, NULL);
INSERT INTO `emp` VALUES (34, '烟烟罗', '女', 8, NULL);
INSERT INTO `emp` VALUES (35, '食发鬼', '男', 8, NULL);
INSERT INTO `emp` VALUES (36, '麓铭大岳丸', '男', 9, NULL);
INSERT INTO `emp` VALUES (37, '大岳丸', '男', 9, NULL);
INSERT INTO `emp` VALUES (38, '铃鹿御前', '女', 9, NULL);
INSERT INTO `emp` VALUES (39, '蝎女', '女', 9, NULL);
INSERT INTO `emp` VALUES (40, '久次良', '男', 9, NULL);
INSERT INTO `emp` VALUES (41, '蟹姬', '女', 9, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `stat` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', 0);
INSERT INTO `user` VALUES (2, '#admin', 'admin', 1);

SET FOREIGN_KEY_CHECKS = 1;
