/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.activity.controller;

import javax.annotation.Resource;

import com.yi.core.activity.domain.bo.CouponReceiveBo;
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

import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.domain.vo.CouponReceiveListVo;
import com.yi.core.activity.domain.vo.CouponReceiveVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 优惠券领取表
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("优惠券领取表控制层")
@RestController
@RequestMapping(value = "/couponReceive")
public class CouponReceiveController {

	private final Logger LOG = LoggerFactory.getLogger(CouponReceiveController.class);

	@Resource
	private ICouponReceiveService couponReceiveService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取优惠券领取表列表", notes = "根据分页参数查询优惠券领取表列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponReceiveListVo> queryCouponReceive(@RequestBody Pagination<CouponReceive> query) {
		Page<CouponReceiveListVo> page = couponReceiveService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看优惠券领取表信息", notes = "根据优惠券领取表Id获取优惠券信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "优惠券领取表Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCouponReceive(@RequestParam("id") int couponReceiveId) {
		try {
			CouponReceiveVo couponReceive = couponReceiveService.getVoById(couponReceiveId);
			return RestUtils.successWhenNotNull(couponReceive);
		} catch (BusinessException ex) {
			LOG.error("get CouponReceive failure : id={}", couponReceiveId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增优惠券领取表", notes = "添加优惠券领取表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponGrantRecordBo", value = "优惠券领取表对象", required = true, dataType = "CouponGrantRecordBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCouponReceive(@RequestBody CouponReceiveBo couponReceive) {
		try {
			CouponReceiveListVo dbCouponReceive = couponReceiveService.addCouponReceive(couponReceive);
			return RestUtils.successWhenNotNull(dbCouponReceive);
		} catch (BusinessException ex) {
			LOG.error("add CouponReceive failure : {}", couponReceive, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新优惠券领取表", notes = "修改优惠券领取表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponGrantRecordBo", value = "优惠券领取表对象", required = true, dataType = "CouponGrantRecordBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCouponReceive(@RequestBody CouponReceiveBo couponReceive) {
		try {
			CouponReceiveListVo dbCouponReceive = couponReceiveService.updateCouponReceive(couponReceive);
			return RestUtils.successWhenNotNull(dbCouponReceive);
		} catch (BusinessException ex) {
			LOG.error("update CouponReceive failure : {}", couponReceive, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除优惠券领取表", notes = "删除优惠券领取表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "优惠券发领取表Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCouponReceiveById(@RequestParam("id") int couponReceiveId) {
		try {
			couponReceiveService.removeCouponReceiveById(couponReceiveId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove CouponReceive failure : id={}", couponReceiveId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 手工发放优惠券
	 **/
	@ApiOperation(value = "手工发放优惠券", notes = "手工发放优惠券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveBo", value = "优惠券领取表对象", required = true, dataType = "CouponReceiveBo") })
	@RequestMapping(value = "grantCoupon", method = RequestMethod.POST)
	public RestResult grantCoupon(@RequestBody CouponReceiveBo couponReceiveBo) {
		try {
			couponReceiveService.grantCoupon(couponReceiveBo);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("grant Coupon failure : {} ", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 手工发放代金券
	 **/
	@ApiOperation(value = "手工发放代金券", notes = "手工发放代金券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveBo", value = "代金券领取表对象", required = true, dataType = "CouponReceiveBo") })
	@RequestMapping(value = "grantVoucher", method = RequestMethod.POST)
	public RestResult grantVoucher(@RequestBody CouponReceiveBo couponReceiveBo) {
		try {
			couponReceiveService.grantVoucher(couponReceiveBo);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("grant voucher failure : {} ", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 撤回发放的代金券
	 **/
	@ApiOperation(value = "撤回发放的代金券", notes = "撤回发放的代金券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveBo", value = "代金券领取表对象", required = true, dataType = "CouponReceiveBo") })
	@RequestMapping(value = "revokeVoucher", method = RequestMethod.POST)
	public RestResult revokeVoucher(@RequestBody CouponReceiveBo couponReceiveBo) {
		try {
			couponReceiveService.revokeVoucher(couponReceiveBo);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("revoke Voucher failure : {} ", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 手工发放储值券
	 **/
	@ApiOperation(value = "手工发放储值券", notes = "手工发放储值券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveBo", value = "储值券领取表对象", required = true, dataType = "CouponReceiveBo") })
	@RequestMapping(value = "grantStored", method = RequestMethod.POST)
	public RestResult grantStored(@RequestBody CouponReceiveBo couponReceiveBo) {
		try {
			couponReceiveService.grantStored(couponReceiveBo);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("grant Stored failure : {} ", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 撤回发放的储值券
	 **/
	@ApiOperation(value = "撤回发放的储值券", notes = "撤回发放的储值券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveBo", value = "储值券领取表对象", required = true, dataType = "CouponReceiveBo") })
	@RequestMapping(value = "revokeStored", method = RequestMethod.POST)
	public RestResult revokeStored(@RequestBody CouponReceiveBo couponReceiveBo) {
		try {
			couponReceiveService.revokeStored(couponReceiveBo);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("revoke Stored failure : {} ", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}