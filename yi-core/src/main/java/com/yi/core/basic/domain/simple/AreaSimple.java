/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.simple;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.SimpleDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AreaSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 地区Id
	 */
	@Max(9999999999L)
	private int id;
	// /**
	// * 父节点ID
	// */
	//
	// private AreaSimple parent;
	// /**
	// * 父节点ID
	// */
	// private List<AreaSimple> children;
	/**
	 * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
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
	 * 城市中心点（即：经纬度坐标）
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

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// @JsonIgnore
	// public AreaSimple getParent() {
	// return this.parent;
	// }
	//
	// public void setParent(AreaSimple parent) {
	// this.parent = parent;
	// }

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getPinYin() {
		return this.pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getJianPin() {
		return this.jianPin;
	}

	public void setJianPin(String jianPin) {
		this.jianPin = jianPin;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCenter() {
		return this.center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	// public List<AreaSimple> getChildren() {
	// return children;
	// }
	//
	// public void setChildren(List<AreaSimple> children) {
	// this.children = children;
	// }

}