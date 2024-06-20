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

 Date: 17/06/2024 18:16:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `users_id` int NULL DEFAULT NULL,
  `create_time` date NULL DEFAULT NULL,
  `sum_price` double NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `productkind`(`users_id` ASC) USING BTREE,
  CONSTRAINT `productkind` FOREIGN KEY (`users_id`) REFERENCES `usermessage` (`userid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1718619294305', 18, '2024-06-17', 19.8);
INSERT INTO `orders` VALUES ('1718619311864', 18, '2024-06-17', 656.7);

SET FOREIGN_KEY_CHECKS = 1;
