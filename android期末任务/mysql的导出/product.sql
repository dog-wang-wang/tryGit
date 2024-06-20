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

 Date: 17/06/2024 18:16:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `picAddress` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `inventory` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '熊猫头全套表情包', '/android_term/product_pic/1.jpg', 0.9, 936);
INSERT INTO `product` VALUES (2, '书包', '/android_term/product_pic/2.jpg', 199, 929);
INSERT INTO `product` VALUES (3, '张学友专辑', '/android_term/product_pic/3.jpg', 999, 979);
INSERT INTO `product` VALUES (4, '只强mod', '/android_term/product_pic/4.jpg', 19.9, 648);
INSERT INTO `product` VALUES (5, '张学友全套表情包', '/android_term/product_pic/5.jpg', 0.9, 964);
INSERT INTO `product` VALUES (6, '东邪西毒', '/android_term/product_pic/6.jpg', 9.9, 871);
INSERT INTO `product` VALUES (7, '复古裱花生日蛋糕', '/android_term/product_pic/7.jpg', 299, 981);
INSERT INTO `product` VALUES (8, '东成西就', '/android_term/product_pic/8.jpg', 9.9, 962);
INSERT INTO `product` VALUES (9, '裤子', '/android_term/product_pic/9.jpg', 199, 1000);
INSERT INTO `product` VALUES (10, '人头马1898特优香槟干邑第三代水晶限量版', '/android_term/product_pic/10.jpg', 4200, 998);
INSERT INTO `product` VALUES (11, 'Martell马爹利XO干邑白兰地', '/android_term/product_pic/11.jpg', 1280, 1000);

SET FOREIGN_KEY_CHECKS = 1;
