/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service;

import org.springframework.data.domain.Page;

import com.yi.core.gift.domain.bo.GiftBagItemBo;
import com.yi.core.gift.domain.entity.GiftBagItem;
import com.yi.core.gift.domain.vo.GiftBagItemListVo;
import com.yi.core.gift.domain.vo.GiftBagItemVo;
import com.yihz.common.persistence.Pagination;

/**
 * 礼包项
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IGiftBagItemService {

	/**
	 * 分页查询: GiftBagItem
	 **/
	Page<GiftBagItem> query(Pagination<GiftBagItem> query);

	/**
	 * 分页查询: GiftBagItem
	 **/
	Page<GiftBagItemListVo> queryListVo(Pagination<GiftBagItem> query);

	/**
	 * 创建GiftBagItem
	 **/
	GiftBagItem addGiftBagItem(GiftBagItem giftBagItem);

	/**
	 * 创建GiftBagItem
	 **/
	GiftBagItemVo addGiftBagItem(GiftBagItemBo giftBagItem);

	/**
	 * 更新GiftBagItem
	 **/
	GiftBagItem updateGiftBagItem(GiftBagItem giftBagItem);

	/**
	 * 更新GiftBagItem
	 **/
	GiftBagItemVo updateGiftBagItem(GiftBagItemBo giftBagItem);

	/**
	 * 删除GiftBagItem
	 **/
	void removeGiftBagItemById(int giftBagItemId);

	/**
	 * 根据ID得到GiftBagItemBo
	 **/
	GiftBagItemBo getGiftBagItemBoById(int giftBagItemId);

	/**
	 * 根据ID得到GiftBagItemVo
	 **/
	GiftBagItemVo getGiftBagItemVoById(int giftBagItemId);

	/**
	 * 根据ID得到GiftBagItemListVo
	 **/
	GiftBagItemListVo getListVoById(int giftBagItemId);

}
