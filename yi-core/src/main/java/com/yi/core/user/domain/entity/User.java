//package com.yi.core.user.domain.entity;
//
//import com.yi.core.dept.domain.entity.Dept;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.validator.constraints.Length;
//import javax.validation.constraints.NotBlank;
//
//import javax.persistence.*;
//import javax.validation.constraints.Max;
//import java.io.Serializable;
//import java.util.*;
//
//import static javax.persistence.GenerationType.IDENTITY;
//
///**
// * 用户
// */
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table
//@NamedEntityGraphs(value = {@NamedEntityGraph(name = "User.depts", attributeNodes = @NamedAttributeNode("depts"))})
//public class User implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    //columns START
//    /**
//     * 员工ID
//     */
//    private int userId;
//    /**
//     * GUID
//     */
//    @NotBlank
//    @Length(max = 64)
//    private String guid;
//    /**
//     * 用户编码
//     */
//    @NotBlank
//    @Length(max = 32)
//    private String userCode;
//    /**
//     * 员工名称
//     */
//    @NotBlank
//    @Length(max = 64)
//    private String userName;
//
//    /**
//     * 钉钉号
//     */
//    @Length(max = 64)
//    private String dingId;
//    /**
//     * 微信号
//     */
//    @Length(max = 64)
//    private String wechatId;
//    /**
//     * EMAIL
//     */
//    @Length(max = 128)
//    private String email;
//    /**
//     * 工号
//     */
//    @Length(max = 32)
//    private String jobNumber;
//    /**
//     * 启用、停用
//     */
//    private boolean enabled;
//    /**
//     * 状态
//     */
//    @NotBlank
//    @Length(max = 32)
//    private String state;
//    /**
//     * 办公电话
//     */
//    @Length(max = 32)
//    private String telephone;
//    /**
//     * 办公地点
//     */
//    @Length(max = 128)
//    private String workPlace;
//    /**
//     * 职位信息
//     */
//    @Length(max = 32)
//    private String position;
//    /**
//     * 员工说明
//     */
//    @Length(max = 255)
//    private String remark;
//    /**
//     * 手机
//     */
//    @NotBlank
//    @Length(max = 20)
//    private String phone;
//
//    /**
//     * 头像
//     */
//    @NotBlank
//    private String avatarImg;
//    /**
//     * 管理员管理的范围部门
//     */
//    private Set<Dept> depts = new HashSet<Dept>(0);
//
//    public User() {
//    }
//
//    public User(int userId) {
//        this.userId = userId;
//    }
//
//    public User(String userCode) {
//        this.userCode = userCode;
//    }
//
//    public User(int userId, String userCode) {
//        this.userId = userId;
//        this.userCode = userCode;
//    }
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    @Column(unique = true, nullable = false, length = 10)
//    public int getUserId() {
//        return this.userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getUserCode() {
//        return userCode;
//    }
//
//    public void setUserCode(String userCode) {
//        this.userCode = userCode;
//    }
//
//    @Column(unique = false, nullable = false, length = 64)
//    public String getUserName() {
//        return this.userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//
//    @Column(unique = false, nullable = true, length = 64)
//    public String getDingId() {
//        return this.dingId;
//    }
//
//    public void setDingId(String dingId) {
//        this.dingId = dingId;
//    }
//
//    @Column(unique = false, nullable = true, length = 64)
//    public String getWechatId() {
//        return wechatId;
//    }
//
//    public void setWechatId(String wechatId) {
//        this.wechatId = wechatId;
//    }
//
//
//    @Column(unique = false, nullable = true, length = 128)
//    public String getEmail() {
//        return this.email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getJobNumber() {
//        return this.jobNumber;
//    }
//
//    public void setJobNumber(String jobNumber) {
//        this.jobNumber = jobNumber;
//    }
//
//    @Column(unique = false, nullable = false, length = 1)
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    @Column(unique = false, nullable = false, length = 32)
//    public String getState() {
//        return this.state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getTelephone() {
//        return this.telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    @Column(unique = false, nullable = true, length = 128)
//    public String getWorkPlace() {
//        return this.workPlace;
//    }
//
//    public void setWorkPlace(String workPlace) {
//        this.workPlace = workPlace;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getPosition() {
//        return this.position;
//    }
//
//    public void setPosition(String position) {
//        this.position = position;
//    }
//
//    @Column(unique = false, nullable = true, length = 255)
//    public String getRemark() {
//        return this.remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    @Column(unique = false, nullable = false, length = 20)
//    public String getPhone() {
//        return this.phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    @Column(nullable = false, length = 512)
//    public String getAvatarImg() {
//        return avatarImg;
//    }
//
//    public void setAvatarImg(String avatarImg) {
//        this.avatarImg = avatarImg;
//    }
//
//
//    @Column(nullable = false, length = 64, updatable = false)
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//
////    @ManyToMany(targetEntity = Resource.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
////    @JoinTable(name = "user_resc", joinColumns = {@JoinColumn(name = "USER_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "RESC_ID", nullable = false, updatable = false)})
////    public Set<Resource> getUserRescs() {
////        return userRescs;
////    }
////
////    public void setUserRescs(Set<Resource> userRescs) {
////        this.userRescs = userRescs;
////    }
//
//    @ManyToMany(targetEntity = Dept.class, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @JoinTable(name = "user_dept", joinColumns = {@JoinColumn(name = "USER_ID", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "DEPT_ID", nullable = false, updatable = false)})
//    public Set<Dept> getDepts() {
//        return depts;
//    }
//
//    public void setDepts(Set<Dept> depts) {
//        this.depts = depts;
//    }
//
//}