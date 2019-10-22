//package com.yi.core.user.domain.simple;
//
//import com.yi.core.user.domain.entity.User;
//import com.yihz.common.convert.domain.SimpleDomain;
//
//public class UserSimple extends SimpleDomain {
//    /**
//     * 员工ID
//     */
//    private int userId;
//    /**
//     * GUID
//     */
//    private String guid;
//
//    /**
//     * 员工名称
//     */
//    private String userName;
//
//    /**
//     * 头像
//     */
//    private String avatarImg;
//
//    public static UserSimple from(User user) {
//        if (user == null) {
//            return null;
//        }
//
//        UserSimple simple = new UserSimple();
//        simple.setUserId(user.getUserId());
//        simple.setUserName(user.getUserName());
//        simple.setGuid(user.getGuid());
//        simple.setAvatarImg(user.getAvatarImg());
//        return simple;
//    }
//
//    public UserSimple() {
//    }
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
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
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
//}
