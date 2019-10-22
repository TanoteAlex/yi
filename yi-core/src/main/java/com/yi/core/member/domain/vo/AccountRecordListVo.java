/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员资金账户记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class AccountRecordListVo extends ListVoDomain implements java.io.Serializable {

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
	private MemberSimple member;
	/**
	 * 贡献者
	 */
	@Length(max = 32)
	private String contributor;
	/**
	 * 头像
	 */
	@Length(max = 255)
	private String avater;
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
	private BigDecimal tradeAmount;
	/**
	 * 交易类型（1佣金2在线支付3提现）
	 */
	private Integer tradeType;
	/**
	 * 交易时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
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

	//@JsonIgnore
	public MemberSimple getMember() {
		return this.member;
	}

	public void setMember(MemberSimple member) {
		this.member = member;
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

	public Integer getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
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

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

}