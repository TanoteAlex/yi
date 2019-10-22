package com.yi.webserver.web.auth.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.session.ThreadLocalSession;
import com.yihz.common.annotation.session.CurrentUser;

/**
 * 将含有 CurrentUser 注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

//    @Autowired
//    private IPCStaffService staffService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是 Staff 并且有 CurrentStaff 注解则支持
        if (parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SessionData sessionData = ThreadLocalSession.getSessionData();
        if (sessionData == null) {
            return null;
        }

//        Staff staff = staffService.getById(sessionData.getStaffId());
//        return staff;
        return null;
    }
}