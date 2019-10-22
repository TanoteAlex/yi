package com.yi.core.commodity.service;
/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.StockRecordBo;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.StockRecord;
import com.yi.core.commodity.domain.vo.StockRecordListVo;
import com.yi.core.commodity.domain.vo.StockRecordVo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IStockRecordService {

	/**
	 * 根据ID得到StockRecord
	 * 
	 * @param stockRecordId
	 * @return
	 */
	StockRecord getStockRecordById(int stockRecordId);

	/**
	 * 根据ID得到StockRecordVo
	 * 
	 * @param stockRecordId
	 * @return
	 */
	StockRecordVo getStockRecordVoById(int stockRecordId);

	/**
	 * 根据ID得到StockRecordListVo
	 * 
	 * @param stockRecordId
	 * @return
	 */
	StockRecordListVo getStockRecordListVoById(int stockRecordId);

	/**
	 * 根据Entity创建StockRecord
	 * 
	 * @param stockRecord
	 * @return
	 */
	StockRecord addStockRecord(StockRecord stockRecord);

	/**
	 * 根据BO创建StockRecord
	 * 
	 * @param stockRecordBo
	 * @return
	 */
	StockRecordListVo addStockRecord(StockRecordBo stockRecordBo);

	/**
	 * 根据Entity更新StockRecord
	 * 
	 * @param stockRecord
	 * @return
	 */
	StockRecord updateStockRecord(StockRecord stockRecord);

	/**
	 * 根据BO更新StockRecord
	 * 
	 * @param stockRecordBo
	 * @return
	 */
	StockRecordListVo updateStockRecord(StockRecordBo stockRecordBo);

	/**
	 * 删除StockRecord
	 * 
	 * @param stockRecordId
	 */
	void removeStockRecordById(int stockRecordId);

	/**
	 * 分页查询: StockRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<StockRecord> query(Pagination<StockRecord> query);

	/**
	 * 分页查询: StockRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<StockRecordListVo> queryListVo(Pagination<StockRecord> query);

	/**
	 * 下单或支付修改库存 添加库存记录
	 * 
	 * @param member
	 * @param saleOrder
	 * @param product
	 * @param useQuantity
	 * @param remark
	 */
	void addStockRecordByOrder(Member member, SaleOrder saleOrder, Product product, Integer useQuantity, String remark);

}
