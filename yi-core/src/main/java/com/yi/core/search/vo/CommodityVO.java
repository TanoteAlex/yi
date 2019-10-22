package com.yi.core.search.vo;

import com.yi.core.commodity.domain.entity.Commodity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.beanutils.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApiModel(value="商品搜索对象",description="商品搜索对象 CommodityVO")
public class CommodityVO implements java.io.Serializable {
    private static final long serialVersionUID = -3968560903203859821L;

    public CommodityVO() {
    }

    public CommodityVO(Commodity commodity,List levelPrices,BigDecimal returnVoucher) {
        try{
            this.commodityId = commodity.getId();
            this.commodityType = commodity.getCommodityType();
            this.commodityNo = commodity.getCommodityNo();
            this.commodityName = commodity.getCommodityName();
            this.commodityShortName = commodity.getCommodityShortName();
            this.description = commodity.getDescription();
            this.originalPrice = commodity.getOriginalPrice();
            this.currentPrice = commodity.getCurrentPrice();
            this.supplyPrice = commodity.getSupplyPrice();
            this.imgPath=commodity.getImgPath();
            this.freightSet= commodity.getFreightSet();
            this.unifiedFreight = commodity.getUnifiedFreight();

            this.discountInfo = commodity.getDiscountInfo();
            this.saleQuantity = commodity.getSaleQuantity();
            this.levelPrices = levelPrices;
            this.returnVoucher = returnVoucher;


            //商品分类
            commodity.getOperateCategories().forEach(commodityCategory -> {
                Category category = new Category();
                try {
                    BeanUtils.copyProperties(category, commodityCategory);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.categories.add(category);
            });
            //商品规格
//            commodity.getCommoditySpecs().forEach(commoditySpec -> {
//                CommoditySpec spec = new CommoditySpec(commoditySpec.getSpec().getSpecName(), commoditySpec.getValue());
//                this.commoditySpecs.add(spec);
//            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 商品ID
     */
    @ApiModelProperty(value="商品ID",name="commodityId",example="1")
    private int commodityId;

//    /**
//     * 商品所属价格段
//     */
//    @ApiModelProperty(value="价格段",name="priceSection",example="1000-19999")
//    private PriceSection priceSection;
//    /**
//     * 品牌
//     */
//    @ApiModelProperty(value="品牌",name="brand",example="品牌")
//    private Brand brand;
//    /**
//     * 供应商
//     */
//    @ApiModelProperty(value="商家",name="seller",example="商家")
//    private Seller seller;
    /**
     * 商品类型（0普通商品，1批发商品，2送礼商品，3限量商品）
     */
    private Integer commodityType;
    /**
     * 商品编码
     */
    @ApiModelProperty(value="商品编码",name="commodityNo",example="121233485")
    private String commodityNo;
    /**
     * 商品名
     */
    @ApiModelProperty(value="商品名",name="commodityName",example="BMWX1")
    private String commodityName;
    /**
     * 简称
     */
    @ApiModelProperty(value="简称",name="shortName",example="BMW")
    private String commodityShortName;
    /**
     * 描述
     */
    @ApiModelProperty(value="描述",name="description",example="1")
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
    @ApiModelProperty(value="创建时间",name="createTime",example="bmw.jpg")
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

//    /**
//     * 商品规格
//     */
//    @ApiModelProperty(value="商品规格",name="commoditySpecs",example="商品规格")
//    private Set<CommoditySpec> commoditySpecs = new HashSet<>();


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

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
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
