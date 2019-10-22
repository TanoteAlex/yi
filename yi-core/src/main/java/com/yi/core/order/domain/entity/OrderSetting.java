/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 订单设置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class OrderSetting implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 订单设置ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 订单设置类型
	 */
	@NotNull
	@Max(127)
	private Integer setType;
	/**
	 * 超时时间
	 */
	@Max(9999999999L)
	private Integer timeout;

	/**
	 * 时间单位（1天，2小时，3分钟）
	 */
	@NotNull
	@Max(127)
	private Integer timeUnit;
	/**
	 * 天
	 */
	@Deprecated
	@Max(9999999999L)
	private Integer day;
	/**
	 * 小时
	 */
	@Deprecated
	@Max(9999999999L)
	private Integer hour;
	/**
	 * 分钟
	 */
	@Deprecated
	@Max(9999999999L)
	private Integer minute;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	public OrderSetting() {
	}

	public OrderSetting(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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
	public Integer getSetType() {
		return this.setType;
	}

	public void setSetType(Integer setType) {
		this.setType = setType;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getDay() {
		return this.day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	@Column(unique = false, nullable = true, length = 10)
	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

}