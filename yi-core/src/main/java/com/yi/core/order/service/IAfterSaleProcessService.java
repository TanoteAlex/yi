/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import org.springframework.data.domain.Page;

import com.yi.core.order.domain.bo.AfterSaleProcessBo;
import com.yi.core.order.domain.entity.AfterSaleProcess;
import com.yi.core.order.domain.vo.AfterSaleProcessListVo;
import com.yi.core.order.domain.vo.AfterSaleProcessVo;
import com.yihz.common.persistence.Pagination;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IAfterSaleProcessService {

	/**
	 * 分页查询: AfterSaleProcess
	 **/
	Page<AfterSaleProcess> query(Pagination<AfterSaleProcess> query);

	/**
	 * 分页查询: AfterSaleProcess
	 **/
	Page<AfterSaleProcessListVo> queryListVo(Pagination<AfterSaleProcess> query);

	/**
	 * 分页查询: AfterSaleProcess
	 **/
	Page<AfterSaleProcessListVo> queryListVoForApp(Pagination<AfterSaleProcess> query);

	/**
	 * 创建AfterSaleProcess
	 **/
	AfterSaleProcess addAfterSaleProcess(AfterSaleProcess afterSaleProcess);

	/**
	 * 创建AfterSaleProcess
	 **/
	AfterSaleProcessListVo addAfterSaleProcess(AfterSaleProcessBo afterSaleProcess);

	/**
	 * 更新AfterSaleProcess
	 **/
	AfterSaleProcess updateAfterSaleProcess(AfterSaleProcess afterSaleProcess);

	/**
	 * 更新AfterSaleProcess
	 **/
	AfterSaleProcessListVo updateAfterSaleProcess(AfterSaleProcessBo afterSaleProcess);

	/**
	 * 删除AfterSaleProcess
	 **/
	void removeAfterSaleProcessById(int afterSaleProcessId);

	/**
	 * 根据ID得到AfterSaleProcess
	 **/
	AfterSaleProcess getAfterSaleProcessById(int afterSaleProcessId);

	/**
	 * 根据ID得到AfterSaleProcessBo
	 **/
	AfterSaleProcessBo getAfterSaleProcessBoById(int afterSaleProcessId);

	/**
	 * 根据ID得到AfterSaleProcessVo
	 **/
	AfterSaleProcessVo getAfterSaleProcessVoById(int afterSaleProcessId);

	/**
	 * 根据ID得到AfterSaleProcessListVo
	 **/
	AfterSaleProcessListVo getListVoById(int afterSaleProcessId);

	/**
	 * 根据 处理状态 保存处理流程
	 * 
	 */
	void addByProcessState(AfterSaleProcessBo afterSaleProcess);

}
