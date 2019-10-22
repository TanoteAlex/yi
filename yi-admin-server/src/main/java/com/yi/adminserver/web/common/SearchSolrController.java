package com.yi.adminserver.web.common;
//package com.yi.admin.web.common;
//
//import com.yi.core.attachment.domain.vo.AttachmentVo;
//import com.yi.core.search.service.ISearchSolrService;
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.common.SolrDocument;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.*;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@RestController
//@RequestMapping("/search")
//public class SearchSolrController {
//
//    private final Logger LOG = LoggerFactory.getLogger(SearchSolrController.class);
//
//    @Autowired
//    private ISearchSolrService searchSolrService;
//
//
//    @RequestMapping("/addCommodity")
//    public String addCommodity() throws Exception {
//        searchSolrService.save(1,"asdf","qwfqwefqwef");
//        LOG.info("addCommodity={}");
//        return null;
//    }
//
//
//    @RequestMapping("/commodities")
//    public Page commodities() throws Exception {
//        Page page = searchSolrService.query("s", new PageRequest(0, 10,new Sort(Sort.Direction.DESC, "id")));
//        LOG.info("commodities={}",page);
//        return page;
//    }
//}
