/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.finance.controller;

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
import com.yi.core.finance.domain.bo.SupplierWithdrawBo;
import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.finance.domain.vo.SupplierWithdrawListVo;
import com.yi.core.finance.domain.vo.SupplierWithdrawVo;
import com.yi.core.finance.service.ISupplierWithdrawService;
import com.yi.core.supplier.domain.bo.SupplierBo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 供应商提现
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("供应商提现控制层")
@RestController
@RequestMapping(value = "/supplierWithdraw")
public class SupplierWithdrawController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierWithdrawController.class);

	@Resource
	private ISupplierWithdrawService supplierWithdrawService;

	@Resource
	private ISupplierService supplierService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取供应商提现列表(总后台)", notes = "根据分页参数查询供应商提现列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierWithdrawListVo> querySupplierWithdraw(@RequestBody Pagination<SupplierWithdraw> query) {
		Page<SupplierWithdrawListVo> page = supplierWithdrawService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取供应商提现列表(供应商)", notes = "根据分页参数查询供应商提现列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<SupplierWithdrawListVo> querySupplierWithdrawForSupplier(@RequestBody Pagination<SupplierWithdraw> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<SupplierWithdrawListVo> page = supplierWithdrawService.queryListVoForSupplier(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看供应商提现信息", notes = "根据供应商提现Id获取供应商提现信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商提现Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierWithdraw(@RequestParam("id") int supplierWithdrawId) {
		try {
			SupplierWithdrawVo supplierWithdraw = supplierWithdrawService.getVoById(supplierWithdrawId);
			return RestUtils.successWhenNotNull(supplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("get SupplierWithdraw failure : id={}", supplierWithdrawId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增供应商提现", notes = "添加供应商提现")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierWithdraw", value = "供应商提现对象", required = true, dataType = "SupplierWithdraw") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierWithdraw(@RequestBody SupplierWithdrawBo supplierWithdraw) {
		try {
			SupplierWithdrawListVo dbSupplierWithdraw = supplierWithdrawService.addSupplierWithdraw(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("add SupplierWithdraw failure : {}", supplierWithdraw, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新供应商提现", notes = "修改供应商提现")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierWithdraw", value = "供应商提现对象", required = true, dataType = "SupplierWithdraw") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierWithdraw(@RequestBody SupplierWithdrawBo supplierWithdraw) {
		try {
			SupplierWithdrawListVo dbSupplierWithdraw = supplierWithdrawService.updateSupplierWithdraw(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("update SupplierWithdraw failure : {}", supplierWithdraw, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除供应商提现", notes = "删除供应商提现")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商提现Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierWithdrawById(@RequestParam("id") int supplierWithdrawId) {
		try {
			supplierWithdrawService.removeSupplierWithdrawById(supplierWithdrawId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierWithdraw failure : id={}", supplierWithdrawId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "申请提现", notes = "申请提现")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierWithdraw", value = "供应商提现对象", required = true, dataType = "SupplierWithdraw") })
	@RequestMapping(value = "addForSupplier", method = RequestMethod.POST)
	public RestResult addForSupplier(@RequestBody SupplierWithdrawBo supplierWithdraw, HttpServletRequest request) {
		try {
			SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
			SupplierBo supplierBo = supplierService.getSupplierBoById(supplierToken.getId());
			supplierWithdraw.setSupplier(supplierBo);
			SupplierWithdrawListVo dbSupplierWithdraw = supplierWithdrawService.addForSupplier(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("add SupplierWithdraw failure : {}", supplierWithdraw, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "重新申请", notes = "重新申请")
	@ApiImplicitParams({ @ApiImplicitParam(name = "supplierWithdraw", value = "供应商提现对象", required = true, dataType = "SupplierWithdraw") })
	@RequestMapping(value = "updateForSupplier", method = RequestMethod.POST)
	public RestResult updateForSupplier(@RequestBody SupplierWithdrawBo supplierWithdraw) {
		try {
			SupplierWithdrawListVo dbSupplierWithdraw = supplierWithdrawService.updateForSupplier(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (BusinessException ex) {
			LOG.error("update SupplierWithdraw failure : {}", supplierWithdraw, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 发放
	 * 
	 * @param supplierWithdrawId
	 * @return
	 */
	@ApiOperation(value = "供应商提现发放", notes = "供应商提现发放")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商提现Id", required = true, dataType = "Int") })
	@RequestMapping(value = "grant", method = RequestMethod.POST)
	public RestResult grant(@RequestBody SupplierWithdrawBo supplierWithdraw) {
		try {
			SupplierWithdrawListVo dbSupplierWithdraw = supplierWithdrawService.grant(supplierWithdraw);
			return RestUtils.successWhenNotNull(dbSupplierWithdraw);
		} catch (Exception ex) {
			LOG.error("grant SupplierWithdraw failure : supplierWithdraw={}", supplierWithdraw, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 驳回
	 * 
	 * @param supplierWithdrawId
	 * @return
	 */
	@ApiOperation(value = "供应商提现驳回", notes = "供应商提现驳回")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "供应商提现Id", required = true, dataType = "Int") })
	@RequestMapping(value = "reject", method = RequestMethod.GET)
	public RestResult reject(@RequestParam("id") int supplierWithdrawId) {
		try {
			supplierWithdrawService.reject(supplierWithdrawId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("reject SupplierWithdraw failure : id={}", supplierWithdrawId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}