/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.CouponGrantStepBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantStep;
import com.yi.core.activity.domain.vo.CouponGrantStepListVo;
import com.yi.core.activity.domain.vo.CouponGrantStepVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICouponGrantStepService {

	/**
	 * 分页查询: CouponGrantStep
	 **/
	Page<CouponGrantStep> query(Pagination<CouponGrantStep> query);

	/**
	 * 分页查询: CouponGrantStep
	 **/
	Page<CouponGrantStepListVo> queryListVo(Pagination<CouponGrantStep> query);

	/**
	 * 分页查询: CouponGrantStep
	 **/
	Page<CouponGrantStepListVo> queryListVoForApp(Pagination<CouponGrantStep> query);

	/**
	 * 创建CouponGrantStep
	 **/
	CouponGrantStep addCouponGrantStep(CouponGrantStep couponGrantStep);

	/**
	 * 创建CouponGrantStep
	 **/
	CouponGrantStepListVo addCouponGrantStep(CouponGrantStepBo couponGrantStep);

	/**
	 * 更新CouponGrantStep
	 **/
	CouponGrantStep updateCouponGrantStep(CouponGrantStep couponGrantStep);

	/**
	 * 更新CouponGrantStep
	 **/
	CouponGrantStepListVo updateCouponGrantStep(CouponGrantStepBo couponGrantStep);

	/**
	 * 删除CouponGrantStep
	 **/
	void removeCouponGrantStepById(int couponGrantStepId);

	/**
	 * 根据ID得到CouponGrantStep
	 **/
	CouponGrantStep getCouponGrantStepById(int couponGrantStepId);

	/**
	 * 根据ID得到CouponGrantStepBo
	 **/
	CouponGrantStepBo getCouponGrantStepBoById(int couponGrantStepId);

	/**
	 * 根据ID得到CouponGrantStepVo
	 **/
	CouponGrantStepVo getCouponGrantStepVoById(int couponGrantStepId);

	/**
	 * 根据ID得到CouponGrantStepListVo
	 **/
	CouponGrantStepListVo getListVoById(int couponGrantStepId);

	/**
	 * 批量保存发放步骤
	 * 
	 * @param CouponGrantConfig
	 * @param List<CouponGrantStep>
	 *            grantSteps
	 */
	void batchAddGrantStep(CouponGrantConfig grantConfig, Collection<CouponGrantStep> grantSteps);

	/**
	 * 批量更新发放步骤
	 * 
	 * @param CouponGrantConfig
	 * @param List<CouponGrantStep>
	 *            grantSteps
	 */
	void batchUpdateGrantStep(CouponGrantConfig grantConfig, Collection<CouponGrantStep> grantSteps);

}
