/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AddressVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 地址ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 业务ID
	 */
	@NotNull
	@Max(9999999999L)
	private Integer businessId;
	/**
	 * 业务类型（1收货地址，2发货地址，3物流地址）
	 */
	@NotNull
	@Max(9999999999L)
	private Integer businessType;
	/**
	 * 姓名
	 */
	@NotBlank
	@Length(max = 16)
	private String fullName;
	/**
	 * 手机号
	 */
	@NotBlank
	@Length(max = 16)
	private String phone;
	/**
	 * 省
	 */
	@Length(max = 8)
	private String province;
	/**
	 * 市
	 */
	@Length(max = 8)
	private String city;
	/**
	 * 区
	 */
	@Length(max = 8)
	private String district;
	/**
	 * 经度
	 */

	private Double longitude;
	/**
	 * 纬度
	 */

	private Double latitude;
	/**
	 * 邮政编码
	 */
	@Length(max = 16)
	private String zipCode;
	/**
	 * 详细地址
	 */
	@Length(max = 64)
	private String addr;
	/**
	 * 完整地址
	 */
	@Length(max = 64)
	private String fullAddr;
	/**
	 * 默认地址(0非默认1默认）
	 */
	@Max(127)
	private Integer defaulted;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	public Integer getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
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

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFullAddr() {
		return this.fullAddr;
	}

	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}

	public Integer getDefaulted() {
		return this.defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
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

}