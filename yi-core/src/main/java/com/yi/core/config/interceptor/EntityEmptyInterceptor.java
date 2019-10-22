package com.yi.core.config.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.yi.core.basic.BasicEnum;
import com.yi.core.common.Deleted;
import com.yihz.common.utils.ValueUtils;

/**
 * 
 * hibernate 拦截器 通用字段赋值
 * 
 * @author xuyh
 *
 */
public class EntityEmptyInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4309422314499256850L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return updateEntityBySave(entity, state, propertyNames);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		return updateEntityByFlush(entity, currentState, previousState, propertyNames, types);
	}

	/**
	 * 通用字段赋值
	 * 
	 * @param entity
	 * @param state
	 * @param propertyNames
	 * @return
	 */
	private boolean updateEntityBySave(Object entity, Object[] state, String[] propertyNames) {
		boolean modified = false;
		// if (entity instanceof EntityOwner) {
		for (int i = 0; i < propertyNames.length; i++) {
			if (state[i] == null) {
				String propertyName = propertyNames[i];
				if ("guid".equals(propertyName)) {
					state[i] = ValueUtils.generateGUID();
					modified = true;
				} else if ("state".equals(propertyName)) {
					state[i] = BasicEnum.STATE_ENABLE.getCode();
					modified = true;
				} else if ("creator".equals(propertyName)) {
					// Member creator = loginService.getDbLoginUser();
					// state[i] = creator;
					// modified = true;
				} else if ("deleted".equals(propertyName)) {
					state[i] = Deleted.DEL_FALSE;
					modified = true;
				} else if ("createTime".equals(propertyName)) {
					state[i] = new Date();
					modified = true;
				}
			}
		}
		// }
		return modified;
	}

	/**
	 * 修改时 通用字段为空 赋值数据库值
	 * 
	 * @param entity
	 * @param currentState
	 * @param previousState
	 * @param propertyNames
	 * @return
	 */
	private boolean updateEntityByFlush(Object entity, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		boolean modified = false;
		for (int i = 0; i < propertyNames.length; i++) {
			if (currentState[i] == null) {
				String propertyName = propertyNames[i];
				if ("guid".equals(propertyName)) {
					currentState[i] = previousState[i];
					modified = true;
				} else if ("deleted".equals(propertyName)) {
					currentState[i] = previousState[i];
					modified = true;
				} else if ("createTime".equals(propertyName)) {
					currentState[i] = previousState[i];
					modified = true;
				}
			}
		}
		return modified;
	}

}
