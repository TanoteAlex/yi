/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.gift.domain.entity.GiftBag;

/**
 * 礼包
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface GiftBagDao extends JpaRepository<GiftBag, Integer>, JpaSpecificationExecutor<GiftBag> {

	/**
	 * 查询未失效 礼包
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<GiftBag> findByStateAndDeleted(Integer state, Integer deleted);

	/**
	 * 查询订单的礼包
	 * 
	 * @param saleOrderId
	 * @param deleted
	 * @return
	 */
	List<GiftBag> findBySaleOrder_idAndDeleted(Integer saleOrderId, Integer deleted);

}