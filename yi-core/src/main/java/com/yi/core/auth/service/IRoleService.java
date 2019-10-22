/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.RoleBo;
import com.yi.core.auth.domain.entity.Role;
import com.yi.core.auth.domain.vo.RoleListVo;
import com.yi.core.auth.domain.vo.RoleVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IRoleService {

	/**
	 * 根据ID得到Role
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRoleById(int roleId);

	/**
	 * 根据ID得到RoleVo
	 * 
	 * @param roleId
	 * @return
	 */
	RoleVo getRoleVoById(int roleId);

	/**
	 * 根据ID得到RoleListVo
	 * 
	 * @param roleId
	 * @return
	 */
	RoleListVo getRoleListVoById(int roleId);

	/**
	 * 根据Entity创建Role
	 * 
	 * @param role
	 * @return
	 */
	Role addRole(Role role);

	/**
	 * 根据BO创建Role
	 * 
	 * @param roleBo
	 * @return
	 */
	RoleVo addRole(RoleBo roleBo);

	/**
	 * 根据Entity更新Role
	 * 
	 * @param role
	 * @return
	 */
	Role updateRole(Role role);

	/**
	 * 根据BO更新Role
	 * 
	 * @param roleBo
	 * @return
	 */
	RoleListVo updateRole(RoleBo roleBo);

	/**
	 * 删除Role
	 * 
	 * @param roleId
	 */
	void removeRoleById(int roleId);

	/**
	 * 分页查询: Role
	 * 
	 * @param query
	 * @return
	 */
	Page<Role> query(Pagination<Role> query);

	/**
	 * 分页查询: RoleListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<RoleListVo> queryListVo(Pagination<Role> query);

	/**
	 * 添加权限
	 */
	Role jurisdiction(Role role);

	/**
	 * 禁用
	 */
	RoleVo updateDisable(int roleId);

	/**
	 * 启用
	 */
	RoleVo updateEnable(int roleId);

}
