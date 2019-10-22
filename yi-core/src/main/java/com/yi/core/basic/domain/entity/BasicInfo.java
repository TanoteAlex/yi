package com.yi.core.basic.domain.entity;/*
										* Powered By [yihz-framework]
										* Web Site: yihz
										* Since 2018 - 2018
										*/

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class BasicInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 基础信息ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 公司名称
	 */
	@Length(max = 255)
	private String logoUrl;
	/**
	 * 公司名称
	 */
	@Length(max = 127)
	private String companyName;
	/**
	 * 公司地址
	 */
	@Length(max = 255)
	private String companyAddr;
	/**
	 * 公司电话
	 */
	@Length(max = 16)
	private String companyTel;
	/**
	 * 手机号码
	 */
	@Length(max = 16)
	private String companyMobile;
	/**
	 * 公司邮箱
	 */
	@Length(max = 32)
	private String companyMail;
	/**
	 * 成立时间
	 */

	private Date setupTime;
	/**
	 * 公司简介
	 */
	@Length(max = 65535)
	private String contentProfile;
	/**
	 * 创建时间
	 */

	private Date createTime;
	// columns END

	public BasicInfo() {
	}

	public BasicInfo(int id) {
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

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getLogoUrl() {
		return this.logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getCompanyAddr() {
		return this.companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getCompanyMobile() {
		return this.companyMobile;
	}

	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getCompanyMail() {
		return this.companyMail;
	}

	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getSetupTime() {
		return this.setupTime;
	}

	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public String getContentProfile() {
		return this.contentProfile;
	}

	public void setContentProfile(String contentProfile) {
		this.contentProfile = contentProfile;
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

}