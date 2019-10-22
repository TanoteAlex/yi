/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.promotion;

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

import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityProductVo;
import com.yi.core.promotion.service.IGroupBuyActivityProductService;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购活动
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "团购活动控制层")
@RestController
@RequestMapping(value = "/groupBuyActivity")
public class GroupBuyActivityController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityController.class);

	@Resource
	private IGroupBuyActivityService groupBuyActivityService;

	@Resource
	private IGroupBuyActivityProductService groupBuyActivityProductService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购活动列表", notes = "根据分页参数查询团购活动列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyActivityListVo> queryGroupBuyActivity(@RequestBody Pagination<GroupBuyActivity> query) {
		Page<GroupBuyActivityListVo> page = groupBuyActivityService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "获取团购活动货品信息", notes = "根据货品id获取团购活动货品信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "货品id", required = true, dataType = "Int") })
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyActivityVoById(@RequestParam("id") int groupBuyActivityProductId) {
		try {
			GroupBuyActivityProductVo groupBuyActivityProductVo = groupBuyActivityProductService.getVoByIdForApp(groupBuyActivityProductId);
			return RestUtils.successWhenNotNull(groupBuyActivityProductVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyActivityProduct failure : id={}", groupBuyActivityProductId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 查询最新的团购活动
	 **/
	@ApiOperation(value = "查询最新的团购活动", notes = "查询最新的团购活动")
	@RequestMapping(value = "getLatestGroupBuyActivity", method = RequestMethod.GET)
	public RestResult getLatestGroupBuyActivity() {
		try {
			GroupBuyActivityListVo groupBuyActivity = groupBuyActivityService.getLatestGroupBuyActivity();
			return RestUtils.successWhenNotNull(groupBuyActivity);
		} catch (BusinessException ex) {
			LOG.error("get Latest GroupBuyActivity failure :{}", ex.getMessage(), ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}