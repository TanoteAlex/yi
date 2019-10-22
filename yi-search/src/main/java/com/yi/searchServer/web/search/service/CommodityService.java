package com.yi.searchServer.web.search.service;

import com.yi.searchServer.web.search.document.Commodity;
import com.yi.searchServer.web.search.vo.CombinationVO;
import com.yi.searchServer.web.search.vo.SearchVo;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommodityService {

    /**
     * 添加商品
     * @param commodity
     */
    void addCommodity(Commodity commodity);

    /**
     * 根据商品id删除商品
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 基于keyword进行搜索，返回分页
     */
    Page<Commodity> keywordSearch(String keyword, Pageable pageable) ;

    /**
     * 分类/条件搜索
     *
     * @param searchVo
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<Commodity> categorySearch(SearchVo searchVo, Pageable pageable) throws Exception;

    /**
     * 更新ES搜索器中的商品
     * @param commodity
     */
    void updateCommodity(Commodity commodity);



    void bulkIndex(List<Commodity> queries) throws Exception;
    /**
     * 根据商品ID查询商品
     * @param commodityId
     * @return
     */
    Commodity selectByCommodityId(int commodityId);

    /**
     * 查询所有ES中所有商品
     */
    List<Commodity> findByAll();

}
