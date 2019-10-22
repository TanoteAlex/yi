/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.simple.AreaColumnSimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.AreaColumnBo;
import com.yi.core.activity.domain.entity.AreaColumn;
import com.yi.core.activity.domain.vo.AreaColumnListVo;
import com.yi.core.activity.domain.vo.AreaColumnVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IAreaColumnService {

	/**
	 * 分页查询: AreaColumn
	 **/
	Page<AreaColumn> query(Pagination<AreaColumn> query);

	/**
	 * 分页查询: AreaColumn
	 **/
	Page<AreaColumnListVo> queryListVo(Pagination<AreaColumn> query);

	/**
	 * 分页查询: AreaColumn
	 **/
	Page<AreaColumnListVo> queryListVoForApp(Pagination<AreaColumn> query);

	/**
	 * 创建AreaColumn
	 **/
	AreaColumn addAreaColumn(AreaColumn areaColumn);

	/**
	 * 创建AreaColumn
	 **/
	AreaColumnListVo addAreaColumn(AreaColumnBo areaColumn);

	/**
	 * 更新AreaColumn
	 **/
	AreaColumn updateAreaColumn(AreaColumn areaColumn);

	/**
	 * 更新AreaColumn
	 **/
	AreaColumnListVo updateAreaColumn(AreaColumnBo areaColumn);

	/**
	 * 删除AreaColumn
	 **/
	void removeAreaColumnById(Integer areaColumnId);

	/**
	 * 根据ID得到AreaColumn
	 **/
	AreaColumn getAreaColumnById(Integer areaColumnId);

	/**
	 * 根据ID得到AreaColumnBo
	 **/
	AreaColumnBo getAreaColumnBoById(Integer areaColumnId);

	/**
	 * 根据ID得到AreaColumnVo
	 **/
	AreaColumnVo getAreaColumnVoById(Integer areaColumnId);

	/**
	 * 根据ID得到AreaColumnListVo
	 **/
	AreaColumnListVo getListVoById(Integer areaColumnId);

	EntityListVoBoSimpleConvert<AreaColumn, AreaColumnBo, AreaColumnVo, AreaColumnSimple, AreaColumnListVo> getAreaColumnConvert();

}
