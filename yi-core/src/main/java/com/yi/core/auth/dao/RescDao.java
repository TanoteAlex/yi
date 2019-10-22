/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.auth.domain.entity.Resc;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface RescDao extends JpaRepository<Resc, Integer>, JpaSpecificationExecutor<Resc> {

	/**
	 * 校验重复
	 *
	 * @param parent
	 * @param name
	 * @param deleted
	 * @return
	 */
	int countByParentAndNameAndDeleted(Resc parent, String name, Integer deleted);

	/**
	 * 校验重复
	 *
	 * @param parent
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByParentAndNameAndDeletedAndIdNot(Resc parent, String name, Integer deleted, Integer id);

	/**
	 * 获取一级资源列表
	 * 
	 * @return
	 */
	List<Resc> findByParentAndDeleted(Resc parent, Integer deleted);

}