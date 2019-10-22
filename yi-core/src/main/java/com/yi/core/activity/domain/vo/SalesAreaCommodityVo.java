/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import com.yi.core.activity.domain.entity.SalesAreaCommodityId;
import com.yi.core.activity.domain.simple.SalesAreaSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.VoDomain;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class SalesAreaCommodityVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private SalesAreaCommodityId salesAreaCommodityId;
	private CommoditySimple commodity;
	private SalesAreaSimple salesArea;
	private java.lang.Integer sort;

	public SalesAreaCommodityId getSalesAreaCommodityId() {
		return this.salesAreaCommodityId;
	}

	public void setSalesAreaCommodityId(SalesAreaCommodityId salesAreaCommodityId) {
		this.salesAreaCommodityId = salesAreaCommodityId;
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

	public SalesAreaSimple getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(SalesAreaSimple salesArea) {
		this.salesArea = salesArea;
	}
}