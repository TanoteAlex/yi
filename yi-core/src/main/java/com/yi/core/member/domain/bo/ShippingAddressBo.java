/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class ShippingAddressBo extends BoDomain implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    // columns START
    /**
     * 收货地址ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * member表ID
     */
    private MemberBo member;
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
     * 详细地址
     */
    @Length(max = 32)
    private String address;
	/**
	 * 默认地址（0非默认1默认）
	 */
	private Integer defaulted;
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

    private Date delTime;
    // columns END

    /**
     * 地址邮编
     */
    @Length(max = 16)
    private String zipCode;

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

    public MemberBo getMember() {
        return member;
    }

    public void setMember(MemberBo member) {
        this.member = member;
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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}
}