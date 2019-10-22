/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.InvitePrizeBo;
import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.entity.InvitePrize;
import com.yi.core.activity.domain.vo.InvitePrizeListVo;
import com.yi.core.activity.domain.vo.InvitePrizeVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IInvitePrizeService {

	/**
	 * 分页查询: InvitePrize
	 **/
	Page<InvitePrize> query(Pagination<InvitePrize> query);

	/**
	 * 分页查询: InvitePrize
	 **/
	Page<InvitePrizeListVo> queryListVo(Pagination<InvitePrize> query);

	/**
	 * 分页查询: InvitePrize
	 **/
	Page<InvitePrizeListVo> queryListVoForApp(Pagination<InvitePrize> query);

	/**
	 * 创建InvitePrize
	 **/
	InvitePrize addInvitePrize(InvitePrize invitePrize);

	/**
	 * 创建InvitePrize
	 **/
	InvitePrizeListVo addInvitePrize(InvitePrizeBo invitePrize);

	/**
	 * 更新InvitePrize
	 **/
	InvitePrize updateInvitePrize(InvitePrize invitePrize);

	/**
	 * 更新InvitePrize
	 **/
	InvitePrizeListVo updateInvitePrize(InvitePrizeBo invitePrize);

	/**
	 * 删除InvitePrize
	 **/
	void removeInvitePrizeById(Integer invitePrizeId);

	/**
	 * 根据ID得到InvitePrize
	 **/
	InvitePrize getInvitePrizeById(Integer invitePrizeId);

	/**
	 * 根据ID得到InvitePrizeBo
	 **/
	InvitePrizeBo getInvitePrizeBoById(Integer invitePrizeId);

	/**
	 * 根据ID得到InvitePrizeVo
	 **/
	InvitePrizeVo getInvitePrizeVoById(Integer invitePrizeId);

	/**
	 * 根据ID得到InvitePrizeListVo
	 **/
	
	InvitePrizeListVo getListVoById(Integer invitePrizeId);

	/**
	 * 批量新增邀请有礼奖品
	 **/
	@Deprecated
	void batchAddInvitePrize(InviteActivity inviteActivity, Collection<InvitePrize> invitePrizes);

	/**
	 * 批量更新邀请有礼奖品
	 **/
	@Deprecated
	void batchUpdateInvitePrize(InviteActivity inviteActivity, Collection<InvitePrize> invitePrizes);

	/**
	 * 批量删除邀请有礼奖品
	 **/
	void batchDeletedInvitePrize(Collection<InvitePrize> invitePrizes);

	/**
	 * 领取积分
	 **/
	void receiveIntegral(InvitePrizeBo invitePrizeBo);

	/**
	 * 领取优惠券
	 **/
	void receiveCoupon(InvitePrizeBo invitePrizeBo);

	/**
	 * 领取商品
	 **/
	void receiveCommodity(InvitePrizeBo invitePrizeBo);

}
