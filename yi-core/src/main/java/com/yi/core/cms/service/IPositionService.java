/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.PositionBo;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.vo.PositionListVo;
import com.yi.core.cms.domain.vo.PositionVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IPositionService {

	Page<Position> query(Pagination<Position> query);

	/**
	 * 创建Position
	 **/
	PositionVo addPosition(PositionBo position);

	/**
	 * 更新Position
	 **/
	PositionVo updatePosition(Position position);

	/**
	 * 删除Position
	 **/
	void removePositionById(int positionId);

	/**
	 * 根据ID得到PositionVo
	 **/
	PositionVo getPositionVoById(int positionId);

	/**
	 * 根据ID得到PositionListVo
	 **/
	PositionListVo getListVoById(int positionId);

	/**
	 * 分页查询: Position
	 **/
	Page<PositionListVo> queryListVo(Pagination<Position> query);

	/**
	 * 禁用
	 **/
	PositionVo updateStateDisable(int id);

	/**
	 * 启用
	 **/
	PositionVo updateStateEnable(int id);

	/**
	 * 根据位置类型 查询位置
	 * 
	 * @param positionType
	 * @return
	 */
	Position getPositionByPositionType(Integer positionType);

}