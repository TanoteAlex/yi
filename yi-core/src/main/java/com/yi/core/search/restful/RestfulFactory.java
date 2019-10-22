package com.yi.core.search.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yi.core.search.service.SearchRestService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @Author: tanggangyi
 **/
public class RestfulFactory {


    private String BASE_URL;

    private static String API_VERSION = "v1";

    private static RestfulFactory restfulFactory;
    private SearchRestService searchRestService;


    private RestfulFactory() {

    }

    public void init(String baseUrl) {
        this.BASE_URL = baseUrl;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        //配置日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                // 针对rxjava2.x
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        searchRestService = retrofit.create(SearchRestService.class);
    }

    public synchronized static RestfulFactory getInstance() {
        if (restfulFactory == null) {
            restfulFactory = new RestfulFactory();
        }
        return restfulFactory;
    }


    public SearchRestService getSearchRestService() {
        return searchRestService;
    }
}
