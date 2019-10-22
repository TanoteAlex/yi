/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.AfterSaleReasonBo;
import com.yi.core.order.domain.entity.AfterSaleReason;
import com.yi.core.order.domain.vo.AfterSaleReasonListVo;
import com.yi.core.order.domain.vo.AfterSaleReasonVo;
import com.yihz.common.persistence.Pagination;

/**
 * 售后原因
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IAfterSaleReasonService {

	/**
	 * 分页查询: AfterSaleReason
	 **/
	Page<AfterSaleReason> query(Pagination<AfterSaleReason> query);

	/**
	 * 分页查询: AfterSaleReason
	 **/
	Page<AfterSaleReasonListVo> queryListVo(Pagination<AfterSaleReason> query);

//	/**
//	 * 分页查询: AfterSaleReason
//	 **/
//	Page<AfterSaleReasonListVo> queryListVoForApp(Pagination<AfterSaleReason> query);

	/**
	 * 分页查询: AfterSaleReason
	 **/
	List<AfterSaleReasonListVo> queryAllForApp();

	/**
	 * 创建AfterSaleReason
	 **/
	AfterSaleReason addAfterSaleReason(AfterSaleReason afterSaleReason);

	/**
	 * 创建AfterSaleReason
	 **/
	AfterSaleReasonListVo addAfterSaleReason(AfterSaleReasonBo afterSaleReason);

	/**
	 * 更新AfterSaleReason
	 **/
	AfterSaleReason updateAfterSaleReason(AfterSaleReason afterSaleReason);

	/**
	 * 更新AfterSaleReason
	 **/
	AfterSaleReasonListVo updateAfterSaleReason(AfterSaleReasonBo afterSaleReason);

	/**
	 * 删除AfterSaleReason
	 **/
	void removeAfterSaleReasonById(int afterSaleReasonId);

	/**
	 * 根据ID得到AfterSaleReason
	 **/
	AfterSaleReason getById(int afterSaleReasonId);

	/**
	 * 根据ID得到AfterSaleReasonBo
	 **/
	AfterSaleReasonBo getBoById(int afterSaleReasonId);

	/**
	 * 根据ID得到AfterSaleReasonVo
	 **/
	AfterSaleReasonVo getVoById(int afterSaleReasonId);

	/**
	 * 根据ID得到AfterSaleReasonListVo
	 **/
	AfterSaleReasonListVo getListVoById(int afterSaleReasonId);

}
