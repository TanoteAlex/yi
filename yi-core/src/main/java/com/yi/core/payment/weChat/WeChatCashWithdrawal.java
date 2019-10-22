package com.yi.core.payment.weChat;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.vo.AccountVo;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.payment.PaymentUtils;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.utils.RandomUtils;

/**
 * @ClassName 微信提现
 * @Author jstin
 * @Date 2018/12/25 15:57
 * @Version 1.0
 **/
@Component
@Transactional
public class WeChatCashWithdrawal {

	private static final Logger LOG = LoggerFactory.getLogger(WeChatCashWithdrawal.class);

	@Resource
	private IAccountRecordService accountRecordService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IAccountService accountService;

	/**
	 * 微信公众号提现
	 * 
	 * @param money
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public String cashWithdrawalForSp(Integer money, Integer memberId) throws Exception {
		Member member = memberService.getMemberById(memberId);
		if (member != null) {
			if (money < 1) {
				LOG.error("提现金额不能小于1元哦");
				throw new BusinessException("提现金额不能小于1元哦!");
			}
			if (StringUtils.isEmpty(member.getSpOpenId())) {
				LOG.error("提现账号不能为空");
				throw new BusinessException("提现账号不能为空");
			}
			AccountVo accountVo = accountService.getAccountVoByMember(memberId);
			BigDecimal payAmount = new BigDecimal(money);
			if (payAmount.compareTo(accountVo.getCashableCommission()) > 0) {
				LOG.error("提现金额不可大于可提现金额!");
				throw new BusinessException("提现金额不可大于可提现金额!");
			}
			String orderNo = RandomUtils.randomString(11, RandomUtils.RANDRULE.RAND_NUMBER);
			WeChatVo weChatVo = new WeChatVo();
			weChatVo.setAppId(WeChatConfig.SP_APP_ID);
			weChatVo.setMchId(WeChatConfig.SP_MCH_ID);
			weChatVo.setOpenId(member.getSpOpenId());
			weChatVo.setPartnerKey(WeChatConfig.SP_PARTNER_KEY);
			weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// 生产环境
			// if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// } else {
			// weChatVo.setTotalFee("1");
			// }
			weChatVo.setDesc("微信提现");
			weChatVo.setPartnerTradeNo(orderNo);
			// 构建订单参数 和 签名
			SortedMap<String, String> orderMap = PaymentUtils.buildCashWithdrawalOrder_jsapi(weChatVo);
			// 将请求参数转为xml格式
			String requestXml = PaymentUtils.mapToXml(orderMap);
			Map<String, String> map = PaymentUtils.getCashWithdrawalForMpInfo(WeChatConfig.SP_MCH_ID, WeChatConfig.SEND_EED_PACK_URL, requestXml);
			if (map != null && map.size() > 0) {
				String returnCode = map.get("return_code").toString();
				String resultCode = map.get("result_code").toString();
				if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
					Account account = accountService.getById(accountVo.getId());
					/* 可提现佣金 **/
					account.setCashableCommission(account.getCashableCommission().subtract(payAmount));
					/* 已提现佣金 **/
					account.setCashedCommission(account.getCashedCommission().add(payAmount));
					// 会员资金账户记录
					accountRecordService.addAccountRecordByTradeType(null, member, payAmount, MemberEnum.TRADE_TYPE_WITHDRAW_CASH, member);
					LOG.error("提现成功");
					return "提现成功!";
				} else {
					LOG.error("提现失败");
					return "提现失败!";
				}
			}

		}

		return null;
	}

	/**
	 * 微信小程序提现
	 * 
	 * @param money
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public String cashWithdrawalForMp(Integer money, Integer memberId) throws Exception {
		Member member = memberService.getMemberById(memberId);
		if (member != null) {
			if (money < 1) {
				LOG.error("提现金额不能小于1元哦");
				throw new BusinessException("提现金额不能小于1元哦!");
			}
			if (StringUtils.isEmpty(member.getSpOpenId())) {
				LOG.error("提现账号不能为空");
				throw new BusinessException("提现账号不能为空");
			}
			AccountVo accountVo = accountService.getAccountVoByMember(memberId);
			BigDecimal payAmount = new BigDecimal(money);
			if (payAmount.compareTo(accountVo.getCashableCommission()) > 0) {
				LOG.error("提现金额不可大于可提现金额!");
				throw new BusinessException("提现金额不可大于可提现金额!");
			}
			String orderNo = RandomUtils.randomString(11, RandomUtils.RANDRULE.RAND_NUMBER);
			WeChatVo weChatVo = new WeChatVo();
			weChatVo.setAppId(WeChatConfig.MP_APP_ID);
			weChatVo.setMchId(WeChatConfig.MP_MCH_ID);
			weChatVo.setOpenId(member.getMpOpenId());
			weChatVo.setPartnerKey(WeChatConfig.MP_PARTNER_KEY);
			weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// 生产环境
			// if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// } else {
			// weChatVo.setTotalFee("1");
			// }
			weChatVo.setDesc("微信提现");
			weChatVo.setPartnerTradeNo(orderNo);
			// 构建订单参数 和 签名
			SortedMap<String, String> orderMap = PaymentUtils.buildCashWithdrawalOrder_jsapi(weChatVo);
			// 将请求参数转为xml格式
			String requestXml = PaymentUtils.mapToXml(orderMap);
			Map<String, String> map = PaymentUtils.getCashWithdrawalForMpInfo(WeChatConfig.MP_MCH_ID, WeChatConfig.SEND_EED_PACK_URL, requestXml);
			if (map != null && map.size() > 0) {
				String returnCode = map.get("return_code").toString();
				String resultCode = map.get("result_code").toString();
				if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
					Account account = accountService.getById(accountVo.getId());
					/* 可提现佣金 **/
					account.setCashableCommission(account.getCashableCommission().subtract(payAmount));
					/* 已提现佣金 **/
					account.setCashedCommission(account.getCashedCommission().add(payAmount));
					// 会员资金账户记录
					accountRecordService.addAccountRecordByTradeType(null, member, payAmount, MemberEnum.TRADE_TYPE_WITHDRAW_CASH, member);
					return "提现成功!";
				} else {
					return "提现失败!";
				}
			}

		}

		return null;
	}

	/**
	 * 微信公众号提现
	 * 
	 * @param money
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public String cashWithdrawalForApp(Integer money, Integer memberId) throws Exception {
		Member member = memberService.getMemberById(memberId);
		if (member != null) {
			if (money < 1) {
				LOG.error("提现金额不能小于1元哦");
				throw new BusinessException("提现金额不能小于1元哦!");
			}
			if (StringUtils.isEmpty(member.getAppOpenId())) {
				LOG.error("提现账号不能为空");
				throw new BusinessException("提现账号不能为空");
			}
			AccountVo accountVo = accountService.getAccountVoByMember(memberId);
			BigDecimal payAmount = new BigDecimal(money);
			if (payAmount.compareTo(accountVo.getCashableCommission()) > 0) {
				LOG.error("提现金额不可大于可提现金额!");
				throw new BusinessException("提现金额不可大于可提现金额!");
			}
			String orderNo = RandomUtils.randomString(11, RandomUtils.RANDRULE.RAND_NUMBER);
			WeChatVo weChatVo = new WeChatVo();
			weChatVo.setAppId(WeChatConfig.APP_ID);
			weChatVo.setMchId(WeChatConfig.APP_MCH_ID);
			weChatVo.setOpenId(member.getAppOpenId());
			weChatVo.setPartnerKey(WeChatConfig.APP_PARTNER_KEY);
			weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// 生产环境
			// if (WeChatConfig.PROD_ENV) {
			// weChatVo.setTotalFee(PaymentUtils.yuanToFen(payAmount));
			// } else {
			// weChatVo.setTotalFee("1");
			// }
			weChatVo.setDesc("微信提现");
			weChatVo.setPartnerTradeNo(orderNo);
			// 构建订单参数 和 签名
			SortedMap<String, String> orderMap = PaymentUtils.buildCashWithdrawalOrder_jsapi(weChatVo);
			// 将请求参数转为xml格式
			String requestXml = PaymentUtils.mapToXml(orderMap);
			Map<String, String> map = PaymentUtils.getCashWithdrawalForMpInfo(WeChatConfig.APP_MCH_ID, WeChatConfig.SEND_EED_PACK_URL, requestXml);
			if (map != null && map.size() > 0) {
				String returnCode = map.get("return_code").toString();
				String resultCode = map.get("result_code").toString();
				if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
					Account account = accountService.getById(accountVo.getId());
					/* 可提现佣金 **/
					account.setCashableCommission(account.getCashableCommission().subtract(payAmount));
					/* 已提现佣金 **/
					account.setCashedCommission(account.getCashedCommission().add(payAmount));
					// 会员资金账户记录
					accountRecordService.addAccountRecordByTradeType(null, member, payAmount, MemberEnum.TRADE_TYPE_WITHDRAW_CASH, member);
					LOG.error("提现成功");
					return "提现成功!";
				} else {
					LOG.error("提现失败");
					return "提现失败!";
				}
			}

		}

		return null;
	}
}
