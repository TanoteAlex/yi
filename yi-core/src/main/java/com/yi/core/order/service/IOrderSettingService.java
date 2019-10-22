/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.bo.OrderSettingBo;
import com.yi.core.order.domain.entity.OrderSetting;
import com.yi.core.order.domain.vo.OrderSettingListVo;
import com.yi.core.order.domain.vo.OrderSettingVo;
import com.yihz.common.persistence.Pagination;

/**
 * 订单设置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IOrderSettingService {

	/**
	 * 根据ID得到OrderSetting
	 * 
	 * @param orderSettingId
	 * @return
	 */
	OrderSetting getOrderSettingById(int orderSettingId);

	/**
	 * 根据ID得到OrderSettingVo
	 * 
	 * @param orderSettingId
	 * @return
	 */
	OrderSettingVo getOrderSettingVoById(int orderSettingId);

	/**
	 * 根据ID得到OrderSettingListVo
	 * 
	 * @param orderSettingId
	 * @return
	 */
	OrderSettingListVo getOrderSettingListVoById(int orderSettingId);

	/**
	 * 根据Entity创建OrderSetting
	 * 
	 * @param orderSetting
	 * @return
	 */
	OrderSetting addOrderSetting(OrderSetting orderSetting);

	/**
	 * 根据BO创建OrderSetting
	 * 
	 * @param orderSettingBo
	 * @return
	 */
	OrderSettingVo addOrderSetting(OrderSettingBo orderSettingBo);

	/**
	 * 根据Entity更新OrderSetting
	 * 
	 * @param orderSetting
	 * @return
	 */
	OrderSetting updateOrderSetting(OrderSetting orderSetting);

	/**
	 * 根据BO更新OrderSetting
	 * 
	 * @param orderSettingBo
	 * @return
	 */
	OrderSettingVo updateOrderSetting(OrderSettingBo orderSettingBo);

	/**
	 * 删除OrderSetting
	 * 
	 * @param orderSettingId
	 */
	void removeOrderSettingById(int orderSettingId);

	/**
	 * 分页查询: OrderSetting
	 * 
	 * @param query
	 * @return
	 */
	Page<OrderSetting> query(Pagination<OrderSetting> query);

	/**
	 * 分页查询: OrderSettingListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<OrderSettingListVo> queryListVo(Pagination<OrderSetting> query);

	/**
	 * 更新订单设置失效值
	 * 
	 * @param id
	 * @param amount
	 * @return
	 */
	OrderSettingVo updateTimeoutValue(Integer id, Integer amount);

	/**
	 * 校验是否超时
	 * 
	 * @param startTime
	 * @param endTime
	 * @param setType
	 * @return true 超时 false未超时
	 */
	boolean checkTimeout(Date startTime, Date endTime, OrderEnum setType);

	/**
	 * 根据订单设置 计算失效时间
	 * 
	 * @param date
	 * @param setType
	 * @return
	 */
	Date getInvalidTimeBySetType(Date date, OrderEnum setType);

}
