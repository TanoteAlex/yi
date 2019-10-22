/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 * 团购成员
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class GroupBuyActivityMember implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	* 
	*/
	@Max(9999999999L)
	private int id;
	/**
	* 
	*/
	@Length(max = 32)
	private String guid;
	/**
	 * 活动编号
	 */
	@NotNull
	private GroupBuyActivity groupBuyActivity;

	/**
	 * 会员类型:全部会员,等级会员
	 */
	@NotNull
	@Max(127)
	private int memberType;
	/**
	 * 会员级别:普通会员,Vip会员...
	 */
	@Length(max = 32)
	private String memberLevel;
	// columns END

	public GroupBuyActivityMember() {
	}

	public GroupBuyActivityMember(int id) {
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

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 3)
	public int getMemberType() {
		return this.memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getMemberLevel() {
		return this.memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public void setGroupBuyActivity(GroupBuyActivity groupBuyActivity) {
		this.groupBuyActivity = groupBuyActivity;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GROUP_BUY_ACTIVITY_ID", nullable = false) })
	public GroupBuyActivity getGroupBuyActivity() {
		return groupBuyActivity;
	}

}