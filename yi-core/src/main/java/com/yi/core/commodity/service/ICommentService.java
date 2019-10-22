/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.CommentBo;
import com.yi.core.commodity.domain.entity.Comment;
import com.yi.core.commodity.domain.vo.CommentListVo;
import com.yi.core.commodity.domain.vo.CommentVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 商品评论
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICommentService {

	/**
	 * 根据ID得到Comment
	 * 
	 * @param commentId
	 * @return
	 */
	Comment getCommentById(int commentId);

	/**
	 * 根据ID得到CommentVo
	 * 
	 * @param commentId
	 * @return
	 */
	CommentVo getCommentVoById(int commentId);

	/**
	 * 根据ID得到CommentListVo
	 * 
	 * @param commentId
	 * @return
	 */
	CommentListVo getCommentListVoById(int commentId);

	/**
	 * 根据Entity创建Comment
	 * 
	 * @param comment
	 * @return
	 */
	Comment addComment(Comment comment);

	/**
	 * 根据BO创建Comment
	 * 
	 * @param commentBo
	 * @return
	 */
	CommentVo addComment(CommentBo commentBo);

	/**
	 * 根据Entity更新Comment
	 * 
	 * @param comment
	 * @return
	 */
	Comment updateComment(Comment comment);

	/**
	 * 根据BO更新Comment
	 * 
	 * @param commentBo
	 * @return
	 */
	CommentVo updateComment(CommentBo commentBo);

	/**
	 * 删除Comment
	 * 
	 * @param commentId
	 */
	void removeCommentById(int commentId);

	/**
	 * 分页查询: Comment
	 * 
	 * @param query
	 * @return
	 */
	Page<Comment> query(Pagination<Comment> query);

	/**
	 * 分页查询: CommentListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CommentListVo> queryListVo(Pagination<Comment> query);

	/**
	 * 分页查询: CommentListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CommentListVo> queryListVoForApp(Pagination<Comment> query);

	/**
	 * 获取商品的评论
	 * 
	 * @param commodityId
	 * @return
	 */
	List<CommentListVo> getCommentsByCommodityId(int commodityId);

	/**
	 * 评论订单
	 * 
	 * @param
	 * @return
	 */
	List<Object> commentOrder(List<CommentBo> commodityComments);

	/**
	 * 回复评论
	 * 
	 * @param commentId
	 * @param replyContent
	 */
	void replyComment(int commentId, String replyContent);

	/**
	 * /** 显示评论
	 * 
	 * @param id
	 * @param display
	 * @return
	 */
	CommentVo display(int id, int display);

	/**
	 * 隐藏评论
	 * 
	 * @param id
	 * @param display
	 * @return
	 */
	CommentVo hide(int id, int display);

	/**
	 * 超时自动评价订单
	 * 
	 * @param needCommentOrders
	 */
	void autoCommentByOrders(Set<SaleOrder> needCommentOrders);
}
