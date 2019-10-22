package com.yi.core.search.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

@ApiModel(value="商品搜索对象",description="商品搜索对象 SearchVo")
public class SearchVo implements java.io.Serializable {

    private static final long serialVersionUID = -3123560903203859821L;

    @ApiModelProperty(value="关键词",name="keyword",example="hello")
    private String keyword;

    @ApiModelProperty(value="分类ID",name="categoryId",example="1")
    private int categoryId;

    @ApiModelProperty(value="最低销售价",name="lowestPrice",example="50")
    private int lowestPrice;

    @ApiModelProperty(value="最高销售价",name="highestPrice",example="500")
    private int highestPrice;

    @ApiModelProperty(value="价格段",name="priceSection",example="价格段")
    private PriceSection priceSection;

    @ApiModelProperty(value="城市编码",name="cityCode",example="440300")
    private String cityCode;

    @ApiModelProperty(value="排序字段",name="sortBy",example="sellPrice")
    private String sortBy;

    @ApiModelProperty(value="排序方向，枚举:升序or降序",name="direction",example="ASC|DESC")
    private Sort.Direction direction;

    @NotNull(message = "页大小pageSize不能为空")
    @ApiModelProperty(value="返回行数",name="pageSize",example="10", required = true)
    private Integer pageSize;

    @NotNull(message = "页码page不能为空")
    @ApiModelProperty(value="页码",name="page",example="0", required = true)
    private Integer page;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(int lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    public PriceSection getPriceSection() {
        return priceSection;
    }

    public void setPriceSection(PriceSection priceSection) {
        this.priceSection = priceSection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

}
