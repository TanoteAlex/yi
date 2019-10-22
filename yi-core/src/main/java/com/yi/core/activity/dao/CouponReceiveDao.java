/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.activity.domain.entity.CouponReceive;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface CouponReceiveDao
		extends JpaRepository<CouponReceive, Integer>, JpaSpecificationExecutor<CouponReceive> {

	@EntityGraph(attributePaths = { "coupon" })
	List<CouponReceive> findByMember_id(int memberId);

	int countByCoupon_idAndMember_id(Integer couponId, Integer memberId);

	/**
	 * 根据优惠券类型查询优惠券
	 * 
	 * @param couponType
	 * @param memberId
	 * @param availableDate
	 * @param state
	 * @param deleted
	 * @return
	 */
	@Query(value = "select t1 from CouponReceive t1 where t1.coupon.couponType=?1 and t1.member.id=?2  and Date(?3)>Date(t1.startTime) and  Date(?3)<Date(t1.endTime  )and t1.state=?4 and t1.deleted=?5")
	List<CouponReceive> findBycouponTypeAndMemberId(Integer couponType, Integer memberId, Date availableDate,
			Integer state, Integer deleted);

	/**
	 * 根据类型查询会员可用的优惠券</br>
	 * 按面值倒序
	 * 
	 * @param memberId
	 * @param couponType
	 * @param state
	 * @param deleted
	 * @param now1
	 * @param now2
	 * @return
	 */
	List<CouponReceive> findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDesc(
			Integer memberId, Integer[] couponType, Integer state, Integer deleted, Date now1, Date now2);

	/**
	 * 根据类型查询会员可用的代金券</br>
	 * 按结束时间正序
	 * 
	 * @param memberId
	 * @param couponType
	 * @param state
	 * @param deleted
	 * @param now1
	 * @param now2
	 * @return
	 */
	List<CouponReceive> findByMember_idAndCoupon_CouponTypeInAndStateAndDeletedAndStartTimeBeforeAndEndTimeAfterOrderByParValueDescEndTimeAsc(
			Integer memberId, Integer[] couponType, Integer state, Integer deleted, Date now1, Date now2);

	/**
	 * 检查优惠券领取情况
	 * 
	 * @param memberId
	 * @return
	 */
	@Query(value = "select t1.coupon.id,count(t1.id) from CouponReceive t1 where t1.member.id=?1 group by t1.coupon.id ")
	List<Object[]> checkReceiveNum(Object memberId);

	int countByCouponId(int couponId);

	/**
	 * 统计会员领取优惠券数量
	 * 
	 * @param memberId
	 * @return
	 */
	int countByMember_idAndStateInAndDeleted(Integer memberId, Integer[] state, Integer deleted);

	/**
	 * 查询失效的优惠券
	 * 
	 * @param state
	 * @param now
	 * @param deleted
	 * @return
	 */
	List<CouponReceive> findByStateAndEndTimeLessThanAndDeleted(Integer state, Date now, Integer deleted);

	/**
	 * 根据会员和发放记录ID 查询领取的优惠券
	 * 
	 * @param memberId
	 * @param grantRecordId
	 * @param deleted
	 * @return
	 */
	CouponReceive findByMember_idAndCouponGrantRecord_idAndDeleted(Integer memberId, Integer grantRecordId,
			Integer deleted);

	/**
	 * 根据发放记录ID 查询领取的优惠券
	 * 
	 * @param grantRecordIds
	 * @param deleted
	 * @return
	 */
	List<CouponReceive> findByCouponGrantRecord_idInAndDeleted(List<Integer> grantRecordIds, Integer deleted);

	/**
	 * 退款时 查询已经使用的代金券
	 * 
	 * @param memberId
	 * @param saleOrderId
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<CouponReceive> findByMember_idAndSaleOrder_idAndStateAndDeleted(Integer memberId, Integer saleOrderId,
			Integer state, Integer deleted);

}