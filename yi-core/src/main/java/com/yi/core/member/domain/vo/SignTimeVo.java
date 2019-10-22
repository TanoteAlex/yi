/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.vo.RewardVo;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 签到时间
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class SignTimeVo extends VoDomain implements java.io.Serializable {

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
	private MemberSimple member;
	/**
	 * 签到时间
	 */
	@NotNull
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date signTime;
	/**
	 * 持续签到天数
	 */
	@NotNull
	@Max(99999999999L)
	private int signDays;

	/**
	 * 判断是否签到
	 * 
	 * @return
	 */
	private boolean isSigned;

	/**
	 * 奖励集合
	 */
	private List<RewardVo> rewards = new ArrayList<>();

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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public List<RewardVo> getRewards() {
		return rewards;
	}

	public void setRewards(List<RewardVo> rewards) {
		this.rewards = rewards;
	}

	public boolean isSigned() {
		return isSigned;
	}

	public void setSigned(boolean signed) {
		isSigned = signed;
	}
}