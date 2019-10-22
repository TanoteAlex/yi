///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.finance.domain.simple;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
//import com.yihz.common.json.serializer.JsonTimestampSerializer;
//import org.hibernate.validator.constraints.Length;
//
//import com.yihz.common.convert.domain.SimpleDomain;
//
///**
// * *
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//public class PlatformSaleStatSimple extends SimpleDomain implements java.io.Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	// columns START
//	/**
//	 * 平台销售统计ID
//	 */
//	@Max(9999999999L)
//	private int id;
//	/**
//	 * GUID
//	 */
//	@Length(max = 32)
//	private String guid;
//	/**
//	 * 平台订单数
//	 */
//	@NotNull
//	@Max(9999999999L)
//	private int platformOrderNum;
//	/**
//	 * 销售额
//	 */
//	@NotNull
//	@Max(999999999999999L)
//	private BigDecimal saleAmount;
//	/**
//	 * 平台占比
//	 */
//	@NotNull
//	@Max(999999999999999L)
//	private BigDecimal platformRatio;
//	/**
//	 * 成本
//	 */
//	@NotNull
//	@Max(999999999999999L)
//	private BigDecimal cost;
//	/**
//	 * 利润
//	 */
//	@NotNull
//	@Max(999999999999999L)
//	private BigDecimal profit;
//	/**
//	 * 统计时间
//	 */
//	@JsonSerialize(using = JsonTimestampSerializer.class)
//	@JsonDeserialize(using = JsonTimestampDeserializer.class)
//	private Date statTime;
//	// columns END
//
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getGuid() {
//		return this.guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	public int getPlatformOrderNum() {
//		return this.platformOrderNum;
//	}
//
//	public void setPlatformOrderNum(int platformOrderNum) {
//		this.platformOrderNum = platformOrderNum;
//	}
//
//	public BigDecimal getSaleAmount() {
//		return this.saleAmount;
//	}
//
//	public void setSaleAmount(BigDecimal saleAmount) {
//		this.saleAmount = saleAmount;
//	}
//
//	public BigDecimal getPlatformRatio() {
//		return this.platformRatio;
//	}
//
//	public void setPlatformRatio(BigDecimal platformRatio) {
//		this.platformRatio = platformRatio;
//	}
//
//	public BigDecimal getCost() {
//		return this.cost;
//	}
//
//	public void setCost(BigDecimal cost) {
//		this.cost = cost;
//	}
//
//	public BigDecimal getProfit() {
//		return this.profit;
//	}
//
//	public void setProfit(BigDecimal profit) {
//		this.profit = profit;
//	}
//
//	public Date getStatTime() {
//		return this.statTime;
//	}
//
//	public void setStatTime(Date statTime) {
//		this.statTime = statTime;
//	}
//
//}