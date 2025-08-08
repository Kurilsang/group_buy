# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 5.6.39)
# 数据库: group_buy_market
# 生成时间: 2024-01-01 00:00:00 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# 转储表 activity_user_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity_user_group`;

CREATE TABLE `activity_user_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` varchar(12) NOT NULL COMMENT '活动ID',
  `group_name` varchar(64) NOT NULL COMMENT '用户组名称',
  `group_desc` varchar(128) DEFAULT NULL COMMENT '用户组描述',
  `group_sort` int(8) NOT NULL DEFAULT '0' COMMENT '用户组排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id_group_name` (`activity_id`,`group_name`),
  KEY `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动配置，用户组';

LOCK TABLES `activity_user_group` WRITE;
/*!40000 ALTER TABLE `activity_user_group` DISABLE KEYS */;

INSERT INTO `activity_user_group` (`id`, `activity_id`, `group_name`, `group_desc`, `group_sort`, `create_time`, `update_time`)
VALUES
	(1,'RQ_KJHKL98UU78H66554GFDV','潜在消费用户','潜在消费用户',33,'2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `activity_user_group` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 activity_user_group_relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity_user_group_relation`;

CREATE TABLE `activity_user_group_relation` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` varchar(12) NOT NULL COMMENT '活动ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id_user_id` (`activity_id`,`user_id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动配置，用户组关系表';

LOCK TABLES `activity_user_group_relation` WRITE;
/*!40000 ALTER TABLE `activity_user_group_relation` DISABLE KEYS */;

INSERT INTO `activity_user_group_relation` (`id`, `activity_id`, `user_id`, `create_time`, `update_time`)
VALUES
	(4,'RQ_KJHKL98UU78H66554GFDV','test001','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(5,'RQ_KJHKL98UU78H66554GFDV','test002','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(9,'RQ_KJHKL98UU78H66554GFDV','test003','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(10,'RQ_KJHKL98UU78H66554GFDV','test004','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(11,'RQ_KJHKL98UU78H66554GFDV','test005','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(17,'RQ_KJHKL98UU78H66554GFDV','test006','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(18,'RQ_KJHKL98UU78H66554GFDV','test007','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(19,'RQ_KJHKL98UU78H66554GFDV','test008','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(20,'RQ_KJHKL98UU78H66554GFDV','test009','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(21,'RQ_KJHKL98UU78H66554GFDV','test010','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(22,'RQ_KJHKL98UU78H66554GFDV','test011','2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `activity_user_group_relation` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 group_buy_activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_activity`;

CREATE TABLE `group_buy_activity` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) NOT NULL COMMENT '活动名称',
  `discount_id` varchar(32) NOT NULL COMMENT '优惠券ID',
  `group_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '拼团类型；0-固定人数拼团，1-阶梯拼团',
  `take_limit_count` int(8) NOT NULL DEFAULT '1' COMMENT '活动参与限制次数',
  `target` int(8) NOT NULL DEFAULT '2' COMMENT '拼团目标人数',
  `valid_time` int(8) NOT NULL DEFAULT '15' COMMENT '拼团有效时间；分钟',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '活动状态；1-生效，2-失效，3-完结',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `tag_id` varchar(128) NOT NULL COMMENT '标签ID',
  `tag_scope` varchar(128) NOT NULL COMMENT '标签范围',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团活动配置';

LOCK TABLES `group_buy_activity` WRITE;
/*!40000 ALTER TABLE `group_buy_activity` DISABLE KEYS */;

INSERT INTO `group_buy_activity` (`id`, `activity_id`, `activity_name`, `discount_id`, `group_type`, `take_limit_count`, `target`, `valid_time`, `status`, `start_time`, `end_time`, `tag_id`, `tag_scope`, `create_time`, `update_time`)
VALUES
	(1,100123,'测试活动','25120207',0,1,3,15,1,'2024-01-01 00:00:00','2029-12-31 23:59:59','1','1','2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `group_buy_activity` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 group_buy_activity_count
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_activity_count`;

