/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.domain.simple;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class RescSimple extends SimpleDomain implements java.io.Serializable {

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

	private RescSimple parent;
	/**
	 * 子类资源
	 */
	private List<RescSimple> children;
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;

	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 角色集合
	 */
	/*private Set<RoleSimple> roles;*/

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


	public RescSimple getParent() {
		return parent;
	}

	public void setParent(RescSimple parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public List<RescSimple> getChildren() {
		return children;
	}

	public void setChildren(List<RescSimple> children) {
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

   /* @JsonIgnore
    public Set<RoleSimple> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleSimple> roles) {
        this.roles = roles;
    }*/
}