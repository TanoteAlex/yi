package com.yi.adminserver.web.auth.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.annotation.session.CurrentSessionData;

/**
 * 将含有 CurrentSessionData 注解的方法参数注入当前登录用户
 */
@Component
public class SessionDataMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 如果参数类型是 SessionData 并且有 CurrentSessionData 注解则支持
		if (parameter.getParameterType().isAssignableFrom(SessionData.class)
				&& parameter.hasParameterAnnotation(CurrentSessionData.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		SessionData sessionData = ThreadLocalSession.getSessionData();
		return sessionData;
	}
}