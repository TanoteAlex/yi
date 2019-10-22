package com.yi.adminserver.web.config.common;
// package com.yi.admin.web.config.common;
//
// import org.apache.solr.client.solrj.SolrClient;
// import org.apache.solr.client.solrj.impl.HttpSolrClient;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.solr.core.SolrTemplate;
// import
// org.springframework.data.solr.repository.config.EnableSolrRepositories;
//
// @Configuration
// @EnableSolrRepositories(basePackages = { "com.yi" })
// public class SolrConfig {
//
//// @Value("${spring.data.solr.host}")
// private String url;
//
// @Bean
// public SolrClient solrClient() {
// return new HttpSolrClient(url);
// }
//
// @Bean
// public SolrTemplate solrTemplate(SolrClient client){
// return new SolrTemplate(client);
// }
//
// }
