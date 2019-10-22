/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.cms.controller;

import javax.annotation.Resource;

import com.yi.core.cms.domain.vo.BannerVo;
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

import com.yi.core.cms.domain.entity.Banner;
import com.yi.core.cms.domain.vo.BannerListVo;
import com.yi.core.cms.domain.vo.BannerVo;
import com.yi.core.cms.service.IBannerService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 轮播图
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("banner控制层")
@RestController
@RequestMapping(value = "/banner")
public class BannerController {

	private final Logger LOG = LoggerFactory.getLogger(BannerController.class);

	@Resource
	private IBannerService bannerService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取banner列表", notes = "根据分页参数查询banner列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<BannerListVo> queryBanner(@RequestBody Pagination<Banner> query) {
		Page<BannerListVo> page = bannerService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看banner信息", notes = "根据bannerId获取banner信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "bannerId", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewBanner(@RequestParam("id") int bannerId) {
		try {
			BannerVo banner = bannerService.getBannerListVoById(bannerId);
			return RestUtils.successWhenNotNull(banner);
		} catch (BusinessException ex) {
			LOG.error("get Banner failure : id=bannerId", ex);
			return RestUtils.error("get Banner failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增banner", notes = "添加banner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "banner", value = "banner对象", required = true, dataType = "Banner")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addBanner(@RequestBody Banner banner) {
		try {
			BannerVo dbBanner = bannerService.addBanner(banner);
			return RestUtils.successWhenNotNull(dbBanner);
		} catch (BusinessException ex) {
			LOG.error("add Banner failure : banner", banner, ex);
			return RestUtils.error("add Banner failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新banner", notes = "修改banner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "banner", value = "banner对象", required = true, dataType = "Banner")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateBanner(@RequestBody Banner banner) {
		try {
			BannerVo dbBanner = bannerService.updateBanner(banner);
			return RestUtils.successWhenNotNull(dbBanner);
		} catch (BusinessException ex) {
			LOG.error("update Banner failure : banner", banner, ex);
			return RestUtils.error("update Banner failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除banner", notes = "删除banner")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "bannerId", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeBannerById(@RequestParam("id") int bannerId) {
		try {
			bannerService.removeBannerById(bannerId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Banner failure : id=bannerId", ex);
			return RestUtils.error("remove Banner failure : " + ex.getMessage());
		}
	}
}