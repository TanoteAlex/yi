/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 奖励
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class RewardListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 奖励表ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 奖励编码
	 */
	@Length(max = 16)
	private String code;
	/**
	 * 奖励名称
	 */
	@Length(max = 32)
	private String name;
	/**
	 * 奖励类型（1注册，3评论，2连续签到）
	 */
	@Max(127)
	private Integer rewardType;
	/**
	 * 连续签到天数
	 */
	@Max(127)
	private Integer signDays;
	/**
	 * 邀请人数
	 */
	@Max(127)
	private Integer inviteNum;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 提示语
	 */
	private String reminder;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRewardType() {
		return this.rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getSignDays() {
		return this.signDays;
	}

	public void setSignDays(Integer signDays) {
		this.signDays = signDays;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public Integer getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}
}