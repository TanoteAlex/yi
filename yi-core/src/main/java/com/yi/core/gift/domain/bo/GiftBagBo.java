/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.domain.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yihz.common.convert.domain.BoDomain;
import com.yihz.common.json.deserializer.JsonTimestampDeserializer;

/**
 * 礼包
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public class GiftBagBo extends BoDomain implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// columns START
	/**
	 * 礼包ID
	 */
	@Max(9999999999L)
	private int id;
	/**
	 * GUID
	 */
	@Length(max = 32)
	private String guid;
	/**
	 * 会员（member表ID）礼包所属人或领取人账号
	 */
	@NotNull
	private MemberBo member;
	/**
	 * 订单（sale_order表ID）
	 */
	@NotNull
	private SaleOrderBo saleOrder;
	/**
	 * 礼包编号
	 */
	@Length(max = 32)
	private String giftBagNo;
	/**
	 * 礼包名称
	 */
	@Length(max = 127)
	private String giftBagName;
	/**
	 * 商品（commodity表ID）
	 */
	private CommodityBo commodity;
	/**
	 * 货品（product表ID）
	 */
	private ProductBo product;
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
	 * 礼物数
	 */
	@Max(9999999999L)
	private int giftNum;
	/**
	 * 领取礼品数
	 */
	@Max(9999999999L)
	private int receiveGiftNum;
	/**
	 * 神秘礼物（0否1是）
	 */
	@Max(127)
	private Integer mysteryGift;
	/**
	 * 自定义祝福语
	 */
	@Length(max = 127)
	private String blessWord;
	/**
	 * 有效状态（0有效1失效）
	 */
	@Max(127)
	private Integer state;
	/**
	 * 结束时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date finishTime;
	/**
	 * 失效时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date invalidTime;
	/**
	 * 创建时间
	 */
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
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
	@JsonDeserialize(using = JsonTimestampDeserializer.class)
	private Date delTime;
	// columns END

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

	public String getGiftBagNo() {
		return this.giftBagNo;
	}

	public void setGiftBagNo(String giftBagNo) {
		this.giftBagNo = giftBagNo;
	}

	public String getGiftBagName() {
		return this.giftBagName;
	}

	public void setGiftBagName(String giftBagName) {
		this.giftBagName = giftBagName;
	}

	public MemberBo getMember() {
		return this.member;
	}

	public void setMember(MemberBo member) {
		this.member = member;
	}

	public SaleOrderBo getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrderBo saleOrder) {
		this.saleOrder = saleOrder;
	}

	public CommodityBo getCommodity() {
		return this.commodity;
	}

	public void setCommodity(CommodityBo commodity) {
		this.commodity = commodity;
	}

	public ProductBo getProduct() {
		return this.product;
	}

	public void setProduct(ProductBo product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public int getGiftNum() {
		return this.giftNum;
	}

	public void setGiftNum(int giftNum) {
		this.giftNum = giftNum;
	}

	public int getReceiveGiftNum() {
		return this.receiveGiftNum;
	}

	public void setReceiveGiftNum(int receiveGiftNum) {
		this.receiveGiftNum = receiveGiftNum;
	}

	public Integer getMysteryGift() {
		return this.mysteryGift;
	}

	public void setMysteryGift(Integer mysteryGift) {
		this.mysteryGift = mysteryGift;
	}

	public String getBlessWord() {
		return this.blessWord;
	}

	public void setBlessWord(String blessWord) {
		this.blessWord = blessWord;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

}