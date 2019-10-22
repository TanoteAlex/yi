/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.StockBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.commodity.domain.vo.StockListVo;
import com.yi.core.commodity.domain.vo.StockVo;
import com.yi.core.order.domain.entity.AfterSaleOrder;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 库存
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IStockService {

	/**
	 * 分页查询: Stock
	 * 
	 * @param query
	 * @return
	 */
	Page<Stock> query(Pagination<Stock> query);

	/**
	 * 分页查询: Stock
	 **/
	Page<StockListVo> queryListVo(Pagination<Stock> query);

	/**
	 * 创建Stock
	 **/
	Stock addStock(Stock stock);

	/**
	 * 创建Stock
	 **/
	StockVo addStock(StockBo stock);

	/**
	 * 更新Stock
	 **/
	Stock updateStock(Stock stock);

	/**
	 * 更新Stock
	 **/
	StockVo updateStock(StockBo stock);

	/**
	 * 删除Stock
	 **/
	void removeStockById(int stockId);

	/**
	 * 根据ID得到Stock
	 **/
	Stock getStockById(int stockId);

	/**
	 * 根据ID得到StockVo
	 **/
	StockVo getStockVoById(int stockId);

	/**
	 * 根据ID得到StockListVo
	 **/
	StockListVo getListVoById(int stockId);

	/**
	 * 批量 新增库存
	 * 
	 * @param stocks
	 */
	void batchAddStock(Collection<Product> products, Commodity commodity);

	/**
	 * 批量 更新库存
	 * 
	 * @param products
	 * @param commodity
	 * @param supplier
	 */
	void batchUpdateStock(Collection<Product> products, Commodity commodity);

	/**
	 * 检查库存
	 * 
	 * @param productId
	 * @return
	 */
	boolean checkStock(Integer productId);

	/**
	 * 检查库存
	 * 
	 * @param productId
	 * @param quantity
	 * @return true 有库存 false库存不足
	 */
	boolean checkStock(Integer productId, int quantity);

	/**
	 * 根据商品 更新库存
	 * 
	 * @param commodity
	 */
	void updateStockByCommodity(Commodity commodity);

	/**
	 * 下单减库存
	 * 
	 * @param saleOrders
	 */
	void useStockBySubmitOrder(List<SaleOrder> saleOrders);

	/**
	 * 支付减库存
	 * 
	 * @param saleOrders
	 */
	void useStockByPayOrder(SaleOrder... saleOrders);

	/**
	 * 退款退回库存
	 * 
	 * @param saleOrders
	 */
	void returnStockByRefundOrder(AfterSaleOrder afterSaleOrder);

	/**
	 * 根据商品 删除货品库存
	 * 
	 * @param commodityId
	 */
	void deleteByCommodity(Integer commodityId);

}
