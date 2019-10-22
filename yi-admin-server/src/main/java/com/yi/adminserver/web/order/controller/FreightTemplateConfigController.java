/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
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
import com.yi.core.order.domain.bo.FreightTemplateConfigBo;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.vo.FreightTemplateConfigListVo;
import com.yi.core.order.domain.vo.FreightTemplateConfigVo;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 运费模板配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("运费模板配置控制层")
@RestController
@RequestMapping(value = "/freightTemplateConfig")
public class FreightTemplateConfigController {

	private final Logger LOG = LoggerFactory.getLogger(FreightTemplateConfigController.class);

	@Resource
	private IFreightTemplateConfigService freightTemplateConfigService;

	@Resource
	private ISupplierService supplierService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取运费模板配置列表(总后台)", notes = "根据分页参数查询运费模板配置列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<FreightTemplateConfigListVo> queryFreightTemplateConfig(@RequestBody Pagination<FreightTemplateConfig> query) {
		Page<FreightTemplateConfigListVo> page = freightTemplateConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取运费模板配置列表(供应商)", notes = "根据分页参数查询运费模板配置列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<FreightTemplateConfigListVo> queryFreightTemplateConfigForSupplier(@RequestBody Pagination<FreightTemplateConfig> query) {
		Page<FreightTemplateConfigListVo> page = freightTemplateConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增运费模板配置(总后台)", notes = "添加运费模板配置(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "freightTemplateConfigBo", value = "运费模板配置对象", required = true, dataType = "FreightTemplateConfigBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addFreightTemplateConfig(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo) {
		try {
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.addFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("add FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商保存新增对象
	 **/
	@ApiOperation(value = "新增运费模板配置(供应商)", notes = "添加运费模板配置(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "freightTemplateConfigBo", value = "运费模板配置对象", required = true, dataType = "FreightTemplateConfigBo")
	})
	@RequestMapping(value = "addForSupplier", method = RequestMethod.POST)
	public RestResult addFreightTemplateConfigForSupplier(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			freightTemplateConfigBo.setSupplier(supplierBo);
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.addFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("add FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新运费模板配置(总后台)", notes = "修改运费模板配置(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "freightTemplateConfigBo", value = "运费模板配置对象", required = true, dataType = "FreightTemplateConfigBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateFreightTemplateConfig(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo) {
		try {
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.updateFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("update FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商保存更新对象
	 **/
	@ApiOperation(value = "更新运费模板配置(供应商)", notes = "修改运费模板配置(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "freightTemplateConfigBo", value = "运费模板配置对象", required = true, dataType = "FreightTemplateConfigBo")
	})
	@RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
	public RestResult updateFreightTemplateConfigForSupplier(@RequestBody FreightTemplateConfigBo freightTemplateConfigBo, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			freightTemplateConfigBo.setSupplier(supplierBo);
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.updateFreightTemplateConfig(freightTemplateConfigBo);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("update FreightTemplateConfig failure : freightTemplateConfigBo", freightTemplateConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除运费模板配置(总后台)", notes = "删除运费模板配置(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "运费模板配置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeFreightTemplateConfigById(@RequestParam("id") int freightTemplateConfigId) {
		try {
			freightTemplateConfigService.removeFreightTemplateConfigById(freightTemplateConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商删除对象
	 **/
	@ApiOperation(value = "删除运费模板配置(供应商)", notes = "删除运费模板配置(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "运费模板配置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeByIdForSupplier", method = RequestMethod.GET)
	public RestResult removeFreightTemplateConfigByIdForSupplier(@RequestParam("id") int freightTemplateConfigId) {
		try {
			freightTemplateConfigService.removeFreightTemplateConfigById(freightTemplateConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看运费模板配置信息(总后台)", notes = "根据运费模板配置Id获取运费模板配置信息(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "运费模板配置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getFreightTemplateConfigVoById(@RequestParam("id") int freightTemplateConfigId) {
		try {
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.getVoById(freightTemplateConfigId);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("get FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商查看对象详情
	 **/
	@ApiOperation(value = "查看运费模板配置信息(供应商)", notes = "根据运费模板配置Id获取运费模板配置信息(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "运费模板配置Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
	public RestResult getFreightTemplateConfigVoByIdForSupplier(@RequestParam("id") int freightTemplateConfigId) {
		try {
			FreightTemplateConfigVo freightTemplateConfigVo = freightTemplateConfigService.getVoById(freightTemplateConfigId);
			return RestUtils.successWhenNotNull(freightTemplateConfigVo);
		} catch (BusinessException ex) {
			LOG.error("get FreightTemplateConfig failure : id=freightTemplateConfigId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}