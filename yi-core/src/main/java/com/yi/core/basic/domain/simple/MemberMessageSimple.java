/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.domain.simple;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员消息
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class MemberMessageSimple extends SimpleDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员消息ID
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
	private MemberSimple member;
	/**
	 * 消息标题
	 */
	@NotBlank
	@Length(max = 127)
	private String title;
	/**
	 * 消息内容
	 */
	@NotBlank
	@Length(max = 65535)
	private String content;
	/**
	 * 消息类型（1下单，2支付，3发货，）
	 */
	@Max(127)
	private Integer messageType;
	/**
	 * 排序
	 */
	@Max(127)
	private Integer sort;
	/**
	 * 显示（0显示1不显示）
	 */
	@Max(127)
	private Integer state;
	/**
	 * 阅读状态（0未读1已读）
	 */
	@Max(127)
	private Integer readState;
	/**
	 * 阅读时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date readTime;
	/**
	 * 发送时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;

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

	public MemberSimple getMember() {
		return member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
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

	// columns END

}