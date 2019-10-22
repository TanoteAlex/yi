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

import com.yi.core.member.domain.bo.AccountRecordBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.AccountRecordVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员资金账户记录
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("会员账户记录控制层")
@RestController
@RequestMapping(value = "/accountRecord")
public class AccountRecordController {

	private final Logger LOG = LoggerFactory.getLogger(AccountRecordController.class);

	@Resource
	private IAccountRecordService accountRecordService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取会员账户记录列表", notes = "根据分页参数查询会员账户记录列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AccountRecordListVo> queryAccountRecord(@RequestBody Pagination<AccountRecord> query) {
		Page<AccountRecordListVo> page = accountRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看会员账户记录信息", notes = "根据会员账户记录Id获取会员账户记录信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "会员账户记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewAccountRecord(@RequestParam("id") int accountRecordId) {
		try {
			AccountRecordVo accountRecord = accountRecordService.getVoById(accountRecordId);
			return RestUtils.successWhenNotNull(accountRecord);
		} catch (BusinessException ex) {
			LOG.error("get AccountRecord failure : id=accountRecordId", ex);
			return RestUtils.error("get AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增会员账户记录", notes = "添加会员账户记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountRecord", value = "会员账户记录对象", required = true, dataType = "AccountRecordBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAccountRecord(@RequestBody AccountRecordBo accountRecord) {
		try {
			AccountRecordVo dbAccountRecord = accountRecordService.addAccountRecord(accountRecord);
			return RestUtils.successWhenNotNull(dbAccountRecord);
		} catch (BusinessException ex) {
			LOG.error("add AccountRecord failure : accountRecord", accountRecord, ex);
			return RestUtils.error("add AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新会员账户记录", notes = "修改会员账户记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "accountRecord", value = "会员账户记录对象", required = true, dataType = "AccountRecordBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAccountRecord(@RequestBody AccountRecordBo accountRecord) {
		try {
			AccountRecordVo dbAccountRecord = accountRecordService.updateAccountRecord(accountRecord);
			return RestUtils.successWhenNotNull(dbAccountRecord);
		} catch (BusinessException ex) {
			LOG.error("update AccountRecord failure : accountRecord", accountRecord, ex);
			return RestUtils.error("update AccountRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除会员账户记录", notes = "删除会员账户记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "会员账户记录Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAccountRecordById(@RequestParam("id") int accountRecordId) {
		try {
			accountRecordService.removeAccountRecordById(accountRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AccountRecord failure : id=accountRecordId", ex);
			return RestUtils.error("remove AccountRecord failure : " + ex.getMessage());
		}
	}

}