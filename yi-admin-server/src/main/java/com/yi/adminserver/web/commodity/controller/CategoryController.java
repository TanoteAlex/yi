/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.cms.domain.vo.OperateCategoryListVo;
import com.yi.core.cms.service.IOperateCategoryService;
import com.yi.core.commodity.domain.bo.CategoryBo;
import com.yi.core.commodity.domain.vo.CategoryListVo;
import com.yi.core.commodity.domain.vo.CategoryVo;
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

import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.service.ICategoryService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import java.util.List;

/**
 * 商品分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("商品分类控制层")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

	private final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Resource
	private ICategoryService categoryService;

	@Resource
	private IOperateCategoryService operateCategoryService;

	/**
	 * 获取所有分类
	 **/
	@ApiOperation(value = "获取所有分类(总后台)", notes = "获取所有分类(总后台)")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public RestResult getAll() {
		try {
			return RestUtils.successWhenNotNull(categoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll Category failure : category", ex);
			return RestUtils.error("getAll Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取商品分类列表(总后台)", notes = "根据分页参数查询商品分类列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CategoryListVo> queryCategory(@RequestBody Pagination<Category> query) {
		Page<CategoryListVo> page = categoryService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取商品分类列表(供应商)", notes = "根据分页参数查询商品分类列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<CategoryListVo> queryCategoryForSupplier(@RequestBody Pagination<Category> query) {
		Page<CategoryListVo> page = categoryService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看商品分类信息", notes = "根据商品分类Id获取商品分类信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "商品分类Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCategory(@RequestParam("id") int categoryId) {
		try {
			CategoryVo category = categoryService.getCategoryVoById(categoryId);
			return RestUtils.successWhenNotNull(category);
		} catch (BusinessException ex) {
			LOG.error("get Category failure : id=categoryId", ex);
			return RestUtils.error("get Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增商品分类", notes = "添加商品分类")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "商品分类对象", required = true, dataType = "CategoryBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCategory(@RequestBody CategoryBo categoryBo) {
		try {
			CategoryVo dbCategory = categoryService.addCategory(categoryBo);
			return RestUtils.successWhenNotNull(dbCategory);
		} catch (BusinessException ex) {
			LOG.error("add Category failure : category", categoryBo, ex);
			return RestUtils.error("add Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新商品分类", notes = "修改商品分类")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "商品分类对象", required = true, dataType = "CategoryBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCategory(@RequestBody CategoryBo category) {
		try {
			CategoryVo dbCategory = categoryService.updateCategory(category);
			return RestUtils.successWhenNotNull(dbCategory);
		} catch (BusinessException ex) {
			LOG.error("update Category failure : category", category, ex);
			return RestUtils.error("update Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除商品分类", notes = "删除商品分类")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "商品分类Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCategoryById(@RequestParam("id") int categoryId) {
		try {
			categoryService.removeCategoryById(categoryId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Category failure : id=categoryId", ex);
			return RestUtils.error("remove Category failure : " + ex.getMessage());
		}
	}

	/**
	 * 供应商：获取所有分类
	 **/
	@ApiOperation(value = "获取所有分类(供应商)", notes = "获取所有分类(供应商)")
	@RequestMapping(value = "getAllForSupplier", method = RequestMethod.GET)
	public RestResult getAllForSupplier() {
		try {
			return RestUtils.successWhenNotNull(categoryService.getAll());
		} catch (BusinessException ex) {
			LOG.error("getAll Category failure : category", ex);
			return RestUtils.error("add Category failure : " + ex.getMessage());
		}
	}
}