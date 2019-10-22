/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import org.hibernate.validator.constraints.Length;

import com.yihz.common.convert.domain.BoDomain;

/**
 * 会员资金账户记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Deprecated
public class AccountRecordBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 账户记录ID
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
	@Max(9999999999L)
	private int memberId;
	/**
	 * 操作类型（1收入2支出）
	 */
	private Integer operateType;
	/**
	 * 流水号
	 */
	@Length(max = 16)
	private String serialNo;
	/**
	 * 交易金额
	 */
	@Max(999999999999999L)
	private BigDecimal tradeAmount;
	/**
	 * 账户余额
	 */
	@Max(999999999999999L)
	private BigDecimal balance;
	/**
	 * 交易类型（1佣金转入2在线支付3提现）
	 */

	private Integer tradeType;
	/**
	 * 交易方式（1店铺佣金2余额）
	 */

	private Integer tradeMode;
	/**
	 * 交易时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date tradeTime;
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

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Integer getOperateType() {
		return this.operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getTradeMode() {
		return this.tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}