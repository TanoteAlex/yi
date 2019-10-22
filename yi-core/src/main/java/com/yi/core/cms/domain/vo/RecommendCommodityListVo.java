/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.domain.vo;

import com.yi.core.cms.domain.entity.RecommendCommodityId;
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
public class RecommendCommodityListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private RecommendCommodityId recommendCommodityId;
	private int sort;
	private CommoditySimple commodity;

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

}