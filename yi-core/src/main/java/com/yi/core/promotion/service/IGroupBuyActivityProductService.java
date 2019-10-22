/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service;

import java.util.Collection;

import com.yi.core.promotion.domain.simple.GroupBuyActivityProductSimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.promotion.domain.bo.GroupBuyActivityProductBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityProductListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityProductVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGroupBuyActivityProductService {

	/**
	 * 分页查询: GroupBuyActivityProduct
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityProduct> query(Pagination<GroupBuyActivityProduct> query);

	/**
	 * 分页查询: GroupBuyActivityProduct
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityProductListVo> queryListVo(Pagination<GroupBuyActivityProduct> query);
	
	/**
	 * 分页查询: GroupBuyActivityProduct
	 * 
	 * @param query
	 * @return
	 */
	Page<GroupBuyActivityProductListVo> queryListVoForApp(Pagination<GroupBuyActivityProduct> query);

	/**
	 * 创建GroupBuyActivityProduct
	 **/
	GroupBuyActivityProduct addGroupBuyActivityProduct(GroupBuyActivityProduct groupBuyActivityProduct);

	/**
	 * 创建GroupBuyActivityProduct
	 **/
	GroupBuyActivityProductListVo addGroupBuyActivityProduct(GroupBuyActivityProductBo groupBuyActivityProduct);

	/**
	 * 更新GroupBuyActivityProduct
	 **/
	GroupBuyActivityProduct updateGroupBuyActivityProduct(GroupBuyActivityProduct groupBuyActivityProduct);

	/**
	 * 更新GroupBuyActivityProduct
	 **/
	GroupBuyActivityProductListVo updateGroupBuyActivityProduct(GroupBuyActivityProductBo groupBuyActivityProduct);

	/**
	 * 删除GroupBuyActivityProduct
	 **/
	void removeGroupBuyActivityProductById(int groupBuyActivityProductId);

	/**
	 * 根据ID得到GroupBuyActivityProduct
	 **/
	GroupBuyActivityProduct getById(int groupBuyActivityProductId);

	/**
	 * 根据ID得到GroupBuyActivityProductBo
	 **/
	GroupBuyActivityProductBo getBoById(int groupBuyActivityProductId);

	/**
	 * 根据ID得到GroupBuyActivityProductVo
	 **/
	GroupBuyActivityProductVo getVoById(int groupBuyActivityProductId);
	
	/**
	 * 根据ID得到GroupBuyActivityProductVo
	 **/
	GroupBuyActivityProductVo getVoByIdForApp(int groupBuyActivityProductId);

	/**
	 * 根据ID得到GroupBuyActivityProductListVo
	 **/
	GroupBuyActivityProductListVo getListVoById(int groupBuyActivityProductId);

	/**
	 * 批量新增团购商品
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityProducts
	 */
	void batchAddGroupBuyActivityProduct(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityProduct> groupBuyActivityProducts);

	/**
	 * 批量修改团购商品
	 * 
	 * @param groupBuyActivity
	 * @param groupBuyActivityProducts
	 */
	void batchUpdateGroupBuyActivityProduct(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityProduct> groupBuyActivityProducts);

	EntityListVoBoSimpleConvert<GroupBuyActivityProduct, GroupBuyActivityProductBo, GroupBuyActivityProductVo, GroupBuyActivityProductSimple, GroupBuyActivityProductListVo> getGroupBuyActivityProductConvert();

}
