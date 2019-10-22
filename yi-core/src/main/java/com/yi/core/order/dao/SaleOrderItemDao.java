/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.order.domain.entity.SaleOrderItem;

/**
 * 销售订单项
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface SaleOrderItemDao extends JpaRepository<SaleOrderItem, Integer>,
        JpaSpecificationExecutor<SaleOrderItem> {


    //删除订单表 sale_order_item
    @Modifying
    @Query("update SaleOrderItem set deleted=1 where id=?1")
    int deletesale_order_itemTest(int id);

    // 获取单个商品的订单数
    int countByProduct(Product product);

    // 获取单个商品获取对象
    @Query(value = "select sum(quantity) from SaleOrderItem where product=?1")
    int findByProducts(Product product);

    @Query(value = "select ct.id from SaleOrderItem em left join  em.saleOrder ms left join em.product ct where em" +
            ".saleOrder.id=?1 group by em.product")
    int[] supplierId(int orderId);

    /**
     * 获得供应商销售合计数据
     *
     * @param pageable
     * @param orderState
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select count(distinct sa.saleOrder), sum(sa.saleOrder.payAmount), sum(sa.product.supplyPrice)" +
            " from SaleOrderItem sa "
            + " where sa.saleOrder.orderState=?1 ")
    List<Object[]> findTotalSupplierSaleData(Integer orderState);

    /**
     * 按日期查询供应商销售数据
     *
     * @param pageable
     * @param orderState
     * @param startDate
     * @param endDate
     * @return
     */
    @Query(value = "select sa.supplier.supplierName, count(distinct sa.saleOrder), sum(sa.saleOrder.payAmount), sum" +
            "(sa.product.supplyPrice) " + " from SaleOrderItem sa "
            + " where Date(sa.saleOrder.paymentTime)>=Date(:startDate) and Date(sa.saleOrder.paymentTime)<=Date" +
            "(:endDate) " + " and sa.saleOrder.orderState=:orderState "
            + " group by sa.supplier.id")
    List<Object[]> getSupplierSaleDataByDate(Pageable pageable, @Param("orderState") Integer orderState, @Param
            ("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询供应商销售数据
     *
     * @param pageable
     * @param orderState
     * @return
     */
    @Query(value = "select sa.supplier.supplierName, count(distinct sa.saleOrder), sum(sa.saleOrder.payAmount), sum" +
            "(sa.product.supplyPrice) " + " from SaleOrderItem sa "
            + " where sa.saleOrder.orderState=:orderState " + " group by sa.supplier.id")
    List<Object[]> getSupplierSaleDataByDate(Pageable pageable, @Param("orderState") Integer orderState);

    /**
     * 供应商分组流水
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select sa.supplier.supplierName,count(distinct sa.saleOrder),sum(sa.saleOrder.payAmount),sum(sa" +
            ".product.supplyPrice) from SaleOrderItem sa where Date(sa.saleOrder.paymentTime)>=Date(?1) "
            + "and Date(sa.saleOrder.paymentTime)<=Date(?2) and sa.saleOrder.orderState=?3  group by sa.supplier.id")
    List<Object[]> getSupplierGrouping(String startTime, String endTime, Integer orderState);

    @Query(value = "select sa.supplier.supplierName,count(distinct sa.saleOrder),sum(sa.saleOrder.payAmount),sum(sa" +
            ".product.supplyPrice) " + "from SaleOrderItem sa where "
            + " sa.saleOrder.orderState=?1  group by sa.supplier.id")
    List<Object[]> getSupplierGrouping(Integer orderState);


    /**
     * 售卖商品销售统计
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(value = "select sa.product.productImgPath,sa.product.productName,count(sa.product.id),sum(sa.quantity),sum" +
            "(sa.subtotal) " +
            "from SaleOrderItem sa where Date(sa.saleOrder.paymentTime) >=Date(?1) and Date(sa.saleOrder" +
            ".paymentTime)<=Date(?2) and sa.saleOrder.orderState >= 2 and sa.saleOrder.orderState <= 4" +
            " and sa.deleted =0 group by sa.product.id")
    List<Object[]> getSaleStatisticsGrouping(String startTime, String endTime);

    @Query(value = "select sa.product.productImgPath,sa.product.productName,count(sa.product.id),sum(sa.quantity),sum" +
            "(sa.subtotal) " +
            "from SaleOrderItem sa where sa.saleOrder.orderState >= 2 and sa.saleOrder.orderState <= 4 " +
            "and sa.deleted =0  group by sa.product.id")
    List<Object[]> getSaleStatisticsGrouping();
}