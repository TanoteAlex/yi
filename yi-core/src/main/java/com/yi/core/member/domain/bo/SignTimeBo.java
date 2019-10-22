/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.BoDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SignTimeBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 签到时间ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 签到时间
	 */
	@NotNull
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date signTime;
	/**
	 * 持续签到天数
	 */
	@NotNull
	@Max(99999999999L)
	private int signDays;
	// columns END

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public int getSignDays() {
		return this.signDays;
	}

	public void setSignDays(int signDays) {
		this.signDays = signDays;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}
}