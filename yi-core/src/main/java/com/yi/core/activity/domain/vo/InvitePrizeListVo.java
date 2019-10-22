/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.vo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.activity.domain.simple.InviteActivitySimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class InvitePrizeListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 邀请奖品ID
	 */
	@Max(9999999999L)
	private Integer id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 邀请有礼活动（invite_activity表id）
	 */
	@NotNull
	private InviteActivitySimple inviteActivity;
	/**
	 * 奖品编码
	 */
	@Length(max = 16)
	private String prizeNo;
	/**
	 * 奖品名称
	 */
	@Length(max = 32)
	private String prizeName;
	/**
	 * 邀请人数
	 */
	@Max(9999999999L)
	private Integer inviteNum;
	/**
	 * 奖品类型（1积分，2商品，3优惠券）
	 */
	@Max(127)
	private Integer prizeType;
	/**
	 * 积分
	 */
	@Max(9999999999L)
	private Integer integral;
	/**
	 * 商品（commodity表ID）
	 */
	private CommoditySimple commodity;
	/**
	 * 商品（product表ID）
	 */
	private ProductSimple product;
	/**
	 * 优惠券（coupon表ID）
	 */
	private CouponSimple coupon;
	/**
	 * 排序
	 */

	private Integer sort;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
	/**
	 * 创建时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	// columns END

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public InviteActivitySimple getInviteActivity() {
		return this.inviteActivity;
	}

	public void setInviteActivity(InviteActivitySimple inviteActivity) {
		this.inviteActivity = inviteActivity;
	}

	public String getPrizeNo() {
		return this.prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public String getPrizeName() {
		return this.prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getInviteNum() {
		return this.inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}

	public Integer getPrizeType() {
		return this.prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	public CommoditySimple getCommodity() {
		return this.commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public ProductSimple getProduct() {
		return this.product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public CouponSimple getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponSimple coupon) {
		this.coupon = coupon;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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