/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yi.core.commodity.domain.entity.Commodity;

/**
 * 销售专区商品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SalesAreaCommodity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private SalesAreaCommodityId salesAreaCommodityId;
	private java.lang.Integer sort;

	public SalesAreaCommodity() {
	}

	public SalesAreaCommodity(SalesAreaCommodityId salesAreaCommodityId) {
		this.salesAreaCommodityId = salesAreaCommodityId;
	}

	@EmbeddedId
	public SalesAreaCommodityId getSalesAreaCommodityId() {
		return this.salesAreaCommodityId;
	}

	public void setSalesAreaCommodityId(SalesAreaCommodityId salesAreaCommodityId) {
		this.salesAreaCommodityId = salesAreaCommodityId;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private Commodity commodity;

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = false, insertable = false, updatable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	private SalesArea salesArea;

	public void setSalesArea(SalesArea salesArea) {
		this.salesArea = salesArea;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALES_AREA_ID", nullable = false, insertable = false, updatable = false) })
	public SalesArea getSalesArea() {
		return salesArea;
	}

}