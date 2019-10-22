/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.CategoryBo;
import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.domain.vo.CategoryListVo;
import com.yi.core.commodity.domain.vo.CategoryVo;
import com.yihz.common.persistence.Pagination;

/**
 * 商品分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICategoryService {

	/**
	 * 根据ID得到Category
	 * 
	 * @param categoryId
	 * @return
	 */
	Category getCategoryById(int categoryId);

	/**
	 * 根据ID得到CategoryVo
	 * 
	 * @param categoryId
	 * @return
	 */
	CategoryVo getCategoryVoById(int categoryId);

	/**
	 * 根据ID得到CategoryListVo
	 * 
	 * @param categoryId
	 * @return
	 */
	CategoryListVo getCategoryListVoById(int categoryId);

	/**
	 * 根据Entity创建Category
	 * 
	 * @param category
	 * @return
	 */
	Category addCategory(Category category);

	/**
	 * 根据BO创建Category
	 * 
	 * @param categoryBo
	 * @return
	 */
	CategoryVo addCategory(CategoryBo categoryBo);

	/**
	 * 根据Entity更新Category
	 * 
	 * @param category
	 * @return
	 */
	Category updateCategory(Category category);

	/**
	 * 根据BO更新Category
	 * 
	 * @param categoryBo
	 * @return
	 */
	CategoryVo updateCategory(CategoryBo categoryBo);

	/**
	 * 删除Category
	 * 
	 * @param categoryId
	 */
	void removeCategoryById(int categoryId);

	/**
	 * 分页查询: Category
	 * 
	 * @param query
	 * @return
	 */
	Page<Category> query(Pagination<Category> query);

	/**
	 * 分页查询: CategoryListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CategoryListVo> queryListVo(Pagination<Category> query);

    List<CategoryVo> getCommodityCategory();

    List<CategoryVo> getAll();
}
