package com.yi.core.commodity.dao;
/**
* Powered By [yihz-framework]
* Web Site: yihz
* Since 2018 - 2018
*/

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;

/**
 * 会员等级折扣
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface CommodityLevelDiscountDao extends JpaRepository<CommodityLevelDiscount, Integer>, JpaSpecificationExecutor<CommodityLevelDiscount> {

	List<CommodityLevelDiscount> findByCommodity_id(int id);

	List<CommodityLevelDiscount> findByMemberLevel_id(int id);
}