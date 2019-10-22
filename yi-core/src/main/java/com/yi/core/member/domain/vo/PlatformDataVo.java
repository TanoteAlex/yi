package com.yi.core.member.domain.vo;

import java.math.BigDecimal;

public class PlatformDataVo {
    //会员总数
    private int memberCount;
    //供应商数
    private int supplierCount;
    //订单数
    private int orderCount;
    //今日订单数
    private int nowOrderCount;
    //成交额
    private BigDecimal amountSum;
    //每周订单数
    private int weekOrderCount;
    //每月订单数
    private int monthOrderCount;

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getSupplierCount() {
        return supplierCount;
    }

    public void setSupplierCount(int supplierCount) {
        this.supplierCount = supplierCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getNowOrderCount() {
        return nowOrderCount;
    }

    public void setNowOrderCount(int nowOrderCount) {
        this.nowOrderCount = nowOrderCount;
    }

    public BigDecimal getAmountSum() {
        return amountSum;
    }

    public void setAmountSum(BigDecimal amountSum) {
        this.amountSum = amountSum;
    }

    public int getWeekOrderCount() {
        return weekOrderCount;
    }

    public void setWeekOrderCount(int weekOrderCount) {
        this.weekOrderCount = weekOrderCount;
    }

    public int getMonthOrderCount() {
        return monthOrderCount;
    }

    public void setMonthOrderCount(int month) {
        this.monthOrderCount = month;
    }
}
