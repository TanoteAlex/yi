/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class TaskConfigVo extends VoDomain implements java.io.Serializable {

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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date lastExecuteTime;
	/**
	 * 说明
	 */
	@Length(max = 255)
	private String description;
	// columns END

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getLastExecuteTime() {
		return this.lastExecuteTime;
	}

	public void setLastExecuteTime(Date lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}