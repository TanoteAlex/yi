/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.AttributeBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.AttributeGroup;
import com.yi.core.commodity.domain.vo.AttributeListVo;
import com.yi.core.commodity.domain.vo.AttributeVo;
import com.yihz.common.persistence.Pagination;

/**
 * 属性
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IAttributeService {

	/**
	 * 分页查询: Attribute
	 * 
	 * @param query
	 * @return
	 */
	Page<Attribute> query(Pagination<Attribute> query);

	/**
	 * 分页查询: AttributeListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AttributeListVo> queryListVo(Pagination<Attribute> query);

	/**
	 * 根据Entity创建Attribute
	 * 
	 * @param attribute
	 * @return
	 */
	Attribute addAttribute(Attribute attribute);

	/**
	 * 根据BO创建Attribute
	 * 
	 * @param attributeBo
	 * @return
	 */
	AttributeVo addAttribute(AttributeBo attributeBo);

	/**
	 * 根据Entity更新Attribute
	 * 
	 * @param attribute
	 * @return
	 */
	Attribute updateAttribute(Attribute attribute);

	/**
	 * 根据BO更新Attribute
	 * 
	 * @param attributeBo
	 * @return
	 */
	AttributeVo updateAttribute(AttributeBo attributeBo);

	/**
	 * 删除Attribute
	 * 
	 * @param attributeId
	 */
	void removeAttributeById(int attributeId);

	/**
	 * 根据ID得到Attribute
	 * 
	 * @param attributeId
	 * @return
	 */
	Attribute getById(int attributeId);

	/**
	 * 根据ID得到AttributeVo
	 * 
	 * @param attributeId
	 * @return
	 */
	AttributeVo getVoById(int attributeId);

	/**
	 * 根据ID得到AttributeListVo
	 * 
	 * @param attributeId
	 * @return
	 */
	AttributeListVo getListVoById(int attributeId);

	/**
	 * 批量新增属性
	 * 
	 * @param attributeGroup
	 * @param attributes
	 */
	void batchAddAttribute(AttributeGroup attributeGroup, Collection<Attribute> attributes);

	/**
	 * 批量更新属性
	 * 
	 * @param attributeGroup
	 * @param attributes
	 */
	void batchUpdateAttribute(AttributeGroup attributeGroup, Collection<Attribute> attributes);

	/**
	 * 批量删除属性
	 * 
	 * @param attributes
	 */
	void batchDeleteAttribute(Collection<Attribute> attributes);

}
