/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 供应商提现
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SupplierWithdrawBo extends BoDomain implements java.io.Serializable {

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
	private SupplierBo supplier;
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
	@NotNull
	@Max(999999999999999L)
	private BigDecimal actualAmount;
	/**
	 * 手续费
	 */
	@NotNull
	@Max(999999999999999L)
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
	@NotNull
	@Max(127)
	private Integer paymentMethod;
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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date applyTime;
	/**
	 * 发放时间
	 */

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
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

	public SupplierBo getSupplier() {
		return this.supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPayee() {
		return this.payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getReceiptsNo() {
		return this.receiptsNo;
	}

	public void setReceiptsNo(String receiptsNo) {
		this.receiptsNo = receiptsNo;
	}

	public String getReceiptsName() {
		return this.receiptsName;
	}

	public void setReceiptsName(String receiptsName) {
		this.receiptsName = receiptsName;
	}

	public BigDecimal getApplyAmount() {
		return this.applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getServiceCharge() {
		return this.serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getDrawee() {
		return this.drawee;
	}

	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	public String getPaymentsNo() {
		return this.paymentsNo;
	}

	public void setPaymentsNo(String paymentsNo) {
		this.paymentsNo = paymentsNo;
	}

	public String getPaymentsName() {
		return this.paymentsName;
	}

	public void setPaymentsName(String paymentsName) {
		this.paymentsName = paymentsName;
	}

	public Integer getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Date getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getGrantTime() {
		return this.grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
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

}