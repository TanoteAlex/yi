/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.bo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ArticleBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 文章ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 文章副标题
	 */
	private String subtitle;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 商品图片
	 */
	private String imgPath;
	/**
	 * 商品链接
	 */
	private String url;
	/**
	 * 显示（0显示1不显示）
	 */
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	/**
	 * 文章评论集合
	 */
	private List<ArticleCommentBo> articleComments;
	/**
	 * 商品集合
	 */
	private List<CommodityBo> commodities;

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

	public List<ArticleCommentBo> getArticleComments() {
		return articleComments;
	}

	public void setArticleComments(List<ArticleCommentBo> articleComments) {
		this.articleComments = articleComments;
	}

	public List<CommodityBo> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommodityBo> commodities) {
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