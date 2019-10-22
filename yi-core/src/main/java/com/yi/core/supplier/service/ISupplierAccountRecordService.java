/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.service;

import com.yi.core.supplier.domain.bo.SupplierAccountRecordBo;
import com.yi.core.supplier.domain.entity.SupplierAccountRecord;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountRecordVo;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;

/**
 * 供应商账户记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ISupplierAccountRecordService {

	Page<SupplierAccountRecord> query(Pagination<SupplierAccountRecord> query);

	/**
	 * 创建SupplierAccountRecord
	 **/
	SupplierAccountRecordListVo addSupplierAccountRecord(SupplierAccountRecordBo supplierAccountRecord);

	/**
	 * 更新SupplierAccountRecord
	 **/
	SupplierAccountRecordListVo updateSupplierAccountRecord(SupplierAccountRecordBo supplierAccountRecord);

	/**
	 * 删除SupplierAccountRecord
	 **/
	void removeSupplierAccountRecordById(int supplierAccountRecordId);

	/**
	 * 根据ID得到SupplierAccountRecordBo
	 **/
	SupplierAccountRecordBo getSupplierAccountRecordBoById(int supplierAccountRecordId);

	/**
	 * 根据ID得到SupplierAccountRecordVo
	 **/
	SupplierAccountRecordVo getSupplierAccountRecordVoById(int supplierAccountRecordId);

	/**
	 * 根据ID得到SupplierAccountRecordListVo
	 **/
	SupplierAccountRecordListVo getListVoById(int supplierAccountRecordId);

	/**
	 * 分页查询: SupplierAccountRecord
	 **/
	Page<SupplierAccountRecordListVo> queryListVo(Pagination<SupplierAccountRecord> query);

}
