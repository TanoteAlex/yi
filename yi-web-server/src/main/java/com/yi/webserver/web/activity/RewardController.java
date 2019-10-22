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

import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.domain.vo.RewardListVo;
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
 * 奖励相关控制层
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
		Page<RewardListVo> page = rewardService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 根据奖励类型查询奖励
	 **/
	@ApiOperation(value = "根据奖励类型查询奖励", notes = "根据奖励类型查询奖励")
	@ApiImplicitParams({ @ApiImplicitParam(name = "rewardType", value = "奖励类型", required = true, dataType = "Int") })
	@RequestMapping(value = "getRewardsByRewardType", method = RequestMethod.GET)
	public RestResult getRewardsByRewardType(@RequestParam("rewardType") int rewardType) {
		try {
			return RestUtils.success(rewardService.getRewardsByRewardType(rewardType));
		} catch (BusinessException ex) {
			LOG.error("get Rewards failure : rewardType={}", rewardType, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}