///*
// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.finance.dao;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import com.yi.core.finance.domain.entity.PlatformSaleStat;
//import com.yihz.common.orm.BaseDao;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Date;
//
//
///**
//*  *
// * @author lemosen
// * @version 1.0
// * @since 1.0
// *
//*/
//public interface PlatformSaleStatDao extends JpaRepository<PlatformSaleStat, Integer> ,JpaSpecificationExecutor<PlatformSaleStat> {
//    //SELECT COUNT(*),SUM(er.PAY_AMOUNT),SUM(p.COST_PRICE) FROM sale_order_item it,product p,sale_order er WHERE  it.ORDER_ID=er.ID AND it.PRODUCT_ID=p.ID
//    //CREATE_TIME                                      PAY_AMOUNT         COST_PRICE                                          er.CREATE_TIME<='2018-8-28' AND '2018-7-7'<=er.CREATE_TIME
//    @Query(value = "select  COUNT(er.id) as platformOrderNum,SUM(er.payAmount) as saleAmount,SUM(p.costPrice) as cost,(SUM(er.payAmount)-SUM(p.costPrice)) as profit from    SaleOrderItem sa left join sa.product p left join sa.saleOrder er where  ?1<=er.createTime AND er.createTime<=?2 ")
//    String[] platformSaleStatPart(Date startTime, Date endTime);
//    /*String name, Date startTime,Date endTime*/
//
//    @Query(value = "select   COUNT(er.id) as platformOrderNum,SUM(er.payAmount) as saleAmount,SUM(p.costPrice) as cost,(SUM(er.payAmount)-SUM(p.costPrice)) as profit from SaleOrderItem sa left join sa.product p left join sa.saleOrder er where ?1<=er.createTime")
//    String[] platformSaleStatStart(Date startTime);
//
//    @Query(value = "select   COUNT(er.id) as platformOrderNum,SUM(er.payAmount) as saleAmount,SUM(p.costPrice) as cost,(SUM(er.payAmount)-SUM(p.costPrice)) as profit from SaleOrderItem sa left join sa.product p left join sa.saleOrder er where  er.createTime<=?1 ")
//    String[] platformSaleStatEnd(Date endTime);
//
//    @Query(value = "select   COUNT(er.id) as platformOrderNum,SUM(er.payAmount) as saleAmount,SUM(p.costPrice) as cost,(SUM(er.payAmount)-SUM(p.costPrice)) as   profit from SaleOrderItem sa left join sa.product p left join sa.saleOrder er")
//    String[] platformSaleStatEndAll();
//
//}