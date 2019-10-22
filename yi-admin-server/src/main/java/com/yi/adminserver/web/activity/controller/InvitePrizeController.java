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

import com.yi.core.activity.domain.bo.InvitePrizeBo;
import com.yi.core.activity.domain.entity.InvitePrize;
import com.yi.core.activity.domain.vo.InvitePrizeListVo;
import com.yi.core.activity.domain.vo.InvitePrizeVo;
import com.yi.core.activity.service.IInvitePrizeService;
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
@RequestMapping(value = "/invitePrize")
public class InvitePrizeController {

	private final Logger LOG = LoggerFactory.getLogger(InvitePrizeController.class);

	@Resource
	private IInvitePrizeService invitePrizeService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<InvitePrizeListVo> queryInvitePrize(@RequestBody Pagination<InvitePrize> query) {
		Page<InvitePrizeListVo> page = invitePrizeService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addInvitePrize(@RequestBody InvitePrizeBo invitePrizeBo) {
		try {
			InvitePrizeListVo invitePrizeListVo = invitePrizeService.addInvitePrize(invitePrizeBo);
			return RestUtils.successWhenNotNull(invitePrizeListVo);
		} catch (BusinessException ex) {
			LOG.error("add InvitePrize failure : {}", invitePrizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateInvitePrize(@RequestBody InvitePrizeBo invitePrizeBo) {
		try {
			InvitePrizeListVo invitePrizeListVo = invitePrizeService.updateInvitePrize(invitePrizeBo);
			return RestUtils.successWhenNotNull(invitePrizeListVo);
		} catch (BusinessException ex) {
			LOG.error("update InvitePrize failure : {}", invitePrizeBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeInvitePrizeById(@RequestParam("id") int invitePrizeId) {
		try {
			invitePrizeService.removeInvitePrizeById(invitePrizeId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove InvitePrize failure : id={}", invitePrizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getInvitePrizeVoById(@RequestParam("id") int invitePrizeId) {
		try {
			InvitePrizeVo invitePrizeVo = invitePrizeService.getInvitePrizeVoById(invitePrizeId);
			return RestUtils.successWhenNotNull(invitePrizeVo);
		} catch (BusinessException ex) {
			LOG.error("get InvitePrize failure : id={}", invitePrizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getInvitePrizeBoById(@RequestParam("id") int invitePrizeId) {
		try {
			InvitePrizeBo invitePrizeBo = invitePrizeService.getInvitePrizeBoById(invitePrizeId);
			return RestUtils.successWhenNotNull(invitePrizeBo);
		} catch (BusinessException ex) {
			LOG.error("get InvitePrize failure : id={}", invitePrizeId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}