/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.bo.CouponReceiveBo;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 优惠券领取
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICouponReceiveService {

	/**
	 * 分页查询: CouponReceive
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceive> query(Pagination<CouponReceive> query);

	/**
	 * 分页查询: CouponReceiveListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceiveListVo> queryListVo(Pagination<CouponReceive> query);

	/**
	 * 分页查询: CouponReceiveListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CouponReceiveListVo> queryListVoForApp(Pagination<CouponReceive> query);

	/**
	 * 根据Entity创建CouponReceive
	 * 
	 * @param couponReceive
	 * @return
	 */
	CouponReceive addCouponReceive(CouponReceive couponReceive);

	/**
	 * 根据BO创建CouponReceive
	 * 
	 * @param couponReceiveBo
	 * @return
	 */
	CouponReceiveListVo addCouponReceive(CouponReceiveBo couponReceiveBo);

	/**
	 * 根据Entity更新CouponReceive
	 * 
	 * @param couponReceive
	 * @return
	 */
	CouponReceive updateCouponReceive(CouponReceive couponReceive);

	/**
	 * 根据BO更新CouponReceive
	 *
	 * @param couponReceiveBo
	 * @return
	 */
	CouponReceiveListVo updateCouponReceive(CouponReceiveBo couponReceiveBo);

	/**
	 * 删除CouponReceive
	 * 
	 * @param couponReceiveId
	 */
	void removeCouponReceiveById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceive
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceive getById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceiveVo
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceiveVo getVoById(int couponReceiveId);

	/**
	 * 根据ID得到CouponReceiveListVo
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceiveListVo getListVoById(int couponReceiveId);

	/**
	 * 领取优惠券
	 * 
	 * @param couponId
	 * @param memberId
	 * @return
	 */
	CouponReceiveListVo receiveCoupon(Integer couponId, Integer memberId);

	/**
	 * 发放奖励时 领取优惠券
	 * 
	 * @param couponId
	 * @param memberId
	 * @return
	 */
	void receiveCouponForGrantReward(Integer couponId, Integer memberId);

	/**
	 * 领取转赠代金券
	 * 
	 * @param couponReceiveId
	 * @param memberId
	 * @return
	 */
	CouponReceiveListVo receiveVoucher(Integer couponReceiveId, Integer memberId);

	/**
	 * 获取会员的优惠券
	 * 
	 * @param memberId
	 * @param saleOrders
	 * @return
	 */
	List<CouponReceive> getMemberCoupons(Integer memberId, SaleOrder... saleOrders);

	/**
	 * 获取会员的代金券
	 * 
	 * @param memberId
	 * @return
	 */
	List<CouponReceive> getMemberVouchers(Integer memberId);

	/**
	 * 使用优惠券
	 * 
	 * @param saleOrder
	 */
	void useCouponsByOrder(SaleOrder... saleOrders);

	/**
	 * 分步发放代金券
	 * 
	 * @param member
	 * @param saleOrder
	 * @param grantStep
	 */
	List<Object> grantVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep);

	/**
	 * 根据释放节点 解冻代金券
	 * 
	 * @param member
	 * @param saleOrder
	 * @param grantStep
	 */
	void thawVoucherByStep(Member member, SaleOrder saleOrder, ActivityEnum grantStep);

	/**
	 * 支付后获取 需要发放的代金券
	 * 
	 * @param orderIds
	 * @return
	 */
	List<Object> getGrantVoucherByPaid(String orderIds);

	/**
	 * 手工发送优惠券
	 * 
	 * @param couponReceiveBo
	 */
	void grantCoupon(CouponReceiveBo couponReceiveBo);

	/**
	 * 手工发送代金券
	 * 
	 * @param couponReceiveBo
	 */
	void grantVoucher(CouponReceiveBo couponReceiveBo);

	/**
	 * 撤回发送的代金券
	 * 
	 * @param couponReceiveBo
	 */
	void revokeVoucher(CouponReceiveBo couponReceiveBo);

	/**
	 * 手工发送储值券
	 * 
	 * @param couponReceiveBo
	 */
	void grantStored(CouponReceiveBo couponReceiveBo);

	/**
	 * 撤回发送的储值券
	 * 
	 * @param couponReceiveBo
	 */
	void revokeStored(CouponReceiveBo couponReceiveBo);

	/**
	 * 退款时 收回已经发出的代金券
	 * 
	 * @param member
	 * @param saleOrder
	 */
	void returnVoucherByRefund(Member member, SaleOrder saleOrder);

	/**
	 * 退款时 释放已经使用的代金券
	 * 
	 * @param member
	 * @param saleOrder
	 */
	void returnUseVoucherByRefund(Member member, SaleOrder saleOrder);

	/**
	 * 该优惠券领取数量
	 */
	int countByCouponId(int couponId);

	/**
	 * 统计会员优惠券领取数量
	 * 
	 * @param memberId
	 * @return
	 */
	int countByMemberId(int memberId);

	/**
	 * 获取代金券领取的详情
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	CouponReceiveVo getCouponReceiveDetail(Integer couponReceiveId);

	/**
	 * 自动作废优惠券
	 */
	void autoCancelCouponByTask();

	/**
	 * 领取邀请有礼奖品
	 * 
	 * @param memberId
	 * @param couponId
	 */
	void receiveInvitePrize(Integer memberId, Integer couponId);

	/***
	 * 检查用户是否有领取过优惠券
	 * @param memberId
	 * @param couponId
	 * @return
	 */
	boolean checkReceivePrizeForCoupon(Integer memberId, Integer couponId);

}
