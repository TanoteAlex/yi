/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.supplier.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.supplier.domain.bo.SupplierAccountBo;
import com.yi.core.supplier.domain.entity.SupplierAccount;
import com.yi.core.supplier.domain.vo.SupplierAccountListVo;
import com.yi.core.supplier.domain.vo.SupplierAccountVo;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 供应商账户
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("供应商账户控制层")
@RestController
@RequestMapping(value = "/supplierAccount")
public class SupplierAccountController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierAccountController.class);

	@Resource
	private ISupplierAccountService supplierAccountService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取供应商账户列表", notes = "根据分页参数查询供应商账户列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierAccountListVo> querySupplierAccount(@RequestBody Pagination<SupplierAccount> query) {
		Page<SupplierAccountListVo> page = supplierAccountService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看供应商账户信息", notes = "根据供应商账户Id获取供应商账户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商账户Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierAccount(@RequestParam("id") int supplierAccountId) {
		try {
			SupplierAccountVo supplierAccount = supplierAccountService.getSupplierAccountVoById(supplierAccountId);
			return RestUtils.successWhenNotNull(supplierAccount);
		} catch (BusinessException ex) {
			LOG.error("get SupplierAccount failure : id=supplierAccountId", ex);
			return RestUtils.error("get SupplierAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增供应商账户", notes = "添加供应商账户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierAccount", value = "供应商账户对象", required = true, dataType = "SupplierAccountBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierAccount(@RequestBody SupplierAccountBo supplierAccount) {
		try {
			SupplierAccountVo dbSupplierAccount = supplierAccountService.addSupplierAccount(supplierAccount);
			return RestUtils.successWhenNotNull(dbSupplierAccount);
		} catch (BusinessException ex) {
			LOG.error("add SupplierAccount failure : supplierAccount", supplierAccount, ex);
			return RestUtils.error("add SupplierAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新供应商账户", notes = "修改供应商账户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierAccount", value = "供应商账户对象", required = true, dataType = "SupplierAccountBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierAccount(@RequestBody SupplierAccountBo supplierAccount) {
		try {
			SupplierAccountVo dbSupplierAccount = supplierAccountService.updateSupplierAccount(supplierAccount);
			return RestUtils.successWhenNotNull(dbSupplierAccount);
		} catch (BusinessException ex) {
			LOG.error("update SupplierAccount failure : supplierAccount", supplierAccount, ex);
			return RestUtils.error("update SupplierAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除供应商账户", notes = "删除供应商账户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商账户Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierAccountById(@RequestParam("id") int supplierAccountId) {
		try {
			supplierAccountService.removeSupplierAccountById(supplierAccountId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierAccount failure : id=supplierAccountId", ex);
			return RestUtils.error("remove SupplierAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询该供应商的账户
	 **/
	@ApiOperation(value = "查询该供应商的账户", notes = "查询该供应商的账户")
	@RequestMapping(value = "getForSupplier", method = RequestMethod.GET)
	public RestResult getForSupplier(HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			return RestUtils.success(supplierAccountService.getBySupplier(supplierToken.getId()));
		} catch (Exception ex) {
			LOG.error("remove SupplierAccount failure : id=supplierAccountId", ex);
			return RestUtils.error("remove SupplierAccount failure : " + ex.getMessage());
		}
	}

}