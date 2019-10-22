package com.yi.webserver.web.member;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.service.ICommunityService;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.common.SmsService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.bo.ShippingAddressBo;
import com.yi.core.member.domain.entity.AccountRecord;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.vo.AccountRecordListVo;
import com.yi.core.member.domain.vo.MemberCommissionListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.member.service.IShippingAddressService;
import com.yi.core.member.service.ISignTimeService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.weChat.WeChatAuthService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 会员
 */
@Api(tags = "会员控制层")
@RestController
@RequestMapping("/member")
public class MemberController {

	private final Logger LOG = LoggerFactory.getLogger(MemberController.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private IShippingAddressService shippingAddressService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private ICommunityService communityService;

	@Resource
	private ISignTimeService signTimeService;

	@Resource
	private IIntegralRecordService integralRecordService;

	@Resource
	private SmsService smsService;

	@Resource
	private IAccountService accountService;

	@Resource
	private IIntegralTaskService integralTaskService;

	@Resource
	private WeChatAuthService weChatAuthService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	@Resource
	private ISaleOrderService saleOrderService;

	/**
	 * 通过验证码登录
	 *
	 * @param memberBo
	 * @return
	 */
	@ApiOperation(value = "通过手机号和验证码登录", notes = "通过手机号和验证码登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "loginBySms", method = RequestMethod.POST)
	public RestResult loginBySms(@RequestBody MemberBo memberBo, HttpServletRequest request) {
		try {
			if (StringUtils.isAnyBlank(memberBo.getPhone(), memberBo.getSmsCode())) {
				return RestUtils.error("手机号或验证码不能为空");
			}
			boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
			if (!flag) {
				return RestUtils.error("验证码不正确");
			}
			MemberVo memberVo = memberService.loginBySms(memberBo);
			return RestUtils.successWhenNotNull(memberVo);
		} catch (Exception e) {
			LOG.error("login error：{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 通过账号密码登陆
	 *
	 * @param memberBo
	 * @return
	 */
	@ApiOperation(value = "通过账号密码登陆", notes = "通过账号密码登陆")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "loginByPassword", method = RequestMethod.POST)
	public RestResult loginByPassword(@RequestBody MemberBo memberBo, HttpServletRequest request) {
		try {
			MemberVo memberVo = memberService.loginByPassword(memberBo);
			return RestUtils.successWhenNotNull(memberVo);
		} catch (Exception e) {
			LOG.error("login error： {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 服务号 微信授权 自动登录
	 *
	 * @param openId
	 * @return
	 */
	@ApiOperation(value = "服务号 微信授权 自动登录", notes = "服务号 微信授权 自动登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "unionId", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "getMemberByWeChatForSp", method = RequestMethod.GET)
	public RestResult getMemberByWeChatForSp(@RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
		try {
			return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForSp(unionId, openId));
		} catch (Exception e) {
			LOG.error("getMemberByWeChatForSp error：{}", e.getMessage(), e);
			return RestUtils.error("授权失败，请稍后重试");
		}
	}

	/**
	 * 小程序 微信授权 自动登录
	 *
	 * @param openId
	 * @return
	 */
	@ApiOperation(value = "小程序 微信授权 自动登录", notes = "小程序 微信授权 自动登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "unionId", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "getMemberByWeChatForMp", method = RequestMethod.GET)
	public RestResult getMemberByWeChatForMp(@RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
		try {
			return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForMp(unionId, openId));
		} catch (Exception e) {
			LOG.error("getMemberByWeChatForMp error：{}", e.getMessage(), e);
			return RestUtils.error("授权失败，请稍后重试");
		}
	}

	/**
	 * app 微信授权 自动登录
	 *
	 * @param unionId
	 * @param unionId
	 * @return
	 */
	@ApiOperation(value = "app 微信授权 自动登录", notes = "app 微信授权 自动登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "unionId", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "appOpenId", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "getMemberByWeChatForApp", method = RequestMethod.GET)
	public RestResult getMemberByWeChatForApp(@RequestParam("unionId") String unionId, @RequestParam("appOpenId") String appOpenId) {
		try {
			return RestUtils.successWhenNotNull(memberService.getMemberByWeChatForApp(unionId, appOpenId));
		} catch (Exception e) {
			LOG.error("getMemberByWeChatForApp error：{}", e.getMessage(), e);
			return RestUtils.error("授权失败，请稍后重试");
		}
	}

	/**
	 * 服务号 绑定微信
	 *
	 * @param memberId
	 * @param unionId
	 * @param openId
	 * @return
	 */
	@ApiOperation(value = "服务号 绑定微信", notes = "服务号 绑定微信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "unionId", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "bindWeChatForSp", method = RequestMethod.GET)
	public RestResult bindWeChatForSp(@RequestParam("memberId") String memberId, @RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
		try {
			return RestUtils.successWhenNotNull(memberService.bindWeChatForSp(memberId, unionId, openId));
		} catch (Exception e) {
			LOG.error("bindWeChatForSp error{}", e.getMessage(), e);
			return RestUtils.error("绑定失败，请稍后重试");
		}
	}

	/**
	 * 微信小程序 绑定手机
	 * 
	 * @param memberId
	 * @param encryptedData
	 * @param iv
	 * @return
	 */
	@ApiOperation(value = "微信小程序 绑定微信", notes = "微信小程序 绑定微信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "code", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "encryptedData", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "bindPhoneForMp", method = RequestMethod.GET)
	public RestResult bindPhoneForMp(@RequestParam("memberId") Integer memberId, @RequestParam("code") String code, @RequestParam("encryptedData") String encryptedData,
			@RequestParam("iv") String iv) {
		try {
			String phone = weChatAuthService.getUserInfoByBindPhoneForMp(code, encryptedData, iv);
			MemberListVo dbMemberListVo = memberService.bindPhoneForMp(memberId, phone);
			return RestUtils.successWhenNotNull(dbMemberListVo);
		} catch (Exception e) {
			LOG.error("bindPhoneForMp error:{}", e.getMessage(), e);
			return RestUtils.error("绑定失败，请稍后重试");
		}
	}

	/**
	 * APP 绑定微信
	 * 
	 * @param unionId
	 * @param memberId
	 * @param openId
	 * @return
	 */
	@ApiOperation(value = "APP 绑定微信", notes = "APP 绑定微信")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "String"),
			@ApiImplicitParam(name = "unionId", value = "unionId", required = true, dataType = "String"),
			@ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String"), })
	@RequestMapping(value = "bindWeChatForApp", method = RequestMethod.GET)
	public RestResult bindWeChatForApp(@RequestParam("memberId") String memberId, @RequestParam("unionId") String unionId, @RequestParam("openId") String openId) {
		try {
			return RestUtils.successWhenNotNull(memberService.bindWeChatForApp(memberId, unionId, openId));
		} catch (Exception e) {
			LOG.error("bindWeChatForApp error:{}", e.getMessage(), e);
			return RestUtils.error("绑定失败，请稍后重试");
		}
	}

	/**
	 * 注册
	 *
	 * @param memberBo
	 * @return
	 */
	@ApiOperation(value = "注册", notes = "注册")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public RestResult register(@RequestBody MemberBo memberBo) {
		if (StringUtils.isBlank(memberBo.getPhone())) {
			return RestUtils.error("手机号不能为空");
		}
		if (StringUtils.isBlank(memberBo.getPassword())) {
			return RestUtils.error("密码不能为空");
		}
		if (StringUtils.isBlank(memberBo.getSmsCode())) {
			return RestUtils.error("验证码不能为空");
		}
		boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
		if (!flag) {
			return RestUtils.error("验证码不正确");
		}
		try {
			MemberVo memberVo = null;
			// 微信授权登录的注册
			if (StringUtils.isNotBlank(memberBo.getUnionId())) {
				memberVo = memberService.registerByWeChat(memberBo);
			} else {
				// 普通注册
				memberVo = memberService.register(memberBo);
			}
			return RestUtils.success(memberVo);
		} catch (Exception e) {
			LOG.error("register error：{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 邀请注册
	 *
	 * @param memberBo
	 * @return
	 */
	@ApiOperation(value = "邀请注册", notes = "邀请注册")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "inviteRegister", method = RequestMethod.POST)
	public RestResult inviteRegister(@RequestBody MemberBo memberBo) {
		if (StringUtils.isBlank(memberBo.getPhone())) {
			return RestUtils.error("手机号不能为空");
		}
		if (StringUtils.isBlank(memberBo.getPassword())) {
			return RestUtils.error("密码不能为空");
		}
		if (StringUtils.isBlank(memberBo.getSmsCode())) {
			return RestUtils.error("验证码不能为空");
		}
		boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
		if (!flag) {
			return RestUtils.error("验证码不正确");
		}
		try {
			MemberVo memberVo = memberService.inviteRegister(memberBo);
			return RestUtils.success(memberVo);
		} catch (Exception e) {
			LOG.error("register error：{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 根据ID获取用户信息 包括支付密码
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "根据会员ID获取用户信息 包括支付密码", notes = "根据会员ID获取用户信息 包括支付密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getMember", method = RequestMethod.GET)
	public RestResult getMember(@RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.successWhenNotNull(memberService.getMemberVoByIdForApp(memberId));
		} catch (Exception e) {
			LOG.error("getMember error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 修改用户信息
	 *
	 * @param memberBo
	 * @return
	 */
	@ApiOperation(value = "修改用户信息", notes = "修改用户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "changeMember", method = RequestMethod.POST)
	public RestResult changeMember(@RequestBody MemberBo memberBo) {
		try {
			return RestUtils.success(memberService.changeMember(memberBo));
		} catch (Exception e) {
			LOG.error("changeMember error : {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 忘记密码
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "忘记密码", notes = "忘记密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "forgetPassword", method = RequestMethod.POST)
	public RestResult forgetPassword(@RequestBody MemberBo memberBo) {
		if (StringUtils.isBlank(memberBo.getPhone())) {
			return RestUtils.error("手机号不能为空");
		}
		if (StringUtils.isBlank(memberBo.getSmsCode())) {
			return RestUtils.error("验证码不能为空");
		}
		boolean checkCode = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
		if (!checkCode) {
			return RestUtils.error("验证码不正确");
		}
		try {
			return RestUtils.success(memberService.forgetPassword(memberBo));
		} catch (Exception e) {
			LOG.error("forgetPassword error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 修改密码
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "changePwd", method = RequestMethod.POST)
	public RestResult changePwd(@RequestBody MemberBo memberBo) {
		try {
			return RestUtils.success(memberService.changePwd(memberBo));
		} catch (Exception e) {
			LOG.error("changePwd error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 修改支付密码
	 */
	@ApiOperation(value = "修改支付密码", notes = "修改支付密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "modifyPayPassword", method = RequestMethod.POST)
	public RestResult modifyPayPassword(@RequestBody MemberBo memberBo) {
		try {
			return RestUtils.success(memberService.modifyPayPassword(memberBo));
		} catch (Exception e) {
			LOG.error("changePwd error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取收货地址
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取收货地址", notes = "根据会员Id获取收货地址")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getAddress", method = RequestMethod.GET)
	public RestResult getAddress(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(shippingAddressService.getShippingAddressListVoByMemberId(memberId));
		} catch (Exception e) {
			LOG.error("getAddress error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 添加收货地址
	 *
	 * @param shippingAddressBo
	 * @return
	 */
	@ApiOperation(value = "添加收货地址", notes = "添加收货地址")
	@ApiImplicitParams({ @ApiImplicitParam(name = "shippingAddressBo", value = "收货地址对象", required = true, dataType = "ShippingAddressBo") })
	@RequestMapping(value = "addAddress", method = RequestMethod.POST)
	public RestResult addAddress(@RequestBody ShippingAddressBo shippingAddressBo) {
		try {
			return RestUtils.success(shippingAddressService.addShippingAddress(shippingAddressBo));
		} catch (Exception e) {
			LOG.error("addAddress error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 编辑收货地址
	 *
	 * @param shippingAddressBo
	 * @return
	 */
	@ApiOperation(value = "编辑收货地址", notes = "编辑收货地址")
	@ApiImplicitParams({ @ApiImplicitParam(name = "shippingAddressBo", value = "收货地址对象", required = true, dataType = "ShippingAddressBo") })
	@RequestMapping(value = "changeAddress", method = RequestMethod.POST)
	public RestResult changeAddress(@RequestBody ShippingAddressBo shippingAddressBo) {
		try {
			return RestUtils.success(shippingAddressService.updateShippingAddress(shippingAddressBo));
		} catch (Exception e) {
			LOG.error("changeAddress error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 删除收货地址
	 *
	 * @param addressId
	 * @return
	 */
	@ApiOperation(value = "删除收货地址", notes = "根据收货地址Id删除收货地址")
	@ApiImplicitParams({ @ApiImplicitParam(name = "addressId", value = "收货地址Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeAddress", method = RequestMethod.GET)
	public RestResult removeAddress(@RequestParam("addressId") int addressId) {
		try {
			shippingAddressService.removeShippingAddressById(addressId);
			return RestUtils.success();
		} catch (Exception e) {
			LOG.error("removeAddress error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取地址信息
	 *
	 * @param addressId
	 * @return
	 */
	@ApiOperation(value = "获取地址信息", notes = "根据收货地址Id获取地址信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "addressId", value = "收货地址Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getAddressDetail", method = RequestMethod.GET)
	public RestResult getAddressDetail(@RequestParam("addressId") int addressId) {
		try {
			return RestUtils.success(shippingAddressService.getShippingAddressDetail(addressId));
		} catch (Exception e) {
			LOG.error("getAddressDetail error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 设置收货地址为默认地址
	 *
	 * @param memberId
	 * @param addressId
	 * @return
	 */
	@ApiOperation(value = "设置收货地址为默认地址", notes = "设置收货地址为默认地址")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "addressId", value = "收货地址Id", required = true, dataType = "Int") })
	@RequestMapping(value = "setDefaultAddress", method = RequestMethod.GET)
	public RestResult setDefaultAddress(@RequestParam("memberId") int memberId, @RequestParam("addressId") int addressId) {
		try {
			return RestUtils.success(shippingAddressService.setDefaultAddress(memberId, addressId));
		} catch (Exception e) {
			LOG.error("setDefaultAddress error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取我的优惠券
	 * 
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "获取我的优惠券", notes = "获取我的优惠券")
	@RequestMapping(value = "getCoupon", method = RequestMethod.POST)
	public RestResult getCoupon(@RequestBody Pagination<CouponReceive> query) {
		try {
			return RestUtils.success(couponReceiveService.queryListVoForApp(query));
		} catch (Exception e) {
			LOG.error("getCoupon error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取会员登录信息", notes = "获取会员登录信息")
	@RequestMapping(value = "memberSign", method = RequestMethod.GET)
	public RestResult memberSign(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(signTimeService.getSignInfo(memberId));
		} catch (Exception e) {
			LOG.error("getRewards error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 点击签到
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "点击签到", notes = "根据会员Id点击签到")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "checkIn", method = RequestMethod.GET)
	public RestResult checkIn(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(signTimeService.clickSign(memberId));
		} catch (Exception e) {
			LOG.error("clickCheck error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取我的团队人数
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取我的团队人数", notes = "根据会员Id获取我的团队人数")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getMyTeamNum", method = RequestMethod.GET)
	public RestResult getMyTeamNum(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(memberService.getMyTeamNum(memberId));
		} catch (Exception e) {
			LOG.error("getMyTeamNum error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取会员余额
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取会员余额", notes = "根据会员Id获取会员余额")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getBalance", method = RequestMethod.GET)
	public RestResult getBalance(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(memberService.getBalance(memberId));
		} catch (Exception e) {
			LOG.error("getBalance error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 账户记录
	 * 
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "账户记录", notes = "账户记录")
	@RequestMapping(value = "getAccountRecords", method = RequestMethod.POST)
	public Page<AccountRecordListVo> getAccountRecords(@RequestBody Pagination<AccountRecord> query) {
		return accountRecordService.queryListVoForApp(query);
	}

	/**
	 * 根据城市查询小区
	 * 
	 * @param city
	 * @return
	 */
	@ApiOperation(value = "根据城市查询小区", notes = "根据城市查询小区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "city", value = "城市", required = true, dataType = "String") })
	@RequestMapping(value = "getCommunityByCity", method = RequestMethod.GET)
	public RestResult getCommunityByCity(@RequestParam("city") String city) {
		try {
			return RestUtils.success(communityService.getCommunityByCity(city));
		} catch (Exception ex) {
			LOG.error("getAddressByCity failure : city={}", city, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改用户小区
	 **/
	@ApiOperation(value = "修改用户小区", notes = "修改用户小区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "communityId", value = "小区Id", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "memberId", value = "会员Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateCommunity", method = RequestMethod.GET)
	public RestResult updateCommunity(@RequestParam("communityId") int communityId, @RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(memberService.updateCommunity(communityId, memberId));
		} catch (Exception ex) {
			LOG.error("update Community failure : memberId={}", memberId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 修改手机号
	 */
	@ApiOperation(value = "修改手机号", notes = "修改手机号")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberBo", value = "会员对象", required = true, dataType = "MemberBo") })
	@RequestMapping(value = "updatePhone", method = RequestMethod.POST)
	public RestResult updatePhone(@RequestBody MemberBo memberBo) {
		if (StringUtils.isBlank(memberBo.getPhone())) {
			return RestUtils.error("手机号不能为空");
		}
		if (StringUtils.isBlank(memberBo.getSmsCode())) {
			return RestUtils.error("验证码不能为空");
		}
		boolean flag = smsService.checkCode(memberBo.getPhone(), memberBo.getSmsCode());
		if (!flag) {
			return RestUtils.error("验证码不正确");
		}
		try {
			return RestUtils.success(memberService.forgetPassword(memberBo));
		} catch (Exception e) {
			LOG.error("forgetPassword error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取会员等级信息
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取会员等级信息", notes = "根据会员ID获取会员等级信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getMemberLevelInfo", method = RequestMethod.GET)
	public RestResult getMemberLevelInfo(@RequestParam("memberId") Integer memberId) {
		if (memberId == null) {
			return RestUtils.error("请求参数不能为空");
		}
		try {
			return RestUtils.success(memberService.getMemberLevelInfo(memberId));
		} catch (Exception e) {
			LOG.error("forgetPassword error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取用户所属小区信息
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取用户所属小区信息", notes = "根据会员ID获取用户所属小区信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getCommunityByMemberId", method = RequestMethod.GET)
	public RestResult getCommunityByMemberId(@RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.success(communityService.getCommunityInfo(memberId));
		} catch (Exception e) {
			LOG.error("getCommunityByMemberId error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 查询会员账户信息
	 *
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "查询会员账户信息", notes = "根据会员ID查询会员账户信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getAccountByMemberId", method = RequestMethod.GET)
	public RestResult getAccountByMemberId(@RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.success(accountService.getAccountVoByMember(memberId));
		} catch (Exception e) {
			LOG.error("getAccountByMemberId error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 分页查询用户积分记录
	 *
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "分页查询用户积分记录", notes = "分页查询用户积分记录")
	@RequestMapping(value = "queryIntegralRecords", method = RequestMethod.POST)
	public Page<IntegralRecordListVo> getIntegralRecord(@RequestBody Pagination<IntegralRecord> query) {
		Page<IntegralRecordListVo> pageInfo = new PageImpl<IntegralRecordListVo>(new ArrayList<>());
		// 没有查询条件 不查询数据
		if (CollectionUtils.isNotEmpty(query.getFilter().getFilters()) && "member.id".equals(query.getFilter().getFilters().get(0).getField())) {
			pageInfo = integralRecordService.queryListVo(query);
		}
		return pageInfo;
	}

	/**
	 * 查看积分赠送规则
	 *
	 * @param taskType
	 * @return
	 */
	@ApiOperation(value = "查看积分赠送规则", notes = "查看积分赠送规则")
	@ApiImplicitParams({ @ApiImplicitParam(name = "taskType", value = "积分任务类型", required = true, dataType = "Int") })
	@RequestMapping(value = "getIntegralTask", method = RequestMethod.GET)
	public RestResult getIntegralRecord(@RequestParam("taskType") Integer taskType) {
		try {
			return RestUtils.success(integralTaskService.getIntegralTaskByType(taskType));
		} catch (Exception e) {
			LOG.error("get IntegralTask error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 分页查询 我的一级团队
	 * 
	 */
	@ApiOperation(value = "分页查询 我的一级团队", notes = "分页查询 我的一级团队")
	@RequestMapping(value = "queryFirstLevelTeam", method = RequestMethod.POST)
	public Page<MemberListVo> queryFirstLevelTeam(@RequestBody Pagination<Member> query) {
		Page<MemberListVo> page = memberService.queryMyTeam(query, MemberEnum.DISTRIBUTION_LEVEL_FIRST.getCode());
		return page;
	}

	/**
	 * 分页查询 我的二级团队
	 */
	@ApiOperation(value = "分页查询 我的二级团队", notes = "分页查询 我的二级团队")
	@RequestMapping(value = "querySecondLevelTeam", method = RequestMethod.POST)
	public Page<MemberListVo> querySecondLevelTeam(@RequestBody Pagination<Member> query) {
		Page<MemberListVo> page = memberService.queryMyTeam(query, MemberEnum.DISTRIBUTION_LEVEL_SECOND.getCode());
		return page;
	}

	/**
	 * 查询我的分销佣金
	 */
	@ApiOperation(value = "分页查询 查询我的分销佣金", notes = "分页查询 查询我的分销佣金")
	@RequestMapping(value = "queryMyCommission", method = RequestMethod.POST)
	public Page<MemberCommissionListVo> queryMyCommission(@RequestBody Pagination<MemberCommission> query) {
		Page<MemberCommissionListVo> pages = memberCommissionService.queryListVoForApp(query);
		return pages;
	}

	/**
	 * 查询小区业绩
	 * 
	 * @param query
	 * @return
	 */
	@ApiOperation(value = "分页查询 查询小区业绩", notes = "分页查询 查询小区业绩")
	@RequestMapping(value = "queryCommunityPerformance", method = RequestMethod.POST)
	public Page<SaleOrderListVo> queryCommunityPerformance(@RequestBody Pagination<SaleOrder> query) {
		Page<SaleOrderListVo> page = saleOrderService.queryListVoForCommunity(query);
		return page;
	}

	/**
	 * 查询小区成员
	 */
	@ApiOperation(value = "分页查询 查询小区成员", notes = "分页查询 查询小区成员")
	@RequestMapping(value = "queryCommunityMember", method = RequestMethod.POST)
	public Page<MemberListVo> queryMember(@RequestBody Pagination<Member> query) {
		Page<MemberListVo> page = memberService.queryListVoForCommunity(query);
		return page;
	}

	/**
	 * 佣金提至余额
	 * 
	 * @param memberId
	 *            会员ID
	 * @param withdrawAmount
	 *            提取金额
	 * @return
	 */
	@ApiOperation(value = "佣金提至余额", notes = "佣金提至余额")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "withdrawAmount", value = "提取金额", required = true, dataType = "Int") })
	@RequestMapping(value = "withdrawCommission", method = RequestMethod.GET)
	public RestResult withdrawCommission(@RequestParam("memberId") Integer memberId, @RequestParam("withdrawAmount") BigDecimal withdrawAmount) {
		try {
			return RestUtils.success(accountService.withdrawCommission(memberId, withdrawAmount));
		} catch (Exception e) {
			LOG.error("withdraw Commission error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取邀请人数
	 * 
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取邀请人数", notes = "获取邀请人数")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getInviteNum", method = RequestMethod.GET)
	public RestResult getInviteNum(@RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.success(memberService.getInviteNum(memberId));
		} catch (Exception e) {
			LOG.error("get Invite Number error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取邀请订单和会员
	 * 
	 * @param memberId
	 * @return
	 */
	@ApiOperation(value = "获取邀请订单和会员", notes = "获取邀请订单和会员")
	@ApiImplicitParams({ @ApiImplicitParam(name = "memberId", value = "会员ID", required = true, dataType = "Int") })
	@RequestMapping(value = "getInviteOrderAndMember", method = RequestMethod.GET)
	public RestResult getInviteOrderAndMember(@RequestParam("memberId") Integer memberId) {
		try {
			return RestUtils.success(memberService.getInviteOrderAndMember(memberId));
		} catch (Exception e) {
			LOG.error("get Invite Number and order error :{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
