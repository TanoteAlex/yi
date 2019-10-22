/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.bo.CouponGrantRecordBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.entity.CouponGrantRecord;
import com.yi.core.activity.domain.vo.CouponGrantRecordListVo;
import com.yi.core.activity.domain.vo.CouponGrantRecordVo;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ICouponGrantRecordService {

	/**
	 * 分页查询: CouponGrantRecord
	 **/
	Page<CouponGrantRecord> query(Pagination<CouponGrantRecord> query);

	/**
	 * 分页查询: CouponGrantRecord
	 **/
	Page<CouponGrantRecordListVo> queryListVo(Pagination<CouponGrantRecord> query);

	/**
	 * 分页查询: CouponGrantRecord
	 **/
	Page<CouponGrantRecordListVo> queryListVoForApp(Pagination<CouponGrantRecord> query);

	/**
	 * 创建CouponGrantRecord
	 **/
	CouponGrantRecord addCouponGrantRecord(CouponGrantRecord couponGrantRecord);

	/**
	 * 创建CouponGrantRecord
	 **/
	CouponGrantRecordListVo addCouponGrantRecord(CouponGrantRecordBo couponGrantRecord);

	/**
	 * 更新CouponGrantRecord
	 **/
	CouponGrantRecord updateCouponGrantRecord(CouponGrantRecord couponGrantRecord);

	/**
	 * 更新CouponGrantRecord
	 **/
	CouponGrantRecordListVo updateCouponGrantRecord(CouponGrantRecordBo couponGrantRecord);

	/**
	 * 删除CouponGrantRecord
	 **/
	void removeCouponGrantRecordById(int couponGrantRecordId);

	/**
	 * 根据ID得到CouponGrantRecord
	 **/
	CouponGrantRecord getCouponGrantRecordById(int couponGrantRecordId);

	/**
	 * 根据ID得到CouponGrantRecordBo
	 **/
	CouponGrantRecordBo getCouponGrantRecordBoById(int couponGrantRecordId);

	/**
	 * 根据ID得到CouponGrantRecordVo
	 **/
	CouponGrantRecordVo getCouponGrantRecordVoById(int couponGrantRecordId);

	/**
	 * 根据ID得到CouponGrantRecordListVo
	 **/
	CouponGrantRecordListVo getListVoById(int couponGrantRecordId);

	/**
	 * 检查是不是发放过优惠券
	 * 
	 * @param grantStep
	 * @param member
	 * @param saleOrder
	 * @param couponGrantConfig
	 * @return
	 */
	boolean checkGrantForOnce(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity);

	/**
	 * 检查该步骤是不是发放过优惠券
	 * 
	 * @param grantStep
	 * @param member
	 * @param saleOrder
	 * @param couponGrantConfig
	 * @return
	 */
	boolean checkGrantForStep(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity);

	/**
	 * 检查是不是第一次发放
	 * 
	 * @param member
	 * @param saleOrder
	 * @param couponGrantConfig
	 * @return
	 */
	boolean checkFirstGrant(Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, int buyQuantity);

	/**
	 * 根据发放步骤发放优惠券
	 * 
	 * @param grantStep
	 * @param member
	 * @param saleOrder
	 * @param couponGrantConfig
	 * @return
	 */
	CouponGrantRecord addByGrantStepForOnce(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, BigDecimal parValue);

	/**
	 * 根据发放步骤发放优惠券
	 * 
	 * @param grantStep
	 * @param member
	 * @param saleOrder
	 * @param couponGrantConfig
	 * @param parValue
	 * @return
	 */
	CouponGrantRecord addByGrantStepForStep(ActivityEnum grantStep, Member member, SaleOrder saleOrder, CouponGrantConfig couponGrantConfig, BigDecimal parValue);

	/**
	 * 查询已经发放的优惠券
	 * 
	 * @param member
	 * @param saleOrder
	 * @return
	 */
	List<CouponGrantRecord> getGrantRecordsByMemberAndOrder(Member member, SaleOrder saleOrder);

}
