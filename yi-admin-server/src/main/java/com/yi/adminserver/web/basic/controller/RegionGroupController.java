/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import java.util.List;

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

import com.yi.core.basic.domain.bo.RegionGroupBo;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.vo.RegionGroupListVo;
import com.yi.core.basic.domain.vo.RegionGroupVo;
import com.yi.core.basic.service.IRegionGroupService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 区域
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("区域控制层")
@RestController
@RequestMapping(value = "/regionGroup")
public class RegionGroupController {

	private final Logger LOG = LoggerFactory.getLogger(RegionGroupController.class);

	@Resource
	private IRegionGroupService regionGroupService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取区域列表", notes = "根据分页参数查询区域列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RegionGroupListVo> queryRegionGroup(@RequestBody Pagination<RegionGroup> query) {
		Page<RegionGroupListVo> page = regionGroupService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看区域信息", notes = "根据区域Id获取区域信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "区域Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRegionGroup(@RequestParam("id") int regionGroupId) {
		try {
			RegionGroupVo regionGroupVo = regionGroupService.getVoById(regionGroupId);
			return RestUtils.successWhenNotNull(regionGroupVo);
		} catch (BusinessException ex) {
			LOG.error("get RegionGroup failure : id={}", regionGroupId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增区域", notes = "添加区域")
	@ApiImplicitParams({ @ApiImplicitParam(name = "regionGroup", value = "区域对象", required = true, dataType = "RegionGroupBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRegionGroup(@RequestBody RegionGroupBo regionGroup) {
		try {
			RegionGroupVo dbRegionGroup = regionGroupService.addRegionGroup(regionGroup);
			return RestUtils.successWhenNotNull(dbRegionGroup);
		} catch (BusinessException ex) {
			LOG.error("add RegionGroup failure : regionGroup={}", regionGroup, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新区域", notes = "修改区域")
	@ApiImplicitParams({ @ApiImplicitParam(name = "regionGroup", value = "区域对象", required = true, dataType = "RegionGroupBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRegionGroup(@RequestBody RegionGroupBo regionGroup) {
		try {
			RegionGroupVo dbRegionGroup = regionGroupService.updateRegionGroup(regionGroup);
			return RestUtils.successWhenNotNull(dbRegionGroup);
		} catch (BusinessException ex) {
			LOG.error("update RegionGroup failure : regionGroup={}", regionGroup, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除区域", notes = "删除区域")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "区域Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRegionGroupById(@RequestParam("id") int regionGroupId) {
		try {
			regionGroupService.removeRegionGroupById(regionGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove RegionGroup failure : id={}", regionGroupId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取地区组集合
	 *
	 * @return
	 */
	@ApiOperation(value = "获取地区组集合", notes = "获取地区组集合")
	@RequestMapping(value = "getRegionGroups", method = RequestMethod.GET)
	public RestResult getRegionGroups() {
		try {
			List<RegionGroupListVo> regionGroups = regionGroupService.getRegionGroupListVos();
			return RestUtils.successWhenNotNull(regionGroups);
		} catch (BusinessException ex) {
			LOG.error("get RegionGroups failure", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 地区启用禁用
	 **/
	@ApiOperation(value = "启用禁用区域", notes = "启用禁用区域")
	@ApiImplicitParams({ @ApiImplicitParam(name = "regionGroupId", value = "区域Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("regionGroupId") int regionGroupId) {
		try {
			regionGroupService.updateState(regionGroupId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("update RegionGroup failure : id={}", regionGroupId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}