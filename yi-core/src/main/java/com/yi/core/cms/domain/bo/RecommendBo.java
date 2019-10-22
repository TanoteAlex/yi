/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.bo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.cms.domain.simple.PositionSimple;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 推荐位
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class RecommendBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 推荐位ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 位置表（position表ID）
	 */
	@NotNull
	private PositionSimple position;
	/**
	 * 推荐位标题
	 */
	@NotBlank
	@Length(max = 127)
	private String title;
	/**
	 * 推荐类型（1今日推荐2楼层推荐）
	 */
	private Integer recommendType;
	/**
	 * 排列方式（2展示2个，3展示3个，4展示4个，5展示5个）
	 */
	private Integer showMode;
	/**
	 * 推荐位默认图片路径
	 */
	@NotBlank
	@Length(max = 255)
	private String imgPath;

	/**
	 * 状态（0启用1禁用）
	 */
	private Integer state;
	/**
	 * 链接类型（1商品2文章3活动4专区）
	 */
	private Integer linkType;
	/**
	 * 链接ID
	 */
	private Integer linkId;
	/**
	 * 是否显示banner图 0显示1不显示
	 */
	private Integer showBanner;
	/**
	 * 是否显示标题 0显示1不显示
	 */
	private Integer showTitle;

	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;

	/**
	 * 排序号
	 */
	private Integer sort;
	// columns END

	/**
	 * 推荐商品
	 */
	private List<RecommendCommodityBo> recommendCommodities;

	/**
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

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

	public PositionSimple getPosition() {
		return position;
	}

	public void setPosition(PositionSimple position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getShowMode() {
		return showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<RecommendCommodityBo> getRecommendCommodities() {
		return recommendCommodities;
	}

	public void setRecommendCommodities(List<RecommendCommodityBo> recommendCommodities) {
		this.recommendCommodities = recommendCommodities;
	}

	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<AttachmentVo> getAttachmentVos() {
		return attachmentVos;
	}

	public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
		this.attachmentVos = attachmentVos;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public Integer getShowBanner() {
		return showBanner;
	}

	public void setShowBanner(Integer showBanner) {
		this.showBanner = showBanner;
	}

	public Integer getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(Integer showTitle) {
		this.showTitle = showTitle;
	}

	// public List<CommodityBo> getCommodities() {
	// return commodities;
	// }
	//
	// public void setCommodities(List<CommodityBo> commodities) {
	// this.commodities = commodities;
	// }

}