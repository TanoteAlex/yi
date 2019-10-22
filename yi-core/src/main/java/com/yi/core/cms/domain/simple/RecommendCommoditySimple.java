/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.domain.simple;

import java.math.BigDecimal;

import com.yi.core.cms.domain.entity.RecommendCommodityId;
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
public class RecommendCommoditySimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private RecommendCommodityId recommendCommodityId;
	private int sort;
	private CommoditySimple commodity;

	private String commodityNo;
	private String commodityName;
	private BigDecimal currentPrice;
	private String imgPath;

	public RecommendCommodityId getRecommendCommodityId() {
		return this.recommendCommodityId;
	}

	public void setRecommendCommodityId(RecommendCommodityId recommendCommodityId) {
		this.recommendCommodityId = recommendCommodityId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
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

	public int getId() {
		if (commodity != null) {
			id = commodity.getId();
		}
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}