/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.supplier.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.stats.domain.vo.SupplierDataVo;
import com.yi.core.stats.service.ISupplierStatsService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 供应商
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("供应商控制层")
@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierController.class);

	@Resource
	private ISupplierService supplierService;

	@Resource
	private ISupplierStatsService supplierStatsService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取供应商列表", notes = "根据分页参数查询供应商列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierListVo> querySupplier(@RequestBody Pagination<Supplier> query) {
		Page<SupplierListVo> page = supplierService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看供应商信息", notes = "根据供应商Id获取供应商信息")
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplier(@RequestParam("id") int supplierId) {
		try {
			SupplierVo supplier = supplierService.getSupplierVoById(supplierId);
			return RestUtils.successWhenNotNull(supplier);
		} catch (BusinessException ex) {
			LOG.error("get Supplier failure : id=supplierId", ex);
			return RestUtils.error("get Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增供应商", notes = "添加供应商")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplier", value = "供应商对象", required = true, dataType = "SupplierBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplier(@RequestBody SupplierBo supplier) {
		try {
			SupplierVo supplierListVo = supplierService.addSupplier(supplier);
			return RestUtils.successWhenNotNull(supplierListVo);
		} catch (BusinessException ex) {
			LOG.error("add Supplier failure : supplier", supplier, ex);
			return RestUtils.error("add Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新供应商", notes = "修改供应商")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplier", value = "供应商对象", required = true, dataType = "SupplierBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplier(@RequestBody SupplierBo supplier) {
		try {
			SupplierVo supplierVo = supplierService.updateSupplier(supplier);
			return RestUtils.successWhenNotNull(supplierVo);
		} catch (BusinessException ex) {
			LOG.error("update Supplier failure : supplier", supplier, ex);
			return RestUtils.error("update Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商保存更新对象
	 **/
	@ApiOperation(value = "更新供应商(供应商)", notes = "修改供应商(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplier", value = "供应商对象", required = true, dataType = "SupplierBo") })
	@RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
	public RestResult updateSupplierForSupplier(@RequestBody SupplierBo supplier) {
		try {
			// Supplier dbSupplier = supplierService.updateSupplier(supplier);
			SupplierVo supplierListVo = supplierService.updateSupplier(supplier);
			return RestUtils.successWhenNotNull(supplierListVo);
		} catch (BusinessException ex) {
			LOG.error("update Supplier failure : supplier", supplier, ex);
			return RestUtils.error("update Supplier failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除供应商", notes = "删除供应商")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierById(@RequestParam("id") int supplierId) {
		try {
			supplierService.removeSupplierById(supplierId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Supplier failure : id=supplierId", ex);
			return RestUtils.error("remove Supplier failure : " + ex.getMessage());
		}
	}

	@ApiOperation(value = "修改供应商状态", notes = "修改供应商状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierId", value = "供应商Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("supplierId") int supplierId) {
		try {
			SupplierListVo supplier = supplierService.updateState(supplierId);
			return RestUtils.success(supplier);
		} catch (Exception ex) {
			LOG.error("update Supplier failure : id={}", supplierId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商 概况
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询供应商 概况", notes = "查询供应商 概况")
	@RequestMapping(value = "querySupplierData", method = RequestMethod.GET)
	public RestResult querySupplierData() {
		try {
			SupplierDataVo supplierDataVo = supplierStatsService.getSupplierData();
			return RestUtils.successWhenNotNull(supplierDataVo);
		} catch (BusinessException ex) {
			LOG.error("query SupplierData failure : ", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商获取当前供应商信息
	 */
	@ApiOperation(value = "获取当前供应商信息(供应商)", notes = "获取当前供应商信息(供应商)")
	@RequestMapping(value = "getForSupplier", method = RequestMethod.GET)
	public RestResult viewSupplierForSupplier(HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierVo supplier = supplierService.getSupplierVoById(supplierToken.getId());
			return RestUtils.successWhenNotNull(supplier);
		} catch (BusinessException ex) {
			LOG.error("get Supplier failure : id=supplierId", ex);
			return RestUtils.error("get Supplier failure : " + ex.getMessage());
		}
	}

}