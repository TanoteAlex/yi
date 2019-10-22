///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.finance.domain.bo;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.yihz.common.convert.domain.BoDomain;
//import com.yihz.common.json.deserializer.JsonDateDeserializer;
//
///**
// * 供应商销售统计
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//public class SupplierSaleStatsBo extends BoDomain implements java.io.Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	// columns START
//	/**
//	 * 供应商销售统计ID
//	 */
//	private int id;
//	/**
//	 * GUID
//	 */
//	private String guid;
//	/**
//	 * 供应商订单数
//	 */
//	private int supplierOrderNum;
//	/**
//	 * 销售额
//	 */
//	private BigDecimal saleAmount;
//	/**
//	 * 供应商占比
//	 */
//	private BigDecimal supplierRatio;
//	/**
//	 * 成本
//	 */
//	private BigDecimal cost;
//	/**
//	 * 利润
//	 */
//	private BigDecimal profit;
//	/**
//	 * 统计时间
//	 */
//	@JsonDeserialize(using = JsonDateDeserializer.class)
//	private Date statTime;
//
//	/**
//	 * 统计时间
//	 */
//	@JsonDeserialize(using = JsonDateDeserializer.class)
//	private Date endTime;
//	// columns END
//
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getGuid() {
//		return this.guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	public int getSupplierOrderNum() {
//		return this.supplierOrderNum;
//	}
//
//	public void setSupplierOrderNum(int supplierOrderNum) {
//		this.supplierOrderNum = supplierOrderNum;
//	}
//
//	public BigDecimal getSaleAmount() {
//		return this.saleAmount;
//	}
//
//	public void setSaleAmount(BigDecimal saleAmount) {
//		this.saleAmount = saleAmount;
//	}
//
//	public BigDecimal getSupplierRatio() {
//		return this.supplierRatio;
//	}
//
//	public void setSupplierRatio(BigDecimal supplierRatio) {
//		this.supplierRatio = supplierRatio;
//	}
//
//	public BigDecimal getCost() {
//		return this.cost;
//	}
//
//	public void setCost(BigDecimal cost) {
//		this.cost = cost;
//	}
//
//	public BigDecimal getProfit() {
//		return this.profit;
//	}
//
//	public void setProfit(BigDecimal profit) {
//		this.profit = profit;
//	}
//
//	public Date getStatTime() {
//		return this.statTime;
//	}
//
//	public void setStatTime(Date statTime) {
//		this.statTime = statTime;
//	}
//
//	public Date getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//
//}