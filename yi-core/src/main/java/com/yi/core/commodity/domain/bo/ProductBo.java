/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ProductBo extends BoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 货品ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 货品编码
	 */
	private String productNo;
	/**
	 * 货品名称
	 */
	private String productName;
	/**
	 * 货品简称
	 */
	private String productShortName;
	/**
	 * 商品（commodity表ID）
	 */
	private CommodityBo commodity;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierBo supplier;
	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 重量
	 */
	private BigDecimal weight;
	/**
	 * 商品介绍
	 */
	private String description;
	/**
	 * 原价
	 */
	private BigDecimal originalPrice;
	/**
	 * 现价
	 */
	private BigDecimal currentPrice;
	/**
	 * 会员价
	 */
	private BigDecimal supplyPrice;
	/**
	 * SKU
	 */
	private String sku;
	/**
	 * 库存量
	 */
	private int stockQuantity;

	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	/**
	 * 成本
	 */
	private BigDecimal costPrice;
	// columns END

	/**
	 * 商品属性集合
	 */
	private Set<AttributeBo> attributes;

	private Set<String> attrNames;
	/**
	 * vip价格
	 * 
	 * @return
	 */
	private BigDecimal vipPrice;

	/**
	 * 货品图片
	 * 
	 * @return
	 */
	private String productImgPath;

	public String getProductImgPath() {
		return productImgPath;
	}

	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}

	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

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

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public CommodityBo getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public SupplierBo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getSupplyPrice() {
		return this.supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getStockQuantity() {
		return this.stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
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

	public String getProductShortName() {
		return productShortName;
	}

	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}

	public Set<AttributeBo> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<AttributeBo> attributes) {
		this.attributes = attributes;
	}

	public Set<String> getAttrNames() {
		return attrNames;
	}

	public void setAttrNames(Set<String> attrNames) {
		this.attrNames = attrNames;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

}