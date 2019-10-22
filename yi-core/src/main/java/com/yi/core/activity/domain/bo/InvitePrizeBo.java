/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.domain.bo;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.activity.domain.simple.InviteActivitySimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class InvitePrizeBo extends BoDomain implements java.io.Serializable {

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

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */

	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 会员
	 */
	private MemberBo member;

	/**
	 * 收货地址
	 */
	private ShippingAddressBo shippingAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public InviteActivitySimple getInviteActivity() {
		return inviteActivity;
	}

	public void setInviteActivity(InviteActivitySimple inviteActivity) {
		this.inviteActivity = inviteActivity;
	}

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}

	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	public CommoditySimple getCommodity() {
		return commodity;
	}

	public void setCommodity(CommoditySimple commodity) {
		this.commodity = commodity;
	}

	public ProductSimple getProduct() {
		return product;
	}

	public void setProduct(ProductSimple product) {
		this.product = product;
	}

	public CouponSimple getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponSimple coupon) {
		this.coupon = coupon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public MemberBo getMember() {
		return member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public ShippingAddressBo getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddressBo shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}