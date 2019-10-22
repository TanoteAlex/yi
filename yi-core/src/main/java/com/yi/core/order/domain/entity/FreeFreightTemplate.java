/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.basic.domain.entity.Region;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 包邮运费模板
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class FreeFreightTemplate implements java.io.Serializable {

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
	private FreightTemplateConfig freightTemplateConfig;
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
	@Max(999999999999999L)
	private BigDecimal fullAmount;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	/**
	 * 运费模板地区
	 */
	private Set<Region> regions;

	public FreeFreightTemplate() {
	}

	public FreeFreightTemplate(int id) {
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

	@Column(unique = false, nullable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getFreeType() {
		return this.freeType;
	}

	public void setFreeType(Integer freeType) {
		this.freeType = freeType;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getFullQuantity() {
		return this.fullQuantity;
	}

	public void setFullQuantity(Integer fullQuantity) {
		this.fullQuantity = fullQuantity;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getFullAmount() {
		return this.fullAmount;
	}

	public void setFullAmount(BigDecimal fullAmount) {
		this.fullAmount = fullAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "FREIGHT_TEMPLATE_CONFIG_ID", nullable = false, updatable = false) })
	public FreightTemplateConfig getFreightTemplateConfig() {
		return freightTemplateConfig;
	}

	public void setFreightTemplateConfig(FreightTemplateConfig freightTemplateConfig) {
		this.freightTemplateConfig = freightTemplateConfig;
	}

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "free_freight_template_region", joinColumns = @JoinColumn(name = "FREE_FREIGHT_TEMPLATE_ID"), inverseJoinColumns = @JoinColumn(name = "REGION_ID"))
	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

}