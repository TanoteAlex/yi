package com.yi.searchServer.web.search.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApiModel(value="商品对象",description="商品对象 CommodityVO")
public class CommodityVO implements java.io.Serializable {
    private static final long serialVersionUID = -3968560903203859821L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value="商品ID",name="commodityId",example="1")
    private int commodityId;

    /**
     * 商品编码
     */
    @ApiModelProperty(value="商品编码",name="commodityNo",example="SN123456")
    private String commodityNo;
    /**
     * 商品名
     */
    @ApiModelProperty(value="商品名",name="commodityName",example="宝马X5001")
    private String commodityName;

    /**
     * 简称
     */
    @ApiModelProperty(value="简称",name="shortName",example="BMW")
    private String commodityShortName;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述",name="description",example="宝马X5001 ....")
    private String description;
    /**
     * 原价
     */
    @ApiModelProperty(value="原价",name="originalPrice",example="239888")
    private BigDecimal originalPrice;
    /**
     * 现价
     */
    @ApiModelProperty(value="现价",name="currentPrice",example="239888")
    private BigDecimal currentPrice;
    /**
     * 会员价
     */
    @ApiModelProperty(value="会员价",name="supplyPrice",example="239888")
    private BigDecimal supplyPrice;

    /**
     * 图片路径
     */
    @ApiModelProperty(value="图片路径",name="imgPath",example="bmw.jpg")
    private String imgPath;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",name="createTime",example="2018-07-27")
    private Date createTime;

    /**
     * 返代金券
     */
    private BigDecimal returnVoucher;

    /**
     * 等级价格
     */
    private List<Object> levelPrices;

    /**
     * 优惠信息
     */
    private String discountInfo;

    /**
     * 运费设置（1统一运费2运费模板）
     */
    private Integer freightSet;
    /**
     * 统一运费
     */
    private BigDecimal unifiedFreight;
    /**
     * 销售数量
     */
    private Integer saleQuantity;
    /**
     * 商品分类
     */
    @ApiModelProperty(value="商品分类",name="categories",example="分类")
    private Set<Category> categories = new HashSet<>();

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityShortName() {
        return commodityShortName;
    }

    public void setCommodityShortName(String commodityShortName) {
        this.commodityShortName = commodityShortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getReturnVoucher() {
        return returnVoucher;
    }

    public void setReturnVoucher(BigDecimal returnVoucher) {
        this.returnVoucher = returnVoucher;
    }

    public List<Object> getLevelPrices() {
        return levelPrices;
    }

    public void setLevelPrices(List<Object> levelPrices) {
        this.levelPrices = levelPrices;
    }

    public Integer getFreightSet() {
        return freightSet;
    }

    public void setFreightSet(Integer freightSet) {
        this.freightSet = freightSet;
    }

    public BigDecimal getUnifiedFreight() {
        return unifiedFreight;
    }

    public void setUnifiedFreight(BigDecimal unifiedFreight) {
        this.unifiedFreight = unifiedFreight;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public Integer getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Integer saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
