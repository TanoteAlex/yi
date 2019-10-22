/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.simple;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class MemberSimple extends SimpleDomain implements java.io.Serializable {

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
	 * 账号
	 */
	@NotBlank
	@Length(max = 16)
	private String username;
	/**
	 * 会员昵称
	 */
	@Length(max = 32)
	private String nickname;
	/**
	 * 会员等级（member_level表ID）
	 */
	// @NotNull
	// private MemberLevelSimple memberLevel;
	/**
	 * 手机号
	 */
	@Length(max = 16)
	private String phone;
	/**
	 * 会员类型（0普通会员1小区管理员）
	 */
	@NotNull
	private Integer memberType;
	/**
	 * VIP(0否1是)
	 */
	private Integer vip;
	// /**
	// * 所属小区
	// */
	// private CommunitySimple community;
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
	 * 登录次数
	 */
	private Integer loginNum;
	/**
	 * 消费次数
	 */
	private Integer consumeNum;
	/**
	 * 注册时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END
	/**
	 * 支付密码
	 *
	 * @return
	 */
	private String payPassword;
	/**
	 * 会员头像
	 */
	@Length(max = 128)
	private String avater;

	// /**
	// * 地址
	// *
	// */
	// private Set<CartSimple> shoppingCartProducts;

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

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

	// @JsonIgnore
	// public MemberSimple getParent() {
	// return this.parent;
	// }
	//
	// public void setParent(MemberSimple parent) {
	// this.parent = parent;
	// }

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	// public MemberLevelSimple getMemberLevel() {
	// return this.memberLevel;
	// }
	//
	// public void setMemberLevel(MemberLevelSimple memberLevel) {
	// this.memberLevel = memberLevel;
	// }

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

	// @JsonIgnore
	// public CommunitySimple getCommunity() {
	// return community;
	// }
	//
	// public void setCommunity(CommunitySimple community) {
	// this.community = community;
	// }

	// @JsonIgnore
	// public Set<CartSimple> getShoppingCartProducts() {
	// return shoppingCartProducts;
	// }
	//
	// public void setShoppingCartProducts(Set<CartSimple> shoppingCartProducts) {
	// this.shoppingCartProducts = shoppingCartProducts;
	// }

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

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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