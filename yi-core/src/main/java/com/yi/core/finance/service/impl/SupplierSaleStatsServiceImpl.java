/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yi.core.finance.service.ISupplierSaleStatsService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SupplierSaleStatsServiceImpl implements ISupplierSaleStatsService {

	private final Logger LOG = LoggerFactory.getLogger(SupplierSaleStatsServiceImpl.class);

	@Resource
	private ISaleOrderService saleOrderService;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SupplierSaleStatsVo getSupplierSaleTotal(SupplierSaleStatsVo supplierSaleStats) {
		return saleOrderService.getSupplierSaleTotal(supplierSaleStats);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SupplierSaleStatsVo> querySupplierSaleList(Pagination<SupplierSaleStatsVo> query) {
		return saleOrderService.querySupplierSaleList(query);
	}

}
