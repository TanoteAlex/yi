package com.yi.core.search.service;


import com.yi.core.search.vo.CommodityVO;
import com.yi.core.search.vo.ResponseResult;
import com.yi.core.search.vo.SearchVo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * @Author: tanggangyi
 **/
public interface SearchRestService {

    /**
     * 关键字搜索
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST(value = "mall/search/keywordSearch")
    Call<ResponseResult> keywordSearch(@Field("keyword") String keyword, @Field("page") int page, @Field("size") int size);
    /**
     * 分类/条件搜索
     *
     * @param
     * @param
     * @return
     * @throws Exception
     */
    @POST(value = "mall/search/categorySearch")
    Call <ResponseResult> categorySearch(@Body SearchVo searchVo);

    /**
     * 查询所有规格属性等
     * @return
     */
    @GET(value = "mall/search/commodity")
    Call<ResponseResult> selectAll();

    /**
     * 添加商品
     * @param commodityVo
     * @return
     */
    @POST("mall/search/commodity")
    Call<ResponseResult> addCommodity(@Body CommodityVO commodityVo);
    /**
     * 修改商品
     * @param commodityVo
     * @return
     */
    @PUT("mall/search/commodity")
    Call<ResponseResult> updateCommodity(@Body CommodityVO commodityVo);
    /**
     * 删除商品
     * @param commodityId
     * @return
     */
    @DELETE("mall/search/commodity/{commodityId}")
    Call<ResponseResult> removeCommodity(@Path("commodityId") int commodityId);


    @POST("mall/search/bulkIndex")
    Call<ResponseResult> bulkIndex(@Body List<CommodityVO> voList);

    /**
     * 查询商品
     * @param commodityId
     * @return
     */
    @GET("mall/search/commodity/{commodityId}")
    Call<ResponseResult<CommodityVO>> selectByCommodityId(@Path("commodityId") int commodityId);
}