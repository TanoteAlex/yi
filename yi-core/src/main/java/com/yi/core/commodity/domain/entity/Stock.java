/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 库存
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Stock implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 库存ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	/**
	 * 商品（commodity表ID）
	 */
	private Commodity commodity;
	/**
	 * 货品（product表ID）
	 */
	private Product product;
	/**
	 * 库存量
	 */
	@Max(9999999999L)
	private int stockQuantity;
	/**
	 * 锁定库存量
	 */
	@Max(9999999999L)
	private int lockQuantity;
	/**
	 * 使用库存量
	 */
	@Max(9999999999L)
	private int useQuantity;
	/**
	 * 排序
	 */
	@Max(127)
	private int sort;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private int deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	public Stock() {
	}

	public Stock(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getStockQuantity() {
		return this.stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getLockQuantity() {
		return this.lockQuantity;
	}

	public void setLockQuantity(int lockQuantity) {
		this.lockQuantity = lockQuantity;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getUseQuantity() {
		return this.useQuantity;
	}

	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}

	@Column(unique = false, nullable = true, length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 3)
	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = false) })
	public Product getProduct() {
		return product;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID") })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}