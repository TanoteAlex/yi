/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.order.domain.entity.CustomFreightTemplate;

/**
 * 自定义运费
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface CustomFreightTemplateDao extends JpaRepository<CustomFreightTemplate, Integer>, JpaSpecificationExecutor<CustomFreightTemplate> {

//	/**
//	 * 删除指定运费模板数据
//	 * 
//	 * @param freightTemplateConfigId
//	 */
//	@Modifying
//	@Query("delete from CustomFreightTemplate t where t.freightTemplateConfig.id=?1")
//	void deleteByFreightTemplateConfig(Integer freightTemplateConfigId);

	/**
	 * 根据模板配置 查询自定义运费模板
	 * 
	 * @param freightTemplateConfigId
	 * @param deleted
	 * @return
	 */
	List<CustomFreightTemplate> findByFreightTemplateConfig_idAndDeleted(Integer freightTemplateConfigId, Integer deleted);
}