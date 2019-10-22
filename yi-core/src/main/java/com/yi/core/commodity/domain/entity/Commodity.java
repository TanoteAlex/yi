/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.cms.domain.entity.RecommendCommodity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 商品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class Commodity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * setCommodities 商品ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 商品类型（0普通商品，1批发商品，2送礼商品，3限量商品）
	 */
	private Integer commodityType;
	/**
	 * 商品编码
	 */
	@Length(max = 32)
	private String commodityNo;
	/**
	 * 商品名称
	 */
	@Length(max = 64)
	private String commodityName;

	/**
	 * 商品简称
	 */
	@Length(max = 64)
	private String commodityShortName;
	/**
	 * 图片路径
	 */
	@Length(max = 255)
	private String imgPath;
	/**
	 * 商品分类（category表ID）
	 */
	@NotNull
	private Category category;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	/**
	 * 排序
	 */
	@Max(127)
	private int sort;
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
	 * vip价格
	 *
	 * @return
	 */
	private BigDecimal vipPrice;
	/**
	 * 优惠信息
	 */
	private String discountInfo;
	/**
	 * 是否参与分销(0参加1不参加)
	 */
	@NotNull
	private Integer distribution;
	/**
	 * 佣金比例
	 */
	private BigDecimal commissionRate;

	/**
	 * 积分比例
	 */
	private BigDecimal integralRate;

	/**
	 * 运费设置（1统一运费2运费模板）
	 */
	private Integer freightSet;
	/**
	 * 统一运费
	 */
	private BigDecimal unifiedFreight;
	/**
	 * 运费模板（freight_template表ID）
	 */
	private FreightTemplateConfig freightTemplate;
	/**
	 * 库存设置（1下单减库存2支付减库存）
	 */
	private Integer stockSet;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 重量
	 */
	private BigDecimal weight;
