/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.IntegralCashBo;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.simple.IntegralCashSimple;
import com.yi.core.basic.domain.vo.IntegralCashListVo;
import com.yi.core.basic.domain.vo.IntegralCashVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IIntegralCashService {

	/**
	 * 分页查询: IntegralCash
	 **/
	Page<IntegralCash> query(Pagination<IntegralCash> query);

	/**
	 * 分页查询: IntegralCash
	 **/
	Page<IntegralCashListVo> queryListVo(Pagination<IntegralCash> query);

	/**
	 * 分页查询: IntegralCash
	 **/
	Page<IntegralCashListVo> queryListVoForApp(Pagination<IntegralCash> query);

	/**
	 * 创建IntegralCash
	 **/
	IntegralCash addIntegralCash(IntegralCash integralCash);

	/**
	 * 创建IntegralCash
	 **/
	IntegralCashListVo addIntegralCash(IntegralCashBo integralCash);

	/**
	 * 更新IntegralCash
	 **/
	IntegralCash updateIntegralCash(IntegralCash integralCash);

	/**
	 * 更新IntegralCash
	 **/
	IntegralCashListVo updateIntegralCash(IntegralCashBo integralCash);

	/**
	 * 删除IntegralCash
	 **/
	void removeIntegralCashById(Integer integralCashId);

	/**
	 * 根据ID得到IntegralCash
	 **/
	IntegralCash getIntegralCashById(Integer integralCashId);

	/**
	 * 根据ID得到IntegralCashBo
	 **/
	IntegralCashBo getIntegralCashBoById(Integer integralCashId);

	/**
	 * 根据ID得到IntegralCashVo
	 **/
	IntegralCashVo getIntegralCashVoById(Integer integralCashId);

	/**
	 * 根据ID得到IntegralCashListVo
	 **/
	IntegralCashListVo getListVoById(Integer integralCashId);

	/**
	 * 获取最新的积分抵现规则
	 **/
	IntegralCash getLatestIntegralCash();
	
	/**
	 * 获取最新的积分抵现规则
	 **/
	IntegralCashSimple getLatestIntegralCashSimple();
	
}
