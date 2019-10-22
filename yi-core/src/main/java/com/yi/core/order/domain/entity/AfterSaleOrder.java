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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 售后单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class AfterSaleOrder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 售后单ID
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
	 * 小区（community表ID）
	 */
	private Community community;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrder saleOrder;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 售后类型（1退款-未收到货，2退货退款-已收到货，3换货）
	 */
	@NotNull
	@Max(127)
	private Integer afterSaleType;
	/**
	 * 售后单号
	 */
	@NotBlank
	@Length(max = 16)
	private String afterSaleNo;
	/**
	 * 申请状态（1审核中，2处理中，3已完成）
	 */
	@Max(127)
	private Integer applyState;
	/**
	 * 处理状态（1待审核，2待收货，3待退款，4已完成，5拒绝退货，6拒绝退款）
	 */
	@Max(127)
	private Integer processState;
	/**
	 * 退款订单号（申请退款使用）
	 */
	@Length(max = 32)
	private String refundOrderNo;
	/**
	 * 微信/支付宝退款交易号
	 */
	@Length(max = 64)
	private String refundTradeNo;
	/**
	 * 退款支付状态（1待回执，2已回执）
	 */
	@Max(127)
	private Integer refundPayState;
	/**
	 * 订单金额
	 */
	@Max(999999999999999L)
	private BigDecimal orderAmount;
	/**
	 * 支付金额
	 */
	@Max(999999999999999L)
	private BigDecimal payAmount;
	/**
	 * 退款金额
	 */
	@Max(999999999999999L)
	private BigDecimal refundAmount;
	/**
	 * 实际退款金额
	 */
	@Max(999999999999999L)
	private BigDecimal actualRefundAmount;
	/**
	 * 退款方式（1退回原支付渠道）
	 */
	@NotNull
	@Max(127)
	private Integer refundMode;
	/**
	 * 售后原因
	 */
	@Length(max = 127)
	private String afterSaleReason;
	/**
	 * 问题描述
	 */
	@Length(max = 511)
	private String problemDesc;
	/**
	 * 凭证照片
	 */
	@Length(max = 255)
	private String voucherPhoto;
	/**
	 * 联系人
	 */
	@Length(max = 16)
	private String contact;
	/**
	 * 联系人电话
	 */
	@Length(max = 16)
	private String contactPhone;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 收货时间
	 */
	private Date receiptTime;
	/**
	 * 退款时间
	 */
	private Date refundTime;
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
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	/**
	 * 处理流程
	 */
	private List<AfterSaleProcess> afterSaleProcesses = new ArrayList<>(0);

	public AfterSaleOrder() {
	}

	public AfterSaleOrder(int id) {
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMUNITY_ID", nullable = true, updatable = false) })
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = true, updatable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALE_ORDER_ID", nullable = false, updatable = false) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	
	@Column(unique = false, nullable = false, length = 16)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getAfterSaleType() {
		return this.afterSaleType;
	}

	public void setAfterSaleType(Integer afterSaleType) {
		this.afterSaleType = afterSaleType;
	}

	@Column(unique = false, nullable = false, length = 16)
	public String getAfterSaleNo() {
		return this.afterSaleNo;
	}

	public void setAfterSaleNo(String afterSaleNo) {
		this.afterSaleNo = afterSaleNo;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getApplyState() {
		return this.applyState;
	}

	public void setApplyState(Integer applyState) {
		this.applyState = applyState;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getProcessState() {
		return this.processState;
	}

	public void setProcessState(Integer processState) {
		this.processState = processState;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getRefundOrderNo() {
		return this.refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getRefundTradeNo() {
		return this.refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getRefundPayState() {
		return this.refundPayState;
	}

	public void setRefundPayState(Integer refundPayState) {
		this.refundPayState = refundPayState;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getRefundAmount() {
		return this.refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getActualRefundAmount() {
		return this.actualRefundAmount;
	}

	public void setActualRefundAmount(BigDecimal actualRefundAmount) {
		this.actualRefundAmount = actualRefundAmount;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getRefundMode() {
		return this.refundMode;
	}

	public void setRefundMode(Integer refundMode) {
		this.refundMode = refundMode;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getAfterSaleReason() {
		return this.afterSaleReason;
	}

	public void setAfterSaleReason(String afterSaleReason) {
		this.afterSaleReason = afterSaleReason;
	}

	@Column(unique = false, nullable = true, length = 511)
	public String getProblemDesc() {
		return this.problemDesc;
	}

	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getVoucherPhoto() {
		return this.voucherPhoto;
	}

	public void setVoucherPhoto(String voucherPhoto) {
		this.voucherPhoto = voucherPhoto;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(Date receiptTime) {
		this.receiptTime = receiptTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "afterSaleOrder")
	public List<AfterSaleProcess> getAfterSaleProcesses() {
		return afterSaleProcesses;
	}

	public void setAfterSaleProcesses(List<AfterSaleProcess> afterSaleProcesses) {
		this.afterSaleProcesses = afterSaleProcesses;
	}

}