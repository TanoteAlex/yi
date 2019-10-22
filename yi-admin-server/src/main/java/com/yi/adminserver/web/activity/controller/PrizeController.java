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

import com.yi.core.activity.domain.bo.PrizeBo;
import com.yi.core.activity.domain.entity.Prize;
import com.yi.core.activity.domain.vo.PrizeListVo;
import com.yi.core.activity.domain.vo.PrizeVo;
import com.yi.core.activity.service.IPrizeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 奖品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("奖品控制层")
@RestController
@RequestMapping(value = "/prize")
public class PrizeController {

	private final Logger LOG = LoggerFactory.getLogger(PrizeController.class);

	@Resource
	private IPrizeService prizeService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取奖品列表", notes = "根据分页参数查询奖品列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<PrizeListVo> queryPrize(@RequestBody Pagination<Prize> query) {
		Page<PrizeListVo> page = prizeService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增奖品", notes = "添加奖品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "prizeBo", value = "奖品对象", required = true, dataType = "PrizeBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addPrize(@RequestBody PrizeBo prizeBo) {
		try {
			PrizeListVo prizeListVo = prizeService.addPrize(prizeBo);
			return RestUtils.successWhenNotNull(prizeListVo);
		} catch (BusinessException ex) {
			LOG.error("add Prize failure : {}", prizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新奖品", notes = "修改奖品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "prizeBo", value = "奖品对象", required = true, dataType = "PrizeBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updatePrize(@RequestBody PrizeBo prizeBo) {
		try {
			PrizeListVo prizeListVo = prizeService.updatePrize(prizeBo);
			return RestUtils.successWhenNotNull(prizeListVo);
		} catch (BusinessException ex) {
			LOG.error("update Prize failure : {}", prizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除奖品", notes = "删除奖品")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "奖品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removePrizeById(@RequestParam("id") int prizeId) {
		try {
			prizeService.removePrizeById(prizeId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看奖品信息", notes = "根据奖品Id获取奖品信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "奖品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getPrizeVoById(@RequestParam("id") int prizeId) {
		try {
			PrizeVo prizeVo = prizeService.getPrizeVoById(prizeId);
			return RestUtils.successWhenNotNull(prizeVo);
		} catch (BusinessException ex) {
			LOG.error("get Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "获取编辑奖品信息", notes = "编辑时根据奖品Id获取奖品信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "奖品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getPrizeBoById(@RequestParam("id") int prizeId) {
		try {
			PrizeBo prizeBo = prizeService.getPrizeBoById(prizeId);
			return RestUtils.successWhenNotNull(prizeBo);
		} catch (BusinessException ex) {
			LOG.error("get Prize failure : id={}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 奖品禁用启用
	 */
	@ApiOperation(value = "奖品启用禁用", notes = "根据奖品Id修改启用、禁用状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "prizeId", value = "奖品Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("prizeId") int prizeId) {
		try {
			PrizeVo prize=prizeService.updateState(prizeId);
			return RestUtils.success(prize);
		} catch (Exception ex) {
			LOG.error("update state failure : id=prizeId", ex);
			return RestUtils.error("update state failure : " + ex.getMessage());
		}
	}

}