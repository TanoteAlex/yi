/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.bo;

import com.yi.core.activity.domain.entity.SalesAreaCommodityId;
import com.yi.core.activity.domain.simple.SalesAreaSimple;
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
public class SalesAreaCommodityBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private SalesAreaCommodityId salesAreaCommodityId;
	private SalesAreaSimple salesArea;
	private CommoditySimple commodity;
	private java.lang.Integer sort;

	/**
	 * 该ID是商品ID
	 */
	private int id;

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

	public SalesAreaSimple getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(SalesAreaSimple salesArea) {
		this.salesArea = salesArea;
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