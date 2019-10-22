package com.yi.webserver.web.auth.service.impl;

import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.webserver.web.auth.jwt.WebToken;
import com.yi.webserver.web.auth.service.ILoginService;
import org.springframework.stereotype.Component;

@Component
public class LoginService implements ILoginService {

//    @Autowired
//    private IUserService userService;

    @Override
    public SessionData getSessionDataByToken(WebToken token) {
//        final User user = userService.getById(token.getId());
//        if (user == null) {
//            return null;
//        }
//
//        SessionData sessionData = new SessionData();
//        sessionData.setId(user.getUserId());
//        sessionData.setUserCode(user.getUserCode());
//        sessionData.setUserName(user.getUserName());
//        sessionData.setAvatarImg(user.getAvatarImg());
//
//        return sessionData;
        return null;
    }


    @Override
    public RestLoginResult loginByToken(String token) {
//        WebToken tk = JwtWebToken.getToken(token);
//        if (tk == null) {
//            return new RestLoginResult(Result.FAILURE, "无法识别的JWT令牌内容", null);
//        }
//
//        final User user = userService.getById(tk.getId());
//        if (user == null) {
//            return new RestLoginResult(Result.FAILURE, "员工资料已经不存在,无法登陆", null);
//        }
//
//        SessionData sessionData = new SessionData();
//        sessionData.setId(user.getUserId());
//        sessionData.setUserCode(user.getUserCode());
//        sessionData.setUserName(user.getUserName());
//        sessionData.setAvatarImg(user.getAvatarImg());
//
//        RestLoginResult data = new RestLoginResult();
//        data.setUser(user);
//        data.setSessionData(sessionData);
//
//        return data;
        return null;
    }

    @Override
    public RestLoginResult login(String userName, String password) {
//        RestResult responseResult = authService.login(userName, password, currentDataSource);
//
//        if (responseResult.getResult() != Result.SUCCESS) {
//            return new RestLoginResult(responseResult);
//        }
//
//        AuthResult authResult = (AuthResult) responseResult.getData();
//
//        SessionData sessionData = new SessionData();
//        final Tenant tenant = authResult.getTenant();
//        if (tenant != null) {
//            sessionData.setTenantId(tenant.getTenantId());
//            sessionData.setDataSource(tenant.getDataSourceName());
//        }
//
//        final User user = authResult.getUser();
//        sessionData.setUserId(user.getUserId());
//        sessionData.setUserName(user.getUserName());
//
//        final User user = authResult.getUser();
//        if (user != null) {
//            sessionData.setUserCode(user.getUserCode());
//            sessionData.setAvatarImg(user.getAvatarImg());
//            sessionData.setOfAccount(user.getOfAccount());
//
//        } else {
//            sessionData.setUserCode("user:" + user.getUserId());
//        }
//
//        RestLoginResult data = buildRestLoginResult(user);
//        data.setSessionData(sessionData);
//        data.setCurrentTenant(authResult.getTenant());
//
//        if (user != null) {
//            data.setTenants(authResult.getUser().getTenants());
//        }
//
//        return data;
        return null;
    }


}
