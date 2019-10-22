/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
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
public class IntegralRecord implements Serializable {

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
	private Member member;
	/**
	 * 奖品ID
	 */
	private Integer prizeId;
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
	private Date createTime;
	// columns END

	public IntegralRecord() {
	}

	public IntegralRecord(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(unique = false, nullable = false, length = 3)
	public int getOperateType() {
		return this.operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getOperateIntegral() {
		return this.operateIntegral;
	}

	public void setOperateIntegral(int operateIntegral) {
		this.operateIntegral = operateIntegral;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getCurrentIntegral() {
		return this.currentIntegral;
	}

	public void setCurrentIntegral(int currentIntegral) {
		this.currentIntegral = currentIntegral;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

}