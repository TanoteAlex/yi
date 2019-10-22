/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.AreaColumnCommodity;
import com.yi.core.activity.domain.entity.AreaColumnCommodityId;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public interface AreaColumnCommodityDao extends JpaRepository<AreaColumnCommodity, AreaColumnCommodityId>, JpaSpecificationExecutor<AreaColumnCommodity> {

    /**
     * 根据商品获取专区栏目商品
     *
     * @param commodityId
     * @return
     */
    List<AreaColumnCommodity> findByCommodity_id(int commodityId);

    /**
     * 根据专区栏目 获取专区栏目商品
     *
     * @param areaColumnId
     * @return
     */
    List<AreaColumnCommodity> findByAreaColumn_id(int areaColumnId);

    AreaColumnCommodity findByAreaColumn_IdAndCommodity_Id(int areaColumnId, int commodityId);

}