/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.OrderSetting;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface OrderSettingDao extends JpaRepository<OrderSetting, Integer>, JpaSpecificationExecutor<OrderSetting> {

	/**
	 * 根据设置类型 查询订单设置数据
	 * 
	 * @param setType
	 * @param deleted
	 * @return
	 */
	OrderSetting findBySetTypeAndDeleted(Integer setType, Integer deleted);

}