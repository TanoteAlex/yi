package com.yi.adminserver.web.config.interceptor;

import static com.yihz.common.utils.web.WebUtils.HEADER_CACHE_CONTROL;
import static com.yihz.common.utils.web.WebUtils.HEADER_EXPIRES;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.annotation.session.LoginRequired;
import com.yihz.common.annotation.session.LoginType;

/**
 * 检查用户登录, 只要在方法上使用 LoginRequired 注解即可，可以跳转到指定页面或者输出JSON，JS
 */
@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

	private static final String defaultUrl = "${contextPath}/login.html";
	private static final String defaultJSON = "{\"result\":\"FAILURE\",\"code\":\"notLogin\",\"message\":\"用户未登录.\"}";
	private static final String defaultJS = "YI.showLogin();";

	@Value("${yi.login.require.url:}")
	private String redirectUrl;

	@Value("${yi.login.require.json:")
	private String jsonBody;

	@Value("${yi.login.require.js:")
	private String jsBody;

	@Value("${yi.login.require.header.name:RELOGIN")
	private String header;

	@Value("${yi.login.require.header.content:")
	private String headerContent;

	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
			return true;
		}

		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		LoginRequired annotation = method.getAnnotation(LoginRequired.class);
		if (annotation == null) {
			annotation = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
		}

		if (annotation == null || !annotation.required()) {
			return true;
		}

		SessionData sessionData = (SessionData) request.getAttribute(sessionDataKey);
		if (sessionData == null) {
			sessionData = ThreadLocalSession.getSessionData();
		}

		if (sessionData != null) {
			return true;
		}

		LoginType loginType = annotation.type();
		if (loginType == LoginType.JSON) {
			String body = annotation.body();
			if (StringUtils.isBlank(body)) {
				body = defaultJSON;
			}
			writeBody(response, "application/json; charset=utf-8", body);
			return false;
		}

		if (loginType == LoginType.JS) {
			String body = annotation.body();
			if (StringUtils.isBlank(body)) {
				body = defaultJS;
			}
			writeBody(response, "application/javascript; charset=utf-8", body);
			return false;
		}

		if (loginType == LoginType.HTML) {
			String body = annotation.body();
			writeBody(response, "text/html; charset=utf-8", body);
			return false;
		}

		if (loginType == LoginType.URL) {
			// 跳转到指定的登录URL
			response.sendRedirect(redirectUrl);
			return false;
		}

		return true;
	}

	/**
	 * 输出登录要求信息到客户端
	 *
	 * @param response
	 * @param contentType
	 * @param body
	 * @throws IOException
	 */
	private void writeBody(HttpServletResponse response, String contentType, String body) throws IOException {
		response.setHeader(HEADER_CACHE_CONTROL, "no-cache");
		response.setHeader("Prama", "no-cache");
		response.setDateHeader(HEADER_EXPIRES, 0);

		if (StringUtils.isNotBlank(header)) {
			response.setHeader(header, headerContent);
		}

		if (StringUtils.isNotBlank(body)) {
			response.setCharacterEncoding("utf-8");
			response.setContentType(contentType);

			PrintWriter writer = response.getWriter();
			writer.write(body);
			writer.flush();
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
		ServletContext servletContext = webApplicationContext.getServletContext();

		String contextPath = servletContext.getContextPath();
		if (StringUtils.equals(contextPath, "/")) {
			contextPath = "";
		}

		String url = redirectUrl;
		if (StringUtils.isBlank(url)) {
			url = defaultUrl;
		}
		this.redirectUrl = StringUtils.replace(url, "${contextPath}", contextPath);
	}
}
