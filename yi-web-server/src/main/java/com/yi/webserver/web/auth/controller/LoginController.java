package com.yi.webserver.web.auth.controller;

import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yi.webserver.web.auth.jwt.JwtWebToken;
import com.yi.webserver.web.auth.service.ILoginService;
import com.yihz.common.annotation.session.LoginRequired;
import com.yihz.common.json.RestResult;
import com.yihz.common.json.Result;
import com.yihz.common.utils.web.RestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author aidy
 * @version 1.0
 * @since 1.0 本controller 类里面的接口均无需登录
 **/
@Api(tags = "登录控制层")
@RestController
@RequestMapping(value = "/auth")
@LoginRequired(required = false)
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Autowired
	private ILoginService loginService;

	@ApiOperation(value = "退出登录", notes = "退出登录")
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public RestResult logout(HttpServletRequest request, HttpServletResponse response) {
		SessionData sessionData = ThreadLocalSession.getSessionData();
		if (sessionData != null) {
			// 采用了 JWT 令牌, 没有必要了
			ThreadLocalSession.setSessionData(null);
			LOG.info("用户注销成功, sessionData={}", sessionData);
		}
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		return RestUtils.success();
	}

	/**
	 * 登录
	 **/
	@ApiOperation(value = "登录", notes = "登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String"),
	})
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public RestLoginResult login(@RequestParam(required = false) String userName, @RequestParam(required = false) String password, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			RestLoginResult data = new RestLoginResult();
			data.setResult(Result.FAILURE);
			data.setMessage("用户名和密码必须输入");
			return data;
		}

		RestLoginResult data = loginService.login(userName, password);

		if (data.getResult() == Result.SUCCESS) {
			SessionData sessionData = data.getSessionData();
			ThreadLocalSession.setSessionData(sessionData);

			data.setToken(generateJwtToken(sessionData));

			response.setHeader(JwtWebToken.TOKEN_HEADER, data.getToken());
			LOG.info("用户登录成功, sessionData={}", sessionData);
		}

		return data;
	}

	/**
	 * 员工重新登录
	 **/
	@ApiOperation(value = "员工重新登录", notes = "员工重新登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "用户名", required = false, dataType = "String")
	})
	@RequestMapping(value = "loginByToken", method = RequestMethod.POST)
	public RestLoginResult loginByToken(@RequestParam(name = "token", required = false) String token, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isBlank(token)) {
			RestLoginResult data = new RestLoginResult();
			data.setResult(Result.FAILURE);
			data.setMessage("没有设置参数 token");
			return data;
		}

		RestLoginResult data = loginService.loginByToken(token);

		if (data.getResult() == Result.SUCCESS) {
			SessionData sessionData = data.getSessionData();
			ThreadLocalSession.setSessionData(sessionData);

			data.setToken(generateJwtToken(sessionData));

			response.setHeader(JwtWebToken.TOKEN_HEADER, data.getToken());
			LOG.info("用户重新登录成功, sessionData={}", sessionData);
		}

		return data;
	}

	/**
	 * 根据登陆信息生成JWT令牌
	 *
	 * @param sessionData
	 * @return
	 */
	@ApiOperation(value = "根据登陆信息生成JWT令牌", notes = "根据登陆信息生成JWT令牌")
	private String generateJwtToken(SessionData sessionData) {
		String token = JwtWebToken.generateToken(sessionData.getId(), sessionData.getUserCode(), null, null);
		return token;
	}

}