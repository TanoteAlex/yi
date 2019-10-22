/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 专区栏目
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class AreaColumn implements java.io.Serializable {

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
	private SalesArea salesArea;
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

	public AreaColumn() {
	}

	public AreaColumn(java.lang.Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getShowMode() {
		return this.showMode;
	}

	public void setShowMode(Integer showMode) {
		this.showMode = showMode;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	private List<AreaColumnCommodity> areaColumnCommodities = new ArrayList<>(0);

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "areaColumn")
	@OrderBy("sort asc")
	public List<AreaColumnCommodity> getAreaColumnCommodities() {
		return areaColumnCommodities;
	}

	public void setAreaColumnCommodities(List<AreaColumnCommodity> areaColumnCommodities) {
		this.areaColumnCommodities = areaColumnCommodities;
	}

	public void setSalesArea(SalesArea salesArea) {
		this.salesArea = salesArea;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALES_AREA_ID", nullable = false, updatable = false) })
	public SalesArea getSalesArea() {
		return salesArea;
	}

}