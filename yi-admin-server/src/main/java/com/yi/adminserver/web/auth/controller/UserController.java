/**Powered By[yihz-framework]*Web Site:yihz*Since 2018-2018*/

package com.yi.adminserver.web.auth.controller;

import javax.annotation.Resource;

import com.yi.core.auth.domain.bo.UserBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.service.IUserService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 用户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("用户控制层")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Resource
	private IUserService userService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取用户列表", notes = "根据分页参数查询用户列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<UserListVo> queryUser(@RequestBody Pagination<User> query) {
		Page<UserListVo> page = userService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看用户信息", notes = "根据用户Id获取用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewUser(@RequestParam("id") int userId) {
		try {
			UserVo user = userService.getUserVoById(userId);
			return RestUtils.successWhenNotNull(user);
		} catch (BusinessException ex) {
			LOG.error("get User failure : id=userId", ex);
			return RestUtils.error("get User failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增用户", notes = "添加用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "UserBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addUser(@RequestBody UserBo user) {
		try {
			UserVo dbUser = userService.addUser(user);
			return RestUtils.successWhenNotNull(dbUser);
		} catch (BusinessException ex) {
			LOG.error("add User failure : user", user, ex);
			return RestUtils.error("add User failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新用户", notes = "修改用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "UserBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateUser(@RequestBody UserBo user) {
		try {
			UserVo dbUser = userService.updateUser(user);
			return RestUtils.successWhenNotNull(dbUser);
		} catch (BusinessException ex) {
			LOG.error("update User failure : user", user, ex);
			return RestUtils.error("update User failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeUserById(@RequestParam("id") int userId) {
		try {
			userService.removeUserById(userId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove User failure : id=userId", ex);
			return RestUtils.error("remove User failure : " + ex.getMessage());
		}
	}

	/**
	 * 禁用
	 */
	@ApiOperation(value = "禁用用户", notes = "禁用用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "disable", method = RequestMethod.GET)
	public RestResult updateDisable(@RequestParam("id") int id) {
		try {
			return RestUtils.success(userService.updateDisable(id));

		} catch (Exception ex) {
			LOG.error("remove User failure : id=userId", ex);
			return RestUtils.error("remove User failure : " + ex.getMessage());
		}
	}

	/**
	 * 启用
	 */
	@ApiOperation(value = "启用用户", notes = "启用用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "enable", method = RequestMethod.GET)
	public RestResult updateEnable(@RequestParam("id") int id) {
		try {
			return RestUtils.success(userService.updateEnable(id));
		} catch (Exception ex) {
			LOG.error("remove User failure : id=userId", ex);
			return RestUtils.error("remove User failure : " + ex.getMessage());
		}
	}
}