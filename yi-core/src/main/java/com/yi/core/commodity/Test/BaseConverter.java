package com.yi.core.commodity.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseConverter<DO, VO> {

    private final Logger logger = LoggerFactory.getLogger(BaseConverter.class);

    /**
     * 单个对象转换
     */
    public VO convert(DO from, Class<VO> clazz) {
        if (from == null) {
            return null;
        }
        VO to = null;
        try {
            to = clazz.newInstance();
        } catch (Exception e) {
            logger.error("初始化{}对象失败。", clazz, e);
        }
        convert(from, clazz);
        return to;
    }
}