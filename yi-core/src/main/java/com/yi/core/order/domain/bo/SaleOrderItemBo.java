/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;
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
public class SaleOrderItemBo extends BoDomain implements Serializable {

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
	private SaleOrderBo saleOrder;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierBo supplier;
	/**
	 * 商品(commodity表ID)
	 */
	private CommodityBo commodity;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private ProductBo product;
	/**
	 * 单价
	 */
	private BigDecimal price;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 优惠金额
	 */
	private BigDecimal discount;
	/**
	 * 小计金额
	 */
	private BigDecimal subtotal;
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

	public SaleOrderBo getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderBo saleOrder) {
		this.saleOrder = saleOrder;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public SupplierBo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

	public CommodityBo getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public ProductBo getProduct() {
		return product;
	}

	public void setProduct(ProductBo product) {
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

}