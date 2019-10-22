/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.basic.domain.simple.CommunitySimple;
import com.yi.core.member.domain.simple.AccountRecordSimple;
import com.yi.core.member.domain.simple.AccountSimple;
import com.yi.core.member.domain.simple.ConsumeRecordSimple;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.member.domain.simple.ShippingAddressSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonDateSerializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 会员
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class MemberVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 会员ID
	 */
	private int id;
	/**
	 * GUID
	 */
	private String guid;
	/**
	 * 上级ID
	 */
	private MemberSimple parent;
	/**
	 * 账号
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 会员昵称
	 */
	private String nickname;
	/**
	 * 会员等级（member_level表ID）
	 */
	private MemberLevelSimple memberLevel;
	/**
	 * 会员类型（0普通会员1小区管理员）
	 */
	private Integer memberType;
	/**
	 * VIP(0否1是)
	 */
	private Integer vip;
	/**
	 * 所属小区
	 */
	private CommunitySimple community;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String district;
	/**
	 * 小区
	 */
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
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;

	/**
	 * 性别（0男，1女）
	 */
	private Integer sex;

	/**
	 * 会员头像
	 */
	private String avater;

	/**
	 * 会员生日
	 *
	 * @return
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date birthday;

	/**
	 * 支付密码
	 *
	 * @return
	 */
	private String payPassword;
	/**
	 * 手机号
	 */
	private String phone;
	// columns END

	/**
	 * 会员状态 1启用，2冻结
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
	 * 地址
	 */
	private List<ShippingAddressSimple> shippingAddresss;

	/**
	 * 消费明细
	 */
	private List<ConsumeRecordSimple> consumeRecords;

	/**
	 * 交易记录
	 */
	private List<AccountRecordSimple> accountRecords;

	private AccountSimple account;

	private int parentId;

	private String parentName;

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
		return this.parent;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<ShippingAddressSimple> getShippingAddresss() {
		return shippingAddresss;
	}

	public void setShippingAddresss(List<ShippingAddressSimple> shippingAddresss) {
		this.shippingAddresss = shippingAddresss;
	}

	public List<ConsumeRecordSimple> getConsumeRecords() {
		return consumeRecords;
	}

	public void setConsumeRecords(List<ConsumeRecordSimple> consumeRecords) {
		this.consumeRecords = consumeRecords;
	}

	public List<AccountRecordSimple> getAccountRecords() {
		return accountRecords;
	}

	public void setAccountRecords(List<AccountRecordSimple> accountRecords) {
		this.accountRecords = accountRecords;
	}

	public CommunitySimple getCommunity() {
		return community;
	}

	public void setCommunity(CommunitySimple community) {
		this.community = community;
	}

	public AccountSimple getAccount() {
		return account;
	}

	public void setAccount(AccountSimple account) {
		this.account = account;
	}

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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvater() {
		return avater;
	}

	public void setAvater(String avater) {
		this.avater = avater;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}