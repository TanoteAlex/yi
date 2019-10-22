/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.activity.controller;

import javax.annotation.Resource;

import com.yi.core.member.domain.vo.MemberVo;
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

import com.yi.core.activity.domain.bo.CouponGrantConfigBo;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.activity.domain.vo.CouponGrantConfigListVo;
import com.yi.core.activity.domain.vo.CouponGrantConfigVo;
import com.yi.core.activity.service.ICouponGrantConfigService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券发放方案
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("优惠券发放方案控制层")
@RestController
@RequestMapping(value = "/couponGrantConfig")
public class CouponGrantConfigController {

	private final Logger LOG = LoggerFactory.getLogger(CouponGrantConfigController.class);

	@Resource
	private ICouponGrantConfigService couponGrantConfigService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取优惠券发放方案列表", notes = "根据分页参数查询优惠券发放方案列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponGrantConfigListVo> queryCouponGrantConfig(@RequestBody Pagination<CouponGrantConfig> query) {
		Page<CouponGrantConfigListVo> page = couponGrantConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增优惠券发放方案", notes = "添加优惠券发放方案")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponGrantConfigBo", value = "优惠券发放方案对象", required = true, dataType = "CouponGrantConfigBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponGrantConfig(@RequestBody CouponGrantConfigBo couponGrantConfigBo) {
		try {
			CouponGrantConfigListVo couponGrantConfigListVo = couponGrantConfigService.addCouponGrantConfig(couponGrantConfigBo);
			return RestUtils.successWhenNotNull(couponGrantConfigListVo);
		} catch (BusinessException ex) {
			LOG.error("add CouponGrantConfig failure : couponGrantConfigBo", couponGrantConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新优惠券发放方案", notes = "修改优惠券发放方案")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponGrantConfigBo", value = "优惠券发放方案对象", required = true, dataType = "CouponGrantConfigBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponGrantConfig(@RequestBody CouponGrantConfigBo couponGrantConfigBo) {
		try {
			CouponGrantConfigListVo couponGrantConfigListVo = couponGrantConfigService.updateCouponGrantConfig(couponGrantConfigBo);
			return RestUtils.successWhenNotNull(couponGrantConfigListVo);
		} catch (BusinessException ex) {
			LOG.error("update CouponGrantConfig failure : couponGrantConfigBo", couponGrantConfigBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除优惠券发放方案", notes = "删除优惠券发放方案")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "优惠券发放方案Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponGrantConfigById(@RequestParam("id") int couponGrantConfigId) {
		try {
			couponGrantConfigService.removeCouponGrantConfigById(couponGrantConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看优惠券发放方案信息", notes = "根据优惠券发放方案Id获取优惠券发放方案信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "优惠券发放方案Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult getCouponGrantConfigVoById(@RequestParam("id") int couponGrantConfigId) {
		try {
			CouponGrantConfigVo couponGrantConfigVo = couponGrantConfigService.getCouponGrantConfigVoById(couponGrantConfigId);
			return RestUtils.successWhenNotNull(couponGrantConfigVo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "获取编辑优惠券发放方案信息", notes = "编辑时根据优惠券发放方案Id获取优惠券发放方案信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "优惠券发放方案Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getCouponGrantConfigBoById(@RequestParam("id") int couponGrantConfigId) {
		try {
			CouponGrantConfigBo couponGrantConfigBo = couponGrantConfigService.getCouponGrantConfigBoById(couponGrantConfigId);
			return RestUtils.successWhenNotNull(couponGrantConfigBo);
		} catch (BusinessException ex) {
			LOG.error("get CouponGrantConfig failure : id={}", couponGrantConfigId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 启用禁用
	 **/
	@ApiOperation(value = "优惠券发放方案启用禁用", notes = "根据优惠券发放方案Id修改启用、禁用状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponGrantConfigId", value = "优惠券发放方案Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "upAndDown", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("couponGrantConfigId")int couponGrantConfigId) {
		try {
			CouponGrantConfigVo couponGrantConfigVo=couponGrantConfigService.updateState(couponGrantConfigId);
			return RestUtils.success(couponGrantConfigId);
		} catch (Exception ex) {
			LOG.error("修改状态失败:", ex);
			return RestUtils.error("修改状态失败:" + ex.getMessage());
		}
	}

}