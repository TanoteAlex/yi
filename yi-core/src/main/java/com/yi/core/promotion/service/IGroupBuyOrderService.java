/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.promotion.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.promotion.domain.bo.GroupBuyOrderBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.entity.GroupBuyOrder;
import com.yi.core.promotion.domain.listVo.GroupBuyOrderListVo;
import com.yi.core.promotion.domain.vo.GroupBuyOrderVo;
import com.yihz.common.persistence.Pagination;

/**
 * 开团
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGroupBuyOrderService {

	/**
	 * 分页查询: GroupBuyOrder
	 **/
	Page<GroupBuyOrder> query(Pagination<GroupBuyOrder> query);

	/**
	 * 分页查询: GroupBuyOrder
	 **/
	Page<GroupBuyOrderListVo> queryListVo(Pagination<GroupBuyOrder> query);

	/**
	 * 分页查询: GroupBuyOrder
	 **/
	Page<GroupBuyOrderListVo> queryListVoForApp(Pagination<GroupBuyOrder> query);

	/**
	 * 创建GroupBuyOrder
	 **/
	GroupBuyOrder addGroupBuyOrder(GroupBuyOrder groupBuyOrder);

	/**
	 * 创建GroupBuyOrder
	 **/
	GroupBuyOrderListVo addGroupBuyOrder(GroupBuyOrderBo groupBuyOrder);

	/**
	 * 更新GroupBuyOrder
	 **/
	GroupBuyOrder updateGroupBuyOrder(GroupBuyOrder groupBuyOrder);

	/**
	 * 更新GroupBuyOrder
	 **/
	GroupBuyOrderListVo updateGroupBuyOrder(GroupBuyOrderBo groupBuyOrder);

	/**
	 * 删除GroupBuyOrder
	 **/
	void removeGroupBuyOrderById(int groupBuyOrderId);

	/**
	 * 根据ID得到GroupBuyOrder
	 **/
	GroupBuyOrder getById(int groupBuyOrderId);

	/**
	 * 根据ID得到GroupBuyOrderBo
	 **/
	GroupBuyOrderBo getBoById(int groupBuyOrderId);

	/**
	 * 根据ID得到GroupBuyOrderVo
	 **/
	GroupBuyOrderVo getVoById(int groupBuyOrderId);

	/**
	 * 根据ID得到GroupBuyOrderListVo
	 **/
	GroupBuyOrderListVo getListVoById(int groupBuyOrderId);

	/**
	 * 获取订单的团购数据
	 * 
	 * @param memberId
	 * @param saleOrderId
	 * @return
	 */
	GroupBuyOrderVo getVoByMemberAndSaleOrder(Integer memberId, Integer saleOrderId);

	/**
	 * 下单时 添加开团单
	 * 
	 * @param member
	 * @param groupBuyProduct
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder addOpenGroupByOrder(Member member, GroupBuyActivityProduct groupBuyProduct, SaleOrder saleOrder);

	/**
	 * 下单时 添加参团单
	 * 
	 * @param member
	 * @param groupBuyProduct
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder addJoinGroupByOrder(Member member, GroupBuyActivityProduct groupBuyProduct, SaleOrder saleOrder, GroupBuyOrder groupBuyOrder);

	/**
	 * 支付成功后 修改开团单
	 * 
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder updateOpenGroupByAfterPay(SaleOrder saleOrder);

	/**
	 * 支付成功后 修改参团单
	 * 
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder updateJoinGroupByAfterPay(SaleOrder saleOrder);

	/**
	 * 退款成功后 修改开/参团单状态
	 * 
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder updateByAfterRefund(SaleOrder saleOrder);

	/**
	 * 关闭订单时 修改开/参团单状态
	 * 
	 * @param saleOrder
	 * @return
	 */
	GroupBuyOrder updateByCloseOrder(SaleOrder saleOrder);

	/**
	 * 参团成功后 修改 参团人数
	 * 
	 * @param groupBuyOrder
	 * @param joinNum
	 * @return
	 */
	GroupBuyOrder updateJoinNumByAfterPay(GroupBuyOrder groupBuyOrder, Integer joinNum);

	/**
	 * 查询团购商品的 开团集合
	 * 
	 * @param groupBuyProductId
	 * @return
	 */
	List<GroupBuyOrderListVo> getByGroupBuyProductId(Integer groupBuyProductId);

	/**
	 * 查询团购商品的 开团集合
	 * 
	 * @param groupBuyProductId
	 * @return
	 */
	List<GroupBuyOrderListVo> getAllByGroupBuyProductId(Integer groupBuyProductId);

	/**
	 * 未成团 自动失效并退款
	 */
	void autoCancelGroupBuyOrderByTask();

}
