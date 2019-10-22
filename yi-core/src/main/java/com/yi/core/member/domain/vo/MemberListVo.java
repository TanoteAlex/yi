/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员
 *
 * @author xuyh
 * @version 1.0
 * @since 1.0
 */
public class MemberListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 上级ID
	 */
	private MemberSimple parent;
	/**
	 * 账号
	 */
	@NotBlank
	@Length(max = 16)
	private String username;
	/**
	 * 密码
	 */
	@NotBlank
	@Length(max = 32)
	private String password;
	/**
	 * 支付密码
	 *
	 */
	private String payPassword;
	// /**
	// * 所属小区
	// */
	// private CommunitySimple community;
	/**
	 * 会员状态（0启用1禁用）
	 */
	private Integer state;
	/**
	 * 登录次数
	 */
	private Integer loginNum;
	/**
	 * 消费次数
	 */
	private Integer consumeNum;
	/**
	 * 会员昵称
	 */
	@Length(max = 32)
	private String nickname;
	/**
	 * 会员头像
	 */
	@Length(max = 255)
	private String avater;
	/**
	 * 会员等级（member_level表ID）
	 */
	@NotNull
	private MemberLevelSimple memberLevel;
	/**
	 * 会员类型（0普通会员1小区管理员）
	 */
	@NotNull
	private Integer memberType;
	/**
	 * VIP(0否1是)
	 */
	private Integer vip;
	/**
	 * 省
	 */
	@Length(max = 8)
	private String province;
	/**
	 * 市
	 */
	@Length(max = 8)
	private String city;
	/**
	 * 区
	 */
	@Length(max = 8)
	private String district;
	/**
	 * 小区
	 */
	@Length(max = 32)
	private String address;
	/**
	 * 服务号 openid
	 */
	private String spOpenId;

	/**
	 * 小程序 openid
	 */
	private String mpOpenId;

	/**
	 * app openid
	 */
	private String appOpenId;
	
	/**
	 * qq openid
	 */
	private String qqOpenId;

	/**
	 * 应用 unionid
	 */
	private String unionId;
	/**
	 * 注册时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	// columns END

	/**
	 * 消费金额
	 */
	private BigDecimal consumeAmount;
	/**
	 * 订单数
	 */
	private Integer orderNum;
	/**
	 * 推广人数
	 */
	private Integer promotionNum;

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

	public MemberSimple getParent() {
		return parent;
	}

	public void setParent(MemberSimple parent) {
		this.parent = parent;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public MemberLevelSimple getMemberLevel() {
		return this.memberLevel;
	}

	public void setMemberLevel(MemberLevelSimple memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getMemberType() {
		return this.memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	// public CommunitySimple getCommunity() {
	// return community;
	// }
	//
	// public void setCommunity(CommunitySimple community) {
	// this.community = community;
	// }
	//

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}

	public String getSpOpenId() {
		return spOpenId;
	}

	public void setSpOpenId(String spOpenId) {
		this.spOpenId = spOpenId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getAppOpenId() {
		return appOpenId;
	}

	public void setAppOpenId(String appOpenId) {
		this.appOpenId = appOpenId;
	}

	public String getMpOpenId() {
		return mpOpenId;
	}

	public void setMpOpenId(String mpOpenId) {
		this.mpOpenId = mpOpenId;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getPromotionNum() {
		return promotionNum;
	}

	public void setPromotionNum(Integer promotionNum) {
		this.promotionNum = promotionNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Integer consumeNum) {
		this.consumeNum = consumeNum;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}
}