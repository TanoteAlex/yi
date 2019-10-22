/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 运费模板配置
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
@Proxy(lazy = false)
public class FreightTemplateConfig implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 运费模板配置ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 模板配置编号
	 */
	@Length(max = 16)
	private String configNo;
	/**
	 * 模板配置名称
	 */
	@NotBlank
	@Length(max = 127)
	private String configName;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	/**
	 * 发货时间（1-12小时内，2-24小时内，3-1天内，4-3天内，5-5天内）
	 */
	@Max(127)
	private Integer deliveryTime;
	/**
	 * 发货时间单位（1小时，2天）
	 */
	@Max(127)
	private Integer timeUnit;
	/**
	 * 运费类型（1自定义运费，2卖家承担运费）
	 */
	@Max(127)
	private Integer freightType;
	/**
	 * 计价方式（1按件数，2按重量，3按体积）
	 */
	@NotNull
	@Max(127)
	private Integer chargeMode;
	/**
	 * 运送方式（1快递，2EMS，3平邮）
	 */
	@NotNull
	@Max(127)
	private Integer deliveryMode;
	/**
	 * 包邮条件（0非指定条件1指定条件）
	 */
	@Max(127)
	private Integer freeCondition;
	/**
	 * 状态（0启用1禁用）
	 */
	@NotNull
	private Integer state;
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
	 * 自定义运费
	 */
	private List<CustomFreightTemplate> customFreightTemplates = new ArrayList<>(0);

	/**
	 * 指定条件包邮
	 */
	private List<FreeFreightTemplate> freeFreightTemplates = new ArrayList<>(0);

	/**
	 * 商家
	 */
	private Set<Commodity> commodities;

	public FreightTemplateConfig() {
	}

	public FreightTemplateConfig(int id) {
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

	@Column(unique = false, nullable = true, length = 16)
	public String getConfigNo() {
		return this.configNo;
	}

	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}

	@Column(unique = false, nullable = false, length = 127)
	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getTimeUnit() {
		return this.timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getFreightType() {
		return this.freightType;
	}

	public void setFreightType(Integer freightType) {
		this.freightType = freightType;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getChargeMode() {
		return this.chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getFreeCondition() {
		return this.freeCondition;
	}

	public void setFreeCondition(Integer freeCondition) {
		this.freeCondition = freeCondition;
	}

	@Column(unique = false, nullable = false, length = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "freightTemplateConfig")
	public List<CustomFreightTemplate> getCustomFreightTemplates() {
		return customFreightTemplates;
	}

	public void setCustomFreightTemplates(List<CustomFreightTemplate> customFreightTemplates) {
		this.customFreightTemplates = customFreightTemplates;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "freightTemplateConfig")
	public List<FreeFreightTemplate> getFreeFreightTemplates() {
		return freeFreightTemplates;
	}

	public void setFreeFreightTemplates(List<FreeFreightTemplate> freeFreightTemplates) {
		this.freeFreightTemplates = freeFreightTemplates;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "freightTemplate")
	public Set<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(Set<Commodity> commodities) {
		this.commodities = commodities;
	}

}