/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;

import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.VoDomain;

/**
 * 商品评价
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ProductCommentVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 评论ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 64)
	private String guid;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private ProductSimple product;
	/**
	 * 商品名称
	 */
	@Length(max = 64)
	private String productName;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberSimple member;
	/**
	 * 会员昵称
	 */
	@Length(max = 32)
	private String nickname;
	/**
	 * 编号
	 */
	@Length(max = 16)
	private String serialNo;
	/**
	 * 评价星级
	 */
	private Integer reviewStar;
	/**
	 * 评价内容
	 */
	@Length(max = 256)
	private String reviewContent;
	/**
	 * 回复内容
	 */
	@Length(max = 256)
	private String replyContent;
	/**
	 * 是否显示（0不显示1显示）
	 */
	private Integer display;
	/**
	 * 评价时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date reviewTime;
	/**
	 * 回复时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date replyTime;
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

	public ProductSimple getProduct() {
		return product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getReviewStar() {
		return this.reviewStar;
	}

	public void setReviewStar(Integer reviewStar) {
		this.reviewStar = reviewStar;
	}

	public String getReviewContent() {
		return this.reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getReplyContent() {
		return this.replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Date getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public Date getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
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

}