/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.promotion.domain.simple.GroupBuyActivitySimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonDateSerializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class GroupBuyActivityTimeVo extends VoDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// columns START
	/**
	 *
	 */
	@Max(9999999999L)
	private int id;
	/**
	 *
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 团购ID
	 */
	@NotNull
	private GroupBuyActivitySimple groupBuyActivity;
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

	public GroupBuyActivitySimple getGroupBuyActivity() {
		return groupBuyActivity;
	}

	public void setGroupBuyActivity(GroupBuyActivitySimple groupBuyActivity) {
		this.groupBuyActivity = groupBuyActivity;
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

}