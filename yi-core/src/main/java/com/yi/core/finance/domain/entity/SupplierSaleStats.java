///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.finance.domain.entity;
//
//import java.math.BigDecimal;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
///**
// * 供应商销售统计
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table(name = "supplier_sale_stats_view")
//public class SupplierSaleStats implements java.io.Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * 供应商ID
//	 */
//	private Integer supplierId;
//
//	/**
//	 * 供应商名称
//	 */
//	private String supplierName;
//
//	/**
//	 * 订单数
//	 */
//	private int orderNum;
//	/**
//	 * 销售额
//	 */
//	private BigDecimal saleAmount;
//	/**
//	 * 成本
//	 */
//	private BigDecimal costAmount;
//	/**
//	 * 运费
//	 */
//	private BigDecimal freightAmount;
//	/**
//	 * 利润
//	 */
//	private BigDecimal profitAmount;
//	
//	public Integer getSupplierId() {
//		return supplierId;
//	}
//
//	public void setSupplierId(Integer supplierId) {
//		this.supplierId = supplierId;
//	}
//
//	public String getSupplierName() {
//		return supplierName;
//	}
//
//	public void setSupplierName(String supplierName) {
//		this.supplierName = supplierName;
//	}
//
//	public int getOrderNum() {
//		return orderNum;
//	}
//
//	public void setOrderNum(int orderNum) {
//		this.orderNum = orderNum;
//	}
//
//	public BigDecimal getSaleAmount() {
//		return saleAmount;
//	}
//
//	public void setSaleAmount(BigDecimal saleAmount) {
//		this.saleAmount = saleAmount;
//	}
//
//	public BigDecimal getCostAmount() {
//		return costAmount;
//	}
//
//	public void setCostAmount(BigDecimal costAmount) {
//		this.costAmount = costAmount;
//	}
//
//	public BigDecimal getFreightAmount() {
//		return freightAmount;
//	}
//
//	public void setFreightAmount(BigDecimal freightAmount) {
//		this.freightAmount = freightAmount;
//	}
//
//	public BigDecimal getProfitAmount() {
//		return profitAmount;
//	}
//
//	public void setProfitAmount(BigDecimal profitAmount) {
//		this.profitAmount = profitAmount;
//	}
//
//}