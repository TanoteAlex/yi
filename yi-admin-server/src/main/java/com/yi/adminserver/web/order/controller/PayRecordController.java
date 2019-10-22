/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

import com.yi.core.order.domain.bo.PayRecordBo;
import com.yi.core.order.domain.entity.PayRecord;
import com.yi.core.order.domain.vo.PayRecordListVo;
import com.yi.core.order.domain.vo.PayRecordVo;

import com.yi.core.order.service.IPayRecordService;
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
 * 支付记录
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("支付记录控制层")
@RestController
@RequestMapping(value = "/payRecord")
public class PayRecordController {

    private final Logger LOG = LoggerFactory.getLogger(PayRecordController.class);


    @Resource
    private IPayRecordService payRecordService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取支付记录列表", notes = "根据分页参数查询支付记录列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<PayRecord> queryPayRecord(@RequestBody Pagination<PayRecord> query) {
        Page<PayRecord> page = payRecordService.query(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看支付记录信息", notes = "根据支付记录Id获取支付记录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "支付记录Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewPayRecord(@RequestParam("id") int payRecordId) {
        try {
            PayRecordVo payRecord = payRecordService.getPayRecordVoById(payRecordId);
            return RestUtils.successWhenNotNull(payRecord);
        } catch (BusinessException ex) {
            LOG.error("get PayRecord failure : id=payRecordId", ex);
            return RestUtils.error("get PayRecord failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增支付记录", notes = "添加支付记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payRecord", value = "支付记录对象", required = true, dataType = "PayRecordBo")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addPayRecord(@RequestBody PayRecordBo payRecord) {
        try {
            PayRecordListVo dbPayRecord = payRecordService.addPayRecord(payRecord);
            return RestUtils.successWhenNotNull(dbPayRecord);
        } catch (BusinessException ex) {
            LOG.error("add PayRecord failure : payRecord", payRecord, ex);
            return RestUtils.error("add PayRecord failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新支付记录", notes = "修改支付记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payRecord", value = "支付记录对象", required = true, dataType = "PayRecordBo")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updatePayRecord(@RequestBody PayRecordBo payRecord) {
        try {
            PayRecordVo dbPayRecord = payRecordService.updatePayRecord(payRecord);
            return RestUtils.successWhenNotNull(dbPayRecord);
        } catch (BusinessException ex) {
            LOG.error("update PayRecord failure : payRecord", payRecord, ex);
            return RestUtils.error("update PayRecord failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除支付记录", notes = "删除支付记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "支付记录Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removePayRecordById(@RequestParam("id") int payRecordId) {
        try {
            payRecordService.removePayRecordById(payRecordId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove PayRecord failure : id=payRecordId", ex);
            return RestUtils.error("remove PayRecord failure : " + ex.getMessage());
        }
    }
}