
-- 更新订单项的 商品
UPDATE sale_order_item t1
LEFT JOIN product t2 ON t1.product_id = t2.id
LEFT JOIN commodity t3 ON t2.commodity_id = t3.id
SET t1.COMMODITY_ID = t3.id
WHERE
	t1.COMMODITY_ID IS NULL;

-- 修正地区数据
UPDATE region t1
LEFT JOIN area t2 ON t1.PROVINCE = t2.NAME
SET t1.AREA_ID = t2.id
WHERE
	t2.id IS NOT NULL;
	
	
-- 修正积分记录 非积分任务情况
UPDATE integral_record
SET TASK_NAME = '订单'
WHERE
	INTEGRAL_TASK_ID IS NULL;

-- 修正积分记录 积分任务情况
UPDATE integral_record t1
LEFT JOIN integral_task t2 ON t1.INTEGRAL_TASK_ID = t2.id
SET t1.TASK_NAME = t2.TASK_NAME
WHERE
	t1.INTEGRAL_TASK_ID IS NOT NULL;
	
-- 修正 积分记录 类型
UPDATE integral_record
SET TASK_TYPE = IF(
	INTEGRAL_TASK_ID IS NOT NULL,
	INTEGRAL_TASK_ID,
	4
)
WHERE
	TASK_TYPE IS NULL;
	
--  更新订单评价状态
UPDATE sale_order t1
SET t1.COMMENT_STATE =
IF (
	t1.STATE = 10,
	1,
IF (
	t1.STATE = 11,
	2,
	t1.COMMENT_STATE
)
);

-- 修复订单状态
UPDATE sale_order t1
SET t1.STATE = 4
WHERE
	t1.state = 10
OR t1.STATE = 11;

--  修复订单 供应商
UPDATE sale_order t1
LEFT JOIN sale_order_item t2 ON t1.id = t2.order_id
SET t1.SUPPLIER_ID = t2.SUPPLIER_ID
WHERE
	t1.SUPPLIER_ID IS NULL;
	
	
-- 修正 供应商账户
INSERT INTO supplier_account (
	SUPPLIER_ID,
	AMOUNT,
	BALANCE,
	FREEZE_AMOUNT,
	WITHDRAW_AMOUNT,
	CREATE_TIME
) SELECT
	t1.id,
	'0.00',
	'0.00',
	'0.00',
	'0.00',
	NOW()
FROM
	supplier t1
LEFT JOIN supplier_account t2 ON t1.id = t2.SUPPLIER_ID
WHERE
	t2.id IS NULL;

--  修复供应商账户数据
UPDATE supplier_account t2
LEFT JOIN (
	SELECT
		t1.SUPPLIER_ID,
		SUM(t1.PAY_AMOUNT) amount
	FROM
		sale_order t1
	WHERE
		t1.STATE IN (2, 3)
	GROUP BY
		t1.SUPPLIER_ID
) t3 ON t2.SUPPLIER_ID = t3.SUPPLIER_ID
SET t2.FREEZE_AMOUNT = t3.amount;

UPDATE supplier_account t2
LEFT JOIN (
	SELECT
		t1.SUPPLIER_ID,
		SUM(t1.PAY_AMOUNT) amount
	FROM
		sale_order t1
	WHERE
		t1.STATE IN (4)
	GROUP BY
		t1.SUPPLIER_ID
) t3 ON t2.SUPPLIER_ID = t3.SUPPLIER_ID
SET t2.BALANCE = t3.amount;

UPDATE supplier_account t1
SET t1.AMOUNT = IFNULL(t1.AMOUNT, 0.00),
 t1.BALANCE = IFNULL(t1.BALANCE, 0.00),
 t1.FREEZE_AMOUNT = IFNULL(t1.FREEZE_AMOUNT, 0.00);
 
--  修正库存供应商
UPDATE stock t1
LEFT JOIN commodity t2 ON t1.commodity_id = t2.id
SET t1.SUPPLIER_ID = t2.SUPPLIER_ID
WHERE
	t1.SUPPLIER_ID IS NULL
AND t2.id IS NOT NULL;

---  修复图片数据 测试环境
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8081', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8082', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8083', 'com');

update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8081', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8082', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8083', 'com');

update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8081', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8082', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8083', 'com');

update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8081', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8082', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8083', 'com');

update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8081', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8082', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8083', 'com');

update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8081', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8082', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8083', 'com');

update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8081', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8082', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8083', 'com');

update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8081', 'com');
update basic_info t1 set t1.LOGO_URL  =REPLACE (t1.LOGO_URL , 'com:8082', 'com');
update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8083', 'com');

update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8081', 'com');
update basic_info t1 set t1.CONTENT_PROFILE  =REPLACE (t1.CONTENT_PROFILE , 'com:8082', 'com');
update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8083', 'com');

