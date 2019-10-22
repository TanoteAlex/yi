/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.simple;

import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class BasicInfoSimple extends SimpleDomain implements java.io.Serializable {

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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date setupTime;
	/**
	 * 公司简介
	 */
	@Length(max = 65535)
	private String contentProfile;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
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

	public String getLogoUrl() {
		return this.logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddr() {
		return this.companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyMobile() {
		return this.companyMobile;
	}

	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}

	public String getCompanyMail() {
		return this.companyMail;
	}

	public void setCompanyMail(String companyMail) {
		this.companyMail = companyMail;
	}

	public Date getSetupTime() {
		return this.setupTime;
	}

	public void setSetupTime(Date setupTime) {
		this.setupTime = setupTime;
	}

	public String getContentProfile() {
		return this.contentProfile;
	}

	public void setContentProfile(String contentProfile) {
		this.contentProfile = contentProfile;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}