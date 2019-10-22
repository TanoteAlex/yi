/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.member.controller;

import javax.annotation.Resource;

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

import com.yi.core.member.domain.bo.LoginRecordBo;
import com.yi.core.member.domain.entity.LoginRecord;
import com.yi.core.member.domain.vo.LoginRecordListVo;
import com.yi.core.member.domain.vo.LoginRecordVo;
import com.yi.core.member.service.ILoginRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 登录记录
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("登录记录控制层")
@RestController
@RequestMapping(value = "/loginRecord")
public class LoginRecordController {

	private final Logger LOG = LoggerFactory.getLogger(LoginRecordController.class);

	@Resource
	private ILoginRecordService loginRecordService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取登录记录列表", notes = "根据分页参数查询登录记录列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<LoginRecordListVo> queryLoginRecord(@RequestBody Pagination<LoginRecord> query) {
		Page<LoginRecordListVo> page = loginRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增登录记录", notes = "添加登录记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginRecordBo", value = "登录记录对象", required = true, dataType = "LoginRecordBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addLoginRecord(@RequestBody LoginRecordBo loginRecordBo) {
		try {
			LoginRecordListVo loginRecordListVo = loginRecordService.addLoginRecord(loginRecordBo);
			return RestUtils.successWhenNotNull(loginRecordListVo);
		} catch (BusinessException ex) {
			LOG.error("add LoginRecord failure : {}", loginRecordBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新登录记录", notes = "修改登录记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginRecordBo", value = "登录记录对象", required = true, dataType = "LoginRecordBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateLoginRecord(@RequestBody LoginRecordBo loginRecordBo) {
		try {
			LoginRecordListVo loginRecordListVo = loginRecordService.updateLoginRecord(loginRecordBo);
			return RestUtils.successWhenNotNull(loginRecordListVo);
		} catch (BusinessException ex) {
			LOG.error("update LoginRecord failure : {}", loginRecordBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除登录记录", notes = "删除登录记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "登录记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeLoginRecordById(@RequestParam("id") int loginRecordId) {
		try {
			loginRecordService.removeLoginRecordById(loginRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove LoginRecord failure : id={}", loginRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看登录记录信息", notes = "根据登录记录Id获取登录记录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "登录记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getLoginRecordVoById(@RequestParam("id") int loginRecordId) {
		try {
			LoginRecordVo loginRecordVo = loginRecordService.getLoginRecordVoById(loginRecordId);
			return RestUtils.successWhenNotNull(loginRecordVo);
		} catch (BusinessException ex) {
			LOG.error("get LoginRecord failure : id={}", loginRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "查看登录记录信息", notes = "编辑时根据登录记录Id获取登录记录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "登录记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getLoginRecordBoById(@RequestParam("id") int loginRecordId) {
		try {
			LoginRecordBo loginRecordBo = loginRecordService.getLoginRecordBoById(loginRecordId);
			return RestUtils.successWhenNotNull(loginRecordBo);
		} catch (BusinessException ex) {
			LOG.error("get LoginRecord failure : id={}", loginRecordId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}