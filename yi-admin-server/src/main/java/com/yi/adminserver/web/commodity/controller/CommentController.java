/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.bo.CommentBo;
import com.yi.core.commodity.domain.vo.CommentListVo;
import com.yi.core.commodity.domain.vo.CommentVo;
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

import com.yi.core.commodity.domain.entity.Comment;
import com.yi.core.commodity.service.ICommentService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品评论
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("评论控制层")
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

	private final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	@Resource
	private ICommentService commentService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取评论列表", notes = "根据分页参数查询评论列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommentListVo> queryComment(@RequestBody Pagination<Comment> query) {
		Page<CommentListVo> page = commentService.queryListVo(query);
		return page;
	}

	/**
	 * 显示评论
	 **/
	@ApiOperation(value = "显示评论", notes = "显示评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "评论Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "display", value = "状态", required = true, dataType = "Int")
	})
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public RestResult display(@RequestParam("id") int id, @RequestParam("display") int display) {
		try {
			return RestUtils.success(commentService.display(id, display));
		} catch (Exception ex) {
			LOG.error("display ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("display ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 隐藏评论
	 **/
	@ApiOperation(value = "隐藏评论", notes = "隐藏评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "评论Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "display", value = "状态", required = true, dataType = "Int")
	})
	@RequestMapping(value = "hide", method = RequestMethod.GET)
	public RestResult hide(@RequestParam("id") int id, @RequestParam("display") int display) {
		try {
			return RestUtils.success(commentService.hide(id, display));
		} catch (Exception ex) {
			LOG.error("hide ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("hide ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看评论信息", notes = "根据评论Id获取评论信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "评论Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewComment(@RequestParam("id") int commentId) {
		try {
			CommentVo comment = commentService.getCommentVoById(commentId);
			return RestUtils.successWhenNotNull(comment);
		} catch (BusinessException ex) {
			LOG.error("get Comment failure : id=commentId", ex);
			return RestUtils.error("get Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增评论", notes = "添加评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "comment", value = "评论对象", required = true, dataType = "CommentBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addComment(@RequestBody CommentBo comment) {
		try {
			CommentVo dbComment = commentService.addComment(comment);
			return RestUtils.successWhenNotNull(dbComment);
		} catch (BusinessException ex) {
			LOG.error("add Comment failure : comment", comment, ex);
			return RestUtils.error("add Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新评论", notes = "修改评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "comment", value = "评论对象", required = true, dataType = "Comment")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateComment(@RequestBody Comment comment) {
		try {
			Comment dbComment = commentService.updateComment(comment);
			return RestUtils.successWhenNotNull(dbComment);
		} catch (BusinessException ex) {
			LOG.error("update Comment failure : comment", comment, ex);
			return RestUtils.error("update Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除评论", notes = "删除评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "评论Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommentById(@RequestParam("id") int commentId) {
		try {
			commentService.removeCommentById(commentId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Comment failure : id=commentId", ex);
			return RestUtils.error("remove Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 回复
	 */
	@ApiOperation(value = "回复评论", notes = "回复评论")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "评论Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "replyContent", value = "回复内容", required = true, dataType = "String")
	})
	@RequestMapping(value = "reply", method = RequestMethod.GET)
	public RestResult reply(@RequestParam("id") int commentId, @RequestParam("replyContent") String replyContent) {
		try {
			commentService.replyComment(commentId, replyContent);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Comment failure : id=commentId", ex);
			return RestUtils.error("remove Comment failure : " + ex.getMessage());
		}
	}

}