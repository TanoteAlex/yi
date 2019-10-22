/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.supplier.domain.entity.Supplier;

/**
 * 运费模板配置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface FreightTemplateConfigDao extends JpaRepository<FreightTemplateConfig, Integer>, JpaSpecificationExecutor<FreightTemplateConfig> {

	/**
	 * 校验重复
	 * 
	 * @param configName
	 * @param supplier
	 * @param deleted
	 * @return
	 */
	int countByConfigNameAndSupplierAndDeleted(String configName, Supplier supplier, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param configName
	 * @param supplier
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByConfigNameAndSupplierAndDeletedAndIdNot(String configName, Supplier supplier, Integer deleted, Integer id);
}