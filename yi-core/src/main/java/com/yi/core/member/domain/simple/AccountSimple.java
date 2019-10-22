/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.simple;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员资金账号
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class AccountSimple extends SimpleDomain implements java.io.Serializable {

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
	private int orderQuantity;
	/**
	 * 消费金额
	 */
	private BigDecimal consumeAmount;
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 冻结金额
	 */
	private BigDecimal freezeAmount;
//	/**
//	 * 积分
//	 */
//	@Deprecated
//	private int integral;
//	/**
//	 * 剩余积分
//	 */
//	@Deprecated
//	private int residualIntegral;
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	// columns END

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

	@JsonIgnore
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public int getOrderQuantity() {
		return this.orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BigDecimal getConsumeAmount() {
		return this.consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreezeAmount() {
		return this.freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

//	public int getIntegral() {
//		return this.integral;
//	}
//
//	public void setIntegral(int integral) {
//		this.integral = integral;
//	}
//
//	public int getResidualIntegral() {
//		return this.residualIntegral;
//	}
//
//	public void setResidualIntegral(int residualIntegral) {
//		this.residualIntegral = residualIntegral;
//	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

	public BigDecimal getCashableCommission() {
		return cashableCommission;
	}

	public void setCashableCommission(BigDecimal cashableCommission) {
		this.cashableCommission = cashableCommission;
	}

	public BigDecimal getCashedCommission() {
		return cashedCommission;
	}

	public void setCashedCommission(BigDecimal cashedCommission) {
		this.cashedCommission = cashedCommission;
	}

	public BigDecimal getUnsettledCommission() {
		return unsettledCommission;
	}

	public void setUnsettledCommission(BigDecimal unsettledCommission) {
		this.unsettledCommission = unsettledCommission;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(int totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public int getAvailableIntegral() {
		return availableIntegral;
	}

	public void setAvailableIntegral(int availableIntegral) {
		this.availableIntegral = availableIntegral;
	}

	public int getUsedIntegral() {
		return usedIntegral;
	}

	public void setUsedIntegral(int usedIntegral) {
		this.usedIntegral = usedIntegral;
	}

}