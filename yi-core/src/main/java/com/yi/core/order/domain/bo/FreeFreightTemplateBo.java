/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.basic.domain.bo.RegionBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 包邮运费模板
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class FreeFreightTemplateBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 包邮运费模板ID
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
	private FreightTemplateConfigBo freightTemplateConfig;
	/**
	 * 运送方式（1快递，2EMS，3平邮）
	 */
	@NotNull
	@Max(127)
	private Integer deliveryMode;
	/**
	 * 包邮条件类型(1件数，2金额，3件数+金额)
	 */
	@Max(127)
	private Integer freeType;
	/**
	 * 满XX件包邮
	 */
	@Max(127)
	private Integer fullQuantity;
	/**
	 * 满XX元包邮
	 */
	private BigDecimal fullAmount;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

	/**
	 * 运费模板地区
	 */
	private Set<RegionBo> regions;

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

	public FreightTemplateConfigBo getFreightTemplateConfig() {
		return freightTemplateConfig;
	}

	public void setFreightTemplateConfig(FreightTemplateConfigBo freightTemplateConfig) {
		this.freightTemplateConfig = freightTemplateConfig;
	}

	public Integer getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Integer getFreeType() {
		return this.freeType;
	}

	public void setFreeType(Integer freeType) {
		this.freeType = freeType;
	}

	public Integer getFullQuantity() {
		return this.fullQuantity;
	}

	public void setFullQuantity(Integer fullQuantity) {
		this.fullQuantity = fullQuantity;
	}

	public BigDecimal getFullAmount() {
		return this.fullAmount;
	}

	public void setFullAmount(BigDecimal fullAmount) {
		this.fullAmount = fullAmount;
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

	public Set<RegionBo> getRegions() {
		return regions;
	}

	public void setRegions(Set<RegionBo> regions) {
		this.regions = regions;
	}

}