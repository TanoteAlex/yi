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

import com.yi.core.activity.domain.bo.RewardBo;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.vo.RewardListVo;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.activity.service.IRewardService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "奖励控制层")
@RestController
@RequestMapping(value = "/reward")
public class RewardController {

	private final Logger LOG = LoggerFactory.getLogger(RewardController.class);

	@Resource
	private IRewardService rewardService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取奖励列表", notes = "根据分页参数查询奖励列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<RewardListVo> queryReward(@RequestBody Pagination<Reward> query) {
		Page<RewardListVo> page = rewardService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增奖励", notes = "添加奖励")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rewardBo", value = "奖励对象", required = true, dataType = "RewardBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addReward(@RequestBody RewardBo rewardBo) {
		try {
			RewardListVo rewardListVo = rewardService.addReward(rewardBo);
			return RestUtils.successWhenNotNull(rewardListVo);
		} catch (BusinessException ex) {
			LOG.error("add Reward failure : {}", rewardBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新奖励", notes = "修改奖励")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rewardBo", value = "奖励对象", required = true, dataType = "RewardBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateReward(@RequestBody RewardBo rewardBo) {
		try {
			RewardListVo rewardListVo = rewardService.updateReward(rewardBo);
			return RestUtils.successWhenNotNull(rewardListVo);
		} catch (BusinessException ex) {
			LOG.error("update Reward failure : {}", rewardBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除奖励", notes = "删除奖励")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "奖励Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeRewardById(@RequestParam("id") int rewardId) {
		try {
			rewardService.removeRewardById(rewardId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看奖励信息", notes = "根据奖励Id获取奖励信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "奖励Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getRewardVoById(@RequestParam("id") int rewardId) {
		try {
			RewardVo rewardVo = rewardService.getRewardVoById(rewardId);
			return RestUtils.successWhenNotNull(rewardVo);
		} catch (BusinessException ex) {
			LOG.error("get Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "获取编辑奖励信息", notes = "编辑时根据奖励Id获取奖励信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "奖励Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getRewardBoById(@RequestParam("id") int rewardId) {
		try {
			RewardBo rewardBo = rewardService.getRewardBoById(rewardId);
			return RestUtils.successWhenNotNull(rewardBo);
		} catch (BusinessException ex) {
			LOG.error("get Reward failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 奖励禁用启用
	 */
	@ApiOperation(value = "奖励启用禁用", notes = "根据奖励Id修改启用、禁用状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rewardId", value = "奖励Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("rewardId") int rewardId) {
		try {
			RewardListVo reward = rewardService.updateState(rewardId);
			return RestUtils.success(reward);
		} catch (Exception ex) {
			LOG.error("update state failure : id={}", rewardId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}