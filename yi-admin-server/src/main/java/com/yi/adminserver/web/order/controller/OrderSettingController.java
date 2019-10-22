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

import com.yi.core.order.domain.bo.OrderSettingBo;
import com.yi.core.order.domain.entity.OrderSetting;
import com.yi.core.order.domain.vo.OrderSettingVo;
import com.yi.core.order.service.IOrderSettingService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 订单设置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("订单设置控制层")
@RestController
@RequestMapping(value = "/orderSetting")
public class OrderSettingController {

	private final Logger LOG = LoggerFactory.getLogger(OrderSettingController.class);

	@Resource
	private IOrderSettingService orderSettingService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取订单设置列表", notes = "根据分页参数查询订单设置列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<OrderSetting> queryOrderSetting(@RequestBody Pagination<OrderSetting> query) {
		Page<OrderSetting> page = orderSettingService.query(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看订单设置信息", notes = "根据订单设置Id获取订单设置信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "订单设置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOrderSetting(@RequestParam("id") int orderSettingId) {
		try {
			OrderSettingVo orderSetting = orderSettingService.getOrderSettingVoById(orderSettingId);
			return RestUtils.successWhenNotNull(orderSetting);
		} catch (BusinessException ex) {
			LOG.error("get OrderSetting failure : id=orderSettingId", ex);
			return RestUtils.error("get OrderSetting failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增订单设置", notes = "添加订单设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderSetting", value = "订单设置对象", required = true, dataType = "OrderSettingBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOrderSetting(@RequestBody OrderSettingBo orderSetting) {
		try {
			OrderSettingVo dbOrderSetting = orderSettingService.addOrderSetting(orderSetting);
			return RestUtils.successWhenNotNull(dbOrderSetting);
		} catch (BusinessException ex) {
			LOG.error("add OrderSetting failure : orderSetting", orderSetting, ex);
			return RestUtils.error("add OrderSetting failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新订单设置", notes = "修改订单设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderSetting", value = "订单设置对象", required = true, dataType = "OrderSettingBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOrderSetting(@RequestBody OrderSettingBo orderSetting) {
		try {
			OrderSettingVo dbOrderSetting = orderSettingService.updateOrderSetting(orderSetting);
			return RestUtils.successWhenNotNull(dbOrderSetting);
		} catch (BusinessException ex) {
			LOG.error("update OrderSetting failure : orderSetting", orderSetting, ex);
			return RestUtils.error("update OrderSetting failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除订单设置", notes = "删除订单设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "订单设置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOrderSettingById(@RequestParam("id") int orderSettingId) {
		try {
			orderSettingService.removeOrderSettingById(orderSettingId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove OrderSetting failure : id={}", orderSettingId, ex);
			return RestUtils.error("remove OrderSetting failure : " + ex.getMessage());
		}
	}

	/**
	 * 超时判断
	 **/
	@ApiOperation(value = "超时判断", notes = "超时判断")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "订单Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "minute", value = "时间", required = true, dataType = "Int"),
	})
	@RequestMapping(value = "updateTimeoutValue", method = RequestMethod.GET)
	public RestResult updateTimeoutValue(@RequestParam("id") int id, @RequestParam("minute") int minute) {
		try {
			OrderSettingVo orderSetting = orderSettingService.updateTimeoutValue(id, minute);
			return RestUtils.successWhenNotNull(orderSetting);
		} catch (BusinessException ex) {
			LOG.error("update DailyTask failure : orderSetting", minute, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}