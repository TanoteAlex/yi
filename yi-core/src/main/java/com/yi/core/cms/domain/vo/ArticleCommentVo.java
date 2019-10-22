/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.yi.core.attachment.domain.vo.AttachmentVo;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.cms.domain.simple.ArticleSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 文章评论
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ArticleCommentVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 文章ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;

	/**
	 * 评论人
	 */
	@NotBlank
	@Length(max = 127)
	private String commentator;
	/**
	 * 评论内容
	 */
	@NotBlank
	@Length(max = 255)
	private String commentContent;
	/**
	 * 评论时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date commentTime;
	/**
	 * 回复内容
	 */
	@Length(max = 255)
	private String replyContent;
	/**
	 * 回复时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date replyTime;
	/**
	 * 排序
	 */
	@Max(127)
	private Integer sort;
	/**
	 * 显示（0不显示1显示）
	 */
	@NotNull
	private Integer state;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;

	/**
	 * 文章（article表ID）
	 */
	private ArticleSimple article;

	/**
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

	// columns END
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCommentator() {
		return commentator;
	}

	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public ArticleSimple getArticle() {
		return article;
	}

	public void setArticle(ArticleSimple article) {
		this.article = article;
	}

	public List<AttachmentVo> getAttachmentVos() {
		return attachmentVos;
	}

	public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
		this.attachmentVos = attachmentVos;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}