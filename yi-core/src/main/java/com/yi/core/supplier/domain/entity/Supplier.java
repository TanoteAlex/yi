/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 供应商
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Supplier implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 供应商ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 供应商编号
	 */
	@Length(max = 32)
	private String supplierNo;
	/**
	 * 供应商名称
	 */
	@NotBlank
	@Length(max = 32)
	private String supplierName;
	/**
	 * 手机号码
	 */
	@NotBlank
	@Length(max = 16)
	private String phone;
	/**
	 * 密码
	 */
	@NotBlank
	@Length(max = 64)
	private String password;
	/**
	 * 状态（0可用，1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 结算周期（单位天）
	 */
	private Integer settlementCycle;
	/**
	 * 联系人
	 */
	@Length(max = 16)
	private String contact;
	/**
	 * 联系人电话
	 */
	@Length(max = 16)
	private String contactPhone;
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
	 * 详细地址
	 */
	@Length(max = 64)
	private String address;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
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
	// columns END

	public Supplier() {
	}

	public Supplier(int id) {
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

	@Column(unique = false, nullable = true, length = 32)
	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(unique = false, nullable = false, length = 16)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(unique = false, nullable = false, length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(unique = false, nullable = true, length = 8)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(unique = false, nullable = true, length = 8)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(unique = false, nullable = true, length = 8)
	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}