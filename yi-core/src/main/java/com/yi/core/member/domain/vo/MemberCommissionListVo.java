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

import com.yi.core.order.domain.vo.SaleOrderListVo;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员佣金
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class MemberCommissionListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员佣金ID
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
	private MemberListVo member;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrderListVo saleOrder;
	/**
	 * 下级（member表ID）
	 */
	@NotNull
	private MemberListVo subordinate;
	/**
	 * 佣金等级（1一级佣金，2二级佣金，3小区佣金）
	 */
	@Max(127)
	private Integer commissionType;
	/**
	 * 结算状态（1未结算，2已结算，3已退回）
	 */
	@Max(127)
	private Integer settlementState;
	/**
	 * 佣金金额
	 */
	@Max(999999999999999L)
	private BigDecimal commissionAmount;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
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

	public MemberListVo getMember() {
		return member;
	}

	public void setMember(MemberListVo member) {
		this.member = member;
	}

	public SaleOrderListVo getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderListVo saleOrder) {
		this.saleOrder = saleOrder;
	}

	public MemberListVo getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(MemberListVo subordinate) {
		this.subordinate = subordinate;
	}

	public Integer getCommissionType() {
		return this.commissionType;
	}

	public void setCommissionType(Integer commissionType) {
		this.commissionType = commissionType;
	}

	public Integer getSettlementState() {
		return this.settlementState;
	}

	public void setSettlementState(Integer settlementState) {
		this.settlementState = settlementState;
	}

	public BigDecimal getCommissionAmount() {
		return this.commissionAmount;
	}

	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}