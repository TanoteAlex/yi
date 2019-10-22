/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.member.controller;

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

import com.yi.core.member.domain.entity.SignTime;
import com.yi.core.member.domain.vo.SignTimeListVo;
import com.yi.core.member.domain.vo.SignTimeVo;
import com.yi.core.member.service.ISignTimeService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 签到时间
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api("签到时间控制层")
@RestController
@RequestMapping(value = "/signTime")
public class SignTimeController {

	private final Logger LOG = LoggerFactory.getLogger(SignTimeController.class);

	@Resource
	private ISignTimeService signTimeService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取签到时间列表", notes = "根据分页参数查询签到时间列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<SignTimeListVo> querySignTime(@RequestBody Pagination<SignTime> query) {
		Page<SignTimeListVo> page = signTimeService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@ApiOperation(value = "查看签到时间信息", notes = "根据签到时间Id获取签到时间信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "签到时间Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewSignTime(@RequestParam("id") int signTimeId) {
		try {
			SignTimeVo signTime = signTimeService.getSignTimeVoById(signTimeId);
			return RestUtils.successWhenNotNull(signTime);
		} catch (BusinessException ex) {
			LOG.error("get SignTime failure : id=signTimeId", ex);
			return RestUtils.error("get SignTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增签到时间", notes = "添加签到时间")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "signTime", value = "签到时间对象", required = true, dataType = "SignTime")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addSignTime(@RequestBody SignTime signTime) {
		try {
			SignTimeVo dbSignTime = signTimeService.addSignTime(signTime);
			return RestUtils.successWhenNotNull(dbSignTime);
		} catch (BusinessException ex) {
			LOG.error("add SignTime failure : signTime", signTime, ex);
			return RestUtils.error("add SignTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新签到时间", notes = "修改签到时间")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "signTime", value = "签到时间对象", required = true, dataType = "SignTime")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateSignTime(@RequestBody SignTime signTime) {
		try {
			SignTimeVo dbSignTime = signTimeService.updateSignTime(signTime);
			return RestUtils.successWhenNotNull(dbSignTime);
		} catch (BusinessException ex) {
			LOG.error("update SignTime failure : signTime", signTime, ex);
			return RestUtils.error("update SignTime failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除签到时间", notes = "删除签到时间")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "签到时间Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeSignTimeById(@RequestParam("id") int signTimeId) {
		try {
			signTimeService.removeSignTimeById(signTimeId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove SignTime failure : id=signTimeId", ex);
			return RestUtils.error("remove SignTime failure : " + ex.getMessage());
		}
	}
}