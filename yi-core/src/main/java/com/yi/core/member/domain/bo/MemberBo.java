/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.bo;

import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.basic.domain.simple.CommunitySimple;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonDateDeserializer;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 会员
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class MemberBo extends BoDomain implements java.io.Serializable {

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
	private int parentId;
	/**
	 * 上级
	 */
	private MemberBo parent;
	/**
	 * 账号
	 */
	private String username;

	/**
	 * 手机号码
	 */
	private String phone;

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
	@Length(max = 8)
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
	 * 应用 unionid
	 */
	private String unionId;
	
	/**
	 * 公众号 openid
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
	 * 注册时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
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
	private String avater;

	private Set<ShippingAddressBo> shippingAddresss;
	/**
	 * 消费明细
	 */
	private Set<ConsumeRecordBo> consumeRecords;
	/**
	 * 交易记录
	 */
	private Set<AccountRecordBo> accountRecords;
	// /**
	// * 账户
	// */
	// private Set<AccountBo> accounts;
	/**
	 * 支付密码
	 *
	 * @return
	 */
	private String payPassword;

	/**
	 * 生日
	 */
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Date birthday;

	/**
	 * 新支付密码
	 *
	 * @return
	 */
	private String newPayPassword;
	/**
	 * 性别（0男，1女）
	 */
	private Integer sex;

	/**
	 * 账户
	 */
	private AccountBo accountBo;

	/** 短信验证码 */
	private String smsCode;

	/**
	 * 登录Ip
	 */
	private String loginIp;
	/**
	 * 登录来源
	 */
	private String loginSource;

	/**
	 * APP登录
	 */
	private boolean loginByApp;

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

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<ConsumeRecordBo> getConsumeRecords() {
		return consumeRecords;
	}

	public void setConsumeRecords(Set<ConsumeRecordBo> consumeRecords) {
		this.consumeRecords = consumeRecords;
	}

	public Set<AccountRecordBo> getAccountRecords() {
		return accountRecords;
	}

	public void setAccountRecords(Set<AccountRecordBo> accountRecords) {
		this.accountRecords = accountRecords;
	}

	public MemberLevelSimple getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(MemberLevelSimple memberLevel) {
		this.memberLevel = memberLevel;
	}

	// public Set<AccountBo> getAccounts() {
	// return accounts;
	// }
	//
	// public void setAccounts(Set<AccountBo> accounts) {
	// this.accounts = accounts;
	// }

	public CommunitySimple getCommunity() {
		return community;
	}

	public void setCommunity(CommunitySimple community) {
		this.community = community;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNewPayPassword() {
		return newPayPassword;
	}

	public void setNewPayPassword(String newPayPassword) {
		this.newPayPassword = newPayPassword;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set<ShippingAddressBo> getShippingAddresss() {
		return shippingAddresss;
	}

	public void setShippingAddresss(Set<ShippingAddressBo> shippingAddresss) {
		this.shippingAddresss = shippingAddresss;
	}

	public AccountBo getAccountBo() {
		return accountBo;
	}

	public void setAccountBo(AccountBo accountBo) {
		this.accountBo = accountBo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
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

	public MemberBo getParent() {
		return parent;
	}

	public void setParent(MemberBo parent) {
		this.parent = parent;
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

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public boolean isLoginByApp() {
		return loginByApp;
	}

	public void setLoginByApp(boolean loginByApp) {
		this.loginByApp = loginByApp;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

}