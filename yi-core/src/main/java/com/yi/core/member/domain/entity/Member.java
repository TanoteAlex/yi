/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.cart.domain.entity.Cart;
import com.yihz.common.json.deserializer.JsonDateDeserializer;
import com.yihz.common.json.serializer.JsonDateSerializer;
import org.springframework.context.annotation.Lazy;

/**
 * 会员
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
 @Proxy(lazy = false)
public class Member implements java.io.Serializable {

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
     * 上级
     */
    private Member parent;
    /**
     * 下级集合
     */
    private Set<Member> children = new HashSet<>(0);
    /**
     * 账号
     */
    @Length(max = 16)
    private String username;
    /**
     * 密码
     */
    @Length(max = 32)
    private String password;
    /**
     * 会员昵称
     */
    @Length(max = 32)
    private String nickname;
    /**
     * 手机号
     */
    @Length(max = 16)
    private String phone;
    /**
     * 会员等级（member_level表ID）
     */
    @NotNull
    private MemberLevel memberLevel;
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
    private Community community;
    /**
     * 省-作废
     */
    @Length(max = 8)
    private String province;
    /**
     * 市-作废
     */
    @Length(max = 8)
    private String city;
    /**
     * 区-作废
     */
    @Length(max = 8)
    private String district;
    /**
     * 小区-作废
     */
    @Length(max = 32)
    private String address;
    /**
     * 性别（0男，1女）
     */
    private Integer sex;
    /**
     * 会员头像
     */
    @Length(max = 255)
    private String avater;
    /**
     * 会员生日
     */
    private Date birthday;
    /**
     * 支付密码
     *
     * @return
     */
    @Length(max = 32)
    private String payPassword;
    /**
     * 会员状态 0启用1禁用
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
     * 应用 unionid
     */
    private String unionId;
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
     * 注册时间
     */
    private Date createTime;
    /**
     * 删除（0否1是）
     */
    private Integer deleted;
    /**
     * 删除时间
     */
    private Date delTime;
    /**
     * 账号
     */
    private Account account;
    /***
     * 邀请的人数
     */
    private Integer inviteNum;
    // columns END

    private List<ShippingAddress> shippingAddresss;

    // private List<ConsumeRecord> consumeRecords;

    private Set<Cart> carts;
    /**
     * 交易记录
     */
    private List<AccountRecord> accountRecords;
    // /**
    // * 订单
    // */
    // private List<SaleOrder> saleOrders;

    public Member() {
    }

    public Member(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false, length = 10)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Column(unique = false, nullable = true, length = 16)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "PARENT_ID")})
    @JsonIgnore
    public Member getParent() {
        return this.parent;
    }

    public void setParent(Member parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "parent")
    @Where(clause = "deleted=0")
    public Set<Member> getChildren() {
        return children;
    }

    public void setChildren(Set<Member> children) {
        this.children = children;
    }

    @Column(unique = false, nullable = true, length = 16)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "MEMBER_LEVEL_ID", nullable = false)})
    public MemberLevel getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }

    @Column(unique = false, nullable = true, length = 3)
    public Integer getMemberType() {
        return this.memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "COMMUNITY_ID")})
    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @Column(unique = false, nullable = true, length = 8)
    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(unique = false, nullable = true, length = 8)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(unique = false, nullable = true, length = 8)
    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(unique = false, nullable = true, length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(unique = false, nullable = false, length = 3)
    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(unique = false, nullable = true, length = 19)
    public Date getDelTime() {
        return this.delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @Column(unique = false, nullable = true, length = 19)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birdthday) {
        this.birthday = birdthday;
    }

    @Column(length = 32)
    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Column(length = 128)
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

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "ACCOUNT_ID", nullable = true)})
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    // @OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy
    // = "member")
    // public List<ConsumeRecord> getConsumeRecords() {
    // return consumeRecords;
    // }
    //
    // public void setConsumeRecords(List<ConsumeRecord> consumeRecord) {
    // this.consumeRecords = consumeRecord;
    // }

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "member")
    public List<ShippingAddress> getShippingAddresss() {
        return shippingAddresss;
    }

    public void setShippingAddresss(List<ShippingAddress> shippingAddress) {
        this.shippingAddresss = shippingAddress;
    }

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "member")
    public List<AccountRecord> getAccountRecords() {
        return accountRecords;
    }

    public void setAccountRecords(List<AccountRecord> accountRecords) {
        this.accountRecords = accountRecords;
    }

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "member")
    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    @Column(unique = false, nullable = true, length = 3)
    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getSpOpenId() {
        return spOpenId;
    }

    public void setSpOpenId(String spOpenId) {
        this.spOpenId = spOpenId;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getAppOpenId() {
        return appOpenId;
    }

    public void setAppOpenId(String appOpenId) {
        this.appOpenId = appOpenId;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getMpOpenId() {
        return mpOpenId;
    }

    public void setMpOpenId(String mpOpenId) {
        this.mpOpenId = mpOpenId;
    }

    @Column(unique = false, nullable = true, length = 32)
    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    @Column(unique = false, nullable = false, length = 10)
    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    @Column(unique = false, nullable = false, length = 10)
    public Integer getConsumeNum() {
        return consumeNum;
    }

    public void setConsumeNum(Integer consumeNum) {
        this.consumeNum = consumeNum;
    }

}