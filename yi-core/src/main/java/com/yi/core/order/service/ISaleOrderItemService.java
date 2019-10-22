/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;


import com.yi.core.order.domain.bo.SaleOrderItemBo;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.vo.SaleOrderItemListVo;
import com.yi.core.order.domain.vo.SaleOrderItemVo;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.net.*;
import java.util.*;

/**
*  *
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
*/
public interface ISaleOrderItemService {

 	/**
    * 分页查询: SaleOrderItem
    **/
   Page<SaleOrderItem> query(Pagination<SaleOrderItem> query);
   
   /**
    * 分页查询: SaleOrderItem
    **/
    Page<SaleOrderItemListVo> queryListVo(Pagination<SaleOrderItem> query);
    
   /**
    * 分页查询: SaleOrderItem
    **/
    Page<SaleOrderItemListVo> queryListVoForApp(Pagination<SaleOrderItem> query);
   
   /**
    * 创建SaleOrderItem
    **/
    SaleOrderItem addSaleOrderItem(SaleOrderItem saleOrderItem);
   
   /**
    * 创建SaleOrderItem
    **/
    SaleOrderItemListVo addSaleOrderItem(SaleOrderItemBo saleOrderItem);

    /**
    * 更新SaleOrderItem
    **/
    SaleOrderItem updateSaleOrderItem(SaleOrderItem saleOrderItem);
    
    /**
    * 更新SaleOrderItem
    **/
    SaleOrderItemListVo updateSaleOrderItem(SaleOrderItemBo saleOrderItem);

    /**
    * 删除SaleOrderItem
    **/
    void removeSaleOrderItemById(int saleOrderItemId);
    
	/**
     * 根据ID得到SaleOrderItem
    **/
    SaleOrderItem getSaleOrderItemById(int saleOrderItemId);

    /**
     * 根据ID得到SaleOrderItemBo
    **/
    SaleOrderItemBo getSaleOrderItemBoById(int saleOrderItemId);

    /**
    * 根据ID得到SaleOrderItemVo
    **/
    SaleOrderItemVo getSaleOrderItemVoById(int saleOrderItemId);

    /**
    * 根据ID得到SaleOrderItemListVo
    **/
    SaleOrderItemListVo getListVoById(int saleOrderItemId);

    /**
     * 获得供应商销售合计数据
     * @param state
     * @return
     */
    List<Object[]> getTotalSupplierSaleData(Integer state);

    /**
     *  按日期查询供应商销售数据
     * @param pageable
     * @param state
     * @param startDate
     * @param endDate
     * @return
     */
    List<Object[]> getSupplierSaleDataByDate(Pageable pageable, Integer state, Date startDate, Date endDate);

    List<Object[]> getSaleStatisticsGrouping(String startTime, String endTime);



}
