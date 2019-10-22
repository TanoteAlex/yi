/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.domain.bo;

import com.yi.core.cms.domain.entity.RecommendCommodityId;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yihz.common.convert.domain.BoDomain;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class RecommendCommodityBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private RecommendCommodityId recommendCommodityId;
	private RecommendBo recommend;
	private CommodityBo commodity;
	private int sort;
	/**
	 * 该ID是商品ID
	 */
	private int id;

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

	public RecommendBo getRecommend() {
		return recommend;
	}

	public void setRecommend(RecommendBo recommend) {
		this.recommend = recommend;
	}

	public CommodityBo getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}