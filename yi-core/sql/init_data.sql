-- 初始化 数据
-- 用户
INSERT INTO `user` VALUES ('1', 'eac02f365e04444f802f365e04444f37', NULL, 'admin', '123456', '管理员', '18812345678', '18812345678@163.com', '10000', '', '0', NOW(), '0', null);

--  位置
INSERT INTO `position` VALUES ('1', '7a0ce724f75c4c888ce724f75cec881a', '首页轮播图', '1', '首页轮播图', '0', NOW(), '0', null);
INSERT INTO `position` VALUES ('2', 'dd595cef42fe4daf995cef42fefdaf51', '楼层推荐', '2', '楼层推荐', '0', NOW(), '0', null);
INSERT INTO `position` VALUES ('3', '09e50002b630426ea50002b630026e56', '今日推荐位', '3', '今日推荐位', '0', NOW(), '0', null);
INSERT INTO `position` VALUES ('4', '0dd5e247a49e420295e247a49ed20207', '购物车推荐位', '4', '购物车推荐位', '0', NOW(), '0', null);
INSERT INTO `position` VALUES ('5', 'de275dac7cb24672a75dac7cb2967246', '邀请有礼推荐位', '5', '邀请有礼推荐位', '0', NOW(), '0', null);
INSERT INTO `position` VALUES ('6', 'a833609a83154226b3609a8315d22625', '优惠券推荐位', '6', '优惠券推荐位', '0', NOW(), '0', null);

-- 资源
INSERT  INTO `resc`(`ID`,`GUID`,`PARENT_ID`,`CODE`,`NAME`,`URL`,`CREATE_TIME`,`DELETED`,`DEL_TIME`) VALUES
(1,'',NULL,'','平台数据','homePageManage',NOW(),0,NULL),
  
(2,'',NULL,'','地区管理','regionManage',NOW(),0,NULL),
(3,'',2,'','区域列表','regionGroup/query',NOW(),0,NULL),
(4,'',NULL,'','小区管理','communityManage',NOW(),0,NULL),
(5,'',4,'','小区列表','community/query',NOW(),0,NULL),  
  
(6,'',NULL,'','商品分类','categoryManage',NOW(),0,NULL),
(7,'',6,'','商品分类','category/query',NOW(),0,NULL),
(8,'',6,'','运营分类','operateCategory/getAll',NOW(),0,NULL),
  
(9,'',NULL,'','商品管理','commodityManage',NOW(),0,NULL),
(10,'',9,'','商品列表','commodity/query',NOW(),0,NULL),
(11,'',9,'','商品属性','attributeGroup/query',NOW(),0,NULL),
(12,'',9,'','商品审核','commodity/query',NOW(),0,NULL),
(13,'',9,'','商品评价','comment/query',NOW(),0,NULL), 
  
(14,'',NULL,'','库存管理','stockManage',NOW(),0,NULL),     
(15,'',14,'','库存列表','stock/list',NOW(),0,NULL),

(16,'',NULL,'','订单设置','orderSettingManage',NOW(),0,NULL),
(17,'',16,'','订单设置','orderSetting/query',NOW(),0,NULL),
(18,'',16,'','退货原因设置','returnReason/query',NOW(),0,NULL),
  
(19,'',NULL,'','订单管理','orderManage',NOW(),0,NULL),
(20,'',19,'','订单列表','saleOrder/query',NOW(),0,NULL),
(21,'',19,'','退款处理','refundOrder/query',NOW(),0,NULL),
(22,'',19,'','退货处理','returnOrder/query',NOW(),0,NULL),
(23,'',19,'','订单日志','orderLog/query',NOW(),0,NULL),
  
(24,'',NULL,'','快递管理','expressManage',NOW(),0,NULL),
(25,'',24,'','运费模板设置','freightTemplate/query',NOW(),0,NULL),
  
(26,'',NULL,'','会员管理','memberManage',NOW(),0,NULL),
(27,'',NULL,26,'会员列表','member/query',NOW(),0,NULL),
(28,'',NULL,26,'会员等级','memberLevel/query',NOW(),0,NULL),

(29,'',NULL,'','积分管理','integralManage',NOW(),0,NULL),
(30,'',29,'','积分记录','integralRecord/query',NOW(),0,NULL),
(31,'',29,'','积分设置','integralTask/query',NOW(),0,NULL),

(32,'',NULL,'','内容管理','contentManage',NOW(),0,NULL),
(33,'',32,'','爱生活','article/query',NOW(),0,NULL),
(34,'',32,'','爱生活评论','articleComment/query',NOW(),0,NULL),

