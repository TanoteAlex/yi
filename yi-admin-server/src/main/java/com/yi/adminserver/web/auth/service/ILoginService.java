package com.yi.adminserver.web.auth.service;

import com.yi.adminserver.web.auth.jwt.AdminToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.supplier.domain.vo.SupplierVo;

public interface ILoginService {
	/**
	 * 解析 JWT 令牌,获取用户会话信息
	 *
	 * @param token
	 * @return
	 */
	SessionData getSessionDataByToken(AdminToken token);

	/**
	 * 用户根据 JWT token登录
	 *
	 * @param token
	 * @return
	 */
	RestLoginResult loginByToken(String token);

	/**
	 * 用户登录
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	RestLoginResult login(String username, String password);
	
	/**
	 * 供应商登录
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	RestLoginResult loginForSupplier(String username, String password);
	
	/**
	 * 获取当前登录的 供应商
	 * 
	 * @return
	 */
	SupplierVo getLoginSupplier(SupplierToken token);
	
	/**
	 * 解析 JWT 令牌,获取用户会话信息
	 *
	 * @param token
	 * @return
	 */
	SessionData getSessionDataByToken(SupplierToken token);
}
