/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.UserBo;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.simple.UserSimple;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IUserService {

	/**
	 * 根据ID得到User
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(int userId);

	/**
	 * 根据ID得到UserVo
	 * 
	 * @param userId
	 * @return
	 */
	UserVo getUserVoById(int userId);

	/**
	 * 根据ID得到UserListVo
	 * 
	 * @param userId
	 * @return
	 */
	UserListVo getUserListVoById(int userId);

	/**
	 * 根据Entity创建User
	 * 
	 * @param user
	 * @return
	 */
	User addUser(User user);

	/**
	 * 根据BO创建User
	 * 
	 * @param userBo
	 * @return
	 */
	UserVo addUser(UserBo userBo);

	/**
	 * 根据Entity更新User
	 * 
	 * @param user
	 * @return
	 */
	User updateUser(User user);

	/**
	 * 根据BO更新User
	 * 
	 * @param userBo
	 * @return
	 */
	UserVo updateUser(UserBo userBo);

	/**
	 * 删除User
	 * 
	 * @param userId
	 */
	void removeUserById(int userId);

	/**
	 * 分页查询: User
	 * 
	 * @param query
	 * @return
	 */
	Page<User> query(Pagination<User> query);

	/**
	 * 分页查询: UserListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<UserListVo> queryListVo(Pagination<User> query);
	
	/**
	 * 获取user 转换器
	 * @return
	 */
	EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> getUserConvert();
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	UserVo login(String username, String password);


	/**
	 * 禁用
	 */
	UserVo updateDisable(int userId);


	/**
	 *启用
	 */
	UserVo updateEnable(int userId);

}
