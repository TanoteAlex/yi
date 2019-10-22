/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.simple;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 销售订单项
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SaleOrderItemSimple extends SimpleDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 订单项ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrderSimple saleOrder;
	private Integer mySaleOrderId;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	private Integer myMemberId;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
	private Integer mySupplierId;
	/**
	 * 商品(commodity表ID)
	 */
	private CommoditySimple commodity;
	private Integer myCommodityId;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private ProductSimple product;
	private Integer myProductId;
	/**
	 * 单价
	 */
	private BigDecimal price;
	/**
	 * 销售价
	 */
	private BigDecimal salePrice;
	/**
	 * 供货价
	 */
	private BigDecimal supplyPrice;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 优惠金额
	 */
	private BigDecimal discount;
	/**
	 * 成本金额
	 */
	private BigDecimal costAmount;
	/**
	 * 利润
	 */
	private BigDecimal profitAmount;
	/**
	 * 小计金额
	 */
	private BigDecimal subtotal;
	/**
	 * 结算状态
	 */
	private Integer settlementState;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END
	/**
	 * 商品ID
	 */
	private Integer commodityId;

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

	@JsonIgnore
	public SaleOrderSimple getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
	}

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public ProductSimple getProduct() {
		return product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public Integer getCommodityId() {
		return Optional.ofNullable(this.commodity).map(CommoditySimple::getId).orElse(commodityId);
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(BigDecimal profitAmount) {
		this.profitAmount = profitAmount;
	}

	public Integer getSettlementState() {
		return settlementState;
	}

	public void setSettlementState(Integer settlementState) {
		this.settlementState = settlementState;
	}

	@Transient
	public Integer getMySaleOrderId() {
		return mySaleOrderId;
	}

	public void setMySaleOrderId(Integer mySaleOrderId) {
		this.mySaleOrderId = mySaleOrderId;
	}
	@Transient
	public Integer getMyMemberId() {
		return myMemberId;
	}

	public void setMyMemberId(Integer myMemberId) {
		this.myMemberId = myMemberId;
	}
	@Transient
	public Integer getMySupplierId() {
		return mySupplierId;
	}

	public void setMySupplierId(Integer mySupplierId) {
		this.mySupplierId = mySupplierId;
	}
	@Transient
	public Integer getMyCommodityId() {
		return myCommodityId;
	}

	public void setMyCommodityId(Integer myCommodityId) {
		this.myCommodityId = myCommodityId;
	}
	@Transient
	public Integer getMyProductId() {
		return myProductId;
	}

	public void setMyProductId(Integer myProductId) {
		this.myProductId = myProductId;
	}


}