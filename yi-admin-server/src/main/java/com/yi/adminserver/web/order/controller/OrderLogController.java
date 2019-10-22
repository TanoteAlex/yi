/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

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

import com.yi.core.order.domain.entity.OrderLog;
import com.yi.core.order.domain.vo.OrderLogListVo;
import com.yi.core.order.domain.vo.OrderLogVo;
import com.yi.core.order.service.IOrderLogService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 订单日志
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("订单日志控制层")
@RestController
@RequestMapping(value = "/orderLog")
public class OrderLogController {

	private final Logger LOG = LoggerFactory.getLogger(OrderLogController.class);

	@Resource
	private IOrderLogService orderLogService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取订单日志列表", notes = "根据分页参数查询订单日志列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<OrderLogListVo> queryOrderLog(@RequestBody Pagination<OrderLog> query) {
		Page<OrderLogListVo> page = orderLogService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看订单日志信息", notes = "根据订单日志Id获取订单日志信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "订单日志Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOrderLog(@RequestParam("id") int orderLogId) {
		try {
			OrderLogVo orderLog = orderLogService.getOrderLogVoById(orderLogId);
			return RestUtils.successWhenNotNull(orderLog);
		} catch (BusinessException ex) {
			LOG.error("get OrderLog failure : id=orderLogId", ex);
			return RestUtils.error("get OrderLog failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增订单日志", notes = "添加订单日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderLog", value = "订单日志对象", required = true, dataType = "OrderLog")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOrderLog(@RequestBody OrderLog orderLog) {
		try {
			OrderLogVo dbOrderLog = orderLogService.addOrderLog(orderLog);
			return RestUtils.successWhenNotNull(dbOrderLog);
		} catch (BusinessException ex) {
			LOG.error("add OrderLog failure : orderLog", orderLog, ex);
			return RestUtils.error("add OrderLog failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新订单日志", notes = "修改订单日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderLog", value = "订单日志对象", required = true, dataType = "OrderLog")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOrderLog(@RequestBody OrderLog orderLog) {
		try {
			OrderLogVo dbOrderLog = orderLogService.updateOrderLog(orderLog);
			return RestUtils.successWhenNotNull(dbOrderLog);
		} catch (BusinessException ex) {
			LOG.error("update OrderLog failure : orderLog", orderLog, ex);
			return RestUtils.error("update OrderLog failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除订单日志", notes = "删除订单日志")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "订单日志Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOrderLogById(@RequestParam("id") int orderLogId) {
		try {
			orderLogService.removeOrderLogById(orderLogId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove OrderLog failure : id=orderLogId", ex);
			return RestUtils.error("remove OrderLog failure : " + ex.getMessage());
		}
	}
}