//	/**
//	 * 是否上架（1立即上架2放入仓库）
//	 *
//	 * @see shelfState
//	 */
//	@Deprecated
//	private Integer shelf;
//	/**
//	 * 商品上架审批（1、申请上架 2、同意上架 3、拒绝上架）
//	 *
//	 * @see auditState
//	 */
//	@Deprecated
//	private Integer state;
	/**
	 * 上架状态（1立即上架2放入仓库）
	 */
	private Integer shelfState;
	/**
	 * 审核状态（1待审核2审核通过3审核拒绝）
	 */
	private Integer auditState;
	/**
	 * 商品介绍
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;

	/**
	 * 销售数量
	 */
	private Integer saleQuantity;

	/**
	 * 评论数量
	 */
	private Integer commentQuantity;

	/**
	 * 代金券发放方案
	 */
	private CouponGrantConfig couponGrantConfig;
	// columns END

	/**
	 * 商品运营分类集合
	 */
	private Set<OperateCategory> operateCategories;
	/**
	 * 货品集合
	 */
	private List<Product> products;
	/**
	 * 商品评价集合
	 */
	private Set<Comment> comments;
	/**
	 * 商品销售地区表
	 */
	private Set<Region> regions;

	/**
	 * 商品会员折扣集合
	 */
	private List<CommodityLevelDiscount> commodityLevelDiscounts;
	/**
	 * 商品属性组集合</br>
	 * 临时用字段 非数据库字段
	 */
	private Set<AttributeGroup> attributeGroups;

	private Set<RecommendCommodity> recommendCommodities = new HashSet<>(0);

	/**
	 * 定位查询字段</br>
	 * 临时用字段 非数据库字段
	 */
	private String city;

	public Commodity() {
	}

	public Commodity(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, updatable = false, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(Integer commodityType) {
		this.commodityType = commodityType;
	}

	@Column(unique = false, nullable = true, length = 32)
	public String getCommodityNo() {
		return this.commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@Column(unique = false, nullable = true, length = 64)
	public String getCommodityShortName() {
		return commodityShortName;
	}

	@Column(unique = false, nullable = true, length = 64)
	public void setCommodityShortName(String commodityShortName) {
		this.commodityShortName = commodityShortName;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CATEGORY_ID", nullable = false) })
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID") })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(unique = false, nullable = true, length = 3)
	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(length = 15)
	public BigDecimal getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(length = 15)
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Column(length = 15)
	public BigDecimal getSupplyPrice() {
		return this.supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	@Column(unique = false, nullable = true, length = 255)
	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getDistribution() {
		return this.distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCommissionRate() {
		return this.commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getFreightSet() {
		return this.freightSet;
	}

	public void setFreightSet(Integer freightSet) {
		this.freightSet = freightSet;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getUnifiedFreight() {
		return this.unifiedFreight;
	}

	public void setUnifiedFreight(BigDecimal unifiedFreight) {
		this.unifiedFreight = unifiedFreight;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "FREIGHT_TEMPLATE_ID", nullable = true) })
	public FreightTemplateConfig getFreightTemplate() {
		return this.freightTemplate;
	}

	public void setFreightTemplate(FreightTemplateConfig freightTemplate) {
		this.freightTemplate = freightTemplate;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getStockSet() {
		return this.stockSet;
	}

	public void setStockSet(Integer stockSet) {
		this.stockSet = stockSet;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

//	@Column(unique = false, nullable = true, length = 0)
//	public Integer getShelf() {
//		return this.shelf;
//	}
//
//	public void setShelf(Integer shelf) {
//		this.shelf = shelf;
//	}
//
//	@Column(unique = false, nullable = true, length = 0)
//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getShelfState() {
		return shelfState;
	}

	public void setShelfState(Integer shelfState) {
		this.shelfState = shelfState;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	@Column(unique = false, nullable = true, length = 0)
	public Integer getCommentQuantity() {
		return commentQuantity;
	}

	public void setCommentQuantity(Integer commentQuantity) {
		this.commentQuantity = commentQuantity;
	}

	@Column(unique = false, nullable = true)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, updatable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

//	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "commodity")
	@Where(clause = "deleted=0")
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> product) {
		this.products = product;
	}

//	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "commodity_operate_category", joinColumns = @JoinColumn(name = "COMMODITY_ID"), inverseJoinColumns = @JoinColumn(name = "OPERATE_CATEGORY_ID"))
	public Set<OperateCategory> getOperateCategories() {
		return operateCategories;
	}

	public void setOperateCategories(Set<OperateCategory> operateCategories) {
		this.operateCategories = operateCategories;
	}

//	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "commodity")
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

//	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "commodity_region", joinColumns = @JoinColumn(name = "COMMODITY_ID"), inverseJoinColumns = @JoinColumn(name = "REGION_ID"))
	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(BigDecimal integralRate) {
		this.integralRate = integralRate;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

//	@Fetch(FetchMode.SUBSELECT)
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "commodity")
	public List<CommodityLevelDiscount> getCommodityLevelDiscounts() {
		return commodityLevelDiscounts;
	}

	public void setCommodityLevelDiscounts(List<CommodityLevelDiscount> commodityLevelDiscounts) {
		this.commodityLevelDiscounts = commodityLevelDiscounts;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COUPON_GRANT_CONFIG_ID", nullable = true) })
	public CouponGrantConfig getCouponGrantConfig() {
		return couponGrantConfig;
	}

	public void setCouponGrantConfig(CouponGrantConfig couponGrantConfig) {
		this.couponGrantConfig = couponGrantConfig;
	}

	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "commodity")
	public Set<RecommendCommodity> getRecommendCommodities() {
		return recommendCommodities;
	}

	public void setRecommendCommodities(Set<RecommendCommodity> recommendCommodities) {
		this.recommendCommodities = recommendCommodities;
	}

	@Transient
	public Set<AttributeGroup> getAttributeGroups() {
		return attributeGroups;
	}

	public void setAttributeGroups(Set<AttributeGroup> attributeGroups) {
		this.attributeGroups = attributeGroups;
	}

	@Transient
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}