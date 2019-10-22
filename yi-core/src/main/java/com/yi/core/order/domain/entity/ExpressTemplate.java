/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 快递模板
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class ExpressTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 模板ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 单据编号
	 */
	@Length(max = 16)
	private String templateNo;
	/**
	 * 单据名称
	 */
	@NotBlank
	@Length(max = 32)
	private String templateName;
	/**
	 * 打印宽
	 */
	@Max(999999999999999L)
	private BigDecimal printWidth;
	/**
	 * 打印高
	 */
	@Max(999999999999999L)
	private BigDecimal printHigh;
	/**
	 * 状态（0启用1禁用）
	 */
	private Integer state;
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
	// columns END


	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;

	public ExpressTemplate() {
	}

	public ExpressTemplate(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getTemplateNo() {
		return this.templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	@Column(unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getPrintWidth() {
		return this.printWidth;
	}

	public void setPrintWidth(BigDecimal printWidth) {
		this.printWidth = printWidth;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public BigDecimal getPrintHigh() {
		return this.printHigh;
	}

	public void setPrintHigh(BigDecimal printHigh) {
		this.printHigh = printHigh;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public Integer getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getDelTime() {
		return this.delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = true) })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}