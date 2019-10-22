/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cms.domain.entity.RecommendCommodity;
import com.yi.core.cms.domain.entity.RecommendCommodityId;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface RecommendCommodityDao extends JpaRepository<RecommendCommodity, RecommendCommodityId>, JpaSpecificationExecutor<RecommendCommodity> {

	/**
	 * 根据商品获取推荐商品
	 * 
	 * @param commodityId
	 * @return
	 */
	List<RecommendCommodity> findByCommodity_id(int commodityId);

	/**
	 * 根据推荐位 获取推荐商品
	 * 
	 * @param recommendId
	 * @return
	 */
	List<RecommendCommodity> findByRecommend_id(int recommendId);
	
}