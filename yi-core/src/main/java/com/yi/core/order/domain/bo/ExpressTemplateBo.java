/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.yihz.common.convert.domain.BoDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class ExpressTemplateBo extends BoDomain implements Serializable {

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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	 * 供应商（supplier表ID）
	 */
	private SupplierBo supplier;

	// columns END
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTemplateNo() {
		return templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public BigDecimal getPrintWidth() {
		return printWidth;
	}

	public void setPrintWidth(BigDecimal printWidth) {
		this.printWidth = printWidth;
	}

	public BigDecimal getPrintHigh() {
		return printHigh;
	}

	public void setPrintHigh(BigDecimal printHigh) {
		this.printHigh = printHigh;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
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
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public SupplierBo getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierBo supplier) {
		this.supplier = supplier;
	}
}