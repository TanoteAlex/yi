package com.yi.core.common;

/**
 * 业务要素选择时用的查询条件
 */
public class ObjectChooseQuery extends ChooseQueryBase {
	private int userId;
	private int deptId;
	private String state;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
