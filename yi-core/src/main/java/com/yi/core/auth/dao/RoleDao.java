/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.auth.domain.entity.Role;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface RoleDao extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

	int countByNameAndDeleted(String name, Integer deleted);

	int countByNameAndIdNotAndDeleted(String name, Integer id, Integer deleted);

}