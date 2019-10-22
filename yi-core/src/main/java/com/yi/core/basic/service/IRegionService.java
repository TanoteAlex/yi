/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.RegionBo;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.vo.RegionListVo;
import com.yi.core.basic.domain.vo.RegionVo;
import com.yihz.common.persistence.Pagination;

/**
 * 地区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IRegionService {

	/**
	 * 分页查询: Region
	 * 
	 * @param query
	 * @return
	 */
	Page<Region> query(Pagination<Region> query);

	/**
	 * 分页查询: RegionListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<RegionListVo> queryListVo(Pagination<Region> query);

	/**
	 * 根据Entity创建Region
	 * 
	 * @param region
	 * @return
	 */
	Region addRegion(Region region);

	/**
	 * 根据BO创建Region
	 * 
	 * @param regionBo
	 * @return
	 */
	RegionVo addRegion(RegionBo regionBo);

	/**
	 * 根据Entity更新Region
	 * 
	 * @param region
	 * @return
	 */
	Region updateRegion(Region region);

	/**
	 * 根据BO更新Region
	 * 
	 * @param regionBo
	 * @return
	 */
	RegionVo updateRegion(RegionBo regionBo);

	/**
	 * 删除Region
	 * 
	 * @param regionId
	 */
	void removeRegionById(int regionId);

	/**
	 * 根据ID得到Region
	 * 
	 * @param regionId
	 * @return
	 */
	Region getById(int regionId);

	/**
	 * 根据ID得到RegionVo
	 * 
	 * @param regionId
	 * @return
	 */
	RegionVo getVoById(int regionId);

	/**
	 * 根据ID得到RegionListVo
	 * 
	 * @param regionId
	 * @return
	 */
	RegionListVo getListVoById(int regionId);

	/**
	 * 批量保存 地区
	 * 
	 * @param regionGroup
	 */
	void batchAddRegion(RegionGroup regionGroup);

	/**
	 * 批量更新 地区
	 * 
	 * @param regionGroup
	 */
	void batchUpdateRegion(RegionGroup regionGroup);

	List<RegionVo> checkCity(String province);

	List<RegionVo> addList(List<RegionBo> regionBos);

	/**
	 * 通过id的集合 获取 对象集合
	 * 
	 * @param ids
	 * @return
	 */
	List<Region> getRegionsByIds(List<Integer> ids);
}
