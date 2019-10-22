/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class IntegralRecordListVo extends ListVoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 积分记录ID
	 */
	@Max(9999999999L)
	private int id;
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
	 * 任务类型(同IntegralTask积分类型)
	 */
	@NotNull
	private Integer taskType;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 操作类型（0增加1减少）
	 */
	@NotNull
	@Max(127)
	private int operateType;
	/**
	 * 增/减积分数值
	 */
	@NotNull
	@Max(9999999999L)
	private int operateIntegral;
	/**
	 * 当前积分
	 */
	@Max(9999999999L)
	private int currentIntegral;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public int getOperateType() {
		return this.operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getOperateIntegral() {
		return this.operateIntegral;
	}

	public void setOperateIntegral(int operateIntegral) {
		this.operateIntegral = operateIntegral;
	}

	public int getCurrentIntegral() {
		return this.currentIntegral;
	}

	public void setCurrentIntegral(int currentIntegral) {
		this.currentIntegral = currentIntegral;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

}