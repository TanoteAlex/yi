/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.Category;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface CategoryDao extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

	List<Category> findByParentIsNullAndDeleted(int Deleted);

	// @Query("select c from Category c where c.deleted=?1 order by c.sort")
	// List<Category> findByDeleted(int Deleted);

	List<Category> findByDeletedOrderBySortAsc(int Deleted);

	/**
	 * 校验重复
	 * 
	 * @param category
	 * @param name
	 * @param deleted
	 * @return
	 */
	int countByParentAndCategoryNameAndDeleted(Category category, String name, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param category
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByParentAndCategoryNameAndDeletedAndIdNot(Category category, String name, Integer deleted, Integer id);
}