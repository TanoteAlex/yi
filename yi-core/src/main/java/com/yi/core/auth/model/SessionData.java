package com.yi.core.auth.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 会话登录信息，登录后保存到Session中
 * <p/>
 * 对于会话过程数据尽量保存到Redis等数据库中，避免每次请求时获取Session数据时数据过多
 */
public class SessionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; // 用户ID

	private String userCode; // 用户编码

	private String userName; // 用户姓名

	private String avatar; // 头像
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("userName", userName).append("userCode", userCode)
				.append("avatar", avatar).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SessionData that = (SessionData) o;
		return id == that.id && Objects.equals(userName, that.userName) && Objects.equals(userCode, that.userCode)
				&& Objects.equals(avatar, that.avatar);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userName, userCode, avatar);
	}

}
