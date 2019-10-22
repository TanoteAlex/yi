/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.member.domain.bo.MemberBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 消息
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class MessageBo extends BoDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 消息ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 消息标题
	 */

	private String title;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 消息类型（0系统1..）
	 */
	private Integer messageType;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 显示（0显示1不显示）
	 */

	private Integer state;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 推送范围
	 */
	private Integer pushScope;

	/**
	 * 推送会员
	 */
	private List<MemberBo> members;

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getPushScope() {
		return pushScope;
	}

	public void setPushScope(Integer pushScope) {
		this.pushScope = pushScope;
	}

	public List<MemberBo> getMembers() {
		return members;
	}

	public void setMembers(List<MemberBo> members) {
		this.members = members;
	}

}