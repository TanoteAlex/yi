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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员资金账户记录
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
public class AccountRecord implements java.io.Serializable {

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
	private Member member;

	/**
	 * 订单（sale_order表ID）
	 */
	private SaleOrder saleOrder;
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
	@Max(999999999999999L)
	private BigDecimal tradeAmount;
	/**
	 * 交易类型（1佣金转入2在线支付3提现4小区提成）
	 */
	private Integer tradeType;
	/**
	 * 交易时间
	 */
	private Date tradeTime;
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

	public AccountRecord() {
	}

	public AccountRecord(int id) {
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
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALE_ORDER_ID", nullable = false, updatable = false) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getOperateType() {
		return this.operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
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

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}