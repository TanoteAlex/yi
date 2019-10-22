/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 供应商
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SupplierListVo extends ListVoDomain implements java.io.Serializable {

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
	private Date delTime;
	// columns END

	// /**
	// * 供应商 商品集合
	// */
	// private Set<CommoditySimple> commodities;

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

	// public UserSimple getUser() {
	// return this.user;
	// }
	//
	// public void setUser(UserSimple user) {
	// this.user = user;
	// }

	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(Integer settlementCycle) {
		this.settlementCycle = settlementCycle;
	}

	// public Set<CommoditySimple> getCommodities() {
	// return commodities;
	// }
	//
	// public void setCommodities(Set<CommoditySimple> commodities) {
	// this.commodities = commodities;
	// }

}