update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8081', 'com');
update basic_rule t1 set t1.CONTENT  =REPLACE (t1.CONTENT , 'com:8082', 'com');
update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8083', 'com');

update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8081', 'com');
update user t1 set t1.AVATAR  =REPLACE (t1.AVATAR , 'com:8082', 'com');
update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8083', 'com');

update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8081', 'com');
update comment t1 set t1.IMG_PATH  =REPLACE (t1.IMG_PATH , 'com:8082', 'com');
update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8083', 'com');


---  修复图片数据 生产环境
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8181', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8281', 'com');
UPDATE attachment t1 SET t1.FILE_PATH = REPLACE (t1.FILE_PATH, 'com:8381', 'com');

update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update commodity t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8181', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8281', 'com');
update commodity t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8381', 'com');

update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8181', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8281', 'com');
update product t1 set t1.PRODUCT_IMG_PATH=REPLACE (t1.PRODUCT_IMG_PATH, 'com:8381', 'com');

update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update article t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8181', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8281', 'com');
update article t1 set t1.CONTENT=REPLACE (t1.CONTENT, 'com:8381', 'com');

update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update article_comment t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update community t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8181', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8281', 'com');
update community t1 set t1.DESCRIPTION=REPLACE (t1.DESCRIPTION, 'com:8381', 'com');

update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update operate_category t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update advertisement t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8181', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8281', 'com');
update attribute t1 set t1.IMG_PATH=REPLACE (t1.IMG_PATH, 'com:8381', 'com');

update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8181', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8281', 'com');
update sale_order_item t1 set t1.COMMODITY_IMG=REPLACE (t1.COMMODITY_IMG, 'com:8381', 'com');

update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8181', 'com');
update basic_info t1 set t1.LOGO_URL  =REPLACE (t1.LOGO_URL , 'com:8281', 'com');
update basic_info t1 set t1.LOGO_URL =REPLACE (t1.LOGO_URL , 'com:8381', 'com');

update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8181', 'com');
update basic_info t1 set t1.CONTENT_PROFILE  =REPLACE (t1.CONTENT_PROFILE , 'com:8281', 'com');
update basic_info t1 set t1.CONTENT_PROFILE =REPLACE (t1.CONTENT_PROFILE , 'com:8381', 'com');

update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8181', 'com');
update basic_rule t1 set t1.CONTENT  =REPLACE (t1.CONTENT , 'com:8281', 'com');
update basic_rule t1 set t1.CONTENT =REPLACE (t1.CONTENT , 'com:8381', 'com');

update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8181', 'com');
update user t1 set t1.AVATAR  =REPLACE (t1.AVATAR , 'com:8281', 'com');
update user t1 set t1.AVATAR =REPLACE (t1.AVATAR , 'com:8381', 'com');

update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8181', 'com');
update comment t1 set t1.IMG_PATH  =REPLACE (t1.IMG_PATH , 'com:8281', 'com');
update comment t1 set t1.IMG_PATH =REPLACE (t1.IMG_PATH , 'com:8381', 'com');

-- 订单数据优化
UPDATE sale_order
SET PAY_MODE = 0
WHERE
	PAY_MODE = 4;

UPDATE sale_order
SET PAY_INVALID_TIME = CLOSE_TIME
WHERE
	PAY_INVALID_TIME IS NULL;

UPDATE sale_order
SET CREATE_TIME = NOW()
WHERE
	CREATE_TIME IS NULL;

-- 修正支付渠道
UPDATE sale_order
SET PAYMENT_CHANNEL = 1
WHERE
	PAYMENT_CHANNEL IS NULL;
	
-- 修正 下单时间
UPDATE sale_order t
SET t.ORDER_TIME = t.CREATE_TIME
WHERE
	t.ORDER_TIME IS NULL;

-- 修正售后订单编号
UPDATE after_sale_order t1
LEFT JOIN sale_order t2 ON t1.SALE_ORDER_ID = t2.id
SET t1.ORDER_NO = t2.ORDER_NO
WHERE
	t2.ID IS NOT NULL;

-- 销售订单 修正小区
UPDATE sale_order t1
LEFT JOIN member t2 ON t1.MEMBER_ID = t2.id
SET t1.COMMUNITY_ID = t2.COMMUNITY_ID;

-- 售后订单 修正小区
UPDATE after_sale_order t1
LEFT JOIN member t2 ON t1.MEMBER_ID = t2.id
SET t1.COMMUNITY_ID = t2.COMMUNITY_ID;

-- 销售订单 修正供应商
UPDATE sale_order t1
LEFT JOIN sale_order_item t2 ON t2.ORDER_ID = t1.ID
SET t1.SUPPLIER_ID = t2.SUPPLIER_ID
WHERE
	t1.SUPPLIER_ID IS NULL;
	
