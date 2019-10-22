package com.yi.adminserver.web.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yi.adminserver.web.auth.jwt.JwtAdminToken;
import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.adminserver.web.auth.service.ILoginService;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.service.IUserService;
import com.yi.core.supplier.domain.vo.SupplierVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.annotation.session.LoginRequired;
import com.yihz.common.json.RestResult;
import com.yihz.common.json.Result;
import com.yihz.common.utils.web.RestUtils;

/**
 * 登录
 * 
 * @author aidy
 * @version 1.0
 * @since 1.0 本controller 类里面的接口均无需登录
 **/
@Api("登录控制层")
@RestController
@RequestMapping(value = "/auth")
@LoginRequired(required = false)
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Autowired
	private ILoginService loginService;

	@Resource
	private IUserService userService;

	/**
	 * 用户登录
	 **/
	@ApiOperation(value = "用户登录(总后台)", notes = "用户登录(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public RestLoginResult login(@RequestParam(required = false) String username, @RequestParam(required = false) String password, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isAnyBlank(username, password)) {
			return new RestLoginResult(Result.FAILURE, "请输入用户名或密码");
		}
		RestLoginResult restLoginResult = loginService.login(username, password);
		if (restLoginResult.getResult() == Result.SUCCESS) {
			ThreadLocalSession.setSessionData(restLoginResult.getSessionData());
			restLoginResult.setToken(generateJwtToken(restLoginResult.getSessionData()));
			response.setHeader("Access-Control-Expose-Headers", JwtAdminToken.TOKEN_HEADER);
			response.setHeader(JwtAdminToken.TOKEN_HEADER, restLoginResult.getToken());
			LOG.info("用户登录成功, sessionData={}", restLoginResult.getSessionData());
		}
		return restLoginResult;
	}

	// /**
	// * 员工重新登录
	// **/
	// @RequestMapping(value = "loginByToken", method = RequestMethod.POST)
	// public RestLoginResult loginByToken(@RequestParam(name = "token", required =
	// false) String token, HttpServletRequest request, HttpServletResponse
	// response) {
	// if (StringUtils.isBlank(token)) {
	// return new RestLoginResult(Result.FAILURE, "参数（token）不能为空");
	// }
	// RestLoginResult restLoginResult = loginService.loginByToken(token);
	// if (restLoginResult.getResult() == Result.SUCCESS) {
	// ThreadLocalSession.setSessionData(restLoginResult.getSessionData());
	// restLoginResult.setToken(generateJwtToken(restLoginResult.getSessionData()));
	// response.setHeader(JwtAdminToken.TOKEN_HEADER, restLoginResult.getToken());
	// LOG.info("用户重新登录成功, sessionData={}", restLoginResult.getSessionData());
	// }
	// return restLoginResult;
	// }

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "登出", notes = "登出")
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
	 * 供应商
	 **/
	@ApiOperation(value = "用户登录(供应商)", notes = "用户登录(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value = "loginForSupplier", method = RequestMethod.POST)
	public RestLoginResult loginForSupplier(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isAnyBlank(username, password)) {
			return new RestLoginResult(Result.FAILURE, "请输入用户名或密码");
		}
		RestLoginResult restLoginResult = loginService.loginForSupplier(username, password);
		if (restLoginResult.getResult() == Result.SUCCESS) {
			SessionData sessionData = restLoginResult.getSessionData();
			ThreadLocalSession.setSessionData(restLoginResult.getSessionData());
			restLoginResult.setToken(generateSupplierJwtToken(sessionData));
			response.setHeader("Access-Control-Expose-Headers", JwtSupplierToken.TOKEN_HEADER);
			response.setHeader(JwtSupplierToken.TOKEN_HEADER, restLoginResult.getToken());
			LOG.info("供应商登录成功, sessionData={}", restLoginResult.getSessionData());
		}
		return restLoginResult;
	}

	/**
	 * 用户-根据登陆信息生成JWT令牌
	 *
	 * @param sessionData
	 * @return
	 */
	@ApiOperation(value = "用户-根据登陆信息生成JWT令牌(总后台)", notes = "用户-根据登陆信息生成JWT令牌(总后台)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sessionData", value = "登陆信息", required = true, dataType = "SessionData"),
	})
	private String generateJwtToken(SessionData sessionData) {
		return JwtAdminToken.generateToken(sessionData.getId(), sessionData.getUserCode(), null, null);
	}

	/**
	 * 供应商-根据登陆信息生成JWT令牌
	 *
	 * @param sessionData
	 * @return
	 */
	@ApiOperation(value = "用户-根据登陆信息生成JWT令牌(供应商)", notes = "用户-根据登陆信息生成JWT令牌(供应商)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sessionData", value = "登陆信息", required = true, dataType = "SessionData"),
	})
	private String generateSupplierJwtToken(SessionData sessionData) {
		return JwtSupplierToken.generateToken(sessionData.getId(), sessionData.getUserCode(), null, null);
	}

	/**
	 * 获取登陆用户菜单
	 * @return
	 */
	@ApiOperation(value = "获取登陆用户菜单", notes = "获取登陆用户菜单")
	@RequestMapping(value = "getUserMenu",method = RequestMethod.GET)
	public  RestResult getUserMenu(){
		SessionData user=(SessionData)ThreadLocalSession.getSessionData();
		if(user!=null){
			UserVo dbUser = userService.getUserVoById(user.getId());
			return RestUtils.successWhenNotNull(dbUser);
		}
		return null;
	}


	/**
	 * 获取供应商基础信息
	 * @return
	 */
	@ApiOperation(value = "获取供应商基础信息", notes = "获取供应商基础信息")
	@RequestMapping(value = "geSupplierBasicInfo",method = RequestMethod.GET)
	public  RestResult geSupplierBasicInfo(){
		SessionData user=(SessionData)ThreadLocalSession.getSessionData();
		if(user!=null){
			SupplierToken token=new SupplierToken();
			token.setId(user.getId());

			SupplierVo dbUser = loginService.getLoginSupplier(token);
			return RestUtils.successWhenNotNull(dbUser);
		}
		return null;
	}
}