/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service;

import org.springframework.data.domain.Page;

import com.yi.core.member.domain.bo.MemberCommissionBo;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.vo.MemberCommissionListVo;
import com.yi.core.member.domain.vo.MemberCommissionVo;
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
public interface IMemberCommissionService {

	/**
	 * 分页查询: MemberCommission
	 **/
	Page<MemberCommission> query(Pagination<MemberCommission> query);

	/**
	 * 分页查询: MemberCommission
	 **/
	Page<MemberCommissionListVo> queryListVo(Pagination<MemberCommission> query);

	/**
	 * 分页查询: MemberCommission
	 **/
	Page<MemberCommissionListVo> queryListVoForApp(Pagination<MemberCommission> query);

	/**
	 * 创建MemberCommission
	 **/
	MemberCommission addMemberCommission(MemberCommission memberCommission);

	/**
	 * 创建MemberCommission
	 **/
	MemberCommissionListVo addMemberCommission(MemberCommissionBo memberCommission);

	/**
	 * 更新MemberCommission
	 **/
	MemberCommission updateMemberCommission(MemberCommission memberCommission);

	/**
	 * 更新MemberCommission
	 **/
	MemberCommissionListVo updateMemberCommission(MemberCommissionBo memberCommission);

	/**
	 * 删除MemberCommission
	 **/
	void removeMemberCommissionById(int memberCommissionId);

	/**
	 * 根据ID得到MemberCommission
	 **/
	MemberCommission getMemberCommissionById(int memberCommissionId);

	/**
	 * 根据ID得到MemberCommissionBo
	 **/
	MemberCommissionBo getMemberCommissionBoById(int memberCommissionId);

	/**
	 * 根据ID得到MemberCommissionVo
	 **/
	MemberCommissionVo getMemberCommissionVoById(int memberCommissionId);

	/**
	 * 根据ID得到MemberCommissionListVo
	 **/
	MemberCommissionListVo getListVoById(int memberCommissionId);

	/**
	 * 自动将未结算的佣金转到可提现的佣金
	 */
	void autoUnsettledCommissionToCashableCommissionByTask();

	/**
	 * 下级退款时 将该笔订单产生的佣金退回
	 */
	void returnSuperiorCommissionBySubordinateRefund(SaleOrder saleOrder);

}
