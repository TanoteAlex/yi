/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.commodity.controller;

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

import com.yi.core.commodity.domain.bo.StockBo;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.commodity.domain.vo.StockListVo;
import com.yi.core.commodity.domain.vo.StockVo;
import com.yi.core.commodity.service.IStockService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 库存
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("库存控制层")
@RestController
@RequestMapping(value = "/stock")
public class StockController {

	private final Logger LOG = LoggerFactory.getLogger(StockController.class);

	@Resource
	private IStockService stockService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取库存列表", notes = "根据分页参数查询库存列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<StockListVo> queryStock(@RequestBody Pagination<Stock> query) {
		Page<StockListVo> page = stockService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看库存信息", notes = "根据库存Id获取库存信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "库存Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewStock(@RequestParam("id") int stockId) {
		try {
			StockVo stock = stockService.getStockVoById(stockId);
			return RestUtils.successWhenNotNull(stock);
		} catch (BusinessException ex) {
			LOG.error("get Stock failure : id={}", stockId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增库存", notes = "添加库存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stock", value = "库存对象", required = true, dataType = "StockBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addStock(@RequestBody StockBo stock) {
		try {
			StockVo dbStock = stockService.addStock(stock);
			return RestUtils.successWhenNotNull(dbStock);
		} catch (BusinessException ex) {
			LOG.error("add Stock failure : {}", stock, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新库存", notes = "修改库存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stock", value = "库存对象", required = true, dataType = "StockBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateStock(@RequestBody StockBo stock) {
		try {
			StockVo dbStock = stockService.updateStock(stock);
			return RestUtils.successWhenNotNull(dbStock);
		} catch (BusinessException ex) {
			LOG.error("update Stock failure : {}", stock, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除库存", notes = "删除库存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "库存Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeStockById(@RequestParam("id") int stockId) {
		try {
			stockService.removeStockById(stockId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Stock failure : id={}", stockId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}