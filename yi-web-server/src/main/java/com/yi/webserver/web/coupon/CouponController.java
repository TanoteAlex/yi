package com.yi.webserver.web.coupon;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.vo.CouponListVo;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.ICouponService;
import com.yi.core.member.service.IMemberService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 优惠券
 */
//检查controller
@Api(tags = "优惠券控制层")
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {

	private final Logger LOG = LoggerFactory.getLogger(CouponController.class);

	@Resource
	private ICouponService couponService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IMemberService memberService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取优惠券列表", notes = "根据分页参数查询优惠券列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CouponListVo> queryCoupon(@RequestBody Pagination<Coupon> query) {
		return couponService.queryListVoForApp(query);
	}

	/**
	 * 领取优惠券
	 **/
	@ApiOperation(value = "领取优惠券", notes = "根据会员Id领取优惠券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponId", value = "优惠券Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "receiveCoupon", method = RequestMethod.GET)
	public RestResult receiveCoupon(@RequestParam("couponId") Integer couponId, @RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.successWhenNotNull(couponReceiveService.receiveCoupon(couponId, memberId));
		} catch (BusinessException ex) {
			LOG.error("receive Coupon failure：couponId={}, memberId={}", couponId, memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取优惠券详情
	 * 
	 * @param couponId
	 * @return
	 */
	@ApiOperation(value = "获取优惠券详情", notes = "根据优惠券Id获取优惠券详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponId", value = "优惠券Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getCouponDetail", method = RequestMethod.GET)
	public RestResult getCouponDetail(@RequestParam("couponId") int couponId) {
		try {
			return RestUtils.success(couponService.getCouponDetail(couponId));
		} catch (Exception e) {
			LOG.error("getCouponDetail error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 领取转赠的代金券
	 **/
	@ApiOperation(value = "领取转赠的代金券", notes = "根据会员Id领取转赠的代金券")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveId", value = "代金券Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "receiveVoucher", method = RequestMethod.GET)
	public RestResult receiveVoucher(@RequestParam("couponReceiveId") Integer couponReceiveId, @RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.successWhenNotNull(couponReceiveService.receiveVoucher(couponReceiveId, memberId));
		} catch (BusinessException ex) {
			LOG.error("receive Voucher failure：couponId={}, memberId={}", couponReceiveId, memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取代金券详情
	 * 
	 * @param couponReceiveId
	 * @return
	 */
	@ApiOperation(value = "获取代金券详情", notes = "根据代金券Id获取代金券详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "couponReceiveId", value = "代金券Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getCouponReceiveDetail", method = RequestMethod.GET)
	public RestResult getCouponReceiveDetail(@RequestParam("couponReceiveId") int couponReceiveId) {
		try {
			return RestUtils.success(couponReceiveService.getCouponReceiveDetail(couponReceiveId));
		} catch (Exception e) {
			LOG.error("get CouponReceive Detail error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
