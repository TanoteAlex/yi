/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.activity.controller;

import javax.annotation.Resource;

import com.yi.core.activity.domain.simple.SalesAreaCommodityIdSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.SalesAreaCommodityBo;
import com.yi.core.activity.domain.entity.SalesAreaCommodity;
import com.yi.core.activity.domain.entity.SalesAreaCommodityId;
import com.yi.core.activity.domain.vo.SalesAreaCommodityListVo;
import com.yi.core.activity.domain.vo.SalesAreaCommodityVo;
import com.yi.core.activity.service.ISalesAreaCommodityService;
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
@RequestMapping(value = "/salesAreaCommodity")
public class SalesAreaCommodityController {

	private final Logger LOG = LoggerFactory.getLogger(SalesAreaCommodityController.class);

	@Resource
	private ISalesAreaCommodityService salesAreaCommodityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SalesAreaCommodityListVo> querySalesAreaCommodity(@RequestBody Pagination<SalesAreaCommodity> query) {
		Page<SalesAreaCommodityListVo> page = salesAreaCommodityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSalesAreaCommodity(@RequestBody SalesAreaCommodityBo salesAreaCommodityBo) {
		try {
			SalesAreaCommodityListVo salesAreaCommodityListVo = salesAreaCommodityService.addSalesAreaCommodity(salesAreaCommodityBo);
			return RestUtils.successWhenNotNull(salesAreaCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("add SalesAreaCommodity failure : {}", salesAreaCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSalesAreaCommodity(@RequestBody SalesAreaCommodityBo salesAreaCommodityBo) {
		try {
			SalesAreaCommodityListVo salesAreaCommodityListVo = salesAreaCommodityService.updateSalesAreaCommodity(salesAreaCommodityBo);
			return RestUtils.successWhenNotNull(salesAreaCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("update SalesAreaCommodity failure : {}", salesAreaCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.POST)
	public RestResult removeSalesAreaCommodityById(@RequestBody SalesAreaCommodityId salesAreaCommodityId) {
		try {
			salesAreaCommodityService.removeSalesAreaCommodityById(salesAreaCommodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SalesAreaCommodity failure : id={}", salesAreaCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.POST)
	public RestResult getSalesAreaCommodityBoById(@RequestBody SalesAreaCommodityIdSimple id) {
		try {
			SalesAreaCommodityId salesAreaCommodityId1=new SalesAreaCommodityId(id.getSalesAreaId(),id.getCommodityId());
			SalesAreaCommodityBo salesAreaCommodityBo = salesAreaCommodityService.getSalesAreaCommodityBoById(salesAreaCommodityId1);
			return RestUtils.successWhenNotNull(salesAreaCommodityBo);
		} catch (BusinessException ex) {
			LOG.error("get SalesAreaCommodity failure : id={}", id, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.POST)
	public RestResult getSalesAreaCommodityVoById(@RequestBody SalesAreaCommodityIdSimple id) {
		try {
			SalesAreaCommodityId salesAreaCommodityId1=new SalesAreaCommodityId(id.getSalesAreaId(),id.getCommodityId());
			SalesAreaCommodityVo salesAreaCommodityVo = salesAreaCommodityService.getSalesAreaCommodityVoById(salesAreaCommodityId1);
			return RestUtils.successWhenNotNull(salesAreaCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("get SalesAreaCommodity failure : id={}", id, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}