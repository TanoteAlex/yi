package com.yi.webserver.web.auth.service;


import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.webserver.web.auth.jwt.WebToken;

public interface ILoginService {
    /**
     * 解析 JWT 令牌,获取用户会话信息
     *
     * @param token
     * @return
     */
    SessionData getSessionDataByToken(WebToken token);

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
    RestLoginResult login(String userName, String password);
}
