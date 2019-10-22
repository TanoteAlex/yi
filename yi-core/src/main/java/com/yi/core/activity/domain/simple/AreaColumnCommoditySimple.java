/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.simple;

import java.math.BigDecimal;

import com.yi.core.activity.domain.entity.AreaColumnCommodityId;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.SimpleDomain;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AreaColumnCommoditySimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private AreaColumnCommodityId areaColumnCommodityId;
	private java.lang.Integer sort;

	private CommoditySimple commodity;
	private int id;
	private String commodityNo;
	private String commodityName;
	private BigDecimal currentPrice;
	private String imgPath;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommodityNo() {
		if (commodity != null) {
			commodityNo = commodity.getCommodityNo();
		}
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		if (commodity != null) {
			commodityName = commodity.getCommodityName();
		}
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public BigDecimal getCurrentPrice() {
		if (commodity != null) {
			currentPrice = commodity.getCurrentPrice();
		}
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getImgPath() {
		if (commodity != null) {
			imgPath = commodity.getImgPath();
		}
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}