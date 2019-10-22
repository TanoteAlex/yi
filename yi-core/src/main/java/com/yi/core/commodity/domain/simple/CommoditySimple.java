/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.domain.simple;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.yi.core.attachment.domain.vo.AttachmentVo;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.order.domain.simple.FreightTemplateConfigSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 商品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public class CommoditySimple extends SimpleDomain implements java.io.Serializable {

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
	 * 销售数量
	 */
	private Integer saleQuantity;

	/**
	 * 评论数量
	 */
	private Integer commentQuantity;

	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	 * 图片附件集合
	 */
	private List<AttachmentVo> attachmentVos;

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


	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public BigDecimal getIntegralRate() {
		return integralRate;
	}

	public void setIntegralRate(BigDecimal integralRate) {
		this.integralRate = integralRate;
	}


	public CategorySimple getCategory() {
		return category;
	}

	public void setCategory(CategorySimple category) {
		this.category = category;
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

	public List<AttachmentVo> getAttachmentVos() {
		return attachmentVos;
	}

	public void setAttachmentVos(List<AttachmentVo> attachmentVos) {
		this.attachmentVos = attachmentVos;
	}

}