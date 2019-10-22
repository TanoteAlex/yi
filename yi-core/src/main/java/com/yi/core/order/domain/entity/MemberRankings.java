package com.yi.core.order.domain.entity;

public class MemberRankings {
    /**
     * 用户名
     */
    private String username;

    /**
     * 订单数
     */
    private int orderNumber;


    /**
     *交易额
     */
    private int transactionQuota;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTransactionQuota() {
        return transactionQuota;
    }

    public void setTransactionQuota(int transactionQuota) {
        this.transactionQuota = transactionQuota;
    }
}
