package com.yi.adminserver.web.stats;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.adminserver.web.auth.jwt.JwtSupplierToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.core.stats.domain.vo.PlatformDataVo;
import com.yi.core.stats.service.IHomepageService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 首页
 *
 * @author xuyh
 */
@Api("首页控制层")
@RestController
@RequestMapping(value = "/homepage")
public class HomepageController {

    private static final Logger LOG = LoggerFactory.getLogger(HomepageController.class);

    @Resource
    private IHomepageService homepageService;

    /**
     * 查询 平台数据
     **/
    @ApiOperation(value = "查询 平台数据(总后台)", notes = "查询 平台数据(总后台)")
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public RestResult queryPlatformData() {
        try {
            PlatformDataVo platformDataVo = homepageService.getPlatformData();
            return RestUtils.successWhenNotNull(platformDataVo);
        } catch (BusinessException ex) {
            LOG.error("query platformData failure : ", ex);
            return RestUtils.error(ex.getMessage());
        }
    }

    /**
     * 查询 平台数据
     **/
    @ApiOperation(value = "查询 平台数据(供应商)", notes = "查询 平台数据(供应商)")
    @RequestMapping(value = "queryForSupplier", method = RequestMethod.GET)
    public RestResult queryForSupplier(HttpServletRequest request) {
        try {
            SupplierToken supplierToken = JwtSupplierToken.getSupplierToken(request);
            PlatformDataVo platformDataVo = homepageService.getSupplierData(supplierToken.getId());
            return RestUtils.successWhenNotNull(platformDataVo);
        } catch (BusinessException ex) {
            LOG.error("query SupplierData failure : {}", ex, ex);
            return RestUtils.error(ex.getMessage());
        }
    }

}
