/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.order.domain.bo.ExpressTemplateBo;
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

import com.yi.core.order.domain.entity.ExpressTemplate;
import com.yi.core.order.domain.vo.ExpressTemplateListVo;
import com.yi.core.order.domain.vo.ExpressTemplateVo;
import com.yi.core.order.service.IExpressTemplateService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import java.util.List;

/**
 * 快递模板
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("快递模板控制层")
@RestController
@RequestMapping(value = "/expressTemplate")
public class ExpressTemplateController {

	private final Logger LOG = LoggerFactory.getLogger(ExpressTemplateController.class);

	@Resource
	private IExpressTemplateService expressTemplateService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取快递模板列表(总后台)", notes = "根据分页参数查询快递模板列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ExpressTemplateListVo> queryExpressTemplate(@RequestBody Pagination<ExpressTemplate> query) {
		Page<ExpressTemplateListVo> page = expressTemplateService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取快递模板列表(供应商)", notes = "根据分页参数查询快递模板列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<ExpressTemplateListVo> queryExpressTemplateForSupplier(@RequestBody Pagination<ExpressTemplate> query,
															HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<ExpressTemplateListVo> page = expressTemplateService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看快递模板信息(总后台)", notes = "根据快递模板Id获取快递模板信息(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "快递模板Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewExpressTemplate(@RequestParam("id") int expressTemplateId) {
		try {
			ExpressTemplateVo expressTemplate = expressTemplateService.getExpressTemplateVoById(expressTemplateId);
			return RestUtils.successWhenNotNull(expressTemplate);
		} catch (BusinessException ex) {
			LOG.error("get ExpressTemplate failure : id=expressTemplateId", ex);
			return RestUtils.error("get ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商查看对象
	 **/
	@ApiOperation(value = "查看快递模板信息(供应商)", notes = "根据快递模板Id获取快递模板信息(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "快递模板Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
	public RestResult viewExpressTemplateForSupplier(@RequestParam("id") int expressTemplateId) {
		try {
			ExpressTemplateVo expressTemplate = expressTemplateService.getExpressTemplateVoById(expressTemplateId);
			return RestUtils.successWhenNotNull(expressTemplate);
		} catch (BusinessException ex) {
			LOG.error("get ExpressTemplate failure : id=expressTemplateId", ex);
			return RestUtils.error("get ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增快递模板(总后台)", notes = "添加快递模板(总后台)")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addExpressTemplate(@RequestBody List<ExpressTemplateBo> expressTemplate) {
		try {
			List<ExpressTemplateListVo> dbExpressTemplate = expressTemplateService.addExpressTemplate(expressTemplate,
					null);
			return RestUtils.successWhenNotNull(dbExpressTemplate);
		} catch (BusinessException ex) {
			LOG.error("add ExpressTemplate failure : expressTemplate", expressTemplate, ex);
			return RestUtils.error("add ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商保存新增对象
	 **/
	@ApiOperation(value = "新增快递模板(供应商)", notes = "添加快递模板(供应商)")
	@RequestMapping(value = "addForSupplier", method = RequestMethod.POST)
	public RestResult addExpressTemplateForSupplier(@RequestBody List<ExpressTemplateBo> expressTemplate, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			List<ExpressTemplateListVo> dbExpressTemplate = expressTemplateService.addExpressTemplate(expressTemplate,supplierToken.getId());
			return RestUtils.successWhenNotNull(dbExpressTemplate);
		} catch (BusinessException ex) {
			LOG.error("add ExpressTemplate failure : expressTemplate", expressTemplate, ex);
			return RestUtils.error("add ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新快递模板(总后台)", notes = "修改快递模板(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "expressTemplate", value = "快递模板对象", required = true, dataType = "ExpressTemplate")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateExpressTemplate(@RequestBody ExpressTemplate expressTemplate) {
		try {
			ExpressTemplateVo dbExpressTemplate = expressTemplateService.updateExpressTemplate(expressTemplate);
			return RestUtils.successWhenNotNull(dbExpressTemplate);
		} catch (BusinessException ex) {
			LOG.error("update ExpressTemplate failure : expressTemplate", expressTemplate, ex);
			return RestUtils.error("update ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商保存更新对象
	 **/
	@ApiOperation(value = "更新快递模板(供应商)", notes = "修改快递模板(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "expressTemplate", value = "快递模板对象", required = true, dataType = "ExpressTemplate")
	})
	@RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
	public RestResult updateExpressTemplateForSupplier(@RequestBody ExpressTemplate expressTemplate) {
		try {
			ExpressTemplateVo dbExpressTemplate = expressTemplateService.updateExpressTemplate(expressTemplate);
			return RestUtils.successWhenNotNull(dbExpressTemplate);
		} catch (BusinessException ex) {
			LOG.error("update ExpressTemplate failure : expressTemplate", expressTemplate, ex);
			return RestUtils.error("update ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除快递模板(总后台)", notes = "删除快递模板(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "快递模板Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeExpressTemplateById(@RequestParam("id") int expressTemplateId) {
		try {
			expressTemplateService.removeExpressTemplateById(expressTemplateId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ExpressTemplate failure : id=expressTemplateId", ex);
			return RestUtils.error("remove ExpressTemplate failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商删除对象
	 **/
	@ApiOperation(value = "删除快递模板(供应商)", notes = "删除快递模板(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "快递模板Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeByIdForSupplier", method = RequestMethod.GET)
	public RestResult removeExpressTemplateByIdForSupplier(@RequestParam("id") int expressTemplateId) {
		try {
			expressTemplateService.removeExpressTemplateById(expressTemplateId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ExpressTemplate failure : id=expressTemplateId", ex);
			return RestUtils.error("remove ExpressTemplate failure : " + ex.getMessage());
		}
	}
}