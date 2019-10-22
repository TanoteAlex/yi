/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierSaleStatsService {

	/**
	 * 供应商销售合计
	 * 
	 * @param supplierSaleStatsVo
	 * @return
	 */
	SupplierSaleStatsVo getSupplierSaleTotal(SupplierSaleStatsVo supplierSaleStats);

	/**
	 * 供应商销售集合
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierSaleStatsVo> querySupplierSaleList(Pagination<SupplierSaleStatsVo> query);

}
