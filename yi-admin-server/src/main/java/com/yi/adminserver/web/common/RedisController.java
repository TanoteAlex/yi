package com.yi.adminserver.web.common;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.adminserver.web.commodity.controller.CommodityController;
import com.yi.core.common.RedisService;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

@RequestMapping("redis")
@RestController
public class RedisController {

	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private RedisService redisService;

	@Resource
	private IMemberService memberService;

	// /**
	// * 查看对象
	// **/
	// @RequestMapping(value = "getById", method = RequestMethod.GET)
	// public RestResult getById(@RequestParam("id") int memberId) {
	// try {
	// if (redisTemplate.hasKey("member#" + memberId)) {
	// return RestUtils.successWhenNotNull(redisTemplate.opsForValue().get("member#"
	// + memberId));
	// } else {
	// MemberVo member = memberService.getMemberVoById(memberId);
	// redisTemplate.opsForValue().set("member#" + memberId, member);
	// return RestUtils.successWhenNotNull(member);
	// }
	// } catch (BusinessException ex) {
	// LOG.error("get Member failure : id={}", memberId, ex);
	// return RestUtils.error(ex.getMessage());
	// }
	// }

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getById(@RequestParam("id") int memberId) {
		try {
			if (redisService.hasKey("member#" + memberId)) {
				return RestUtils.successWhenNotNull(redisService.get("member#" + memberId));
			} else {
				redisService.set("member#" + memberId, "redis");
				return RestUtils.successWhenNotNull(redisService.get("member#" + memberId));
			}
		} catch (BusinessException ex) {
			LOG.error("get Member failure : id={}", memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}
