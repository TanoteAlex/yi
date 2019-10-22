/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.ArticleCommentBo;
import com.yi.core.cms.domain.entity.ArticleComment;
import com.yi.core.cms.domain.vo.ArticleCommentListVo;
import com.yi.core.cms.domain.vo.ArticleCommentVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IArticleCommentService {

	/**
	 * 根据ID得到ArticleComment
	 * 
	 * @param articleCommentId
	 * @return
	 */
	ArticleComment getArticleCommentById(int articleCommentId);

	/**
	 * 根据ID得到ArticleCommentVo
	 * 
	 * @param articleCommentId
	 * @return
	 */
	ArticleCommentVo getArticleCommentVoById(int articleCommentId);

	/**
	 * 根据ID得到ArticleCommentListVo
	 * 
	 * @param articleCommentId
	 * @return
	 */
	ArticleCommentListVo getArticleCommentListVoById(int articleCommentId);

	/**
	 * 根据Entity创建ArticleComment
	 * 
	 * @param articleComment
	 * @return
	 */
	ArticleComment addArticleComment(ArticleComment articleComment);

	/**
	 * 根据BO创建ArticleComment
	 * 
	 * @param articleCommentBo
	 * @return
	 */
	ArticleCommentListVo addArticleComment(ArticleCommentBo articleCommentBo);

	/**
	 * 根据Entity更新ArticleComment
	 * 
	 * @param articleComment
	 * @return
	 */
	ArticleComment updateArticleComment(ArticleComment articleComment);

	/**
	 * 根据BO更新ArticleComment
	 * 
	 * @param articleCommentBo
	 * @return
	 */
	ArticleCommentVo updateArticleComment(ArticleCommentBo articleCommentBo);

	/**
	 * 删除ArticleComment
	 * 
	 * @param articleCommentId
	 */
	void removeArticleCommentById(int articleCommentId);

	/**
	 * 分页查询: ArticleComment
	 * 
	 * @param query
	 * @return
	 */
	Page<ArticleComment> query(Pagination<ArticleComment> query);

	/**
	 * 分页查询: ArticleCommentListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<ArticleCommentListVo> queryListVo(Pagination<ArticleComment> query);

	/**
	 * 爱生活评论
	 * @param id
	 * @param replys
	 * @return
	 */
	ArticleCommentVo reply(int id, String replys);

	/**
	 * 显示爱生活评论
	 * @param id
	 * @param state
	 * @return
	 */
	ArticleCommentVo display(int id, int state);

	/**
	 *隐藏爱生活评论
	 * @param id
	 * @param state
	 * @return
	 */
	ArticleCommentVo hide(int id, int state);
}
