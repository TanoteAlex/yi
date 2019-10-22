package com.yi.core.basic.domain.vo;

import java.math.BigDecimal;

public class CommissionSumVo {
	/**
	 * 小区提成比例
	 */
	private BigDecimal proportions;
	/**
	 * 小区名称
	 */
	private String address;
	/**
	 * 提成总额
	 */
	private BigDecimal commission;

	public BigDecimal getProportions() {
		return proportions;
	}

	public void setProportions(BigDecimal proportions) {
		this.proportions = proportions;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
}
