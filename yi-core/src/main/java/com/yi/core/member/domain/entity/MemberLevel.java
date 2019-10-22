/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员等级
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
public class MemberLevel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员等级ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 等级名称
	 */
	@NotBlank
	@Length(max = 32)
	private String name;
	/**
	 * 级别(如1,2,3,4...)
	 */
	@NotNull
	private Integer rank;
	/**
	 * 会员人数（冗余）
	 */
	@NotNull
	@Max(9999999999L)
	private int quantity;
	/**
	 * 成长值满足点
	 */
	@NotNull
	@Max(9999999999L)
	private int growthValue;
	/**
	 * 折扣（0.00-100.00）
	 */
	@NotNull
	@Max(99999L)
	private BigDecimal discount;
	/**
	 * 默认等级（0非默认1默认）
	 */
	@Deprecated
	private Integer initial;
	/**
	 * 默认等级（0非默认1默认）
	 */
	private Integer defaulted;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	private Set<Member> members = new HashSet<>(0);

//	/**
//	 * 等级折扣
//	 */
//	private List<CommodityLevelDiscount> commodityLevelDiscounts;

	public MemberLevel() {
	}

	public MemberLevel(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getQuantity() {
		if (members != null) {
			this.quantity = members.size();
		}
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		if (members != null && quantity < 1) {
			quantity = members.size();
		}
		this.quantity = quantity;
	}

	@Column(unique = false, nullable = false, length = 10)
	public int getGrowthValue() {
		return this.growthValue;
	}

	public void setGrowthValue(int growthValue) {
		this.growthValue = growthValue;
	}

	@Column(unique = false, nullable = false, length = 5)
	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getInitial() {
		return this.initial;
	}

	public void setInitial(Integer initial) {
		this.initial = initial;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "memberLevel")
	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> member) {
		this.members = member;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}

//	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//	@JoinTable(name = "commodity_level_discount", joinColumns = @JoinColumn(name = "MEMBER_LEVEL_ID"), inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
//	@JsonIgnore
//	public List<CommodityLevelDiscount> getCommodityLevelDiscounts() {
//		return commodityLevelDiscounts;
//	}
//
//	public void setCommodityLevelDiscounts(List<CommodityLevelDiscount> commodityLevelDiscounts) {
//		this.commodityLevelDiscounts = commodityLevelDiscounts;
//	}

}