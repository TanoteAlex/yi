package com.yi.searchServer.web.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商品
 *
 * @author
 */
@Document(indexName = "yi", type = "commodity", createIndex = false)
public class Commodity implements java.io.Serializable {

    public static final String index_name = "yi";
    public static final String index_type = "commodity";

    private static final long serialVersionUID = -3993560903203859821L;

    @Id
    private String id;

    /**
     * 商品ID
     */
    private int commodityId;
    /**
     * 商品编码
     */
    private String commodityNo;
    /**
     * 商品名
     */
    @Field(type = FieldType.Text, fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String commodityName;
    /**
     * 简称
     */
    @Field(type = FieldType.Text, fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String commodityShortName;
    /**
     * 描述
     */
    @Field(type = FieldType.Text, fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String description;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 现价
     */
    private BigDecimal currentPrice;
    /**
     * 会员价
     */
    private BigDecimal supplyPrice;

    /**
     * 图片路径
     */
    private String imgPath;

    /**
     * 返代金券
     */
    private BigDecimal returnVoucher;

    /**
     * 优惠信息
     */
    private String discountInfo;

    /**
     * 等级价格
     */
    private List<Object> levelPrices;

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
    private Set<com.yi.searchServer.web.search.vo.Category> categories = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Set<com.yi.searchServer.web.search.vo.Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<com.yi.searchServer.web.search.vo.Category> categories) {
        this.categories = categories;
    }
}
