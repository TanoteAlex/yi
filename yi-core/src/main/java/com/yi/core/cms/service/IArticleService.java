/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.ArticleBo;
import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.cms.domain.vo.ArticleVo;
import com.yihz.common.persistence.Pagination;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IArticleService {

	/**
	 * 分页查询: Article
	 * 
	 * @param query
	 * @return
	 */
	Page<Article> query(Pagination<Article> query);

	/**
	 * 分页查询: ArticleListVo
	 *
	 * @param query
	 * @return
	 */
	Page<ArticleListVo> queryListVo(Pagination<Article> query);

	/**
	 * 分页查询: ArticleListVo
	 * 
	 * @param queryApp
	 * @return
	 */
	Page<ArticleListVo> queryListVoForApp(Pagination<Article> query);

	/**
	 * 根据ID得到Article
	 * 
	 * @param articleId
	 * @return
	 */
	Article getArticleById(int articleId);

	/**
	 * 根据ID得到ArticleVo
	 * 
	 * @param articleId
	 * @return
	 */
	ArticleVo getArticleVoByIdForApp(int articleId);

	/**
	 * 根据ID得到ArticleVo
	 * 
	 * @param articleId
	 * @return
	 */
	ArticleVo getArticleVoById(int articleId);

	/**
	 * 根据ID得到ArticleListVo
	 * 
	 * @param articleId
	 * @return
	 */
	ArticleListVo getArticleListVoById(int articleId);

	/**
	 * 根据Entity创建Article
	 * 
	 * @param article
	 * @return
	 */
	Article addArticle(Article article);

	/**
	 * 根据BO创建Article
	 * 
	 * @param articleBo
	 * @return
	 */
	ArticleVo addArticle(ArticleBo articleBo);

	/**
	 * 根据Entity更新Article
	 * 
	 * @param article
	 * @return
	 */
	Article updateArticle(Article article);

	/**
	 * 根据BO更新Article
	 * 
	 * @param articleBo
	 * @return
	 */
	ArticleVo updateArticle(ArticleBo articleBo);

	/**
	 * 删除Article
	 * 
	 * @param articleId
	 */
	void removeArticleById(int articleId);

	/**
	 * 更新阅读量
	 * 
	 * @param articleId
	 * @param amount
	 */
	void updateReadQuantity(int articleId, int amount);

	/**
	 * 更新评论量
	 * 
	 * @param articleId
	 * @param amount
	 */
	void updateCommentQuantity(int articleId, int amount);

	/**
	 * 更新文章显示状态
	 * 
	 * @param articleId
	 * @return
	 */
	void updateDisplayState(int articleId);

}
