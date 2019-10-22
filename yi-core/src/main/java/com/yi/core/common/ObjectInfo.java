package com.yi.core.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 业务要素的对象类型,对象ID和名称,以及实体对象
 */
public class ObjectInfo implements Serializable {
	/**
	 * 业务要素类型
	 */
	private ObjectType objectType;
	/**
	 * 业务要素ID
	 */
	private int objectId;
	/**
	 * 业务要素名称
	 */
	private String objectName;
	/**
	 * 业务要素路径
	 */
	private String objectPath;

	/**
	 * 实体对象, 使用时注意JPA Session问题
	 */
	@JsonIgnore
	private Object entity;

	public ObjectInfo() {
	}

	public ObjectInfo(ObjectType objectType, int objectId) {
		this.objectType = objectType;
		this.objectId = objectId;
	}

	public ObjectInfo(ObjectType objectType, int objectId, String objectName) {
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectName = objectName;
	}

	public ObjectInfo(ObjectType objectType, int objectId, String objectName, String objectPath) {
		this.objectType = objectType;
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectPath = objectPath;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	/**
	 * 业务要素对应的实体
	 *
	 * @return
	 */
	@JsonIgnore
	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("objectType", objectType).append("objectId", objectId).append("objectName", objectName).append("objectPath", objectPath).toString();
	}
}
