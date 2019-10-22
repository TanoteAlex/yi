/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.supplier.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.supplier.domain.entity.Supplier;

/**
 * 供应商
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface SupplierDao extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

	Supplier findByPhoneAndPasswordAndDeleted(String username, String password, Integer deleted);

	/**
	 * 统计供应商数量
	 * 
	 * @param deleted
	 * @return
	 */
	int countByDeleted(Integer deleted);

	Supplier findByIdAndDeleted(Integer id, Integer deleted);

	/**
	 * 统计每天新增数量
	 * 
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "select Date(createTime) as createTime, count(id) from Supplier" + " where deleted=?1 and Date(createTime)>=Date(?2) and Date(createTime)<=Date(?3)"
			+ " group by Date(createTime)")
	List<Object[]> findDailyAddNumByDate(Integer deleted, Date startDate, Date endDate);

	/**
	 * 校验重复
	 * 
	 * @param supplierName
	 * @param deleted
	 * @return
	 */
	int countBySupplierNameAndPhoneAndDeleted(String supplierName, String phone, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param supplierName
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countBySupplierNameAndDeletedAndIdNot(String supplierName, Integer deleted, Integer id);

}