package com.yi.searchServer.web.search.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 品牌
 */

public class Brand implements java.io.Serializable {

    @ApiModelProperty(value="品牌ID",name="brandId",example="1")
    private int brandId;
    /**
     * 品牌编号
     */
    @ApiModelProperty(value="品牌编号",name="brandNo",example="xcode001")
    private String brandNo;
    /**
     * 品牌中文名称
     */
    @ApiModelProperty(value="品牌名称",name="brandNo",example="宝马")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String brandName;
    /**
     * 品牌英文名称
     */
    @ApiModelProperty(value="品牌英文名称",name="brandEnName",example="BMW")
    private String brandEnName;
    /**
     * 品牌标题
     */
    @ApiModelProperty(value="品牌标题",name="brandTitle",example="sheer driving pleasure")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String brandTitle;
    /**
     * 图片路径  150*58 png
     */
    @ApiModelProperty(value="图片路径",name="picPath",example="/pic/bmw.png")
    private String picPath;


    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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

    public String getBrandTitle() {
        return brandTitle;
    }

    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
