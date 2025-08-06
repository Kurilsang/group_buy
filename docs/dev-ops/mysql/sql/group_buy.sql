# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 5.6.39)
# 数据库: group_buy_market
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE database if NOT EXISTS `group_buy_market` default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use `group_buy_market`;

# 转储表 crowd_tags
# ------------------------------------------------------------

DROP TABLE IF EXISTS `crowd_tags`;

CREATE TABLE `crowd_tags` (
                              `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                              `tag_id` varchar(32) NOT NULL COMMENT '人群ID',
                              `tag_name` varchar(64) NOT NULL COMMENT '人群名称',
                              `tag_desc` varchar(256) NOT NULL COMMENT '人群描述',
                              `statistics` int(8) NOT NULL COMMENT '人群标签统计量',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uq_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人群标签';

LOCK TABLES `crowd_tags` WRITE;
/*!40000 ALTER TABLE `crowd_tags` DISABLE KEYS */;

INSERT INTO `crowd_tags` (`id`, `tag_id`, `tag_name`, `tag_desc`, `statistics`, `create_time`, `update_time`)
VALUES
    (1,'RQ_KJHKL98UU78H66554GFDV','潜在消费用户','潜在消费用户',33,NOW(),NOW());

/*!40000 ALTER TABLE `crowd_tags` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 crowd_tags_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `crowd_tags_detail`;

CREATE TABLE `crowd_tags_detail` (
                                     `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                     `tag_id` varchar(32) NOT NULL COMMENT '人群ID',
                                     `user_id` varchar(16) NOT NULL COMMENT '用户ID',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uq_tag_user` (`tag_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人群标签明细';

LOCK TABLES `crowd_tags_detail` WRITE;
/*!40000 ALTER TABLE `crowd_tags_detail` DISABLE KEYS */;

INSERT INTO `crowd_tags_detail` (`id`, `tag_id`, `user_id`, `create_time`, `update_time`)
VALUES
    (4,'RQ_KJHKL98UU78H66554GFDV','user001',NOW(),NOW()),
    (5,'RQ_KJHKL98UU78H66554GFDV','user002',NOW(),NOW()),
    (9,'RQ_KJHKL98UU78H66554GFDV','user003',NOW(),NOW()),
    (10,'RQ_KJHKL98UU78H66554GFDV','user004',NOW(),NOW()),
    (11,'RQ_KJHKL98UU78H66554GFDV','user005',NOW(),NOW()),
    (17,'RQ_KJHKL98UU78H66554GFDV','user006',NOW(),NOW()),
    (18,'RQ_KJHKL98UU78H66554GFDV','user007',NOW(),NOW()),
    (19,'RQ_KJHKL98UU78H66554GFDV','user008',NOW(),NOW()),
    (20,'RQ_KJHKL98UU78H66554GFDV','user009',NOW(),NOW()),
    (21,'RQ_KJHKL98UU78H66554GFDV','user010',NOW(),NOW()),
    (22,'RQ_KJHKL98UU78H66554GFDV','user011',NOW(),NOW());

/*!40000 ALTER TABLE `crowd_tags_detail` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 crowd_tags_job
# ------------------------------------------------------------

DROP TABLE IF EXISTS `crowd_tags_job`;

CREATE TABLE `crowd_tags_job` (
                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                  `tag_id` varchar(32) NOT NULL COMMENT '标签ID',
                                  `batch_id` varchar(8) NOT NULL COMMENT '批次ID',
                                  `tag_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '标签类型（参与量、消费金额）',
                                  `tag_rule` varchar(8) NOT NULL COMMENT '标签规则（限定类型 N次）',
                                  `stat_start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计数据，开始时间',
                                  `stat_end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '统计数据，结束时间',
                                  `stat_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '统计状态（0初始、1完成、2异常）',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uq_tag_batch` (`tag_id`,`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人群标签任务';

LOCK TABLES `crowd_tags_job` WRITE;
/*!40000 ALTER TABLE `crowd_tags_job` DISABLE KEYS */;

INSERT INTO `crowd_tags_job` (`id`, `tag_id`, `batch_id`, `tag_type`, `tag_rule`, `stat_start_time`, `stat_end_time`, `stat_status`, `create_time`, `update_time`)
VALUES
    (1,'RQ_KJHKL98UU78H66554GFDV','10001',0,'100',NOW(),NOW(),0,NOW(),NOW());

/*!40000 ALTER TABLE `crowd_tags_job` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 group_buy_activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_activity`;

CREATE TABLE `group_buy_activity` (
                                      `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                      `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                      `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                      `activity_desc` varchar(128) NOT NULL COMMENT '活动描述',
                                      `activity_type` tinyint(2) NOT NULL COMMENT '活动类型',
                                      `state` tinyint(2) NOT NULL DEFAULT '1' COMMENT '活动状态',
                                      `begin_date_time` datetime NOT NULL COMMENT '开始时间',
                                      `end_date_time` datetime NOT NULL COMMENT '结束时间',
                                      `target_count` int(4) NOT NULL DEFAULT '1' COMMENT '拼团人数',
                                      `valid_time` int(4) NOT NULL DEFAULT '15' COMMENT '拼团有效时长（分钟）',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uq_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团活动';

LOCK TABLES `group_buy_activity` WRITE;
/*!40000 ALTER TABLE `group_buy_activity` DISABLE KEYS */;

INSERT INTO `group_buy_activity` (`id`, `activity_id`, `activity_name`, `activity_desc`, `activity_type`, `state`, `begin_date_time`, `end_date_time`, `target_count`, `valid_time`, `create_time`, `update_time`)
VALUES
    (1,100123,'测试活动','25120207',0,1,NOW(),DATE_ADD(NOW(), INTERVAL 30 DAY),1,15,NOW(),NOW());

/*!40000 ALTER TABLE `group_buy_activity` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 group_buy_activity_discount
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_activity_discount`;

CREATE TABLE `group_buy_activity_discount` (
                                               `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                               `activity_desc` varchar(128) NOT NULL COMMENT '活动描述',
                                               `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
                                               `discount_desc` varchar(128) NOT NULL COMMENT '折扣描述',
                                               `discount_type` tinyint(2) NOT NULL COMMENT '折扣类型',
                                               `discount_value` varchar(32) NOT NULL COMMENT '折扣值',
                                               `discount_amount` varchar(32) NOT NULL COMMENT '折扣金额',
                                               `discount_rule` text COMMENT '折扣规则',
                                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团活动折扣';

LOCK TABLES `group_buy_activity_discount` WRITE;
/*!40000 ALTER TABLE `group_buy_activity_discount` DISABLE KEYS */;

INSERT INTO `group_buy_activity_discount` (`id`, `activity_desc`, `activity_name`, `discount_desc`, `discount_type`, `discount_value`, `discount_amount`, `discount_rule`, `create_time`, `update_time`)
VALUES
    (1,'25120207','测试优惠','测试优惠',0,'ZJ','20',NULL,NOW(),NOW());

/*!40000 ALTER TABLE `group_buy_activity_discount` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 group_buy_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_order`;

CREATE TABLE `group_buy_order` (
                                   `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `team_id` varchar(32) NOT NULL COMMENT '拼单组队ID',
                                   `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                   `source` varchar(8) NOT NULL COMMENT '渠道',
                                   `channel` varchar(8) NOT NULL COMMENT '来源',
                                   `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
                                   `deduction_price` decimal(10,2) NOT NULL COMMENT '减免价格',
                                   `pay_price` decimal(10,2) NOT NULL COMMENT '支付价格',
                                   `target_count` int(4) NOT NULL DEFAULT '1' COMMENT '拼团人数',
                                   `complete_count` int(4) NOT NULL DEFAULT '0' COMMENT '完成人数',
                                   `lock_count` int(4) NOT NULL DEFAULT '0' COMMENT '锁定人数',
                                   `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '拼团状态',
                                   `valid_start_time` datetime NOT NULL COMMENT '有效开始时间',
                                   `valid_end_time` datetime NOT NULL COMMENT '有效结束时间',
                                   `notify_type` varchar(16) DEFAULT 'MQ' COMMENT '通知类型（HTTP、MQ）',
                                   `notify_url` varchar(256) DEFAULT NULL COMMENT '通知地址',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uq_team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团订单';

LOCK TABLES `group_buy_order` WRITE;
/*!40000 ALTER TABLE `group_buy_order` DISABLE KEYS */;

INSERT INTO `group_buy_order` (`id`, `team_id`, `activity_id`, `source`, `channel`, `original_price`, `deduction_price`, `pay_price`, `target_count`, `complete_count`, `lock_count`, `status`, `valid_start_time`, `valid_end_time`, `notify_type`, `notify_url`, `create_time`, `update_time`)
VALUES
    (1,'58693013',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,NOW(),DATE_ADD(NOW(), INTERVAL 15 MINUTE),'MQ',NULL,NOW(),NOW()),
    (2,'16341565',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,NOW(),DATE_ADD(NOW(), INTERVAL 15 MINUTE),'HTTP','http://127.0.0.1:8091/api/v1/test/group_buy_notify',NOW(),NOW()),
    (3,'63403622',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,NOW(),DATE_ADD(NOW(), INTERVAL 15 MINUTE),'MQ',NULL,NOW(),NOW());

/*!40000 ALTER TABLE `group_buy_order` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 group_buy_order_list
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_order_list`;

CREATE TABLE `group_buy_order_list` (
                                        `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                        `user_id` varchar(32) NOT NULL COMMENT '用户ID',
                                        `team_id` varchar(32) NOT NULL COMMENT '拼单组队ID',
                                        `order_id` varchar(12) NOT NULL COMMENT '订单ID',
                                        `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                                        `start_time` datetime NOT NULL COMMENT '活动开始时间',
                                        `end_time` datetime NOT NULL COMMENT '活动结束时间',
                                        `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
                                        `source` varchar(8) NOT NULL COMMENT '渠道',
                                        `channel` varchar(8) NOT NULL COMMENT '来源',
                                        `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
                                        `deduction_price` decimal(10,2) NOT NULL COMMENT '减免价格',
                                        `pay_price` decimal(10,2) NOT NULL COMMENT '支付价格',
                                        `trade_order_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '交易订单状态',
                                        `out_trade_no` varchar(64) DEFAULT NULL COMMENT '外部交易单号',
                                        `out_trade_time` datetime DEFAULT NULL COMMENT '外部交易时间',
                                        `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
                                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `uq_user_team` (`user_id`,`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团订单明细';

LOCK TABLES `group_buy_order_list` WRITE;
/*!40000 ALTER TABLE `group_buy_order_list` DISABLE KEYS */;

INSERT INTO `group_buy_order_list` (`id`, `user_id`, `team_id`, `order_id`, `activity_id`, `start_time`, `end_time`, `goods_id`, `source`, `channel`, `original_price`, `deduction_price`, `pay_price`, `trade_order_status`, `out_trade_no`, `out_trade_time`, `order_no`, `create_time`, `update_time`)
VALUES
    (1,'user003','58693013','480088144059',100123,NOW(),DATE_ADD(NOW(), INTERVAL 30 DAY),'9890001','s01','c01',100.00,20.00,80.00,1,'214969043474',NOW(),'100123_user003_1',NOW(),NOW()),
    (2,'user004','16341565','550620893253',100123,NOW(),DATE_ADD(NOW(), INTERVAL 30 DAY),'9890001','s01','c01',100.00,20.00,80.00,1,'539291175688',NOW(),'100123_user004_1',NOW(),NOW()),
    (3,'user005','63403622','221878862945',100123,NOW(),DATE_ADD(NOW(), INTERVAL 30 DAY),'9890001','s01','c01',100.00,20.00,80.00,1,'904941690333',NOW(),'100123_user005_1',NOW(),NOW());

/*!40000 ALTER TABLE `group_buy_order_list` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 notify_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notify_task`;

CREATE TABLE `notify_task` (
                               `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                               `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                               `team_id` varchar(32) NOT NULL COMMENT '拼单组队ID',
                               `notify_type` varchar(16) DEFAULT 'MQ' COMMENT '回调类型（HTTP、MQ）',
                               `notify_mq` varchar(64) DEFAULT NULL COMMENT '回调消息',
                               `notify_url` varchar(256) DEFAULT NULL COMMENT '回调接口',
                               `notify_count` int(4) NOT NULL DEFAULT '0' COMMENT '回调次数',
                               `notify_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '回调状态【0初始、1完成、2重试、3失败】',
                               `parameter_json` text COMMENT '参数对象',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uq_team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回调通知任务';

LOCK TABLES `notify_task` WRITE;
/*!40000 ALTER TABLE `notify_task` DISABLE KEYS */;

INSERT INTO `notify_task` (`id`, `activity_id`, `team_id`, `notify_type`, `notify_mq`, `notify_url`, `notify_count`, `notify_status`, `parameter_json`, `create_time`, `update_time`)
VALUES
    (7,100123,'58693013','MQ','topic.team_success',NULL,1,1,'{\"teamId\":\"58693013\",\"outTradeNoList\":[\"214969043474\"]}',NOW(),NOW()),
    (8,100123,'16341565','HTTP',NULL,'http://127.0.0.1:8091/api/v1/test/group_buy_notify',1,1,'{\"teamId\":\"16341565\",\"outTradeNoList\":[\"539291175688\"]}',NOW(),NOW()),
    (9,100123,'63403622','MQ','topic.team_success',NULL,1,1,'{\"teamId\":\"63403622\",\"outTradeNoList\":[\"904941690333\"]}',NOW(),NOW());

/*!40000 ALTER TABLE `notify_task` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sku_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku_product`;

CREATE TABLE `sku_product` (
                               `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                               `source` varchar(8) NOT NULL COMMENT '渠道',
                               `channel` varchar(8) NOT NULL COMMENT '来源',
                               `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
                               `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uq_source_channel_activity_goods` (`source`,`channel`,`activity_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU';

LOCK TABLES `sku_product` WRITE;
/*!40000 ALTER TABLE `sku_product` DISABLE KEYS */;

INSERT INTO `sku_product` (`id`, `source`, `channel`, `activity_id`, `goods_id`, `create_time`, `update_time`)
VALUES
    (1,'s01','c01',100123,'9890001',NOW(),NOW());

/*!40000 ALTER TABLE `sku_product` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sku_product_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku_product_detail`;

CREATE TABLE `sku_product_detail` (
                                      `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                      `source` varchar(8) NOT NULL COMMENT '渠道',
                                      `channel` varchar(8) NOT NULL COMMENT '来源',
                                      `goods_id` varchar(16) NOT NULL COMMENT '商品ID',
                                      `goods_name` varchar(64) NOT NULL COMMENT '商品名称',
                                      `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uq_source_channel_goods` (`source`,`channel`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU明细';

LOCK TABLES `sku_product_detail` WRITE;
/*!40000 ALTER TABLE `sku_product_detail` DISABLE KEYS */;

INSERT INTO `sku_product_detail` (`id`, `source`, `channel`, `goods_id`, `goods_name`, `original_price`, `create_time`, `update_time`)
VALUES
    (1,'s01','c01','9890001','《手写MyBatis：渐进式源码实践》',100.00,NOW(),NOW());

/*!40000 ALTER TABLE `sku_product_detail` ENABLE KEYS */;
UNLOCK TABLES;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
