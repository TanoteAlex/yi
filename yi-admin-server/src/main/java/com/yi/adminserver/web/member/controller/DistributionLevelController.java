/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.member.controller;

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

import com.yi.core.member.domain.bo.DistributionLevelBo;
import com.yi.core.member.domain.entity.DistributionLevel;
import com.yi.core.member.domain.vo.DistributionLevelListVo;
import com.yi.core.member.domain.vo.DistributionLevelVo;
import com.yi.core.member.service.IDistributionLevelService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 分销等级
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("分销等级控制层")
@RestController
@RequestMapping(value = "/distributionLevel")
public class DistributionLevelController {

	private final Logger LOG = LoggerFactory.getLogger(DistributionLevelController.class);

	@Resource
	private IDistributionLevelService distributionLevelService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取分销等级列表", notes = "根据分页参数查询分销等级列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<DistributionLevelListVo> queryDistributionLevel(@RequestBody Pagination<DistributionLevel> query) {
		Page<DistributionLevelListVo> page = distributionLevelService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增分销等级", notes = "添加分销等级")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "distributionLevelBo", value = "分销等级对象", required = true, dataType = "DistributionLevelBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addDistributionLevel(@RequestBody DistributionLevelBo distributionLevelBo) {
		try {
			DistributionLevelVo distributionLevelVo = distributionLevelService.addDistributionLevel(distributionLevelBo);
			return RestUtils.successWhenNotNull(distributionLevelVo);
		} catch (BusinessException ex) {
			LOG.error("add DistributionLevel failure : {}", distributionLevelBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新分销等级", notes = "修改分销等级")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "distributionLevelBo", value = "分销等级对象", required = true, dataType = "DistributionLevelBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateDistributionLevel(@RequestBody DistributionLevelBo distributionLevelBo) {
		try {
			DistributionLevelVo distributionLevelVo = distributionLevelService.updateDistributionLevel(distributionLevelBo);
			return RestUtils.successWhenNotNull(distributionLevelVo);
		} catch (BusinessException ex) {
			LOG.error("update DistributionLevel failure : {}", distributionLevelBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除分销等级", notes = "删除分销等级")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "分销等级Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeDistributionLevelById(@RequestParam("id") int distributionLevelId) {
		try {
			distributionLevelService.removeDistributionLevelById(distributionLevelId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove DistributionLevel failure : id={}", distributionLevelId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "查看分销等级信息", notes = "编辑时根据分销等级Id获取分销等级信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "分销等级Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getDistributionLevelBoById(@RequestParam("id") int distributionLevelId) {
		try {
			DistributionLevelBo distributionLevelBo = distributionLevelService.getBoById(distributionLevelId);
			return RestUtils.successWhenNotNull(distributionLevelBo);
		} catch (BusinessException ex) {
			LOG.error("get DistributionLevel failure : id={}", distributionLevelId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看分销等级信息", notes = "根据分销等级Id获取分销等级信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "分销等级Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getDistributionLevelVoById(@RequestParam("id") int distributionLevelId) {
		try {
			DistributionLevelVo distributionLevelVo = distributionLevelService.getVoById(distributionLevelId);
			return RestUtils.successWhenNotNull(distributionLevelVo);
		} catch (BusinessException ex) {
			LOG.error("get DistributionLevel failure : id={}", distributionLevelId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}