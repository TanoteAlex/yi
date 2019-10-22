/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.order.controller;

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.order.domain.bo.AfterSaleProcessBo;
import com.yi.core.order.domain.entity.AfterSaleProcess;
import com.yi.core.order.domain.vo.AfterSaleProcessListVo;
import com.yi.core.order.domain.vo.AfterSaleProcessVo;
import com.yi.core.order.service.IAfterSaleProcessService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
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

import com.yihz.common.exception.BusinessException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 售后处理
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("售后处理控制层")
@RestController
@RequestMapping(value = "/afterSaleProcess")
public class AfterSaleProcessController {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleProcessController.class);

	@Resource
	private IAfterSaleProcessService afterSaleProcessService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取售后处理列表(总后台)", notes = "根据分页参数查询售后处理列表(总后台)")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<AfterSaleProcessListVo> queryAfterSaleProcess(@RequestBody Pagination<AfterSaleProcess> query) {
		Page<AfterSaleProcessListVo> page = afterSaleProcessService.queryListVo(query);
		return page;
	}

	/**
	 * 供应商分页查询
	 */
	@ApiOperation(value = "获取售后处理列表(供应商)", notes = "根据分页参数查询售后处理列表(供应商)")
	@RequestMapping(value = "queryForSupplier", method = RequestMethod.POST)
	public Page<AfterSaleProcessListVo> queryAfterSaleProcessForSupplier(@RequestBody Pagination<AfterSaleProcess> query, HttpServletRequest request) {
		SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
		query.getParams().put("supplier.id", supplierToken.getId());
		Page<AfterSaleProcessListVo> page = afterSaleProcessService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增售后处理", notes = "添加售后处理")
	@ApiImplicitParams({ @ApiImplicitParam(name = "afterSaleProcessBo", value = "售后处理对象", required = true, dataType = "AfterSaleProcessBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addAfterSaleProcess(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			AfterSaleProcessListVo afterSaleProcessListVo = afterSaleProcessService.addAfterSaleProcess(afterSaleProcessBo);
			return RestUtils.successWhenNotNull(afterSaleProcessListVo);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleProcess failure : {}", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新售后处理", notes = "修改售后处理")
	@ApiImplicitParams({ @ApiImplicitParam(name = "afterSaleProcessBo", value = "售后处理对象", required = true, dataType = "AfterSaleProcessBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateAfterSaleProcess(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			AfterSaleProcessListVo afterSaleProcessListVo = afterSaleProcessService.updateAfterSaleProcess(afterSaleProcessBo);
			return RestUtils.successWhenNotNull(afterSaleProcessListVo);
		} catch (BusinessException ex) {
			LOG.error("update AfterSaleProcess failure : {}", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除售后处理", notes = "删除售后处理")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "售后处理Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeAfterSaleProcessById(@RequestParam("id") int afterSaleProcessId) {
		try {
			afterSaleProcessService.removeAfterSaleProcessById(afterSaleProcessId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看售后处理信息(总后台)", notes = "根据售后处理Id获取售后处理信息(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "售后处理Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getAfterSaleProcessVoById(@RequestParam("id") int afterSaleProcessId) {
		try {
			AfterSaleProcessVo afterSaleProcessVo = afterSaleProcessService.getAfterSaleProcessVoById(afterSaleProcessId);
			return RestUtils.successWhenNotNull(afterSaleProcessVo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商查看对象详情
	 **/
	@ApiOperation(value = "查看售后处理信息(供应商)", notes = "根据售后处理Id获取售后处理信息(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "售后处理Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getByIdForSupplier", method = RequestMethod.GET)
	public RestResult getAfterSaleProcessVoByIdForSupplier(@RequestParam("id") int afterSaleProcessId) {
		try {
			AfterSaleProcessVo afterSaleProcessVo = afterSaleProcessService.getAfterSaleProcessVoById(afterSaleProcessId);
			return RestUtils.successWhenNotNull(afterSaleProcessVo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑时查看售后处理信息", notes = "编辑时根据售后处理Id获取售后处理信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "售后处理Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getAfterSaleProcessBoById(@RequestParam("id") int afterSaleProcessId) {
		try {
			AfterSaleProcessBo afterSaleProcessBo = afterSaleProcessService.getAfterSaleProcessBoById(afterSaleProcessId);
			return RestUtils.successWhenNotNull(afterSaleProcessBo);
		} catch (BusinessException ex) {
			LOG.error("get AfterSaleProcess failure : id={}", afterSaleProcessId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 确认退货
	 **/
	@ApiOperation(value = "确认退货(总后台)", notes = "确认退货(总后台)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "afterSaleProcessBo", value = "售后处理对象", required = true, dataType = "AfterSaleProcessBo") })
	@RequestMapping(value = "confirmReturn", method = RequestMethod.POST)
	public RestResult confirmReturn(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			afterSaleProcessService.addByProcessState(afterSaleProcessBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("confirm return failure : {}", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 供应商确认退货
	 **/
	@ApiOperation(value = "确认退货(供应商)", notes = "确认退货(供应商)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "afterSaleProcessBo", value = "售后处理对象", required = true, dataType = "AfterSaleProcessBo") })
	@RequestMapping(value = "confirmReturnForSupplier", method = RequestMethod.POST)
	public RestResult confirmReturnForSupplier(@RequestBody AfterSaleProcessBo afterSaleProcessBo) {
		try {
			afterSaleProcessService.addByProcessState(afterSaleProcessBo);
			return RestUtils.success(Boolean.TRUE);
		} catch (BusinessException ex) {
			LOG.error("add AfterSaleProcess failure : {}", afterSaleProcessBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}