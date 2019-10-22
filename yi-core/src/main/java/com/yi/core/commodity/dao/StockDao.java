/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.Stock;

/**
 * 库存
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface StockDao extends JpaRepository<Stock, Integer>, JpaSpecificationExecutor<Stock> {

	/**
	 * 根据货品ID 检查该货品库存是否存在
	 * 
	 * @param productId
	 * @param deleted
	 * @return int
	 */
	int countByProduct_IdAndDeleted(Integer productId, Integer deleted);

	/**
	 * 根据货品ID 查询库存
	 * 
	 * @param productId
	 * @param deleted
	 * @return List<Stock>
	 */
	Stock findByProduct_IdAndDeleted(Integer productId, Integer deleted);

	/**
	 * 根据商品ID 查询库存
	 * 
	 * @param commodityId
	 * @param deleted
	 * @param productIds
	 * @return
	 */
	List<Stock> findByCommodity_IdAndDeletedAndProduct_IdNotIn(Integer commodityId, Integer deleted, Object[] productIds);

	/**
	 * 查询商品下的货品库存
	 * 
	 * @param commodityId
	 * @param deleted
	 * @return
	 */
	Set<Stock> findByCommodity_idAndDeleted(Integer commodityId, Integer deleted);

}