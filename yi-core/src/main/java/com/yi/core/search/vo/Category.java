package com.yi.core.search.vo;

/**
 * 运营分类
 */

public class Category implements java.io.Serializable {
    /**
     * ID
     */

    private int categoryId;
    /**
     * 分类编号
     */
    private String categoryNo;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 图标路径
     */
    private String imgPath;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
