package com.yi.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yi.core.auth.domain.entity.Dept;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.common.ObjectInfo;
import com.yi.core.common.ObjectType;

public class ObjectPathUtils {
	private final static Logger LOG = LoggerFactory.getLogger(ObjectPathUtils.class);
	/**
	 * 业务对象路径分割符
	 */
	private final static String pathSeparator = "/";

	private ObjectPathUtils() {
	}

	/**
	 * 将业务要素类型字符串转换成枚举对象
	 *
	 * @param strObjectType
	 * @return
	 */
	public static ObjectType toObjectType(String strObjectType) {
		try {
			String name = StringUtils.upperCase(StringUtils.trim(strObjectType));
			ObjectType objectType = ObjectType.valueOf(name);
			return objectType;

		} catch (Exception ex) {
			LOG.warn("无法识别的业务要素类型字符串 {}", strObjectType);
			return null;
		}
	}

	/**
	 * 获取对象的业务要素类型和ID
	 *
	 * @param object
	 * @return
	 */
	public static ObjectInfo getObjectInfo(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof Dept) {
			Dept dept = (Dept) object;
			ObjectInfo objectInfo = new ObjectInfo(ObjectType.DEPT, dept.getId(), dept.getDeptName(), getPath(dept));
			objectInfo.setEntity(object);
			return objectInfo;
		}

		if (object instanceof User) {
			User user = (User) object;
			ObjectInfo objectInfo = new ObjectInfo(ObjectType.USER, user.getId(), user.getUsername(), getPath(user));
			objectInfo.setEntity(object);
			return objectInfo;
		}

		LOG.warn("无法识别的业务对象, {}", object);
		return null;
	}

	/**
	 * 获取部门的对象路径
	 *
	 * @param dept
	 * @return
	 */
	public static String getPath(Dept dept) {
		return concatObjectPath(ObjectType.DEPT, dept.getId());
	}

	/**
	 * 获取用户的对象路径
	 *
	 * @param user
	 * @return
	 */
	public static String getPath(User user) {
		return concatObjectPath(ObjectType.USER, user.getId());
	}

	/**
	 * 将给定的内容用路径分隔符拼接成业务对象路径<br>
	 * (val1,val2,val3,valu4) => /val1/val2/val3/val4
	 *
	 * @param value
	 * @param values
	 * @return
	 */
	public static String concatObjectPath(Object value, Object... values) {
		StringBuilder buf = new StringBuilder();

		String str = value.toString();
		if (!StringUtils.startsWith(str, pathSeparator)) {
			buf.append(pathSeparator);
		}
		buf.append(str);

		if (values == null || values.length == 0) {
			return buf.toString();
		}

		for (Object val : values) {
			if (val == null) {
				continue;
			}

			if (val.getClass().isEnum()) {
				str = ((Enum) val).name();

			} else {
				str = val.toString();
			}

			if (!StringUtils.startsWith(str, pathSeparator) && !StringUtils.endsWith(buf, pathSeparator)) {
				buf.append(pathSeparator);
			}

			buf.append(str);
		}

		return buf.toString();
	}
}
