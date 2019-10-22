/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Article implements java.io.Serializable {

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
	@Length(max = 127)
	private String subtitle;
	/**
	 * 作者
	 */
	@Length(max = 127)
	private String author;
	/**
	 * 文章内容
	 */
	private String content;
	/**
	 * 商品图片
	 */
	@Length(max = 255)
	private String imgPath;
	/**
	 * 商品链接
	 */
	@Length(max = 255)
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
	 * 文章类型（1专题2主题）
	 */
	@Deprecated
	private Integer articleType;
	/**
	 * 创建时间
	 */
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
	 * 文章评论集合
	 */
	private List<ArticleComment> articleComments;
	/**
	 * 商品集合
	 */
	private List<Commodity> commodities;

	public Article() {
	}

	public Article(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 127)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(unique = false, nullable = false, length = 127)
	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(unique = false, nullable = true)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getArticleType() {
		return this.articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@Column(unique = false, nullable = true)
	public int getCollectionQuantity() {
		return collectionQuantity;
	}

	public void setCollectionQuantity(int collectionQuantity) {
		this.collectionQuantity = collectionQuantity;
	}

	@Column(unique = false, nullable = true)
	public int getReadQuantity() {
		return readQuantity;
	}

	public void setReadQuantity(int readQuantity) {
		this.readQuantity = readQuantity;
	}

	@Column(unique = false, nullable = true)
	public int getCommentQuantity() {
		return commentQuantity;
	}

	public void setCommentQuantity(int commentQuantity) {
		this.commentQuantity = commentQuantity;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "article")
	public List<ArticleComment> getArticleComments() {
		return articleComments;
	}

	public void setArticleComments(List<ArticleComment> articleComments) {
		this.articleComments = articleComments;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "article_commodity", joinColumns = @JoinColumn(name = "ARTICLE_ID"), inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
	@Where(clause = "deleted=0")
	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

}