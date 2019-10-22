/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import java.util.Collection;

import com.yi.core.activity.domain.simple.AreaColumnCommoditySimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.AreaColumnCommodityBo;
import com.yi.core.activity.domain.entity.AreaColumn;
import com.yi.core.activity.domain.entity.AreaColumnCommodity;
import com.yi.core.activity.domain.entity.AreaColumnCommodityId;
import com.yi.core.activity.domain.vo.AreaColumnCommodityListVo;
import com.yi.core.activity.domain.vo.AreaColumnCommodityVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IAreaColumnCommodityService {

	/**
	 * 分页查询: AreaColumnCommodity
	 **/
	Page<AreaColumnCommodity> query(Pagination<AreaColumnCommodity> query);

	/**
	 * 分页查询: AreaColumnCommodity
	 **/
	Page<AreaColumnCommodityListVo> queryListVo(Pagination<AreaColumnCommodity> query);

	/**
	 * 分页查询: AreaColumnCommodity
	 **/
	Page<AreaColumnCommodityListVo> queryListVoForApp(Pagination<AreaColumnCommodity> query);

	/**
	 * 创建AreaColumnCommodity
	 **/
	AreaColumnCommodity addAreaColumnCommodity(AreaColumnCommodity areaColumnCommodity);

	/**
	 * 创建AreaColumnCommodity
	 **/
	AreaColumnCommodityListVo addAreaColumnCommodity(AreaColumnCommodityBo areaColumnCommodity);

	/**
	 * 更新AreaColumnCommodity
	 **/
	AreaColumnCommodity updateAreaColumnCommodity(AreaColumnCommodity areaColumnCommodity);

	/**
	 * 更新AreaColumnCommodity
	 **/
	AreaColumnCommodityListVo updateAreaColumnCommodity(AreaColumnCommodityBo areaColumnCommodity);

	/**
	 * 删除AreaColumnCommodity
	 **/
	void removeAreaColumnCommodityById(AreaColumnCommodityId areaColumnCommodityId);

	/**
	 * 根据ID得到AreaColumnCommodity
	 **/
	AreaColumnCommodity getAreaColumnCommodityById(AreaColumnCommodityId areaColumnCommodityId);

	/**
	 * 根据ID得到AreaColumnCommodityBo
	 **/
	AreaColumnCommodityBo getAreaColumnCommodityBoById(AreaColumnCommodityId areaColumnCommodityId);

	/**
	 * 根据ID得到AreaColumnCommodityVo
	 **/
	AreaColumnCommodityVo getAreaColumnCommodityVoById(AreaColumnCommodityId areaColumnCommodityId);

	/**
	 * 根据ID得到AreaColumnCommodityListVo
	 **/
	AreaColumnCommodityListVo getListVoById(AreaColumnCommodityId areaColumnCommodityId);
	
	/**
	 * 批量新增专区栏目商品
	 * 
	 * @param areaColumn
	 * @param areaColumnCommodities
	 */
	void batchAddAreaColumnCommodity(AreaColumn areaColumn, Collection<AreaColumnCommodity> areaColumnCommodities);

	/**
	 * 批量修改专区栏目商品
	 * 
	 * @param recommend
	 * @param recommendCommodities
	 */
	void batchUpdateAreaColumnCommodity(AreaColumn areaColumn, Collection<AreaColumnCommodity> areaColumnCommodities);

	/**
	 * 删除专区栏目商品
	 * 
	 * @param areaColumnId
	 */
	void removeByAreaColumn(Integer areaColumnId);

	/**
	 * 删除专区栏目商品
	 * 
	 * @param commodityId
	 */
	void removeByCommodity(Integer commodityId);

	public EntityListVoBoSimpleConvert<AreaColumnCommodity, AreaColumnCommodityBo, AreaColumnCommodityVo, AreaColumnCommoditySimple, AreaColumnCommodityListVo> getAreaColumnCommodityConvert();
}
