/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import com.yi.core.activity.domain.entity.AreaColumnCommodityId;
import com.yi.core.activity.domain.simple.AreaColumnSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.ListVoDomain;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AreaColumnCommodityListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private AreaColumnCommodityId areaColumnCommodityId;
	private java.lang.Integer sort;
	private CommoditySimple commodity;
	private AreaColumnSimple areaColumn;

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

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public AreaColumnSimple getAreaColumn() {
		return areaColumn;
	}

	public void setAreaColumn(AreaColumnSimple areaColumn) {
		this.areaColumn = areaColumn;
	}
}