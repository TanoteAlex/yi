package com.yi.webserver.web.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yi.webserver.web.auth.jwt.JwtWebToken;
import com.yi.webserver.web.auth.jwt.WebToken;
import com.yi.webserver.web.auth.service.ILoginService;

@Component
public class JwtTokenInterceptor extends HandlerInterceptorAdapter {
	private final Logger LOG = LoggerFactory.getLogger(JwtTokenInterceptor.class);

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
		// 会员
		String token = getTokenFromRequest(request);
		if (StringUtils.isBlank(token)) {
			return true;
		}
		try {
			WebToken tk = JwtWebToken.getToken(token);
			if (tk == null || StringUtils.isBlank(tk.getUserCode())) {
				return true;
			}

			SessionData sessionData = loginService.getSessionDataByToken(tk);
			if (sessionData == null) {
				return true;
			}

			request.setAttribute(sessionDataKey, sessionData);
			ThreadLocalSession.setSessionData(sessionData);

			token = JwtWebToken.updateToken(token);
			response.setHeader(JwtWebToken.TOKEN_HEADER, token);

		} catch (Exception ex) {
			LOG.info("JWT令牌处理失败", ex);
			return true;
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
	private String getTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(JwtWebToken.TOKEN_HEADER);

		if (StringUtils.isBlank(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(JwtWebToken.TOKEN_HEADER);
		}
		return token;
	}
}