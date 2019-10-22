/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.AfterSaleOrder;

/**
 * 售后单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface AfterSaleOrderDao extends JpaRepository<AfterSaleOrder, Integer>, JpaSpecificationExecutor<AfterSaleOrder> {

	/**
	 * 根据退款单号 查询售后订单
	 * 
	 * @param refundOrderNo
	 * @param deleted
	 * @return
	 */
	AfterSaleOrder findByRefundOrderNoAndDeleted(String refundOrderNo, Integer deleted);

	/**
	 * 根据销售订单ID 查询售后订单
	 * 
	 * @param saleOrderId
	 * @param deleted
	 * @return
	 */
	AfterSaleOrder findBySaleOrder_idAndDeleted(Integer saleOrderId, Integer deleted);

	/**
	 * 查询待回执退款单
	 * 
	 * @param refundPayState
	 * @param deleted
	 * @return
	 */
	List<AfterSaleOrder> findByRefundPayStateAndDeleted(Integer refundPayState, Integer deleted);

}