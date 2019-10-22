package com.yi.webserver.web.home;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.entity.Message;
import com.yi.core.basic.service.IMessageService;
import com.yi.core.cms.service.IAdvertisementService;
import com.yi.core.cms.service.IBannerService;
import com.yi.core.cms.service.IRecommendService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 广告 推荐 通知 接口
 */
@Api(tags = "广告 推荐 通知 接口控制层")
@RestController
@RequestMapping(value = "/homepage")
public class HomepageController {

	private final Logger LOG = LoggerFactory.getLogger(HomepageController.class);

	@Resource
	private IBannerService bannerService;

	@Resource
	private IRecommendService recommendService;

	@Resource
	private IAdvertisementService advertisementService;

	@Resource
	private IMessageService messageService;

	/**
	 * 获取系统消息
	 *
	 * @return
	 */
	@ApiOperation(value = "获取系统消息", notes = "根据分页参数获取系统消息列表")
	@RequestMapping(value = "getMessages", method = RequestMethod.POST)
	public RestResult getMessages(@RequestBody Pagination<Message> query) {
		try {
			return RestUtils.success(messageService.queryListVo(query));
		} catch (Exception e) {
			LOG.error("getMessages error:" + e.getMessage(), e);
			return RestUtils.error("getMessages error: " + e.getMessage());
		}
	}

	// /**
	// * 获取轮播图
	// *
	// * @return
	// */
	// @Deprecated
	// @RequestMapping(value = "getBanner", method = RequestMethod.GET)
	// public RestResult getBanner() {
	// try {
	// // 轮播图
	// return RestUtils
	// .success(advertisementService.getAdvertisementListVoForApp(CmsEnum.POSITION_TYPE_BANNER.getCode()));
	// } catch (Exception e) {
	// LOG.error("getBanner error" + e.getMessage(), e);
	// return RestUtils.error("getBanner error: " + e.getMessage());
	// }
	// }
	//
	// @Deprecated
	// @RequestMapping(value = "getFloorRecommends", method = RequestMethod.GET)
	// public RestResult getFloorRecommends() {
	// try {
	// // 楼层推荐
	// return
	// RestUtils.success(recommendService.getFloorRecommends(CmsEnum.POSITION_TYPE_FLOOR.getCode()));
	// } catch (Exception e) {
	// LOG.error("getFloorRecommends error" + e.getMessage(), e);
	// return RestUtils.error("getFloorRecommends error: " + e.getMessage());
	// }
	// }
	//
	// /**
	// * 今日推荐
	// *
	// * @return
	// */
	// @Deprecated
	// @RequestMapping(value = "getTodayRecommends", method = RequestMethod.GET)
	// public RestResult getTodayRecommends() {
	// try {
	// // 今日推荐
	// return
	// RestUtils.success(recommendService.getTodayRecommends(CmsEnum.POSITION_TYPE_TODAY.getCode()));
	// } catch (Exception e) {
	// LOG.error("getTodayRecommends error:" + e.getMessage(), e);
	// return RestUtils.error("getTodayRecommends error: " + e.getMessage());
	// }
	// }

	// /**
	// * 全部广告位
	// */
	// @RequestMapping(value = "wholeRecommends", method = RequestMethod.GET)
	// public RestResult wholeRecommends(@RequestParam("id") int id) {
	// try {
	// // TODO 临时使用 今日推荐
	// Position position = new Position();
	// position.setId(id);
	// return RestUtils.success(recommendService.getTodayRecommends(position));
	// } catch (Exception e) {
	// LOG.error("getTodayRecommends error:" + e.getMessage(), e);
	// return RestUtils.error("getTodayRecommends error: " + e.getMessage());
	// }
	// }

}
