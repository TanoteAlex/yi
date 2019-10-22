/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.DeptBo;
import com.yi.core.auth.domain.entity.Dept;
import com.yi.core.auth.domain.simple.DeptSimple;
import com.yi.core.auth.domain.vo.DeptListVo;
import com.yi.core.auth.domain.vo.DeptVo;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IDeptService {

	/**
	 * 根据ID得到Dept
	 * 
	 * @param deptId
	 * @return
	 */
	Dept getDeptById(int deptId);

	/**
	 * 根据ID得到DeptVo
	 * 
	 * @param deptId
	 * @return
	 */
	DeptVo getDeptVoById(int deptId);

	/**
	 * 根据ID得到DeptListVo
	 * 
	 * @param deptId
	 * @return
	 */
	DeptListVo getDeptListVoById(int deptId);

	/**
	 * 根据Entity创建Dept
	 * 
	 * @param dept
	 * @return
	 */
	Dept addDept(Dept dept);

	/**
	 * 根据BO创建Dept
	 * 
	 * @param deptBo
	 * @return
	 */
	DeptVo addDept(DeptBo deptBo);

	/**
	 * 根据Entity更新Dept
	 * 
	 * @param dept
	 * @return
	 */
	Dept updateDept(Dept dept);

	/**
	 * 根据BO更新Dept
	 * 
	 * @param deptBo
	 * @return
	 */
	DeptVo updateDept(DeptBo deptBo);

	/**
	 * 删除Dept
	 * 
	 * @param deptId
	 */
	void removeDeptById(int deptId);

	/**
	 * 分页查询: Dept
	 * 
	 * @param query
	 * @return
	 */
	Page<Dept> query(Pagination<Dept> query);

	/**
	 * 分页查询: DeptListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<DeptListVo> queryListVo(Pagination<Dept> query);

	/**
	 * 获取dept 转换器
	 * 
	 * @return
	 */
	EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> getDeptConvert();

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<DeptListVo> getAll();
}
