/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.cms.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.bo.RecommendCommodityBo;
import com.yi.core.cms.domain.entity.RecommendCommodity;
import com.yi.core.cms.domain.entity.RecommendCommodityId;
import com.yi.core.cms.domain.vo.RecommendCommodityListVo;
import com.yi.core.cms.domain.vo.RecommendCommodityVo;
import com.yi.core.cms.service.IRecommendCommodityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/recommendCommodity")
public class RecommendCommodityController {

	private final Logger LOG = LoggerFactory.getLogger(RecommendCommodityController.class);

	@Resource
	private IRecommendCommodityService recommendCommodityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RecommendCommodityListVo> queryRecommendCommodity(@RequestBody Pagination<RecommendCommodity> query) {
		Page<RecommendCommodityListVo> page = recommendCommodityService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRecommendCommodity(@RequestBody RecommendCommodityBo recommendCommodityBo) {
		try {
			RecommendCommodityListVo recommendCommodityListVo = recommendCommodityService.addRecommendCommodity(recommendCommodityBo);
			return RestUtils.successWhenNotNull(recommendCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("add RecommendCommodity failure : recommendCommodityBo", recommendCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRecommendCommodity(@RequestBody RecommendCommodityBo recommendCommodityBo) {
		try {
			RecommendCommodityListVo recommendCommodityListVo = recommendCommodityService.updateRecommendCommodity(recommendCommodityBo);
			return RestUtils.successWhenNotNull(recommendCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("update RecommendCommodity failure : recommendCommodityBo", recommendCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRecommendCommodityById(@RequestParam("id") RecommendCommodityId recommendCommodityId) {
		try {
			recommendCommodityService.removeRecommendCommodityById(recommendCommodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove RecommendCommodity failure : id={}", recommendCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getRecommendCommodityVoById(@RequestParam("id") RecommendCommodityId recommendCommodityId) {
		try {
			RecommendCommodityVo recommendCommodityVo = recommendCommodityService.getRecommendCommodityVoById(recommendCommodityId);
			return RestUtils.successWhenNotNull(recommendCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("get RecommendCommodity failure : id={}", recommendCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getRecommendCommodityBoById(@RequestParam("id") RecommendCommodityId recommendCommodityId) {
		try {
			RecommendCommodityBo recommendCommodityBo = recommendCommodityService.getRecommendCommodityBoById(recommendCommodityId);
			return RestUtils.successWhenNotNull(recommendCommodityBo);
		} catch (BusinessException ex) {
			LOG.error("get RecommendCommodity failure : id={}", recommendCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}