package com.yi.core.commodity.dao;///*

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// * Powered By [yihz-framework]
// * Web Site: yihz
// * Since 2018 - 2018
// */
//
//package com.yi.core.commodity.dao;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import com.yi.core.commodity.domain.entity.AttributeName;
//import com.yihz.common.orm.BaseDao;

import com.yi.core.commodity.domain.entity.Attribute;

/**
 * 属性
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface AttributeDao extends JpaRepository<Attribute, Integer>, JpaSpecificationExecutor<Attribute> {

	/**
	 * 根据attributeGroupId 查询属性集合
	 * 
	 * @param attributeGroupId
	 * @param deleted
	 * @return
	 */
	Set<Attribute> findByAttributeGroup_idAndDeleted(Integer attributeGroupId, Integer deleted);

}