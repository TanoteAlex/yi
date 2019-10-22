package com.yi.core.stats.domain.vo;

public class DailyAddNumVo {

	private String date;

	private int DailyAddOrderNum;

	private int DailyAddMemberNum;
	
	private int DailyAddSupplierNum;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDailyAddOrderNum() {
		return DailyAddOrderNum;
	}

	public void setDailyAddOrderNum(int dailyAddOrderNum) {
		DailyAddOrderNum = dailyAddOrderNum;
	}

	public int getDailyAddMemberNum() {
		return DailyAddMemberNum;
	}

	public void setDailyAddMemberNum(int dailyAddMemberNum) {
		DailyAddMemberNum = dailyAddMemberNum;
	}
	
	public int getDailyAddSupplierNum() {
		return DailyAddSupplierNum;
	}

	public void setDailyAddSupplierNum(int dailyAddSupplierNum) {
		DailyAddSupplierNum = dailyAddSupplierNum;
	}

}
