/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.domain.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.CouponGrantStepSimple;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 优惠券发放配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class CouponGrantConfigVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 发放配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 优惠券（coupon表ID）
	 */
	@NotNull
	private CouponSimple coupon;
	/**
	 * 发放策略（1一次性发放，2分批发放）
	 */
	@Max(127)
	private Integer grantStrategy;
	/**
	 * 一次性发放节点（1购买，2收货，3评论，4超过三包时效）
	 */
	@Max(127)
	private Integer grantNode;
	/**
	 * 冻结天数
	 */
	@Max(127)
	private Integer freezeDays;
	/**
	 * 解冻节点（1下单，2收货，3评论，4超过三包时效）
	 */
	@Max(127)
	private Integer thawNode;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	@Max(127)
	private Integer state;
	/**
	 * 备注
	 */
	@Length(max = 255)
	private String remark;
	/**
	 * 创建时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	// columns END

	private List<CouponGrantStepSimple> couponGrantSteps = new ArrayList<>(0);

	private Set<CommoditySimple> commodities = new HashSet<>();

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

	public CouponSimple getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponSimple coupon) {
		this.coupon = coupon;
	}

	public Integer getGrantStrategy() {
		return this.grantStrategy;
	}

	public void setGrantStrategy(Integer grantStrategy) {
		this.grantStrategy = grantStrategy;
	}

	public Integer getGrantNode() {
		return this.grantNode;
	}

	public void setGrantNode(Integer grantNode) {
		this.grantNode = grantNode;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public List<CouponGrantStepSimple> getCouponGrantSteps() {
		return couponGrantSteps;
	}

	public void setCouponGrantSteps(List<CouponGrantStepSimple> couponGrantSteps) {
		this.couponGrantSteps = couponGrantSteps;
	}

	public Set<CommoditySimple> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<CommoditySimple> commodities) {
		this.commodities = commodities;
	}

	public Integer getFreezeDays() {
		return freezeDays;
	}

	public void setFreezeDays(Integer freezeDays) {
		this.freezeDays = freezeDays;
	}

	public Integer getThawNode() {
		return thawNode;
	}

	public void setThawNode(Integer thawNode) {
		this.thawNode = thawNode;
	}

}