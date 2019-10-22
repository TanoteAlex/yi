package com.yi.webserver.web.commodity;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.service.ICommunityService;
import com.yi.core.basic.service.IRegionService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 地区管理
 */
//检查controller
@Api(tags = "地区控制层")
@RestController
@RequestMapping("/region")
public class RegionController {

	private final Logger LOG = LoggerFactory.getLogger(CommodityController.class);

	@Resource
	private IRegionService regionService;

	@Resource
	private ICommunityService communityService;

	/**
	 * 根据省查市 return_order
	 *
	 * @return
	 */
	@ApiOperation(value = "根据省查市", notes = "根据省查市")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "province", value = "省", required = false, dataType = "String")
	})
	@RequestMapping(value = "getProvinces", method = RequestMethod.GET)
	public RestResult getCity(@RequestParam(value = "province", required = false) String province) {
		try {
			return RestUtils.success(regionService.checkCity(province));
		} catch (Exception e) {
			LOG.error("getCity error: ", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 获取小区的省市
	 * 
	 * @return
	 */
	@ApiOperation(value = "获取小区的省市", notes = "获取小区的省市")
	@RequestMapping(value = "getCommunityProvinceCity", method = RequestMethod.GET)
	public RestResult getCommunityProvinceCity() {
		try {
			return RestUtils.success(communityService.getCommunityProvinceCity());
		} catch (Exception e) {
			LOG.error("getProvince error: ", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
