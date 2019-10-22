/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 
 * 供应商提现
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierWithdraw implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商提现ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商（supplier表id）
	 */
	@NotNull
	private Supplier supplier;
	/**
	 * 供应商名称
	 */
	@Length(max = 32)
	private String supplierName;
	/**
	 * 收款人
	 */
	@Length(max = 16)
	private String payee;
	/**
	 * 收款账号
	 */
	@Length(max = 32)
	private String receiptsNo;
	/**
	 * 收款名称
	 */
	@Length(max = 32)
	private String receiptsName;
	/**
	 * 申请提现金额
	 */
	@NotNull
	@Max(999999999999999L)
	private BigDecimal applyAmount;
	/**
	 * 实际到账金额
	 */
	private BigDecimal actualAmount;
	/**
	 * 手续费
	 */
	private BigDecimal serviceCharge;
	/**
	 * 付款人
	 */
	@Length(max = 16)
	private String drawee;
	/**
	 * 付款账号
	 */
	@Length(max = 32)
	private String paymentsNo;
	/**
	 * 付款名称
	 */
	@Length(max = 32)
	private String paymentsName;
	/**
	 * 付款方式（1线下转账2线上转账）
	 */
	@Max(127)
	private int paymentMethod;
	/**
	 * 提现状态（1待发放，2已发放，3发放异常）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 错误说明
	 */
	@Length(max = 255)
	private String errorDesc;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 发放时间
	 */
	private Date grantTime;
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

	public SupplierWithdraw() {
	}

	public SupplierWithdraw(int id) {
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
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = false, updatable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(unique = false, nullable = false, length = 16)
	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getReceiptsNo() {
		return this.receiptsNo;
	}

	public void setReceiptsNo(String receiptsNo) {
		this.receiptsNo = receiptsNo;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getReceiptsName() {
		return this.receiptsName;
	}

	public void setReceiptsName(String receiptsName) {
		this.receiptsName = receiptsName;
	}

	@Column(unique = false, nullable = false, length = 15)
	public BigDecimal getApplyAmount() {
		return this.applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getServiceCharge() {
		return this.serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getDrawee() {
		return this.drawee;
	}

	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getPaymentsNo() {
		return this.paymentsNo;
	}

	public void setPaymentsNo(String paymentsNo) {
		this.paymentsNo = paymentsNo;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getPaymentsName() {
		return this.paymentsName;
	}

	public void setPaymentsName(String paymentsName) {
		this.paymentsName = paymentsName;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
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
	public Date getGrantTime() {
		return this.grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
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

}