package com.yi.core.common;

import java.sql.Date;

/**
 * 删除标识
 * 
 * @author xuyh
 *
 */
// @SoftDelete(property = "deleted", timestamp = "delTime", type =
// Integer.class, value = "1", notValue = "0")
public interface Deleted {

	/** 未删除 */
	public static final Integer DEL_FALSE = 0;
	/** 已删除 */
	public static final Integer DEL_TRUE = 1;
	/** 模拟数据 */
	public static final Integer DEL_MOCK = 2;

	public Integer getDeleted();

	public void setDeleted(Integer deleted);

	public Date getDelTime();

	public void setDelTime(Date delTime);

}
