/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.webserver.web.activity;

import javax.annotation.Resource;

import com.yi.core.activity.domain.vo.InvitePrizeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yi.core.activity.domain.bo.InvitePrizeBo;
import com.yi.core.activity.domain.entity.InvitePrize;
import com.yi.core.activity.domain.vo.InvitePrizeListVo;
import com.yi.core.activity.service.IInvitePrizeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 邀请有礼奖品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "邀请有礼奖品")
@RestController
@RequestMapping(value = "/invitePrize")
public class InvitePrizeController {

	private final Logger LOG = LoggerFactory.getLogger(InvitePrizeController.class);

	@Resource
	private IInvitePrizeService invitePrizeService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取邀请有礼奖品列表", notes = "根据分页参数查询邀请有礼奖品列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<InvitePrizeListVo> queryInvitePrize(@RequestBody Pagination<InvitePrize> query) {
		Page<InvitePrizeListVo> page = invitePrizeService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 领取积分
	 **/
	@PostMapping(value = "receiveIntegral")
	public RestResult receiveIntegral(@RequestBody InvitePrizeBo invitePrizeBo) {
		try {
			invitePrizeService.receiveIntegral(invitePrizeBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("receive Integral failure : {}", invitePrizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 领取优惠券
	 **/
	@PostMapping(value = "receiveCoupon")
	public RestResult receiveCoupon(@RequestBody InvitePrizeBo invitePrizeBo) {
		try {
			invitePrizeService.receiveCoupon(invitePrizeBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("receive Coupon failure : {}", invitePrizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 领取商品
	 **/
	@PostMapping(value = "receiveCommodity")
	public RestResult receiveCommodity(@RequestBody InvitePrizeBo invitePrizeBo) {
		try {
			invitePrizeService.receiveCommodity(invitePrizeBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("receive Commodity failure : {}", invitePrizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}


	/**
	 * 领取商品
	 **/
	@GetMapping(value = "getInvitePrizeById")
	public RestResult getInvitePrizeById(@RequestParam("id") int prizeId) {
		try {
			InvitePrizeVo invitePrizeVo = invitePrizeService.getInvitePrizeVoById(prizeId);
			return RestUtils.successWhenNotNull(invitePrizeVo);
		} catch (BusinessException ex) {
			LOG.error("getInvitePrizeById failure : {}", prizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}