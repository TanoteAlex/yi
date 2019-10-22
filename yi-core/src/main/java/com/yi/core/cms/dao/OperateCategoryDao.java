/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cms.domain.entity.OperateCategory;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface OperateCategoryDao extends JpaRepository<OperateCategory, Integer>, JpaSpecificationExecutor<OperateCategory> {

	List<OperateCategory> findByStateAndDeleted(Integer state, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param category
	 * @param name
	 * @param deleted
	 * @return
	 */
	int countByParentAndCategoryNameAndDeleted(OperateCategory category, String name, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param category
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByParentAndCategoryNameAndDeletedAndIdNot(OperateCategory category, String name, Integer deleted, Integer id);

	/**
	 * 根据父类查询运营分类
	 * 
	 * @param operateCategory
	 * @param deleted
	 * @return
	 */
	List<OperateCategory> findByParentAndDeletedOrderBySortAsc(OperateCategory operateCategory, Integer deleted);

}