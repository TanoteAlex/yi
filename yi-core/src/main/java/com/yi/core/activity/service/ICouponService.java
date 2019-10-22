/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.simple.CouponSimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CouponBo;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.vo.CouponListVo;
import com.yi.core.activity.domain.vo.CouponVo;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICouponService {

	/**
	 * 分页查询: Coupon
	 * 
	 * @param query
	 * @return
	 */
	Page<Coupon> query(Pagination<Coupon> query);

	/**
	 * 分页查询: CouponListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponListVo> queryListVo(Pagination<Coupon> query);

	/**
	 * 分页查询: CouponListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponListVo> queryListVoForApp(Pagination<Coupon> query);

	/**
	 * 根据Entity创建Coupon
	 * 
	 * @param coupon
	 * @return
	 */
	Coupon addCoupon(Coupon coupon);

	/**
	 * 根据BO创建Coupon
	 * 
	 * @param couponBo
	 * @return
	 */
	CouponListVo addCoupon(CouponBo couponBo);

	/**
	 * 根据Entity更新Coupon
	 * 
	 * @param coupon
	 * @return
	 */
	Coupon updateCoupon(Coupon coupon);

	/**
	 * 根据BO更新Coupon
	 * 
	 * @param couponBo
	 * @return
	 */
	CouponListVo updateCoupon(CouponBo couponBo);

	/**
	 * 删除Coupon
	 * 
	 * @param couponId
	 */
	void removeCouponById(int couponId);

	/**
	 * 根据ID得到Coupon
	 * 
	 * @param couponId
	 * @return
	 */
	Coupon getById(int couponId);

	/**
	 * 根据ID得到CouponVo
	 * 
	 * @param couponId
	 * @return
	 */
	CouponVo getVoById(int couponId);

	/**
	 * 根据ID得到CouponListVo
	 * 
	 * @param couponId
	 * @return
	 */
	CouponVo getListVoById(int couponId);

	/**
	 * 获取优惠券详情
	 * 
	 * @param couponId
	 * @return
	 */
	CouponVo getCouponDetail(int couponId);

	/**
	 * 更新优惠券领取数量
	 * 
	 * @param couponId
	 * @param receiveQuantity
	 */
	void updateReceiveQuantity(Integer couponId, Integer receiveQuantity);

	/**
	 * 更新优惠券的使用数量
	 * 
	 * @param couponId
	 * @param useQuantity
	 */
	void updateUseQuantity(Integer couponId, Integer useQuantity);

	EntityListVoBoSimpleConvert<Coupon, CouponBo, CouponVo, CouponSimple, CouponListVo> getCouponConvert();

}
