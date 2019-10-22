package com.yi.searchServer.web.search.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 商品规格
 */
public class CommoditySpec implements java.io.Serializable {

    @ApiModelProperty(value="规格名称",name="specName",example="排量")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String specName;

    @ApiModelProperty(value="规格值",name="value",example="2.0T")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String value;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
