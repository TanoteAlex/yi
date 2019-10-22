package com.yi.searchServer.web.search.vo;

import io.swagger.annotations.ApiModel;

@ApiModel(value="搜索返回对象",description="搜索返回对象 SearchResultVo")
public class SearchResultVo {

    private PageFinder<CommodityVO> pageFinder;

    public PageFinder<CommodityVO> getPageFinder() {
        return pageFinder;
    }

    public void setPageFinder(PageFinder<CommodityVO> pageFinder) {
        this.pageFinder = pageFinder;
    }

}
