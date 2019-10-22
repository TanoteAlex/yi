/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 支付记录
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class PayRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 支付记录ID
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
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrder saleOrder;
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
	/**
	 * 实际支付金额（单位分）
	 */
	private String actualPayAmount;
	/**
	 * 优惠金额
	 */
	private BigDecimal discountAmount;

	/**
	 * 交易类型（JSAPI公众号支付，APP，NATIVE扫码支付）
	 */
	@Length(max = 16)
	private String tradeType;
	/**
	 * 微信交易状态（SUCCESS支付成功，REFUND转入退款，NOTPAY未支付..） </br>
	 * 支付宝交易状态 （WAIT_BUYER_PAY交易创建，等待买家付款，TRADE_CLOSED未付款交易超时关闭，或支付完成后全额退款，
	 * TRADE_SUCCESS交易支付成功，TRADE_FINISHED交易结束，不可退款）
	 */
	@Length(max = 32)
	private String tradeState;
	/**
	 * 交易状态描述
	 */
	@Length(max = 255)
	private String tradeStateDesc;
	/**
	 * 微信/支付宝交易号
	 */
	@Length(max = 64)
	private String tradeNo;
	/**
	 * 商户订单号
	 */
	@Length(max = 64)
	private String outTradeNo;
	/**
	 * 支付时间
	 */
	@Length(max = 32)
	private String paymentTime;

	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	// columns END

	public PayRecord() {
	}

	public PayRecord(int id) {
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

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getTradeState() {
		return this.tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getTradeStateDesc() {
		return this.tradeStateDesc;
	}

	public void setTradeStateDesc(String tradeStateDesc) {
		this.tradeStateDesc = tradeStateDesc;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Column(unique = false, nullable = true, length = 255)
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

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ORDER_ID", nullable = false, insertable = true, updatable = true) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false, insertable = true, updatable = true) })
	public Member getMember() {
		return member;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getActualPayAmount() {
		return actualPayAmount;
	}

	public void setActualPayAmount(String actualPayAmount) {
		this.actualPayAmount = actualPayAmount;
	}

}