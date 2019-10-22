/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.domain.simple;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class RecommendSimple extends SimpleDomain implements java.io.Serializable {

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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	// @DateTimeFormat
	private Date delTime;

	/**
	 * 排序号
	 */
	private Integer sort;
	// columns END

	/**
	 * 推荐商品
	 */
	private List<RecommendCommoditySimple> recommendCommodities;

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
	
	public List<RecommendCommoditySimple> getRecommendCommodities() {
		return recommendCommodities;
	}

	public void setRecommendCommodities(List<RecommendCommoditySimple> recommendCommodities) {
		this.recommendCommodities = recommendCommodities;
	}

}