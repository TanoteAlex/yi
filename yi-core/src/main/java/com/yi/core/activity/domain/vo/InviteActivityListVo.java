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
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class InviteActivityListVo extends ListVoDomain implements java.io.Serializable {

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

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
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

}