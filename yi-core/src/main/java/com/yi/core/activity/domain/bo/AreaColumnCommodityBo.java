/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.bo;

import com.yi.core.activity.domain.entity.AreaColumnCommodityId;
import com.yi.core.activity.domain.simple.AreaColumnSimple;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.BoDomain;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AreaColumnCommodityBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private AreaColumnCommodityId areaColumnCommodityId;
	private AreaColumnSimple areaColumn;
	private CommoditySimple commodity;
	private java.lang.Integer sort;
	
	/**
	 * 该ID是商品ID
	 */
	private int id;

	public AreaColumnCommodityId getAreaColumnCommodityId() {
		return this.areaColumnCommodityId;
	}

	public void setAreaColumnCommodityId(AreaColumnCommodityId areaColumnCommodityId) {
		this.areaColumnCommodityId = areaColumnCommodityId;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public AreaColumnSimple getAreaColumn() {
		return areaColumn;
	}

	public void setAreaColumn(AreaColumnSimple areaColumn) {
		this.areaColumn = areaColumn;
	}

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}