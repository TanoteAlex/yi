/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.order.domain.vo;

import java.io.*;
import java.net.*;
import java.util.*;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import static javax.persistence.GenerationType.IDENTITY;

import com.yihz.common.convert.domain.VoDomain;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class LogisticsAddressVo extends VoDomain implements Serializable {

    private static final long serialVersionUID = 1L;


    //columns START
    /**
     * 物流地址ID
     */
    @Max(9999999999L)
    private int id;
    /**
     * GUID
     */
    @Length(max = 32)
    private String guid;
    /**
     * 供应商ID
     */
    @NotNull
    private SupplierVo supplier;
    /**
     * 地址类型(1、收货地址 2、发货地址)
     */
    @Max(127)
    private int addressType;
    /**
     * 联系人
     */
    @Length(max = 16)
    private String contact;
    /**
     * 联系电话
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
     * 默认状态（0、非默认 1、默认）
     */
    @Max(127)
    private int state;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date createTime;
    /**
     * 删除（0、否1、是）
     */
    @Max(127)
    private int deleted;
    /**
     * 删除时间
     */
    @JsonSerialize(using = JsonTimestampSerializer.class)
    private Date delTime;
    //columns END

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

    public SupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierVo supplier) {
        this.supplier = supplier;
    }

    public int getAddressType() {
        return this.addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
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

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }


}