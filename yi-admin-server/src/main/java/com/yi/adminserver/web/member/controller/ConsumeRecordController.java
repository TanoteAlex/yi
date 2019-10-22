/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.member.controller;

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

import com.yi.core.member.domain.entity.ConsumeRecord;
import com.yi.core.member.domain.vo.ConsumeRecordListVo;
import com.yi.core.member.domain.vo.ConsumeRecordVo;
import com.yi.core.member.service.IConsumeRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 消费记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("消费记录控制层")
@RestController
@RequestMapping(value = "/consumeRecord")
public class ConsumeRecordController {

	private final Logger LOG = LoggerFactory.getLogger(ConsumeRecordController.class);

	@Resource
	private IConsumeRecordService consumeRecordService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取消费记录列表", notes = "根据分页参数查询消费记录列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ConsumeRecordListVo> queryConsumeRecord(@RequestBody Pagination<ConsumeRecord> query) {
		Page<ConsumeRecordListVo> page = consumeRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看消费记录信息", notes = "根据消费记录Id获取消费记录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "消费记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewConsumeRecord(@RequestParam("id") int consumeRecordId) {
		try {
			ConsumeRecordVo consumeRecord = consumeRecordService.getConsumeRecordVoById(consumeRecordId);
			return RestUtils.successWhenNotNull(consumeRecord);
		} catch (BusinessException ex) {
			LOG.error("get ConsumeRecord failure : id=consumeRecordId", ex);
			return RestUtils.error("get ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增消费记录", notes = "添加消费记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "consumeRecord", value = "消费记录对象", required = true, dataType = "ConsumeRecord")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addConsumeRecord(@RequestBody ConsumeRecord consumeRecord) {
		try {
			ConsumeRecordVo dbConsumeRecord = consumeRecordService.addConsumeRecord(consumeRecord);
			return RestUtils.successWhenNotNull(dbConsumeRecord);
		} catch (BusinessException ex) {
			LOG.error("add ConsumeRecord failure : consumeRecord", consumeRecord, ex);
			return RestUtils.error("add ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新消费记录", notes = "修改消费记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "consumeRecord", value = "消费记录对象", required = true, dataType = "ConsumeRecord")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateConsumeRecord(@RequestBody ConsumeRecord consumeRecord) {
		try {
			ConsumeRecordVo dbConsumeRecord = consumeRecordService.updateConsumeRecord(consumeRecord);
			return RestUtils.successWhenNotNull(dbConsumeRecord);
		} catch (BusinessException ex) {
			LOG.error("update ConsumeRecord failure : consumeRecord", consumeRecord, ex);
			return RestUtils.error("update ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除消费记录", notes = "删除消费记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "消费记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeConsumeRecordById(@RequestParam("id") int consumeRecordId) {
		try {
			consumeRecordService.removeConsumeRecordById(consumeRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ConsumeRecord failure : id=consumeRecordId", ex);
			return RestUtils.error("remove ConsumeRecord failure : " + ex.getMessage());
		}
	}
}