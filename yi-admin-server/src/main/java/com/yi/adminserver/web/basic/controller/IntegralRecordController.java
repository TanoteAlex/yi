/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.basic.service.IIntegralRecordService;
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
 * 积分记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("积分记录控制层")
@RestController
@RequestMapping(value = "/integralRecord")
public class IntegralRecordController {

	private final Logger LOG = LoggerFactory.getLogger(IntegralRecordController.class);

	@Resource
	private IIntegralRecordService integralRecordService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取积分记录列表", notes = "根据分页参数查询积分记录列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<IntegralRecordListVo> queryIntegralRecord(@RequestBody Pagination<IntegralRecord> query) {
		Page<IntegralRecordListVo> page = integralRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看积分记录信息", notes = "根据积分记录Id获取积分记录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "积分记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewIntegralRecord(@RequestParam("id") int integralRecordId) {
		try {
			IntegralRecordVo integralRecord = integralRecordService.getIntegralRecordVoById(integralRecordId);
			return RestUtils.successWhenNotNull(integralRecord);
		} catch (BusinessException ex) {
			LOG.error("get IntegralRecord failure : id=integralRecordId", ex);
			return RestUtils.error("get IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增积分记录", notes = "添加积分记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "integralRecord", value = "积分记录对象", required = true, dataType = "IntegralRecordBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addIntegralRecord(@RequestBody IntegralRecordBo integralRecord) {
		try {
			IntegralRecordVo dbIntegralRecord = integralRecordService.addIntegralRecord(integralRecord);
			return RestUtils.successWhenNotNull(dbIntegralRecord);
		} catch (BusinessException ex) {
			LOG.error("add IntegralRecord failure : integralRecord", integralRecord, ex);
			return RestUtils.error("add IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新积分记录", notes = "修改积分记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "integralRecord", value = "积分记录对象", required = true, dataType = "IntegralRecordBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateIntegralRecord(@RequestBody IntegralRecordBo integralRecord) {
		try {
			IntegralRecordVo dbIntegralRecord = integralRecordService.updateIntegralRecord(integralRecord);
			return RestUtils.successWhenNotNull(dbIntegralRecord);
		} catch (BusinessException ex) {
			LOG.error("update IntegralRecord failure : integralRecord", integralRecord, ex);
			return RestUtils.error("update IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除积分记录", notes = "删除积分记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "积分记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeIntegralRecordById(@RequestParam("id") int integralRecordId) {
		try {
			integralRecordService.removeIntegralRecordById(integralRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove IntegralRecord failure : id=integralRecordId", ex);
			return RestUtils.error("remove IntegralRecord failure : " + ex.getMessage());
		}
	}
}