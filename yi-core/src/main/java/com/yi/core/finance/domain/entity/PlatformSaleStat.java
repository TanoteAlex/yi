///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.finance.domain.entity;
//
//import static javax.persistence.GenerationType.IDENTITY;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotNull;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.validator.constraints.Length;
//
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.yihz.common.json.serializer.JsonTimestampSerializer;
//
///**
// * *
// * 
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
// */
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table(name="")
//public class PlatformSaleStat implements java.io.Serializable {
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
//
//	private Date statTime;
//	// columns END
//
//	public PlatformSaleStat() {
//	}
//
//	public PlatformSaleStat(int id) {
//		this.id = id;
//	}
//
//	@Id
//	@GeneratedValue(strategy = IDENTITY)
//	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
//	public int getId() {
//		return this.id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
//	public String getGuid() {
//		return this.guid;
//	}
//
//	public void setGuid(String guid) {
//		this.guid = guid;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 10)
//	public int getPlatformOrderNum() {
//		return this.platformOrderNum;
//	}
//
//	public void setPlatformOrderNum(int platformOrderNum) {
//		this.platformOrderNum = platformOrderNum;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
//	public BigDecimal getSaleAmount() {
//		return this.saleAmount;
//	}
//
//	public void setSaleAmount(BigDecimal saleAmount) {
//		this.saleAmount = saleAmount;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
//	public BigDecimal getPlatformRatio() {
//		return this.platformRatio;
//	}
//
//	public void setPlatformRatio(BigDecimal platformRatio) {
//		this.platformRatio = platformRatio;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
//	public BigDecimal getCost() {
//		return this.cost;
//	}
//
//	public void setCost(BigDecimal cost) {
//		this.cost = cost;
//	}
//
//	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 15)
//	public BigDecimal getProfit() {
//		return this.profit;
//	}
//
//	public void setProfit(BigDecimal profit) {
//		this.profit = profit;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@JsonSerialize(using = JsonTimestampSerializer.class)
//	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
//	public Date getStatTime() {
//		return this.statTime;
//	}
//
//	public void setStatTime(Date statTime) {
//		this.statTime = statTime;
//	}
//
//}