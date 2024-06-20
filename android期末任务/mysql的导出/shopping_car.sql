/*
 Navicat Premium Data Transfer

 Source Server         : 123.249.14.197
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36-0ubuntu0.22.04.1)
 Source Host           : 123.249.14.197:3306
 Source Schema         : android_termend

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 17/06/2024 18:16:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for shopping_car
-- ----------------------------
DROP TABLE IF EXISTS `shopping_car`;
CREATE TABLE `shopping_car`  (
  `userid` int NULL DEFAULT NULL,
  `productid` int NULL DEFAULT NULL,
  `num` int NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  INDEX `car_user`(`userid` ASC) USING BTREE,
  INDEX `car_product`(`productid` ASC) USING BTREE,
  CONSTRAINT `car_product` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `car_user` FOREIGN KEY (`userid`) REFERENCES `usermessage` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_car
-- ----------------------------
INSERT INTO `shopping_car` VALUES (18, 4, 5, 99.5);
INSERT INTO `shopping_car` VALUES (18, 6, 5, 49.5);

SET FOREIGN_KEY_CHECKS = 1;
