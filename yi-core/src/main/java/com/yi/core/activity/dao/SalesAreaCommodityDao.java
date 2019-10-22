/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.SalesAreaCommodity;
import com.yi.core.activity.domain.entity.SalesAreaCommodityId;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface SalesAreaCommodityDao extends JpaRepository<SalesAreaCommodity, SalesAreaCommodityId>, JpaSpecificationExecutor<SalesAreaCommodity> {

	/**
	 * 根据商品获取销售专区商品
	 * 
	 * @param commodityId
	 * @return
	 */
	List<SalesAreaCommodity> findByCommodity_id(int commodityId);

	/**
	 * 根据销售专区 获取销售专区商品
	 * 
	 * @param salesAreaId
	 * @return
	 */
	List<SalesAreaCommodity> findBySalesArea_id(int salesAreaId);

	SalesAreaCommodity findBySalesArea_IdAndCommodity_Id(int salesAreaId, int commodityId);

}