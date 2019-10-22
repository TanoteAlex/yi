/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 供应商账户
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class SupplierAccountVo extends VoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 账户ID
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
	private SupplierSimple supplier;
	/**
	 * 账户金额-待定字段
	 */

	private BigDecimal amount;
	/**
	 * 账户余额
	 */

	private BigDecimal balance;
	/**
	 * 冻结金额
	 */

	private BigDecimal freezeAmount;
	/**
	 * 提现金额
	 */

	private BigDecimal withdrawAmount;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	// columns END

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

	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreezeAmount() {
		return this.freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public BigDecimal getWithdrawAmount() {
		return this.withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}