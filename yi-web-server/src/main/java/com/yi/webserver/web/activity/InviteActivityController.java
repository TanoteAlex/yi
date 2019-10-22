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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.vo.InviteActivityListVo;
import com.yi.core.activity.domain.vo.InviteActivityVo;
import com.yi.core.activity.service.IInviteActivityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 邀请有礼活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "邀请有礼活动")
@RestController
@RequestMapping(value = "/inviteActivity")
public class InviteActivityController {

	private final Logger LOG = LoggerFactory.getLogger(InviteActivityController.class);

	@Resource
	private IInviteActivityService inviteActivityService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<InviteActivityListVo> queryInviteActivity(@RequestBody Pagination<InviteActivity> query) {
		Page<InviteActivityListVo> page = inviteActivityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 获取最新的 邀请有礼活动
	 **/
	@ApiOperation(value = "获取最新的邀请有礼活动", notes = "获取最新的邀请有礼活动")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = false, dataType = "Int") })
	@GetMapping(value = "getLatestInviteActivity")
	public RestResult getLatestInviteActivity(@RequestParam(name = "memberId", required = false) int memberId) {
		try {
			InviteActivityVo inviteActivityVo = inviteActivityService.getLatestInviteActivity(memberId);
			return RestUtils.success(inviteActivityVo);
		} catch (BusinessException ex) {
			LOG.error("get Latest InviteActivity failure : ", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}