package com.yi.webserver.web.share;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.share.ShareService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品分享
 * 
 * @ClassName SharingController
 * @Author jstin
 * @Date 2018/12/27 13:51
 * @Version 1.0
 **/
@Api(tags = "商品分享控制层")
@RestController
@RequestMapping("/share")
public class ShareController {

	private final Logger LOG = LoggerFactory.getLogger(ShareController.class);

	@Resource
	private ShareService shareService;

	/**
	 * 合成二维码海报图片
	 * 
	 * @param url
	 * @param commodityId
	 * @return
	 */
	@ApiOperation(value = "合成二维码海报图片", notes = "合成二维码海报图片")
	@ApiImplicitParams({ @ApiImplicitParam(name = "url", value = "路径", required = true, dataType = "String"),
			@ApiImplicitParam(name = "commodityId", value = "商品ID", required = true, dataType = "Int"), })
	@RequestMapping(value = "getShareImage", method = RequestMethod.GET)
	public RestResult getShareImage(String url, int commodityId) {
		try {
			return RestUtils.successWhenNotNull(shareService.getShareImage(url, commodityId));
		} catch (Exception e) {
			LOG.error("合成分享图片二维码失败==>{}", e.getMessage(), e);
			return RestUtils.error("合成分享图片二维码失败");
		}
	}
}
