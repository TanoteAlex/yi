/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.List;
import java.util.Set;

import com.yi.core.commodity.domain.simple.ProductSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.Product;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface ProductDao extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	Product findByIdAndDeleted(Integer id, Integer deleted);

	List<Product> findByCommodity_id(Integer commodityId);

	/**
	 * 根据商品ID 查询货品集合
	 * 
	 * @param commodityId
	 * @param deleted
	 * @return
	 */
	Set<Product> findByCommodity_idAndDeleted(Integer commodityId, Integer deleted);


}