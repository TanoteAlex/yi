/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.RegionBo;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.domain.vo.RegionListVo;
import com.yi.core.basic.domain.vo.RegionVo;
import com.yi.core.basic.service.IRegionService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 地区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Deprecated
@RestController
@RequestMapping(value = "/region")
public class RegionController {

	private final Logger LOG = LoggerFactory.getLogger(RegionController.class);

	@Resource
	private IRegionService regionService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RegionListVo> queryRegion(@RequestBody Pagination<Region> query) {
		Page<RegionListVo> page = regionService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewRegion(@RequestParam("id") int regionId) {
		try {
			RegionVo region = regionService.getVoById(regionId);
			return RestUtils.successWhenNotNull(region);
		} catch (BusinessException ex) {
			LOG.error("get Region failure : id=regionId", ex);
			return RestUtils.error("get Region failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addRegion(@RequestBody RegionBo region) {
		try {
			RegionVo dbRegion = regionService.addRegion(region);
			return RestUtils.successWhenNotNull(dbRegion);
		} catch (BusinessException ex) {
			LOG.error("add Region failure : region", region, ex);
			return RestUtils.error("add Region failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateRegion(@RequestBody RegionBo region) {
		try {
			RegionVo dbRegion = regionService.updateRegion(region);
			return RestUtils.successWhenNotNull(dbRegion);
		} catch (BusinessException ex) {
			LOG.error("update Region failure : region", region, ex);
			return RestUtils.error("update Region failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRegionById(@RequestParam("id") int regionId) {
		try {
			regionService.removeRegionById(regionId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Region failure : id=regionId", ex);
			return RestUtils.error("remove Region failure : " + ex.getMessage());
		}
	}

}