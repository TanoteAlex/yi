/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.CouponGrantRecord;

/**
 * 优惠券发放记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface CouponGrantRecordDao extends JpaRepository<CouponGrantRecord, Integer>, JpaSpecificationExecutor<CouponGrantRecord> {

	/**
	 * 检查某个步骤是否发过优惠券
	 * 
	 * @param memberId
	 * @param couponGrantConfigId
	 * @param couponId
	 * @param grantNode
	 * @param parValue
	 * @param deleted
	 * @return
	 */
	Set<CouponGrantRecord> findByMember_idAndSaleOrder_idAndCouponGrantConfig_idAndCoupon_idAndGrantNodeAndParValueAndDeleted(Integer memberId, Integer saleOrderId,
			Integer couponGrantConfigId, Integer couponId, Integer grantNode, BigDecimal parValue, Integer deleted);

	/**
	 * 查询发放记录
	 * 
	 * @param memberId
	 * @param saleOrderId
	 * @param deleted
	 * @return
	 */
	List<CouponGrantRecord> findByMember_idAndSaleOrder_idAndDeleted(Integer memberId, Integer saleOrderId, Integer deleted);

	/**
	 * 检查是不是第一次发放
	 * 
	 * @param memberId
	 * @param saleOrderId
	 * @param couponGrantConfigId
	 * @param couponId
	 * @param deleted
	 * @return
	 */
	int countByMember_idAndSaleOrder_idAndCouponGrantConfig_idAndCoupon_idAndDeleted(Integer memberId, Integer saleOrderId, Integer couponGrantConfigId, Integer couponId,
			Integer deleted);

}