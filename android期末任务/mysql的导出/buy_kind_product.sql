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

 Date: 17/06/2024 18:16:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for buy_kind_product
-- ----------------------------
DROP TABLE IF EXISTS `buy_kind_product`;
CREATE TABLE `buy_kind_product`  (
  `product_id` int NULL DEFAULT NULL,
  `order_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `buy_num` int NULL DEFAULT NULL,
  `sum_price` double NULL DEFAULT NULL,
  INDEX `product_kind`(`product_id` ASC) USING BTREE,
  INDEX `order_kind`(`order_id` ASC) USING BTREE,
  CONSTRAINT `order_kind` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `product_kind` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of buy_kind_product
-- ----------------------------
INSERT INTO `buy_kind_product` VALUES (6, '1718619294305', 2, 19.8);
INSERT INTO `buy_kind_product` VALUES (2, '1718619311864', 3, 597);
INSERT INTO `buy_kind_product` VALUES (4, '1718619311864', 3, 59.699999999999996);

SET FOREIGN_KEY_CHECKS = 1;
