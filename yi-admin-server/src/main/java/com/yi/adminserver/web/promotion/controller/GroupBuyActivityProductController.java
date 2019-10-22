/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.promotion.controller;

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

import com.yi.core.promotion.domain.bo.GroupBuyActivityProductBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityProductListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityProductVo;
import com.yi.core.promotion.service.IGroupBuyActivityProductService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动商品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("团购活动商品控制层")
@RestController
@RequestMapping(value = "/groupBuyActivityProduct")
public class GroupBuyActivityProductController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityProductController.class);

	@Resource
	private IGroupBuyActivityProductService groupBuyActivityProductService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购活动商品列表", notes = "根据分页参数查询团购活动商品列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityProductListVo> queryGroupBuyActivityProduct(@RequestBody Pagination<GroupBuyActivityProduct> query) {
		Page<GroupBuyActivityProductListVo> page = groupBuyActivityProductService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增团购活动商品", notes = "添加团购活动商品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityProductBo", value = "团购活动商品对象", required = true, dataType = "GroupBuyActivityProductBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyActivityProduct(@RequestBody GroupBuyActivityProductBo groupBuyActivityProductBo) {
		try {
			GroupBuyActivityProductListVo groupBuyActivityProductListVo = groupBuyActivityProductService.addGroupBuyActivityProduct(groupBuyActivityProductBo);
			return RestUtils.successWhenNotNull(groupBuyActivityProductListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyActivityProduct failure : groupBuyActivityProductBo", groupBuyActivityProductBo, ex);
			return RestUtils.error("add GroupBuyActivityProduct failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新团购活动商品", notes = "修改团购活动商品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityProductBo", value = "团购活动商品对象", required = true, dataType = "GroupBuyActivityProductBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyActivityProduct(@RequestBody GroupBuyActivityProductBo groupBuyActivityProductBo) {
		try {
			GroupBuyActivityProductListVo groupBuyActivityProductListVo = groupBuyActivityProductService.updateGroupBuyActivityProduct(groupBuyActivityProductBo);
			return RestUtils.successWhenNotNull(groupBuyActivityProductListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyActivityProduct failure : groupBuyActivityProductBo", groupBuyActivityProductBo, ex);
			return RestUtils.error("update GroupBuyActivityProduct failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除团购活动商品", notes = "删除团购活动商品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动商品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyActivityProductById(@RequestParam("id") int groupBuyActivityProductId) {
		try {
			groupBuyActivityProductService.removeGroupBuyActivityProductById(groupBuyActivityProductId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyActivityProduct failure : id=groupBuyActivityProductId", ex);
			return RestUtils.error("remove GroupBuyActivityProduct failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑时获取团购活动商品信息", notes = "编辑时根据团购活动商品Id获取团购活动商品信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动商品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityProductBoById(@RequestParam("id") int groupBuyActivityProductId) {
		try {
			GroupBuyActivityProductBo groupBuyActivityProductBo = groupBuyActivityProductService.getBoById(groupBuyActivityProductId);
			return RestUtils.successWhenNotNull(groupBuyActivityProductBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityProduct failure : id=groupBuyActivityProductId", ex);
			return RestUtils.error("get GroupBuyActivityProduct failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看团购活动商品信息", notes = "根据团购活动商品Id获取团购活动商品信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动商品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityProductVoById(@RequestParam("id") int groupBuyActivityProductId) {
		try {
			GroupBuyActivityProductVo groupBuyActivityProductVo = groupBuyActivityProductService.getVoById(groupBuyActivityProductId);
			return RestUtils.successWhenNotNull(groupBuyActivityProductVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityProduct failure : id=groupBuyActivityProductId", ex);
			return RestUtils.error("get GroupBuyActivityProduct failure : " + ex.getMessage());
		}
	}
}