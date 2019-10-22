/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.bo.GiftBo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.vo.GiftListVo;
import com.yi.core.gift.domain.vo.GiftVo;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.persistence.Pagination;

/**
 * 礼物
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGiftService {

	/**
	 * 分页查询: Gift
	 **/
	Page<Gift> query(Pagination<Gift> query);

	/**
	 * 分页查询: Gift
	 **/
	Page<GiftListVo> queryListVo(Pagination<Gift> query);

	/**
	 * 分页查询: Gift
	 **/
	Page<GiftListVo> queryListVoForApp(Pagination<Gift> query);

	/**
	 * 创建Gift
	 **/
	Gift addGift(Gift gift);

	/**
	 * 创建Gift
	 **/
	GiftVo addGift(GiftBo gift);

	/**
	 * 更新Gift
	 **/
	Gift updateGift(Gift gift);

	/**
	 * 更新Gift
	 **/
	GiftVo updateGift(GiftBo gift);

	/**
	 * 删除Gift
	 **/
	void removeGiftById(int giftId);

	/**
	 * 根据ID得到GiftBo
	 **/
	GiftBo getGiftBoById(int giftId);

	/**
	 * 根据ID得到GiftVo
	 **/
	GiftVo getGiftVoById(int giftId);

	/**
	 * 根据ID得到GiftListVo
	 **/
	GiftListVo getListVoById(int giftId);

	/**
	 * 批量生成礼物
	 * 
	 * @param giftBag
	 */
	void batchCreateGift(GiftBag giftBag);

	/**
	 * 领取礼物
	 * 
	 * @param giftBagBo
	 * @param gift
	 * @param member
	 * @return
	 */
	void receiveGift(GiftBagBo giftBagBo, Gift gift, Member member);

	/**
	 * 自动作废礼物
	 */
	void autoCancelGiftByTask();

	/**
	 * 批量更新礼物
	 * 
	 * @param giftBag
	 * @param gifts
	 */
	void bathcUpdateByAfterPay(GiftBag giftBag, Collection<Gift> gifts);

}
