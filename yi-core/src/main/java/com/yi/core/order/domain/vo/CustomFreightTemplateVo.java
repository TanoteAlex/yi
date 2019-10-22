/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.basic.domain.simple.RegionSimple;
import com.yi.core.order.domain.simple.FreightTemplateConfigSimple;
import com.yihz.common.convert.domain.VoDomain;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class CustomFreightTemplateVo extends VoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 自定义运费模板ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 运费模板配置（freight_template_config表ID）
	 */
	private FreightTemplateConfigSimple freightTemplateConfig;
	/**
	 * 运送方式（1快递，2EMS，3平邮）
	 */
	@NotNull
	@Max(127)
	private Integer deliveryMode;
	/**
	 * 默认（0非默认1默认）
	 */
	@Max(127)
	private Integer defaulted;
	/**
	 * 首件/重/体积
	 */
	@Max(999999999999999L)
	private BigDecimal firstQuantity;
	/**
	 * 首费
	 */
	@Max(999999999999999L)
	private BigDecimal firstFee;
	/**
	 * 续件/重/体积
	 */
	@Max(999999999999999L)
	private BigDecimal extraQuantity;
	/**
	 * 续费
	 */
	@Max(999999999999999L)
	private BigDecimal extraFee;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonSerialize(using = JsonTimestampSerializer.class)
	private Date delTime;
	// columns END

	/**
	 * 运费模板地区
	 */
	private Set<RegionSimple> regions;

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

	public FreightTemplateConfigSimple getFreightTemplateConfig() {
		return freightTemplateConfig;
	}

	public void setFreightTemplateConfig(FreightTemplateConfigSimple freightTemplateConfig) {
		this.freightTemplateConfig = freightTemplateConfig;
	}

	public Integer getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public BigDecimal getFirstQuantity() {
		return this.firstQuantity;
	}

	public void setFirstQuantity(BigDecimal firstQuantity) {
		this.firstQuantity = firstQuantity;
	}

	public BigDecimal getFirstFee() {
		return this.firstFee;
	}

	public void setFirstFee(BigDecimal firstFee) {
		this.firstFee = firstFee;
	}

	public BigDecimal getExtraQuantity() {
		return this.extraQuantity;
	}

	public void setExtraQuantity(BigDecimal extraQuantity) {
		this.extraQuantity = extraQuantity;
	}

	public BigDecimal getExtraFee() {
		return this.extraFee;
	}

	public void setExtraFee(BigDecimal extraFee) {
		this.extraFee = extraFee;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeleted() {
		return deleted;
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

	public Integer getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(Integer defaulted) {
		this.defaulted = defaulted;
	}

	public Set<RegionSimple> getRegions() {
		return regions;
	}

	public void setRegions(Set<RegionSimple> regions) {
		this.regions = regions;
	}

}