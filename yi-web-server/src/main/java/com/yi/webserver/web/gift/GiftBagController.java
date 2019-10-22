/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.gift;

import java.util.SortedMap;

import javax.annotation.Resource;

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

import com.yi.core.gift.domain.bo.GiftBagBo;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.gift.domain.vo.GiftBagListVo;
import com.yi.core.gift.domain.vo.GiftListVo;
import com.yi.core.gift.service.IGiftBagService;
import com.yi.core.gift.service.IGiftService;
import com.yi.core.payment.weChat.WeChatPayService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * 礼包
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
//检查controller
@Api(tags = "礼包控制层")
@RestController
@RequestMapping(value = "/giftBag")
public class GiftBagController {

	private final Logger LOG = LoggerFactory.getLogger(GiftBagController.class);

	@Resource
	private IGiftBagService giftBagService;

	@Resource
	private IGiftService giftService;

	@Resource
	private WeChatPayService weChatPayService;

	/**
	 * 我发出的礼包
	 */
	@ApiOperation(value = "获取我发出的礼包", notes = "根据分页参数查询我发出的礼包列表")
	@RequestMapping(value = "queryMySend", method = RequestMethod.POST)
	public Page<GiftBagListVo> queryGiftBag(@RequestBody Pagination<GiftBag> query) {
		Page<GiftBagListVo> page = giftBagService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 我收到的礼物
	 */
	@ApiOperation(value = "获取我收到的礼物", notes = "根据分页参数查询我收到的礼物列表")
	@RequestMapping(value = "queryMyReceive", method = RequestMethod.POST)
	public Page<GiftListVo> queryGift(@RequestBody Pagination<Gift> query) {
		Page<GiftListVo> page = giftService.queryListVo(query);
		return page;
	}

	/**
	 * 服务号 </br>
	 * 1生成礼包</br>
	 * 2生成礼包</br>
	 * 3生成预支付订单
	 **/
	@ApiOperation(value = "服务号 1生成礼包 2生成礼包 3生成预支付订单", notes = "服务号 1生成礼包 2生成礼包 3生成预支付订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "giftBagBo", value = "礼包对象", required = true, dataType = "GiftBagBo")
	})
	@RequestMapping(value = "createGiftBagForSp", method = RequestMethod.POST)
	public RestResult createGiftBagForSp(@RequestBody GiftBagBo giftBagBo) {
		try {
			SortedMap<String, String> resultMap = giftBagService.createGiftBagForSp(giftBagBo);
			return RestUtils.success(resultMap);
		} catch (Exception ex) {
			LOG.error("create GiftBag failure : {}", giftBagBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 小程序 </br>
	 * 1生成礼包</br>
	 * 2生成礼包订单</br>
	 * 3生成预支付订单
	 **/
	@ApiOperation(value = "小程序 1生成礼包 2生成礼包 3生成预支付订单", notes = "小程序 1生成礼包 2生成礼包 3生成预支付订单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "giftBagBo", value = "礼包对象", required = true, dataType = "GiftBagBo")
	})
	@RequestMapping(value = "createGiftBagForMp", method = RequestMethod.POST)
	public RestResult createGiftBagForMp(@RequestBody GiftBagBo giftBagBo) {
		try {
			SortedMap<String, String> resultMap = giftBagService.createGiftBagForMp(giftBagBo);
			return RestUtils.success(resultMap);
		} catch (Exception ex) {
			LOG.error("create GiftBag failure : {}", giftBagBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 获取礼包详情
	 * 
	 * @param giftBagId
	 * @return
	 */
	@ApiOperation(value = "获取礼包详情", notes = "根据礼包Id获取获取礼包详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "giftBagId", value = "礼包Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getGiftBagDetail", method = RequestMethod.GET)
	public RestResult getGiftBagDetail(@RequestParam("giftBagId") int giftBagId) {
		try {
			return RestUtils.success(giftBagService.getGiftBagDetail(giftBagId));
		} catch (Exception e) {
			LOG.error("getGiftBagDetail error : {}", e.getMessage());
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 领取礼物
	 **/
	@ApiOperation(value = "领取礼物", notes = "领取礼物")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "giftBagBo", value = "礼物对象", required = true, dataType = "GiftBagBo")
	})
	@RequestMapping(value = "receiveGift", method = RequestMethod.POST)
	public RestResult receiveGift(@RequestBody GiftBagBo giftBagBo) {
		try {
			GiftBagListVo giftBagListVo = giftBagService.receiveGift(giftBagBo);
			return RestUtils.successWhenNotNull(giftBagListVo);
		} catch (BusinessException ex) {
			LOG.error("receive Gift failure : {}", giftBagBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}