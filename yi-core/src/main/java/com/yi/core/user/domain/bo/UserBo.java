//package com.yi.core.user.domain.bo;
//
//import com.google.common.collect.Lists;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yihz.common.convert.domain.BoDomain;
//import org.hibernate.validator.constraints.Length;
//import javax.validation.constraints.NotBlank;
//
//import java.util.Collection;
//
//public class UserBo extends BoDomain {
//    /**
//     * 对象路径
//     */
//    private String objectPath;
//
//    /**
//     * 员工ID
//     */
//    private int userId;
//    /**
//     * GUID
//     */
//    private String guid;
//    /**
//     * 员工编码
//     */
//    @Length(max = 32)
//    private String userCode;
//    /**
//     * 员工名称
//     */
//    @NotBlank
//    @Length(max = 64)
//    private String userName;
//    /**
//     * 钉钉号
//     */
//    @Length(max = 64)
//    private String dingId;
//    /**
//     * 微信号
//     */
//    @Length(max = 64)
//    private String weixinId;
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
//    @Length(max = 11)
//    private String phone;
//    /**
//     * 头像
//     */
//    @NotBlank
//    private String avatarImg;
//
//    /**
//     * 员工所属部门
//     */
//    private Collection<DeptSimple> workDepts = Lists.newArrayList();
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//
//    public String getUserCode() {
//        return userCode;
//    }
//
//    public void setUserCode(String userCode) {
//        this.userCode = userCode;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getDingId() {
//        return dingId;
//    }
//
//    public void setDingId(String dingId) {
//        this.dingId = dingId;
//    }
//
//    public String getWeixinId() {
//        return weixinId;
//    }
//
//    public void setWeixinId(String weixinId) {
//        this.weixinId = weixinId;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getJobNumber() {
//        return jobNumber;
//    }
//
//    public void setJobNumber(String jobNumber) {
//        this.jobNumber = jobNumber;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getWorkPlace() {
//        return workPlace;
//    }
//
//    public void setWorkPlace(String workPlace) {
//        this.workPlace = workPlace;
//    }
//
//    public String getPosition() {
//        return position;
//    }
//
//    public void setPosition(String position) {
//        this.position = position;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAvatarImg() {
//        return avatarImg;
//    }
//
//    public void setAvatarImg(String avatarImg) {
//        this.avatarImg = avatarImg;
//    }
//
//    public Collection<DeptSimple> getWorkDepts() {
//        return workDepts;
//    }
//
//    public void setWorkDepts(Collection<DeptSimple> workDepts) {
//        this.workDepts = workDepts;
//    }
//
//    public String getObjectPath() {
//        return objectPath;
//    }
//
//    public void setObjectPath(String objectPath) {
//        this.objectPath = objectPath;
//    }
//
//}
