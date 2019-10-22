/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.PaymentConfigBo;
import com.yi.core.basic.domain.entity.PaymentConfig;
import com.yi.core.basic.domain.vo.PaymentConfigListVo;
import com.yi.core.basic.domain.vo.PaymentConfigVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IPaymentConfigService {

	/**
	 * 根据ID得到PaymentConfig
	 * 
	 * @param paymentConfigId
	 * @return
	 */
	PaymentConfig getPaymentConfigById(int paymentConfigId);

	/**
	 * 根据ID得到PaymentConfigVo
	 * 
	 * @param paymentConfigId
	 * @return
	 */
	PaymentConfigVo getPaymentConfigVoById(int paymentConfigId);

	/**
	 * 根据ID得到PaymentConfigListVo
	 * 
	 * @param paymentConfigId
	 * @return
	 */
	PaymentConfigVo getPaymentConfigListVoById(int paymentConfigId);

	/**
	 * 根据Entity创建PaymentConfig
	 * 
	 * @param paymentConfig
	 * @return
	 */
	PaymentConfigVo addPaymentConfig(PaymentConfig paymentConfig);

	/**
	 * 根据BO创建PaymentConfig
	 * 
	 * @param paymentConfigBo
	 * @return
	 */
	PaymentConfigVo addPaymentConfig(PaymentConfigBo paymentConfigBo);

	/**
	 * 根据Entity更新PaymentConfig
	 * 
	 * @param paymentConfig
	 * @return
	 */
	PaymentConfigVo updatePaymentConfig(PaymentConfig paymentConfig);

	/**
	 * 根据BO更新PaymentConfig
	 * 
	 * @param paymentConfigBo
	 * @return
	 */
	PaymentConfigVo updatePaymentConfig(PaymentConfigBo paymentConfigBo);

	/**
	 * 删除PaymentConfig
	 * 
	 * @param paymentConfigId
	 */
	void removePaymentConfigById(int paymentConfigId);

	/**
	 * 分页查询: PaymentConfig
	 * 
	 * @param query
	 * @return
	 */
	Page<PaymentConfig> query(Pagination<PaymentConfig> query);

	/**
	 * 分页查询: PaymentConfigListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<PaymentConfigListVo> queryListVo(Pagination<PaymentConfig> query);

}
