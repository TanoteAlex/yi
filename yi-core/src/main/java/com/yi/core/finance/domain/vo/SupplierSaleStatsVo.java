package com.yi.core.finance.domain.vo;

import java.math.BigDecimal;

/**
 * 供应商销售统计
 * 
 * @author xuyh
 *
 */
public class SupplierSaleStatsVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5171178373889160656L;

	/**
	 * 供应商ID
	 */
	private Integer supplierId;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 订单状态
	 */
	private Integer orderState;

	/**
	 * 订单数
	 */
	private int orderNum;

	/**
	 * 销售额
	 */
	private BigDecimal saleAmount;
	/**
	 * 成本
	 */
	private BigDecimal costAmount;
	/**
	 * 运费
	 */
	private BigDecimal freightAmount;
	/**
	 * 利润
	 */
	private BigDecimal profitAmount;

	/**
	 * 统计开始时间
	 */
	private String startTime;

	/**
	 * 统计结束时间
	 */
	private String endTime;

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	public BigDecimal getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(BigDecimal profitAmount) {
		this.profitAmount = profitAmount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}