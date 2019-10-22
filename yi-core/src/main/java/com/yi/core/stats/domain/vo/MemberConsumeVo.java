package com.yi.core.stats.domain.vo;

import java.math.BigDecimal;

/**
 * 会员消费
 * 
 * @author xuyh
 *
 */
public class MemberConsumeVo {

	/** 会员名称 */
	private String memberName;
	/** 订单数 */
	private Integer orderNum;
	/** 交易金额 */
	private BigDecimal tradeAmount;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

}
