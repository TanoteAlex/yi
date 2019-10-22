package com.yi.core.auth.session;

import com.yi.core.auth.model.SessionData;
import com.yihz.common.utils.ThreadLocalUtils;

/**
 * 采用 ThreadLocal 保存 SessionData
 */
public final class ThreadLocalSession {

	private static ThreadLocal<SessionData> sessionDataHolder = ThreadLocalUtils.newInstance();

	private ThreadLocalSession() {
	}

	/**
	 * 清除当前会话信息
	 */
	public static void clear() {
		sessionDataHolder.remove();
	}

	/**
	 * 获取当前登录的用户ID, 未登录时返回0
	 *
	 * @return
	 */
	public static int getUserId() {
		SessionData sessionData = getSessionData();
		if (sessionData == null) {
			return 0;
		}

		return sessionData.getId();
	}

	/**
	 * 获取当前会话信息
	 *
	 * @return
	 */
	public static SessionData getSessionData() {
		return sessionDataHolder.get();
	}

	/**
	 * 设置当前会话信息
	 *
	 * @param data
	 */
	public static void setSessionData(SessionData data) {
		sessionDataHolder.set(data);
	}
}
