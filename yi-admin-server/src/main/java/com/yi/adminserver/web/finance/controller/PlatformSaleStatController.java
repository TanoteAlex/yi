/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.finance.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.finance.service.IPlatformSaleStatService;

import io.swagger.annotations.Api;

/**
 * 平台销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("平台销售统计控制层")
@RestController
@RequestMapping(value = "/platformSaleStat")
public class PlatformSaleStatController {

	private final Logger LOG = LoggerFactory.getLogger(PlatformSaleStatController.class);

	@Resource
	private IPlatformSaleStatService platformSaleStatService;

//	/**
//	 * 分页查询
//	 */
//	@ApiOperation(value = "获取平台销售统计列表", notes = "根据分页参数查询平台销售统计列表")
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public Page<PlatformSaleStatListVo> queryPlatformSaleStat(@RequestBody Pagination<PlatformSaleStat> query) {
//		Page<PlatformSaleStatListVo> page = platformSaleStatService.queryListVo(query);
//		return page;
//	}

}