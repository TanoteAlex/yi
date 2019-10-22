/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.simple;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ArticleSimple extends SimpleDomain implements java.io.Serializable {

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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
//	/**
//	 * 文章评论集合
//	 */
//	private List<ArticleCommentSimple> articleComments;
//	/**
//	 * 商品集合
//	 */
//	private List<CommoditySimple> commodities;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

//	public List<ArticleCommentSimple> getArticleComments() {
//		return articleComments;
//	}
//
//	public void setArticleComments(List<ArticleCommentSimple> articleComments) {
//		this.articleComments = articleComments;
//	}

//	public List<CommoditySimple> getCommodities() {
//		return commodities;
//	}
//
//	public void setCommodities(List<CommoditySimple> commodities) {
//		this.commodities = commodities;
//	}

}