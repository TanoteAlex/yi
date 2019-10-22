package com.yi.core.stats.domain.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台数据
 * 
 * @author xuyh
 *
 */
public class PlatformDataVo {

	/** 今日订单数 */
	private int todayOrderNum;
	/** 昨日订单数 */
	private int yesterdayOrderNum;
	/** 待发货订单 */
	private int waitDeliveryNum;
	/** 今日成交额 */
	private BigDecimal todayTradeAmout;
	/** 昨日成交额 */
	private BigDecimal yesterdayTradeAmout;

	/** 今日新增会员数 */
	private int todayMemberNum;
	/** 会员数 */
	private int memberNum;
	/** 商品数 */
	private int commodityNum;
	/** 供应商数 */
	private int supplierNum;
	/** 售后订单数 */
	private int afterSaleOrderNum;
	/** 会员消费排行 */
	private List<MemberConsumeVo> memberConsumes;
	/** 商品销售排行 */
	private List<CommoditySaleVo> commoditySales;

	/** 供应商后台 提现数人数 */
	private int withdrawNum;

	/**
	 * 每日新增
	 */
	private List<DailyAddNumVo> dailyAddNums;

	public int getTodayOrderNum() {
		return todayOrderNum;
	}

	public void setTodayOrderNum(int todayOrderNum) {
		this.todayOrderNum = todayOrderNum;
	}

	public int getYesterdayOrderNum() {
		return yesterdayOrderNum;
	}

	public void setYesterdayOrderNum(int yesterdayOrderNum) {
		this.yesterdayOrderNum = yesterdayOrderNum;
	}

	public int getWaitDeliveryNum() {
		return waitDeliveryNum;
	}

	public void setWaitDeliveryNum(int waitDeliveryNum) {
		this.waitDeliveryNum = waitDeliveryNum;
	}

	public BigDecimal getTodayTradeAmout() {
		return todayTradeAmout;
	}

	public void setTodayTradeAmout(BigDecimal todayTradeAmout) {
		this.todayTradeAmout = todayTradeAmout;
	}

	public BigDecimal getYesterdayTradeAmout() {
		return yesterdayTradeAmout;
	}

	public void setYesterdayTradeAmout(BigDecimal yesterdayTradeAmout) {
		this.yesterdayTradeAmout = yesterdayTradeAmout;
	}

	public int getTodayMemberNum() {
		return todayMemberNum;
	}

	public void setTodayMemberNum(int todayMemberNum) {
		this.todayMemberNum = todayMemberNum;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public int getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(int commodityNum) {
		this.commodityNum = commodityNum;
	}
	
	public int getAfterSaleOrderNum() {
		return afterSaleOrderNum;
	}

	public void setAfterSaleOrderNum(int afterSaleOrderNum) {
		this.afterSaleOrderNum = afterSaleOrderNum;
	}

	public List<MemberConsumeVo> getMemberConsumes() {
		return memberConsumes;
	}

	public void setMemberConsumes(List<MemberConsumeVo> memberConsumes) {
		this.memberConsumes = memberConsumes;
	}

	public List<CommoditySaleVo> getCommoditySales() {
		return commoditySales;
	}

	public void setCommoditySales(List<CommoditySaleVo> commoditySales) {
		this.commoditySales = commoditySales;
	}

	public List<DailyAddNumVo> getDailyAddNums() {
		return dailyAddNums;
	}

	public void setDailyAddNums(List<DailyAddNumVo> dailyAddNums) {
		this.dailyAddNums = dailyAddNums;
	}

	public int getWithdrawNum() {
		return withdrawNum;
	}

	public void setWithdrawNum(int withdrawNum) {
		this.withdrawNum = withdrawNum;
	}

	public int getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(int supplierNum) {
		this.supplierNum = supplierNum;
	}
}
