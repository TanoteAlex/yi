/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.domain.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.json.serializer.JsonTimestampSerializer;

/**
 * 礼物
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
public class Gift implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 礼物ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 礼物编号
	 */
	@Length(max = 32)
	private String giftNo;
	/**
	 * 礼包（gift_bag表ID）
	 */
	@NotNull
	private GiftBag giftBag;
	/**
	 * 收礼人（member表ID）
	 */
	private Member member;
	/**
	 * 销售订单（sale_order表ID）
	 */
	private SaleOrder saleOrder;
	/**
	 * 商品（commodity表ID）
	 */
	private Commodity commodity;
	/**
	 * 货品（product表ID）
	 */
	private Product product;
	/**
	 * 单价
	 */
	@Max(999999999999999L)
	private BigDecimal price;
	/**
	 * 数量
	 */
	@Max(9999999999L)
	private int quantity;
	/**
	 * 合计
	 */
	@Max(999999999999999L)
	private BigDecimal total;
	/**
	 * 祝福语
	 */
	@Length(max = 127)
	private String blessWord;
	/**
	 * 收货人
	 */
	@Length(max = 16)
	private String consignee;
	/**
	 * 收货人电话
	 */
	@Length(max = 16)
	private String consigneePhone;
	/**
	 * 收货人地址
	 */
	@Length(max = 127)
	private String consigneeAddr;
	/**
	 * 领取状态（0待领取1已领取2已超时）->计划修正为 0待支付1待领取2已领取3已超时
	 */
	@Max(127)
	private Integer state;
	/**
	 * 领取时间
	 */
	private Date receiveTime;
	/**
	 * 失效时间
	 */
	private Date invalidTime;
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

	public Gift() {
	}

	public Gift(int id) {
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

	@Column(unique = false, nullable = true, length = 32)
	public String getGiftNo() {
		return this.giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GIFT_BAG_ID", nullable = false, updatable = false) })
	public GiftBag getGiftBag() {
		return giftBag;
	}

	public void setGiftBag(GiftBag giftBag) {
		this.giftBag = giftBag;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "MEMBER_ID", nullable = true) })
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "SALE_ORDER_ID", nullable = true) })
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COMMODITY_ID", nullable = true, updatable = false) })
	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "PRODUCT_ID", nullable = true, updatable = false) })
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

	@Column(unique = false, nullable = true, length = 10)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(unique = false, nullable = true, length = 15)
	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getBlessWord() {
		return this.blessWord;
	}

	public void setBlessWord(String blessWord) {
		this.blessWord = blessWord;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(unique = false, nullable = true, length = 16)
	public String getConsigneePhone() {
		return this.consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	@Column(unique = false, nullable = true, length = 127)
	public String getConsigneeAddr() {
		return this.consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	@Column(unique = false, nullable = true, length = 3)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getReceiveTime() {
		return this.receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonTimestampSerializer.class)
	@Column(unique = false, nullable = true, length = 19)
	public Date getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
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

}