/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.cms.controller;

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

import com.yi.core.cms.domain.bo.ArticleBo;
import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.cms.domain.vo.ArticleVo;
import com.yi.core.cms.service.IArticleService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("爱生活控制层")
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

	private final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@Resource
	private IArticleService articleService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取爱生活列表", notes = "根据分页参数查询爱生活列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ArticleListVo> queryArticle(@RequestBody Pagination<Article> query) {
		Page<ArticleListVo> page = articleService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看爱生活信息", notes = "根据爱生活Id获取爱生活信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "爱生活Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewArticle(@RequestParam("id") int articleId) {
		try {
			ArticleVo article = articleService.getArticleVoById(articleId);
			return RestUtils.successWhenNotNull(article);
		} catch (BusinessException ex) {
			LOG.error("get Article failure : id={}", articleId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增爱生活", notes = "添加爱生活")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "article", value = "爱生活对象", required = true, dataType = "ArticleBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addArticle(@RequestBody ArticleBo article) {
		try {
			ArticleVo dbArticle = articleService.addArticle(article);
			return RestUtils.successWhenNotNull(dbArticle);
		} catch (BusinessException ex) {
			LOG.error("add Article failure : {}", article, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新爱生活", notes = "修改爱生活")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "article", value = "爱生活对象", required = true, dataType = "ArticleBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateArticle(@RequestBody ArticleBo article) {
		try {
			ArticleVo dbArticle = articleService.updateArticle(article);
			return RestUtils.successWhenNotNull(dbArticle);
		} catch (BusinessException ex) {
			LOG.error("update Article failure : {}", article, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除爱生活", notes = "删除爱生活")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "爱生活Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeArticleById(@RequestParam("id") int articleId) {
		try {
			articleService.removeArticleById(articleId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Article failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 更新文章显示状态
	 * 
	 * @param articleId
	 * @return
	 */
	@ApiOperation(value = "更新文章显示状态", notes = "更新文章显示状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "articleId", value = "爱生活Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "updateDisplayState", method = RequestMethod.GET)
	public RestResult updateDisplayState(@RequestParam("articleId") int articleId) {
		try {
			articleService.updateDisplayState(articleId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("update Display State failure : id={}", articleId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}