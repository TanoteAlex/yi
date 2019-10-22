package com.yi.core.commodity.service;/*
										* Powered By [yihz-framework]
										* Web Site: yihz
										* Since 2018 - 2018
										*/

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.CommodityLevelDiscountBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yi.core.commodity.domain.vo.CommodityLevelDiscountListVo;
import com.yi.core.commodity.domain.vo.CommodityLevelDiscountVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICommodityLevelDiscountService {

	/**
	 * 根据ID得到CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscountId
	 * @return
	 */
	CommodityLevelDiscount getById(int commodityLevelDiscountId);

	/**
	 * 根据ID得到CommodityLevelDiscountVo
	 * 
	 * @param commodityLevelDiscountId
	 * @return
	 */
	CommodityLevelDiscountVo getVoById(int commodityLevelDiscountId);

	/**
	 * 根据ID得到CommodityLevelDiscountListVo
	 * 
	 * @param commodityLevelDiscountId
	 * @return
	 */
	CommodityLevelDiscountListVo getListVoById(int commodityLevelDiscountId);

	/**
	 * 根据Entity创建CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscount
	 * @return
	 */
	CommodityLevelDiscount addCommodityLevelDiscount(CommodityLevelDiscount commodityLevelDiscount);

	/**
	 * 根据BO创建CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscountBo
	 * @return
	 */
	CommodityLevelDiscountListVo addCommodityLevelDiscount(CommodityLevelDiscountBo commodityLevelDiscountBo);

	/**
	 * 根据Entity更新CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscount
	 * @return
	 */
	CommodityLevelDiscount updateCommodityLevelDiscount(CommodityLevelDiscount commodityLevelDiscount);

	/**
	 * 根据BO更新CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscountBo
	 * @return
	 */
	CommodityLevelDiscountListVo updateCommodityLevelDiscount(CommodityLevelDiscountBo commodityLevelDiscountBo);

	/**
	 * 删除CommodityLevelDiscount
	 * 
	 * @param commodityLevelDiscountId
	 */
	void removeCommodityLevelDiscountById(int commodityLevelDiscountId);

	/**
	 * 分页查询: CommodityLevelDiscount
	 * 
	 * @param query
	 * @return
	 */
	Page<CommodityLevelDiscount> query(Pagination<CommodityLevelDiscount> query);

	/**
	 * 分页查询: CommodityLevelDiscountListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CommodityLevelDiscountListVo> queryListVo(Pagination<CommodityLevelDiscount> query);

	/**
	 * 根据商品id获取会员中间表
	 */
	List<CommodityLevelDiscount> getByCommodity(int id);

	/**
	 * 批量更新 会员等级折扣
	 * 
	 * @param commodity
	 * @param commodityLevelDiscounts
	 */
	void batchAddLevelDiscount(Commodity commodity, List<CommodityLevelDiscount> commodityLevelDiscounts);

	/**
	 * 批量更新 会员等级折扣
	 * 
	 * @param commodity
	 * @param commodityLevelDiscounts
	 */
	void batchUpdateLevelDiscount(Commodity commodity, List<CommodityLevelDiscount> commodityLevelDiscounts);

	/**
	 * 删除CommodityLevelDiscount
	 * 
	 * @param memberLevelId
	 */
	void removeByMemberLevel(int memberLevelId);

}
