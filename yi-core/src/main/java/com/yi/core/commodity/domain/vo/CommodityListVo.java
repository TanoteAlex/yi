/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.simple.CategorySimple;
import com.yi.core.order.domain.simple.FreightTemplateConfigSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.ListVoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 商品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CommodityListVo extends ListVoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 商品ID
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
	private CategorySimple category;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
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
	 * 销售数量
	 */
	private Integer saleQuantity;

	/**
	 * 评论数量
	 */
	private Integer commentQuantity;
	/**
	 * 佣金比例
	 */
	private BigDecimal commissionRate;
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
	private FreightTemplateConfigSimple freightTemplate;
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
//	 */
//	@Deprecated
//	private Integer shelf;
//	/**
//	 * 商品上架审批（1、申请上架 2、同意上架 3、拒绝上架）
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
	@Length(max = 65535)
	private String description;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	// /**
	// * 商品分类集合
	// */
	// @Deprecated
	// private Set<OperateCategorySimple> operateCategories;
	// /**
	// * 商品属性组集合
	// */
	// private Set<AttributeGroupSimple> attributeGroups;
	// /**
	// * 货品集合
	// */
	// private Set<ProductSimple> products;
	// /**
	// * 商品评价集合
	// */
	// private Set<CommentSimple> comments;
	// /**
	// * 商品销售地区表
	// */
	// private Set<RegionSimple> regions;

	// /**
	// * 图片附件集合
	// */
	// private List<AttachmentVo> attachmentVos;

	/**
	 * 积分比例
	 */
	private BigDecimal integralRate;

	// /**
	// * 商品会员折扣集合
	// */
	// private List<CommodityLevelDiscountSimple> commodityLevelDiscounts;

	/**
	 * 返代金券
	 */
	private BigDecimal returnVoucher;

	/**
	 * 等级价格
	 */
	private List<Object> levelPrices;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCommodityNo() {
		return this.commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		return this.commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public SupplierSimple getSupplier() {
		return this.supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Integer getDistribution() {
		return this.distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	public BigDecimal getCommissionRate() {
		return this.commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Integer getFreightSet() {
		return this.freightSet;
	}

	public void setFreightSet(Integer freightSet) {
		this.freightSet = freightSet;
	}

	public BigDecimal getUnifiedFreight() {
		return this.unifiedFreight;
	}

	public void setUnifiedFreight(BigDecimal unifiedFreight) {
		this.unifiedFreight = unifiedFreight;
	}

	public FreightTemplateConfigSimple getFreightTemplate() {
		return this.freightTemplate;
	}

	public void setFreightTemplate(FreightTemplateConfigSimple freightTemplate) {
		this.freightTemplate = freightTemplate;
	}

	public Integer getStockSet() {
		return this.stockSet;
	}

	public void setStockSet(Integer stockSet) {
		this.stockSet = stockSet;
	}

	public BigDecimal getVolume() {
		return this.volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

//	public Integer getShelf() {
//		return this.shelf;
//	}
//
//	public void setShelf(Integer shelf) {
//		this.shelf = shelf;
//	}
//	
//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public String getCommodityShortName() {
		return commodityShortName;
	}

	public void setCommodityShortName(String commodityShortName) {
		this.commodityShortName = commodityShortName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	// public Set<OperateCategorySimple> getOperateCategories() {
	// return operateCategories;
	// }
	//
	// public void setOperateCategories(Set<OperateCategorySimple>
	// operateCategories) {
	// this.operateCategories = operateCategories;
	// }

	// public Set<AttributeGroupSimple> getAttributeGroups() {
	// return attributeGroups;
	// }
	//
	// public void setAttributeGroups(Set<AttributeGroupSimple> attributeGroups) {
	// this.attributeGroups = attributeGroups;
	// }

	// public Set<ProductSimple> getProducts() {
	// return products;
	// }
	//
	// public void setProducts(Set<ProductSimple> products) {
	// this.products = products;
	// }

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

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	// public Set<CommentSimple> getComments() {
	// return comments;
	// }
	//
	// public void setComments(Set<CommentSimple> comments) {
	// this.comments = comments;
	// }

	// public Set<RegionSimple> getRegions() {
	// return regions;
	// }
	//
	// public void setRegions(Set<RegionSimple> regions) {
	// this.regions = regions;
	// }

	// public List<AttachmentVo> getAttachmentVos() {
	// return attachmentVos;
	// }
	//
	// public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
	// this.attachmentVos = attachmentVos;
	// }

	public BigDecimal getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(BigDecimal integralRate) {
		this.integralRate = integralRate;
	}

	public BigDecimal getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}

	// public List<CommodityLevelDiscountSimple> getCommodityLevelDiscounts() {
	// return commodityLevelDiscounts;
	// }
	//
	// public void setCommodityLevelDiscounts(List<CommodityLevelDiscountSimple>
	// commodityLevelDiscounts) {
	// this.commodityLevelDiscounts = commodityLevelDiscounts;
	// }

	public CategorySimple getCategory() {
		return category;
	}

	public void setCategory(CategorySimple category) {
		this.category = category;
	}

	public Integer getShelfState() {
		return shelfState;
	}

	public void setShelfState(Integer shelfState) {
		this.shelfState = shelfState;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(Integer commodityType) {
		this.commodityType = commodityType;
	}

	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Integer getCommentQuantity() {
		return commentQuantity;
	}

	public void setCommentQuantity(Integer commentQuantity) {
		this.commentQuantity = commentQuantity;
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
}