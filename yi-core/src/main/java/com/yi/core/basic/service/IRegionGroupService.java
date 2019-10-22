/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.RegionGroupBo;
import com.yi.core.basic.domain.entity.RegionGroup;
import com.yi.core.basic.domain.vo.RegionGroupListVo;
import com.yi.core.basic.domain.vo.RegionGroupVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IRegionGroupService {

	/**
	 * 分页查询: RegionGroup
	 * 
	 * @param query
	 * @return
	 */
	Page<RegionGroup> query(Pagination<RegionGroup> query);

	/**
	 * 分页查询: RegionGroup
	 * 
	 * @param query
	 * @return
	 */
	Page<RegionGroupListVo> queryListVo(Pagination<RegionGroup> query);

	/**
	 * 创建RegionGroup
	 **/
	RegionGroup addRegionGroup(RegionGroup regionGroup);

	/**
	 * 创建RegionGroup
	 **/
	RegionGroupVo addRegionGroup(RegionGroupBo regionGroup);

	/**
	 * 更新RegionGroup
	 **/
	RegionGroup updateRegionGroup(RegionGroup regionGroup);

	/**
	 * 更新RegionGroup
	 **/
	RegionGroupVo updateRegionGroup(RegionGroupBo regionGroup);

	/**
	 * 删除RegionGroup
	 **/
	void removeRegionGroupById(int regionGroupId);

	/**
	 * 根据ID得到RegionGroup
	 **/
	RegionGroup getById(int regionGroupId);

	/**
	 * 根据ID得到RegionGroupVo
	 **/
	RegionGroupVo getVoById(int regionGroupId);

	/**
	 * 根据ID得到RegionGroupListVo
	 **/
	RegionGroupVo getListVoById(int regionGroupId);

	/**
	 * 获取地区组集合
	 * 
	 * @return
	 */
	List<RegionGroupListVo> getRegionGroupListVos();

	/**
	 * 更新状态
	 * 
	 * @param regionGroupId
	 * @return
	 */
	void updateState(int regionGroupId);

}
