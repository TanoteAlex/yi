/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import java.util.Collection;

import com.yi.core.activity.domain.simple.SalesAreaCommoditySimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.SalesAreaCommodityBo;
import com.yi.core.activity.domain.entity.SalesArea;
import com.yi.core.activity.domain.entity.SalesAreaCommodity;
import com.yi.core.activity.domain.entity.SalesAreaCommodityId;
import com.yi.core.activity.domain.vo.SalesAreaCommodityListVo;
import com.yi.core.activity.domain.vo.SalesAreaCommodityVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ISalesAreaCommodityService {

	/**
	 * 分页查询: SalesAreaCommodity
	 **/
	Page<SalesAreaCommodity> query(Pagination<SalesAreaCommodity> query);

	/**
	 * 分页查询: SalesAreaCommodity
	 **/
	Page<SalesAreaCommodityListVo> queryListVo(Pagination<SalesAreaCommodity> query);

	/**
	 * 分页查询: SalesAreaCommodity
	 **/
	Page<SalesAreaCommodityListVo> queryListVoForApp(Pagination<SalesAreaCommodity> query);

	/**
	 * 创建SalesAreaCommodity
	 **/
	SalesAreaCommodity addSalesAreaCommodity(SalesAreaCommodity salesAreaCommodity);

	/**
	 * 创建SalesAreaCommodity
	 **/
	SalesAreaCommodityListVo addSalesAreaCommodity(SalesAreaCommodityBo salesAreaCommodity);

	/**
	 * 更新SalesAreaCommodity
	 **/
	SalesAreaCommodity updateSalesAreaCommodity(SalesAreaCommodity salesAreaCommodity);

	/**
	 * 更新SalesAreaCommodity
	 **/
	SalesAreaCommodityListVo updateSalesAreaCommodity(SalesAreaCommodityBo salesAreaCommodity);

	/**
	 * 删除SalesAreaCommodity
	 **/
	void removeSalesAreaCommodityById(SalesAreaCommodityId salesAreaCommodityId);

	/**
	 * 根据ID得到SalesAreaCommodity
	 **/
	SalesAreaCommodity getSalesAreaCommodityById(SalesAreaCommodityId salesAreaCommodityId);

	/**
	 * 根据ID得到SalesAreaCommodityBo
	 **/
	SalesAreaCommodityBo getSalesAreaCommodityBoById(SalesAreaCommodityId salesAreaCommodityId);

	/**
	 * 根据ID得到SalesAreaCommodityVo
	 **/
	SalesAreaCommodityVo getSalesAreaCommodityVoById(SalesAreaCommodityId salesAreaCommodityId);

	/**
	 * 根据ID得到SalesAreaCommodityListVo
	 **/
	SalesAreaCommodityListVo getListVoById(SalesAreaCommodityId salesAreaCommodityId);

	/**
	 * 批量新增销售专区商品
	 * 
	 * @param salesArea
	 * @param salesAreaCommodities
	 */
	void batchAddSalesAreaCommodity(SalesArea salesArea, Collection<SalesAreaCommodity> salesAreaCommodities);

	/**
	 * 批量修改销售专区商品
	 * 
	 * @param recommend
	 * @param recommendCommodities
	 */
	void batchUpdateSalesAreaCommodity(SalesArea salesArea, Collection<SalesAreaCommodity> salesAreaCommodities);

	/**
	 * 删除销售专区商品
	 * 
	 * @param salesAreaId
	 */
	void removeBySalesArea(Integer salesAreaId);

	/**
	 * 删除销售专区商品
	 * 
	 * @param commodityId
	 */
	void removeByCommodity(Integer commodityId);

	EntityListVoBoSimpleConvert<SalesAreaCommodity, SalesAreaCommodityBo, SalesAreaCommodityVo, SalesAreaCommoditySimple, SalesAreaCommodityListVo> getSalesAreaCommodityConvert();

}
