/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.auth.domain.entity.Dept;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface DeptDao extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept> {

	/**
	 * 校验重复
	 *
	 * @param parent
	 * @param name
	 * @param deleted
	 * @return
	 */
	int countByParentAndDeptNameAndDeleted(Dept parent, String name, Integer deleted);

	/**
	 * 校验重复
	 *
	 * @param parent
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByParentAndDeptNameAndDeletedAndIdNot(Dept parent, String name, Integer deleted, Integer id);

}