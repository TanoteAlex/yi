/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yihz.common.json.serializer.JsonDateSerializer;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.InvitePrizeSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class InviteActivityVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 邀请有礼活动ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 标题
	 */
	@Length(max = 32)
	private String title;
	/**
	 * banner图
	 */
	@Length(max = 255)
	private String banner;
	/**
	 * 活动规则
	 */
	@Length(max = 65535)
	private String rule;
	/**
	 * 开始时间
	 */

	@JsonSerialize(using = JsonDateSerializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */

	@JsonSerialize(using = JsonDateSerializer.class)
	private Date endTime;
	/**
	 * 邀请成功标示（1注册，2购买）
	 */
	@NotNull
	private Integer inviteFlag;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	// columns END

	private List<InvitePrizeSimple> invitePrizes = new ArrayList<>();

	/**
	 * 邀请人数
	 */
	private long inviteNum;

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getRule() {
		return this.rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getInviteFlag() {
		return this.inviteFlag;
	}

	public void setInviteFlag(Integer inviteFlag) {
		this.inviteFlag = inviteFlag;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public List<InvitePrizeSimple> getInvitePrizes() {
		return invitePrizes;
	}

	public void setInvitePrizes(List<InvitePrizeSimple> invitePrizes) {
		this.invitePrizes = invitePrizes;
	}

	public long getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(long inviteNum) {
		this.inviteNum = inviteNum;
	}

}