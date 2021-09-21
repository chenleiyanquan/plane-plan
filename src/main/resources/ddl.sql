/*
 Source Schema         : airplane
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for plane_schedule
-- ----------------------------
DROP TABLE IF EXISTS `plane_schedule`;
CREATE TABLE `plane_schedule` (
                                  `id` int(12) NOT NULL AUTO_INCREMENT,
                                  `callsign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `registration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `origin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '出发站',
                                  `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '到达站',
                                  `airline` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '航空公司',
                                  `data_source` tinyint(2) DEFAULT '0' COMMENT '数据来源：0、原航班数据 1、生成的数据',
                                  `airType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `airTime` time DEFAULT NULL COMMENT '航班时间',
                                  `deptime` time DEFAULT NULL COMMENT '出发时间',
                                  `routing` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                  `arrtime` time DEFAULT NULL COMMENT '到达时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=731 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;