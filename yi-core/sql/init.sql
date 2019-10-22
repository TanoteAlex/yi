/*
Navicat MySQL Data Transfer

Source Server         : 壹壹-阿里云
Source Server Version : 50722
Source Host           : 47.107.68.47:3306
Source Database       : yi_test

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-10-24 13:37:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `ORDER_QUANTITY` int(11) DEFAULT NULL COMMENT '成交单数',
  `CONSUME_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '消费金额',
  `BALANCE` decimal(15,2) DEFAULT NULL COMMENT '账户余额',
  `FREEZE_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '冻结金额',
  `INTEGRAL` int(11) DEFAULT NULL COMMENT '积分',
  `RESIDUAL_INTEGRAL` int(11) DEFAULT NULL COMMENT '剩余积分',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_7` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_7` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员账户表';

-- ----------------------------
-- Table structure for account_record
-- ----------------------------
DROP TABLE IF EXISTS `account_record`;
CREATE TABLE `account_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `OPERATE_TYPE` tinyint(1) DEFAULT NULL COMMENT '操作类型（1收入2支出）',
  `SERIAL_NO` varchar(16) DEFAULT NULL COMMENT '流水号',
  `TRADE_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '交易金额',
  `BALANCE` decimal(15,2) DEFAULT NULL COMMENT '账户余额',
  `TRADE_TYPE` tinyint(1) DEFAULT NULL COMMENT '交易类型（1佣金转入2在线支付3提现4小区提成）',
  `TRADE_MODE` tinyint(1) DEFAULT NULL COMMENT '交易方式（1店铺佣金2余额）',
  `TRADE_TIME` datetime DEFAULT NULL COMMENT '交易时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_8` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_8` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户记录表';

-- ----------------------------
-- Table structure for advertisement
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '广告位ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `POSITION_ID` int(11) DEFAULT NULL COMMENT '位置ID',
  `TITLE` varchar(255) DEFAULT NULL COMMENT '广告位标题',
  `CONTENT` text COMMENT '内容',
  `IMG_PATH` varchar(255) NOT NULL COMMENT '广告位图片路径',
  `URL` varchar(255) DEFAULT NULL COMMENT '广告位链接',
  `LINK_TYPE` tinyint(4) DEFAULT NULL COMMENT '链接类型（1商品2文章）',
  `LINK_ID` int(11) DEFAULT NULL COMMENT '链接ID',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `POSITION_ID` (`POSITION_ID`),
  CONSTRAINT `advertisement_ibfk_1` FOREIGN KEY (`POSITION_ID`) REFERENCES `position` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告位表';

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(127) NOT NULL COMMENT '文章标题',
  `SUBTITLE` varchar(127) DEFAULT NULL COMMENT '文章副标题',
  `AUTHOR` varchar(127) DEFAULT NULL COMMENT '作者',
  `CONTENT` text COMMENT '文章内容',
  `IMG_PATH` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `URL` varchar(255) DEFAULT NULL COMMENT '商品链接',
  `ARTICLE_TYPE` tinyint(4) DEFAULT NULL COMMENT '文章类型（1专题，2主题）',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `COLLECTION_QUANTITY` int(11) DEFAULT '0' COMMENT '收藏量',
  `READ_QUANTITY` int(11) DEFAULT '0' COMMENT '阅读量',
  `COMMENT_QUANTITY` int(11) DEFAULT '0' COMMENT '评论量',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `ARTICLE_ID` int(11) NOT NULL COMMENT '文章（article表ID）',
  `COMMENTATOR` varchar(127) NOT NULL COMMENT '评论人',
  `COMMENT_CONTENT` varchar(255) NOT NULL COMMENT '评论内容',
  `COMMENT_TIME` datetime NOT NULL COMMENT '评论时间',
  `REPLY_CONTENT` varchar(255) DEFAULT NULL COMMENT '回复内容',
  `REPLY_TIME` datetime DEFAULT NULL COMMENT '回复时间',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_article_id_1` (`ARTICLE_ID`) USING BTREE,
  CONSTRAINT `fk_article_id_1` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `article` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章评论表';

-- ----------------------------
-- Table structure for article_commodity
-- ----------------------------
DROP TABLE IF EXISTS `article_commodity`;
CREATE TABLE `article_commodity` (
  `ARTICLE_ID` int(11) NOT NULL COMMENT '文章（article表ID）',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  PRIMARY KEY (`ARTICLE_ID`,`COMMODITY_ID`) USING BTREE,
  KEY `fk_article_id_2` (`ARTICLE_ID`) USING BTREE,
  KEY `fk_commodity_id_5` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_article_id_2` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `article` (`ID`),
  CONSTRAINT `fk_commodity_id_5` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章商品表';

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `ATTACH_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件id',
  `FS_GUID` varchar(64) DEFAULT NULL COMMENT '文件存储系统标识',
  `OBJECT_TYPE` varchar(64) DEFAULT NULL COMMENT '业务对象类型',
  `FILE_PATH` varchar(255) DEFAULT NULL COMMENT '访问路径',
  `OBJECT_ID` int(11) DEFAULT NULL COMMENT '业务对象id',
  `OBJECT_PATH` varchar(255) DEFAULT NULL COMMENT '所属业务对象路径',
  `FILE_NAME` varchar(255) DEFAULT NULL COMMENT '附件名称',
  `FILE_EXT` varchar(64) DEFAULT NULL COMMENT '扩展文件名',
  `FILE_TYPE` varchar(64) DEFAULT NULL COMMENT '文件类型',
  `FILE_SIZE` decimal(11,0) DEFAULT NULL COMMENT '文件大小',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '上传时间',
  `USER_ID` int(11) DEFAULT NULL COMMENT '上传人id',
  `USER_NAME` varchar(64) DEFAULT NULL COMMENT '长传人',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ATTACH_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Table structure for attribute
-- ----------------------------
DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `ATTRIBUTE_GROUP_ID` int(11) NOT NULL COMMENT '属性组（attribute_group表ID）',
  `ATTR_NAME` varchar(32) DEFAULT NULL COMMENT '属性名',
  `ATTR_VALUE` varchar(127) NOT NULL COMMENT '属性值',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_attribute_group_id_2` (`ATTRIBUTE_GROUP_ID`) USING BTREE,
  CONSTRAINT `fk_attribute_group_id_2` FOREIGN KEY (`ATTRIBUTE_GROUP_ID`) REFERENCES `attribute_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性表';

-- ----------------------------
-- Table structure for attribute_group
-- ----------------------------
DROP TABLE IF EXISTS `attribute_group`;
CREATE TABLE `attribute_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性组ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `GROUP_NO` varchar(16) DEFAULT NULL COMMENT '编码',
  `GROUP_NAME` varchar(64) NOT NULL COMMENT '名称',
  `IMG_PATH` varchar(127) DEFAULT NULL COMMENT '图片',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_commodity_id_30` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_30` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性组表';

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'banner表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(127) NOT NULL COMMENT '标题',
  `CONTENT` text COMMENT '内容',
  `BANNER_TYPE` tinyint(4) DEFAULT '1' COMMENT '轮播图类型（1商品列表，2商品详情，3爱生活文章）',
  `IMG_PATH` varchar(255) NOT NULL COMMENT '图片路径',
  `URL` varchar(255) NOT NULL COMMENT '链接路径',
  `SORT` tinyint(2) DEFAULT NULL COMMENT '排序',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='banner表';

-- ----------------------------
-- Table structure for basic_info
-- ----------------------------
DROP TABLE IF EXISTS `basic_info`;
CREATE TABLE `basic_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '基础信息ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `LOGO_URL` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `COMPANY_NAME` varchar(127) DEFAULT NULL COMMENT '公司名称',
  `COMPANY_ADDR` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `COMPANY_TEL` varchar(16) DEFAULT NULL COMMENT '公司电话',
  `COMPANY_MOBILE` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `COMPANY_MAIL` varchar(32) DEFAULT NULL COMMENT '公司邮箱',
  `SETUP_TIME` datetime DEFAULT NULL COMMENT '成立时间',
  `CONTENT_PROFILE` text COMMENT '公司简介',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础信息表';

-- ----------------------------
-- Table structure for basic_rule
-- ----------------------------
DROP TABLE IF EXISTS `basic_rule`;
CREATE TABLE `basic_rule` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '基础规则ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(127) NOT NULL COMMENT '规则标题',
  `CONTENT` text COMMENT '规则内容',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础规则表';

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `BRAND_NO` varchar(16) NOT NULL COMMENT '编码',
  `CN_NAME` varchar(32) NOT NULL COMMENT '品牌名',
  `EN_NAME` varchar(32) DEFAULT NULL COMMENT '英文名',
  `IMG_PATH` varchar(255) NOT NULL COMMENT '图片路径',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌表';

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `PRODUCT_NAME` varchar(255) DEFAULT NULL COMMENT '商品名称-冗余',
  `PRODUCT_SHORT_NAME` varchar(255) DEFAULT NULL COMMENT '商品简称-冗余',
  `QUANTITY` int(11) NOT NULL DEFAULT '0' COMMENT '购买数量',
  `PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '商品价格',
  `DISCOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '优惠金额',
  `DISCOUNT_INFO` varchar(255) DEFAULT NULL COMMENT '优惠信息',
  `IMG_PATH` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `STATE` tinyint(4) DEFAULT '0' COMMENT '状态（0有效1失效）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_66` (`MEMBER_ID`) USING BTREE,
  KEY `fk_product_id_66` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_66` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_product_id_66` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '上级分类ID',
  `CATEGORY_NO` varchar(16) DEFAULT NULL COMMENT '分类编码',
  `CATEGORY_NAME` varchar(32) NOT NULL COMMENT '分类名称',
  `IMG_PATH` varchar(127) DEFAULT NULL COMMENT '图片路径',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_parent_id_3` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `fk_parent_id_3` FOREIGN KEY (`PARENT_ID`) REFERENCES `category` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品（commodity表ID）',
  `COMMODITY_NAME` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `NICKNAME` varchar(32) DEFAULT NULL COMMENT '会员昵称',
  `SERIAL_NO` varchar(16) DEFAULT NULL COMMENT '编号',
  `COMMENT_STAR` tinyint(4) DEFAULT NULL COMMENT '评价星级',
  `COMMENT_CONTENT` varchar(256) DEFAULT NULL COMMENT '评价内容',
  `REPLY_CONTENT` varchar(256) DEFAULT NULL COMMENT '回复内容',
  `DISPLAY` tinyint(4) DEFAULT '1' COMMENT '是否显示（0不显示1显示）',
  `IMG_PATH` varchar(511) DEFAULT NULL COMMENT '评论商品图片',
  `COMMENT_TIME` datetime DEFAULT NULL COMMENT '评价时间',
  `REPLY_TIME` datetime DEFAULT NULL COMMENT '回复时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_commodity_id_52` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_member_id_33` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_52` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_member_id_33` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='商品评价表';

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COMMODITY_NO` varchar(32) DEFAULT NULL COMMENT '商品编码',
  `COMMODITY_NAME` varchar(127) NOT NULL COMMENT '商品名称',
  `COMMODITY_SHORT_NAME` varchar(127) DEFAULT NULL COMMENT '商品简称',
  `IMG_PATH` varchar(511) DEFAULT NULL COMMENT '商品图片',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `ORIGINAL_PRICE` decimal(15,2) DEFAULT NULL COMMENT '原价',
  `CURRENT_PRICE` decimal(15,2) DEFAULT NULL COMMENT '现价',
  `MEMBER_PRICE` decimal(15,2) DEFAULT NULL COMMENT '会员价',
  `VIP_PRICE` decimal(15,2) DEFAULT NULL COMMENT 'VIP价',
  `DISCOUNT_INFO` varchar(255) DEFAULT NULL COMMENT '优惠信息',
  `DISTRIBUTION` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否参与分销(0参加1不参加)',
  `COMMISSION_RATE` decimal(15,2) DEFAULT NULL COMMENT '佣金比例',
  `INTEGRAL_RATE` decimal(15,2) DEFAULT NULL COMMENT '积分比例',
  `FREIGHT_SET` tinyint(4) DEFAULT '1' COMMENT '运费设置（1统一运费2运费模板）',
  `UNIFIED_FREIGHT` decimal(15,2) DEFAULT '0.00' COMMENT '统一运费',
  `FREIGHT_TEMPLATE_ID` int(11) DEFAULT NULL COMMENT '运费模板（freight_template表ID）',
  `STOCK_SET` tinyint(4) DEFAULT '1' COMMENT '库存设置（1下单减库存2支付减库存）',
  `VOLUME` decimal(15,2) DEFAULT NULL COMMENT '体积',
  `WEIGHT` decimal(15,2) DEFAULT NULL COMMENT '重量',
  `SHELF` tinyint(4) DEFAULT '1' COMMENT '是否上架（1立即上架2放入仓库）',
  `DESCRIPTION` text COMMENT '商品介绍',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `STATE` tinyint(4) DEFAULT '1' COMMENT '商品上架审批（1、申请上架  2、同意上架  3、拒绝上架）',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_supplier_id_1` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_1` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for commodity_category
-- ----------------------------
DROP TABLE IF EXISTS `commodity_category`;
CREATE TABLE `commodity_category` (
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `CATEGORY_ID` int(11) NOT NULL COMMENT '分类（category表ID）',
  PRIMARY KEY (`COMMODITY_ID`,`CATEGORY_ID`) USING BTREE,
  KEY `fk_commodity_id_3` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_category_id_1` (`CATEGORY_ID`) USING BTREE,
  CONSTRAINT `fk_category_id_1` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`),
  CONSTRAINT `fk_commodity_id_3` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- ----------------------------
-- Table structure for commodity_region
-- ----------------------------
DROP TABLE IF EXISTS `commodity_region`;
CREATE TABLE `commodity_region` (
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `REGION_ID` int(11) NOT NULL COMMENT '地区（region表ID）',
  PRIMARY KEY (`COMMODITY_ID`,`REGION_ID`) USING BTREE,
  KEY `fk_region_id_1` (`REGION_ID`) USING BTREE,
  KEY `fk_commodity_id_18` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_18` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_region_id_1` FOREIGN KEY (`REGION_ID`) REFERENCES `region` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品销售地区表';

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '小区ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) DEFAULT NULL COMMENT '管理员（member表ID）',
  `PROVINCE` varchar(16) DEFAULT NULL COMMENT '省',
  `CITY` varchar(16) DEFAULT NULL COMMENT '市',
  `INITIALS` varchar(16) DEFAULT NULL COMMENT '市首字母大写',
  `DISTRICT` varchar(16) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(32) DEFAULT NULL COMMENT '小区',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `COMMISSION_RATE` decimal(5,2) DEFAULT '0.00' COMMENT '提成比例',
  `DESCRIPTION` text NOT NULL COMMENT '小区介绍',
  `IMG_PATH` varchar(255) NOT NULL COMMENT '小区图片',
  `RECEIVING_ADDRESS` varchar(255) NOT NULL COMMENT '小区收货地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_9` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_9` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='小区表';

-- ----------------------------
-- Table structure for community_group
-- ----------------------------
DROP TABLE IF EXISTS `community_group`;
CREATE TABLE `community_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '小区拼团ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `LABEL` varchar(255) NOT NULL COMMENT '活动标签',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `ACTIVITY_COVER` varchar(255) DEFAULT NULL COMMENT '活动封面',
  `SHARE_TITLE` varchar(255) DEFAULT NULL COMMENT '分享标题',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `COMMUNITY_ID` int(11) NOT NULL COMMENT '小区（community表ID）',
  `ACTIVITY_STOCK` int(11) NOT NULL DEFAULT '0' COMMENT '活动库存',
  `GROUP_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '拼团价',
  `GROUP_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '成团人数',
  `LIMIT_GROUP_TIME` int(11) DEFAULT NULL COMMENT '成团时限（单位小时），团长发起的组团有效期',
  `LIMIT_QUANTITY` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `LIMIT_PAY_TIME` int(11) NOT NULL DEFAULT '0' COMMENT '支付时限（单位分钟），买家XX分钟内未支付，开团/参团记录自动取消',
  `REWARD_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '奖励类型（1积分2优惠券）',
  `REWARD_INTEGRAL` int(11) DEFAULT '0' COMMENT '奖励积分',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `FREIGHT_SET` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运费设置（1卖家包邮2买家承担运费）',
  `FREIGHT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_product_id_56` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_community_id_56` (`COMMUNITY_ID`) USING BTREE,
  KEY `fk_coupon_id_56` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_community_id_56` FOREIGN KEY (`COMMUNITY_ID`) REFERENCES `community` (`ID`),
  CONSTRAINT `fk_coupon_id_56` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_product_id_56` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='小区拼团表';

-- ----------------------------
-- Table structure for community_group_member
-- ----------------------------
DROP TABLE IF EXISTS `community_group_member`;
CREATE TABLE `community_group_member` (
  `COMMUNITY_GROUP_RECORD_ID` int(11) NOT NULL COMMENT '小区拼团记录（community_group_record表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '成员（member表ID）',
  PRIMARY KEY (`COMMUNITY_GROUP_RECORD_ID`,`MEMBER_ID`) USING BTREE,
  KEY `fk_community_group_record_id_1` (`COMMUNITY_GROUP_RECORD_ID`) USING BTREE,
  KEY `fk_member_id_38` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_community_group_record_id_1` FOREIGN KEY (`COMMUNITY_GROUP_RECORD_ID`) REFERENCES `community_group_record` (`ID`),
  CONSTRAINT `fk_member_id_38` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区拼团成员表';

-- ----------------------------
-- Table structure for community_group_record
-- ----------------------------
DROP TABLE IF EXISTS `community_group_record`;
CREATE TABLE `community_group_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '小区拼团记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COMMUNITY_GROUP_ID` int(11) NOT NULL COMMENT '小区拼团（community_group表ID）',
  `GROUP_CODE` varchar(127) NOT NULL COMMENT '团编号',
  `MEMBER_ID` int(11) NOT NULL COMMENT '团长（member表ID）',
  `GROUP_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '成团人数',
  `JOIN_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '参团人数',
  `OPEN_TIME` datetime DEFAULT NULL COMMENT '开团时间',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1等待开团2开团成功3开团失败）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_37` (`MEMBER_ID`) USING BTREE,
  KEY `fk_community_group_id_1` (`COMMUNITY_GROUP_ID`) USING BTREE,
  CONSTRAINT `fk_community_group_id_1` FOREIGN KEY (`COMMUNITY_GROUP_ID`) REFERENCES `community_group` (`ID`),
  CONSTRAINT `fk_member_id_37` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区拼团记录表';

-- ----------------------------
-- Table structure for consume_record
-- ----------------------------
DROP TABLE IF EXISTS `consume_record`;
CREATE TABLE `consume_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '消费记录ID',
  `GUID` varchar(64) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `TRADE_NO` varchar(16) DEFAULT NULL COMMENT '交易号',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单号',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `PAY_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '实付金额',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '完成时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_10` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_10` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8 COMMENT='消费记录表';

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COUPON_NO` varchar(16) NOT NULL COMMENT '优惠券编码',
  `COUPON_NAME` varchar(32) NOT NULL COMMENT '优惠券名称',
  `COUPON_TYPE` tinyint(4) NOT NULL COMMENT '优惠券类型（1满减券2买送券3储值券）',
  `PAR_VALUE` decimal(15,2) NOT NULL COMMENT '面值',
  `QUANTITY` int(11) NOT NULL COMMENT '发放数量',
  `RECEIVE_QUANTITY` int(11) DEFAULT '0' COMMENT '领取数量',
  `USE_QUANTITY` int(11) DEFAULT '0' COMMENT '使用数量',
  `USE_CONDITION_TYPE` tinyint(4) DEFAULT '0' COMMENT '使用条件类型（0无限制，1满XX元可用，2满XX件可用）',
  `FULL_MONEY` decimal(15,2) DEFAULT '0.00' COMMENT '满XX元可用',
  `FULL_QUANTITY` int(11) DEFAULT '0' COMMENT '满XX件可用',
  `USE_CONDITION` decimal(15,2) DEFAULT NULL COMMENT '使用条件（满xx可用，不限制为空）',
  `RECEIVE_MODE` tinyint(4) DEFAULT NULL COMMENT '领取方式（1手工发放2自助领取3活动赠送）',
  `MEMBER_LEVEL_ID` int(11) DEFAULT NULL COMMENT '会员等级（member_level表ID）',
  `LIMITED` int(11) DEFAULT NULL COMMENT '每人限领（不限制为空）',
  `VALID_TYPE` tinyint(4) DEFAULT NULL COMMENT '有效期类型（1时间段2固定天数）',
  `START_TIME` date DEFAULT NULL COMMENT '开始时间',
  `END_TIME` date DEFAULT NULL COMMENT '结束时间',
  `FIXED_DAY` int(11) DEFAULT NULL COMMENT '固定天数（领取后到期天数）',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_level_id_2` (`MEMBER_LEVEL_ID`) USING BTREE,
  CONSTRAINT `fk_member_level_id_2` FOREIGN KEY (`MEMBER_LEVEL_ID`) REFERENCES `member_level` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='优惠券';

-- ----------------------------
-- Table structure for coupon_commodity
-- ----------------------------
DROP TABLE IF EXISTS `coupon_commodity`;
CREATE TABLE `coupon_commodity` (
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  PRIMARY KEY (`COUPON_ID`,`COMMODITY_ID`) USING BTREE,
  KEY `fk_coupon_id_11` (`COUPON_ID`) USING BTREE,
  KEY `fk_commodity_id_21` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_21` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_coupon_id_11` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券商品表';

-- ----------------------------
-- Table structure for coupon_product
-- ----------------------------
DROP TABLE IF EXISTS `coupon_product`;
CREATE TABLE `coupon_product` (
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  PRIMARY KEY (`COUPON_ID`,`PRODUCT_ID`) USING BTREE,
  KEY `fk_coupon_id_1` (`COUPON_ID`) USING BTREE,
  KEY `fk_product_id_2` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_1` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_product_id_2` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券商品表';

-- ----------------------------
-- Table structure for coupon_receive
-- ----------------------------
DROP TABLE IF EXISTS `coupon_receive`;
CREATE TABLE `coupon_receive` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `GUID` varchar(64) DEFAULT NULL COMMENT 'GUID',
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `COUPON_NO` varchar(16) DEFAULT NULL COMMENT '优惠码',
  `PAR_VALUE` decimal(15,2) DEFAULT NULL COMMENT '面值',
  `USED` decimal(15,2) DEFAULT NULL COMMENT '使用',
  `SURPLUS` decimal(15,2) DEFAULT NULL COMMENT '剩余',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `MEMBER_PHONE` varchar(16) NOT NULL COMMENT '会员账号',
  `RECEIVE_MODE` tinyint(1) DEFAULT NULL COMMENT '领取方式（1手工发放2自助领取）',
  `RECEIVE_TIME` datetime DEFAULT NULL COMMENT '领取时间',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `STATE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态（1未使用2已使用3已失效4未达不可使用）',
  `USE_TIME` datetime DEFAULT NULL COMMENT '使用时间',
  `ORDER_ID` int(11) DEFAULT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_coupon_id_2` (`COUPON_ID`) USING BTREE,
  KEY `fk_member_id_1` (`MEMBER_ID`) USING BTREE,
  KEY `fk_order_id_4` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_2` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_member_id_1` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_4` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='优惠券领取表';

-- ----------------------------
-- Table structure for coupon_use
-- ----------------------------
DROP TABLE IF EXISTS `coupon_use`;
CREATE TABLE `coupon_use` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `GUID` varchar(64) DEFAULT NULL COMMENT 'GUID',
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `COUPON_NO` varchar(16) DEFAULT NULL COMMENT '优惠码',
  `COUPON_RECEIVE_ID` int(11) NOT NULL COMMENT '优惠券领取（coupon_receive表ID）',
  `PAR_VALUE` decimal(15,2) DEFAULT NULL COMMENT '面值',
  `USE` decimal(15,2) DEFAULT NULL COMMENT '使用',
  `SURPLUS` decimal(15,2) DEFAULT NULL COMMENT '剩余',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `MEMBER_PHONE` varchar(16) DEFAULT NULL COMMENT '会员账号',
  `USE_TIME` datetime DEFAULT NULL COMMENT '使用时间',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_coupon_id_3` (`COUPON_ID`) USING BTREE,
  KEY `fk_coupon_receive_id` (`COUPON_RECEIVE_ID`) USING BTREE,
  KEY `fk_member_id_2` (`MEMBER_ID`) USING BTREE,
  KEY `fk_order_id_5` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_3` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_coupon_receive_id` FOREIGN KEY (`COUPON_RECEIVE_ID`) REFERENCES `coupon_receive` (`ID`),
  CONSTRAINT `fk_member_id_2` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_5` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券使用表';

-- ----------------------------
-- Table structure for daily_task
-- ----------------------------
DROP TABLE IF EXISTS `daily_task`;
CREATE TABLE `daily_task` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TASK_NAME` varchar(32) NOT NULL COMMENT '任务名称',
  `GROWTH_VALUE` int(11) NOT NULL COMMENT '成长值',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用（0禁用1启用）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='日常任务表--作废';

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '上级部门',
  `DEPT_NO` varchar(16) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME` varchar(16) NOT NULL COMMENT '部门名称',
  `DESCRIPTION` varchar(32) DEFAULT NULL COMMENT '部门描述',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_parent_id_1` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `fk_parent_id_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `dept` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Table structure for express_template
-- ----------------------------
DROP TABLE IF EXISTS `express_template`;
CREATE TABLE `express_template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TEMPLATE_NO` varchar(16) DEFAULT NULL COMMENT '单据编号',
  `TEMPLATE_NAME` varchar(32) NOT NULL COMMENT '单据名称',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `PRINT_WIDTH` decimal(15,2) DEFAULT NULL COMMENT '打印宽',
  `PRINT_HIGH` decimal(15,2) DEFAULT NULL COMMENT '打印高',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `express_template_ib_fk_1` (`SUPPLIER_ID`),
  CONSTRAINT `express_template_ib_fk_1` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='快递单模板表';

-- ----------------------------
-- Table structure for freight_template
-- ----------------------------
DROP TABLE IF EXISTS `freight_template`;
CREATE TABLE `freight_template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '运费模板ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TEMPLATE_NO` varchar(16) NOT NULL COMMENT '模板编号',
  `TEMPLATE_NAME` varchar(32) NOT NULL COMMENT '模板名称',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `CHARGE_MODE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '计费方式（1按重量2按件数）',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `PRESET_WEIGHT` decimal(15,2) DEFAULT NULL COMMENT '首重',
  `PRESET_FEE` decimal(15,2) DEFAULT NULL COMMENT '首费',
  `EXTRA_WEIGHT` decimal(15,2) DEFAULT NULL COMMENT '续重',
  `EXTRA_FEE` decimal(15,2) DEFAULT NULL COMMENT '续费',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(8) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(64) DEFAULT NULL COMMENT '小区',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `freight_template_ib_fk_1` (`SUPPLIER_ID`),
  CONSTRAINT `freight_template_ib_fk_1` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='运费模板表';

-- ----------------------------
-- Table structure for integral_record
-- ----------------------------
DROP TABLE IF EXISTS `integral_record`;
CREATE TABLE `integral_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '积分记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `INTEGRAL_TASK_ID` int(11) NOT NULL COMMENT '积分任务（integral_task表ID）',
  `OPERATE_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作类型（0增加1减少）',
  `OPERATE_INTEGRAL` int(11) NOT NULL DEFAULT '0' COMMENT '增/减积分数值',
  `CURRENT_INTEGRAL` int(11) DEFAULT '0' COMMENT '当前积分',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_40` (`MEMBER_ID`) USING BTREE,
  KEY `fk_integral_task_id_1` (`INTEGRAL_TASK_ID`) USING BTREE,
  CONSTRAINT `fk_integral_task_id_1` FOREIGN KEY (`INTEGRAL_TASK_ID`) REFERENCES `integral_task` (`ID`),
  CONSTRAINT `fk_member_id_40` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='积分记录表';

-- ----------------------------
-- Table structure for integral_task
-- ----------------------------
DROP TABLE IF EXISTS `integral_task`;
CREATE TABLE `integral_task` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TASK_NAME` varchar(32) NOT NULL COMMENT '任务名称',
  `GROWTH_VALUE` int(11) NOT NULL DEFAULT '0' COMMENT '成长值',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='积分任务表';

-- ----------------------------
-- Table structure for logistics_address
-- ----------------------------
DROP TABLE IF EXISTS `logistics_address`;
CREATE TABLE `logistics_address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流地址ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商ID',
  `ADDRESS_TYPE` tinyint(4) DEFAULT '1' COMMENT '地址类型(1、收货地址 2、发货地址)',
  `CONTACT` varchar(16) DEFAULT NULL COMMENT '联系人',
  `CONTACT_PHONE` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(8) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(64) DEFAULT NULL COMMENT '详细地址',
  `STATE` tinyint(4) DEFAULT '0' COMMENT '默认状态（0、非默认 1、默认）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) DEFAULT '0' COMMENT '删除（0、否1、是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`),
  KEY `fk_supplier_address_id_1` (`SUPPLIER_ID`),
  CONSTRAINT `fk_supplier_address_id_1` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流地址';

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '上级ID',
  `USERNAME` varchar(16) NOT NULL COMMENT '账号',
  `PASSWORD` varchar(32) NOT NULL COMMENT '密码',
  `PAY_PASSWORD` varchar(32) DEFAULT NULL COMMENT '支付密码',
  `COMMUNITY_ID` int(11) DEFAULT NULL COMMENT '小区',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0启用1禁用',
  `VIP` tinyint(4) DEFAULT '0' COMMENT 'VIP会员（0否1是）',
  `NICKNAME` varchar(32) DEFAULT NULL COMMENT '会员昵称',
  `PHONE` varchar(32) NOT NULL COMMENT '手机号',
  `SEX` tinyint(4) DEFAULT NULL COMMENT '性别(0男，1女)',
  `BIRTHDAY` date DEFAULT NULL COMMENT '会员生日',
  `AVATER` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `MEMBER_LEVEL_ID` int(11) NOT NULL COMMENT '会员等级（member_level表ID）',
  `ACCOUNT_ID` int(11) DEFAULT NULL COMMENT '账户（account表ID）',
  `MEMBER_TYPE` tinyint(4) NOT NULL COMMENT '会员类型（0普通会员1管理员）',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(8) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(32) DEFAULT NULL COMMENT '小区',
  `OPEN_ID` varchar(32) DEFAULT NULL COMMENT '用户的唯一标识',
  `UNION_ID` varchar(32) DEFAULT NULL COMMENT '用户不同应用的唯一标识',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '注册时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_level_id_3` (`MEMBER_LEVEL_ID`) USING BTREE,
  KEY `fk_community_id_11` (`COMMUNITY_ID`) USING BTREE,
  CONSTRAINT `fk_community_id_11` FOREIGN KEY (`COMMUNITY_ID`) REFERENCES `community` (`ID`),
  CONSTRAINT `fk_member_level_id_3` FOREIGN KEY (`MEMBER_LEVEL_ID`) REFERENCES `member_level` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Table structure for member_level
-- ----------------------------
DROP TABLE IF EXISTS `member_level`;
CREATE TABLE `member_level` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员等级ID',
  `GUID` varchar(64) DEFAULT NULL COMMENT 'GUID',
  `NAME` varchar(32) NOT NULL COMMENT '等级名称',
  `QUANTITY` int(11) NOT NULL COMMENT '会员人数（冗余）',
  `GROWTH_VALUE` int(11) NOT NULL COMMENT '成长值满足点',
  `DISCOUNT` decimal(5,2) NOT NULL COMMENT '折扣（0.00-100.00）',
  `INITIAL` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认等级（0非默认1默认）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '注册时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`,`NAME`),
  KEY `ID` (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='会员等级表';

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(127) NOT NULL COMMENT '消息标题',
  `CONTENT` text COMMENT '消息内容',
  `MESSAGE_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型（0系统1..）',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='消息表';

-- ----------------------------
-- Table structure for message_read
-- ----------------------------
DROP TABLE IF EXISTS `message_read`;
CREATE TABLE `message_read` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息阅读ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MESSAGE_ID` int(11) NOT NULL COMMENT '消息（message表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `READ_TIME` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_message_id_61` (`MESSAGE_ID`) USING BTREE,
  KEY `fk_member_id_21` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_61` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_message_id_1` FOREIGN KEY (`MESSAGE_ID`) REFERENCES `message` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息阅读表';

-- ----------------------------
-- Table structure for national_group
-- ----------------------------
DROP TABLE IF EXISTS `national_group`;
CREATE TABLE `national_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '全国拼团ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `LABEL` varchar(255) NOT NULL COMMENT '活动标签',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `ACTIVITY_COVER` varchar(255) DEFAULT NULL COMMENT '活动封面',
  `SHARE_TITLE` varchar(255) DEFAULT NULL COMMENT '分享标题',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `ACTIVITY_STOCK` int(11) NOT NULL DEFAULT '0' COMMENT '活动库存',
  `GROUP_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '拼团价',
  `GROUP_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '成团人数',
  `LIMIT_GROUP_TIME` int(11) DEFAULT NULL COMMENT '成团时限（单位小时），团长发起的组团有效期',
  `LIMIT_QUANTITY` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `STOCK_SET` tinyint(4) DEFAULT '1' COMMENT '库存设置（1下单减库存2支付减库存）',
  `LIMIT_PAY_TIME` int(11) NOT NULL DEFAULT '0' COMMENT '支付时限（单位分钟），买家XX分钟内未支付，开团/参团记录自动取消',
  `REWARD_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '奖励类型（1积分2优惠券）',
  `REWARD_INTEGRAL` int(11) DEFAULT '0' COMMENT '奖励积分',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `FREIGHT_SET` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运费设置（1卖家包邮2买家承担运费）',
  `FREIGHT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_product_id_55` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_coupon_id_55` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_55` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_product_id_55` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='全国拼团表';

-- ----------------------------
-- Table structure for national_group_member
-- ----------------------------
DROP TABLE IF EXISTS `national_group_member`;
CREATE TABLE `national_group_member` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '全国拼团成员ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NATIONAL_GROUP_RECORD_ID` int(11) NOT NULL COMMENT '全国拼团记录（national_group_record表ID）',
  `PAY` tinyint(4) DEFAULT NULL COMMENT '是否支付 0否 1是',
  `MEMBER_ID` int(11) NOT NULL COMMENT '成员（member表ID）',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_national_group_record_id_1` (`NATIONAL_GROUP_RECORD_ID`) USING BTREE,
  KEY `fk_member_id_36` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_36` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_national_group_record_id_1` FOREIGN KEY (`NATIONAL_GROUP_RECORD_ID`) REFERENCES `national_group_record` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='全国拼团成员表';

-- ----------------------------
-- Table structure for national_group_record
-- ----------------------------
DROP TABLE IF EXISTS `national_group_record`;
CREATE TABLE `national_group_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '全国拼团记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NATIONAL_GROUP_ID` int(11) NOT NULL COMMENT '全国拼团（national_group表ID）',
  `GROUP_CODE` varchar(127) NOT NULL COMMENT '团编号',
  `MEMBER_ID` int(11) NOT NULL COMMENT '团长（member表ID）',
  `GROUP_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '成团人数',
  `JOIN_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '参团人数',
  `PAY` tinyint(4) DEFAULT '0' COMMENT '是否支付 0否 1是',
  `OPEN_TIME` datetime DEFAULT NULL COMMENT '开团时间',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1等待开团2开团成功3开团失败）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_35` (`MEMBER_ID`) USING BTREE,
  KEY `fk_national_group_id_1` (`NATIONAL_GROUP_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_35` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_national_group_id_1` FOREIGN KEY (`NATIONAL_GROUP_ID`) REFERENCES `national_group` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='全国拼团记录表';

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(127) NOT NULL COMMENT '通知标题',
  `CONTENT` text COMMENT '通知内容',
  `NOTICE_TYPE` tinyint(4) DEFAULT '0' COMMENT '通知类型（0..）',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

-- ----------------------------
-- Table structure for notice_read
-- ----------------------------
DROP TABLE IF EXISTS `notice_read`;
CREATE TABLE `notice_read` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知阅读ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NOTICE_ID` int(11) NOT NULL COMMENT '通知（notice表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `READ_TIME` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_notice_id_1` (`NOTICE_ID`) USING BTREE,
  KEY `fk_member_id_21` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_21` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_notice_id_1` FOREIGN KEY (`NOTICE_ID`) REFERENCES `notice` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知阅读表';

-- ----------------------------
-- Table structure for operate_category
-- ----------------------------
DROP TABLE IF EXISTS `operate_category`;
CREATE TABLE `operate_category` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '运营分类ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '上级运营分类ID',
  `CODE` varchar(16) DEFAULT NULL COMMENT '运营分类编码',
  `NAME` varchar(32) NOT NULL COMMENT '运营分类名称',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `IMG_PATH` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_parent_id_13` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `fk_parent_id_13` FOREIGN KEY (`PARENT_ID`) REFERENCES `operate_category` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='运营分类表';

-- ----------------------------
-- Table structure for order_log
-- ----------------------------
DROP TABLE IF EXISTS `order_log`;
CREATE TABLE `order_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `STATE` tinyint(1) DEFAULT NULL COMMENT '订单状态（1创建订单2支付成功3开始配送4确认收货）',
  `OPERATE_TIME` datetime NOT NULL COMMENT '操作时间',
  `OPERATOR` varchar(32) NOT NULL COMMENT '操作人',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单日志表';

-- ----------------------------
-- Table structure for order_setting
-- ----------------------------
DROP TABLE IF EXISTS `order_setting`;
CREATE TABLE `order_setting` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单设置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SET_TYPE` tinyint(4) NOT NULL COMMENT '订单设置类型（1、秒杀订单超过（未付款） 2、正常订单超过（未付款）3、发货超过（未收货）4、正常完成超过（申请售后）5、正常完成超过（自动五星好评）',
  `TIMEOUT` int(11) NOT NULL DEFAULT '0' COMMENT '超时时间',
  `TIME_UNIT` tinyint(4) DEFAULT NULL COMMENT '时间单位（1天，2小时，3分钟）',
  `DAY` int(11) NOT NULL DEFAULT '0' COMMENT '天',
  `HOUR` int(11) NOT NULL DEFAULT '0' COMMENT '小时',
  `MINUTE` int(11) NOT NULL DEFAULT '0' COMMENT '分钟',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='订单设置';

-- ----------------------------
-- Table structure for payment_config
-- ----------------------------
DROP TABLE IF EXISTS `payment_config`;
CREATE TABLE `payment_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付配置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PAY_TYPE` tinyint(1) NOT NULL COMMENT '支付类型（1支付宝2微信）',
  `APP_ID` varchar(32) NOT NULL COMMENT '支付宝分配给开发者的应用ID',
  `METHOD` varchar(128) NOT NULL COMMENT '接口名称',
  `CHARSET` varchar(10) NOT NULL COMMENT '请求使用的编码格式',
  `SIGN_TYPE` varchar(10) NOT NULL COMMENT '商户生成签名字符串所使用的签名算法类型',
  `SIGN` varchar(19) NOT NULL COMMENT '商户请求参数的签名串',
  `VERSION` varchar(10) NOT NULL COMMENT '调用的接口版本，固定为：1.0',
  `NOTIFY_URL` varchar(10) NOT NULL COMMENT '支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https',
  `MCH_ID` varchar(32) NOT NULL COMMENT '商户收款账号',
  `APP_KEY` varchar(32) NOT NULL COMMENT '交易过程生成签名的密钥',
  `APP_SECRET` varchar(32) NOT NULL COMMENT 'APPID对应的接口密码',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付配置表';

-- ----------------------------
-- Table structure for platform_sale_stat
-- ----------------------------
DROP TABLE IF EXISTS `platform_sale_stat`;
CREATE TABLE `platform_sale_stat` (
  `ID` int(11) NOT NULL COMMENT '平台销售统计ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PLATFORM_ORDER_NUM` int(11) NOT NULL DEFAULT '0' COMMENT '平台订单数',
  `SALE_AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售额',
  `PLATFORM_RATIO` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '平台占比',
  `COST` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '成本',
  `PROFIT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '利润',
  `STAT_TIME` datetime DEFAULT NULL COMMENT '统计时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台销售统计表';

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '位置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NAME` varchar(127) DEFAULT NULL COMMENT '名称',
  `POSITION_TYPE` tinyint(4) DEFAULT NULL COMMENT '位置类型（1.首页轮播图2.楼层推荐位3.今日推荐位4.购物车推荐位5.邀请有礼推荐位6.优惠券推荐位）',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='位置表';

-- ----------------------------
-- Table structure for position_type
-- ----------------------------
DROP TABLE IF EXISTS `position_type`;
CREATE TABLE `position_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '位置类型ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NAME` varchar(32) DEFAULT NULL COMMENT '位置名称',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='位置类型表';

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款处理ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REFUND_ORDER_ID` int(11) DEFAULT NULL COMMENT '退款单（refund_order表ID）',
  `RETURN_ORDER_ID` int(11) DEFAULT NULL COMMENT '退货单（return_order表ID）',
  `USER_ID` int(11) NOT NULL COMMENT '处理人（user表ID）',
  `USERNAME` varchar(16) DEFAULT NULL COMMENT '处理人',
  `PROCESS_TYPE` tinyint(1) DEFAULT NULL COMMENT '处理类型（1确认退·2拒绝退 3确认收货',
  `PROCESS_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '处理备注',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_refund_order_id_1` (`REFUND_ORDER_ID`) USING BTREE,
  KEY `fk_return_order_id_2` (`RETURN_ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_refund_order_id_1` FOREIGN KEY (`REFUND_ORDER_ID`) REFERENCES `refund_order` (`ID`),
  CONSTRAINT `fk_return_order_id_2` FOREIGN KEY (`RETURN_ORDER_ID`) REFERENCES `return_order` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退货退款处理表';

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '货品ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PRODUCT_NO` varchar(32) DEFAULT NULL COMMENT '货品编码',
  `PRODUCT_NAME` varchar(127) NOT NULL COMMENT '货品名称',
  `PRODUCT_SHORT_NAME` varchar(127) DEFAULT NULL COMMENT '货品简称',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序',
  `VOLUME` decimal(15,2) DEFAULT NULL COMMENT '体积',
  `WEIGHT` decimal(15,2) DEFAULT NULL COMMENT '重量',
  `DESCRIPTION` text COMMENT '商品介绍',
  `COST_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '成本',
  `PRODUCT_IMG_PATH` varchar(511) DEFAULT NULL COMMENT '货品图片',
  `ORIGINAL_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '原价',
  `CURRENT_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '现价',
  `MEMBER_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '会员价',
  `VIP_PRICE` decimal(15,2) DEFAULT NULL COMMENT 'vip价',
  `SKU` varchar(64) DEFAULT NULL COMMENT 'SKU',
  `STOCK` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_commodity_id_4` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_supplier_id_2` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_4` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_supplier_id_2` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=914 DEFAULT CHARSET=utf8 COMMENT='货品表';

-- ----------------------------
-- Table structure for product_attribute
-- ----------------------------
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute` (
  `PRODUCT_ID` int(11) NOT NULL COMMENT '货品（product表ID）',
  `ATTRIBUTE_ID` int(11) NOT NULL COMMENT '属性（attribute表ID）',
  PRIMARY KEY (`PRODUCT_ID`,`ATTRIBUTE_ID`) USING BTREE,
  KEY `fk_product_id_3` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_attribute_id_3` (`ATTRIBUTE_ID`) USING BTREE,
  CONSTRAINT `fk_attribute_id_3` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `attribute` (`ID`),
  CONSTRAINT `fk_product_id_3` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货品属性表';

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `QUESTION_TYPE_ID` int(11) NOT NULL COMMENT '问题类型(question_type表ID)',
  `ASK_QUESTION` varchar(255) NOT NULL COMMENT '提出问题',
  `ANSWER_QUESTION` text COMMENT '回答问题',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(4) DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_question_type_id_1` (`QUESTION_TYPE_ID`) USING BTREE,
  CONSTRAINT `fk_question_type_id_1` FOREIGN KEY (`QUESTION_TYPE_ID`) REFERENCES `question_type` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='问题表';

-- ----------------------------
-- Table structure for question_type
-- ----------------------------
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE `question_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题分类ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TYPE_NAME` varchar(127) NOT NULL COMMENT '分类名称',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='问题分类表';

-- ----------------------------
-- Table structure for rebate_group
-- ----------------------------
DROP TABLE IF EXISTS `rebate_group`;
CREATE TABLE `rebate_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '返现拼团ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `LABEL` varchar(255) NOT NULL COMMENT '活动标签',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `ACTIVITY_COVER` varchar(255) DEFAULT NULL COMMENT '活动封面',
  `SHARE_TITLE` varchar(255) DEFAULT NULL COMMENT '分享标题',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `ACTIVITY_STOCK` int(11) DEFAULT '0' COMMENT '活动库存（活动库存为独立库存，成交不会减少商品库存）',
  `LIMIT_GROUP_TIME` int(11) DEFAULT '0' COMMENT '成团时限（单位小时）团长发起的组团有效期',
  `LIMIT_QUANTITY` int(11) DEFAULT NULL COMMENT '限购数量',
  `LIMIT_PAY_TIME` int(11) DEFAULT NULL COMMENT '支付时限（单位分钟），买家XX分钟内未支付，自动释放库存',
  `REWARD_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '奖励类型（1积分2优惠券）',
  `REWARD_INTEGRAL` int(11) DEFAULT '0' COMMENT '奖励积分',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `FREIGHT_SET` tinyint(4) NOT NULL COMMENT '运费设置（1卖家包邮1买家承担运费）',
  `FREIGHT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`),
  KEY `fk_coupon_id_58` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_58` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返现拼团表';

-- ----------------------------
-- Table structure for rebate_group_member
-- ----------------------------
DROP TABLE IF EXISTS `rebate_group_member`;
CREATE TABLE `rebate_group_member` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '返现拼团成员ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REBATE_GROUP_RECORD_ID` int(11) NOT NULL COMMENT '返现拼团记录（rebate_group_record表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '成员（member表ID）',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_rebate_group_record_id_2` (`REBATE_GROUP_RECORD_ID`) USING BTREE,
  KEY `fk_member_id_55` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_55` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_rebate_group_record_id_2` FOREIGN KEY (`REBATE_GROUP_RECORD_ID`) REFERENCES `rebate_group_record` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返现拼团成员表';

-- ----------------------------
-- Table structure for rebate_group_record
-- ----------------------------
DROP TABLE IF EXISTS `rebate_group_record`;
CREATE TABLE `rebate_group_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '返现拼团记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `GROUP_CODE` varchar(127) DEFAULT NULL COMMENT '团编号',
  `REBATE_GROUP_ID` int(11) NOT NULL COMMENT '返现拼团（rebate_group表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '团长（member表ID）',
  `GROUP_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '团长拼团价（退款时根据当前拼团价退款）',
  `JOIN_PEOPLE` int(11) NOT NULL DEFAULT '0' COMMENT '参团人数',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0成功）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_rebate_group_id_2` (`REBATE_GROUP_ID`) USING BTREE,
  KEY `fk_member_id_41` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_41` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_rebate_group_id_2` FOREIGN KEY (`REBATE_GROUP_ID`) REFERENCES `rebate_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返现拼团记录表';

-- ----------------------------
-- Table structure for rebate_record
-- ----------------------------
DROP TABLE IF EXISTS `rebate_record`;
CREATE TABLE `rebate_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '返现记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REBATE_GROUP_RECORD_ID` int(11) NOT NULL COMMENT '返现拼团记录（rebate_group_record表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `GROUP_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '拼团价-冗余',
  `REBATE_AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '返现金额',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0成功）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_rebate_group_record_id_1` (`REBATE_GROUP_RECORD_ID`) USING BTREE,
  KEY `fk_member_id_56` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_56` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_rebate_group_record_id_1` FOREIGN KEY (`REBATE_GROUP_RECORD_ID`) REFERENCES `rebate_group_record` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返现记录表';

-- ----------------------------
-- Table structure for rebate_set
-- ----------------------------
DROP TABLE IF EXISTS `rebate_set`;
CREATE TABLE `rebate_set` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '返现设置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REBATE_GROUP_ID` int(11) NOT NULL COMMENT '返现拼团（rebate_group表ID）',
  `GROUP_PEOPLE` int(11) DEFAULT '0' COMMENT '拼团人数',
  `GROUP_PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '拼团价',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `fk_rebate_group_id_1` (`REBATE_GROUP_ID`) USING BTREE,
  CONSTRAINT `fk_rebate_group_id_1` FOREIGN KEY (`REBATE_GROUP_ID`) REFERENCES `rebate_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返现设置表';

-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '推荐位ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `POSITION_ID` int(11) NOT NULL COMMENT '推荐位位置',
  `TITLE` varchar(127) NOT NULL COMMENT '推荐位标题',
  `RECOMMEND_TYPE` tinyint(4) DEFAULT NULL COMMENT '推荐类型（1今日推荐2楼层推荐）',
  `SORT` tinyint(4) DEFAULT NULL COMMENT '排序号',
  `SHOW_MODE` tinyint(4) DEFAULT NULL COMMENT '排列方式（2展示2个，3展示3个，4展示4个）',
  `IMG_PATH` varchar(255) DEFAULT NULL COMMENT '推荐位默认图片路径',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `POSITION_ID` (`POSITION_ID`) USING BTREE,
  CONSTRAINT `fk_position_id_1` FOREIGN KEY (`POSITION_ID`) REFERENCES `position` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COMMENT='推荐位表';

-- ----------------------------
-- Table structure for recommend_commodity
-- ----------------------------
DROP TABLE IF EXISTS `recommend_commodity`;
CREATE TABLE `recommend_commodity` (
  `RECOMMEND_ID` int(11) NOT NULL COMMENT '推荐位（recommend表ID）',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  PRIMARY KEY (`RECOMMEND_ID`,`COMMODITY_ID`) USING BTREE,
  KEY `fk_recommend_id_11` (`RECOMMEND_ID`) USING BTREE,
  KEY `fk_commodity_id_51` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_51` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_recommend_id_11` FOREIGN KEY (`RECOMMEND_ID`) REFERENCES `recommend` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推荐位商品表';

-- ----------------------------
-- Table structure for refund_order
-- ----------------------------
DROP TABLE IF EXISTS `refund_order`;
CREATE TABLE `refund_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REFUND_NO` varchar(16) NOT NULL COMMENT '服务单号',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `STATE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '申请状态（1待处理2已处理）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `MEMBER_PHONE` varchar(16) DEFAULT NULL COMMENT '会员账号',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `ORDER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `REFUND_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '退款金额',
  `REFUND_MODE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '退款方式（1退回原支付渠道）',
  `REFUND_TYPE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '退款类型（1取消订单（待发货））',
  `REFUND_REASON` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `HANDLE_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '处理备注',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `PROCESS_TYPE` tinyint(1) DEFAULT '0' COMMENT '处理类型（1确认退·2拒绝退3、未处理)',
  `DISPOSE_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_5` (`MEMBER_ID`) USING BTREE,
  KEY `fk_order_id_2` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_5` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_2` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='退款表';

-- ----------------------------
-- Table structure for refund_process
-- ----------------------------
DROP TABLE IF EXISTS `refund_process`;
CREATE TABLE `refund_process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货流程id',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CONTENT` varchar(128) NOT NULL COMMENT '内容',
  `USER_ID` int(11) DEFAULT NULL COMMENT '处理人id',
  `HANDLE_DATE` datetime NOT NULL COMMENT '处理时间',
  `REFUND_ORDER_ID` int(11) DEFAULT NULL COMMENT '退款id',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '处理备注',
  PRIMARY KEY (`ID`),
  KEY `REFUND_ORDER_ID` (`REFUND_ORDER_ID`) USING BTREE,
  KEY `USER_ID` (`USER_ID`) USING BTREE,
  CONSTRAINT `refund_process_ibfk_1` FOREIGN KEY (`REFUND_ORDER_ID`) REFERENCES `refund_order` (`ID`),
  CONSTRAINT `refund_process_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='退款处理表';

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='地区表';

-- ----------------------------
-- Table structure for resc
-- ----------------------------
DROP TABLE IF EXISTS `resc`;
CREATE TABLE `resc` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '父级资源',
  `CODE` varchar(16) DEFAULT NULL COMMENT '资源编码',
  `NAME` varchar(32) NOT NULL COMMENT '资源名称',
  `URL` varchar(64) NOT NULL COMMENT '资源路径',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_parent_id_2` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `fk_parent_id_2` FOREIGN KEY (`PARENT_ID`) REFERENCES `resc` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Table structure for return_order
-- ----------------------------
DROP TABLE IF EXISTS `return_order`;
CREATE TABLE `return_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `RETURN_NO` varchar(32) DEFAULT NULL COMMENT '服务单号',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `STATE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '申请状态（1待处理2待收货3已完成 4退款）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `MEMBER_PHONE` varchar(16) DEFAULT NULL COMMENT '会员账号',
  `CONTACT` varchar(16) DEFAULT NULL COMMENT '联系人',
  `CONTACT_PHONE` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `ORDER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `FREIGHT` decimal(15,2) DEFAULT NULL COMMENT '运费',
  `REFUND_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '退款金额',
  `RETURN_REASON` varchar(127) DEFAULT NULL COMMENT '退货原因',
  `PROBLEM_DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '问题描述',
  `VOUCHER_PHOTO` varchar(255) DEFAULT NULL COMMENT '凭证照片',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(1) DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `HANDLE_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  `PROCESS_TYPE` tinyint(1) DEFAULT '0' COMMENT '处理类型（1确认退·2拒绝退 3确认收货 0为没状态 4确认退款 5拒绝退狂)',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_6` (`MEMBER_ID`) USING BTREE,
  KEY `fk_order_id_3` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_6` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_3` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='退货表';

-- ----------------------------
-- Table structure for return_process
-- ----------------------------
DROP TABLE IF EXISTS `return_process`;
CREATE TABLE `return_process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货流程id',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CONTENT` varchar(128) NOT NULL COMMENT '内容',
  `USER_ID` int(11) DEFAULT NULL COMMENT '处理人id',
  `HANDLE_DATE` datetime NOT NULL COMMENT '处理时间',
  `RETURN_ORDER_ID` int(11) DEFAULT NULL COMMENT '退货id',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '处理备注',
  PRIMARY KEY (`ID`),
  KEY `RETURN_ORDER_ID` (`RETURN_ORDER_ID`) USING BTREE,
  KEY `USER_ID` (`USER_ID`) USING BTREE,
  CONSTRAINT `return_process_ibfk_1` FOREIGN KEY (`RETURN_ORDER_ID`) REFERENCES `return_order` (`ID`),
  CONSTRAINT `return_process_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='退货处理表';

-- ----------------------------
-- Table structure for return_reason
-- ----------------------------
DROP TABLE IF EXISTS `return_reason`;
CREATE TABLE `return_reason` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退货原因ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REASON_TYPE` varchar(127) NOT NULL COMMENT '原因类型',
  `SORT` tinyint(2) DEFAULT NULL COMMENT '排序',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='退货原因表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NAME` varchar(16) NOT NULL COMMENT '角色名称',
  `DESCRIPTION` varchar(16) DEFAULT NULL COMMENT '角色描述',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for role_resc
-- ----------------------------
DROP TABLE IF EXISTS `role_resc`;
CREATE TABLE `role_resc` (
  `ROLE_ID` int(11) NOT NULL COMMENT '角色（role表id）',
  `RESC_ID` int(11) NOT NULL COMMENT '资源（resc表id）',
  PRIMARY KEY (`ROLE_ID`,`RESC_ID`) USING BTREE,
  KEY `fk_role_id_2` (`ROLE_ID`) USING BTREE,
  KEY `fk_resc_id_1` (`RESC_ID`) USING BTREE,
  CONSTRAINT `fk_resc_id_1` FOREIGN KEY (`RESC_ID`) REFERENCES `resc` (`ID`),
  CONSTRAINT `fk_role_id_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源表';

-- ----------------------------
-- Table structure for sale_order
-- ----------------------------
DROP TABLE IF EXISTS `sale_order`;
CREATE TABLE `sale_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态（1待付款2待发货3已发货4已完成5已关闭10待评价11已评价）',
  `ORDER_TYPE` varchar(255) DEFAULT NULL COMMENT '0默认订单 1开团，参团',
  `NATIONAL_GROUP_RECORD_ID` int(11) DEFAULT '0' COMMENT '普通订单和开团为0   参团时为开团id',
  `AFTER_SALE_STATE` tinyint(4) DEFAULT NULL COMMENT '售后状态（1可申请2申请中3已申请4已过期）',
  `BUYER_MESSAGE` varchar(255) DEFAULT NULL COMMENT '买家留言',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `DELIVERY_MODE` varchar(32) DEFAULT NULL COMMENT '配送方式',
  `LOGISTICS_COMPANY` varchar(32) DEFAULT NULL COMMENT '物流公司',
  `TRACKING_NO` varchar(32) DEFAULT NULL COMMENT '快递单号',
  `ORDER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `DISCOUNT_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠金额',
  `FREIGHT` decimal(15,2) DEFAULT NULL COMMENT '运费',
  `PAY_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '实付金额',
  `TRADE_NO` varchar(32) DEFAULT NULL COMMENT '交易号',
  `PAY_MODE` tinyint(4) DEFAULT '4' COMMENT '支付方式（1支付宝2微信3银联4未支付）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '付款时间',
  `DELIVERY_TIME` datetime DEFAULT NULL COMMENT '发货时间',
  `DEAL_TIME` datetime DEFAULT NULL COMMENT '成交时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `VOUCHER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '储值券优惠金额',
  `COUPON_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠券优惠金额',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_3` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_3` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1886 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for sale_order_item
-- ----------------------------
DROP TABLE IF EXISTS `sale_order_item`;
CREATE TABLE `sale_order_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `COMMODITY_IMG` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `COMMODITY_NAME` varchar(127) DEFAULT NULL COMMENT '商品名称',
  `COMMODITY_SHORT_NAME` varchar(255) DEFAULT NULL,
  `PRICE` decimal(15,2) DEFAULT NULL COMMENT '单价',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `DISCOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠',
  `TOTAL` decimal(15,2) DEFAULT NULL COMMENT '小计',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `DELIVERY_MODE` varchar(32) DEFAULT NULL COMMENT '配送方式',
  `LOGISTICS_COMPANY` varchar(32) DEFAULT NULL COMMENT '物流公司',
  `TRACKING_NO` varchar(32) DEFAULT NULL COMMENT '快递单号',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_product_id_7` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_member_id_4` (`MEMBER_ID`) USING BTREE,
  KEY `fk_supplier_id_3` (`SUPPLIER_ID`) USING BTREE,
  KEY `fk_sale_order_id_1` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_4` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`),
  CONSTRAINT `fk_product_id_7` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`),
  CONSTRAINT `fk_supplier_id_3` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2255 DEFAULT CHARSET=utf8 COMMENT='订单项表';

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀活动ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `LABEL` varchar(255) NOT NULL COMMENT '活动标签',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `ACTIVITY_COVER` varchar(255) DEFAULT NULL COMMENT '活动封面',
  `SHARE_TITLE` varchar(255) DEFAULT NULL COMMENT '分享标题',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `ACTIVITY_STOCK` int(11) NOT NULL DEFAULT '0' COMMENT '活动库存',
  `SECKILL_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '秒杀价',
  `LIMIT_QUANTITY` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `LIMIT_PAY_TIME` int(11) NOT NULL DEFAULT '0' COMMENT '支付时限（单位分钟），XX分钟内不支付，自动释放库存',
  `REWARD_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '奖励类型（1积分2优惠券）',
  `REWARD_INTEGRAL` int(11) DEFAULT '0' COMMENT '奖励积分',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `FREIGHT_SET` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运费设置（1卖家包邮2买家承担运费）',
  `FREIGHT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_product_id_57` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_coupon_id_57` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_57` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`),
  CONSTRAINT `fk_product_id_57` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秒杀活动表';

-- ----------------------------
-- Table structure for seckill_record
-- ----------------------------
DROP TABLE IF EXISTS `seckill_record`;
CREATE TABLE `seckill_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒杀活动记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SECKILL_ID` int(11) NOT NULL COMMENT '秒杀活动（seckill表ID）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1成功2失败）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_seckill_id_1` (`SECKILL_ID`) USING BTREE,
  KEY `fk_member_id_39` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_39` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_seckill_id_1` FOREIGN KEY (`SECKILL_ID`) REFERENCES `seckill` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀记录表';

-- ----------------------------
-- Table structure for shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `shipping_address`;
CREATE TABLE `shipping_address` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT 'member表ID',
  `FULL_NAME` varchar(16) NOT NULL COMMENT '姓名',
  `PHONE` varchar(16) NOT NULL COMMENT '手机号',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(8) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(32) DEFAULT NULL COMMENT '详细地址',
  `PREFERRED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认地址（0非默认1默认）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `ZIP_CODE` varchar(16) DEFAULT NULL COMMENT '地址邮编',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_member_id_11` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_11` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- ----------------------------
-- Table structure for shopping_cart_product
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart_product`;
CREATE TABLE `shopping_cart_product` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车商品ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `PRODUCT_NAME` varchar(127) DEFAULT NULL COMMENT '商品名称',
  `PRODUCT_SHORT_NAME` varchar(127) DEFAULT NULL COMMENT '商品简称',
  `QUANTITY` int(11) NOT NULL COMMENT '购买数量',
  `PRICE` decimal(15,2) DEFAULT '0.00' COMMENT '商品价格',
  `ORIGINAL_PRICE` decimal(15,2) DEFAULT NULL COMMENT '原价',
  `DISCOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠金额',
  `DISCOUNT_INFO` varchar(255) DEFAULT NULL COMMENT '优惠信息',
  `IMG_PATH` varchar(255) DEFAULT NULL COMMENT '商品图片',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_20` (`MEMBER_ID`) USING BTREE,
  KEY `fk_product_id_10` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_20` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_product_id_10` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8 COMMENT='购物车商品表';

-- ----------------------------
-- Table structure for sign_time
-- ----------------------------
DROP TABLE IF EXISTS `sign_time`;
CREATE TABLE `sign_time` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '签到时间ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `SIGN_TIME` datetime NOT NULL COMMENT '签到时间',
  `SIGN_DAYS` int(11) NOT NULL DEFAULT '1' COMMENT '持续签到天数',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_22` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_22` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='签到时间表';

-- ----------------------------
-- Table structure for sms_record
-- ----------------------------
DROP TABLE IF EXISTS `sms_record`;
CREATE TABLE `sms_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '接收手机号',
  `TEMPLATE_CODE` varchar(127) DEFAULT NULL COMMENT '短信模板',
  `TEMPLATE_PARAM` varchar(512) DEFAULT NULL COMMENT '短信模板参数',
  `SMS_STATE` tinyint(4) DEFAULT NULL COMMENT '短信状态 1：等待回执，2：发送失败，3：发送成功',
  `SEND_CONTENT` varchar(512) DEFAULT NULL COMMENT '短信内容',
  `SEND_CODE` varchar(127) DEFAULT NULL COMMENT '状态码-返回OK代表请求成功,其他错误码详见错误码列表',
  `SEND_MESSAGE` varchar(512) DEFAULT NULL COMMENT '状态码的描述',
  `SEND_BIZ_ID` varchar(127) DEFAULT NULL COMMENT '发送回执ID,可根据该ID查询具体的发送状态',
  `SEND_DATE` datetime DEFAULT NULL COMMENT '发送时间',
  `RECEIVE_DATE` datetime DEFAULT NULL COMMENT '接收时间',
  `ERROR_CODE` varchar(127) DEFAULT NULL COMMENT '运营商短信错误码',
  `SMS_OUT_ID` varchar(127) DEFAULT NULL COMMENT '外部流水扩展字段',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='短信记录表';

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品（commodity表ID）',
  `PRODUCT_ID` int(11) DEFAULT NULL COMMENT '货品（product表ID）',
  `STOCK_QUANTITY` int(11) DEFAULT '0' COMMENT '库存量',
  `LOCK_QUANTITY` int(11) DEFAULT '0' COMMENT '锁定库存量',
  `USE_QUANTITY` int(11) DEFAULT '0' COMMENT '使用库存量',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_commodity_id_61` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_61` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='库存表';

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户（user表ID）',
  `SUPPLIER_NO` varchar(32) DEFAULT NULL COMMENT '供应商编号',
  `SUPPLIER_NAME` varchar(32) NOT NULL COMMENT '供应商名称',
  `PHONE` varchar(16) NOT NULL COMMENT '手机号码',
  `PASSWORD` varchar(64) NOT NULL COMMENT '密码',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0可用，1禁用）',
  `CONTACT` varchar(16) DEFAULT NULL COMMENT '联系人',
  `CONTACT_PHONE` varchar(16) DEFAULT NULL COMMENT '联系人电话',
  `PROVINCE` varchar(8) DEFAULT NULL COMMENT '省',
  `CITY` varchar(8) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(8) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(64) DEFAULT NULL COMMENT '详细地址',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_user_id_2` (`USER_ID`) USING BTREE,
  CONSTRAINT `fk_user_id_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- ----------------------------
-- Table structure for supplier_check_account
-- ----------------------------
DROP TABLE IF EXISTS `supplier_check_account`;
CREATE TABLE `supplier_check_account` (
  `ID` int(11) NOT NULL COMMENT '供应商对账ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `SUPPLIER_NAME` varchar(32) DEFAULT NULL COMMENT '供应商名称',
  `SALE_ORDER_ID` int(11) NOT NULL COMMENT '订单（sale_order表ID）',
  `SALE_ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `ORDER_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `PRODUCT_NO` varchar(127) DEFAULT NULL COMMENT '商品编号',
  `PRODUCT_NAME` varchar(127) DEFAULT NULL COMMENT '商品名称',
  `SUPPLY_PRICE` decimal(15,2) NOT NULL COMMENT '供货价',
  `QUANTITY` int(11) NOT NULL COMMENT '数量',
  `SETTLEMENT_AMOUNT` decimal(15,2) NOT NULL COMMENT '应结货款',
  `SETTLEMENT_TIME` datetime DEFAULT NULL COMMENT '结算时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_supplier_id_4` (`SUPPLIER_ID`) USING BTREE,
  KEY `fk_sale_order_id_20` (`SALE_ORDER_ID`) USING BTREE,
  KEY `fk_product_id_11` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `fk_product_id_11` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`),
  CONSTRAINT `fk_sale_order_id_20` FOREIGN KEY (`SALE_ORDER_ID`) REFERENCES `sale_order` (`ID`),
  CONSTRAINT `fk_supplier_id_4` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商对账表';

-- ----------------------------
-- Table structure for supplier_sale_stat
-- ----------------------------
DROP TABLE IF EXISTS `supplier_sale_stat`;
CREATE TABLE `supplier_sale_stat` (
  `ID` int(11) NOT NULL COMMENT '供应商销售统计ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ORDER_NUM` int(11) NOT NULL DEFAULT '0' COMMENT '供应商订单数',
  `SALE_AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售额',
  `SUPPLIER_RATIO` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '供应商占比',
  `COST` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '成本',
  `PROFIT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '利润',
  `STAT_TIME` datetime DEFAULT NULL COMMENT '统计时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商销售统计表';

-- ----------------------------
-- Table structure for supplier_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `supplier_withdraw`;
CREATE TABLE `supplier_withdraw` (
  `ID` int(11) NOT NULL COMMENT '供应商提现ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表id）',
  `SUPPLIER_NAME` varchar(32) DEFAULT NULL COMMENT '供应商名称',
  `AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `CARD_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号类型（1线下支付2..）',
  `CARD_NO` varchar(127) DEFAULT NULL COMMENT '账号/卡号/openID',
  `PAYEE` varchar(32) DEFAULT NULL COMMENT '收款人',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '申请状态（1待发放2发放异常3已发放）',
  `ERROR_DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '出错说明',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `DEAL_TIME` datetime DEFAULT NULL COMMENT '发放时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_supplier_id_5` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_5` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商提现表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `DEPT_ID` int(11) DEFAULT NULL COMMENT '部门（dept表ID）',
  `USERNAME` varchar(16) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(32) NOT NULL COMMENT '密码',
  `FULL_NAME` varchar(16) DEFAULT NULL COMMENT '姓名',
  `PHONE` varchar(16) DEFAULT NULL COMMENT '手机号',
  `EMAIL` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `JOB_NO` varchar(32) DEFAULT NULL COMMENT '工号',
  `AVATAR` varchar(255) DEFAULT NULL COMMENT '头像',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_dept_id_1` (`DEPT_ID`) USING BTREE,
  CONSTRAINT `fk_dept_id_1` FOREIGN KEY (`DEPT_ID`) REFERENCES `dept` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `USER_ID` int(11) NOT NULL COMMENT '用户（user表id）',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色（role表id）',
  PRIMARY KEY (`USER_ID`,`ROLE_ID`) USING BTREE,
  KEY `fk_user_id_1` (`USER_ID`) USING BTREE,
  KEY `fk_role_id_1` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `fk_role_id_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ID`),
  CONSTRAINT `fk_user_id_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';






CREATE TABLE `refund_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '退款单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `REFUND_NO` varchar(16) NOT NULL COMMENT '退款单号',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `STATE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '申请状态（1待处理2已处理）',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `MEMBER_PHONE` varchar(16) DEFAULT NULL COMMENT '会员账号',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `ORDER_NO` varchar(16) DEFAULT NULL COMMENT '订单编号',
  `ORDER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `REFUND_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '退款金额',
  `REFUND_MODE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '退款方式（1退回原支付渠道）',
  `REFUND_TYPE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '退款类型（1取消订单（待发货））',
  `REFUND_REASON` varchar(255) DEFAULT NULL COMMENT '退款原因',
  `HANDLE_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '处理备注',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  `PROCESS_TYPE` tinyint(1) DEFAULT '0' COMMENT '处理类型（1确认退·2拒绝退3、未处理)',
  `DISPOSE_TIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_5` (`MEMBER_ID`) USING BTREE,
  KEY `fk_order_id_2` (`ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_5` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_2` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='退款表';

-- 

CREATE TABLE `commodity_attribute_group` (
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `ATTRIBUTE_GROUP_ID` int(11) NOT NULL COMMENT '属性组（attribute_group表ID）',
  PRIMARY KEY (`COMMODITY_ID`,`ATTRIBUTE_GROUP_ID`) USING BTREE,
  KEY `fk_commodity_id_17` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_attribute_group_id_17` (`ATTRIBUTE_GROUP_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_17` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_attribute_group_id_17` FOREIGN KEY (`ATTRIBUTE_GROUP_ID`) REFERENCES `attribute_group` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性组表';






CREATE TABLE `region_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域群ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `NAME` varchar(16) DEFAULT NULL COMMENT '区域群名称',
  `SORT` tinyint(4) DEFAULT '1' COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='区域群表';


CREATE TABLE `commodity_operate_category` (
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `OPERATE_CATEGORY_ID` int(11) NOT NULL COMMENT '运营分类（operate_category表ID）',
  PRIMARY KEY (`COMMODITY_ID`,`OPERATE_CATEGORY_ID`) USING BTREE,
  KEY `fk_commodity_id_11` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_operate_category_id_11` (`OPERATE_CATEGORY_ID`) USING BTREE,
  CONSTRAINT `fk_operate_category_id_11` FOREIGN KEY (`OPERATE_CATEGORY_ID`) REFERENCES `operate_category` (`ID`),
  CONSTRAINT `fk_commodity_id_11` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品运营分类表';


CREATE TABLE `task_config` (
  `ID` int(11) NOT NULL COMMENT '任务配置ID',
  `NAME` varchar(127) DEFAULT NULL COMMENT '任务名称',
  `CLAZZ` varchar(255) DEFAULT NULL COMMENT '类全名',
  `METHOD` varchar(127) DEFAULT NULL COMMENT '方法',
  `CRON` varchar(255) DEFAULT NULL COMMENT '表达式',
  `SITE` varchar(127) DEFAULT NULL COMMENT '运行站点',
  `ACTIVE` tinyint(1) DEFAULT '0' COMMENT '是否激活（0激活1未激活）',
  `LAST_EXECUTE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后执行时间',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务配置表';


-- 供应商账户表
CREATE TABLE `supplier_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '账户金额-待定字段',
  `BALANCE` decimal(15,2) DEFAULT '0.00' COMMENT '账户余额',
  `FREEZE_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '冻结金额',
  `WITHDRAW_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '提现金额',
  `CREATE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_supplier_id_7` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_7` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商账户表';


CREATE TABLE `supplier_account_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `OPERATE_TYPE` tinyint(4) DEFAULT NULL COMMENT '操作类型（1收入2支出）',
  `SERIAL_NO` varchar(16) DEFAULT NULL COMMENT '流水号',
  `TRADE_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '交易金额',
  `BALANCE` decimal(15,2) DEFAULT NULL COMMENT '账户余额',
  `TRADE_TYPE` tinyint(4) DEFAULT NULL COMMENT '交易类型（1订单收入2提现）',
  `TRADE_TIME` datetime DEFAULT NULL COMMENT '交易时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_supplier_id_8` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_8` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='供应商账户记录表';


-- 图库分组表
CREATE TABLE `gallery_group` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `USER_ID` int(11) DEFAULT NULL COMMENT '用户（user表ID）',
  `CODE` varchar(16) DEFAULT NULL COMMENT '分组编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '分组名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_user_id_11` (`USER_ID`) USING BTREE,
  CONSTRAINT `fk_user_id_11` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='图库分组表';





-- 运费模板配置表
CREATE TABLE `freight_template_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '运费模板配置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CONFIG_NO` varchar(16) DEFAULT NULL COMMENT '模板配置编号',
  `CONFIG_NAME` varchar(127) NOT NULL COMMENT '模板配置名称',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `DELIVERY_TIME` tinyint(4) DEFAULT '1' COMMENT '发货时间（1-12小时内，2-24小时内，3-1天内，4-3天内，5-5天内）',
  `TIME_UNIT` tinyint(4) DEFAULT '1' COMMENT '发货时间单位（1小时，2天）',
  `FREIGHT_TYPE` tinyint(4) DEFAULT '1' COMMENT '运费类型（1自定义运费，2卖家承担运费）',
  `CHARGE_MODE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '计价方式（1按件数，2按重量，3按体积）',
  `DELIVERY_MODE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运送方式（1快递，2EMS，3平邮）',
  `FREE_CONDITION` tinyint(4) DEFAULT '0' COMMENT '包邮条件（0非指定条件1指定条件）',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='运费模板配置表';


-- 自定义运费模板
CREATE TABLE `custom_freight_template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '正常运费模板ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `FREIGHT_TEMPLATE_CONFIG_ID` int(11) DEFAULT NULL COMMENT '运费模板配置（freight_template_config表ID）',
  `DELIVERY_MODE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运送方式（1快递，2EMS，3平邮）',
  `FIRST_QUANTITY` decimal(15,2) DEFAULT '0.00' COMMENT '首件/重/体积',
  `FIRST_FEE` decimal(15,2) DEFAULT '0.00' COMMENT '首费',
  `EXTRA_QUANTITY` decimal(15,2) DEFAULT '0.00' COMMENT '续件/重/体积',
  `EXTRA_FEE` decimal(15,2) DEFAULT '0.00' COMMENT '续费',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_freight_template_config_id_1` (`FREIGHT_TEMPLATE_CONFIG_ID`) USING BTREE,
  CONSTRAINT `fk_freight_template_config_id_1` FOREIGN KEY (`FREIGHT_TEMPLATE_CONFIG_ID`) REFERENCES `freight_template_config` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='自定义运费模板表';

-- 包邮运费模板
DROP TABLE IF EXISTS `free_freight_template`;
CREATE TABLE `free_freight_template` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '包邮运费模板ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `FREIGHT_TEMPLATE_CONFIG_ID` int(11) DEFAULT NULL COMMENT '运费模板配置（freight_template_config表ID）',
  `DELIVERY_MODE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '运送方式（1快递，2EMS，3平邮）',
  `FREE_TYPE` tinyint(4) DEFAULT '1' COMMENT '包邮条件类型(1件数，2金额，3件数+金额)',
  `FULL_QUANTITY` tinyint(4) DEFAULT '1' COMMENT '满XX件包邮',
  `FULL_AMOUT` decimal(15,2) DEFAULT '0.00' COMMENT '满XX元包邮',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `fk_freight_template_config_id_2` (`FREIGHT_TEMPLATE_CONFIG_ID`) USING BTREE,
  CONSTRAINT `fk_freight_template_config_id_2` FOREIGN KEY (`FREIGHT_TEMPLATE_CONFIG_ID`) REFERENCES `freight_template_config` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='包邮运费模板';

--运费模板地区表 
DROP TABLE IF EXISTS `custom_freight_template_region`;
CREATE TABLE `custom_freight_template_region` (
  `CUSTOM_FREIGHT_TEMPLATE_ID` int(11) NOT NULL COMMENT '自定义运费模板（custom_freight_template表ID）',
  `REGION_ID` int(11) NOT NULL COMMENT '地区（region表ID）',
  PRIMARY KEY (`CUSTOM_FREIGHT_TEMPLATE_ID`,`REGION_ID`),
  KEY `fk_custom_freight_template_id_1` (`CUSTOM_FREIGHT_TEMPLATE_ID`) USING BTREE,
  KEY `fk_region_id_13` (`REGION_ID`) USING BTREE,
  CONSTRAINT `fk_custom_freight_template_id_1` FOREIGN KEY (`CUSTOM_FREIGHT_TEMPLATE_ID`) REFERENCES `custom_freight_template` (`ID`),
  CONSTRAINT `fk_region_id_13` FOREIGN KEY (`REGION_ID`) REFERENCES `region` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义运费模板地区表';

-- 包邮运费模板地区表
DROP TABLE IF EXISTS `free_freight_template_region`;
CREATE TABLE `free_freight_template_region` (
  `FREE_FREIGHT_TEMPLATE_ID` int(11) NOT NULL COMMENT '包邮运费模板（free_freight_template表ID）',
  `REGION_ID` int(11) NOT NULL COMMENT '地区（region表ID）',
  PRIMARY KEY (`FREE_FREIGHT_TEMPLATE_ID`,`REGION_ID`),
  KEY `fk_free_freight_template_id_1` (`FREE_FREIGHT_TEMPLATE_ID`) USING BTREE,
  KEY `fk_region_id_14` (`REGION_ID`) USING BTREE,
  CONSTRAINT `fk_freight_template_id_1` FOREIGN KEY (`FREE_FREIGHT_TEMPLATE_ID`) REFERENCES `free_freight_template` (`ID`),
  CONSTRAINT `fk_region_id_14` FOREIGN KEY (`REGION_ID`) REFERENCES `region` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='包邮运费模板地区表';


-- 仓库表
CREATE TABLE `warehouse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `CODE` varchar(16) DEFAULT NULL COMMENT '仓库编码',
  `NAME` varchar(32) NOT NULL COMMENT '仓库名称',
  `PROVINCE` varchar(16) DEFAULT NULL COMMENT '省',
  `CITY` varchar(16) DEFAULT NULL COMMENT '市',
  `DISTRICT` varchar(16) DEFAULT NULL COMMENT '区',
  `ADDRESS` varchar(127) DEFAULT NULL COMMENT '地址',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库表';


-- 祝福语
CREATE TABLE `bless_word` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '祝福语ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `WORD` varchar(127) NOT NULL COMMENT '祝福语',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='祝福语表';


-- 礼物表
CREATE TABLE `gift` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '礼物ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `GIFT_NO` varchar(32) DEFAULT NULL COMMENT '礼物编号',
  `MYSTERY_GIFT` tinyint(4) DEFAULT '0' COMMENT '神秘礼物（0否1是）',
  `GIFT_MODE` tinyint(4) DEFAULT '0' COMMENT '送礼方式（0随机送1顺序送2全部送）',
  `BLESS_WORD_ID` int(11) NOT NULL COMMENT '祝福语（bless_word表ID）',
  `CUSTOM_BLESS_WORD` varchar(127) DEFAULT NULL COMMENT '自定义祝福语',
  `GIFT_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '礼物金额',
  `PAY_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '实付金额',
  `TRADE_NO` varchar(64) DEFAULT NULL COMMENT '微信/支付宝交易号',
  `PAY_MODE` tinyint(4) DEFAULT '0' COMMENT '支付方式（0未支付1支付宝2微信3银联卡）',
  `ORDER_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '付款时间',
  `REFUND_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0有效1失效）',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_88` (`MEMBER_ID`) USING BTREE,
  KEY `fk_bless_word_id_1` (`BLESS_WORD_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_88` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
  CONSTRAINT `fk_bless_word_id_1` FOREIGN KEY (`BLESS_WORD_ID`) REFERENCES `bless_word` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='礼物表';

-- 礼物项表
CREATE TABLE `gift_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '礼物项ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `GIFT_ID` int(11) NOT NULL COMMENT '礼物（gift表ID）',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品(commodity表ID)',
  `MYSTERY_GIFT` tinyint(4) DEFAULT '0' COMMENT '神秘礼物（0否1是）',
  `GIFT_MODE` tinyint(4) DEFAULT '0' COMMENT '送礼方式（0随机送1顺序送2全部送）',
  `BLESS_WORD` varchar(127) DEFAULT NULL COMMENT '祝福语',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '货品（product表ID）',
  `PRICE` decimal(15,2) DEFAULT NULL COMMENT '单价',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `SUBTOTAL` decimal(15,2) DEFAULT NULL COMMENT '小计金额',
  `GIFT_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '礼物金额',
  `PAY_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '实付金额',
  `TRADE_NO` varchar(64) DEFAULT NULL COMMENT '微信/支付宝交易号',
  `PAY_MODE` tinyint(4) DEFAULT '0' COMMENT '支付方式（0未支付1支付宝2微信3银联卡）',
  `ORDER_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '付款时间',
  `REFUND_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_89` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_89` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='礼物项表';



-- 送礼订单
CREATE TABLE `gift_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '礼物订单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `SUPPLIER_ID` int(11) DEFAULT NULL COMMENT '供应商（supplier表ID）',
  `PARENT_ORDER_NO` varchar(32) DEFAULT NULL COMMENT '总单订单号',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单状态（1待付款 2已付款待发货 3已发货待收货 4已收货已完成 5已关闭已取消）',
  `COMMENT_STATE` tinyint(4) DEFAULT NULL COMMENT '评价状态（默认为空订单未完成 1待评价2已评价）',
  `AFTER_SALE_STATE` tinyint(4) DEFAULT NULL COMMENT '售后状态（1可申请2申请中3已申请4已过期）',
  `BLESS_INFO` varchar(127) DEFAULT NULL COMMENT '祝福语',
  `ORDER_TYPE` tinyint(4) DEFAULT NULL COMMENT '0默认订单  1开团   2参团',
  `BUYER_MESSAGE` varchar(255) DEFAULT NULL COMMENT '买家留言',
  `CONSIGNEE` varchar(16) DEFAULT NULL COMMENT '收货人',
  `CONSIGNEE_PHONE` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `CONSIGNEE_ADDR` varchar(127) DEFAULT NULL COMMENT '收货人地址',
  `DELIVERY_MODE` varchar(32) DEFAULT NULL COMMENT '配送方式',
  `LOGISTICS_COMPANY` varchar(32) DEFAULT NULL COMMENT '物流公司',
  `TRACKING_NO` varchar(32) DEFAULT NULL COMMENT '快递单号',
  `ORDER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `DISCOUNT_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '优惠金额',
  `FREIGHT` decimal(15,2) DEFAULT NULL COMMENT '运费',
  `PAY_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '实付金额',
  `COUPON_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠券优惠金额',
  `VOUCHER_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '储值券优惠金额',
  `TRADE_NO` varchar(32) DEFAULT NULL COMMENT '交易号',
  `PAY_MODE` tinyint(4) DEFAULT '4' COMMENT '支付方式（1支付宝2微信3银联4未支付）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `PAYMENT_TIME` datetime DEFAULT NULL COMMENT '付款时间',
  `DELIVERY_TIME` datetime DEFAULT NULL COMMENT '发货时间',
  `DEAL_TIME` datetime DEFAULT NULL COMMENT '成交时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `READ_TYPE` tinyint(4) DEFAULT '0' COMMENT '供应商阅读状态（0未阅读1已阅读）',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_3` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_3` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2614 DEFAULT CHARSET=utf8 COMMENT='礼物订单表';


CREATE TABLE `sale_order_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '礼物订单项ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `ORDER_ID` int(11) NOT NULL COMMENT '订单（order表ID）',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品(commodity表ID)',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '商品（product表ID）',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `PRICE` decimal(15,2) DEFAULT NULL COMMENT '单价',
  `QUANTITY` int(11) DEFAULT NULL COMMENT '数量',
  `DISCOUNT` decimal(15,2) DEFAULT NULL COMMENT '优惠',
  `TOTAL` decimal(15,2) DEFAULT NULL COMMENT '小计',
  `LOGISTICS_COMPANY` varchar(32) DEFAULT NULL COMMENT '物流公司',
  `TRACKING_NO` varchar(32) DEFAULT NULL COMMENT '快递单号',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_product_id_7` (`PRODUCT_ID`) USING BTREE,
  KEY `fk_member_id_4` (`MEMBER_ID`) USING BTREE,
  KEY `fk_supplier_id_3` (`SUPPLIER_ID`) USING BTREE,
  KEY `fk_sale_order_id_1` (`ORDER_ID`) USING BTREE,
  KEY `fk_commodity_id_55` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_55` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_member_id_4` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_order_id_1` FOREIGN KEY (`ORDER_ID`) REFERENCES `sale_order` (`ID`),
  CONSTRAINT `fk_product_id_7` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`),
  CONSTRAINT `fk_supplier_id_3` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3118 DEFAULT CHARSET=utf8 COMMENT='礼物订单项表';

-- 发放配置表
CREATE TABLE `coupon_grant_config` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '发放配置ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `GRANT_STRATEGY` tinyint(4) DEFAULT '1' COMMENT '发放策略（1一次性发放，2分批发放）',
  `GRANT_NODE` tinyint(4) DEFAULT '1' COMMENT '一次性发放节点（1购买，2收货，3评论，4超过15天）',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_coupon_id_211` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_id_211` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券发放配置表';

-- 优惠券发放步骤
CREATE TABLE `coupon_grant_step` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '发放步骤ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `COUPON_GRANT_CONFIG_ID` int(11) NOT NULL COMMENT '优惠券发放配置（coupon_grant_config表ID）',
  `GRANT_NODE` tinyint(4) DEFAULT '1' COMMENT '发放节点（1购买，2收货，3评论，4超过15天）',
  `GRANT_RATE` decimal(5,2) NOT NULL COMMENT '发放比例（0.00-100.00）',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_coupon_grant_config_id_1` (`COUPON_GRANT_CONFIG_ID`) USING BTREE,
  CONSTRAINT `fk_coupon_grant_config_id_1` FOREIGN KEY (`COUPON_GRANT_CONFIG_ID`) REFERENCES `coupon_grant_config` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券发放步骤表';

-- 发放记录表
CREATE TABLE `coupon_grant_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '发放记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `COUPON_GRANT_CONFIG_ID` int(11) NOT NULL COMMENT '发放配置（coupon_grant_config表ID）',
  `COUPON_ID` int(11) NOT NULL COMMENT '优惠券（coupon表ID）',
  `GRANT_NODE` tinyint(4) DEFAULT '1' COMMENT '发放节点（1购买，2收货，3评论，4超过15天）',
  `PAR_VALUE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '面值',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_211` (`MEMBER_ID`) USING BTREE,
  KEY `fk_coupon_grant_config_id_2` (`COUPON_GRANT_CONFIG_ID`) USING BTREE,
  KEY `fk_coupon_id_212` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_88` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_coupon_grant_config_id_2` FOREIGN KEY (`COUPON_GRANT_CONFIG_ID`) REFERENCES `coupon_grant_config` (`ID`),
  CONSTRAINT `fk_coupon_id_212` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='优惠券发放记录表';



-- 奖励表
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖励表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CODE` varchar(16) DEFAULT NULL COMMENT '奖励编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '奖励名称',
  `REWARD_TYPE` tinyint(4) DEFAULT NULL COMMENT '奖励类型（1邀请，3评论，2连续签到）',
  `SIGN_DAYS` tinyint(4) DEFAULT '0' COMMENT '连续签到天数',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='奖励表';

-- 奖品表
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖品表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CODE` varchar(16) DEFAULT NULL COMMENT '奖品编码',
  `NAME` varchar(32) DEFAULT NULL COMMENT '奖品名称',
  `PRIZE_TYPE` tinyint(4) DEFAULT NULL COMMENT '奖品类型（1积分，2商品，3优惠券）',
  `INTEGRAL` int(11) DEFAULT NULL COMMENT '积分',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品（commodity表ID）',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_commodity_id_233` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_coupon_id_233` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_233` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_coupon_id_233` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='奖品表';

-- 奖励奖品表
DROP TABLE IF EXISTS `reward_prize`;
CREATE TABLE `reward_prize` (
  `REWARD_ID` int(11) NOT NULL COMMENT '奖励（reward表ID）',
  `PRIZE_ID` int(11) NOT NULL COMMENT '奖品（prize表ID）',
  PRIMARY KEY (`REWARD_ID`,`PRIZE_ID`),
  KEY `fk_reward_id_1` (`REWARD_ID`) USING BTREE,
  KEY `fk_prize_id_1` (`PRIZE_ID`) USING BTREE,
  CONSTRAINT `fk_reward_id_1` FOREIGN KEY (`REWARD_ID`) REFERENCES `reward` (`ID`),
  CONSTRAINT `fk_prize_id_1` FOREIGN KEY (`PRIZE_ID`) REFERENCES `prize` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='奖励奖品表';


-- 开团表
DROP TABLE IF EXISTS `open_group_buy`;
CREATE TABLE `open_group_buy` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '开团表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `GROUP_BUY_ACTIVITY_PRODUCT_ID` int(11) NOT NULL COMMENT '团购商品（group_buy_activity_product表ID）',
  `SALE_ORDER_ID` int(11) NOT NULL COMMENT '订单（订单表ID）',
  `STATE` tinyint(4) NOT NULL COMMENT '团购状态（1待付款，2拼团中，3已成团，4已失效）',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_1121` (`MEMBER_ID`) USING BTREE,
  KEY `fk_group_buy_activity_product_id_1121` (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) USING BTREE,
  KEY `fk_sale_order_id_1121` (`SALE_ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_1121` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_group_buy_activity_product_id_1121` FOREIGN KEY (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) REFERENCES `group_buy_activity_product` (`ID`),
  CONSTRAINT `fk_sale_order_id_1121` FOREIGN KEY (`SALE_ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='开团表';

-- 参团表
DROP TABLE IF EXISTS `join_group_buy`;
CREATE TABLE `join_group_buy` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '参团表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `OPEN_GROUP_BUY_ID` int(11) NOT NULL COMMENT '开团（open_group_buy表ID）',
  `GROUP_BUY_ACTIVITY_PRODUCT_ID` int(11) NOT NULL COMMENT '团购商品（group_buy_activity_product表ID）',
  `SALE_ORDER_ID` int(11) NOT NULL COMMENT '订单（订单表ID）',
  `STATE` tinyint(4) NOT NULL COMMENT '团购状态（1待付款，2拼团中，3已成团，4已失效）',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_1122` (`MEMBER_ID`) USING BTREE,
  KEY `fk_open_group_buy_id_1122` (`OPEN_GROUP_BUY_ID`) USING BTREE,
  KEY `fk_group_buy_activity_product_id_1122` (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) USING BTREE,
  KEY `fk_sale_order_id_1122` (`SALE_ORDER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_1121` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_open_group_buy_id_1122` FOREIGN KEY (`OPEN_GROUP_BUY_ID`) REFERENCES `open_group_buy` (`ID`),
  CONSTRAINT `fk_group_buy_activity_product_id_1122` FOREIGN KEY (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) REFERENCES `group_buy_activity_product` (`ID`),
  CONSTRAINT `fk_sale_order_id_1122` FOREIGN KEY (`SALE_ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='参团表';

CREATE TABLE `group_buy_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '团购单表ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `PARENT_ID` int(11) DEFAULT NULL COMMENT '开团单（group_buy_order表ID）',
  `GROUP_BUY_ACTIVITY_PRODUCT_ID` int(11) NOT NULL COMMENT '团购商品（group_buy_activity_product表ID）',
  `SALE_ORDER_ID` int(11) NOT NULL COMMENT '订单（订单表ID）',
  `STATE` tinyint(4) NOT NULL COMMENT '团购状态（1待付款，2拼团中，3已成团，4已失效，5已退款）',
  `GROUP_TYPE` tinyint(4) DEFAULT NULL COMMENT '团购类型（1开团单，2参团单）',
  `GROUP_NUM` tinyint(4) DEFAULT '0' COMMENT '成团人数',
  `JOIN_NUM` tinyint(4) DEFAULT '0' COMMENT '参团人数',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_1122` (`MEMBER_ID`) USING BTREE,
  KEY `fk_group_buy_activity_product_id_1122` (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) USING BTREE,
  KEY `fk_sale_order_id_1122` (`SALE_ORDER_ID`) USING BTREE,
  KEY `fk_parent_id_1122` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `fk_group_buy_activity_product_id_1122` FOREIGN KEY (`GROUP_BUY_ACTIVITY_PRODUCT_ID`) REFERENCES `group_buy_activity_product` (`ID`),
  CONSTRAINT `fk_member_id_1122` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
  CONSTRAINT `fk_parent_id_1122` FOREIGN KEY (`PARENT_ID`) REFERENCES `group_buy_order` (`ID`),
  CONSTRAINT `fk_sale_order_id_1122` FOREIGN KEY (`SALE_ORDER_ID`) REFERENCES `sale_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='团购单表';


DROP TABLE IF EXISTS `login_record`;
CREATE TABLE `login_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `LOGIN_IP` varchar(16) DEFAULT NULL COMMENT '登录IP',
  `LOGIN_SOURCE` varchar(16) DEFAULT NULL COMMENT '登录来源',
  `SESSION_ID` varchar(16) DEFAULT NULL COMMENT 'session',
  `LOGIN_TIME` datetime DEFAULT NULL COMMENT '登录时间',
  `LOGOUT_TIME` datetime DEFAULT NULL COMMENT '登出时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE,
  KEY `fk_member_id_116` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_116` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- 对账单
DROP TABLE IF EXISTS `supplier_check_account`;
CREATE TABLE `supplier_check_account` (
  `ID` int(11) NOT NULL COMMENT '对账单ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表ID）',
  `SUPPLIER_NAME` varchar(127) DEFAULT NULL COMMENT '供应商名称',
  `SALE_ORDER_ID` int(11) NOT NULL COMMENT '订单（sale_order表ID）',
  `SALE_ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `SALE_ORDER_ITEM_ID` int(11) NOT NULL COMMENT '订单项ID（sale_order_item表ID）',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品(商品表ID)',
  `COMMODITY_NO` varchar(32) DEFAULT NULL COMMENT '商品编号',
  `PRODUCT_ID` int(11) NOT NULL COMMENT '货品（product表ID）',
  `PRODUCT_NO` varchar(32) DEFAULT NULL COMMENT '货品编号',
  `PRODUCT_NAME` varchar(127) DEFAULT NULL COMMENT '货品名称',
  `SUPPLY_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '供货价',
  `SALE_PRICE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `QUANTITY` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `SETTLEMENT_AMOUNT` decimal(15,2) NOT NULL COMMENT '应结货款',
  `ORDER_TIME` datetime DEFAULT NULL COMMENT '下单时间',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `SETTLEMENT_TIME` datetime DEFAULT NULL COMMENT '结算时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_supplier_id_888` (`SUPPLIER_ID`) USING BTREE,
  KEY `fk_sale_order_id_8888` (`SALE_ORDER_ID`) USING BTREE,
  KEY `fk_sale_order_item_id_8888` (`SALE_ORDER_ITEM_ID`) USING BTREE,
  KEY `fk_commodity_id_8888` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_product_id_8888` (`PRODUCT_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_888` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`),
  CONSTRAINT `fk_sale_order_id_8888` FOREIGN KEY (`SALE_ORDER_ID`) REFERENCES `sale_order` (`ID`),
  CONSTRAINT `fk_sale_order_item_id_8888` FOREIGN KEY (`SALE_ORDER_ITEM_ID`) REFERENCES `sale_order_item` (`ID`),
  CONSTRAINT `fk_commodity_id_8888` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_product_id_8888` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商对账表';


DROP TABLE IF EXISTS `feel_word`;
CREATE TABLE `feel_word` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '情怀文案ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `CODE` varchar(16) DEFAULT NULL COMMENT '编码',
  `WORD` varchar(127) DEFAULT NULL COMMENT '文案',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='情怀文案表';


-- 客户消息表
DROP TABLE IF EXISTS `member_message`;
CREATE TABLE `member_message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户消息ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `TITLE` varchar(127) NOT NULL COMMENT '消息标题',
  `CONTENT` text COMMENT '消息内容',
  `MESSAGE_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型（1下单，2支付，3发货，）',
  `READ_STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '阅读状态（0未读1已读）',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '显示（0显示1不显示）',
  `READ_TIME` datetime DEFAULT NULL COMMENT '阅读时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '发送时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_member_id_320` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_320` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='客户消息表';


DROP TABLE IF EXISTS `supplier_withdraw`;
CREATE TABLE `supplier_withdraw` (
  `ID` int(11) NOT NULL COMMENT '供应商提现ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SUPPLIER_ID` int(11) NOT NULL COMMENT '供应商（supplier表id）',
  `SUPPLIER_NAME` varchar(32) DEFAULT NULL COMMENT '供应商名称',
  `PAYEE` varchar(16) DEFAULT NULL COMMENT '收款人',
  `RECEIPTS_NO` varchar(32) DEFAULT NULL COMMENT '收款账号',
  `RECEIPTS_NAME` varchar(32) DEFAULT NULL COMMENT '收款名称',
  `APPLY_AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '申请提现金额',
  `ACTUAL_AMOUNT` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '实际到账金额',
  `SERVICE_CHARGE` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '手续费',
  `DRAWEE` varchar(16) DEFAULT NULL COMMENT '付款人',
  `PAYMENTS_NO` varchar(32) DEFAULT NULL COMMENT '付款账号',
  `PAYMENTS_NAME` varchar(32) DEFAULT NULL COMMENT '付款名称',
  `PAYMENT_METHOD` tinyint(4) NOT NULL DEFAULT '1' COMMENT '付款方式（1线下转账2线上转账）',
  `STATE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '提现状态（1待发放，2，驳回，3已发放，4发放异常）',
  `ERROR_DESC` varchar(255) DEFAULT NULL COMMENT '错误说明',
  `APPLY_TIME` datetime DEFAULT NULL COMMENT '申请时间',
  `GRANT_TIME` datetime DEFAULT NULL COMMENT '发放时间',
  `DELETED` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_supplier_id_5` (`SUPPLIER_ID`) USING BTREE,
  CONSTRAINT `fk_supplier_id_5` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商提现表';

-- 邀请有礼活动
DROP TABLE IF EXISTS `invite_activity`;
CREATE TABLE `invite_activity` (
  `ID` int(11) NOT NULL COMMENT '邀请有礼活动ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(32) DEFAULT NULL COMMENT '标题',
  `BANNER` varchar(255) DEFAULT NULL COMMENT 'banner图',
  `RULE` text COMMENT '活动规则',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `INVITE_FLAG` tinyint(1) NOT NULL DEFAULT '1' COMMENT '邀请成功标示（1注册，2购买）',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请有礼活动表';

-- 邀请有礼 奖励表
DROP TABLE IF EXISTS `invite_reward`;
CREATE TABLE `invite_reward` (
  `ID` int(11) NOT NULL COMMENT '邀请奖品ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `INVITE_ACTIVITY_ID` int(11) NOT NULL COMMENT '邀请有礼活动（invite_activity表id）',
  `REWARD_ID` int(11) NOT NULL COMMENT '奖励（reward表id）',
  `SORT` tinyint(1) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_invite_activity_id_1` (`INVITE_ACTIVITY_ID`) USING BTREE,
  KEY `fk_reward_id_6` (`REWARD_ID`) USING BTREE,
  CONSTRAINT `fk_invite_activity_id_1` FOREIGN KEY (`INVITE_ACTIVITY_ID`) REFERENCES `invite_activity` (`ID`),
  CONSTRAINT `fk_reward_id_6` FOREIGN KEY (`REWARD_ID`) REFERENCES `reward` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请有礼奖励表';


-- 积分抵现设置
DROP TABLE IF EXISTS `integral_cash`;
CREATE TABLE `integral_cash` (
  `ID` int(11) NOT NULL COMMENT '积分抵现ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `INTEGRAL` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `CASH` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '抵现金额',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分抵现表';


-- 邀请奖品表
DROP TABLE IF EXISTS `invite_prize`;
CREATE TABLE `invite_prize` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '邀请奖品ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `INVITE_ACTIVITY_ID` int(11) NOT NULL COMMENT '邀请有礼活动（invite_activity表id）',
  `PRIZE_NO` varchar(16) DEFAULT NULL COMMENT '奖品编码',
  `PRIZE_NAME` varchar(32) DEFAULT NULL COMMENT '奖品名称',
  `INVITE_NUM` tinyint(4) DEFAULT '0' COMMENT '邀请人数',
  `PRIZE_TYPE` tinyint(4) DEFAULT NULL COMMENT '奖品类型（1积分，2商品，3优惠券）',
  `INTEGRAL` int(11) DEFAULT NULL COMMENT '积分',
  `COMMODITY_ID` int(11) DEFAULT NULL COMMENT '商品（commodity表ID）',
  `COUPON_ID` int(11) DEFAULT NULL COMMENT '优惠券（coupon表ID）',
  `SORT` tinyint(1) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_invite_activity_id_2` (`INVITE_ACTIVITY_ID`) USING BTREE,
  KEY `fk_commodity_id_266` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_coupon_id_266` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_invite_activity_id_2` FOREIGN KEY (`INVITE_ACTIVITY_ID`) REFERENCES `invite_activity` (`ID`),
   CONSTRAINT `fk_commodity_id_266` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_coupon_id_266` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请有礼奖品表';

-- 邀请记录表
DROP TABLE IF EXISTS `invite_record`;
CREATE TABLE `invite_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '邀请记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `INVITER_ID` int(11) NOT NULL COMMENT '邀请人（member表id）',
  `INVITEE_ID` varchar(16) DEFAULT NULL COMMENT '被邀请人（member表id）',
  
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_invite_activity_id_2` (`INVITE_ACTIVITY_ID`) USING BTREE,
  KEY `fk_commodity_id_266` (`COMMODITY_ID`) USING BTREE,
  KEY `fk_coupon_id_266` (`COUPON_ID`) USING BTREE,
  CONSTRAINT `fk_invite_activity_id_2` FOREIGN KEY (`INVITE_ACTIVITY_ID`) REFERENCES `invite_activity` (`ID`),
   CONSTRAINT `fk_commodity_id_266` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_coupon_id_266` FOREIGN KEY (`COUPON_ID`) REFERENCES `coupon` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请有礼奖品表';

-- 会员资产账户
DROP TABLE IF EXISTS `member_account`;
CREATE TABLE `member_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `DEAL_QUANTITY` int(11) DEFAULT '0' COMMENT '交易单数',
  `DEAL_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '交易金额',
  `TOTAL_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '账户总额',
  `USED_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '已用金额',
  `AVAILABLE_AMOUNT` decimal(15,2) DEFAULT '0.00' COMMENT '可用余额',
  `TOTAL_INTEGRAL` int(11) DEFAULT '0' COMMENT '总积分',
  `USED_INTEGRAL` int(11) DEFAULT '0' COMMENT '已用积分',
  `AVAILABLE_INTEGRAL` int(11) DEFAULT '0' COMMENT '可用积分',
  `TOTAL_COMMISSION` decimal(15,2) DEFAULT '0.00' COMMENT '总佣金',
  `CASHED_COMMISSION` decimal(15,2) DEFAULT '0.00' COMMENT '已提现佣金',
  `CASHABLE_COMMISSION` decimal(15,2) DEFAULT '0.00' COMMENT '可提现佣金',
  `UNSETTLED_COMMISSION` decimal(15,2) DEFAULT '0.00' COMMENT '未结算佣金',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_member_id_88` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_88` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='会员资产账户表';

-- 会员资产账户记录表
DROP TABLE IF EXISTS `member_account_record`;
CREATE TABLE `member_account_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户记录ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `MEMBER_ID` int(11) NOT NULL COMMENT '会员（member表ID）',
  `SALE_ORDER_ID` int(11) DEFAULT NULL COMMENT '订单（sale_order表ID）',
  `CONTRIBUTOR` varchar(32) DEFAULT NULL COMMENT '贡献者',
  `AVATER` varchar(255) DEFAULT NULL COMMENT '头像',
  `SERIAL_NO` varchar(16) DEFAULT NULL COMMENT '流水号',
  `OPERATE_TYPE` tinyint(4) DEFAULT NULL COMMENT '操作类型（1收入2支出）',
  `TRADE_TYPE` tinyint(4) DEFAULT NULL COMMENT '交易类型（1佣金2在线支付3提现4小区提成5送礼支付6退款）',
  `TRADE_AMOUNT` decimal(15,2) DEFAULT NULL COMMENT '交易金额',
  `TRADE_TIME` datetime DEFAULT NULL COMMENT '交易时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` varchar(127) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_member_id_8` (`MEMBER_ID`) USING BTREE,
  CONSTRAINT `fk_member_id_8` FOREIGN KEY (`MEMBER_ID`) REFERENCES `member` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=738 DEFAULT CHARSET=utf8 COMMENT='会员资产账户记录表';


--  销售专区
DROP TABLE IF EXISTS `sales_area`;
CREATE TABLE `sales_area` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '销售专区ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `TITLE` varchar(32) DEFAULT NULL COMMENT '标题',
  `BANNER` varchar(255) DEFAULT NULL COMMENT 'banner图',
  `SHOW_MODE` tinyint(1) DEFAULT NULL COMMENT '排列方式（1一排展一个，2一排展两个）',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=738 DEFAULT CHARSET=utf8 COMMENT='销售专区表';

--  专区栏目
DROP TABLE IF EXISTS `area_column`;
CREATE TABLE `area_column` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '专区栏目ID',
  `GUID` varchar(32) DEFAULT NULL COMMENT 'GUID',
  `SALES_AREA_ID` int(11) NOT NULL COMMENT '销售专区（sales_area表ID）',
  `TITLE` varchar(32) DEFAULT NULL COMMENT '标题',
  `BANNER` varchar(255) DEFAULT NULL COMMENT 'banner图',
  `SHOW_MODE` tinyint(1) DEFAULT NULL COMMENT '排列方式（1一排展一个，2一排展两个）',
  `SORT` tinyint(1) DEFAULT '0' COMMENT '排序',
  `STATE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态（0启用1禁用）',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DELETED` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除（0否1是）',
  `DEL_TIME` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `UNIQUE_ID` (`ID`) USING HASH,
  KEY `fk_sales_area_1` (`SALES_AREA_ID`) USING BTREE,
  CONSTRAINT `fk_sales_area_1` FOREIGN KEY (`SALES_AREA_ID`) REFERENCES `sales_area` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='专区栏目表';

-- 销售专区商品
DROP TABLE IF EXISTS `sales_area_commodity`;
CREATE TABLE `sales_area_commodity` (
  `SALES_AREA_ID` int(11) NOT NULL COMMENT '销售专区（sales_area表ID）',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`SALES_AREA_ID`,`COMMODITY_ID`),
  KEY `fk_sales_area_id_11` (`SALES_AREA_ID`) USING BTREE,
  KEY `fk_commodity_id_61` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_61` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_sales_area_id_11` FOREIGN KEY (`SALES_AREA_ID`) REFERENCES `sales_area` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售专区商品表';


-- 专区栏目商品
DROP TABLE IF EXISTS `area_column_commodity`;
CREATE TABLE `area_column_commodity` (
  `AREA_COLUMN_ID` int(11) NOT NULL COMMENT '专区栏目（area_column表ID）',
  `COMMODITY_ID` int(11) NOT NULL COMMENT '商品（commodity表ID）',
  `SORT` tinyint(4) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`AREA_COLUMN_ID`,`COMMODITY_ID`),
  KEY `fk_area_column_id_11` (`AREA_COLUMN_ID`) USING BTREE,
  KEY `fk_commodity_id_71` (`COMMODITY_ID`) USING BTREE,
  CONSTRAINT `fk_commodity_id_71` FOREIGN KEY (`COMMODITY_ID`) REFERENCES `commodity` (`ID`),
  CONSTRAINT `fk_area_column_id_11` FOREIGN KEY (`AREA_COLUMN_ID`) REFERENCES `area_column` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区栏目商品表';


