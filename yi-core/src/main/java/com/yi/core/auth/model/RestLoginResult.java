package com.yi.core.auth.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yihz.common.json.RestResult;
import com.yihz.common.json.Result;

/**
 * 用户登录成功后返回前端的数据
 */
public class RestLoginResult extends RestResult {

	/** JWT token */
	private String token;
	/** 会话信息 */
	private SessionData sessionData;
	/** 登录用户 */
	private Object loginUser;

	public RestLoginResult() {
		setResult(Result.SUCCESS);
	}

	public RestLoginResult(RestResult restResult) {
		super(restResult.getResult(), restResult.getMessage(), restResult.getData());
	}

	public RestLoginResult(Result result, Object data) {
		super(result, data);
	}

	public RestLoginResult(Result result, String message, Object data) {
		super(result, message, data);
	}

	public RestLoginResult(Result result, String message) {
		super(result, message, null);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}

	public Object getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Object loginUser) {
		this.loginUser = loginUser;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("token", token).append("sessionData", sessionData)
				.append("loginUser", loginUser).toString();
	}

}
