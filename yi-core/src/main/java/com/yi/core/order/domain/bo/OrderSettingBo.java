/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.bo;

import java.io.Serializable;
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
public class OrderSettingBo extends BoDomain implements Serializable {

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
	@NotNull
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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

	public Integer getSetType() {
		return this.setType;
	}

	public void setSetType(Integer setType) {
		this.setType = setType;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getDay() {
		return this.day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
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

	public Integer getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

}