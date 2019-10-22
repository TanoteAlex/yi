/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import org.springframework.data.domain.Page;

import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.bo.OrderLogBo;
import com.yi.core.order.domain.entity.OrderLog;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.OrderLogListVo;
import com.yi.core.order.domain.vo.OrderLogVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IOrderLogService {

	/**
	 * 根据ID得到OrderLog
	 * 
	 * @param orderLogId
	 * @return
	 */
	OrderLog getOrderLogById(int orderLogId);

	/**
	 * 根据ID得到OrderLogVo
	 * 
	 * @param orderLogId
	 * @return
	 */
	OrderLogVo getOrderLogVoById(int orderLogId);

	/**
	 * 根据ID得到OrderLogListVo
	 * 
	 * @param orderLogId
	 * @return
	 */
	OrderLogListVo getOrderLogListVoById(int orderLogId);

	/**
	 * 根据Entity创建OrderLog
	 * 
	 * @param orderLog
	 * @return
	 */
	OrderLogVo addOrderLog(OrderLog orderLog);

	/**
	 * 根据BO创建OrderLog
	 * 
	 * @param orderLogBo
	 * @return
	 */
	OrderLogListVo addOrderLog(OrderLogBo orderLogBo);

	/**
	 * 根据Entity更新OrderLog
	 * 
	 * @param orderLog
	 * @return
	 */
	OrderLogVo updateOrderLog(OrderLog orderLog);

	/**
	 * 根据BO更新OrderLog
	 * 
	 * @param orderLogBo
	 * @return
	 */
	OrderLogListVo updateOrderLog(OrderLogBo orderLogBo);

	/**
	 * 删除OrderLog
	 * 
	 * @param orderLogId
	 */
	void removeOrderLogById(int orderLogId);

	/**
	 * 分页查询: OrderLog
	 * 
	 * @param query
	 * @return
	 */
	Page<OrderLog> query(Pagination<OrderLog> query);

	/**
	 * 分页查询: OrderLogListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<OrderLogListVo> queryListVo(Pagination<OrderLog> query);

	/**
	 * 根据订单 批量新增订单日志
	 * 
	 * @param saleOrders
	 * @param logState
	 *            日志状态
	 */
	void batchAddByOrders(Integer logState, SaleOrder... saleOrders);

	/**
	 * 根据订单 新增订单日志
	 * 
	 * @param saleOrder
	 * @param logState
	 * @param remark
	 */
	void addLogByOrder(SaleOrder saleOrder, OrderEnum logState, String remark);

}
