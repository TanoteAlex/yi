package com.yi.core.stats.domain.vo;

import java.math.BigDecimal;

/**
 * 商品销量
 * 
 * @author xuyh
 *
 */
public class CommoditySaleVo {

	/** 商品名称 */
	private String commodityName;
	/** 销售数量 */
	private Integer saleNum;
	/** 销售金额 */
	private BigDecimal saleAmount;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

}
