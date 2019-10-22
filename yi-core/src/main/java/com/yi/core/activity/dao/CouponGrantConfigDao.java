/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.CouponGrantConfig;

/**
 * 优惠券发放配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface CouponGrantConfigDao extends JpaRepository<CouponGrantConfig, Integer>, JpaSpecificationExecutor<CouponGrantConfig> {

}