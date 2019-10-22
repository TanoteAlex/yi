package com.yi.searchServer.web.search.rest;


import com.yi.searchServer.web.search.document.Commodity;
import com.yi.searchServer.web.search.service.CommodityService;
import com.yi.searchServer.web.search.vo.*;
import com.yi.searchServer.web.search.vo.meta.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Api(description = "商城商品搜索接口")
@RestController
@RequestMapping(value = "/mall/search")
public class SearchResource extends BaseResource {

    private static final Logger logger = LoggerFactory.getLogger(SearchResource.class);

    @Resource
    private CommodityService commodityService;


    @ApiOperation(value = "关键字搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "返回行数", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping(value = "keywordSearch")
    public ResponseResult keywordSearch(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Commodity> result = commodityService.keywordSearch(keyword, pageable);
            return new ResponseResult(result);
        } catch (Exception e) {
            logger.info("keywordSearch excption", e);
            return new ResponseResult(Result.FAILURE);
        }
    }



    @ApiOperation(value = "分类搜索")
    @ApiImplicitParams({
    })
    @PostMapping(value = "categorySearch")
    public ResponseResult categorySearch(@RequestBody SearchVo searchVo) {
        try {
            Page<Commodity> result = commodityService.categorySearch(searchVo, PageRequest.of(searchVo.getPage(), searchVo.getPageSize()));
            return new ResponseResult(result);
        } catch (Exception e) {
            logger.info("categorySearch excption", e);
            return new ResponseResult(Result.FAILURE);
        }
    }

    @ApiOperation(value = "添加对象到ES")
    @ApiImplicitParams({
    })
    @PostMapping(value = "commodity")
    public ResponseResult addCommodity(@RequestBody CommodityVO commodityVO) {
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityVO, commodity);
        commodity.setId(String.valueOf(commodityVO.getCommodityId()));
        commodityService.addCommodity(commodity);
        return new ResponseResult(Result.SUCCESS);
    }

    @ApiOperation(value = "删除ES对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityId", value = "商品ID", required = true, paramType = "path", dataType = "int"),
    })
    @DeleteMapping(value = "commodity/{commodityId}")
    public ResponseResult removeCommodity(@PathVariable int commodityId) {
        commodityService.deleteById(commodityId);
        return new ResponseResult(Result.SUCCESS);
    }

    @ApiOperation(value = "批量添加对象到ES")
    @ApiImplicitParams({
    })
    @PostMapping(value = "bulkIndex")
    public ResponseResult bulkIndex(@RequestBody List<CommodityVO> voList) {
        try {
            List<Commodity> list = new ArrayList<>();
            for (CommodityVO commodityVO : voList) {
                Commodity commodity = new Commodity();
                BeanUtils.copyProperties(commodityVO, commodity);
                commodity.setId(String.valueOf(commodityVO.getCommodityId()));
                list.add(commodity);
            }
            commodityService.bulkIndex(list);
        } catch (Exception e) {
            logger.info("bulkIndex failled", e);
        }
        return new ResponseResult(Result.SUCCESS);
    }

    @ApiOperation(value = "更新ES对象")
    @PutMapping(value = "commodity")
    public ResponseResult updateCommodity(@RequestBody CommodityVO commodityVO){
        Commodity commodity = new Commodity();
        BeanUtils.copyProperties(commodityVO, commodity);
        commodity.setId(String.valueOf(commodityVO.getCommodityId()));
        commodityService.updateCommodity(commodity);
        return new ResponseResult(Result.SUCCESS);
    }

    @ApiOperation(value = "查询ES对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityId", value = "商品ID", required = true, paramType = "path", dataType = "int"),
    })
    @GetMapping(value = "commodity/{commodityId}")
    public ResponseResult<CommodityVO> selectByCommodityId(@PathVariable int commodityId) {
        Commodity commodity = commodityService.selectByCommodityId(commodityId);
        CommodityVO commodityVO = new CommodityVO();
        BeanUtils.copyProperties(commodity, commodityVO);
        return new ResponseResult(commodityVO);
    }

}
