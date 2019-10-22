/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.finance.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yi.core.finance.service.ISupplierSaleStatsService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("供应商销售统计控制层")
@RestController
@RequestMapping(value = "/supplierSaleStats")
public class SupplierSaleStatsController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierSaleStatsController.class);

	@Resource
	private ISupplierSaleStatsService supplierSaleStatsService;

	/**
	 * 查询供应商销售合计数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询供应商销售合计数据", notes = "查询供应商销售合计数据")
	@RequestMapping(value = "getSupplierSaleTotal", method = RequestMethod.POST)
	public RestResult getSupplierSaleTotal(@RequestBody SupplierSaleStatsVo supplierSaleStats) {
		try {
			return RestUtils.success(supplierSaleStatsService.getSupplierSaleTotal(supplierSaleStats));
		} catch (Exception ex) {
			LOG.error("get TotalSaleStats has exception", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询供应商销售集合
	 * 
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "获取供应商销售列表", notes = "根据分页参数查询供应商销售统计列表")
	@RequestMapping(value = "querySupplierSaleList", method = RequestMethod.POST)
	public Page<SupplierSaleStatsVo> querySupplierSaleList(@RequestBody Pagination<SupplierSaleStatsVo> query) {
		return supplierSaleStatsService.querySupplierSaleList(query);
	}

}