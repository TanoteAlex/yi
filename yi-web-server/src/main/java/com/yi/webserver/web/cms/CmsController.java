package com.yi.webserver.web.cms;

import javax.annotation.Resource;

import com.yi.core.cms.domain.vo.RecommendVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.service.IAdvertisementService;
import com.yi.core.cms.service.IRecommendService;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 内容管理相关
 * 
 * @author xuyh
 *
 */
// 检查controller
@Api(tags = "内容管理相关控制层")
@RestController
@RequestMapping(value = "/cms")
public class CmsController {

	private final Logger LOG = LoggerFactory.getLogger(CmsController.class);

	@Resource
	private IAdvertisementService advertisementService;

	@Resource
	private IRecommendService recommendService;

	//@Resource
	//private IIntegralProductService integralProductService;
	/**
	 * 获取轮播图
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取轮播图", notes = "获取轮播图")
	@RequestMapping(value = "getBanner", method = RequestMethod.GET)
	public RestResult getBanner() {
		try {
			// 轮播图
			return RestUtils.success(advertisementService.getAdvertisementListVoForApp(CmsEnum.POSITION_TYPE_BANNER.getCode()));
		} catch (Exception e) {
			LOG.error("getBanner error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 根据位置获取推荐数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "根据位置获取推荐数据", notes = "根据位置获取推荐数据")
	@ApiImplicitParams({ @ApiImplicitParam(name = "positionType", value = "位置类型", required = true, dataType = "Int"),
			@ApiImplicitParam(name = "city", value = "位置", required = false, dataType = "String"), })
	@RequestMapping(value = "getRecommends", method = RequestMethod.GET)
	public RestResult getRecommends(@RequestParam("positionType") Integer positionType, @RequestParam(name = "city", required = false) String city) {
		try {
			return RestUtils.success(recommendService.queryRecommends(positionType, city));
		} catch (Exception e) {
			LOG.error("getRecommends error{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 分页查询推荐数据
	 * 
	 * @return
	 */
	@ApiOperation(value = "分页查询推荐数据", notes = "分页查询推荐数据")
	@RequestMapping(value = "queryRecommends", method = RequestMethod.POST)
	public RestResult queryRecommends(@RequestBody Pagination<Recommend> query) {
		try {
			Page<RecommendVo> recommendVos = recommendService.queryVosForApp(query);
			return RestUtils.success(recommendVos);
		} catch (Exception e) {
			LOG.error("query Recommends error{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}
	/**
	@ApiOperation(value = "分页查询积分商品", notes = "分页查询积分商品")
	@RequestMapping(value = "queryIntegralProduct", method = RequestMethod.POST)
	public Page<IntegralProduct> queryIntegralProduct(@RequestBody Pagination<IntegralProduct> query) {
		Page<IntegralProduct> page = integralProductService.queryAllList(query);
		return page;
	}
*/


}
