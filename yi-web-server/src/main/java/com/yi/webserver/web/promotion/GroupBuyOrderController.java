/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.webserver.web.promotion;

import java.util.List;

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

import com.yi.core.promotion.domain.entity.GroupBuyOrder;
import com.yi.core.promotion.domain.listVo.GroupBuyOrderListVo;
import com.yi.core.promotion.domain.vo.GroupBuyOrderVo;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 团购单
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "团购单控制层")
@RestController
@RequestMapping(value = "/groupBuyOrder")
public class GroupBuyOrderController {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyOrderController.class);

	@Resource
	private IGroupBuyOrderService groupBuyOrderService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取团购单列表", notes = "根据分页参数查询团购单列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<GroupBuyOrderListVo> queryGroupBuyOrder(@RequestBody Pagination<GroupBuyOrder> query) {
		Page<GroupBuyOrderListVo> page = groupBuyOrderService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 查询开团单
	 */
	@ApiOperation(value = "查询开团单", notes = "根据货品id获取开团单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupBuyProductId", value = "货品id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getOpenGroups", method = RequestMethod.GET)
	public List<GroupBuyOrderListVo> getOpenGroups(@RequestParam("groupBuyProductId") int groupBuyProductId) {
		List<GroupBuyOrderListVo> page = groupBuyOrderService.getByGroupBuyProductId(groupBuyProductId);
		return page;
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "获取团购单信息", notes = "根据团购单id获取团购单信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "团购单id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getGroupBuyOrderVoById(@RequestParam("id") int groupBuyOrderId) {
		try {
			GroupBuyOrderVo groupBuyOrderVo = groupBuyOrderService.getVoById(groupBuyOrderId);
			return RestUtils.successWhenNotNull(groupBuyOrderVo);
		} catch (BusinessException ex) {
			LOG.error("get GroupBuyOrder failure : id={}", groupBuyOrderId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}
}