package com.yi.core.search.vo;

/**
 * 品牌
 */

public class Brand implements java.io.Serializable {

    private int id;
    /**
     * 品牌编号
     */
    private String brandNo;
    /**
     * 品牌中文名称
     */
    private String brandName;
    /**
     * 品牌英文名称
     */
    private String brandEnName;

    /**
     * 图片路径  150*58 png
     */
    private String imgPath;

    public Brand() {
    }

    public Brand(com.yi.core.commodity.domain.entity.Brand brand) {
        this.id = brand.getId();
        this.brandName = brand.getCnName();
        this.brandEnName = brand.getEnName();
        this.brandNo = brand.getBrandNo();
        this.imgPath = brand.getImgPath();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandEnName() {
        return brandEnName;
    }

    public void setBrandEnName(String brandEnName) {
        this.brandEnName = brandEnName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
