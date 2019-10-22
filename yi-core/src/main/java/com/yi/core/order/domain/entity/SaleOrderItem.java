/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 销售订单项
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class SaleOrderItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 订单项ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrder saleOrder;

	private Integer mySaleOrderId;
	/**
	 * 会员（member表ID）
	 */
	@NotNull
	private Member member;
	private Integer myMemberId;
	/**
	 * 供应商（supplier表ID）
	 */
	private Supplier supplier;
	private Integer mySupplierId;
	/**
	 * 商品(commodity表ID)
	 */
	private Commodity commodity;
	private Integer myCommodityId;
	/**
	 * 商品（product表ID）
	 */
	@NotNull
	private Product product;
	private Integer myProductId;
	/**
	 * 单价
	 */
	private BigDecimal price;

	/**
	 * 供货价
	 */
	private BigDecimal supplyPrice;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 优惠金额
	 */
	private BigDecimal discount;
	/**
	 * 成本金额
	 */
	private BigDecimal costAmount;
	/**
	 * 小计金额
	 */
	private BigDecimal subtotal;
	/**
	 * 结算状态（0未计算1已结算）
	 */
	private Integer settlementState;
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

	public SaleOrderItem() {
	}

	public SaleOrderItem(int id) {
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

	@Column(length = 32)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SUPPLIER_ID", nullable = true, updatable = false) })
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = true, updatable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false) })
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	@Column(unique = false, nullable = true, length = 10)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(unique = false, nullable = true, length = 10)
	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	@Column(unique = false, nullable = false, length = 3)
	public Integer getSettlementState() {
		return settlementState;
	}

	public void setSettlementState(Integer settlementState) {
		this.settlementState = settlementState;
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

	@Column(unique = false, nullable = false, length = 3)
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


	@Transient
	public Integer getMySaleOrderId() {
		return mySaleOrderId;
	}

	public void setMySaleOrderId(Integer mySaleOrderId) {
		this.mySaleOrderId = mySaleOrderId;
	}
	@Transient
	public Integer getMyMemberId() {
		return myMemberId;
	}

	public void setMyMemberId(Integer myMemberId) {
		this.myMemberId = myMemberId;
	}
	@Transient
	public Integer getMySupplierId() {
		return mySupplierId;
	}

	public void setMySupplierId(Integer mySupplierId) {
		this.mySupplierId = mySupplierId;
	}
	@Transient
	public Integer getMyCommodityId() {
		return myCommodityId;
	}

	public void setMyCommodityId(Integer myCommodityId) {
		this.myCommodityId = myCommodityId;
	}
	@Transient
	public Integer getMyProductId() {
		return myProductId;
	}

	public void setMyProductId(Integer myProductId) {
		this.myProductId = myProductId;
	}

}