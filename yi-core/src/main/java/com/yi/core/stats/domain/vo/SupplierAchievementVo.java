package com.yi.core.stats.domain.vo;

import java.math.BigDecimal;

/**
 * 
 * @author 供应商业绩
 *
 */
public class SupplierAchievementVo {

	/** 供应商名称 */
	private String supplierName;

	/** 供应商销售额 */
	private BigDecimal saleAmount;

	/** 订单数 */
	private int orderNum;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

}
