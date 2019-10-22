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

import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.bo.SaleOrderItemBo;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;

/**
 * 供应商对账单
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SupplierCheckAccountBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商对账ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商
	 */
	@NotNull
	private SupplierBo supplier;
	/**
	 * 供应商名称
	 */
	@Length(max = 127)
	private String supplierName;
	/**
	 * 销售订单
	 */
	@NotNull
	private SaleOrderBo saleOrder;
	/**
	 * 订单编号
	 */
	@Length(max = 32)
	private String saleOrderNo;
	/**
	 * 销售订单项
	 */
	@NotNull
	private SaleOrderItemBo saleOrderItem;
	/**
	 * 商品(商品表ID)
	 */
	private CommodityBo commodity;
	/**
	 * 商品编号
	 */
	@Length(max = 32)
	private String commodityNo;
	/**
	 * 商品名称
	 */
	@Length(max = 127)
	private String commodityName;
	/**
	 * 货品（product表ID）
	 */
	@NotNull
	private ProductBo product;
	/**
	 * 货品编号
	 */
	@Length(max = 32)
	private String productNo;
	/**
	 * 货品名称
	 */
	@Length(max = 127)
	private String productName;
	/**
	 * 供货价
	 */
	@NotNull
	private BigDecimal supplyPrice;
	/**
	 * 销售价
	 */
	@NotNull
	private BigDecimal salePrice;
	/**
	 * 数量
	 */
	@Max(9999999999L)
	private int quantity;
	/**
	 * 应结货款
	 */
	@NotNull
	private BigDecimal settlementAmount;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 结算时间
	 */
	private Date settlementTime;
	// columns END

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public SupplierBo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public SaleOrderBo getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderBo saleOrder) {
		this.saleOrder = saleOrder;
	}

	public String getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public SaleOrderItemBo getSaleOrderItem() {
		return saleOrderItem;
	}

	public void setSaleOrderItem(SaleOrderItemBo saleOrderItem) {
		this.saleOrderItem = saleOrderItem;
	}

	public CommodityBo getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public ProductBo getProduct() {
		return product;
	}

	public void setProduct(ProductBo product) {
		this.product = product;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

}