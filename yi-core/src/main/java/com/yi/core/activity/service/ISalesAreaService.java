/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.bo.SalesAreaBo;
import com.yi.core.activity.domain.entity.SalesArea;
import com.yi.core.activity.domain.vo.SalesAreaListVo;
import com.yi.core.activity.domain.vo.SalesAreaVo;
import com.yihz.common.persistence.Pagination;

import java.util.List;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ISalesAreaService {

	/**
	 * 分页查询: SalesArea
	 **/
	Page<SalesArea> query(Pagination<SalesArea> query);

	/**
	 * 分页查询: SalesArea
	 **/
	Page<SalesAreaListVo> queryListVo(Pagination<SalesArea> query);

	/**
	 * 分页查询: SalesArea
	 **/
	Page<SalesAreaListVo> queryListVoForApp(Pagination<SalesArea> query);

	/**
	 * 查询所有活动专区
	 **/
	public List<SalesAreaListVo> getAll();

	/**
	 * 创建SalesArea
	 **/
	SalesArea addSalesArea(SalesArea salesArea);

	/**
	 * 创建SalesArea
	 **/
	SalesAreaListVo addSalesArea(SalesAreaBo salesArea);

	/**
	 * 更新SalesArea
	 **/
	SalesArea updateSalesArea(SalesArea salesArea);

	/**
	 * 更新SalesArea
	 **/
	SalesAreaListVo updateSalesArea(SalesAreaBo salesArea);

	/**
	 * 删除SalesArea
	 **/
	void removeSalesAreaById(Integer salesAreaId);

	/**
	 * 根据ID得到SalesArea
	 **/
	SalesArea getSalesAreaById(Integer salesAreaId);

	/**
	 * 根据ID得到SalesAreaBo
	 **/
	SalesAreaBo getSalesAreaBoById(Integer salesAreaId);

	/**
	 * 根据ID得到SalesAreaVo
	 **/
	SalesAreaVo getSalesAreaVoById(Integer salesAreaId);

	/**
	 * 根据ID得到SalesAreaListVo
	 **/
	SalesAreaListVo getListVoById(Integer salesAreaId);

}
