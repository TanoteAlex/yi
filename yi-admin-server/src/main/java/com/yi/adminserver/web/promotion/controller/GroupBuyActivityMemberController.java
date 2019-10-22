/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.promotion.controller;

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

import com.yi.core.promotion.domain.bo.GroupBuyActivityMemberBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivityMember;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityMemberListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityMemberVo;
import com.yi.core.promotion.service.IGroupBuyActivityMemberService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动成员
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("团购活动成员控制层")
@RestController
@RequestMapping(value = "/groupBuyActivityMember")
public class GroupBuyActivityMemberController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityMemberController.class);

	@Resource
	private IGroupBuyActivityMemberService groupBuyActivityMemberService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购活动成员列表", notes = "根据分页参数查询团购活动成员列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityMemberListVo> queryGroupBuyActivityMember(@RequestBody Pagination<GroupBuyActivityMember> query) {
		Page<GroupBuyActivityMemberListVo> page = groupBuyActivityMemberService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增团购活动成员", notes = "添加团购活动成员")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityMemberBo", value = "团购活动成员对象", required = true, dataType = "GroupBuyActivityMemberBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addGroupBuyActivityMember(@RequestBody GroupBuyActivityMemberBo groupBuyActivityMemberBo) {
		try {
			GroupBuyActivityMemberListVo groupBuyActivityMemberListVo = groupBuyActivityMemberService.addGroupBuyActivityMember(groupBuyActivityMemberBo);
			return RestUtils.successWhenNotNull(groupBuyActivityMemberListVo);
		} catch (BusinessException ex) {
			LOG.error("add GroupBuyActivityMember failure : groupBuyActivityMemberBo", groupBuyActivityMemberBo, ex);
			return RestUtils.error("add GroupBuyActivityMember failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新团购活动成员", notes = "修改团购活动成员")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyActivityMemberBo", value = "团购活动成员对象", required = true, dataType = "GroupBuyActivityMemberBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateGroupBuyActivityMember(@RequestBody GroupBuyActivityMemberBo groupBuyActivityMemberBo) {
		try {
			GroupBuyActivityMemberListVo groupBuyActivityMemberListVo = groupBuyActivityMemberService.updateGroupBuyActivityMember(groupBuyActivityMemberBo);
			return RestUtils.successWhenNotNull(groupBuyActivityMemberListVo);
		} catch (BusinessException ex) {
			LOG.error("update GroupBuyActivityMember failure : groupBuyActivityMemberBo", groupBuyActivityMemberBo, ex);
			return RestUtils.error("update GroupBuyActivityMember failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除团购活动成员", notes = "删除团购活动成员")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动成员Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeGroupBuyActivityMemberById(@RequestParam("id") int groupBuyActivityMemberId) {
		try {
			groupBuyActivityMemberService.removeGroupBuyActivityMemberById(groupBuyActivityMemberId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove GroupBuyActivityMember failure : id=groupBuyActivityMemberId", ex);
			return RestUtils.error("remove GroupBuyActivityMember failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑时获取团购活动成员信息", notes = "编辑时根据团购活动成员Id获取团购活动成员信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动成员Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityMemberBoById(@RequestParam("id") int groupBuyActivityMemberId) {
		try {
			GroupBuyActivityMemberBo groupBuyActivityMemberBo = groupBuyActivityMemberService.getGroupBuyActivityMemberBoById(groupBuyActivityMemberId);
			return RestUtils.successWhenNotNull(groupBuyActivityMemberBo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityMember failure : id=groupBuyActivityMemberId", ex);
			return RestUtils.error("get GroupBuyActivityMember failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看团购活动成员信息", notes = "根据团购活动成员Id获取团购活动成员信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购活动成员Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityMemberVoById(@RequestParam("id") int groupBuyActivityMemberId) {
		try {
			GroupBuyActivityMemberVo groupBuyActivityMemberVo = groupBuyActivityMemberService.getGroupBuyActivityMemberVoById(groupBuyActivityMemberId);
			return RestUtils.successWhenNotNull(groupBuyActivityMemberVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityMember failure : id=groupBuyActivityMemberId", ex);
			return RestUtils.error("get GroupBuyActivityMember failure : " + ex.getMessage());
		}
	}
}