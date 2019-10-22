/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import com.yi.core.basic.domain.bo.MessageBo;
import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.domain.vo.MessageListVo;
import com.yi.core.basic.domain.vo.MessageVo;

import com.yi.core.basic.service.IMessageService;
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
 * 消息
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "消息控制层")
@RestController
@RequestMapping(value = "/message")
public class MessageController {

	private final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	@Resource
	private IMessageService messageService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取消息列表", notes = "根据分页参数查询消息列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MessageListVo> queryMessage(@RequestBody Pagination<Message> query) {
		Page<MessageListVo> page = messageService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看消息信息", notes = "根据消息Id获取消息信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "消息Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewMessage(@RequestParam("id") int messageId) {
		try {
			MessageVo message = messageService.getVoById(messageId);
			return RestUtils.successWhenNotNull(message);
		} catch (BusinessException ex) {
			LOG.error("get Message failure : id={}", messageId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增消息", notes = "添加消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "message", value = "消息对象", required = true, dataType = "MessageBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addMessage(@RequestBody MessageBo message) {
		try {
			MessageListVo dbMessage = messageService.addMessage(message);
			return RestUtils.successWhenNotNull(dbMessage);
		} catch (BusinessException ex) {
			LOG.error("add Message failure : {}", message, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新消息", notes = "修改消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "message", value = "消息对象", required = true, dataType = "MessageBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateMessage(@RequestBody MessageBo message) {
		try {
			MessageListVo dbMessage = messageService.updateMessage(message);
			return RestUtils.successWhenNotNull(dbMessage);
		} catch (BusinessException ex) {
			LOG.error("update Message failure : {}", message, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除消息", notes = "删除消息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "消息Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeMessageById(@RequestParam("id") int messageId) {
		try {
			messageService.removeMessageById(messageId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Message failure : id={}", messageId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 更新消息状态
	 **/
	@ApiOperation(value = "更新消息显示状态", notes = "更新消息显示状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "消息Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult enable(@RequestParam("id") int messageId) {
		try {
			messageService.updateState(messageId);
			return RestUtils.success(Boolean.TRUE);
		} catch (Exception ex) {
			LOG.error("update Message failure : id={}", messageId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}