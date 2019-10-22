package com.yi.core.activity.domain.simple;

import com.yihz.common.convert.domain.SimpleDomain;

public class SalesAreaCommodityIdSimple extends SimpleDomain {
    private int salesAreaId;
    private int commodityId;

    public SalesAreaCommodityIdSimple() {
    }

    public SalesAreaCommodityIdSimple(int salesAreaId, int commodityId) {
        this.salesAreaId = salesAreaId;
        this.commodityId = commodityId;
    }

    public int getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(int salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
}
