/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.FreightTemplateConfigBo;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.vo.FreightTemplateConfigListVo;
import com.yi.core.order.domain.vo.FreightTemplateConfigVo;
import com.yihz.common.persistence.Pagination;

/**
 * 运费模板配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IFreightTemplateConfigService {

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	Page<FreightTemplateConfig> query(Pagination<FreightTemplateConfig> query);

	/**
	 * 分页查询: FreightTemplateConfig
	 * 
	 * @param query
	 * @return
	 */
	Page<FreightTemplateConfigListVo> queryListVo(Pagination<FreightTemplateConfig> query);

	/**
	 * 创建FreightTemplateConfig
	 **/
	FreightTemplateConfig addFreightTemplateConfig(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 创建FreightTemplateConfig
	 **/
	FreightTemplateConfigVo addFreightTemplateConfig(FreightTemplateConfigBo freightTemplateConfig);

	/**
	 * 更新FreightTemplateConfig
	 **/
	FreightTemplateConfig updateFreightTemplateConfig(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 更新FreightTemplateConfig
	 **/
	FreightTemplateConfigVo updateFreightTemplateConfig(FreightTemplateConfigBo freightTemplateConfig);

	/**
	 * 删除FreightTemplateConfig
	 **/
	void removeFreightTemplateConfigById(int freightTemplateConfigId);

	/**
	 * 根据ID得到FreightTemplateConfig
	 **/
	FreightTemplateConfig getById(int freightTemplateConfigId);

	/**
	 * 根据ID得到FreightTemplateConfigVo
	 **/
	FreightTemplateConfigVo getVoById(int freightTemplateConfigId);

	/**
	 * 根据ID得到FreightTemplateConfigListVo
	 **/
	FreightTemplateConfigListVo getListVoById(int freightTemplateConfigId);

	/**
	 * 计算运费
	 * 
	 * @param freightTemplateConfig
	 * @param saleOrderItem
	 * @param province
	 * @param city
	 * @return
	 */
	BigDecimal calculateFreight(FreightTemplateConfig freightTemplateConfig, SaleOrderItem saleOrderItem, String province, String city);

}
