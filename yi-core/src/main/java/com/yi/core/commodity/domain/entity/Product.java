/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 货品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Product implements java.io.Serializable {

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
	private Commodity commodity;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
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
	 * 成本
	 */
	private BigDecimal costPrice;
	/**
	 * 原价
	 */
	private BigDecimal originalPrice;
	/**
	 * 现价
	 */
	private BigDecimal currentPrice;
	/**
	 * 供货价
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
	/**
	 * 上架状态（1立即上架2放入仓库）
	 */
	private Integer shelfState;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END
	/**
	 * 商品属性集合
	 */
	private Set<Attribute> attributes;

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(nullable = true, updatable = false, length = 32)
	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	@Column(nullable = false, length = 64)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(nullable = false, length = 64)
	public String getProductShortName() {
		return productShortName;
	}

	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false) })
	@JsonIgnore
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID") })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(length = 15)
	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Column(length = 15)
	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 15)
	public BigDecimal getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(length = 15)
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Column(length = 15)
	public BigDecimal getSupplyPrice() {
		return this.supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	@Column(length = 64)
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(nullable = false, length = 10)
	public int getStockQuantity() {
		return this.stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 511)
	public String getProductImgPath() {
		return productImgPath;
	}

	public void setProductImgPath(String productImgPath) {
		this.productImgPath = productImgPath;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(nullable = false, length = 3)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "product_attribute", joinColumns = @JoinColumn(name = "PRODUCT_ID"), inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_ID"))
	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Column(nullable = true, length = 3)
	public Integer getShelfState() {
		return shelfState;
	}

	public void setShelfState(Integer shelfState) {
		this.shelfState = shelfState;
	}

}