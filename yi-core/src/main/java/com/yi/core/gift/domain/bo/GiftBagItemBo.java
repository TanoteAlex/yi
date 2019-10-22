/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 礼包项
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class GiftBagItemBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 礼包项ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 礼包（gift_bag表ID）
	 */
	@NotNull
	private GiftBagBo giftBag;
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
	 * 货品（product表ID）
	 */
	private ProductBo product;
	/**
	 * 单价
	 */
	@Max(999999999999999L)
	private BigDecimal price;
	/**
	 * 数量
	 */
	@Max(9999999999L)
	private int quantity;
	/**
	 * 小计金额
	 */
	@Max(999999999999999L)
	private BigDecimal subtotal;
	/**
	 * 失效时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date invalidTime;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
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

	public GiftBagBo getGiftBag() {
		return this.giftBag;
	}

	public void setGiftBag(GiftBagBo giftBag) {
		this.giftBag = giftBag;
	}

	public MemberBo getMember() {
		return this.member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public SupplierBo getSupplier() {
		return this.supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

	public CommodityBo getCommodity() {
		return this.commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public ProductBo getProduct() {
		return this.product;
	}

	public void setProduct(ProductBo product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Date getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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