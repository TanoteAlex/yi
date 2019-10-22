/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员资产账户
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
public class Account implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 账户ID
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
	private Member member;
	/**
	 * 成交单数
	 */
	@Deprecated
	private int orderQuantity;
	/**
	 * 消费金额
	 */
	@Deprecated
	private BigDecimal consumeAmount;
	/**
	 * 账户金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 使用金额
	 */
	@Deprecated
	private BigDecimal useAmount;
	/**
	 * 账户余额
	 */
	@Deprecated
	private BigDecimal balance;
	/**
	 * 冻结金额
	 */
	@Deprecated
	private BigDecimal freezeAmount;
	/**
	 * 总积分
	 */
	private int totalIntegral;

	/**
	 * 可用积分
	 */
	private int availableIntegral;

	/**
	 * 已用积分
	 */
	private int usedIntegral;
	/**
	 * 总佣金
	 */
	private BigDecimal totalCommission;
	/**
	 * 可提现佣金
	 */
	private BigDecimal cashableCommission;
	/**
	 * 已提现佣金
	 */
	private BigDecimal cashedCommission;
	/**
	 * 未结算佣金
	 */
	private BigDecimal unsettledCommission;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;

	// columns END

	public Account() {
	}

	public Account(int id) {
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = true) })
	@JsonIgnore
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getOrderQuantity() {
		return this.orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getConsumeAmount() {
		return this.consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(BigDecimal useAmount) {
		this.useAmount = useAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getFreezeAmount() {
		return this.freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(int totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getAvailableIntegral() {
		return availableIntegral;
	}

	public void setAvailableIntegral(int availableIntegral) {
		this.availableIntegral = availableIntegral;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getUsedIntegral() {
		return usedIntegral;
	}

	public void setUsedIntegral(int usedIntegral) {
		this.usedIntegral = usedIntegral;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCashableCommission() {
		return cashableCommission;
	}

	public void setCashableCommission(BigDecimal cashableCommission) {
		this.cashableCommission = cashableCommission;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCashedCommission() {
		return cashedCommission;
	}

	public void setCashedCommission(BigDecimal cashedCommission) {
		this.cashedCommission = cashedCommission;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getUnsettledCommission() {
		return unsettledCommission;
	}

	public void setUnsettledCommission(BigDecimal unsettledCommission) {
		this.unsettledCommission = unsettledCommission;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = false, length = 19)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}