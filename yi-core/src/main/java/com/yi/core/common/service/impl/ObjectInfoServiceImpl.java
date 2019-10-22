package com.yi.core.common.service.impl;

import static com.yi.core.utils.ObjectPathUtils.getPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.auth.dao.DeptDao;
import com.yi.core.auth.dao.UserDao;
import com.yi.core.auth.domain.entity.Dept;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.common.ObjectInfo;
import com.yi.core.common.ObjectType;
import com.yi.core.common.service.IObjectInfoService;
import com.yi.core.utils.ObjectPathUtils;

@Service
@Transactional
public class ObjectInfoServiceImpl implements IObjectInfoService {

	private final Logger LOG = LoggerFactory.getLogger(ObjectInfoServiceImpl.class);

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private UserDao userDao;

	@Override
	public Object getObject(ObjectType objectType, int objectId) {
		Object record = null;
		switch (objectType) {

		case DEPT:
			record = deptDao.getOne(objectId);
			break;

		case USER:
			record = userDao.getOne(objectId);
			break;

		default:
			LOG.warn("不支持的业务对象类型, {}", objectType);
			break;
		}
		return record;
	}

	@Override
	public ObjectInfo getObjectInfo(ObjectType objectType, int objectId) {
		ObjectInfo objectInfo = null;
		switch (objectType) {

		case DEPT:
			Dept dept = deptDao.getOne(objectId);
			if (dept != null) {
				objectInfo = new ObjectInfo(objectType, objectId, dept.getDeptName(), getPath(dept));
				objectInfo.setEntity(dept);
			}
			break;

		case USER:
			User user = userDao.getOne(objectId);
			if (user != null) {
				objectInfo = new ObjectInfo(objectType, objectId, user.getUsername(), getPath(user));
				objectInfo.setEntity(user);
			}
			break;

		default:
			LOG.warn("不支持的业务对象类型, {}", objectType);
			break;
		}

		return objectInfo;
	}

	@Override
	public ObjectInfo getObjectInfo(Object object) {
		if (object == null) {
			return null;
		}
		return ObjectPathUtils.getObjectInfo(object);
	}
}
