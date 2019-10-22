/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CouponUseBo;
import com.yi.core.activity.domain.entity.CouponUse;
import com.yi.core.activity.domain.simple.CouponUseSimple;
import com.yi.core.activity.domain.vo.CouponUseListVo;
import com.yi.core.activity.domain.vo.CouponUseVo;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICouponUseService {

	EntityListVoBoSimpleConvert<CouponUse, CouponUseBo, CouponUseVo, CouponUseSimple, CouponUseListVo> getCouponUseConvert();

	/**
	 * 根据ID得到CouponUse
	 * 
	 * @param couponUseId
	 * @return
	 */
	CouponUse getCouponUseById(int couponUseId);

	/**
	 * 根据ID得到CouponUseVo
	 * 
	 * @param couponUseId
	 * @return
	 */
	CouponUseVo getCouponUseVoById(int couponUseId);

	/**
	 * 根据ID得到CouponUseListVo
	 * 
	 * @param couponUseId
	 * @return
	 */
	CouponUseVo getCouponUseListVoById(int couponUseId);

	/**
	 * 根据Entity创建CouponUse
	 * 
	 * @param couponUse
	 * @return
	 */
	CouponUse addCouponUse(CouponUse couponUse);

	/**
	 * 根据BO创建CouponUse
	 * 
	 * @param couponUseBo
	 * @return
	 */
	CouponUseVo addCouponUse(CouponUseBo couponUseBo);

	/**
	 * 根据Entity更新CouponUse
	 * 
	 * @param couponUse
	 * @return
	 */
	CouponUse updateCouponUse(CouponUse couponUse);

	/**
	 * 根据BO更新CouponUse
	 * 
	 * @param couponUseBo
	 * @return
	 */
	CouponUseVo updateCouponUse(CouponUseBo couponUseBo);

	/**
	 * 删除CouponUse
	 * 
	 * @param couponUseId
	 */
	void removeCouponUseById(int couponUseId);

	/**
	 * 分页查询: CouponUse
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponUse> query(Pagination<CouponUse> query);

	/**
	 * 分页查询: CouponUseListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponUseListVo> queryListVo(Pagination<CouponUse> query);

}
