/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CouponGrantConfigBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.vo.CouponGrantConfigListVo;
import com.yi.core.activity.domain.vo.CouponGrantConfigVo;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICouponGrantConfigService {

	/**
	 * 分页查询: CouponGrantConfig
	 **/
	Page<CouponGrantConfig> query(Pagination<CouponGrantConfig> query);

	/**
	 * 分页查询: CouponGrantConfig
	 **/
	Page<CouponGrantConfigListVo> queryListVo(Pagination<CouponGrantConfig> query);

	/**
	 * 分页查询: CouponGrantConfig
	 **/
	Page<CouponGrantConfigListVo> queryListVoForApp(Pagination<CouponGrantConfig> query);

	/**
	 * 创建CouponGrantConfig
	 **/
	CouponGrantConfig addCouponGrantConfig(CouponGrantConfig couponGrantConfig);

	/**
	 * 创建CouponGrantConfig
	 **/
	CouponGrantConfigListVo addCouponGrantConfig(CouponGrantConfigBo couponGrantConfig);

	/**
	 * 更新CouponGrantConfig
	 **/
	CouponGrantConfig updateCouponGrantConfig(CouponGrantConfig couponGrantConfig);

	/**
	 * 更新CouponGrantConfig
	 **/
	CouponGrantConfigListVo updateCouponGrantConfig(CouponGrantConfigBo couponGrantConfig);

	/**
	 * 删除CouponGrantConfig
	 **/
	void removeCouponGrantConfigById(int couponGrantConfigId);

	/**
	 * 根据ID得到CouponGrantConfig
	 **/
	CouponGrantConfig getCouponGrantConfigById(int couponGrantConfigId);

	/**
	 * 根据ID得到CouponGrantConfigBo
	 **/
	CouponGrantConfigBo getCouponGrantConfigBoById(int couponGrantConfigId);

	/**
	 * 根据ID得到CouponGrantConfigVo
	 **/
	CouponGrantConfigVo getCouponGrantConfigVoById(int couponGrantConfigId);

	/**
	 * 根据ID得到CouponGrantConfigListVo
	 **/
	CouponGrantConfigListVo getListVoById(int couponGrantConfigId);

	/**
	 * 启用禁用
	 * 
	 * @param couponGrantConfigId
	 * @return
	 */
	CouponGrantConfigVo updateState(int couponGrantConfigId);

}
