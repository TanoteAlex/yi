/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

import com.yi.core.basic.domain.bo.BasicRuleBo;
import com.yi.core.basic.domain.entity.BasicRule;
import com.yi.core.basic.domain.vo.BasicRuleListVo;
import com.yi.core.basic.domain.vo.BasicRuleVo;


import com.yi.core.basic.service.IBasicRuleService;
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
 * 基础规则
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("基础规则控制层")
@RestController
@RequestMapping(value = "/basicRule")
public class BasicRuleController {

    private final Logger LOG = LoggerFactory.getLogger(BasicRuleController.class);


    @Resource
    private IBasicRuleService basicRuleService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取基础规则列表", notes = "根据分页参数查询基础规则列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<BasicRuleListVo> queryBasicRule(@RequestBody Pagination<BasicRule> query) {
        Page<BasicRuleListVo> page = basicRuleService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看基础规则信息", notes = "根据基础规则Id获取基础规则信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "基础规则Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewBasicRule(@RequestParam("id") int basicRuleId) {
        try {
            BasicRuleVo basicRule = basicRuleService.getBasicRuleVoById(basicRuleId);
            return RestUtils.successWhenNotNull(basicRule);
        } catch (BusinessException ex) {
            LOG.error("get BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("get BasicRule failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增基础规则", notes = "添加基础规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "basicRule", value = "基础规则对象", required = true, dataType = "BasicRuleBo")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addBasicRule(@RequestBody BasicRuleBo basicRule) {
        try {
            BasicRuleVo dbBasicRule = basicRuleService.addBasicRule(basicRule);
            return RestUtils.successWhenNotNull(dbBasicRule);
        } catch (BusinessException ex) {
            LOG.error("add BasicRule failure : basicRule", basicRule, ex);
            return RestUtils.error("add BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新基础规则", notes = "修改基础规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "basicRule", value = "基础规则对象", required = true, dataType = "BasicRuleBo")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateBasicRule(@RequestBody BasicRuleBo basicRule) {
        try {
            BasicRuleVo dbBasicRule = basicRuleService.updateBasicRule(basicRule);
            return RestUtils.successWhenNotNull(dbBasicRule);
        } catch (BusinessException ex) {
            LOG.error("update BasicRule failure : basicRule", basicRule, ex);
            return RestUtils.error("update BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除基础规则", notes = "删除基础规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "基础规则Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeBasicRuleById(@RequestParam("id") int basicRuleId) {
        try {
            basicRuleService.removeBasicRuleById(basicRuleId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("remove BasicRule failure : " + ex.getMessage());
        }
    }

    /**
     * 查看初始类容
     **/
    @ApiOperation(value = "查看初始类容", notes = "查看初始类容")
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    public RestResult getAll() {
        try {

            return RestUtils.success( basicRuleService.getAll());
        } catch (Exception ex) {
            LOG.error("remove BasicRule failure : id=basicRuleId", ex);
            return RestUtils.error("remove BasicRule failure : " + ex.getMessage());
        }
    }
}