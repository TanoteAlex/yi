package com.yi.adminserver.web.config.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yi.adminserver.web.auth.jwt.AdminToken;
import com.yi.adminserver.web.auth.jwt.JwtAdminToken;
import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.service.ILoginService;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;

/**
 * JWT
 * 
 * @author Administrator
 *
 */
@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

	private final Logger LOG = LoggerFactory.getLogger(JwtTokenInterceptor.class);

	private static Map<String, String> ADMIN_CACHE = new ConcurrentHashMap<>(0);

	private static Map<String, String> SUPPLIER_CACHE = new ConcurrentHashMap<>(0);

	@Autowired
	private ILoginService loginService;

	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 不拦截预检请求
		if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
			return true;
		}
		// 供应商
		if (request.getRequestURL().lastIndexOf("ForSupplier") > 0) {
			// 从请求头或参数中获取token
			String token = getSupplierTokenFromRequest(request);
			if (StringUtils.isBlank(token)) {
				response.setStatus(HttpStatus.SC_FORBIDDEN);
				return false;
			}
			try {
				boolean flag = JwtSupplierToken.verifyToken(token);
				if (!flag) {
					response.setStatus(HttpStatus.SC_FORBIDDEN);
					return false;
				}
				// token = JwtWebToken.updateToken(token);
				response.setHeader("Access-Control-Expose-Headers", JwtSupplierToken.TOKEN_HEADER);
				response.setHeader(JwtSupplierToken.TOKEN_HEADER, token);
				return true;
			} catch (Exception ex) {
				LOG.error("=====>JWT令牌处理失败", ex);
				response.setStatus(HttpStatus.SC_FORBIDDEN);
				return false;
			}
		} // 用户
		else {
			String token = getAdminTokenFromRequest(request);
			if (StringUtils.isBlank(token)) {
				return true;
			}
			try {
				AdminToken tk = JwtAdminToken.getToken(token);
				if (tk == null || StringUtils.isBlank(tk.getUserCode())) {
					return true;
				}
				SessionData sessionData = loginService.getSessionDataByToken(tk);
				if (sessionData == null) {
					return true;
				}
				request.setAttribute(sessionDataKey, sessionData);
				ThreadLocalSession.setSessionData(sessionData);

				token = JwtAdminToken.updateToken(token);
				response.setHeader(JwtAdminToken.TOKEN_HEADER, token);

			} catch (Exception ex) {
				LOG.info("JWT令牌处理失败", ex);
				return true;
			}
		}
		return true;
	}

	/**
	 * 从请求信息中获取token值
	 *
	 * @param request
	 *            request
	 * @return token值
	 */
	private String getAdminTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(JwtAdminToken.TOKEN_HEADER);
		if (StringUtils.isBlank(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(JwtAdminToken.TOKEN_HEADER);
			if (StringUtils.isBlank(token)) {
				token = ADMIN_CACHE.get(request.getSession().getId());
			} else {
				ADMIN_CACHE.put(request.getSession().getId(), token);
			}
		} else {
			ADMIN_CACHE.put(request.getSession().getId(), token);
		}
		return token;
	}

	/**
	 * 从请求信息中获取token值
	 *
	 * @param request
	 *            request
	 * @return token值
	 */
	private String getSupplierTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(JwtSupplierToken.TOKEN_HEADER);
		if (StringUtils.isBlank(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(JwtSupplierToken.TOKEN_HEADER);
			if (StringUtils.isBlank(token)) {
				token = SUPPLIER_CACHE.get(request.getSession().getId());
			} else {
				SUPPLIER_CACHE.put(request.getSession().getId(), token);
			}
		} else {
			SUPPLIER_CACHE.put(request.getSession().getId(), token);
		}
		return token;
	}

}