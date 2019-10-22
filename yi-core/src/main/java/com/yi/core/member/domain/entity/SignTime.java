/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
 * 签到时间
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
public class SignTime implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 签到时间ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	/**
	 * 签到时间
	 */
	@NotNull
	private Date signTime;
	/**
	 * 持续签到天数
	 */
	@NotNull
	@Max(99999999999L)
	private int signDays;
	// columns END

	public SignTime() {
	}

	public SignTime(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public Date getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 11)
	public int getSignDays() {
		return this.signDays;
	}

	public void setSignDays(int signDays) {
		this.signDays = signDays;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}