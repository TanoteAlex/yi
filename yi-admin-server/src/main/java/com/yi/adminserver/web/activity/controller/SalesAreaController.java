/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.activity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.SalesAreaBo;
import com.yi.core.activity.domain.entity.SalesArea;
import com.yi.core.activity.domain.vo.SalesAreaListVo;
import com.yi.core.activity.domain.vo.SalesAreaVo;
import com.yi.core.activity.service.ISalesAreaService;
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
@RequestMapping(value = "/salesArea")
public class SalesAreaController {

	private final Logger LOG = LoggerFactory.getLogger(SalesAreaController.class);

	@Resource
	private ISalesAreaService salesAreaService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SalesAreaListVo> querySalesArea(@RequestBody Pagination<SalesArea> query) {
		Page<SalesAreaListVo> page = salesAreaService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSalesArea(@RequestBody SalesAreaBo salesAreaBo) {
		try {
			SalesAreaListVo salesAreaListVo = salesAreaService.addSalesArea(salesAreaBo);
			return RestUtils.successWhenNotNull(salesAreaListVo);
		} catch (BusinessException ex) {
			LOG.error("add SalesArea failure : salesAreaBo", salesAreaBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSalesArea(@RequestBody SalesAreaBo salesAreaBo) {
		try {
			SalesAreaListVo salesAreaListVo = salesAreaService.updateSalesArea(salesAreaBo);
			return RestUtils.successWhenNotNull(salesAreaListVo);
		} catch (BusinessException ex) {
			LOG.error("update SalesArea failure : salesAreaBo", salesAreaBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSalesAreaById(@RequestParam("id") int salesAreaId) {
		try {
			salesAreaService.removeSalesAreaById(salesAreaId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SalesArea failure : id={}", salesAreaId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getSalesAreaVoById(@RequestParam("id") int salesAreaId) {
		try {
			SalesAreaVo salesAreaVo = salesAreaService.getSalesAreaVoById(salesAreaId);
			return RestUtils.successWhenNotNull(salesAreaVo);
		} catch (BusinessException ex) {
			LOG.error("get SalesArea failure : id={}", salesAreaId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getSalesAreaBoById(@RequestParam("id") int salesAreaId) {
		try {
			SalesAreaBo salesAreaBo = salesAreaService.getSalesAreaBoById(salesAreaId);
			return RestUtils.successWhenNotNull(salesAreaBo);
		} catch (BusinessException ex) {
			LOG.error("get SalesArea failure : id={}", salesAreaId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}