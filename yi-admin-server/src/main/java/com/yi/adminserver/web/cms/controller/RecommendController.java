/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.cms.controller;

import javax.annotation.Resource;

import com.yi.core.cms.domain.bo.RecommendBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.vo.RecommendListVo;
import com.yi.core.cms.domain.vo.RecommendVo;
import com.yi.core.cms.service.IRecommendService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("推荐位控制层")
@RestController
@RequestMapping(value = "/recommend")
public class RecommendController {

	private final Logger LOG = LoggerFactory.getLogger(RecommendController.class);

	@Resource
	private IRecommendService recommendService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取推荐位列表", notes = "根据分页参数查询推荐位列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RecommendListVo> queryRecommend(@RequestBody Pagination<Recommend> query) {
		Page<RecommendListVo> page = recommendService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看推荐位信息", notes = "根据推荐位Id获取推荐位信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "推荐位Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRecommend(@RequestParam("id") int recommendId) {
		try {
			RecommendVo recommend = recommendService.getRecommendVoById(recommendId);
			return RestUtils.successWhenNotNull(recommend);
		} catch (BusinessException ex) {
			LOG.error("get Recommend failure : id={}", recommendId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增推荐位", notes = "添加推荐位")
	@ApiImplicitParams({ @ApiImplicitParam(name = "recommend", value = "推荐位对象", required = true, dataType = "RecommendBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRecommend(@RequestBody RecommendBo recommend) {
		try {
			RecommendListVo dbRecommend = recommendService.addRecommend(recommend);
			return RestUtils.successWhenNotNull(dbRecommend);
		} catch (BusinessException ex) {
			LOG.error("add Recommend failure : {}", recommend, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新推荐位", notes = "修改推荐位")
	@ApiImplicitParams({ @ApiImplicitParam(name = "recommend", value = "推荐位对象", required = true, dataType = "RecommendBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRecommend(@RequestBody RecommendBo recommend) {
		try {
			RecommendListVo dbRecommend = recommendService.updateRecommend(recommend);
			return RestUtils.successWhenNotNull(dbRecommend);
		} catch (BusinessException ex) {
			LOG.error("update Recommend failure : {}", recommend, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除推荐位", notes = "删除推荐位")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "推荐位Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRecommendById(@RequestParam("id") int recommendId) {
		try {
			recommendService.removeRecommendById(recommendId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Recommend failure : id={}", recommendId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	@ApiOperation(value = "禁用推荐位", notes = "禁用推荐位")
	@ApiImplicitParams({ @ApiImplicitParam(name = "recommendId", value = "推荐位Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateStateDisable", method = RequestMethod.GET)
	public RestResult updateStateDisable(@RequestParam("recommendId") int recommendId) {
		try {
			recommendService.updateStateDisable(recommendId);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception ex) {
			LOG.error("update Recommend failure : id={}", recommendId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	@ApiOperation(value = "启用推荐位", notes = "启用推荐位")
	@ApiImplicitParams({ @ApiImplicitParam(name = "recommendId", value = "推荐位Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateStateEnable", method = RequestMethod.GET)
	public RestResult updateStateEnable(@RequestParam("recommendId") int recommendId) {
		try {
			recommendService.updateStateEnable(recommendId);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception ex) {
			LOG.error("update Recommend failure : id={}", recommendId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}