(35,'',NULL,'','营销管理','marketingManage',NOW(),0,NULL),
(36,'',35,'','满减券','coupon/query',NOW(),0,NULL),
(37,'',35,'','买送券','coupon/query',NOW(),0,NULL),
(38,'',35,'','储值券列表','coupon/query',NOW(),0,NULL),

(39,'',NULL,'','活动管理','activityManage',NOW(),0,NULL),
(40,'',39,'','大转盘','--',NOW(),0,NULL),
(41,'',39,'','大富翁','--',NOW(),0,NULL),
(42,'',39,'','刮刮乐','--',NOW(),0,NULL),

(43,'',NULL,'','拼团管理','groupManage',NOW(),0,NULL),
(44,'',43,'','全国拼团','nationalGroup/query',NOW(),0,NULL),
(45,'',43,'','小区拼团','communityGroup/query',NOW(),0,NULL),
(46,'',43,'','返现拼团','rebateGroup/query',NOW(),0,NULL),
(47,'',43,'','秒杀活动','seckill/query',NOW(),0,NULL),

(48,'',NULL,'','推荐系统','recommendManage',NOW(),0,NULL),
(49,'',48,'','位置管理','position/query',NOW(),0,NULL),
(50,'',48,'','推荐管理','recommend/query',NOW(),0,NULL),
(51,'',48,'','广告管理','advertisement/query',NOW(),0,NULL),

(52,'',NULL,'','基础信息','basicInfoManage',NOW(),0,NULL),
(53,'',52,'','基础信息','basicInfo/getAll',NOW(),0,NULL),
(54,'',52,'','消息通知','message/query',NOW(),0,NULL),
(55,'',52,'','基础规则','basicRule/getAll',NOW(),0,NULL),
(56,'',52,'','问题类型','questionType/query',NOW(),0,NULL),
(57,'',52,'','班助列表','question/query',NOW(),0,NULL),

(58,'',NULL,'','财务信息','financeManage',NOW(),0,NULL),
(59,'',58,'','交易记录','consumeRecord/query',NOW(),0,NULL),
(60,'',58,'','供应商销售统计','platformSaleStat/getSupplierSaleStatsByTime',NOW(),0,NULL),
(61,'',58,'','供应商提现申请','supplierWithdraw/query',NOW(),0,NULL),
(62,'',58,'','供应商提现记录','supplierWithdraw/query',NOW(),0,NULL),
(63,'',58,'','供应商对账明细','supplierCheckAccount/query',NOW(),0,NULL),

(64,'',NULL,'','管理员管理','administratorsManage',NOW(),0,NULL),
(65,'',64,'','成员管理','user/query',NOW(),0,NULL),
(66,'',64,'','角色管理','role/query',NOW(),0,NULL),
(67,'',64,'','部门管理','dept/query',NOW(),0,NULL),

(68,'',NULL,'','供应商管理','supplierManage',NOW(),0,NULL),
(69,'',68,'','供应商列表','supplier/query',NOW(),0,NULL),
(70,'',68,'','供应商概况','supplier/querySupplierData',NOW(),0,NULL),

-- 订单设置
INSERT INTO `order_setting`  VALUES ('1', 'aea872570f4f4db3873f87d798308484', '1', '30', '1', '0', '0', '30', NOW(), '0', NULL);
INSERT INTO `order_setting`  VALUES ('2', '36354ed0080e415db95c519df590227d', '2', '30', '1', '0', '0', '30', NOW(), '0', NULL);
INSERT INTO `order_setting`  VALUES ('3', '78dc1e34e30548b1a716c017a091fdc6', '3', '5', '3', '3', '0', '0', NOW(), '0', NULL);
INSERT INTO `order_setting`  VALUES ('4', '6da1d3dd8b7c4e35b176834aec433b62', '4', '7', '3', '7', '0', '0', NOW(), '0', NULL);
INSERT INTO `order_setting`  VALUES ('5', 'b9b9f2a8ec814c508ebbc99192ab2456', '5', '5', '3', '5', '0', '0', NOW(), '0', NULL);

-- 积分设置
INSERT INTO `integral_task` VALUES (1, '0942699092e3474b856b588741d49570', '签到', 20, 0, NOW(), 0, NULL);
INSERT INTO `integral_task` VALUES (2, 'a6bf53e6fbb1498f8071bdd987a4fa7b', '邀请好友', 100, 0, NOW(), 0, NULL);
INSERT INTO `integral_task` VALUES (3, '357d382ad1b1443d93e6b38825234c06', '评论', 30, 0, NOW(), 0, NULL);

--等级
INSERT INTO `member_level` VALUES ('1', '1ef4be20a9634c9fb4be20a963fc9fb6', '普通会员', '0', '0', '97.00', '1', NOW(), '0', NULL);




