/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.simple;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.domain.SimpleDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 运费模板配置
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public class FreightTemplateConfigSimple extends SimpleDomain implements java.io.Serializable {

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
	@NotEmpty
	@Length(max = 127)
	private String configName;
	/**
	 * 供应商（supplier表ID）
	 */
	private SupplierSimple supplier;
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
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
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

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

	public String getConfigNo() {
		return this.configNo;
	}

	public void setConfigNo(String configNo) {
		this.configNo = configNo;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@JsonIgnore
	public SupplierSimple getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSimple supplier) {
		this.supplier = supplier;
	}

	public Integer getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getTimeUnit() {
		return this.timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getFreightType() {
		return this.freightType;
	}

	public void setFreightType(Integer freightType) {
		this.freightType = freightType;
	}

	public Integer getChargeMode() {
		return this.chargeMode;
	}

	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}

	public Integer getDeliveryMode() {
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Integer getFreeCondition() {
		return this.freeCondition;
	}

	public void setFreeCondition(Integer freeCondition) {
		this.freeCondition = freeCondition;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
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

}