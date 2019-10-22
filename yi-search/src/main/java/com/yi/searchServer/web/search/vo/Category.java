package com.yi.searchServer.web.search.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 运营分类
 */

public class Category implements java.io.Serializable {
    /**
     * ID
     */
    @ApiModelProperty(value="商品id",name="categoryId",example="1")
    private int categoryId;
    /**
     * 分类编号
     */
    @ApiModelProperty(value="分类编号",name="categoryNo",example="cate001")
    private String categoryNo;
    /**
     * 分类名称
     */
    @ApiModelProperty(value="分类名称",name="categoryName",example="汽车")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String categoryName;
    /**
     * 排序号
     */
    @ApiModelProperty(value="排序号",name="sort",example="1")
    private int sort;
    /**
     * 图标路径
     */
    @ApiModelProperty(value="商品分类图标路径",name="imgPath",example="/path/categorie.png")
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
