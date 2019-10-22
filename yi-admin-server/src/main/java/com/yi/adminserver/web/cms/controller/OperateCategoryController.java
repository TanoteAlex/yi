/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.cms.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.service.ICategoryService;
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

import com.yi.core.cms.domain.bo.OperateCategoryBo;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.cms.domain.vo.OperateCategoryListVo;
import com.yi.core.cms.domain.vo.OperateCategoryVo;
import com.yi.core.cms.service.IOperateCategoryService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 运营分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("运营分类控制层")
@RestController
@RequestMapping(value = "/operateCategory")
public class OperateCategoryController {

	private final Logger LOG = LoggerFactory.getLogger(OperateCategoryController.class);

	@Resource
	private IOperateCategoryService operateCategoryService;

	@Resource
	private ICategoryService categoryService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取运营分类列表(总后台)", notes = "根据分页参数查询运营分类列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<OperateCategoryListVo> queryOperateCategory(@RequestBody Pagination<OperateCategory> query) {
		Page<OperateCategoryListVo> page = operateCategoryService.queryListVo(query);
		return page;
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取运营分类列表(供应商)", notes = "根据分页参数查询运营分类列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<OperateCategoryListVo> queryOperateCategoryForSupplier(@RequestBody Pagination<OperateCategory> query) {
		Page<OperateCategoryListVo> page = operateCategoryService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看运营分类信息", notes = "根据运营分类Id获取运营分类信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "运营分类Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewOperateCategory(@RequestParam("id") int operateCategoryId) {
		try {
			OperateCategoryVo operateCategoryVo = operateCategoryService.getOperateCategoryVoById(operateCategoryId);
			return RestUtils.successWhenNotNull(operateCategoryVo);
		} catch (BusinessException ex) {
			LOG.error("get OperateCategory failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增运营分类", notes = "添加运营分类")
	@ApiImplicitParams({ @ApiImplicitParam(name = "operateCategory", value = "运营分类对象", required = true, dataType = "OperateCategoryBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addOperateCategory(@RequestBody OperateCategoryBo operateCategory) {
		try {
			OperateCategoryListVo dbOperateCategory = operateCategoryService.addOperateCategory(operateCategory);
			return RestUtils.successWhenNotNull(dbOperateCategory);
		} catch (BusinessException ex) {
			LOG.error("add OperateCategory failure : {}", operateCategory, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新运营分类", notes = "修改运营分类")
	@ApiImplicitParams({ @ApiImplicitParam(name = "operateCategory", value = "运营分类对象", required = true, dataType = "OperateCategoryBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateOperateCategory(@RequestBody OperateCategoryBo operateCategory) {
		try {
			OperateCategoryVo dbOperateCategory = operateCategoryService.updateOperateCategory(operateCategory);
			return RestUtils.successWhenNotNull(dbOperateCategory);
		} catch (BusinessException ex) {
			LOG.error("update OperateCategory failure : {}", operateCategory, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "更新运营分类", notes = "修改运营分类")
	@ApiImplicitParams({ @ApiImplicitParam(name = "operateCategory", value = "运营分类对象", required = true, dataType = "OperateCategoryBo") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeOperateCategoryById(@RequestParam("id") int operateCategoryId) {
		try {
			operateCategoryService.removeOperateCategoryById(operateCategoryId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove OperateCategory failure : id={}", operateCategoryId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取所有分类
	 **/
	@ApiOperation(value = "获取所有分类(总后台)", notes = "获取所有分类(总后台)")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public RestResult getAll() {
		try {
			return RestUtils.successWhenNotNull(operateCategoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll OperateCategory failure :", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取所有分类
	 **/
	@ApiOperation(value = "获取所有分类(供应商)", notes = "获取所有分类(供应商)")
	@RequestMapping(value = "getAllForSupplier", method = RequestMethod.GET)
	public RestResult getAllForSupplier() {
		try {
			return RestUtils.successWhenNotNull(operateCategoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll OperateCategory failure : ", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}