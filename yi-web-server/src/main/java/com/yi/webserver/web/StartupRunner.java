package com.yi.webserver.web;

import com.yi.core.search.restful.RestfulFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {

    protected static Logger logger =LoggerFactory.getLogger(StartupRunner.class);

    @Value("${yi.searchBaseUrl}")
    private String searchBaseUrl;

    @Override
    public void run(String... strings) throws Exception {
        RestfulFactory.getInstance().init(searchBaseUrl);
    }

}