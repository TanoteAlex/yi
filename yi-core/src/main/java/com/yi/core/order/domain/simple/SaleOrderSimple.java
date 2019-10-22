/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.simple;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.CouponReceiveSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 销售订单
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SaleOrderSimple extends SimpleDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 订单ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
	/**
	 * 订单编号
	 */
	@Length(max = 32)
	private String orderNo;
	/**
	 * 支付订单号
	 */
	@Length(max = 32)
	private String payOrderNo;
	/**
	 * 微信/支付宝交易号
	 */
	@Length(max = 64)
	private String payTradeNo;
	/**
	 * 交易类型（JSAPI服务号或小程序支付，APP，NATIVE扫码支付..）
	 */
	@Length(max = 32)
	private String tradeType;
	/**
	 * 支付方式（0未支付1支付宝2微信3银联）
	 */
	private Integer payMode;
	/**
	 * 订单类型（0普通订单，1礼物订单，2团购订单）
	 */
	@NotNull
	private Integer orderType;
	/**
	 * 订单状态（1待付款 2已付款待发货 3已发货待收货 4已收货已完成 5已关闭已取消）
	 */
	@NotNull
	private Integer orderState;
	/**
	 * 评价状态（默认为空订单未完成 1待评价2已评价）
	 */
	private Integer commentState;
	/**
	 * 售后状态（默认为空订单未完成 1可申请 2申请中 3已申请 4已过期）
	 */
	private Integer afterSaleState;
	/**
	 * 礼物订单类型（1送礼单，2收礼单）
	 */
	private Integer giftOrderType;
	/**
	 * 礼团购订单类型（1开团单，2参团单）
	 */
	private Integer groupOrderType;
	/**
	 * 拼团类型（1全国拼团 2小区拼团 3返现拼团 4秒杀活动）
	 */
	private Integer groupType;
	/**
	 * 团购状态（1待付款，2拼团中，3已成团，4已失效）
	 */
	private Integer groupState;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	/**
	 * 运费
	 */
	private BigDecimal freight;
	/**
	 * 满减券优惠金额
	 */
	private BigDecimal couponAmount;
	/**
	 * 代金券优惠金额
	 */
	private BigDecimal voucherAmount;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 积分抵扣金额
	 */
	private BigDecimal integralCashAmount;
	/**
	 * 实付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 成本金额
	 */
	private BigDecimal costAmount;
	/**
	 * 退款金额
	 */
	private BigDecimal refundAmount;
	/**
	 * 买家留言
	 */
	@Length(max = 255)
	private String buyerMessage;
	/**
	 * 收货人
	 */
	@Length(max = 16)
	private String consignee;
	/**
	 * 收货人电话
	 */
	@Length(max = 16)
	private String consigneePhone;
	/**
	 * 收货人地址
	 */
	@Length(max = 255)
	private String consigneeAddr;
	/**
	 * 配送方式
	 */
	@Length(max = 32)
	private String deliveryMode;
	/**
	 * 物流公司
	 */
	@Length(max = 64)
	private String expressCompany;
	/**
	 * 快递单号
	 */
	@Length(max = 32)
	private String expressNo;
	/**
	 * 下单时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date orderTime;
	/**
	 * 支付失效时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date payInvalidTime;
	/**
	 * 付款时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date paymentTime;
	/**
	 * 发货时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date deliveryTime;
	/**
	 * 收货时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date receiptTime;
	/**
	 * 收货时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date finishTime;
	/**
	 * 关闭时间/取消时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date closeTime;
	/**
	 * 供应商阅读状态（0未阅读1已阅读）
	 */
	private Integer readState;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 订单项
	 */
	private List<SaleOrderItemSimple> saleOrderItems = new ArrayList<>();
	/**
	 * 优惠券
	 */
	private List<CouponReceiveSimple> coupons = new ArrayList<>(0);;
	/**
	 * 代金券
	 */
	private List<CouponReceiveSimple> vouchers = new ArrayList<>(0);

	/**
	 * 临时使用字段 非数据库字段</br>
	 * 是否使用余额
	 */
	private boolean useBalance;

	/**
	 * 是否使用积分抵现
	 */
	private boolean useIntegralCash;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getCommentState() {
		return commentState;
	}

	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}

	public Integer getAfterSaleState() {
		return afterSaleState;
	}

	public void setAfterSaleState(Integer afterSaleState) {
		this.afterSaleState = afterSaleState;
	}

	public Integer getGiftOrderType() {
		return giftOrderType;
	}

	public void setGiftOrderType(Integer giftOrderType) {
		this.giftOrderType = giftOrderType;
	}

	public Integer getGroupOrderType() {
		return groupOrderType;
	}

	public void setGroupOrderType(Integer groupOrderType) {
		this.groupOrderType = groupOrderType;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getGroupState() {
		return groupState;
	}

	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public List<SaleOrderItemSimple> getSaleOrderItems() {
		return saleOrderItems;
	}

	public void setSaleOrderItems(List<SaleOrderItemSimple> saleOrderItems) {
		this.saleOrderItems = saleOrderItems;
	}

	public List<CouponReceiveSimple> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponReceiveSimple> coupons) {
		this.coupons = coupons;
	}

	public List<CouponReceiveSimple> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<CouponReceiveSimple> vouchers) {
		this.vouchers = vouchers;
	}

	public Date getPayInvalidTime() {
		return payInvalidTime;
	}

	public void setPayInvalidTime(Date payInvalidTime) {
		this.payInvalidTime = payInvalidTime;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public boolean isUseBalance() {
		return useBalance;
	}

	public void setUseBalance(boolean useBalance) {
		this.useBalance = useBalance;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getIntegralCashAmount() {
		return integralCashAmount;
	}

	public void setIntegralCashAmount(BigDecimal integralCashAmount) {
		this.integralCashAmount = integralCashAmount;
	}

	public boolean isUseIntegralCash() {
		return useIntegralCash;
	}

	public void setUseIntegralCash(boolean useIntegralCash) {
		this.useIntegralCash = useIntegralCash;
	}

}