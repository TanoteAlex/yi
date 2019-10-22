/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import com.yi.core.basic.domain.bo.MemberMessageBo;
import com.yi.core.basic.domain.entity.MemberMessage;
import com.yi.core.basic.domain.vo.MemberMessageListVo;
import com.yi.core.basic.domain.vo.MemberMessageVo;

import com.yi.core.basic.service.IMemberMessageService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 会员消息
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "会员消息控制层")
@RestController
@RequestMapping(value = "/memberMessage")
public class MemberMessageController {

	private final Logger LOG = LoggerFactory.getLogger(MemberMessageController.class);

	@Resource
	private IMemberMessageService memberMessageService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取客户消息列表", notes = "根据分页参数查询客户消息列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberMessageListVo> queryMemberMessage(@RequestBody Pagination<MemberMessage> query) {
		Page<MemberMessageListVo> page = memberMessageService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看会员信息", notes = "根据会员消息Id获取会员消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "会员消息Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewMemberMessage(@RequestParam("id") int memberMessageId) {
		try {
			MemberMessageVo memberMessage = memberMessageService.getVoById(memberMessageId);
			return RestUtils.successWhenNotNull(memberMessage);
		} catch (BusinessException ex) {
			LOG.error("get MemberMessage failure : id={}", memberMessageId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增会员消息", notes = "添加会员消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberMessage", value = "会员消息对象", required = true, dataType = "MemberMessageBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMemberMessage(@RequestBody MemberMessageBo memberMessage) {
		try {
			MemberMessageListVo dbMemberMessage = memberMessageService.addMemberMessage(memberMessage);
			return RestUtils.successWhenNotNull(dbMemberMessage);
		} catch (BusinessException ex) {
			LOG.error("add MemberMessage failure : {}", memberMessage, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新会员消息", notes = "修改会员消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberMessage", value = "会员消息对象", required = true, dataType = "MemberMessageBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMemberMessage(@RequestBody MemberMessageBo memberMessage) {
		try {
			MemberMessageListVo dbMemberMessage = memberMessageService.updateMemberMessage(memberMessage);
			return RestUtils.successWhenNotNull(dbMemberMessage);
		} catch (BusinessException ex) {
			LOG.error("update MemberMessage failure : {}", memberMessage, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除会员消息", notes = "删除会员消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "会员消息Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMemberMessageById(@RequestParam("id") int memberMessageId) {
		try {
			memberMessageService.removeMemberMessageById(memberMessageId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove MemberMessage failure : id={}", memberMessageId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}