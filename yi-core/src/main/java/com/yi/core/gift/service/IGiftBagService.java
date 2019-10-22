/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service;

import java.util.SortedMap;

import org.springframework.data.domain.Page;

import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.vo.GiftBagListVo;
import com.yi.core.gift.domain.vo.GiftBagVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;

/**
 * 礼包
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGiftBagService {

	/**
	 * 分页查询: GiftBag
	 **/
	Page<GiftBag> query(Pagination<GiftBag> query);

	/**
	 * 分页查询: GiftBag
	 **/
	Page<GiftBagListVo> queryListVo(Pagination<GiftBag> query);

	/**
	 * 分页查询: GiftBag
	 **/
	Page<GiftBagListVo> queryListVoForApp(Pagination<GiftBag> query);

	/**
	 * 创建GiftBag
	 **/
	GiftBag addGiftBag(GiftBag giftBag);

	/**
	 * 创建GiftBag
	 **/
	GiftBagVo addGiftBag(GiftBagBo giftBag);

	/**
	 * 更新GiftBag
	 **/
	GiftBag updateGiftBag(GiftBag giftBag);

	/**
	 * 更新GiftBag
	 **/
	GiftBagVo updateGiftBag(GiftBagBo giftBag);

	/**
	 * 删除GiftBag
	 **/
	void removeGiftBagById(int giftBagId);

	/**
	 * 根据ID得到GiftBag
	 **/
	GiftBag getById(int giftBagId);

	/**
	 * 根据ID得到GiftBagBo
	 **/
	GiftBagBo getBoById(int giftBagId);

	/**
	 * 根据ID得到GiftBagVo
	 **/
	GiftBagVo getVoById(int giftBagId);

	/**
	 * 根据ID得到GiftBagListVo
	 **/
	GiftBagListVo getListVoById(int giftBagId);

	/**
	 * 微信服务号生成礼包 </br>
	 * 1生成礼包</br>
	 * 2生成礼包订单</br>
	 * 3生成预支付订单
	 * 
	 * @param giftBagBo
	 * @return
	 */
	SortedMap<String, String> createGiftBagForSp(GiftBagBo giftBagBo) throws Exception;

	/**
	 * 微信小程序生成礼包 </br>
	 * 1生成礼包</br>
	 * 2生成礼包订单</br>
	 * 3生成预支付订单
	 * 
	 * @param giftBagBo
	 * @return
	 */
	SortedMap<String, String> createGiftBagForMp(GiftBagBo giftBagBo) throws Exception;

	/**
	 * 获取礼包详情
	 * 
	 * @param giftBagId
	 * @return
	 */
	GiftBagVo getGiftBagDetail(Integer giftBagId);

	/**
	 * 领取礼物
	 * 
	 * @param giftBo
	 * @return
	 */
	GiftBagListVo receiveGift(GiftBagBo giftBagBo);

	/**
	 * 自动作废礼包
	 */
	void autoCancelGiftBagByTask();

	/**
	 * 支付时更新礼包失效时间
	 * 
	 * @param saleOrder
	 */
	void updateByAfterPay(SaleOrder saleOrder);

}
