/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.activity.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.AreaColumnCommodityBo;
import com.yi.core.activity.domain.entity.AreaColumnCommodity;
import com.yi.core.activity.domain.entity.AreaColumnCommodityId;
import com.yi.core.activity.domain.vo.AreaColumnCommodityListVo;
import com.yi.core.activity.domain.vo.AreaColumnCommodityVo;
import com.yi.core.activity.service.IAreaColumnCommodityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/areaColumnCommodity")
public class AreaColumnCommodityController {

	private final Logger LOG = LoggerFactory.getLogger(AreaColumnCommodityController.class);

	@Resource
	private IAreaColumnCommodityService areaColumnCommodityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AreaColumnCommodityListVo> queryAreaColumnCommodity(@RequestBody Pagination<AreaColumnCommodity> query) {
		Page<AreaColumnCommodityListVo> page = areaColumnCommodityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAreaColumnCommodity(@RequestBody AreaColumnCommodityBo areaColumnCommodityBo) {
		try {
			AreaColumnCommodityListVo areaColumnCommodityListVo = areaColumnCommodityService.addAreaColumnCommodity(areaColumnCommodityBo);
			return RestUtils.successWhenNotNull(areaColumnCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("add AreaColumnCommodity failure : {}", areaColumnCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAreaColumnCommodity(@RequestBody AreaColumnCommodityBo areaColumnCommodityBo) {
		try {
			AreaColumnCommodityListVo areaColumnCommodityListVo = areaColumnCommodityService.updateAreaColumnCommodity(areaColumnCommodityBo);
			return RestUtils.successWhenNotNull(areaColumnCommodityListVo);
		} catch (BusinessException ex) {
			LOG.error("update AreaColumnCommodity failure : {}", areaColumnCommodityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.POST)
	public RestResult removeAreaColumnCommodityById(@RequestBody AreaColumnCommodityId areaColumnCommodityId) {
		try {
			areaColumnCommodityService.removeAreaColumnCommodityById(areaColumnCommodityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AreaColumnCommodity failure : id={}", areaColumnCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.POST)
	public RestResult getAreaColumnCommodityBoById(@RequestBody AreaColumnCommodityId areaColumnCommodityId) {
		try {
			AreaColumnCommodityBo areaColumnCommodityBo = areaColumnCommodityService.getAreaColumnCommodityBoById(areaColumnCommodityId);
			return RestUtils.successWhenNotNull(areaColumnCommodityBo);
		} catch (BusinessException ex) {
			LOG.error("get AreaColumnCommodity failure : id={}", areaColumnCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.POST)
	public RestResult getAreaColumnCommodityVoById(@RequestBody AreaColumnCommodityId areaColumnCommodityId) {
		try {
			AreaColumnCommodityVo areaColumnCommodityVo = areaColumnCommodityService.getAreaColumnCommodityVoById(areaColumnCommodityId);
			return RestUtils.successWhenNotNull(areaColumnCommodityVo);
		} catch (BusinessException ex) {
			LOG.error("get AreaColumnCommodity failure : id={}", areaColumnCommodityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}