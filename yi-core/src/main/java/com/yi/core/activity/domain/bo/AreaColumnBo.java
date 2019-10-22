/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.activity.domain.simple.AreaColumnCommoditySimple;
import com.yi.core.activity.domain.simple.SalesAreaSimple;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 专区栏目
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AreaColumnBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 专区栏目ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 销售专区（sales_area表ID）
	 */
	@NotNull
	private SalesAreaSimple salesArea;
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
	 * 排序
	 */

	private Integer sort;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 创建时间
	 */

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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	private List<AreaColumnCommoditySimple> areaColumnCommodities = new ArrayList<>();

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

	public SalesAreaSimple getSalesArea() {
		return this.salesArea;
	}

	public void setSalesArea(SalesAreaSimple salesArea) {
		this.salesArea = salesArea;
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

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public List<AreaColumnCommoditySimple> getAreaColumnCommodities() {
		return areaColumnCommodities;
	}

	public void setAreaColumnCommodities(List<AreaColumnCommoditySimple> areaColumnCommodities) {
		this.areaColumnCommodities = areaColumnCommodities;
	}

}