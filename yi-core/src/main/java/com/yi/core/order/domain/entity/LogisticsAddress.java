/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */


package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class LogisticsAddress implements Serializable {

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
    private Supplier supplier;
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

    private Date createTime;
    /**
     * 删除（0、否1、是）
     */
    @Max(127)
    private int deleted;
    /**
     * 删除时间
     */

    private Date delTime;
    //columns END


    public LogisticsAddress() {
    }

    public LogisticsAddress(
            int id
    ) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
    public int getAddressType() {
        return this.addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 8)
    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 8)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 8)
    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 3)
    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonTimestampSerializer.class)
    @Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
    public Date getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "SUPPLIER_ID", nullable = false)})
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}