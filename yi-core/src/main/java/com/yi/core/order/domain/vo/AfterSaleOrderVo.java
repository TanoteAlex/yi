/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.order.domain.simple.AfterSaleProcessSimple;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 售后订单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AfterSaleOrderVo extends VoDomain implements java.io.Serializable {

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
	private MemberSimple member;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrderSimple saleOrder;
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
	 * 微信/支付宝退款单号
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date applyTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	// columns END

	/**
	 * 处理流程
	 */
	private List<AfterSaleProcessSimple> afterSaleProcesses = new ArrayList<>(0);

	/**
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public MemberSimple getMember() {
		return this.member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public SupplierSimple getSupplier() {
		return this.supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public SaleOrderSimple getSaleOrder() {
		return this.saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getAfterSaleType() {
		return this.afterSaleType;
	}

	public void setAfterSaleType(Integer afterSaleType) {
		this.afterSaleType = afterSaleType;
	}

	public String getAfterSaleNo() {
		return this.afterSaleNo;
	}

	public void setAfterSaleNo(String afterSaleNo) {
		this.afterSaleNo = afterSaleNo;
	}

	public Integer getApplyState() {
		return this.applyState;
	}

	public void setApplyState(Integer applyState) {
		this.applyState = applyState;
	}

	public Integer getProcessState() {
		return this.processState;
	}

	public void setProcessState(Integer processState) {
		this.processState = processState;
	}

	public String getRefundOrderNo() {
		return this.refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getRefundTradeNo() {
		return this.refundTradeNo;
	}

	public void setRefundTradeNo(String refundTradeNo) {
		this.refundTradeNo = refundTradeNo;
	}

	public Integer getRefundPayState() {
		return this.refundPayState;
	}

	public void setRefundPayState(Integer refundPayState) {
		this.refundPayState = refundPayState;
	}

	public BigDecimal getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getRefundAmount() {
		return this.refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getActualRefundAmount() {
		return this.actualRefundAmount;
	}

	public void setActualRefundAmount(BigDecimal actualRefundAmount) {
		this.actualRefundAmount = actualRefundAmount;
	}

	public Integer getRefundMode() {
		return this.refundMode;
	}

	public void setRefundMode(Integer refundMode) {
		this.refundMode = refundMode;
	}

	public String getAfterSaleReason() {
		return this.afterSaleReason;
	}

	public void setAfterSaleReason(String afterSaleReason) {
		this.afterSaleReason = afterSaleReason;
	}

	public String getProblemDesc() {
		return this.problemDesc;
	}

	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}

	public String getVoucherPhoto() {
		return this.voucherPhoto;
	}

	public void setVoucherPhoto(String voucherPhoto) {
		this.voucherPhoto = voucherPhoto;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public List<AfterSaleProcessSimple> getAfterSaleProcesses() {
		return afterSaleProcesses;
	}

	public void setAfterSaleProcesses(List<AfterSaleProcessSimple> afterSaleProcesses) {
		this.afterSaleProcesses = afterSaleProcesses;
	}

	public List<AttachmentVo> getAttachmentVos() {
		return attachmentVos;
	}

	public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
		this.attachmentVos = attachmentVos;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}