package com.yi.searchServer.web;//package com.yi.searchServer.web;
//
//import com.yi.searchServer.web.common.Page;
//import com.yi.searchServer.web.domain.CommodityInfo;
//import com.yi.searchServer.web.service.YiSearchService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TestYiSearch {
//
//    @Autowired
//    private YiSearchService yiSearchService;
//
//
//    @Test
//    public void  init() throws Exception {
//        boolean b = yiSearchService.initIndex();
//        System.out.println(b);
//    }
////    @Test
////    public void  create() throws Exception {
////        boolean b = yiSearchService.createIndex();
////        System.out.println(b);
////    }
////
////    @Test
////    public void  createMapping() throws Exception {
////        boolean b = yiSearchService.createIndexMapping();
////        System.out.println(b);
////    }
////
////    @Test
////    public void  updateIndexSettings() throws Exception {
////        boolean b = yiSearchService.updateIndexSettings();
////        System.out.println(b);
////    }
//
//    @Test
//    public void  save() throws Exception {
//        String obj="{\n" +
//                "\t\"id\": \"100\",\n" +
//                "\t\"commodityName\": \"长袖连衣裙女装\",\n" +
//                "\t\"commodityShortName\": \"保湿补水 滋润肌肤 \",\n" +
//                "\t\"imgPath\": \"http://adminserver.my11mall.com/attachment/show/707735e9-0fac-4473-b735-e90fac24739a\",\n" +
//                "\t\"originalPrice\": 198.00,\n" +
//                "\t\"currentPrice\": 188.00,\n" +
//                "\t\"supplyPrice\": 0.00,\n" +
//                "\t\"vipPrice\": 0.00,\n" +
//                "\t\"freight\": 0.00,\n" +
//                "\t\"description\": \"\",\n" +
//                "\t\"returnVoucher\": 0.00\n" +
//                "}";
//
//        boolean b = yiSearchService.saveIndex(obj);
//        System.out.println(b);
//    }
//
//    @Test
//    public void  batchSave() throws Exception {
//        String obj="[\n" +
//                "{\n" +
//                " \"id\":\"101\",\n" +
//                " \"commodityName\":\"壹壹优选测试1\",\n" +
//                " \"commodityShortName\": \"测试 \",\n" +
//                " \"imgPath\":\"http://adminserver.my11mall.com/attachment/show/707735e9-0fac-4473-b735-e90fac24739a\", \n" +
//                " \"originalPrice\":198.00,\n" +
//                " \"currentPrice\":188.00,\n" +
//                " \"supplyPrice\":0.00,\n" +
//                " \"vipPrice\":0.00,\n" +
//                " \"freight\":0.00,\n" +
//                " \"description\":\"\",\n" +
//                " \"returnVoucher\":0.00\n" +
//                " }\n" +
//                "]";
//        boolean b = yiSearchService.saveBatchIndex(obj);
//        System.out.println(b);
//    }
//
//
//    @Test
//    public void  searchIndexList() throws Exception {
//        Page page=new Page(1,10);
//        Page<CommodityInfo> page1 = yiSearchService.searchIndexList(page, "衣裙");
//        System.out.println(page.getTotal());
//        System.out.println(page.getList().toString());
//    }
//}
