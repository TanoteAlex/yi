/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.member.domain.entity.MemberCommission;

/**
 * 会员佣金
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface MemberCommissionDao extends JpaRepository<MemberCommission, Integer>, JpaSpecificationExecutor<MemberCommission> {

	/**
	 * 查询未结算的会员佣金
	 * 
	 * @param settlementState
	 * @param deleted
	 * @return
	 */
	List<MemberCommission> findBySettlementStateAndDeleted(Integer settlementState, Integer deleted);

	/**
	 * 查询某笔订单产生的佣金
	 * 
	 * @param saleOrderId
	 * @param deleted
	 * @return
	 */
	List<MemberCommission> findBySaleOrder_idAndDeleted(Integer saleOrderId, Integer deleted);

}