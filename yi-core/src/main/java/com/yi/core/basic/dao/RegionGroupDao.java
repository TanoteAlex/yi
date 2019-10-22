/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.RegionGroup;

/**
 * 区域
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface RegionGroupDao extends JpaRepository<RegionGroup, Integer>, JpaSpecificationExecutor<RegionGroup> {

	/**
	 * 重复校验
	 * 
	 * @param name
	 * @param deleted
	 * @return
	 */
	int countByNameAndDeleted(String name, Integer deleted);

	/**
	 * 重复校验
	 * 
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByNameAndDeletedAndIdNot(String name, Integer deleted, Integer id);

	/**
	 * 获取地区组集合
	 * 
	 * @param state
	 * @param deleted
	 * @return
	 */
	List<RegionGroup> findByStateAndDeletedOrderBySortAsc(Integer state, Integer deleted);

}