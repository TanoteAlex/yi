/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.entity;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 任务配置表
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
public class TaskConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 任务配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * 任务名称
	 */
	@Length(max = 127)
	private String name;
	/**
	 * 类
	 */
	@Length(max = 255)
	private String clazz;
	/**
	 * 方法
	 */
	@Length(max = 127)
	private String method;
	/**
	 * 表达式
	 */
	@Length(max = 255)
	private String cron;
	/**
	 * 运行站点
	 */
	@Length(max = 127)
	private String site;
	/**
	 * 是否激活（0激活1未激活）
	 */
	private Boolean active;
	/**
	 * 最后执行时间
	 */

	private Date lastExecuteTime;
	/**
	 * 说明
	 */
	@Length(max = 255)
	private String description;
	// columns END

	public TaskConfig() {
	}

	public TaskConfig(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 127)
	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getLastExecuteTime() {
		return this.lastExecuteTime;
	}

	public void setLastExecuteTime(Date lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}