/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.member.domain.bo.MemberLevelBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonDateDeserializer;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 优惠券
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class CouponBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 用户ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 优惠券编码
	 */
	private String couponNo;
	/**
	 * 优惠券名称
	 */
	private String couponName;
	/**
	 * 优惠券类型（1满减券2代金券3礼品券）
	 */
	private Integer couponType;
	/**
	 * 面值
	 */
	private BigDecimal parValue;
	/**
	 * 发放数量
	 */
	private int quantity;
	/**
	 * 领取数量
	 */
	private int receiveQuantity;
	/**
	 * 使用数量
	 */
	private int useQuantity;
	/**
	 * 使用条件类型（0无限制，1满XX元可用，2满XX件可用）
	 */
	private Integer useConditionType;
	/**
	 * 满XX元可用
	 */
	private BigDecimal fullMoney;
	/**
	 * 满XX件可用
	 */
	private int fullQuantity;
	/**
	 * 领取方式（1手工发放，2自助领取，3自动发放）
	 */
	private Integer receiveMode;
	/**
	 * 每人限领（不限制为空）
	 */
	private Integer limited;
	/**
	 * 有效期类型（1时间段2固定天数）
	 */

	private Integer validType;
	/**
	 * 开始时间
	 */
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date endTime;
	/**
	 * 固定天数（领取后到期天数）
	 */
	private Integer fixedDay;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 情怀语
	 */
	private String feelWord;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 商品集合
	 */
	private Set<CommodityBo> commodities;

	/**
	 * 会员等级集合
	 */
	private Set<MemberLevelBo> memberLevels;

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

	public String getCouponNo() {
		return this.couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getCouponName() {
		return this.couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Integer getCouponType() {
		return this.couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public BigDecimal getParValue() {
		return this.parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUseQuantity() {
		return this.useQuantity;
	}

	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}

	public Integer getReceiveMode() {
		return this.receiveMode;
	}

	public void setReceiveMode(Integer receiveMode) {
		this.receiveMode = receiveMode;
	}

	public Integer getLimited() {
		return this.limited;
	}

	public void setLimited(Integer limited) {
		this.limited = limited;
	}

	public Integer getValidType() {
		return this.validType;
	}

	public void setValidType(Integer validType) {
		this.validType = validType;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getFixedDay() {
		return this.fixedDay;
	}

	public void setFixedDay(Integer fixedDay) {
		this.fixedDay = fixedDay;
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

	public Set<CommodityBo> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<CommodityBo> commodities) {
		this.commodities = commodities;
	}

	public int getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(int receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	public Integer getUseConditionType() {
		return useConditionType;
	}

	public void setUseConditionType(Integer useConditionType) {
		this.useConditionType = useConditionType;
	}

	public BigDecimal getFullMoney() {
		return fullMoney;
	}

	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}

	public int getFullQuantity() {
		return fullQuantity;
	}

	public void setFullQuantity(int fullQuantity) {
		this.fullQuantity = fullQuantity;
	}

	public Set<MemberLevelBo> getMemberLevels() {
		return memberLevels;
	}

	public void setMemberLevels(Set<MemberLevelBo> memberLevels) {
		this.memberLevels = memberLevels;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFeelWord() {
		return feelWord;
	}

	public void setFeelWord(String feelWord) {
		this.feelWord = feelWord;
	}
}