/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.adminserver.web.gift.controller;

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

import com.yi.core.gift.domain.bo.BlessWordBo;
import com.yi.core.gift.domain.entity.BlessWord;
import com.yi.core.gift.domain.vo.BlessWordListVo;
import com.yi.core.gift.domain.vo.BlessWordVo;
import com.yi.core.gift.service.IBlessWordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 祝福语
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api("祝福语控制层")
@RestController
@RequestMapping(value = "/blessWord")
public class BlessWordController {

	private final Logger LOG = LoggerFactory.getLogger(BlessWordController.class);

	@Resource
	private IBlessWordService blessWordService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取祝福语列表", notes = "根据分页参数查询祝福语列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<BlessWordListVo> queryBlessWord(@RequestBody Pagination<BlessWord> query) {
		Page<BlessWordListVo> page = blessWordService.queryListVo(query);
		return page;
	}

	/**
	 * 保存新增对象
	 **/
	@ApiOperation(value = "新增祝福语", notes = "添加祝福语")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blessWordBo", value = "祝福语对象", required = true, dataType = "BlessWordBo")
	})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addBlessWord(@RequestBody BlessWordBo blessWordBo) {
		try {
			BlessWordVo blessWordVo = blessWordService.addBlessWord(blessWordBo);
			return RestUtils.successWhenNotNull(blessWordVo);
		} catch (BusinessException ex) {
			LOG.error("add BlessWord failure : blessWordBo", blessWordBo, ex);
			return RestUtils.error("add BlessWord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@ApiOperation(value = "更新祝福语", notes = "修改祝福语")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blessWordBo", value = "祝福语对象", required = true, dataType = "BlessWordBo")
	})
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateBlessWord(@RequestBody BlessWordBo blessWordBo) {
		try {
			BlessWordVo blessWordVo = blessWordService.updateBlessWord(blessWordBo);
			return RestUtils.successWhenNotNull(blessWordVo);
		} catch (BusinessException ex) {
			LOG.error("update BlessWord failure : blessWordBo", blessWordBo, ex);
			return RestUtils.error("update BlessWord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@ApiOperation(value = "删除祝福语", notes = "删除祝福语")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "祝福语Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeBlessWordById(@RequestParam("id") int blessWordId) {
		try {
			blessWordService.removeBlessWordById(blessWordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove BlessWord failure : id=blessWordId", ex);
			return RestUtils.error("remove BlessWord failure : " + ex.getMessage());
		}
	}

	/**
	 * 获取编辑对象
	 **/
	@ApiOperation(value = "编辑祝福语获取祝福语信息", notes = "编辑时根据祝福语Id获取祝福语信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "祝福语Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getBoById", method = RequestMethod.GET)
	public RestResult getBlessWordBoById(@RequestParam("id") int blessWordId) {
		try {
			BlessWordBo blessWordBo = blessWordService.getBlessWordBoById(blessWordId);
			return RestUtils.successWhenNotNull(blessWordBo);
		} catch (BusinessException ex) {
			LOG.error("get BlessWord failure : id=blessWordId", ex);
			return RestUtils.error("get BlessWord failure : " + ex.getMessage());
		}
	}

	/**
	 * 查看对象详情
	 **/
	@ApiOperation(value = "查看祝福语信息", notes = "根据祝福语Id获取祝福语信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "祝福语Id", required = true, dataType = "Int")
	})
	@RequestMapping(value = "getVoById", method = RequestMethod.GET)
	public RestResult getBlessWordVoById(@RequestParam("id") int blessWordId) {
		try {
			BlessWordVo blessWordVo = blessWordService.getBlessWordVoById(blessWordId);
			return RestUtils.successWhenNotNull(blessWordVo);
		} catch (BusinessException ex) {
			LOG.error("get BlessWord failure : id=blessWordId", ex);
			return RestUtils.error("get BlessWord failure : " + ex.getMessage());
		}
	}
}