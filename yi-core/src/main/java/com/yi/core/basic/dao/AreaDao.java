/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.basic.domain.entity.Area;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface AreaDao extends JpaRepository<Area, Integer>, JpaSpecificationExecutor<Area> {

	/**
	 * 根据父类 查询省市区集合
	 * 
	 * @param parent
	 * @return
	 */
	List<Area> findByParent(Area parent);

	/**
	 * 根据父类ID 查询省市区集合
	 * 
	 * @param parentId
	 * @return
	 */
	List<Area> findByParent_id(Integer parentId);

	/**
	 * 
	 * @param areaCode
	 * @return
	 */
	Area findByAreaCode(String areaCode);

}