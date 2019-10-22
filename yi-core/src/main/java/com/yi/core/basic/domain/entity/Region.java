/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.order.domain.entity.CustomFreightTemplate;
import com.yi.core.order.domain.entity.FreeFreightTemplate;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * TODO 后期只做中间表使用 地区
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
public class Region implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 地区ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 地区组
	 */
	private RegionGroup regionGroup;
	/**
	 * 基础地区（Area表ID）
	 */
	private Area area;
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

	/**
	 * 商品集合
	 */
	private Set<Commodity> commodities;

	/**
	 * 自定义运费模板
	 */
	private Set<CustomFreightTemplate> customFreightTemplates;

	/**
	 * 包邮运费模板
	 */
	private Set<FreeFreightTemplate> freeFreightTemplates;

	// columns END

	public Region() {
	}

	public Region(int id) {
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

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "REGION_GROUP_ID", nullable = false, updatable = false) })
	public RegionGroup getRegionGroup() {
		return regionGroup;
	}

	public void setRegionGroup(RegionGroup regionGroup) {
		this.regionGroup = regionGroup;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "AREA_ID", nullable = false, updatable = false) })
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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

	@Column(unique = false, nullable = true, length = 0)
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

	@ManyToMany(targetEntity = Commodity.class, cascade = CascadeType.MERGE)
	@JoinTable(name = "commodity_region", joinColumns = @JoinColumn(name = "REGION_ID"), inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

	@ManyToMany(targetEntity = CustomFreightTemplate.class, cascade = CascadeType.MERGE)
	@JoinTable(name = "custom_freight_template_region", joinColumns = @JoinColumn(name = "REGION_ID"), inverseJoinColumns = @JoinColumn(name = "CUSTOM_FREIGHT_TEMPLATE_ID"))
	public Set<CustomFreightTemplate> getCustomFreightTemplates() {
		return customFreightTemplates;
	}

	public void setCustomFreightTemplates(Set<CustomFreightTemplate> customFreightTemplates) {
		this.customFreightTemplates = customFreightTemplates;
	}

	@ManyToMany(targetEntity = FreeFreightTemplate.class, cascade = CascadeType.PERSIST)
	@JoinTable(name = "free_freight_template_region", joinColumns = @JoinColumn(name = "REGION_ID"), inverseJoinColumns = @JoinColumn(name = "FREE_FREIGHT_TEMPLATE_ID"))
	public Set<FreeFreightTemplate> getFreeFreightTemplates() {
		return freeFreightTemplates;
	}

	public void setFreeFreightTemplates(Set<FreeFreightTemplate> freeFreightTemplates) {
		this.freeFreightTemplates = freeFreightTemplates;
	}

}