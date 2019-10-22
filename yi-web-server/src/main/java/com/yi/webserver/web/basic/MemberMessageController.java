/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.basic;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.entity.MemberMessage;
import com.yi.core.basic.domain.vo.MemberMessageListVo;
import com.yi.core.basic.service.IMemberMessageService;
import com.yihz.common.persistence.Pagination;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员消息
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "会员消息控制层")
@RestController
@RequestMapping(value = "/memberMessage")
public class MemberMessageController {

	private final Logger LOG = LoggerFactory.getLogger(MemberMessageController.class);

	@Resource
	private IMemberMessageService memberMessageService;

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "获取客户消息列表", notes = "根据分页参数查询会员消息列表")
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<MemberMessageListVo> queryMemberMessage(@RequestBody Pagination<MemberMessage> query) {
		Page<MemberMessageListVo> page = memberMessageService.queryListVoForApp(query);
		return page;
	}

}