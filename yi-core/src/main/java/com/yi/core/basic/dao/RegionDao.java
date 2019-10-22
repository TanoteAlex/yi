/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.Region;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface RegionDao extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

	/**
	 * 根据地区组 查询地区集合
	 * 
	 * @param regionGroupId
	 * @param deleted
	 * @return
	 */
	Set<Region> findByRegionGroup_idAndDeleted(Integer regionGroupId, Integer deleted);
}