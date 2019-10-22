package com.yi.core.common;

import java.util.Date;

/**
 * 实体空值字段 统一赋值 如果需要系统处理，请继承该类
 * 并在{@See com.yi.core.config.interceptor.EntityEmptyInterceptor}实现自己的处理逻辑
 * 
 * @author xuyh
 *
 */
public interface CustomEntityEmpty {

	/** GUID */
	default String getGuid() {
		return null;
	}

	default void setGuid(String guid) {
	}

	/** 编码 */
	default String getCode() {
		return null;
	}

	default void setCode(String code) {
	}

	/** 创建时间 */
	default Date getCreateTime() {
		return null;
	}

	default void setCreateTime(Date createTime) {
	}

	/** 修改时间 */
	default Date getUpdateTime() {
		return null;
	}

	default void setUpdateTime(Date updateTime) {
	}

	/** 删除标志 */
	default Integer getDeleted() {
		return null;
	}

	default void setDeleted(Integer deleted) {
	}

	/** 删除时间 */
	default Date getDelTime() {
		return null;
	}

	default void setDelTime(Date delTime) {
	}

}
