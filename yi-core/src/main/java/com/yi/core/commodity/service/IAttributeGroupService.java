/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.AttributeGroupBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.AttributeGroup;
import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.domain.vo.AttributeGroupVo;
import com.yihz.common.persistence.Pagination;

/**
 * 属性组
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IAttributeGroupService {

	/**
	 * 分页查询: AttributeGroup
	 * 
	 * @param query
	 * @return
	 */
	Page<AttributeGroup> query(Pagination<AttributeGroup> query);

	/**
	 * 分页查询: AttributeGroupListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<AttributeGroupListVo> queryListVo(Pagination<AttributeGroup> query);

	/**
	 * 根据Entity创建AttributeGroup
	 * 
	 * @param attributeGroup
	 * @return
	 */
	AttributeGroup addAttributeGroup(AttributeGroup attributeGroup);

	/**
	 * 根据BO创建AttributeGroup
	 * 
	 * @param attributeGroupBo
	 * @return
	 */
	AttributeGroupVo addAttributeGroup(AttributeGroupBo attributeGroupBo);

	/**
	 * 根据Entity更新AttributeGroup
	 * 
	 * @param attributeGroup
	 * @return
	 */
	AttributeGroup updateAttributeGroup(AttributeGroup attributeGroup);

	/**
	 * 根据BO更新AttributeGroup
	 * 
	 * @param attributeGroupBo
	 * @return
	 */
	AttributeGroupVo updateAttributeGroup(AttributeGroupBo attributeGroupBo);

	/**
	 * 删除AttributeGroup
	 * 
	 * @param attributeGroupId
	 */
	void removeAttributeGroupById(int attributeGroupId);

	/**
	 * 根据ID得到AttributeGroup
	 * 
	 * @param attributeGroupId
	 * @return
	 */
	AttributeGroup getById(int attributeGroupId);

	/**
	 * 根据ID得到AttributeGroupVo
	 * 
	 * @param attributeGroupId
	 * @return
	 */
	AttributeGroupVo getVoById(int attributeGroupId);

	/**
	 * 根据ID得到AttributeGroupListVo
	 * 
	 * @param attributeGroupId
	 * @return
	 */
	AttributeGroupListVo getListVoById(int attributeGroupId);

	/**
	 * 批量新增属性组
	 * 
	 * @param attributeGroups
	 */
	List<AttributeGroup> batchAddAttributeGroup(Collection<AttributeGroupBo> attributeGroups);

	/**
	 * 该商品分类属性组
	 * 
	 * @param categoryId
	 * @return
	 */
	List<AttributeGroupListVo> getAttrGroupsByCategory(int categoryId);

	/**
	 * 商品增改时 新增商品属性
	 * 
	 * @param category
	 * @param attributeGroups
	 * @return
	 */
	Map<String, Attribute> addAttributeGroupByCommodity(Category category, Collection<AttributeGroup> attributeGroups);
}
