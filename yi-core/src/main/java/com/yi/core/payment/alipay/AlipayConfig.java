package com.yi.core.payment.alipay;

/**
 * 支付宝支付 配置信息
 * 
 * @author xuyh
 *
 */
public class AlipayConfig {

	/** 运行环境 */
	public static final Boolean PROD_ENV = Boolean.TRUE;

	/** 支付宝网关（固定） */
	public static final String SERVER_URL = "https://openapi.alipay.com/gateway.do";
	/** 支付宝消息验证地址 */
	public static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
	/** 合作伙伴身份（PID） */
	public static final String P_ID = "2088231487454914";
	/** 商户app_id 即创建应用后生成 */
	public static final String APP_ID = "2018092661501415";
	/** 卖家支付宝账号 */
	public static final String SELLER_EMAIL = "yiyi@51utopia.com";
	/** 开发者应用私钥，由开发者自己生成 */
	public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4WHfaGYPRBkNtz4brn9rBIVyzRRIj4BSCHDqtm/sgrJe084MhsDcVxnYwY2iZlfoWroFswF50ncywkSDBn/dg5zzzUpI9RvTT9sq871k/1yiMThcE2kmIoDTMcYJ6F2OdoH6oWN5t8bFRobxAfERkov56CukcWxdEqdjoblChOZGltcC6c7BMM6y87rYXp+myE0monUcMhgGBXfPLAMzGUpYFPpafz6hTn5IS23xgOw1GZeE9YkX08OblUbSi6i6Nhyab7W7AFRXalX4aUeGj4x0w3KqRpHHjuuhvXa5JcMSns5jFJQu2zNiVavoNfoH6YGgAhgpwZ27TApnv54r3AgMBAAECggEAFLmdf9+ueimCGy5htMvcBCqECYOSboYayCX0UuBec2X0CFv4OildiA0v7HDAqcO6wiIMWvWlqOa0xIUMcbhST4qvx4tY9Zqsm8f7MxKVrh+6Z0vAOsNv0PRx+yonySIoCOiqVLLpDuNFVDAiwf02kznf8kiXvKBhFU8jG76LQhTN9Blw5Ig9Ta3adBfdoo/zwt1gta3jlYIih82DSuhjkZt5ANdq/zIX2CTI6QMFSRMl9eM3LeE79ILVUC9PXk9fXAncnUwg4Ex9EVqkP1KthemUGXkPWImjbndyUpDxBwChp7HXeeeinaunZSBU3sR2aqJOlztk6Fbgo2ziFq9NwQKBgQDgNFIwegz+kGehitnl5WSOGFpXAeyp0OGcfu4Yx/d5jR865jHgLGq42u/NJhchhO6zFGn0qttc5mbgKmx66LSRhRUKJSMGID6CJ4N5Ps+nucXHz6o6NvfhIBBLgTqfpsgGh8MTU4zVbEPntE0XLWET32xT5xB8XJl6ttr9UJobwwKBgQDSfRWPxB0/18Ld0hreSbILLCrJaDKnrWgTgBKhvNznlYQmtxQVj5G4W544odwKYl8CUwUy252jpTwia8tkD079FpRHBVuRDXIMIW4bTcl+RZgfElr+DsHUlSW4p/pZr6pjOI5uPjP9ZzmL9AcGNR/AaZcynxagkiTrsPLi0mYEvQKBgD+iegVqTs/d2pv+FfRrVEwEW3fAgxR7xd0uJZQBBZuJU2jbNyLCWJQZB+D5Q61u1nUbgStBZzuJ5X8Qa3PDIqT+2cbkXXl6o2M7pdQ+4J9hWeJpbY5SVa6e9CEKD8KKitFXrSWKHDnjHa7g/NRsO9ZxHoT92y8A9UrgJW2qd+MTAoGBAMqrAZ3BmOAl4U0vxgsOLk4voqr2lv94KePMxyPP82tQPUQBt2bjK820+W1R+8pL2WslgLXtW6V7raD+5YFcdBOgL/kKIZZSueMQNTI4/a+FVulkBRTmJJ+JVB6uLpE4YPjUNd/UKL7vKyukNTc243GiuaBWKbVFwiLl55eaOfyJAoGAH9UDCodN7l7RAl7hUTWjS9OGcTaIPEFnjjW/+k8DwG+Z9j9Fo11gM0F10XKsUTk64ZfadY0MdQDugytX2N8lI0F/vWbqn7K1f2N3q5L3dRpVk03OettV62+oWiFJ9zAR+HZC4HM2mI6Xtn3D5EYj7ksxAuFzZ/KUvRXsmnszYnA=";
	/** 支付宝公钥，由支付宝生成 */
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAljiK8JVd3yvjCKxaQO9XULSkAgBAlkKnCNlbp8l9HzUF04Kfa1LcC9jjtvZP/dvClijSmTiQxAZ8xnGj0HJavVrA7ZztN+1H+Hg4+tugaKEe7S14I5xY9WwJ53+tcFx/OLwzXryq80DCICQDljRGvBY7Rx7DmBH/VolIbDtvHAZOrZhRU0SfEJ7o8UE4JiEGyo5gLzBVb1DkB9QPKdyclWs3jIk/Fu78uzPquWRKsuxQgxBIkhPM80U+ot418ewXFbxVVmo/U8ctmGcvdhgLboFX+F8oQMj0PqmHnYcuDZted+4IMJLjRAr+zy1hVZOU61FNhwBc7QjfoEeXBy4bgQIDAQAB";

	/** 支付宝退款接口 */
	public static final String REFUND_METHOD = "alipay.trade.refund";

	/** json 参数返回格式，只支持json */
	public static final String FORMAT = "json";
	/** UTF-8 请求和签名使用的字符编码格式，支持GBK和UTF-8 */
	public static final String CHARSET_UTF8 = "UTF-8";
	/** RSA2 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2 */
	public static final String SIGN_TYPE = "RSA2";
	/** 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY */
	public static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

	/** TRADE_SUCCESS 交易成功状态 */
	public static final String TRADE_STATUS_SUCCESS = "TRADE_SUCCESS";
	/** 回调成功 */
	public static final String NOTIFY_SUCCESS = "success";
	/** 回调失败 */
	public static final String NOTIFY_FAIL = "failure";

	/** 支付宝支付 回调通知url */
	public static final String APP_TEST_PAY_NOTIFY_URL = "http://test.h5server.my11mall.com/alipay/payNotify";
	/** 支付宝支付 回调通知url */
	public static final String APP_PROD_PAY_NOTIFY_URL = "http://h5server.my11mall.com/alipay/payNotify";

	/** 支付宝退款 回调通知url */
	public static final String APP_TEST_REFUND_NOTIFY_URL = "http://test.h5server.my11mall.com/alipay/refundNotify";
	/** 支付宝退款 回调通知url */
	public static final String APP_PROD_REFUND_NOTIFY_URL = "http://h5server.my11mall.com/alipay/refundNotify";

	/** 描述信息 -商品购买 */
	public static final String BODY = "商品购买";
	/** 商品名称 -美周壹壹优选-订单支付 */
	public static final String SUBJECT = "美周壹壹优选-订单支付";

	/** 接口调用成功，调用结果请参考具体的API文档所对应的业务返回参数 */
	public static final String SUCCESS_CODE = "10000";

}
