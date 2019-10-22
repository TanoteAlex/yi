/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.adminserver.web.basic.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.IntegralCashBo;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.vo.IntegralCashListVo;
import com.yi.core.basic.domain.vo.IntegralCashVo;
import com.yi.core.basic.service.IIntegralCashService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 积分抵现设置
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/integralCash")
public class IntegralCashController {

	private final Logger LOG = LoggerFactory.getLogger(IntegralCashController.class);

	@Resource
	private IIntegralCashService integralCashService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<IntegralCashListVo> queryIntegralCash(@RequestBody Pagination<IntegralCash> query) {
		Page<IntegralCashListVo> page = integralCashService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addIntegralCash(@RequestBody IntegralCashBo integralCashBo) {
		try {
			IntegralCashListVo integralCashListVo = integralCashService.addIntegralCash(integralCashBo);
			return RestUtils.successWhenNotNull(integralCashListVo);
		} catch (BusinessException ex) {
			LOG.error("add IntegralCash failure : {}", integralCashBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateIntegralCash(@RequestBody IntegralCashBo integralCashBo) {
		try {
			System.out.println(integralCashBo.getLimitCash());
			IntegralCashListVo integralCashListVo = integralCashService.updateIntegralCash(integralCashBo);
			return RestUtils.successWhenNotNull(integralCashListVo);
		} catch (BusinessException ex) {
			LOG.error("update IntegralCash failure : {}", integralCashBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeIntegralCashById(@RequestParam("id") int integralCashId) {
		try {
			integralCashService.removeIntegralCashById(integralCashId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove IntegralCash failure : id={}", integralCashId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getIntegralCashVoById(@RequestParam("id") int integralCashId) {
		try {
			IntegralCashVo integralCashVo = integralCashService.getIntegralCashVoById(integralCashId);
			return RestUtils.successWhenNotNull(integralCashVo);
		} catch (BusinessException ex) {
			LOG.error("get IntegralCash failure : id={}", integralCashId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getIntegralCashBoById(@RequestParam("id") int integralCashId) {
		try {
			IntegralCashBo integralCashBo = integralCashService.getIntegralCashBoById(integralCashId);
			return RestUtils.successWhenNotNull(integralCashBo);
		} catch (BusinessException ex) {
			LOG.error("get IntegralCash failure : id={}", integralCashId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}