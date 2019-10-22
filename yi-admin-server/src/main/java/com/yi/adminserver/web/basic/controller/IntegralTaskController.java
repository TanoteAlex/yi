/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import com.yi.core.basic.domain.bo.IntegralTaskBo;
import com.yi.core.basic.domain.entity.IntegralTask;
import com.yi.core.basic.domain.vo.IntegralTaskListVo;
import com.yi.core.basic.domain.vo.IntegralTaskVo;
import com.yi.core.basic.service.IIntegralTaskService;
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
 *  积分任务
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("积分任务控制层")
@RestController
@RequestMapping(value = "/integralTask")
public class IntegralTaskController {

    private final Logger LOG = LoggerFactory.getLogger(IntegralTaskController.class);


    @Resource
    private IIntegralTaskService integralTaskService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取积分任务列表", notes = "根据分页参数查询积分任务列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<IntegralTaskListVo> queryIntegralTask(@RequestBody Pagination<IntegralTask> query) {
        Page<IntegralTaskListVo> page = integralTaskService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看积分任务信息", notes = "根据积分任务Id获取积分任务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "积分任务Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewIntegralTask(@RequestParam("id") int integralTaskId) {
        try {
            IntegralTaskVo integralTask = integralTaskService.getIntegralTaskVoById(integralTaskId);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("get IntegralTask failure : id=integralTaskId", ex);
            return RestUtils.error("get IntegralTask failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增积分任务", notes = "添加积分任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "integralTask", value = "积分任务对象", required = true, dataType = "IntegralTaskBo")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addIntegralTask(@RequestBody IntegralTaskBo integralTask) {
        try {
            IntegralTaskVo dbIntegralTask = integralTaskService.addIntegralTask(integralTask);
            return RestUtils.successWhenNotNull(dbIntegralTask);
        } catch (BusinessException ex) {
            LOG.error("add IntegralTask failure : integralTask", integralTask, ex);
            return RestUtils.error("add IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新积分任务", notes = "修改积分任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "integralTask", value = "积分任务对象", required = true, dataType = "IntegralTaskBo")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateIntegralTask(@RequestBody IntegralTaskBo integralTask) {
        try {
            IntegralTaskVo dbIntegralTask = integralTaskService.updateIntegralTask(integralTask);
            return RestUtils.successWhenNotNull(dbIntegralTask);
        } catch (BusinessException ex) {
            LOG.error("update IntegralTask failure : integralTask", integralTask, ex);
            return RestUtils.error("update IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除积分任务", notes = "删除积分任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "积分任务Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeIntegralTaskById(@RequestParam("id") int integralTaskId) {
        try {
            integralTaskService.removeIntegralTaskById(integralTaskId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove IntegralTask failure : id=integralTaskId", ex);
            return RestUtils.error("remove IntegralTask failure : " + ex.getMessage());
        }
    }

    /**
     * 修改成长值
     **/
    @ApiOperation(value = "修改成长值", notes = "修改成长值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "积分任务Id", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "growthValue", value = "成长值", required = true, dataType = "Int")
    })
    @RequestMapping(value = "updateGrowthValue", method = RequestMethod.GET)
    public RestResult updateGrowthValue(@RequestParam("id") int integralTaskId,@RequestParam("growthValue") int growthValue) {
        try {
            IntegralTaskVo integralTask = integralTaskService.updateGrowthValue(integralTaskId,growthValue);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("update DailyTask failure : dailyTask", integralTaskId, ex);
            LOG.error("update DailyTask failure : dailyTask", growthValue, ex);
            return RestUtils.error("update DailyTask failure : " + ex.getMessage());
        }
    }


    /**
     * 修改状态
     **/
    @ApiOperation(value = "修改状态", notes = "修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "积分任务Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "updateState", method = RequestMethod.GET)
    public RestResult updateState(@RequestParam("id") int integralTaskId) {
        try {
            IntegralTaskVo integralTask = integralTaskService.updateState(integralTaskId);
            return RestUtils.successWhenNotNull(integralTask);
        } catch (BusinessException ex) {
            LOG.error("update DailyTask failure : dailyTask", integralTaskId, ex);
            return RestUtils.error("update DailyTask failure : " + ex.getMessage());
        }
    }


}