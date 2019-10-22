/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class StockBo extends BoDomain implements java.io.Serializable {

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
	private SupplierBo supplier;
	/**
	 * 商品（commodity表ID）
	 */
	private CommodityBo commodity;
	/**
	 * 货品（product表ID）
	 */
	private ProductBo product;
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private int deleted;
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

	public int getStockQuantity() {
		return this.stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getLockQuantity() {
		return this.lockQuantity;
	}

	public void setLockQuantity(int lockQuantity) {
		this.lockQuantity = lockQuantity;
	}

	public int getUseQuantity() {
		return this.useQuantity;
	}

	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public SupplierBo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}

}