/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.Coupon;

/**
 * 优惠券
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface CouponDao extends JpaRepository<Coupon, Integer>, JpaSpecificationExecutor<Coupon> {

	/**
	 * 根据优惠券类型查询数据
	 * 
	 * @param couponType
	 * @param deleted
	 * @return
	 */
	List<Coupon> findByCouponTypeAndDeleted(int couponType, int deleted);

	/**
	 * 校验重复
	 * 
	 * @param couponName
	 * @param couponType
	 * @param deleted
	 * @return
	 */
	int countByCouponNameAndCouponTypeAndDeleted(String couponName, Integer couponType, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param couponName
	 * @param couponType
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByCouponNameAndCouponTypeAndDeletedAndIdNot(String couponName, Integer couponType, Integer deleted, Integer id);

}