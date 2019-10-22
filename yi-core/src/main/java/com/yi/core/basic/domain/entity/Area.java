/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 省市区 基础数据
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
public class Area implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 地区Id
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * 父类
	 */
	private Area parent;
	/**
	 * 子类
	 */
	private List<Area> children;
	/**
	 * 地区级别（1省份province 2市city 3区县district...）
	 */
	@Max(127)
	private Integer level;
	/**
	 * 地区编码
	 */
	@Length(max = 32)
	private String areaCode;
	/**
	 * 地区名
	 */
	@NotBlank
	@Length(max = 32)
	private String name;
	/**
	 * 简称
	 */
	@Length(max = 32)
	private String shortName;
	/**
	 * 拼音
	 */
	@Length(max = 32)
	private String pinYin;
	/**
	 * 简拼
	 */
	@Length(max = 16)
	private String jianPin;
	/**
	 * 城市编码
	 */
	@Length(max = 32)
	private String cityCode;
	/**
	 * 邮政编码
	 */
	@Length(max = 32)
	private String zipCode;
	/**
	 * 城市中心点（即：经纬度坐标，经度纬度以','分割）
	 */
	@Length(max = 32)
	private String center;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	// columns END

	public Area() {
	}

	public Area(int id) {
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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PARENT_ID") })
	public Area getParent() {
		return this.parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parent")
	@Fetch(FetchMode.SUBSELECT)
	public List<Area> getChildren() {
		return children;
	}

	public void setChildren(List<Area> children) {
		this.children = children;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getPinYin() {
		return this.pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getJianPin() {
		return this.jianPin;
	}

	public void setJianPin(String jianPin) {
		this.jianPin = jianPin;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getCenter() {
		return this.center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}