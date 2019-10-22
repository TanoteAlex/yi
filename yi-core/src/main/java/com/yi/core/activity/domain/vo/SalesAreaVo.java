/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.activity.domain.simple.AreaColumnSimple;
import com.yi.core.activity.domain.simple.SalesAreaCommoditySimple;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class SalesAreaVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 销售专区ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 标题
	 */
	@Length(max = 32)
	private String title;
	/**
	 * banner图
	 */
	@Length(max = 255)
	private String banner;
	/**
	 * 排列方式（1一排展一个，2一排展两个）
	 */

	private Integer showMode;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
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

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;

	private List<AreaColumnSimple> areaColumns = new ArrayList<>(0);

	private List<SalesAreaCommoditySimple> salesAreaCommodities = new ArrayList<>(0);
	// columns END

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public Integer getShowMode() {
		return this.showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
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

	public List<AreaColumnSimple> getAreaColumns() {
		return areaColumns;
	}

	public void setAreaColumns(List<AreaColumnSimple> areaColumns) {
		this.areaColumns = areaColumns;
	}

	public List<SalesAreaCommoditySimple> getSalesAreaCommodities() {
		return salesAreaCommodities;
	}

	public void setSalesAreaCommodities(List<SalesAreaCommoditySimple> salesAreaCommodities) {
		this.salesAreaCommodities = salesAreaCommodities;
	}
}