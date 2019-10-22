/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

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

import com.yi.core.basic.domain.bo.AreaBo;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.vo.AreaListVo;
import com.yi.core.basic.domain.vo.AreaVo;
import com.yi.core.basic.service.IAreaService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import java.util.List;

/**
 * 省市区 基础数据
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("地区控制层")
@RestController
@RequestMapping(value = "/area")
public class AreaController {

	private final Logger LOG = LoggerFactory.getLogger(AreaController.class);

	@Resource
	private IAreaService areaService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取地区列表", notes = "根据分页参数查询地区列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AreaListVo> queryArea(@RequestBody Pagination<Area> query) {
		Page<AreaListVo> page = areaService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看地区信息", notes = "根据地区Id获取地区信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "地区Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewArea(@RequestParam("id") int areaId) {
		try {
			AreaVo area = areaService.getAreaVoById(areaId);
			return RestUtils.successWhenNotNull(area);
		} catch (BusinessException ex) {
			LOG.error("get Area failure : id=areaId", ex);
			return RestUtils.error("get Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增地区", notes = "添加地区")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "area", value = "地区对象", required = true, dataType = "AreaBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addArea(@RequestBody AreaBo area) {
		try {
			AreaVo dbArea = areaService.addArea(area);
			return RestUtils.successWhenNotNull(dbArea);
		} catch (BusinessException ex) {
			LOG.error("add Area failure : area", area, ex);
			return RestUtils.error("add Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新地区", notes = "修改地区")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "area", value = "地区对象", required = true, dataType = "AreaBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateArea(@RequestBody AreaBo area) {
		try {
			AreaVo dbArea = areaService.updateArea(area);
			return RestUtils.successWhenNotNull(dbArea);
		} catch (BusinessException ex) {
			LOG.error("update Area failure : area", area, ex);
			return RestUtils.error("update Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除地区", notes = "删除地区")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "地区Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAreaById(@RequestParam("id") int areaId) {
		try {
			areaService.removeAreaById(areaId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Area failure : id=areaId", ex);
			return RestUtils.error("remove Area failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询地区所有数据
	 */
	@ApiOperation(value = "查询上级地区所有数据", notes = "根据上级地区Id获取地区所有数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "parentId", value = "上级地区Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getAreas", method = RequestMethod.GET)
	public RestResult getAreas(@RequestParam("parentId") Integer parentId) {
		try {
			List<Area> dbAreas = areaService.getAreasByParentId(parentId);
			return RestUtils.success(dbAreas);
		} catch (Exception ex) {
			LOG.error("getAreas failure :", ex);
			return RestUtils.error("getAreas failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询地区所有数据
	 */
	@ApiOperation(value = "查询地区所有数据", notes = "查询地区所有数据")
	@RequestMapping(value = "getAllAreas", method = RequestMethod.GET)
	public RestResult getAllAreas() {
		try {
			List<AreaListVo> dbAreas = areaService.getAllAreas();
			return RestUtils.success(dbAreas);
		} catch (Exception ex) {
			LOG.error("getAreas failure :", ex);
			return RestUtils.error("getAreas failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询省
	 */
	@ApiOperation(value = "查询省", notes = "查询省")
	@RequestMapping(value = "getProvinces", method = RequestMethod.GET)
	public RestResult getProvinces() {
		try {
			return RestUtils.success(areaService.getProvinces());
		} catch (Exception ex) {
			LOG.error("getProvinces failure :", ex);
			return RestUtils.error("getProvinces failure : " + ex.getMessage());
		}
	}
}