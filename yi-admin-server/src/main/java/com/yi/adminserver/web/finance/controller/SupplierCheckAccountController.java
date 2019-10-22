/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.finance.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yi.core.common.FileService;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.service.ISupplierCheckAccountService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 供应商对账
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/supplierCheckAccount")
public class SupplierCheckAccountController {

	private final Logger LOG = LoggerFactory.getLogger(SupplierCheckAccountController.class);

	@Resource
	private ISupplierCheckAccountService supplierCheckAccountService;

	@Resource
	private FileService fileService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SupplierCheckAccountListVo> querySupplierCheckAccount(@RequestBody Pagination<SupplierCheckAccount> query) {
		Page<SupplierCheckAccountListVo> page = supplierCheckAccountService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSupplierCheckAccount(@RequestParam("id") int supplierCheckAccountId) {
		try {
			SupplierCheckAccountVo supplierCheckAccount = supplierCheckAccountService.getVoById(supplierCheckAccountId);
			return RestUtils.successWhenNotNull(supplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("get SupplierCheckAccount failure : id=supplierCheckAccountId", ex);
			return RestUtils.error("get SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSupplierCheckAccount(@RequestBody SupplierCheckAccount supplierCheckAccount) {
		try {
			SupplierCheckAccountVo dbSupplierCheckAccount = supplierCheckAccountService.addSupplierCheckAccount(supplierCheckAccount);
			return RestUtils.successWhenNotNull(dbSupplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("add SupplierCheckAccount failure : supplierCheckAccount", supplierCheckAccount, ex);
			return RestUtils.error("add SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSupplierCheckAccount(@RequestBody SupplierCheckAccount supplierCheckAccount) {
		try {
			SupplierCheckAccount dbSupplierCheckAccount = supplierCheckAccountService.updateSupplierCheckAccount(supplierCheckAccount);
			return RestUtils.successWhenNotNull(dbSupplierCheckAccount);
		} catch (BusinessException ex) {
			LOG.error("update SupplierCheckAccount failure : supplierCheckAccount", supplierCheckAccount, ex);
			return RestUtils.error("update SupplierCheckAccount failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSupplierCheckAccountById(@RequestParam("id") int supplierCheckAccountId) {
		try {
			supplierCheckAccountService.removeById(supplierCheckAccountId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SupplierCheckAccount failure : id=supplierCheckAccountId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 导出对账明细
	 */
	@ApiOperation(value = "对账明细导出", notes = "对账明细导出")
	@RequestMapping(value = "exportExcel")
	public void exportExcel(Pagination<SupplierCheckAccount> query, HttpServletRequest request, HttpServletResponse response) {
		try {
			String json = URLDecoder.decode(request.getParameter("query"), "UTF-8");
			JSONObject userJson = JSONObject.parseObject(json).getJSONObject("requests");
			query = JSON.toJavaObject(userJson, query.getClass());
			List<SupplierCheckAccount> supplierCheckAccounts = supplierCheckAccountService.getExportCheckAccounts(query);
			Workbook workbook = fileService.createCheckAccountExcel(supplierCheckAccounts);
			String outputFileName = "供应商对账明细-" + System.currentTimeMillis();
			fileService.exportExcel(workbook, outputFileName, request, response);
		} catch (Exception e) {
			LOG.error("对账明细导出异常", e);
		}
	}
}