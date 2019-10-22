/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.AreaBo;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.vo.AreaListVo;
import com.yi.core.basic.domain.vo.AreaVo;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IAreaService {

	/**
	 * 根据ID得到Area
	 *
	 * @param areaId
	 * @return
	 */
	Area getAreaById(int areaId);

	/**
	 * 根据ID得到AreaVo
	 *
	 * @param areaId
	 * @return
	 */
	AreaVo getAreaVoById(int areaId);

	/**
	 * 根据ID得到AreaListVo
	 *
	 * @param areaId
	 * @return
	 */
	AreaVo getAreaListVoById(int areaId);

	/**
	 * 根据Entity创建Area
	 *
	 * @param area
	 * @return
	 */
	Area addArea(Area area);

	/**
	 * 根据BO创建Area
	 *
	 * @param areaBo
	 * @return
	 */
	AreaVo addArea(AreaBo areaBo);

	/**
	 * 根据Entity更新Area
	 *
	 * @param area
	 * @return
	 */
	Area updateArea(Area area);

	/**
	 * 根据BO更新Area
	 *
	 * @param areaBo
	 * @return
	 */
	AreaVo updateArea(AreaBo areaBo);

	/**
	 * 删除Area
	 *
	 * @param areaId
	 */
	void removeAreaById(int areaId);

	/**
	 * 分页查询: Area
	 *
	 * @param query
	 * @return
	 */
	Page<Area> query(Pagination<Area> query);

	/**
	 * 分页查询: AreaListVo
	 *
	 * @param query
	 * @return
	 */
	Page<AreaListVo> queryListVo(Pagination<Area> query);

	/**
	 * 查询地区所有数据
	 *
	 * @param parentId
	 * @return
	 */
	List<Area> getAreasByParentId(Integer parentId);

	/**
	 * 查询地区所有数据
	 *
	 * @param parentId
	 * @return
	 */
	List<AreaListVo> getAllAreas();

	/**
	 * 获取省
	 * 
	 * @return
	 */
	List<Area> getProvinces();

	Area getByAreaCode(String areaCode);

}
