/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.promotion.domain.entity.GroupBuyOrder;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface GroupBuyOrderDao extends JpaRepository<GroupBuyOrder, Integer>, JpaSpecificationExecutor<GroupBuyOrder> {

	/**
	 * 查询团购商品的开团集合
	 * 
	 * @param GroupBuyProductId
	 * @param state
	 * @param groupType
	 * @param deleted
	 * @param pageable
	 * @return
	 */
	List<GroupBuyOrder> findByGroupBuyActivityProduct_idAndStateAndGroupTypeAndDeletedOrderByEndTimeAsc(Integer GroupBuyProductId, Integer state, Integer groupType,
			Integer deleted, Pageable pageable);

	/**
	 * 查询团购商品的开团集合
	 * 
	 * @param GroupBuyProductId
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<GroupBuyOrder> findByGroupBuyActivityProduct_idAndStateAndDeletedOrderByEndTimeAsc(Integer GroupBuyProductId, Integer state, Integer deleted);

	/**
	 * 查询订单的开团单
	 * 
	 * @param memberId
	 * @param saleOrderId
	 * @param state
	 * @param deleted
	 * @return
	 */
	GroupBuyOrder findByMember_idAndSaleOrder_idAndDeleted(Integer memberId, Integer saleOrderId, Integer deleted);

	/**
	 * 查询未成团的团购单
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<GroupBuyOrder> findByStateAndDeleted(Integer state, Integer deleted);

}