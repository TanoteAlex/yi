package com.yi.webserver.web.search;

import com.yi.core.search.restful.RestfulFactory;
import com.yi.core.search.service.SearchRestService;
import com.yi.core.search.vo.ResponseResult;
import com.yi.core.search.vo.SearchVo;
import com.yihz.common.json.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;

import javax.validation.Valid;

@Api(tags = "商城商品搜索接口")
@RestController
@RequestMapping(value = "/search")
public class SearchController  {

    private final Logger LOG = LoggerFactory.getLogger(SearchController.class);

    @ApiOperation(value = "关键字搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "返回行数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "keywordSearch")
    public ResponseResult keywordSearch(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        if(StringUtils.isBlank(keyword)){
            return new ResponseResult(com.yi.core.search.vo.meta.Result.FAILURE,"请输入关键字进行搜索");
        }
        try {
            SearchRestService restService = RestfulFactory.getInstance().getSearchRestService();
            Call<ResponseResult> call = restService.keywordSearch(keyword, page, size);

            ResponseResult result = call.execute().body();
            return result;
        } catch (Exception e) {
            LOG.error("keywordSearch failed", e);
            return new ResponseResult(com.yi.core.search.vo.meta.Result.FAILURE);
        }
    }

    @ApiOperation(value = "分类搜索")
    @ApiImplicitParams({
    })
    @PostMapping(value = "categorySearch")
    public ResponseResult categorySearch(@RequestBody @Valid SearchVo searchVo) {
        try {
            SearchRestService restService = RestfulFactory.getInstance().getSearchRestService();
            Call<ResponseResult> call = restService.categorySearch(searchVo);
            ResponseResult result = call.execute().body();
            return result;
        } catch (Exception e) {
            LOG.error("categorySearch failed", e);
            return new ResponseResult(com.yi.core.search.vo.meta.Result.FAILURE);
        }
    }

    @ApiOperation(value = "查询所有规格属性等")
    @GetMapping(value = "commodity")
    public ResponseResult selectAll() {
        try {
            SearchRestService restService = RestfulFactory.getInstance().getSearchRestService();
            Call<ResponseResult> call = restService.selectAll();
            ResponseResult result = call.execute().body();
            return result;
        } catch (Exception e) {
            LOG.error("commodity selectAll failed", e);
            return new ResponseResult(com.yi.core.search.vo.meta.Result.FAILURE);
        }
    }

}
