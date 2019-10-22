/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.vo.ProductListVo;
import com.yi.core.commodity.domain.vo.ProductVo;
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

import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("货品控制层")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

	private final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Resource
	private IProductService productService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取货品列表", notes = "根据分页参数查询货品列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ProductListVo> queryProduct(@RequestBody Pagination<Product> query) {
		Page<ProductListVo> page = productService.queryListVo(query);

		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看货品信息", notes = "根据货品Id获取货品信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "货品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewProduct(@RequestParam("id") int productId) {
		try {
			ProductVo product = productService.getVoById(productId);
			return RestUtils.successWhenNotNull(product);
		} catch (BusinessException ex) {
			LOG.error("get Product failure : id=productId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增货品", notes = "添加货品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "product", value = "货品对象", required = true, dataType = "Product")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addProduct(@RequestBody Product product) {
		try {
			Product dbProduct = productService.addProduct(product);
			return RestUtils.successWhenNotNull(dbProduct);
		} catch (BusinessException ex) {
			LOG.error("add Product failure : product", product, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新货品", notes = "修改货品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "product", value = "货品对象", required = true, dataType = "Product")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateProduct(@RequestBody Product product) {
		try {
			Product dbProduct = productService.updateProduct(product);
			return RestUtils.successWhenNotNull(dbProduct);
		} catch (BusinessException ex) {
			LOG.error("update Product failure : product", product, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除货品", notes = "删除货品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "货品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeProductById(@RequestParam("id") int productId) {
		try {
			productService.removeProductById(productId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Product failure : id=productId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}