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

import com.yi.core.activity.domain.bo.InviteActivityBo;
import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.vo.InviteActivityListVo;
import com.yi.core.activity.domain.vo.InviteActivityVo;
import com.yi.core.activity.service.IInviteActivityService;
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
		Page<InviteActivityListVo> page = inviteActivityService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addInviteActivity(@RequestBody InviteActivityBo inviteActivityBo) {
		try {
			InviteActivityListVo inviteActivityListVo = inviteActivityService.addInviteActivity(inviteActivityBo);
			return RestUtils.successWhenNotNull(inviteActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("add InviteActivity failure : {}", inviteActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateInviteActivity(@RequestBody InviteActivityBo inviteActivityBo) {
		try {
			InviteActivityListVo inviteActivityListVo = inviteActivityService.updateInviteActivity(inviteActivityBo);
			return RestUtils.successWhenNotNull(inviteActivityListVo);
		} catch (BusinessException ex) {
			LOG.error("update InviteActivity failure : {}", inviteActivityBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeInviteActivityById(@RequestParam("id") int inviteActivityId) {
		try {
			inviteActivityService.removeInviteActivityById(inviteActivityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove InviteActivity failure : id={}", inviteActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getInviteActivityVoById(@RequestParam("id") int inviteActivityId) {
		try {
			InviteActivityVo inviteActivityVo = inviteActivityService.getInviteActivityVoById(inviteActivityId);
			return RestUtils.successWhenNotNull(inviteActivityVo);
		} catch (BusinessException ex) {
			LOG.error("get InviteActivity failure : id={}", inviteActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getInviteActivityBoById(@RequestParam("id") int inviteActivityId) {
		try {
			InviteActivityBo inviteActivityBo = inviteActivityService.getInviteActivityBoById(inviteActivityId);
			return RestUtils.successWhenNotNull(inviteActivityBo);
		} catch (BusinessException ex) {
			LOG.error("get InviteActivity failure : id={}", inviteActivityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}