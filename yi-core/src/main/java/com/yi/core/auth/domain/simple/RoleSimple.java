/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.domain.simple;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.yihz.common.convert.domain.SimpleDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class RoleSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 角色ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 角色名称
	 */
	@NotBlank
	@Length(max = 16)
	private String name;
	/**
	 * 角色描述
	 */
	@Length(max = 16)
	private String description;
	/**
	 * 状态（0启用1禁用）
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END
	/**
	 * 资源集合
	 */
	private List<RescSimple> rescs;
	/**
	 * 用户集合
	 */
	private Set<UserSimple> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public List<RescSimple> getRescs() {
		return rescs;
	}

	public void setRescs(List<RescSimple> rescs) {
		this.rescs = rescs;
	}

	public Set<UserSimple> getUsers() {
		return users;
	}

	public void setUsers(Set<UserSimple> users) {
		this.users = users;
	}
}