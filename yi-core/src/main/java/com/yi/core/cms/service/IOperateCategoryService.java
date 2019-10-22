/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.OperateCategoryBo;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.cms.domain.vo.OperateCategoryListVo;
import com.yi.core.cms.domain.vo.OperateCategoryVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IOperateCategoryService {

	Page<OperateCategory> query(Pagination<OperateCategory> query);

	/**
	 * 创建OperateCategory
	 **/
	OperateCategoryListVo addOperateCategory(OperateCategoryBo operateCategory);

	/**
	 * 更新OperateCategory
	 **/
	OperateCategoryVo updateOperateCategory(OperateCategoryBo operateCategory);

	/**
	 * 删除OperateCategory
	 **/
	void removeOperateCategoryById(int operateCategoryId);

	/**
	 * 根据ID得到OperateCategory
	 **/
	OperateCategory getOperateCategoryById(int operateCategoryId);

	/**
	 * 根据ID得到OperateCategoryVo
	 **/
	OperateCategoryVo getOperateCategoryVoById(int operateCategoryId);

	/**
	 * 根据ID得到OperateCategoryListVo
	 **/
	OperateCategoryVo getListVoById(int operateCategoryId);

	/**
	 * 分页查询: OperateCategory
	 **/
	Page<OperateCategoryListVo> queryListVo(Pagination<OperateCategory> query);

	/**
	 * 查询所有运营分类
	 * 
	 * @return
	 */
	List<OperateCategoryListVo> getAll();

	/**
	 * 获取首级及二级 运营分类集合
	 * 
	 * @return
	 */
	List<OperateCategoryListVo> getOperateCategoryListVosForApp();

	/**
	 * 根据ID 获取运营分类
	 * 
	 * @param ids
	 * @return
	 */
	List<OperateCategory> getOperateCategoriesByIds(List<Integer> ids);

	List<OperateCategory> getOperateCategoriesByCommodityId(Integer id);

}
