package com.yi.webserver.web.payment;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.payment.qq.QqService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * QQ相关服务
 *
 * @author xuyh
 */
@Api(tags = "QQ相关服务控制层")
@RestController
@RequestMapping("/qq")
public class QqController {

	private static final Logger LOG = LoggerFactory.getLogger(QqController.class);

	@Resource
	private QqService qqService;

	/**
	 * QQ 授权登录
	 *
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "QQ 授权登录", notes = "QQ 授权登录")
	@ApiImplicitParams({ @ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, dataType = "String") })
	@RequestMapping(value = "/authLoginForQq", method = RequestMethod.GET)
	public RestResult authLoginForQq(@RequestParam("accessToken") String accessToken, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("--- 根据accessToken自动登录 ---");
		try {
			return RestUtils.success(qqService.authLoginForQq(accessToken));
		} catch (Exception e) {
			LOG.error("auth Login For Qq error: {}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

}
