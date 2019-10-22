/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.cms.domain.entity.Position;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface PositionDao extends JpaRepository<Position, Integer>, JpaSpecificationExecutor<Position> {

	/**
	 * 根据位置类型查询位置
	 * 
	 * @param positionType 位置类型
	 * @param deleted 删除状态
	 * @return
	 */
	Position findByPositionTypeAndDeleted(Integer positionType, Integer deleted);

	/**
	 * 根据位置类型查询位置
	 * 
	 * @param positionType 位置类型
	 * @param deleted 删除状态
	 * @param id 排除的位置ID
	 * @return
	 */
	Position findByPositionTypeAndDeletedAndIdNot(Integer positionType, Integer deleted, Integer id);

	/**
	 * 根据位置类型查询位置
	 * @param positionType 位置类型
	 * @param state 状态
	 * @param deleted 删除状态
	 * @return
	 */
	Position findByPositionTypeAndStateAndDeleted(Integer positionType, Integer state, Integer deleted);

}