--  更新礼包的订单
UPDATE gift_bag t1
LEFT JOIN sale_order t2 ON t1.id = t2.gift_bag_id
SET t1.SALE_ORDER_ID = t2.ID;

--  更新礼包的订单
UPDATE gift t1
LEFT JOIN sale_order t2 ON t1.id = t2.gift_id
SET t1.SALE_ORDER_ID = t2.ID;

-- 更新销售订单状态
UPDATE sale_order t1
LEFT JOIN group_buy_order t2 ON t1.id = t2.sale_order_id
SET t1.GROUP_STATE = t2.STATE
WHERE
	t1.GROUP_STATE IS NULL;
	
UPDATE coupon
SET LIMITED = NULL
WHERE
	LIMITED = 0;
	
-- 修正代金券发放记录解冻节点
UPDATE coupon_grant_config
SET THAW_NODE = 0
WHERE
	THAW_NODE IS NULL;
	
	
-- 供应商销售统计
SELECT
	t3.ID SUPPLIER_ID,
	t3.SUPPLIER_NAME SUPPLIER_NAME, 
	t2.ID SALE_ORDER_ID,
	t4.ID PRODUCT_ID,
	t4.PRODUCT_NAME PRODUCT_NAME,
	SUM(t2.ORDER_AMOUNT) SALE_AMOUNT,
	SUM(
		t4.SUPPLY_PRICE * t1.QUANTITY
	) COST_AMOUT,
	SUM(
		t1.PRICE * t1.QUANTITY - t4.SUPPLY_PRICE * t1.QUANTITY
	) PROFIT_AMOUNT
FROM
	sale_order_item t1
LEFT JOIN sale_order t2 ON t2.id = t1.ORDER_ID
LEFT JOIN supplier t3 ON t2.SUPPLIER_ID = t3.ID
LEFT JOIN product t4 ON t4.id = t1.PRODUCT_ID
GROUP BY
	t3.id,
	t2.id,t4.ID;
	
-- 更新供货价	
UPDATE sale_order_item t1
LEFT JOIN product t2 ON t1.product_id = t2.id
SET t1.SUPPLY_PRICE = t2.SUPPLY_PRICE
WHERE
	t1.SUPPLY_PRICE = 0.00
OR t1.SUPPLY_PRICE IS NULL;

-- 更新成本价
UPDATE sale_order_item t1
LEFT JOIN product t2 ON t1.product_id = t2.id
SET t1.COST_AMOUNT = t2.SUPPLY_PRICE * t1.QUANTITY
WHERE
	t1.COST_AMOUNT = 0.00
OR t1.COST_AMOUNT IS NULL;

-- 更新小计
UPDATE sale_order_item t1
LEFT JOIN product t2 ON t1.product_id = t2.id
SET t1.SUBTOTAL = t1.PRICE * t1.QUANTITY
WHERE
	t1.SUBTOTAL = 0.00
OR t1.SUBTOTAL IS NULL;

-- 修正 成本金额
UPDATE sale_order t1
SET t1.COST_AMOUNT = (
	SELECT
		sum(t2.cost_amount)
	FROM
		sale_order_item t2
WHERE
	t1.id = t2.order_id
GROUP BY
	t2.order_id
)
WHERE
	t1.COST_AMOUNT IS NULL
OR t1.COST_AMOUNT = 0.00;
		
-- 修改订单供应商名称
UPDATE sale_order t1
LEFT JOIN supplier t2 ON t1.supplier_id = t2.id
SET t1.SUPPLIER_NAME = t2.SUPPLIER_NAME
WHERE
	t1.SUPPLIER_NAME IS NULL;

-- 修正 结算状态
UPDATE sale_order
SET SETTLEMENT_STATE = 1
WHERE
	ORDER_STATE = 4
AND AFTER_SALE_STATE = 4
AND SETTLEMENT_STATE IS NULL;

-- 修正对账状态
UPDATE sale_order
SET CHECK_STATE = 1
WHERE
	ORDER_STATE = 4
AND AFTER_SALE_STATE = 4
AND CHECK_STATE IS NULL;


-- 20190312  修正商品上架 和审核状态
UPDATE commodity
SET SHELF_STATE = SHELF,
 AUDIT_STATE = STATE;
	
-- 修正 推荐商品排序
UPDATE recommend_commodity t1
LEFT JOIN commodity t2 ON t1.COMMODITY_ID = t2.ID
SET t1.SORT = t2.SORT
WHERE
	t1.SORT IS NULL;


ALTER TABLE `yi_prod`.`invite_prize`
ADD COLUMN `RECEIVED` tinyint(1) NULL DEFAULT 0 COMMENT '是否已领取（0未领取1已领取）' AFTER `CREATE_TIME`;