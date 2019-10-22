/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
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

import com.yi.core.member.domain.bo.AccountBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.vo.AccountListVo;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.member.service.IAccountService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员资金账户
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("会员账户控制层")
@RestController
@RequestMapping(value = "/account")
public class AccountController {

	private final Logger LOG = LoggerFactory.getLogger(AccountController.class);

	@Resource
	private IAccountService accountService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取会员账户列表", notes = "根据分页参数查询会员账户列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AccountListVo> queryAccount(@RequestBody Pagination<Account> query) {
		Page<AccountListVo> page = accountService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看会员账户信息", notes = "根据会员账户Id获取会员账户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "会员账户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAccount(@RequestParam("id") int accountId) {
		try {
			AccountVo account = accountService.getVoById(accountId);
			return RestUtils.successWhenNotNull(account);
		} catch (BusinessException ex) {
			LOG.error("get Account failure : id=accountId", ex);
			return RestUtils.error("get Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增会员账户", notes = "添加会员账户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "会员账户对象", required = true, dataType = "AccountBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAccount(@RequestBody AccountBo account) {
		try {
			AccountVo dbAccount = accountService.addAccount(account);
			return RestUtils.successWhenNotNull(dbAccount);
		} catch (BusinessException ex) {
			LOG.error("add Account failure : account", account, ex);
			return RestUtils.error("add Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新会员账户", notes = "修改会员账户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "会员账户对象", required = true, dataType = "AccountBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAccount(@RequestBody AccountBo account) {
		try {
			AccountVo dbAccount = accountService.updateAccount(account);
			return RestUtils.successWhenNotNull(dbAccount);
		} catch (BusinessException ex) {
			LOG.error("update Account failure : account", account, ex);
			return RestUtils.error("update Account failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除会员账户", notes = "删除会员账户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "会员账户Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAccountById(@RequestParam("id") int accountId) {
		try {
			accountService.removeAccountById(accountId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Account failure : id=accountId", ex);
			return RestUtils.error("remove Account failure : " + ex.getMessage());
		}
	}
}