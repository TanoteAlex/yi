/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.basic.controller;

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

import com.yi.core.basic.domain.bo.CommunityBo;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.domain.vo.CommunityListVo;
import com.yi.core.basic.domain.vo.CommunityVo;
import com.yi.core.basic.service.ICommunityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 小区
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("小区控制层")
@RestController
@RequestMapping(value = "/community")
public class CommunityController {

	private final Logger LOG = LoggerFactory.getLogger(CommunityController.class);

	@Resource
	private ICommunityService communityService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取小区列表", notes = "根据分页参数查询小区列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommunityListVo> queryCommunity(@RequestBody Pagination<Community> query) {
		Page<CommunityListVo> page = communityService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看小区信息", notes = "根据小区Id获取小区信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "小区Id", required = true, dataType = "Int") })
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewCommunity(@RequestParam("id") int communityId) {
		try {
			CommunityVo community = communityService.getCommunityVoById(communityId);
			return RestUtils.successWhenNotNull(community);
		} catch (BusinessException ex) {
			LOG.error("get Community failure : id={}", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增小区", notes = "添加小区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "community", value = "小区对象", required = true, dataType = "CommunityBo") })
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addCommunity(@RequestBody CommunityBo community) {
		try {
			CommunityVo dbCommunity = communityService.addCommunity(community);
			return RestUtils.successWhenNotNull(dbCommunity);
		} catch (BusinessException ex) {
			LOG.error("add Community failure : community", community, ex);
			return RestUtils.error("add Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新小区", notes = "修改小区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "community", value = "小区对象", required = true, dataType = "CommunityBo") })
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateCommunity(@RequestBody CommunityBo community) {
		try {
			CommunityVo dbCommunity = communityService.updateCommunity(community);
			return RestUtils.successWhenNotNull(dbCommunity);
		} catch (BusinessException ex) {
			LOG.error("update Community failure : community", community, ex);
			return RestUtils.error("update Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除小区", notes = "删除小区")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "小区Id", required = true, dataType = "Int") })
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommunityById(@RequestParam("id") int communityId) {
		try {
			communityService.removeCommunityById(communityId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Community failure : id=communityId", ex);
			return RestUtils.error("remove Community failure : " + ex.getMessage());
		}
	}

	/**
	 * 查询提成总额
	 */
	@ApiOperation(value = "查询提成总额", notes = "查询提成总额")
	@RequestMapping(value = "commissionSum", method = RequestMethod.GET)
	public RestResult commissionSum() {
		try {
			return RestUtils.success(communityService.commissionSum());
		} catch (Exception ex) {
			LOG.error(" Community failure : id=communityId", ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 小区禁用启用
	 */
	@ApiOperation(value = "小区启用禁用", notes = "根据小区Id修改启用、禁用状态")
	@ApiImplicitParams({ @ApiImplicitParam(name = "communityId", value = "小区Id", required = true, dataType = "Int") })
	@RequestMapping(value = "updateState", method = RequestMethod.GET)
	public RestResult updateState(@RequestParam("communityId") int communityId) {
		try {
			CommunityListVo community = communityService.updateState(communityId);
			return RestUtils.success(community);
		} catch (Exception ex) {
			LOG.error("update Community failure : id={}", communityId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}