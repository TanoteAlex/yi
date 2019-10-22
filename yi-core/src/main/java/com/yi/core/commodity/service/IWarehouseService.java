/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import org.springframework.data.domain.Page;

import com.yi.core.commodity.domain.bo.WarehouseBo;
import com.yi.core.commodity.domain.entity.Warehouse;
import com.yi.core.commodity.domain.vo.WarehouseListVo;
import com.yi.core.commodity.domain.vo.WarehouseVo;
import com.yihz.common.persistence.Pagination;

/**
 * 仓库
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface IWarehouseService {

	/**
	 * 分页查询: Warehouse
	 **/
	Page<Warehouse> query(Pagination<Warehouse> query);

	/**
	 * 分页查询: Warehouse
	 **/
	Page<WarehouseListVo> queryListVo(Pagination<Warehouse> query);

	/**
	 * 创建Warehouse
	 **/
	Warehouse addWarehouse(Warehouse warehouse);

	/**
	 * 创建Warehouse
	 **/
	WarehouseVo addWarehouse(WarehouseBo warehouse);

	/**
	 * 更新Warehouse
	 **/
	Warehouse updateWarehouse(Warehouse warehouse);

	/**
	 * 更新Warehouse
	 **/
	WarehouseVo updateWarehouse(WarehouseBo warehouse);

	/**
	 * 删除Warehouse
	 **/
	void removeWarehouseById(int warehouseId);

	/**
	 * 根据ID得到WarehouseBo
	 **/
	WarehouseBo getWarehouseBoById(int warehouseId);

	/**
	 * 根据ID得到WarehouseVo
	 **/
	WarehouseVo getWarehouseVoById(int warehouseId);

	/**
	 * 根据ID得到WarehouseListVo
	 **/
	WarehouseListVo getListVoById(int warehouseId);

}
