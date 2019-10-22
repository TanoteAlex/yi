/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service;

import java.util.Date;
import java.util.List;

import com.yi.core.supplier.domain.simple.SupplierSimple;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.data.domain.Page;

import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierListVo;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierService {

	/**
	 * 根据ID得到Supplier
	 * 
	 * @param supplierId
	 * @return
	 */
	Supplier getSupplierById(int supplierId);

	/**
	 * 根据ID得到SupplierVo
	 * 
	 * @param supplierId
	 * @return
	 */
	SupplierVo getSupplierVoById(int supplierId);

	/**
	 * 根据ID得到SupplierListVo
	 * 
	 * @param supplierId
	 * @return
	 */
	SupplierListVo getSupplierListVoById(int supplierId);

	/**
	 * 根据ID得到SupplierVo
	 * 
	 * @param supplierId
	 * @return
	 */
	SupplierBo getSupplierBoById(int supplierId);

	/**
	 * 根据Entity创建Supplier
	 * 
	 * @param supplier
	 * @return
	 */
	Supplier addSupplier(Supplier supplier);

	/**
	 * 根据BO创建Supplier
	 * 
	 * @param supplierBo
	 * @return
	 */
	SupplierVo addSupplier(SupplierBo supplierBo);

	/**
	 * 根据Entity更新Supplier
	 * 
	 * @param supplier
	 * @return
	 */
	Supplier updateSupplier(Supplier supplier);

	/**
	 * 根据BO更新Supplier
	 * 
	 * @param supplierBo
	 * @return
	 */
	SupplierVo updateSupplier(SupplierBo supplierBo);

	/**
	 * 删除Supplier
	 * 
	 * @param supplierId
	 */
	void removeSupplierById(int supplierId);

	/**
	 * 分页查询: Supplier
	 * 
	 * @param query
	 * @return
	 */
	Page<Supplier> query(Pagination<Supplier> query);

	/**
	 * 分页查询: SupplierListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierListVo> queryListVo(Pagination<Supplier> query);

	SupplierListVo updateState(int supplierId);

	/**
	 * 查询所有的供应商
	 * 
	 * @return
	 */
	List<SupplierListVo> querySupplierList();

	/**
	 * 供应商登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	SupplierVo login(String username, String password);

	/**
	 * 统计供应商数量
	 *
	 * @return
	 */
	int getSupplierNum();

	/**
	 * 统计每天新增数量
	 *
	 * @param statrtDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate);

	EntityListVoBoSimpleConvert<Supplier, SupplierBo, SupplierVo, SupplierSimple, SupplierListVo> getSupplierConvert();

}
