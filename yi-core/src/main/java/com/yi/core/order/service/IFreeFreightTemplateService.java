/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.FreeFreightTemplateBo;
import com.yi.core.order.domain.entity.CustomFreightTemplate;
import com.yi.core.order.domain.entity.FreeFreightTemplate;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.vo.FreeFreightTemplateListVo;
import com.yi.core.order.domain.vo.FreeFreightTemplateVo;
import com.yihz.common.persistence.Pagination;

/**
 * 包邮运费模板
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IFreeFreightTemplateService {

	/**
	 * 分页查询: FreeFreightTemplate
	 * 
	 * @param query
	 * @return
	 */
	Page<FreeFreightTemplate> query(Pagination<FreeFreightTemplate> query);

	/**
	 * 分页查询: FreeFreightTemplate
	 **/
	Page<FreeFreightTemplateListVo> queryListVo(Pagination<FreeFreightTemplate> query);

	/**
	 * 创建FreeFreightTemplate
	 **/
	FreeFreightTemplate addFreeFreightTemplate(FreeFreightTemplate freeFreightTemplate);

	/**
	 * 创建FreeFreightTemplate
	 **/
	FreeFreightTemplateVo addFreeFreightTemplate(FreeFreightTemplateBo freeFreightTemplate);

	/**
	 * 更新FreeFreightTemplate
	 **/
	FreeFreightTemplate updateFreeFreightTemplate(FreeFreightTemplate freeFreightTemplate);

	/**
	 * 更新FreeFreightTemplate
	 **/
	FreeFreightTemplateVo updateFreeFreightTemplate(FreeFreightTemplateBo freeFreightTemplate);

	/**
	 * 删除FreeFreightTemplate
	 **/
	void removeFreeFreightTemplateById(int freeFreightTemplateId);

	/**
	 * 根据ID得到FreeFreightTemplateBo
	 **/
	FreeFreightTemplate getById(int freeFreightTemplateId);

	/**
	 * 根据ID得到FreeFreightTemplateVo
	 **/
	FreeFreightTemplateVo getVoById(int freeFreightTemplateId);

	/**
	 * 根据ID得到FreeFreightTemplateListVo
	 **/
	FreeFreightTemplateListVo getListVoById(int freeFreightTemplateId);

	/**
	 * 批量保存指定条件包邮
	 * 
	 * @param freightTemplateConfig
	 */
	void batchAddFreeFreightTemplate(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 批量更新指定条件包邮
	 * 
	 * @param freightTemplateConfig
	 */
	void batchUpdateFreeFreightTemplate(FreightTemplateConfig freightTemplateConfig);

	/**
	 * 批量删除指定条件包邮
	 * 
	 * @param freeFreightTemplates
	 */
	void batchDeleteFreeFreightTemplate(List<FreeFreightTemplate> freeFreightTemplates);

}
