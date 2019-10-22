package com.yi.core.search.vo.meta;

/**
 * Created by tanggangyi
 */
public enum SellerType {
    SELLER("商家"), SELF_OPERATION("自营");

    private String name;

    private SellerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
