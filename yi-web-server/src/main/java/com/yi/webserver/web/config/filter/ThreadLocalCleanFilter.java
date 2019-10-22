package com.yi.webserver.web.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;

import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.utils.ThreadLocalUtils;

/**
 * @author
 */
@WebFilter(filterName = "threadLocalCleanFilter", urlPatterns = { "/*" })
public class ThreadLocalCleanFilter implements Filter {
	@Value("${yi.session.data.key:YI_SESSION_DATA}")
	private String sessionDataKey;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		ThreadLocalSession.clear();

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession(false);
		if (session != null) {
			SessionData data = (SessionData) session.getAttribute(sessionDataKey);
			if (data != null) {
				ThreadLocalSession.setSessionData(data);
			}
		}

		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			ThreadLocalUtils.clearAll();
		}
	}

	@Override
	public void destroy() {
	}
}
