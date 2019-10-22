/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import javax.annotation.Resource;

import com.yi.core.basic.domain.vo.SmsRecordListVo;
import com.yi.core.basic.domain.vo.SmsRecordVo;
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

import com.yi.core.basic.domain.entity.SmsRecord;
import com.yi.core.basic.service.ISmsRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 短信记录
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("短信记录控制层")
@RestController
@RequestMapping(value = "/smsRecord")
public class SmsRecordController {

    private final Logger LOG = LoggerFactory.getLogger(SmsRecordController.class);


    @Resource
    private ISmsRecordService smsRecordService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取短信记录列表", notes = "根据分页参数查询短信记录列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<SmsRecordListVo> querySmsRecord(@RequestBody Pagination<SmsRecord> query) {
        Page<SmsRecordListVo> page = smsRecordService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看短信记录信息", notes = "根据短信记录Id获取短信记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信记录Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewSmsRecord(@RequestParam("id") int smsRecordId) {
        try {
            SmsRecordVo smsRecord = smsRecordService.getSmsRecordById(smsRecordId);
            return RestUtils.successWhenNotNull(smsRecord);
        } catch (BusinessException ex) {
            LOG.error("get SmsRecord failure : id=smsRecordId", ex);
            return RestUtils.error("get SmsRecord failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增短信记录", notes = "添加短信记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsRecord", value = "短信记录对象", required = true, dataType = "SmsRecord")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addSmsRecord(@RequestBody SmsRecord smsRecord) {
        try {
            SmsRecordVo dbSmsRecord = smsRecordService.addSmsRecord(smsRecord);
            return RestUtils.successWhenNotNull(dbSmsRecord);
        } catch (BusinessException ex) {
            LOG.error("add SmsRecord failure : smsRecord", smsRecord, ex);
            return RestUtils.error("add SmsRecord failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新短信记录", notes = "修改短信记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsRecord", value = "短信记录对象", required = true, dataType = "SmsRecord")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateSmsRecord(@RequestBody SmsRecord smsRecord) {
        try {
            SmsRecordVo dbSmsRecord = smsRecordService.updateSmsRecord(smsRecord);
            return RestUtils.successWhenNotNull(dbSmsRecord);
        } catch (BusinessException ex) {
            LOG.error("update SmsRecord failure : smsRecord", smsRecord, ex);
            return RestUtils.error("update SmsRecord failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除短信记录", notes = "删除短信记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信记录Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeSmsRecordById(@RequestParam("id") int smsRecordId) {
        try {
            smsRecordService.removeSmsRecordById(smsRecordId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove SmsRecord failure : id=smsRecordId", ex);
            return RestUtils.error("remove SmsRecord failure : " + ex.getMessage());
        }
    }
}