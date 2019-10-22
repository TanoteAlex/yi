/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.auth.domain.entity.User;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	/**
	 * 校验重复
	 * 
	 * @param username
	 * @param deleted
	 * @return
	 */
	int countByUsernameAndDeleted(String username, Integer deleted);

	/**
	 * 校验重复
	 * 
	 * @param username
	 * @param id
	 * @param deleted
	 * @return
	 */
	int countByUsernameAndDeletedAndIdNot(String username, Integer deleted, Integer id);

	/**
	 * 根据用户名和密码 查询数据
	 * 
	 * @param username
	 * @param password
	 * @param deleted
	 * @return
	 */
	User findByUsernameAndPasswordAndDeleted(String username, String password, Integer deleted);

}