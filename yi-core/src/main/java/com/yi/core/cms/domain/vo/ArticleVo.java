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

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.cms.domain.simple.ArticleCommentSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ArticleVo extends VoDomain implements java.io.Serializable {

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
	 * 文章标题
	 */
	@NotBlank
	@Length(max = 127)
	private String title;
	/**
	 * 文章副标题
	 */
	@NotBlank
	@Length(max = 127)
	private String subtitle;
	/**
	 * 作者
	 */
	@NotBlank
	@Length(max = 127)
	private String author;
	/**
	 * 文章内容
	 */
	@Length(max = 65535)
	private String content;
	/**
	 * 商品图片
	 */
	@NotBlank
	@Length(max = 255)
	private String imgPath;
	/**
	 * 商品链接
	 */
	@NotBlank
	@Length(max = 255)
	private String url;
	/**
	 * 显示（0显示1不显示）
	 */
	@NotNull
	private Integer state;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 收藏
	 */
	private int collectionQuantity;
	/**
	 * 阅读
	 */
	private int readQuantity;
	/**
	 * 评论
	 */
	private int commentQuantity;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END
	/**
	 * 文章评论集合
	 */
	private List<ArticleCommentSimple> articleComments;
	/**
	 * 商品集合
	 */
	private List<CommoditySimple> commodities;

	/**
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public List<ArticleCommentSimple> getArticleComments() {
		return articleComments;
	}

	public void setArticleComments(List<ArticleCommentSimple> articleComments) {
		this.articleComments = articleComments;
	}

	public List<CommoditySimple> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommoditySimple> commodities) {
		this.commodities = commodities;
	}

	public int getCollectionQuantity() {
		return collectionQuantity;
	}

	public void setCollectionQuantity(int collectionQuantity) {
		this.collectionQuantity = collectionQuantity;
	}

	public int getReadQuantity() {
		return readQuantity;
	}

	public void setReadQuantity(int readQuantity) {
		this.readQuantity = readQuantity;
	}

	public int getCommentQuantity() {
		return commentQuantity;
	}

	public void setCommentQuantity(int commentQuantity) {
		this.commentQuantity = commentQuantity;
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