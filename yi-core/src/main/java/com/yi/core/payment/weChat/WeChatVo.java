package com.yi.core.payment.weChat;

import java.util.Set;

/**
 * 微信实体
 *
 * @author xuyh
 */
public class WeChatVo {

	/**
	 * 会员ID
	 */
	private String memberId;

	/**
	 * 订单ID组成的字符串 以","分割
	 */
	private Set<Integer> orderIds;

	/**
	 * 商品简单描述
	 */
	private String body;
	/**
	 * 退款原因 若商户传入，会在下发给用户的退款消息中体现退款原因
	 */
	private String refundDesc;
	/**
	 * 商品详细描述 对于使用单品优惠的商户，改字段必须按照规范上传
	 */
	private String detail;

	/**
	 * 附加数据 ，在查询API和支付通知中原样返回，可作为自定义参数使用
	 */
	private String attach;

	/**
	 * 商户订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
	 */
	private String payOrderNo;
	/**
	 * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
	 */
	private String refundOrderNo;

	/**
	 * 微信生成的订单号，在支付通知中有返回
	 */
	private String payTradeNo;

	/**
	 * 微信生成的退款单号，在申请退款接口有返回
	 */
	private String refundTradeNo;

	/**
	 * 企业付款描述信息
	 */
	private String desc;
	/**
	 * 提现订单号
	 */
	private String partnerTradeNo;

	/**
	 * 提现签名
	 */
	private String sign;

	/**
	 * 提现用户ip
	 */
	private String spbillCreateIp;

	/**
	 * 订单总金额，单位为分
	 */
	private String totalFee;
	/**
	 * 退款总金额，订单总金额，单位为分，只能为整数
	 */
	private String refundFee;

	/**
	 * 交易类型 JSAPI 公众号支付 NATIVE 扫码支付 APP app支付
	 */
	private String tradeType;
	/**
	 * 用户标识 trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 */
	private String openId;

	/**
	 * 开发者ID
	 */
	private String appId;
	/**
	 * 开发者密码
	 */
	private String appSecret;
	/**
	 * 商户号
	 */
	private String mchId;
	/**
	 * 商户号对应的密钥
	 */
	private String partnerKey;

	/**
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	 */
	private String notifyUrl;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Set<Integer> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(Set<Integer> orderIds) {
		this.orderIds = orderIds;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}

	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getRefundTradeNo() {
		return refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}
}