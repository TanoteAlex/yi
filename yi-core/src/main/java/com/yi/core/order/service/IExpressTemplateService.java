/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.ExpressTemplateBo;
import com.yi.core.order.domain.entity.ExpressTemplate;
import com.yi.core.order.domain.vo.ExpressTemplateListVo;
import com.yi.core.order.domain.vo.ExpressTemplateVo;
import com.yihz.common.persistence.Pagination;

import java.util.List;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IExpressTemplateService {

	/**
	 * 根据ID得到ExpressTemplate
	 * 
	 * @param expressTemplateId
	 * @return
	 */
	ExpressTemplate getExpressTemplateById(int expressTemplateId);

	/**
	 * 根据ID得到ExpressTemplateVo
	 * 
	 * @param expressTemplateId
	 * @return
	 */
	ExpressTemplateVo getExpressTemplateVoById(int expressTemplateId);

	/**
	 * 根据ID得到ExpressTemplateListVo
	 * 
	 * @param expressTemplateId
	 * @return
	 */
	ExpressTemplateListVo getExpressTemplateListVoById(int expressTemplateId);

	/**
	 * 根据Entity创建ExpressTemplate
	 * 
	 * @param expressTemplate
	 * @return
	 */
	List<ExpressTemplateListVo> addExpressTemplate(List<ExpressTemplateBo> expressTemplate, Integer supplierId);

	/**
	 * 根据BO创建ExpressTemplate
	 * 
	 * @param expressTemplateBo
	 * @return
	 */
	ExpressTemplateListVo addExpressTemplateBo(ExpressTemplateBo expressTemplateBo);

	/**
	 * 根据Entity更新ExpressTemplate
	 * 
	 * @param expressTemplate
	 * @return
	 */
	ExpressTemplateVo updateExpressTemplate(ExpressTemplate expressTemplate);

	/**
	 * 根据BO更新ExpressTemplate
	 * 
	 * @param expressTemplateBo
	 * @return
	 */
	ExpressTemplateVo updateExpressTemplate(ExpressTemplateBo expressTemplateBo);

	/**
	 * 删除ExpressTemplate
	 * 
	 * @param expressTemplateId
	 */
	void removeExpressTemplateById(int expressTemplateId);

	/**
	 * 分页查询: ExpressTemplate
	 * 
	 * @param query
	 * @return
	 */
	Page<ExpressTemplate> query(Pagination<ExpressTemplate> query);

	/**
	 * 分页查询: ExpressTemplateListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<ExpressTemplateListVo> queryListVo(Pagination<ExpressTemplate> query);

}
