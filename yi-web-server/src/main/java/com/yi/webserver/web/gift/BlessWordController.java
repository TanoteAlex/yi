/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.gift;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.gift.domain.entity.BlessWord;
import com.yi.core.gift.domain.vo.BlessWordListVo;
import com.yi.core.gift.service.IBlessWordService;
import com.yihz.common.persistence.Pagination;

/**
 * 祝福语
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Api(tags = "祝福语控制层")
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
	
}