CREATE TABLE `group_buy_activity_count` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` varchar(12) NOT NULL COMMENT '活动ID',
  `activity_count_id` varchar(12) NOT NULL COMMENT '活动次数编号',
  `total_count` int(8) NOT NULL DEFAULT '0' COMMENT '总次数',
  `day_count` int(8) NOT NULL DEFAULT '0' COMMENT '日次数',
  `month_count` int(8) NOT NULL DEFAULT '0' COMMENT '月次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_activity_id_activity_count_id` (`activity_id`,`activity_count_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动次数配置表';

LOCK TABLES `group_buy_activity_count` WRITE;
/*!40000 ALTER TABLE `group_buy_activity_count` DISABLE KEYS */;

INSERT INTO `group_buy_activity_count` (`id`, `activity_id`, `activity_count_id`, `total_count`, `day_count`, `month_count`, `create_time`, `update_time`)
VALUES
	(1,'RQ_KJHKL98UU78H66554GFDV','10001',0,'100','2024-01-01 00:00:00','2024-01-01 00:00:00',0,'2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `group_buy_activity_count` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 group_buy_activity_discount
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_activity_discount`;

CREATE TABLE `group_buy_activity_discount` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `discount_id` varchar(32) NOT NULL COMMENT '优惠券ID',
  `discount_name` varchar(64) NOT NULL COMMENT '优惠券名称',
  `discount_desc` varchar(128) DEFAULT NULL COMMENT '优惠券描述',
  `discount_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '优惠券类型；1-满减，2-直减，3-折扣，4-n元购',
  `discount_rule` varchar(32) NOT NULL COMMENT '优惠规则',
  `discount_value` varchar(32) NOT NULL COMMENT '优惠金额',
  `discount_quota` varchar(32) DEFAULT NULL COMMENT '优惠门槛',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_discount_id` (`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团活动优惠券配置';

LOCK TABLES `group_buy_activity_discount` WRITE;
/*!40000 ALTER TABLE `group_buy_activity_discount` DISABLE KEYS */;

INSERT INTO `group_buy_activity_discount` (`id`, `discount_id`, `discount_name`, `discount_desc`, `discount_type`, `discount_rule`, `discount_value`, `discount_quota`, `create_time`, `update_time`)
VALUES
	(1,'25120207','测试优惠','测试优惠',0,'ZJ','20',NULL,'2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `group_buy_activity_discount` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 group_buy_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_order`;

CREATE TABLE `group_buy_order` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `source` varchar(16) NOT NULL COMMENT '来源',
  `channel` varchar(16) NOT NULL COMMENT '渠道',
  `total_price` decimal(8,2) NOT NULL COMMENT '商品原价',
  `discount_price` decimal(8,2) NOT NULL COMMENT '优惠价格',
  `pay_price` decimal(8,2) NOT NULL COMMENT '支付价格',
  `current_count` int(8) NOT NULL DEFAULT '1' COMMENT '当前人数',
  `require_count` int(8) NOT NULL DEFAULT '2' COMMENT '成团人数',
  `team_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '拼团状态；1-拼团中，2-拼团成功，3-拼团失败',
  `lock_count` int(8) NOT NULL DEFAULT '0' COMMENT '锁定人数',
  `start_time` datetime NOT NULL COMMENT '开团时间',
  `end_time` datetime NOT NULL COMMENT '结团时间',
  `notify_type` varchar(16) DEFAULT 'MQ' COMMENT '通知类型',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '通知地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_team_id` (`team_id`),
  KEY `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团订单表';

LOCK TABLES `group_buy_order` WRITE;
/*!40000 ALTER TABLE `group_buy_order` DISABLE KEYS */;

INSERT INTO `group_buy_order` (`id`, `team_id`, `activity_id`, `source`, `channel`, `total_price`, `discount_price`, `pay_price`, `current_count`, `require_count`, `team_status`, `lock_count`, `start_time`, `end_time`, `notify_type`, `notify_url`, `create_time`, `update_time`)
VALUES
	(1,'58693013',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,'2024-01-01 00:00:00','2024-01-01 00:00:00','MQ',NULL,'2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(2,'16341565',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,'2024-01-01 00:00:00','2024-01-01 00:00:00','HTTP','http://127.0.0.1:8091/api/v1/test/group_buy_notify','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(3,'63403622',100123,'s01','c01',100.00,20.00,80.00,1,1,1,1,'2024-01-01 00:00:00','2024-01-01 00:00:00','MQ',NULL,'2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `group_buy_order` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 group_buy_user_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_buy_user_order`;

CREATE TABLE `group_buy_user_order` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `source` varchar(16) NOT NULL COMMENT '来源',
  `channel` varchar(16) NOT NULL COMMENT '渠道',
  `total_price` decimal(8,2) NOT NULL COMMENT '商品原价',
  `discount_price` decimal(8,2) NOT NULL COMMENT '优惠价格',
  `pay_price` decimal(8,2) NOT NULL COMMENT '支付价格',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态；0-待支付，1-已支付',
  `out_trade_no` varchar(32) NOT NULL COMMENT '外部交易单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `order_state` varchar(32) NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  UNIQUE KEY `uq_out_trade_no` (`out_trade_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_team_id` (`team_id`),
  KEY `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拼团用户订单表';

LOCK TABLES `group_buy_user_order` WRITE;
/*!40000 ALTER TABLE `group_buy_user_order` DISABLE KEYS */;

INSERT INTO `group_buy_user_order` (`id`, `user_id`, `team_id`, `order_id`, `activity_id`, `start_time`, `end_time`, `goods_id`, `source`, `channel`, `total_price`, `discount_price`, `pay_price`, `pay_status`, `out_trade_no`, `pay_time`, `order_state`, `create_time`, `update_time`)
VALUES
	(1,'test001','58693013','480088144059',100123,'2024-01-01 00:00:00','2029-12-31 23:59:59','9890001','s01','c01',100.00,20.00,80.00,1,'214969043474','2024-01-01 00:00:00','100123_test001_1','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(2,'test002','16341565','550620893253',100123,'2024-01-01 00:00:00','2029-12-31 23:59:59','9890001','s01','c01',100.00,20.00,80.00,1,'539291175688','2024-01-01 00:00:00','100123_test002_1','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(3,'test003','63403622','221878862945',100123,'2024-01-01 00:00:00','2029-12-31 23:59:59','9890001','s01','c01',100.00,20.00,80.00,1,'904941690333','2024-01-01 00:00:00','100123_test003_1','2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `group_buy_user_order` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 notify_task
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notify_task`;

CREATE TABLE `notify_task` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `team_id` varchar(32) NOT NULL COMMENT '团队ID',
  `notify_type` varchar(16) NOT NULL DEFAULT 'MQ' COMMENT '通知类型',
  `topic` varchar(128) NOT NULL COMMENT '消息主题',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '通知地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '通知状态；0-待通知，1-通知完成',
  `notify_count` int(8) NOT NULL DEFAULT '0' COMMENT '通知次数',
  `message` text NOT NULL COMMENT '通知消息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_activity_id` (`activity_id`),
  KEY `idx_team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知任务表';

LOCK TABLES `notify_task` WRITE;
/*!40000 ALTER TABLE `notify_task` DISABLE KEYS */;

INSERT INTO `notify_task` (`id`, `activity_id`, `team_id`, `notify_type`, `topic`, `notify_url`, `status`, `notify_count`, `message`, `create_time`, `update_time`)
VALUES
	(7,100123,'58693013','MQ','topic.team_success',NULL,1,1,'{\"teamId\":\"58693013\",\"outTradeNoList\":[\"214969043474\"]}','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(8,100123,'16341565','HTTP','topic.team_success','http://127.0.0.1:8091/api/v1/test/group_buy_notify',1,1,'{\"teamId\":\"16341565\",\"outTradeNoList\":[\"539291175688\"]}','2024-01-01 00:00:00','2024-01-01 00:00:00'),
	(9,100123,'63403622','MQ','topic.team_success',NULL,1,1,'{\"teamId\":\"63403622\",\"outTradeNoList\":[\"904941690333\"]}','2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `notify_task` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 sku
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku`;

CREATE TABLE `sku` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `source` varchar(16) NOT NULL COMMENT '来源',
  `channel` varchar(16) NOT NULL COMMENT '渠道',
  `activity_id` bigint(12) NOT NULL COMMENT '活动ID',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_source_channel_activity_id_goods_id` (`source`,`channel`,`activity_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU配置';

LOCK TABLES `sku` WRITE;
/*!40000 ALTER TABLE `sku` DISABLE KEYS */;

INSERT INTO `sku` (`id`, `source`, `channel`, `activity_id`, `goods_id`, `create_time`, `update_time`)
VALUES
	(1,'s01','c01',100123,'9890001','2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `sku` ENABLE KEYS */;
UNLOCK TABLES;

# 转储表 sku_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sku_info`;

CREATE TABLE `sku_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `source` varchar(16) NOT NULL COMMENT '来源',
  `channel` varchar(16) NOT NULL COMMENT '渠道',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(128) NOT NULL COMMENT '商品名称',
  `goods_price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_source_channel_goods_id` (`source`,`channel`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品SKU信息';

LOCK TABLES `sku_info` WRITE;
/*!40000 ALTER TABLE `sku_info` DISABLE KEYS */;

INSERT INTO `sku_info` (`id`, `source`, `channel`, `goods_id`, `goods_name`, `goods_price`, `create_time`, `update_time`)
VALUES
	(1,'s01','c01','9890001','测试商品',100.00,'2024-01-01 00:00:00','2024-01-01 00:00:00');

/*!40000 ALTER TABLE `sku_info` ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
