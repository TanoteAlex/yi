/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

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

import com.yi.core.basic.domain.bo.QuestionTypeBo;
import com.yi.core.basic.domain.entity.QuestionType;
import com.yi.core.basic.domain.vo.QuestionTypeListVo;
import com.yi.core.basic.domain.vo.QuestionTypeVo;
import com.yi.core.basic.service.IQuestionTypeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 问题类型类型
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("问题类型控制层")
@RestController
@RequestMapping(value = "/questionType")
public class QuestionTypeController {

    private final Logger LOG = LoggerFactory.getLogger(QuestionTypeController.class);


    @Resource
    private IQuestionTypeService questionTypeService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取问题类型列表", notes = "根据分页参数查询问题类型列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<QuestionTypeListVo> queryQuestionType(@RequestBody Pagination<QuestionType> query) {
        Page<QuestionTypeListVo> page = questionTypeService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看问题类型信息", notes = "根据问题类型Id获取问题类型信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题类型Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewQuestionType(@RequestParam("id") int questionTypeId) {
        try {
            QuestionTypeVo questionType = questionTypeService.getQuestionTypeVoById(questionTypeId);
            return RestUtils.successWhenNotNull(questionType);
        } catch (BusinessException ex) {
            LOG.error("get QuestionType failure : id=questionTypeId", ex);
            return RestUtils.error("get QuestionType failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增问题类型", notes = "添加问题类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionType", value = "问题类型对象", required = true, dataType = "QuestionTypeBo")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addQuestionType(@RequestBody QuestionTypeBo questionType) {
        try {
            QuestionTypeVo dbQuestionType = questionTypeService.addQuestionType(questionType);
            return RestUtils.successWhenNotNull(dbQuestionType);
        } catch (BusinessException ex) {
            LOG.error("add QuestionType failure : questionType", questionType, ex);
            return RestUtils.error("add QuestionType failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新问题类型", notes = "修改问题类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionType", value = "问题类型对象", required = true, dataType = "QuestionTypeBo")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateQuestionType(@RequestBody QuestionTypeBo questionType) {
        try {
            QuestionTypeVo dbQuestionType = questionTypeService.updateQuestionType(questionType);
            return RestUtils.successWhenNotNull(dbQuestionType);
        } catch (BusinessException ex) {
            LOG.error("update QuestionType failure : questionType", questionType, ex);
            return RestUtils.error("update QuestionType failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除问题类型", notes = "删除问题类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题类型Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeQuestionTypeById(@RequestParam("id") int questionTypeId) {
        try {
            questionTypeService.removeQuestionTypeById(questionTypeId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove QuestionType failure : id=questionTypeId", ex);
            return RestUtils.error("remove QuestionType failure : " + ex.getMessage());
        }
    }

    /**
     * 启用对象
     **/
    @ApiOperation(value = "启用问题类型", notes = "启用问题类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题类型Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "enable", method = RequestMethod.GET)
    public RestResult enable(@RequestParam("id") int questionTypeId) {
        try {
            return RestUtils.success(questionTypeService.enable(questionTypeId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }



    /**
     * 禁用对象
     **/
    @ApiOperation(value = "禁用问题类型", notes = "禁用问题类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题类型Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "disable", method = RequestMethod.GET)
    public RestResult disable(@RequestParam("id") int questionTypeId) {
        try {
            return RestUtils.success(questionTypeService.disable(questionTypeId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }



}