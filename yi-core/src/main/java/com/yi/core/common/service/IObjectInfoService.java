package com.yi.core.common.service;

import com.yi.core.common.ObjectInfo;
import com.yi.core.common.ObjectType;

public interface IObjectInfoService {
	/**
	 * 获取业务要素实体对象
	 *
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	Object getObject(ObjectType objectType, int objectId);

	/**
	 * 根据类型和ID获取业务要素信息
	 *
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	ObjectInfo getObjectInfo(ObjectType objectType, int objectId);

	/**
	 * 根据实体对象获取业务要素信息
	 *
	 * @param object
	 * @return
	 */
	ObjectInfo getObjectInfo(Object object);
}
