/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.webserver.web.activity;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.bo.AreaColumnBo;
import com.yi.core.activity.domain.entity.AreaColumn;
import com.yi.core.activity.domain.vo.AreaColumnListVo;
import com.yi.core.activity.domain.vo.AreaColumnVo;
import com.yi.core.activity.service.IAreaColumnService;
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
@RequestMapping(value = "/areaColumn")
public class AreaColumnController {

	private final Logger LOG = LoggerFactory.getLogger(AreaColumnController.class);

	@Resource
	private IAreaColumnService areaColumnService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AreaColumnListVo> queryAreaColumn(@RequestBody Pagination<AreaColumn> query) {
		Page<AreaColumnListVo> page = areaColumnService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAreaColumn(@RequestBody AreaColumnBo areaColumnBo) {
		try {
			AreaColumnListVo areaColumnListVo = areaColumnService.addAreaColumn(areaColumnBo);
			return RestUtils.successWhenNotNull(areaColumnListVo);
		} catch (BusinessException ex) {
			LOG.error("add AreaColumn failure : {}", areaColumnBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAreaColumn(@RequestBody AreaColumnBo areaColumnBo) {
		try {
			AreaColumnListVo areaColumnListVo = areaColumnService.updateAreaColumn(areaColumnBo);
			return RestUtils.successWhenNotNull(areaColumnListVo);
		} catch (BusinessException ex) {
			LOG.error("update AreaColumn failure : {}", areaColumnBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAreaColumnById(@RequestParam("id") int areaColumnId) {
		try {
			areaColumnService.removeAreaColumnById(areaColumnId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AreaColumn failure : id={}", areaColumnId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getAreaColumnBoById(@RequestParam("id") int areaColumnId) {
		try {
			AreaColumnBo areaColumnBo = areaColumnService.getAreaColumnBoById(areaColumnId);
			return RestUtils.successWhenNotNull(areaColumnBo);
		} catch (BusinessException ex) {
			LOG.error("get AreaColumn failure : id={}", areaColumnId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getAreaColumnVoById(@RequestParam("id") int areaColumnId) {
		try {
			AreaColumnVo areaColumnVo = areaColumnService.getAreaColumnVoById(areaColumnId);
			return RestUtils.successWhenNotNull(areaColumnVo);
		} catch (BusinessException ex) {
			LOG.error("get AreaColumn failure : id={}", areaColumnId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}