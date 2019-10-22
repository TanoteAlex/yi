/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.domain.bo;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 资源
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class RescBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 资源ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 父类资源
	 */
	private RescBo parent;
	/**
	 * 子类资源
	 */
	private Set<RescBo> children;
	/**
	 * 资源编码
	 */
	@Length(max = 16)
	private String code;
	/**
	 * 资源名称
	 */
	@NotBlank
	@Length(max = 32)
	private String name;
	/**
	 * 资源路径
	 */
	@NotBlank
	@Length(max = 64)
	private String url;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 角色集合
	 */
	private Set<RoleBo> roles;

	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;

	/**
	 * 图标
	 */
	private String icon;

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

	public RescBo getParent() {
		return parent;
	}

	public void setParent(RescBo parent) {
		this.parent = parent;
	}

	public Set<RescBo> getChildren() {
		return children;
	}

	public void setChildren(Set<RescBo> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<RoleBo> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleBo> roles) {
		this.roles = roles;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}