package com.yi.core.task;

import javax.annotation.Resource;

import com.yi.core.basic.dao.IntegralCashDao;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.commodity.service.ICommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.service.IMemberMessageService;
import com.yi.core.common.SmsService;
import com.yi.core.finance.service.ISupplierCheckAccountService;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.gift.service.IGiftService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.order.service.IAfterSaleOrderService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yi.core.promotion.service.IGroupBuyOrderService;

import java.math.BigDecimal;

/**
 * 定时调度任务
 * 
 * @author xuyh
 *
 */
@Component
public class SchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

	@Resource
	private SmsService smsService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IntegralCashDao integralCashDao;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IGiftService giftService;

	@Resource
	private IMemberCommissionService memberCommissionService;

	@Resource
	private IAfterSaleOrderService afterSaleOrderService;

	@Resource
	private IGroupBuyActivityService groupBuyActivityService;

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	@Resource
	private ISupplierCheckAccountService supplierCheckAccountService;

	@Resource
	private IMemberMessageService memberMessageService;

	@Resource
	private ICommodityService commodityService;

	/**
	 * 短信回执 每小时执行一次
	 * 
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void smsReceiptTask() {
		try {
			smsService.updateSmsSendState();
		} catch (Exception e) {
			LOG.error("短信回执-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动作废优惠券 </br>
	 * 1分钟执行一次
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelCouponTask() {
		try {
			couponReceiveService.autoCancelCouponByTask();
		} catch (Exception e) {
			LOG.error("自动作废优惠券-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动关闭未付款订单 每1分钟执行一次</br>
	 * 正常订单超过 XX 分钟未付款，订单自动关闭
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void closeOrderTask() {
		try {
			saleOrderService.closeOrderByTask();
		} catch (Exception e) {
			LOG.error("自动关闭未付款订单-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动收货 每小时执行一次</br>
	 * 发货超过 XX 天未收货，订单自动完成
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoReceiptTask() {
		try {
			saleOrderService.autoReceiptByTask();
		} catch (Exception e) {
			LOG.error("自动收货-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动完成交易 每小时执行一次 </br>
	 * 正常完成超过 XX 天 自动完成交易，不能申请售后
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoTradeTask() {
		try {
			saleOrderService.autoTradeByTask();
		} catch (Exception e) {
			LOG.error("自动完成交易-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动评论 每小时执行一次</br>
	 * 正常完成超过 XX 天 自动五星好评
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoCommentTask() {
		try {
			saleOrderService.autoCommentByTask();
		} catch (Exception e) {
			LOG.error("自动评价-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动作废礼包</br>
	 * 1分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelGiftBagTask() {
		try {
			giftBagService.autoCancelGiftBagByTask();
		} catch (Exception e) {
			LOG.error("自动作废礼包-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动作废礼物</br>
	 * 1分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void autoCancelGiftTask() {
		try {
			giftService.autoCancelGiftByTask();
		} catch (Exception e) {
			LOG.error("自动作废礼物-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 自动将未结算佣金转到可提现佣金</br>
	 * 1小时执行一次
	 * 
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoUnsettledCommissionToCashableCommissionTask() {
		try {
			memberCommissionService.autoUnsettledCommissionToCashableCommissionByTask();
		} catch (Exception e) {
			LOG.error("自动将未计算的佣金转到可提现佣金-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 退款回执 </br>
	 * 3分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */3 * * * ?")
	public void refundReceiptTask() {
		try {
			afterSaleOrderService.refundReceiptByTask();
		} catch (Exception e) {
			LOG.error("退款回执-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 团购发布 </br>
	 * 5分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void publishGroupBuyActivityTask() {
		try {
			groupBuyActivityService.autoPublishGroupBuyActivityByTask();
		} catch (Exception e) {
			LOG.error("团购发布-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 终结团购 </br>
	 * 5分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void finishGroupBuyActivityTask() {
		try {
			groupBuyActivityService.autoFinishGroupBuyActivityByTask();
		} catch (Exception e) {
			LOG.error("终结团购-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 未成团自动失效并退款 </br>
	 * 5分钟执行一次
	 * 
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void autoCancelGroupBuyOrderTask() {
		try {
			groupBuyOrderService.autoCancelGroupBuyOrderByTask();
		} catch (Exception e) {
			LOG.error("失效开团单-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动生成对账单 </br>
	 * 超过三包时效的正常订单
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void autoCheckAccountTask() {
		try {
			supplierCheckAccountService.autoCheckAccountByTask();
		} catch (Exception e) {
			LOG.error("自动生成对账单-->执行异常{}", e.getMessage(), e);
		}
	}

	/**
	 * 自动删除超过三个月会员消息 </br>
	 * 
	 * 每天凌晨一点执行一次
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void autoRemoveMemberMessageTask() {
		try {
			memberMessageService.autoRemoveMemberMessageByTask();
		} catch (Exception e) {
			LOG.error("自动删除会员消息-->执行异常{}", e.getMessage(), e);
		}
	}

//	/**
//	 * 同步商品信息至ES </br>
//	 *
//	 * 每分钟执行一次
//	 */
//	@Scheduled(cron = "0 0/1 * * * ?")
//	public void syncCommoditiesToEsTask(){
//		LOG.info("syncCommodities start");
//		commodityService.syncCommoditiesToEs();
//		LOG.info("syncCommodities end");
//	}
}
