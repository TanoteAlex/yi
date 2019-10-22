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

import com.yi.core.basic.domain.bo.QuestionBo;
import com.yi.core.basic.domain.entity.Question;
import com.yi.core.basic.domain.vo.QuestionListVo;
import com.yi.core.basic.domain.vo.QuestionVo;
import com.yi.core.basic.service.IQuestionService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 问题
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("问题控制层")
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private final Logger LOG = LoggerFactory.getLogger(QuestionController.class);


    @Resource
    private IQuestionService questionService;

    /**
     * 分页查询
     */
    @ApiOperation(value = "获取问题列表", notes = "根据分页参数查询问题列表")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Page<QuestionListVo> queryQuestion(@RequestBody Pagination<Question> query) {
        Page<QuestionListVo> page = questionService.queryListVo(query);
        return page;
    }

    /**
     * 查看对象
     **/
    @ApiOperation(value = "查看问题信息", notes = "根据问题Id获取问题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public RestResult viewQuestion(@RequestParam("id") int questionId) {
        try {
            QuestionVo question = questionService.getQuestionVoById(questionId);
            return RestUtils.successWhenNotNull(question);
        } catch (BusinessException ex) {
            LOG.error("get Question failure : id=questionId", ex);
            return RestUtils.error("get Question failure : " + ex.getMessage());
        }
    }


    /**
     * 保存新增对象
     **/
    @ApiOperation(value = "新增问题", notes = "添加问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "question", value = "问题对象", required = true, dataType = "QuestionBo")
    })
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public RestResult addQuestion(@RequestBody QuestionBo question) {
        try {
            QuestionVo dbQuestion = questionService.addQuestion(question);
            return RestUtils.successWhenNotNull(dbQuestion);
        } catch (BusinessException ex) {
            LOG.error("add Question failure : question", question, ex);
            return RestUtils.error("add Question failure : " + ex.getMessage());
        }
    }

    /**
     * 保存更新对象
     **/
    @ApiOperation(value = "更新问题", notes = "修改问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "question", value = "问题对象", required = true, dataType = "QuestionBo")
    })
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public RestResult updateQuestion(@RequestBody QuestionBo question) {
        try {
            QuestionVo dbQuestion = questionService.updateQuestion(question);
            return RestUtils.successWhenNotNull(dbQuestion);
        } catch (BusinessException ex) {
            LOG.error("update Question failure : question", question, ex);
            return RestUtils.error("update Question failure : " + ex.getMessage());
        }
    }

    /**
     * 删除对象
     **/
    @ApiOperation(value = "删除问题", notes = "删除问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "removeById", method = RequestMethod.GET)
    public RestResult removeQuestionById(@RequestParam("id") int questionId) {
        try {
            questionService.removeQuestionById(questionId);
            return RestUtils.success(true);
        } catch (Exception ex) {
            LOG.error("remove Question failure : id=questionId", ex);
            return RestUtils.error("remove Question failure : " + ex.getMessage());
        }
    }



    /**
     * 启用对象
     **/
    @ApiOperation(value = "启用问题", notes = "启用问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "enable", method = RequestMethod.GET)
    public RestResult enable(@RequestParam("id") int questionId) {
        try {
            return RestUtils.success(questionService.enable(questionId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }



    /**
     * 禁用对象
     **/
    @ApiOperation(value = "禁用问题", notes = "禁用问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "问题Id", required = true, dataType = "Int")
    })
    @RequestMapping(value = "disable", method = RequestMethod.GET)
    public RestResult disable(@RequestParam("id") int questionId) {
        try {
            return RestUtils.success( questionService.disable(questionId));
        } catch (Exception ex) {
            LOG.error("remove Message failure : id=messageId", ex);
            return RestUtils.error("remove Message failure : " + ex.getMessage());
        }
    }




}