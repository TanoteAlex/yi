/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.bo;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 商品评论
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CommentBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 评论ID
	 */
	private Integer id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 商品（commodity表ID）
	 */
	private CommodityBo commodity;
	/**
	 * 订单（sale_order表ID）
	 */
	private SaleOrderSimple saleOrder;
	/**
	 * 商品名称
	 */
	@Deprecated
	private String commodityName;
	/**
	 * 会员（member表ID）
	 */
	private MemberBo member;
	/**
	 * 会员昵称
	 */
	@Deprecated
	private String nickname;
	/**
	 * 编号
	 */
	private String serialNo;
	/**
	 * 评价星级
	 */
	private Integer commentStar;
	/**
	 * 评价内容
	 */
	private String commentContent;
	/**
	 * 回复内容
	 */
	private String replyContent;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 是否显示（0不显示1显示）
	 */
	private Integer display;
	/**
	 * 评价时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date commentTime;
	/**
	 * 回复时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date replyTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;

	/**
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

	/**
	 * 商品评论图片
	 */
	@Length(max = 255)
	private String imgPath;

	// columns END

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public CommodityBo getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getCommentStar() {
		return commentStar;
	}

	public void setCommentStar(Integer commentStar) {
		this.commentStar = commentStar;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
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

	public List<AttachmentVo> getAttachmentVos() {
		return attachmentVos;
	}

	public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
		this.attachmentVos = attachmentVos;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public SaleOrderSimple getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderSimple saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}