/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.order.domain.entity.FreeFreightTemplate;

/**
 * 
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface FreeFreightTemplateDao extends JpaRepository<FreeFreightTemplate, Integer>, JpaSpecificationExecutor<FreeFreightTemplate> {

//	/**
//	 * 删除指定运费模板数据
//	 * 
//	 * @param freightTemplateConfigId
//	 */
//	@Modifying
//	@Query("delete from FreeFreightTemplate t where t.freightTemplateConfig.id=?1")
//	void deleteByFreightTemplateConfig(Integer freightTemplateConfigId);

	/**
	 * 根据模板配置 查询包邮运费模板
	 * 
	 * @param freightTemplateConfigId
	 * @param deleted
	 * @return
	 */
	List<FreeFreightTemplate> findByFreightTemplateConfig_idAndDeleted(Integer freightTemplateConfigId, Integer deleted);

}