/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
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

import com.yi.core.promotion.domain.bo.GroupBuyOrderBo;
import com.yi.core.promotion.domain.entity.GroupBuyOrder;
import com.yi.core.promotion.domain.listVo.GroupBuyOrderListVo;
import com.yi.core.promotion.domain.vo.GroupBuyOrderVo;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("团购单控制层")
@RestController
@RequestMapping(value = "/groupBuyOrder")
public class GroupBuyOrderController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyOrderController.class);

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购单列表", notes = "根据分页参数查询团购单列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyOrderListVo> queryGroupBuyOrder(@RequestBody Pagination<GroupBuyOrder> query) {
		Page<GroupBuyOrderListVo> page = groupBuyOrderService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增团购单", notes = "添加团购单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyOrderBo", value = "团购单对象", required = true, dataType = "GroupBuyOrderBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyOrder(@RequestBody GroupBuyOrderBo groupBuyOrderBo) {
		try {
			GroupBuyOrderListVo groupBuyOrderListVo = groupBuyOrderService.addGroupBuyOrder(groupBuyOrderBo);
			return RestUtils.successWhenNotNull(groupBuyOrderListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyOrder failure : openGroupBuyBo", groupBuyOrderBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新团购单", notes = "修改团购单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyOrderBo", value = "团购单对象", required = true, dataType = "GroupBuyOrderBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyOrder(@RequestBody GroupBuyOrderBo groupBuyOrderBo) {
		try {
			GroupBuyOrderListVo groupBuyOrderListVo = groupBuyOrderService.updateGroupBuyOrder(groupBuyOrderBo);
			return RestUtils.successWhenNotNull(groupBuyOrderListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyOrder failure : openGroupBuyBo", groupBuyOrderBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除团购单", notes = "删除团购单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购单Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyOrderById(@RequestParam("id") int openGroupBuyId) {
		try {
			groupBuyOrderService.removeGroupBuyOrderById(openGroupBuyId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyOrder failure : id={}", openGroupBuyId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看团购单信息", notes = "根据团购单Id获取团购单信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购单Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getGroupBuyOrderVoById(@RequestParam("id") int openGroupBuyId) {
		try {
			GroupBuyOrderVo groupBuyOrderVo = groupBuyOrderService.getVoById(openGroupBuyId);
			return RestUtils.successWhenNotNull(groupBuyOrderVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyOrder failure : id={}", openGroupBuyId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑时获取团购单信息", notes = "编辑时根据团购单Id获取团购单信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购单Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyOrderBoById(@RequestParam("id") int openGroupBuyId) {
		try {
			GroupBuyOrderBo groupBuyOrderBo = groupBuyOrderService.getBoById(openGroupBuyId);
			return RestUtils.successWhenNotNull(groupBuyOrderBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyOrder failure : id={}", openGroupBuyId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}