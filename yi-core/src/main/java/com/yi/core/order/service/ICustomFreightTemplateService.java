/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.CustomFreightTemplateBo;
import com.yi.core.order.domain.entity.CustomFreightTemplate;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.vo.CustomFreightTemplateListVo;
import com.yi.core.order.domain.vo.CustomFreightTemplateVo;
import com.yihz.common.persistence.Pagination;

/**
 * 自定义运费模板
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICustomFreightTemplateService {

	/**
	 * 分页查询: CustomFreightTemplate
	 * 
	 * @param query
	 * @return
	 */
	Page<CustomFreightTemplate> query(Pagination<CustomFreightTemplate> query);

	/**
	 * 分页查询: CustomFreightTemplate
	 **/
	Page<CustomFreightTemplateListVo> queryListVo(Pagination<CustomFreightTemplate> query);

	/**
	 * 创建CustomFreightTemplate
	 **/
	CustomFreightTemplateVo addCustomFreightTemplate(CustomFreightTemplateBo customFreightTemplate);

	/**
	 * 创建CustomFreightTemplate
	 **/
	CustomFreightTemplate addCustomFreightTemplate(CustomFreightTemplate customFreightTemplate);

	/**
	 * 更新CustomFreightTemplate
	 **/
	CustomFreightTemplate updateCustomFreightTemplate(CustomFreightTemplate customFreightTemplate);

	/**
	 * 更新CustomFreightTemplate
	 **/
	CustomFreightTemplateVo updateCustomFreightTemplate(CustomFreightTemplateBo customFreightTemplate);

	/**
	 * 删除CustomFreightTemplate
	 **/
	void removeCustomFreightTemplateById(int customFreightTemplateId);

	/**
	 * 根据ID得到CustomFreightTemplate
	 **/
	CustomFreightTemplate getById(int customFreightTemplateId);

	/**
	 * 根据ID得到CustomFreightTemplateVo
	 **/
	CustomFreightTemplateVo getCustomFreightTemplateVoById(int customFreightTemplateId);

	/**
	 * 根据ID得到CustomFreightTemplateListVo
	 **/
	CustomFreightTemplateListVo getListVoById(int customFreightTemplateId);

	/**
	 * 批量保存自定义运费
	 * 
	 * @param freightTemplateConfig
	 */
	void batchAddCustomFreightTemplate(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 批量更新自定义运费
	 * 
	 * @param freightTemplateConfig
	 */
	void batchUpdateCustomFreightTemplate(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 批量删除自定义运费
	 * 
	 * @param customFreightTemplates
	 */
	void batchDeleteCustomFreightTemplate(List<CustomFreightTemplate> customFreightTemplates);

}
