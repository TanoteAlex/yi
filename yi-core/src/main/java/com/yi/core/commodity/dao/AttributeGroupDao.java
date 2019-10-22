/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.AttributeGroup;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface AttributeGroupDao extends JpaRepository<AttributeGroup, Integer>, JpaSpecificationExecutor<AttributeGroup> {

	/**
	 * 根据分类查询属性组
	 * 
	 * @param categoryId
	 * @param deleted
	 * @return
	 */
	List<AttributeGroup> findByCategoryIdAndDeleted(int categoryId, int deleted);

	/**
	 * 校验重复
	 * 
	 * @param name
	 * @param categoryId
	 * @param deleted
	 * @return
	 */
	int countByGroupNameAndCategory_idAndDeleted(String name, Integer categoryId, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param name
	 * @param categoryId
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByGroupNameAndCategory_idAndDeletedAndIdNot(String name, Integer categoryId, Integer deleted, Integer id);
}