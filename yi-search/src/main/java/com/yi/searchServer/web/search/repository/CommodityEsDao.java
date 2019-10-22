package com.yi.searchServer.web.search.repository;

import com.yi.searchServer.web.search.document.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CommodityEsDao extends ElasticsearchRepository<Commodity, String> {


    Page<Commodity> findByCommodityNameLike(String content, Pageable pageable);

}
