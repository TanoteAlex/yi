package com.yi.searchServer.web.search.service.impl;


import com.google.gson.Gson;
import com.yi.searchServer.web.search.document.*;
import com.yi.searchServer.web.search.repository.*;
import com.yi.searchServer.web.search.service.CommodityService;
import com.yi.searchServer.web.search.vo.CombinationVO;
import com.yi.searchServer.web.search.vo.CommoditySpec;
import com.yi.searchServer.web.search.vo.SearchVo;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static org.elasticsearch.index.query.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author tanggangyi
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);

    @Resource
    private CommodityEsDao commodityEsDao;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void addCommodity(Commodity commodity) {
        commodityEsDao.save(commodity);
    }

    @Override
    public void deleteById(Integer id) {
        commodityEsDao.deleteById(id.toString());
    }


    public Page<Commodity> keywordSearch(String keyword, Pageable pageable) {
        Page<Commodity> commodities = queryPage(keywordSearchQuery(keyword), null, pageable);
        return commodities;
    }

    private QueryBuilder keywordSearchQuery(String keyword) {

        if (StringUtils.isBlank(keyword)) {
            return matchAllQuery();
        }
        return QueryBuilders.matchQuery("commodityName",keyword).minimumShouldMatch("75%");
        //        return QueryBuilders.multiMatchQuery(keyword, "commodityNo", "commodityName", "commodityShortName",
         //                   "description", "categories.categoryName"
//                去除商家相关关键字搜索
      //         ,"seller.sellerCode","seller.sellerName", "brand.brandNo"
    //   );
    }

    public Page<Commodity> categorySearch(SearchVo searchVo, Pageable pageable) throws Exception {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(searchVo.getKeyword())) {
            boolQueryBuilder.must(keywordSearchQuery(searchVo.getKeyword().trim()));
        }
        if(searchVo.getCategoryId()!=0) {
            boolQueryBuilder.must(termQuery("categories.categoryId", searchVo.getCategoryId()));
        }
        if (searchVo.getLowestPrice() > 0 || searchVo.getHighestPrice() > 0) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("currentPrice");
            if (searchVo.getLowestPrice() > 0) {
                rangeQueryBuilder.gte(searchVo.getLowestPrice());
            }
            if (searchVo.getHighestPrice() > 0) {
                rangeQueryBuilder.lte(searchVo.getHighestPrice());
            }
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        if(searchVo.getPriceSection()!=null) {
            if (searchVo.getPriceSection().getStartPrice() >= 0) {
                boolQueryBuilder.must(
                        QueryBuilders.rangeQuery("currentPrice")
                                .gte(searchVo.getPriceSection().getStartPrice()));
            }
            if (searchVo.getPriceSection().getEndPrice() >= 0) {
                boolQueryBuilder.must(
                        QueryBuilders.rangeQuery("currentPrice")
                                .lte(searchVo.getPriceSection().getEndPrice()));
            }
        }

        SortBuilder sortBuilder = null;
        if (searchVo.getDirection() != null && searchVo.getSortBy() != null) {
            sortBuilder = SortBuilders.fieldSort(searchVo.getSortBy().name())
                    .order(SortOrder.valueOf(searchVo.getDirection().name()));
        }

        return queryPage(boolQueryBuilder, sortBuilder, pageable);
    }

    private Page<Commodity> queryPage(QueryBuilder queryBuilder, SortBuilder sortBuilder, Pageable pageable) {

        SortBuilder defaultSort;
        Page<Commodity> page;
        defaultSort = SortBuilders.fieldSort("commodityId").order(SortOrder.DESC);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withIndices(Commodity.index_name)
                .withTypes(Commodity.index_type)
                .withPageable(pageable);
        if (sortBuilder != null) {
            // 指定的排序，第一排序
            nativeSearchQueryBuilder.withSort(sortBuilder);
        }
        SearchQuery searchQuery = nativeSearchQueryBuilder
                .withSort(defaultSort)  //默认排序，第二排序
                .build();

        page = elasticsearchTemplate.queryForPage(searchQuery, Commodity.class);
        return page;
    }
    public void updateCommodity(Commodity commodity){
        commodityEsDao.save(commodity);
    }

    public void bulkIndex(List<Commodity> queries) throws Exception {
        if(!elasticsearchTemplate.indexExists(Commodity.index_name)) {
            elasticsearchTemplate.createIndex(Commodity.index_name);
        }
//        List<IndexQuery> indexQueries=new ArrayList<>();
//        List<IndexQuery> specQueries = new ArrayList<>();
//        for (Commodity commodity :queries)
//        {
//            IndexQuery indexQuery = new IndexQuery();
//            indexQuery.setId(String.valueOf(commodity.getCommodityId()));
//            Gson gson = new Gson();
//            indexQuery.setSource(gson.toJson(commodity));
//            indexQuery.setIndexName(Commodity.index_name);
//            indexQuery.setType(Commodity.index_type);
//            indexQueries.add(indexQuery);
//            count++;
//
//            if (commodity.getCommoditySpecs() == null || commodity.getCommoditySpecs().size() == 0) {
//                continue;
//            }
//
//            for (CommoditySpec spec : commodity.getCommoditySpecs()) {
//                Spec one = new Spec();
//                String uuid = UUID.randomUUID().toString();
////                spec.getSpecName() + String.valueOf(commodity.getCommodityId()
//                one.setId(uuid);
//                one.setSpecName(spec.getSpecName());
//                one.setValue(spec.getValue());
//                one.setParentId(String.valueOf(commodity.getCommodityId()));
//                IndexQuery specQuery = new IndexQueryBuilder().withId(uuid)
//                        .withObject(one).withParentId(String.valueOf(commodity.getCommodityId())).build();
//                specQueries.add(specQuery);
////                indexQueries.add(specQuery);
////                elasticsearchTemplate.index(specQuery);
//            }
////            elasticsearchTemplate.bulkIndex(specQueries);
//        }
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.bulkIndex(specQueries);
//        elasticsearchTemplate.refresh(Commodity.index_name);

        if (!elasticsearchTemplate.typeExists(Commodity.index_name, Commodity.index_type)) {
            elasticsearchTemplate.putMapping(Commodity.class);
        }

        // commodity list is empty
        if (CollectionUtils.isEmpty(queries)) {
            return;
        }

        // spec && brand && category && priceSection
//        List<Category> categories = new ArrayList<>();
//        for (Commodity commodity : queries) {
//
//            // category
//            if (!CollectionUtils.isEmpty(commodity.getCategories())) {
//                for (com.yi.searchServer.web.search.vo.Category category : commodity.getCategories()) {
//                    Category one = new Category();
//                    BeanUtils.copyProperties(category, one);
//                    one.setId(Category.index_type + "-" + category.getCategoryId() + "-" + Commodity.index_type + "-" + commodity.getId());
//                    one.setParentId(String.valueOf(commodity.getId()));
//                    categories.add(one);
//                }
//            }
//
//        }

        // commodity
        commodityEsDao.saveAll(queries);

//        if (!CollectionUtils.isEmpty(categories)) {
//            if(!elasticsearchTemplate.indexExists(Category.index_type)) {
//                elasticsearchTemplate.createIndex(Category.index_type);
//            }
//            categoryEsDao.saveAll(categories);
//        }

    }

    /**
     * 根据商品ID查询商品
     * @param commodityId
     * @return
     */
    public Commodity selectByCommodityId(int commodityId){
        Optional<Commodity> commodity = commodityEsDao.findById(commodityId+"");
        Commodity commod = commodity.get();
        return commod;
    }

    /**
     * 查询所有ES中所有商品
     */
    public List<Commodity> findByAll(){
        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria());
        List<Commodity> list = elasticsearchTemplate.queryForList(criteriaQuery,Commodity.class);
        return list;
    }



}
