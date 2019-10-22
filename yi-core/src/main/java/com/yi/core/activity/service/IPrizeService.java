/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.PrizeBo;
import com.yi.core.activity.domain.entity.Prize;
import com.yi.core.activity.domain.vo.PrizeListVo;
import com.yi.core.activity.domain.vo.PrizeVo;
import com.yihz.common.persistence.Pagination;

/**
 * 
 * 奖品
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IPrizeService {

	/**
	 * 分页查询: Prize
	 **/
	Page<Prize> query(Pagination<Prize> query);

	/**
	 * 分页查询: Prize
	 **/
	Page<PrizeListVo> queryListVo(Pagination<Prize> query);

	/**
	 * 分页查询: Prize
	 **/
	Page<PrizeListVo> queryListVoForApp(Pagination<Prize> query);

	/**
	 * 创建Prize
	 **/
	Prize addPrize(Prize prize);

	/**
	 * 创建Prize
	 **/
	PrizeListVo addPrize(PrizeBo prize);

	/**
	 * 更新Prize
	 **/
	Prize updatePrize(Prize prize);

	/**
	 * 更新Prize
	 **/
	PrizeListVo updatePrize(PrizeBo prize);

	/**
	 * 删除Prize
	 **/
	void removePrizeById(int prizeId);

	/**
	 * 根据ID得到Prize
	 **/
	Prize getPrizeById(int prizeId);

	/**
	 * 根据ID得到PrizeBo
	 **/
	PrizeBo getPrizeBoById(int prizeId);

	/**
	 * 根据ID得到PrizeVo
	 **/
	PrizeVo getPrizeVoById(int prizeId);

	/**
	 * 根据ID得到PrizeListVo
	 **/
	PrizeListVo getListVoById(int prizeId);

	/**
	 * 改变禁用启用状态
	 * 
	 * @param prizeId
	 * @return
	 */
	PrizeVo updateState(int prizeId);

}
