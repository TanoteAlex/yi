/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import org.springframework.data.domain.Page;

import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.order.domain.bo.AfterSaleOrderBo;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.AfterSaleOrderListVo;
import com.yi.core.order.domain.vo.AfterSaleOrderVo;
import com.yihz.common.persistence.Pagination;

/**
 * 售后单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IAfterSaleOrderService {

	/**
	 * 分页查询: AfterSaleOrder
	 **/
	Page<AfterSaleOrder> query(Pagination<AfterSaleOrder> query);

	/**
	 * 分页查询: AfterSaleOrder
	 **/
	Page<AfterSaleOrderListVo> queryListVo(Pagination<AfterSaleOrder> query);

	/**
	 * 分页查询: AfterSaleOrder
	 **/
	Page<AfterSaleOrderListVo> queryListVoForApp(Pagination<AfterSaleOrder> query);

	/**
	 * 创建AfterSaleOrder
	 **/
	AfterSaleOrder addAfterSaleOrder(AfterSaleOrder afterSaleOrder);

	/**
	 * 创建AfterSaleOrder
	 **/
	AfterSaleOrderListVo addAfterSaleOrder(AfterSaleOrderBo afterSaleOrder);

	/**
	 * 更新AfterSaleOrder
	 **/
	AfterSaleOrder updateAfterSaleOrder(AfterSaleOrder afterSaleOrder);

	/**
	 * 更新AfterSaleOrder
	 **/
	AfterSaleOrderListVo updateAfterSaleOrder(AfterSaleOrderBo afterSaleOrder);

	/**
	 * 删除AfterSaleOrder
	 **/
	void removeAfterSaleOrderById(int afterSaleOrderId);

	/**
	 * 根据ID得到AfterSaleOrder
	 **/
	AfterSaleOrder getById(int afterSaleOrderId);

	/**
	 * 根据ID得到AfterSaleOrderBo
	 **/
	AfterSaleOrderBo getBoById(int afterSaleOrderId);

	/**
	 * 根据ID得到AfterSaleOrderVo
	 **/
	AfterSaleOrderVo getVoById(int afterSaleOrderId);

	/**
	 * 根据ID得到AfterSaleOrderListVo
	 **/
	AfterSaleOrderListVo getListVoById(int afterSaleOrderId);

	/**
	 * 通过退款订单号 查询售后订单
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	AfterSaleOrder getByRefundOrderNo(String refundOrderNo);

	/**
	 * 根据saleOrderId得到AfterSaleOrderVo
	 * 
	 * @param saleOrderId
	 * @return
	 */
	AfterSaleOrderVo getVoBySaleOrderId(int saleOrderId);

	/**
	 * 申请售后
	 * 
	 * @param afterSaleOrder
	 */
	AfterSaleOrder applyAfterSale(AfterSaleOrderBo afterSaleOrder);

	/**
	 * 确认退货
	 * 
	 * @param afterSaleOrderId
	 * @return
	 */
	AfterSaleOrder confirmReturn(Integer afterSaleOrderId);

	/**
	 * 拒绝退货
	 * 
	 * @param afterSaleOrderId
	 * @return
	 */
	AfterSaleOrder refuseReturn(Integer afterSaleOrderId);

	/**
	 * 确认收货
	 * 
	 * @param afterSaleOrderId
	 * @return
	 */
	AfterSaleOrder confirmReceipt(Integer afterSaleOrderId);

	/**
	 * 确认退款
	 * 
	 * @param afterSaleOrderId
	 * @return
	 */
	AfterSaleOrder confirmRefund(Integer afterSaleOrderId) throws Exception;

	/**
	 * 拒绝退款
	 * 
	 * @param afterSaleOrderId
	 * @return
	 */
	AfterSaleOrder refuseRefund(Integer afterSaleOrderId);

	/**
	 * 为失效的礼包生成退款单
	 * 
	 * @param giftBag
	 * @return
	 */
	AfterSaleOrder addByInvalidGiftBag(GiftBag giftBag);

	/**
	 * 未成团的失效开团单退款
	 * 
	 * @param saleOrder
	 * @return
	 */
	void refundByInvalidOpenGroupBuy(SaleOrder saleOrder);

	/**
	 * 未成团的失效参团单退款
	 * 
	 * @param saleOrder
	 * @return
	 */
	void refundByInvalidJoinGroupBuy(SaleOrder saleOrder);

	/**
	 * 退款回执
	 */
	void refundReceiptByTask() throws Exception;

}
