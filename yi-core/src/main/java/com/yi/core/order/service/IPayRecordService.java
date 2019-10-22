/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.util.Map;
import java.util.SortedMap;

import org.springframework.data.domain.Page;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yi.core.order.domain.bo.PayRecordBo;
import com.yi.core.order.domain.entity.PayRecord;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.PayRecordListVo;
import com.yi.core.order.domain.vo.PayRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IPayRecordService {

	/**
	 * 根据ID得到PayRecord
	 * 
	 * @param payRecordId
	 * @return
	 */
	PayRecord getPayRecordById(int payRecordId);

	/**
	 * 根据ID得到PayRecordVo
	 * 
	 * @param payRecordId
	 * @return
	 */
	PayRecordVo getPayRecordVoById(int payRecordId);

	/**
	 * 根据ID得到PayRecordListVo
	 * 
	 * @param payRecordId
	 * @return
	 */
	PayRecordListVo getPayRecordListVoById(int payRecordId);

	/**
	 * 根据Entity创建PayRecord
	 * 
	 * @param payRecord
	 * @return
	 */
	PayRecord addPayRecord(PayRecord payRecord);

	/**
	 * 根据BO创建PayRecord
	 * 
	 * @param payRecordBo
	 * @return
	 */
	PayRecordListVo addPayRecord(PayRecordBo payRecordBo);

	/**
	 * 根据Entity更新PayRecord
	 * 
	 * @param payRecord
	 * @return
	 */
	PayRecord updatePayRecord(PayRecord payRecord);

	/**
	 * 根据BO更新PayRecord
	 * 
	 * @param payRecordBo
	 * @return
	 */
	PayRecordVo updatePayRecord(PayRecordBo payRecordBo);

	/**
	 * 删除PayRecord
	 * 
	 * @param payRecordId
	 */
	void removePayRecordById(int payRecordId);

	/**
	 * 分页查询: PayRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<PayRecord> query(Pagination<PayRecord> query);

	/**
	 * 分页查询: PayRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<PayRecordListVo> queryListVo(Pagination<PayRecord> query);

	/**
	 * 支付完成 新增支付记录
	 * 
	 * @param saleOrder
	 * @param resultMap
	 */
	void addPayRecordByOrderForWeChat(SaleOrder saleOrder, SortedMap<String, String> resultMap);

	/**
	 * 支付完成 新增支付记录
	 * 
	 * @param saleOrder
	 * @param resultMap
	 */
	void addPayRecordByOrderForAlipay(SaleOrder saleOrder, Map<String, String> resultMap);

	/**
	 * 支付完成 新增支付记录
	 * 
	 * @param saleOrder
	 * @param resultMap
	 */
	void addPayRecordByOrderForAlipay(SaleOrder saleOrder, AlipayTradeQueryResponse response);

	/**
	 * 支付完成 新增支付记录
	 * 
	 * @param saleOrder
	 * @param resultMap
	 */
	void addPayRecordByOrderForBalance(SaleOrder saleOrder);

}
