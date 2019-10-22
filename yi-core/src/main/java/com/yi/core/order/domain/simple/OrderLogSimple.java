/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.simple;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;
import com.yihz.common.json.serializer.JsonTimestampSerializer;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.yihz.common.convert.domain.SimpleDomain;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public class OrderLogSimple extends SimpleDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 订单ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 订单（order表ID）
	 */
	@NotNull
	private SaleOrderSimple order;
	/**
	 * 订单编号
	 */
	@Length(max = 16)
	private String orderNo;
	/**
	 * 订单状态（1创建订单2支付成功3开始配送4确认收货）
	 */
	private Integer state;
	/**
	 * 操作时间
	 */
	@NotNull
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date operateTime;
	/**
	 * 操作人
	 */
	@NotBlank
	@Length(max = 32)
	private String operator;
	/**
	 * 备注说明
	 */
	@Length(max = 127)
	private String remark;

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

	public SaleOrderSimple getOrder() {
		return order;
	}

	public void setOrder(SaleOrderSimple order) {
		this.order = order;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}