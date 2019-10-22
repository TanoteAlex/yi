package com.yi.core.payment.weChat;

/**
 * 微信相关配置信息
 * 
 * @author xuyh
 *
 */
public class WeChatConfig {

	/** 运行环境 */
	public static final Boolean PROD_ENV = Boolean.TRUE;

	// 微信服务号
	/** 服务号appid */
	public static final String SP_APP_ID = "wx624636d7fc542eb3";
	/** 服务号AppSecret */
	public static final String SP_APP_SECRET = "5435337998a3b45cd31d0677a933df29";
	/** 服务号-商户号mch_id */
	public static final String SP_MCH_ID = "1466953202";
	/** 商户号对应的密钥 */
	public static final String SP_PARTNER_KEY = "jiuziqi412724198512143712yiyiyou";
	/** 微信支付 回调通知url */
	public static final String SP_TEST_PAY_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/payNotifyForSp";
	/** 微信支付 回调通知url */
	public static final String SP_PROD_PAY_NOTIFY_URL = "http://h5server.my11mall.com/weChat/payNotifyForSp";
	/** 微信退款 回调通知url */
	public static final String SP_TEST_REFUND_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/refundNotifyForSp";
	/** 微信退款 回调通知url */
	public static final String SP_PROD_REFUND_NOTIFY_URL = "http://h5server.my11mall.com/weChat/refundNotifyForSp";

	// 微信小程序
	/** 小程序appid */
	public static final String MP_APP_ID = "";
	/** 小程序AppSecret */
	public static final String MP_APP_SECRET = "";
	/** 小程序-商户号mch_id */
	public static final String MP_MCH_ID = "";
	/** 商户号对应的密钥 */
	public static final String MP_PARTNER_KEY = "";
	/** 小程序 获取session_key */
	public static final String SESSION_KEY_URL = "https://api.weixin.qq.com/sns/jscode2session";
	/** 微信支付 回调通知url */
	public static final String MP_TEST_PAY_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/payNotifyForMp";
	/** 微信支付 回调通知url */
	public static final String MP_PROD_PAY_NOTIFY_URL = "http://h5server.my11mall.com/weChat/payNotifyForMp";
	/** 微信退款 回调通知url */
	public static final String MP_TEST_REFUND_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/refundNotifyForMp";
	/** 微信退款 回调通知url */
	public static final String MP_PROD_REFUND_NOTIFY_URL = "http://h5server.my11mall.com/weChat/refundNotifyForMp";

	// APP
	/** 微信开发平台应用ID 微信开放平台审核通过的应用APPID（请登录open.weixin.qq.com查看，注意与公众号的APPID不同） */
	public static final String APP_ID = "wx95cce4bd957b05ad";
	/** 应用对应的凭证 */
	public static final String APP_SECRET = "27c55a83ae52140d36e6e2d4a95b5b7a";
	/** 应用对应的微信支付商户号 */
	public static final String APP_MCH_ID = "1516922611";
	/** 商户号对应的密钥 */
	public static final String APP_PARTNER_KEY = "jiuziqi412724198512143712yiyiyou";
	/** 微信支付 回调通知url */
	public static final String APP_TEST_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/notifyForApp";
	/** 微信支付 回调通知url */
	public static final String APP_PROD_NOTIFY_URL = "http://h5server.my11mall.com/weChat/notifyForApp";

	/** 微信退款 回调通知url */
	public static final String APP_TEST_REFUND_NOTIFY_URL = "http://test.h5server.my11mall.com/weChat/refundNotifyForMp";
	/** 微信退款 回调通知url */
	public static final String APP_PROD_REFUND_NOTIFY_URL = "http://h5server.my11mall.com/weChat/refundNotifyForMp";

	/** 服务号或小程序 获取access_token */
	public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	/** 服务号 获取jsapi_ticket */
	public static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	/** 服务号或APP微信授权登录 通过code获取access_token */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/** 服务号 用户授权 通过access_token刷新网页授权接口调用凭证 */
	public static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	/** 服务号或APP 获取用户信息 */
	public static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

	/** 统一下单 */
	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/** 查询订单 */
	public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	/** 关闭订单 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。 */
	public static final String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	/** 申请退款 PS：一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号 */
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	/** 查询退款 注意：如果单个支付订单部分退款次数超过20次请使用退款单号查询 */
	public static final String REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	/** 下载对账单 */
	public static final String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	/** 下载资金账单 */
	public static final String DOWNLOAD_FUND_FLOW_URL = "https://api.mch.weixin.qq.com/pay/downloadfundflow";
	/** 交易保障 */
	public static final String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
	/** 拉取订单评价数据 */
	public static final String BATCH_QUERY_COMMENT_URL = "https://api.mch.weixin.qq.com/billcommentsp/batchquerycomment";
	/** 提现请求地址 */
	public static final String SEND_EED_PACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	/** APP微信授权登录 检验授权凭证（access_token）是否有效 */
	public static final String APP_CHECK_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/auth";
	/** 服务号或小程序 微信授权登录 grant_type */
	public static final String OAUTH_GRANT_TYPE = "authorization_code";
	/** 常量固定值 */
	public static final String GRANT_TYPE = "client_credential";
	/** 签名类型 -MD5 */
	public static final String SIGN_TYPE_MD5 = "MD5";
	/** 签名类型 -SHA1 */
	public static final String SIGN_TYPE_SHA1 = "SHA1";
	/** 签名类型 -HMAC-SHA256 */
	public static final String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";
	/* 提现校验用户姓名选项 **/
	public static final String CHECK_NAME = "NO_CHECK";

	/** 交易类型 -JSAPI支付（或小程序支付） */
	public static final String TRADE_TYPE_JSAPI = "JSAPI";
	/** 交易类型 -app支付 */
	public static final String TRADE_TYPE_APP = "APP";
	/** 交易类型 -Native支付 */
	public static final String TRADE_TYPE_NATIVE = "NATIVE";
	/** 交易类型 -H5支付 */
	public static final String TRADE_TYPE_MICROPAY = "MWEB";
	/** 货币类型 -人民币 */
	public static final String FEE_TYPE_CNY = "CNY";
	/** package-Sign=WXPay */
	public static final String PACKAGE = "Sign=WXPay";
	/** 商品描述 */
	public static final String BODY = "美周壹壹优选-订单支付";
	/** 返回状态码 SUCCESS */
	public static final String SUCCESS = "SUCCESS";
	/** 返回SUCCESS信息 */
	public static final String SUCCESS_MSG = "OK";
	/** 返回状态码 FAIL */
	public static final String FAIL = "FAIL";
	/** 返回FAIL信息 */
	public static final String FAIL_MSG = "FAIL";
	/** 字符集 */
	public static final String CHARSET_UTF8 = "UTF-8";
	/** 内容类型 */
	public static final String CONTENT_TYPE_XML = "text/xml";
	/** POST请求 */
	public static final String REQUEST_METHOD_POST = "POST";
	/** GET请求 */
	public static final String REQUEST_METHOD_GET = "GET";
	/** 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语 */
	public static final String LANG = "zh_CN";

}
