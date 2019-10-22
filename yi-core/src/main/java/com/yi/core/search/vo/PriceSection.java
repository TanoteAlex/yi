package com.yi.core.search.vo;

/**
 * 商品价格段
 */
public class PriceSection implements java.io.Serializable {
    /**
     * 起始价格
     */
    private int startPrice;

    /**
     * 结束价格
     */
    private int endPrice;

    public PriceSection() {
    }

    public PriceSection(int startPrice, int endPrice) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }
}
