/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.CouponGrantStep;

/**
 * 优惠券发放记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface CouponGrantStepDao extends JpaRepository<CouponGrantStep, Integer>, JpaSpecificationExecutor<CouponGrantStep> {

	/**
	 * 查询该配置下的发放步骤
	 * 
	 * @param couponGrantConfigId
	 * @param deleted
	 * @return
	 */
	List<CouponGrantStep> findByCouponGrantConfig_idAndDeleted(Integer couponGrantConfigId, Integer deleted);

}