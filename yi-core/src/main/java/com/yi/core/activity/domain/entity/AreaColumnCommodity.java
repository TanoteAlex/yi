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
 * 专区栏目商品
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
public class AreaColumnCommodity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private AreaColumnCommodityId areaColumnCommodityId;
	private java.lang.Integer sort;

	public AreaColumnCommodity() {
	}

	public AreaColumnCommodity(AreaColumnCommodityId areaColumnCommodityId) {
		this.areaColumnCommodityId = areaColumnCommodityId;
	}

	@EmbeddedId
	public AreaColumnCommodityId getAreaColumnCommodityId() {
		return this.areaColumnCommodityId;
	}

	public void setAreaColumnCommodityId(AreaColumnCommodityId areaColumnCommodityId) {
		this.areaColumnCommodityId = areaColumnCommodityId;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private AreaColumn areaColumn;

	public void setAreaColumn(AreaColumn areaColumn) {
		this.areaColumn = areaColumn;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "AREA_COLUMN_ID", nullable = false, insertable = false, updatable = false) })
	public AreaColumn getAreaColumn() {
		return areaColumn;
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

}