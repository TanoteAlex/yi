/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.AttributeSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ProductListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 货品ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 货品编码
	 */
	@NotBlank
	@Length(max = 32)
	private String productNo;
	/**
	 * 货品名称
	 */
	@NotBlank
	@Length(max = 64)
	private String productName;
	/**
	 * 货品简称
	 */
	@Length(max = 64)
	private String productShortName;
	/**
	 * 商品（commodity表ID）
	 */
	@NotNull
	private CommoditySimple commodity;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
	/**
	 * 排序
	 */
	@Max(127)
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
	@Length(max = 65535)
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
	@Length(max = 64)
	private String sku;
	/**
	 * 库存量
	 */
	@Max(9999999999L)
	private int stockQuantity;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	/**
	 * 成本
	 */
	private BigDecimal costPrice;
	// columns END

	/**
	 * 商品属性集合
	 */
	private Set<AttributeSimple> attributes;

	/**
	 * vip价格
	 * 
	 * @return
	 */
	private BigDecimal vipPrice;

	/**
	 * 销售属性
	 */
	private String saleAttribute;

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

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
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

	public Set<AttributeSimple> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<AttributeSimple> attributes) {
		this.attributes = attributes;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	// public String getImgPath() {
	//// if (StringUtils.isBlank(imgPath) && this.commodity != null) {
	//// imgPath = this.commodity.getImgPath();
	//// }
	// return imgPath;
	// }
	//
	// public void setImgPath(String imgPath) {
	// this.imgPath = imgPath;
	// }

	public String getSaleAttribute() {
		if (StringUtils.isBlank(saleAttribute) && CollectionUtils.isNotEmpty(this.attributes)) {
			saleAttribute = "";
			this.attributes.forEach(e -> {
				saleAttribute += e.getAttrValue() + " ";
			});
		}
		return saleAttribute;
	}

	public void setSaleAttribute(String saleAttribute) {
		this.saleAttribute = saleAttribute;
	}

}