/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yi.core.order.domain.entity.SaleOrder;

/**
 * 销售订单
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public interface SaleOrderDao extends JpaRepository<SaleOrder, Integer>, JpaSpecificationExecutor<SaleOrder> {

//删除订单表 sale_order
	@Modifying
	@Query("update SaleOrder set deleted=1 where id=?1")
	int deletesale_orderTest(int id);


	// 查询待发货订单
	int countByOrderState(Integer orderState);

	// 可查询每日 每周 每月 订单
	@Query(value = "select count(id) from SaleOrder where createTime>?1 and createTime<?2")
	int timeRange(Date start, Date end);

	// 获取成交额
	@Query(value = "select sum(payAmount) from SaleOrder where orderState='4'")
	BigDecimal amount();

	// 查看订单
	int countByMemberId(Integer MemberId);

	// 会员消费排名
	@Query(value = "select m.id,m.username,count(s),sum(s),sum(s.payAmount) as consumption " + " from SaleOrder s left join s.member m  " + " group by m.id  "
			+ " order by consumption desc ")
	List<Object[]> countMember();

	// 商品销售量
	@Query("select t4.id,t4.commodityName,sum(t2.quantity) as saleQuantity from  " + "SaleOrder t1 left join t1.saleOrderItems t2 " + "left join t2.product t3 "
			+ "left join t3.commodity t4  " + "group by t4.id " + "order by saleQuantity desc ")
	List<Object[]> countCommodity();

	/**
	 * 根据日期 统计下单数
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "select count(*) from SaleOrder where deleted=?1 and Date(orderTime)=Date(?2)")
	int countByDeletedAndCreateTime(Integer deleted, Date date);

	/**
	 * 供应商 根据日期 统计下单数
	 *
	 * @param deleted
	 * @param date
	 * @param supplierId
	 * @return
	 */
	@Query(value = "select count(t1.id) from SaleOrder t1 left join t1.supplier t2 where t1.deleted=?1 and Date(t1.paymentTime)=Date(?2) and t2.id=?3 ")
	int countByDeletedAndCreateTimeAndSupplier(Integer deleted, Date date, Integer supplierId);

	/**
	 * 根据状态 统计售后订单数
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countByDeletedAndAfterSaleState(Integer deleted, Integer afterSaleState);

	/**
	 * 根据状态 统计供应商售后订单数
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countByDeletedAndAfterSaleStateAndSupplier_id(Integer deleted, Integer afterSaleState, Integer supplierId);

	/**
	 * 根据状态 统计订单数
	 * 
	 * @param deleted
	 * @param orderState
	 * @return
	 */
	int countByDeletedAndOrderState(Integer deleted, Integer orderState);

	/**
	 * 根据状态 统计供应商订单数
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int countByDeletedAndOrderStateAndSupplier_id(Integer deleted, Integer orderState, Integer supplierId);

	/**
	 * 根据日期 统计成交额
	 *
	 * @param deleted
	 * @param orderState
	 * @param date
	 * @return
	 */
	@Query(value = "select sum(payAmount) from SaleOrder s where deleted=?1 and orderState in (?2) and Date(s.paymentTime)=Date(?3)")
	BigDecimal sumTradeAmountByDeletedAndStateAndCreateTime(Integer deleted, Integer[] orderState, Date date);

	/**
	 * 根据日期 统计供应商成交额
	 *
	 * @param deleted
	 * @param orderState
	 * @param date
	 * @return
	 */
	@Query(value = "select sum(t1.payAmount) " + "from SaleOrder t1 " + "left join t1.supplier t2 "
			+ "where t1.deleted=?1 and t1.orderState in (?2) and Date(t1.paymentTime)=Date(?3) and t2.id=?4")
	BigDecimal sumTradeAmountByDeletedAndStateAndCreateTimeAndSupplier(Integer deleted, Integer[] orderState, Date date, Integer supplierId);

	/**
	 * 会员消费排行
	 *
	 * @return
	 */
	@Query(value = "select t2.username as memberName, count(t1.id) as orderNum, sum(t1.payAmount) as tradeAmount" + " from SaleOrder t1 left join t1.member t2"
			+ " where t1.deleted=0 and Date(t1.paymentTime)>=Date(:startDate) and Date(t1.paymentTime)<=Date(:endDate) and t1.orderState in (:orderState)"
			+ " group by t2.id order by tradeAmount desc ")
	List<Object[]> findMemberConsumesByDate(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("orderState") Integer[] orderState);

	/**
	 * 商品销售量
	 *
	 * @return
	 */
	@Query("select t4.commodityName as commodityName, sum(t2.quantity) as saleNum, sum(t1.payAmount) as saleAmount"
			+ " from SaleOrder t1 left join t1.saleOrderItems t2 left join t2.product t3 left join t3.commodity t4"
			+ " where t1.deleted=0 and Date(t1.paymentTime)>=Date(:startDate) and Date(t1.paymentTime)<=Date(:endDate) and t1.orderState in (:orderState)"
			+ " group by t4.id order by saleNum desc")
	List<Object[]> findCommoditySalesByDate(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("orderState") Integer[] orderState);

	/**
	 * 供应商店铺会员消费排行
	 *
	 * @return
	 */
	@Query(value = "select t2.username as memberName, count(t1.id) as orderNum, sum(t1.payAmount) as tradeAmount" + " from SaleOrder t1 left join t1.member t2"
			+ " where t1.deleted=0 and Date(t1.createTime)>=Date(:startDate) and Date(t1.createTime)<=Date(:endDate) and t1.orderState in (:orderState) and t1.supplier.id=:supplierId"
			+ " group by t2.id order by tradeAmount desc ")
	List<Object[]> findMemberConsumesByDateAndSupplier(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orderState") Integer[] orderState, @Param("supplierId") Integer supplierId);

	/**
	 * 供应商店铺商品销售量
	 *
	 * @return
	 */
	@Query("select t4.commodityName as commodityName, sum(t2.quantity) as saleNum, sum(t1.payAmount) as saleAmount"
			+ " from SaleOrder t1 left join t1.saleOrderItems t2 left join t2.product t3 left join t3.commodity t4"
			+ " where t1.deleted=0 and Date(t1.createTime)>=Date(:startDate) and Date(t1.createTime)<=Date(:endDate) and t1.orderState in (:orderState) and t1.supplier.id=:supplierId"
			+ " group by t4.id order by saleNum desc")
	List<Object[]> findCommoditySalesByDateAndSupplier(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("orderState") Integer[] orderState, @Param("supplierId") Integer supplierId);

	/**
	 * 统计每天新增订单数量
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "select Date(createTime) as createTime, count(*) from SaleOrder" + " where deleted=?1 and Date(createTime)>=Date(?2) and Date(createTime)<=Date(?3)"
			+ " group by Date(createTime)")
	List<Object[]> findDailyAddNumByDate(Integer deleted, Date startDate, Date endDate);

	/**
	 * 统计供应商每天新增订单数量
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "select Date(t1.createTime) as createTime, count(t1.id) from SaleOrder t1 left join t1.supplier t2"
			+ " where t1.deleted=?1 and Date(t1.createTime)>=Date(?2) and Date(t1.createTime)<=Date(?3) and t2.id=?4" + " group by Date(t1.createTime)")
	List<Object[]> findDailyAddNumByDateAndSupplierId(Integer deleted, Date startDate, Date endDate, Integer supplierId);

	/**
	 * 供应商业绩排名
	 */
	@Query(value = "select t2.supplierName,sum(t1.payAmount) as performance,count(t1.id) as orderNum" + " from SaleOrder t1 left join t1.supplier t2"
			+ " where Date(t1.paymentTime)>=Date(:startDate) and Date(t1.paymentTime)<=Date(:endDate) and t1.orderState in (:orderState)"
			+ " group by t2.id order by performance desc")
	List<Object[]> findSupplierAchievements(Pageable pageable, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("orderState") Integer[] orderState);

	/**
	 * 查询订单各状态的数量
	 * 
	 * @param orderState
	 * @param memberId
	 * @param deleted
	 * @return
	 */
	@Query(value = "select count(t1.id) from SaleOrder t1 left join t1.member t2 where t1.orderState=?1 and t2.id=?2 and t1.deleted=?3 and (t1.orderType=0 or t1.orderType=1 or (t1.orderType=2 and t1.groupState=3)) ")
	int countOrderNumByState(Integer orderState, Integer memberId, Integer deleted);

	/**
	 * 查询待评价订单
	 *
	 * @param orderState
	 * @param memberId
	 * @return
	 */
	int countByCommentStateAndMemberIdAndDeleted(Integer commentState, Integer memberId, Integer deleted);

	/**
	 * 根据订单状态查询订单
	 *
	 * @param orderState
	 * @param deleted
	 * @return
	 */
	List<SaleOrder> findByOrderStateAndDeleted(Integer orderState, Integer deleted);

	/**
	 * 查询待评价订单
	 *
	 * @param commentState
	 * @param deleted
	 * @return
	 */
	List<SaleOrder> findByCommentStateAndDeleted(Integer commentState, Integer deleted);

	/**
	 * 支付宝 根据总单号查询待支付订单
	 * 
	 * @param payOrderNo
	 * @param orderState
	 * @param deleted
	 * @return
	 */
	List<SaleOrder> findByPayOrderNoAndOrderStateAndDeleted(String payOrderNo, Integer orderState, Integer deleted);

	/**
	 * 查询待对账的订单
	 * 
	 * @param orderState
	 * @param afterSaleState
	 * @param checkState
	 * @param deleted
	 * @return
	 */
	List<SaleOrder> findByOrderStateAndAfterSaleStateAndCheckStateAndDeleted(Integer orderState, Integer afterSaleState, Integer checkState, Integer deleted);

	/**
	 * 查询邀请成功的人数
	 * 
	 * @param orderState
	 * @param deleted
	 * @param memberIds
	 * @return
	 */
	@Query(value = "select count(DISTINCT t2.id) from SaleOrder t1 left join t1.member t2 where t1.orderState=?1 and t1.deleted=?2 and t2.id in(?3)")
	int countInviteNum(Integer orderState, Integer deleted, List<Integer> memberIds);

	/**
	 * 获取邀请会员的订单
	 * 
	 * @param orderState
	 * @param deleted
	 * @param memberIds
	 * @return
	 */
	@Query(value = "select DISTINCT t1 from SaleOrder t1 left join t1.member t2 where t1.orderState=?1 and t1.deleted=?2 and t2.id in(?3)")
	List<SaleOrder> findInviteMemberOrders(Integer orderState, Integer deleted, List<Integer> memberIds);

	/**
	 * 销售订单 各状态数量
	 * 
	 * @param supplierId
	 * @return
	 */
	@Query("select t1.orderState, count(t1.id) from SaleOrder t1 where t1.supplier.id=?1 and t1.deleted=0 group by t1.orderState")
	List<Object[]> countOrderNumByForSupplier(Integer supplierId);

	/**
	 * 销售订单 各状态数量
	 * 
	 * @param supplierId
	 * @return
	 */
	@Query("select t1.orderState, count(t1.id) from SaleOrder t1 where t1.deleted=0 group by t1.orderState")
	List<Object[]> countOrderNum();

	/**
	 * 查询邀请成功的人数
	 * 
	 * @param inviteMemberIds
	 * @param orderState
	 * @param deleted
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Query(value = "select count(DISTINCT t2.id) from SaleOrder t1 left join t1.member t2 where t2.id in (?1) and t1.orderState=?2 and t1.deleted=?3 and Date(t1.orderTime)>=Date(?4) and Date(t1.orderTime)<=Date(?5)")
	long countInviteNumByMember(List<Integer> inviteMemberIds, Integer orderState, Integer deleted, Date startTime, Date endTime);

	// /**
	// * 获取礼物订单
	// *
	// * @param giftId
	// * @return
	// */
	// SaleOrder findByGift_id(Integer giftId);
	//
	// /**
	// * 获取礼包订单
	// *
	// * @param orderType
	// * @param giftOrderType
	// * @param giftBagId
	// * @return
	// */
	// SaleOrder findByOrderTypeAndGiftOrderTypeAndGiftBag_id(Integer orderType,
	// Integer giftOrderType, Integer giftBagId);

	// /**
	// *
	// * @return
	// */
	// @Query(value = "select count(distinct t1.id), sum(t1.orderAmount),
	// sum(t1.costAmount), sum(t1.freight), sum(t1.orderAmount-t1.costAmount) "
	// + "from SaleOrder t1 where t1.orderState=?1")
	// List<Object[]> getSupplierSaleTotal(Integer orderState);

}