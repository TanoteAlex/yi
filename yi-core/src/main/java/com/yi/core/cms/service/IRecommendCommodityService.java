/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.cms.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.RecommendCommodityBo;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.entity.RecommendCommodity;
import com.yi.core.cms.domain.entity.RecommendCommodityId;
import com.yi.core.cms.domain.vo.RecommendCommodityListVo;
import com.yi.core.cms.domain.vo.RecommendCommodityVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IRecommendCommodityService {

	/**
	 * 分页查询: RecommendCommodity
	 **/
	Page<RecommendCommodity> query(Pagination<RecommendCommodity> query);

	/**
	 * 分页查询: RecommendCommodity
	 **/
	Page<RecommendCommodityListVo> queryListVo(Pagination<RecommendCommodity> query);

	/**
	 * 创建RecommendCommodity
	 **/
	RecommendCommodity addRecommendCommodity(RecommendCommodity recommendCommodity);

	/**
	 * 创建RecommendCommodity
	 **/
	RecommendCommodityListVo addRecommendCommodity(RecommendCommodityBo recommendCommodity);

	/**
	 * 更新RecommendCommodity
	 **/
	RecommendCommodity updateRecommendCommodity(RecommendCommodity recommendCommodity);

	/**
	 * 更新RecommendCommodity
	 **/
	RecommendCommodityListVo updateRecommendCommodity(RecommendCommodityBo recommendCommodity);

	/**
	 * 删除RecommendCommodity
	 **/
	void removeRecommendCommodityById(RecommendCommodityId recommendCommodityId);

	/**
	 * 根据ID得到RecommendCommodityBo
	 **/
	RecommendCommodityBo getRecommendCommodityBoById(RecommendCommodityId recommendCommodityId);

	/**
	 * 根据ID得到RecommendCommodityVo
	 **/
	RecommendCommodityVo getRecommendCommodityVoById(RecommendCommodityId recommendCommodityId);

	/**
	 * 根据ID得到RecommendCommodityListVo
	 **/
	RecommendCommodityListVo getListVoById(RecommendCommodityId recommendCommodityId);

	/**
	 * 批量新增推荐商品
	 * 
	 * @param recommend
	 * @param recommendCommodities
	 */
	void batchAddRecommendCommodity(Recommend recommend, Collection<RecommendCommodity> recommendCommodities);

	/**
	 * 批量修改推荐商品
	 * 
	 * @param recommend
	 * @param recommendCommodities
	 */
	void batchUpdateRecommendCommodity(Recommend recommend, Collection<RecommendCommodity> recommendCommodities);

	/**
	 * 删除推荐商品
	 * 
	 * @param recommendId
	 */
	void removeByRecommend(Integer recommendId);

	/**
	 * 删除推荐商品
	 * 
	 * @param commodityId
	 */
	void removeByCommodity(Integer commodityId);

}
