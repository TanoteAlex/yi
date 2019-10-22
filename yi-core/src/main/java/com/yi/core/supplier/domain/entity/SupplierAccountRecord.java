/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.domain.entity;

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
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 供应商账户记录
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SupplierAccountRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 账户记录ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商（supplier表ID）
	 */
	@NotNull
	@Max(9999999999L)
	private Supplier supplier;
	/**
	 * 操作类型（1收入2支出）
	 */
	@Max(127)
	private int operateType;
	/**
	 * 流水号
	 */
	@Length(max = 16)
	private String serialNo;
	/**
	 * 交易金额
	 */
	@Max(999999999999999L)
	private BigDecimal tradeAmount;
	/**
	 * 账户余额
	 */
	@Max(999999999999999L)
	private BigDecimal balance;
	/**
	 * 交易类型（1订单收入2提现3退款）
	 */
	@Max(127)
	private int tradeType;
	/**
	 * 交易时间
	 */

	private Date tradeTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	// columns END

	public SupplierAccountRecord() {
	}

	public SupplierAccountRecord(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public int getOperateType() {
		return this.operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public int getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = false, insertable = false, updatable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

}