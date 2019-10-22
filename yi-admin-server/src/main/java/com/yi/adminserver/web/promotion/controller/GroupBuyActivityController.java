/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.promotion.controller;

import javax.annotation.Resource;

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

import com.yi.core.promotion.domain.bo.GroupBuyActivityBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityVo;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("团购活动控制层")
@RestController
@RequestMapping(value = "/groupBuyActivity")
public class GroupBuyActivityController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityController.class);

	@Resource
	private IGroupBuyActivityService groupBuyActivityService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购活动列表", notes = "根据分页参数查询团购活动列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityListVo> queryGroupBuyActivity(@RequestBody Pagination<GroupBuyActivity> query) {
		Page<GroupBuyActivityListVo> page = groupBuyActivityService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增团购活动", notes = "添加团购活动")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityBo", value = "团购活动对象", required = true, dataType = "GroupBuyActivityBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyActivity(@RequestBody GroupBuyActivityBo groupBuyActivityBo) {
		try {
			GroupBuyActivityListVo groupBuyActivityListVo = groupBuyActivityService.addGroupBuyActivity(groupBuyActivityBo);
			return RestUtils.successWhenNotNull(groupBuyActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyActivity failure : {}", groupBuyActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新团购活动", notes = "修改团购活动")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityBo", value = "团购活动对象", required = true, dataType = "GroupBuyActivityBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyActivity(@RequestBody GroupBuyActivityBo groupBuyActivityBo) {
		try {
			GroupBuyActivityListVo groupBuyActivityListVo = groupBuyActivityService.updateGroupBuyActivity(groupBuyActivityBo);
			return RestUtils.successWhenNotNull(groupBuyActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyActivity failure : {}", groupBuyActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除团购活动", notes = "删除团购活动")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyActivityById(@RequestParam("id") int groupBuyActivityId) {
		try {
			groupBuyActivityService.removeGroupBuyActivityById(groupBuyActivityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyActivity failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑时获取团购活动信息", notes = "编辑时根据团购活动Id获取团购活动信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityBoById(@RequestParam("id") int groupBuyActivityId) {
		try {
			GroupBuyActivityBo groupBuyActivityBo = groupBuyActivityService.getBoById(groupBuyActivityId);
			return RestUtils.successWhenNotNull(groupBuyActivityBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivity failure : id={}", groupBuyActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看团购活动信息", notes = "根据团购活动Id获取团购活动信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityVoById(@RequestParam("id") int groupBuyActivityId) {
		try {
			GroupBuyActivityVo groupBuyActivityVo = groupBuyActivityService.getVoById(groupBuyActivityId);
			return RestUtils.successWhenNotNull(groupBuyActivityVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivity failure : id={}", groupBuyActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 更新团购活动
	 **/
	@ApiOperation(value = "更新团购活动", notes = "修改团购活动")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "userId", value = "团购活动操作人Id", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "auditing", method = RequestMethod.GET)
	public RestResult auditingGroupBuyActivity(@RequestParam("id") int groupBuyActivityId,@RequestParam("userId") int
			userId) {
		try {
			GroupBuyActivityVo groupBuyActivityVo = groupBuyActivityService.auditingGroupBuyActivity
					(groupBuyActivityId,userId);
			return RestUtils.successWhenNotNull(groupBuyActivityVo);
		} catch (BusinessException ex) {
			LOG.error("团购活动审核失败 : {}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 发布团购活动
	 **/
	@ApiOperation(value = "发布团购活动", notes = "发布团购活动")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "userId", value = "团购活动操作人Id", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "publish", method = RequestMethod.GET)
	public RestResult publish(@RequestParam("id") int groupBuyActivityId) {
		try {
			boolean result = groupBuyActivityService.manualPublishActivity(groupBuyActivityId);
			return RestUtils.successWhenNotNull(result);
		} catch (BusinessException ex) {
			LOG.error("团购活动审核失败 : {}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}