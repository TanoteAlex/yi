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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * 积分任务
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class IntegralTask implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 任务ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 任务类型（1签到，2邀请好友，3评论）
	 */
	@NotNull
	@Max(127)
	private Integer taskType;
	/**
	 * 任务名称
	 */
	@NotBlank
	@Length(max = 32)
	private String taskName;
	/**
	 * 成长值
	 */
	@NotNull
	@Max(9999999999L)
	private Integer growthValue;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 创建时间
	 */

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

	private Date delTime;
	// columns END

	public IntegralTask() {
	}

	public IntegralTask(int id) {
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

	@Column(unique = false, nullable = false, length = 32)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(unique = false, nullable = false, length = 10)
	public Integer getGrowthValue() {
		return this.growthValue;
	}

	public void setGrowthValue(Integer growthValue) {
		this.growthValue = growthValue;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	// private Set<IntegralRecord> integralRecords;

	// @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
	// CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "integralTask",
	// orphanRemoval = true)
	// public Set<IntegralRecord> getIntegralRecords() {
	// return integralRecords;
	// }
	//
	// public void setIntegralRecords(Set<IntegralRecord> integralRecords) {
	// this.integralRecords = integralRecords;
	// }
}