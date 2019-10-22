/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 销售订单
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SaleOrder implements java.io.Serializable {

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
	private Member member;
	private Integer myMemberId;
	/**
	 * 小区（community表ID）
	 */
	private Community community;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	private Integer mySupplierId;

	/**
	 * 供应商名称
	 */
	private String supplierName;
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
	 * 微信/支付宝支付交易号
	 */
	@Length(max = 64)
	private String payTradeNo;
	/**
	 * 交易类型（JSAPI公众号或小程序支付，APP，NATIVE扫码支付..）
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
	 * 对账状态（1待对账2已对账）
	 */
	private Integer checkState;
	/**
	 * 结算状态（1待结算2已结算）
	 */
	private Integer settlementState;

	/**
	 * 礼物订单类型（1送礼单，2收礼单）
	 */
	private Integer giftOrderType;
	/**
	 * 团购订单类型（1开团单，2参团单）
	 */
	private Integer groupOrderType;
	/**
	 * 团购类型（1全国拼团 2小区拼团 3返现拼团 4秒杀活动）
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
	 * 代金券优惠金额
	 */
	private BigDecimal voucherAmount;
	/**
	 * 满减券优惠金额
	 */
	private BigDecimal couponAmount;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 积分抵现金额
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
	private Date orderTime;
	/**
	 * 支付失效时间
	 */
	private Date payInvalidTime;
	/**
	 * 退货失效时间
	 */
	private Date returnInvalidTime;
	/**
	 * 换货失效时间
	 */
	private Date exchangeInvalidTime;
	/**
	 * 付款时间
	 */
	private Date paymentTime;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 收货时间
	 */
	private Date receiptTime;
	/**
	 * 完成时间
	 */
	private Date finishTime;
	/**
	 * 关闭时间/取消时间
	 */
	private Date closeTime;
	/**
	 * 供应商阅读状态（0未阅读1已阅读）
	 */
	private Integer readState;
	/**
	 * 订单来源（1公众号，2小程序，3APP）
	 */
	private Integer orderSource;
	/**
	 * 支付渠道（1公众号，2小程序，3APP-微信，4APP-支付宝，5余额）
	 */
	private Integer paymentChannel;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	/**
	 * 订单项
	 */
	private List<SaleOrderItem> saleOrderItems = new ArrayList<>(0);
	/**
	 * 优惠券
	 */
	private List<CouponReceive> coupons = new ArrayList<>(0);
	/**
	 * 代金券
	 */
	private List<CouponReceive> vouchers = new ArrayList<>(0);

	/**
	 * 临时使用字段 非数据库字段</br>
	 * 是否使用余额
	 */
	private boolean useBalance;

	/**
	 * 临时使用字段 非数据库字段</br>
	 * 是否使用积分抵现金额
	 */
	private boolean useIntegralCash;


	public SaleOrder() {
	}

	public SaleOrder(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMUNITY_ID", nullable = true, updatable = false) })
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = true, updatable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

	@Column(unique = false, nullable = true, length = 127, updatable = true)
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(unique = false, nullable = true, length = 32, updatable = false)
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(unique = false, nullable = true, length = 32, updatable = false)
	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getPayTradeNo() {
		return this.payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getPayMode() {
		return this.payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getCommentState() {
		return commentState;
	}

	public void setCommentState(Integer commentState) {
		this.commentState = commentState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getAfterSaleState() {
		return afterSaleState;
	}

	public void setAfterSaleState(Integer afterSaleState) {
		this.afterSaleState = afterSaleState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getSettlementState() {
		return settlementState;
	}

	public void setSettlementState(Integer settlementState) {
		this.settlementState = settlementState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGiftOrderType() {
		return giftOrderType;
	}

	public void setGiftOrderType(Integer giftOrderType) {
		this.giftOrderType = giftOrderType;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGroupOrderType() {
		return groupOrderType;
	}

	public void setGroupOrderType(Integer groupOrderType) {
		this.groupOrderType = groupOrderType;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getGroupState() {
		return groupState;
	}

	public void setGroupState(Integer groupState) {
		this.groupState = groupState;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getFreight() {
		return this.freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getIntegralCashAmount() {
		return integralCashAmount;
	}

	public void setIntegralCashAmount(BigDecimal integralCashAmount) {
		this.integralCashAmount = integralCashAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getRefundAmount() {
		return this.refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getConsigneePhone() {
		return this.consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getConsigneeAddr() {
		return this.consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getPayInvalidTime() {
		return this.payInvalidTime;
	}

	public void setPayInvalidTime(Date payInvalidTime) {
		this.payInvalidTime = payInvalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getReturnInvalidTime() {
		return this.returnInvalidTime;
	}

	public void setReturnInvalidTime(Date returnInvalidTime) {
		this.returnInvalidTime = returnInvalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getExchangeInvalidTime() {
		return this.exchangeInvalidTime;
	}

	public void setExchangeInvalidTime(Date exchangeInvalidTime) {
		this.exchangeInvalidTime = exchangeInvalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	@Transient
	public Integer getMyMemberId() {
		return myMemberId;
	}

	public void setMyMemberId(Integer myMemberId) {
		this.myMemberId = myMemberId;
	}
	@Transient
	public Integer getMySupplierId() {
		return mySupplierId;
	}

	public void setMySupplierId(Integer mySupplierId) {
		this.mySupplierId = mySupplierId;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public List<SaleOrderItem> getSaleOrderItems() {
		return saleOrderItems;
	}

	public void setSaleOrderItems(List<SaleOrderItem> saleOrderItems) {
		this.saleOrderItems = saleOrderItems;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public List<CouponReceive> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponReceive> coupons) {
		this.coupons = coupons;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "saleOrder")
	public List<CouponReceive> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<CouponReceive> vouchers) {
		this.vouchers = vouchers;
	}

	@Transient
	public boolean isUseBalance() {
		return useBalance;
	}

	public void setUseBalance(boolean useBalance) {
		this.useBalance = useBalance;
	}

	@Transient
	public boolean isUseIntegralCash() {
		return useIntegralCash;
	}

	public void setUseIntegralCash(boolean useIntegralCash) {
		this.useIntegralCash = useIntegralCash;
	}

}