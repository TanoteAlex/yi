/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class AfterSaleProcess implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 售后处理ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 售后单（after_sale_order表ID）
	 */
	@NotNull
	private AfterSaleOrder afterSaleOrder;
	/**
	 * 处理信息
	 */
	@NotBlank
	@Length(max = 16)
	private String processPerson;
	/**
	 * 处理类型（1待审核，2待收货，3待退款，4已完成，5拒绝退货，6拒绝退款）
	 */
	@Max(127)
	private Integer processType;
	/**
	 * 处理信息
	 */
	@NotBlank
	@Length(max = 255)
	private String processInfo;
	/**
	 * 处理时间
	 */
	@NotNull
	private Date processDate;
	/**
	 * 备注
	 */
	@Length(max = 127)
	private String remark;
	/**
	 * 创建时间
	 */

	private Date createTime;
	/**
	 * 删除（0否1是）
	 */
	@NotNull
	@Max(127)
	private Integer deleted;
	/**
	 * 删除时间
	 */
	private Date delTime;
	// columns END

	public AfterSaleProcess() {
	}

	public AfterSaleProcess(int id) {
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

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "AFTER_SALE_ORDER_ID", nullable = false, updatable = false) })
	public AfterSaleOrder getAfterSaleOrder() {
		return afterSaleOrder;
	}

	public void setAfterSaleOrder(AfterSaleOrder afterSaleOrder) {
		this.afterSaleOrder = afterSaleOrder;
	}

	@Column(unique = false, nullable = false, length = 255)
	public String getProcessPerson() {
		return this.processPerson;
	}

	public void setProcessPerson(String processPerson) {
		this.processPerson = processPerson;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getProcessType() {
		return this.processType;
	}

	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	@Column(unique = false, nullable = false, length = 255)
	public String getProcessInfo() {
		return this.processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = false, length = 19)
	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = false, length = 3)
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

}