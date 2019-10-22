/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service;

import com.yi.core.order.domain.bo.LogisticsAddressBo;
import com.yi.core.order.domain.entity.LogisticsAddress;
import com.yi.core.order.domain.vo.LogisticsAddressListVo;
import com.yi.core.order.domain.vo.LogisticsAddressVo;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface ILogisticsAddressService {


    Page<LogisticsAddress> query(Pagination<LogisticsAddress> query);

    /**
     * 创建LogisticsAddress
     **/
    LogisticsAddressListVo addLogisticsAddress(LogisticsAddressBo logisticsAddress);

    public LogisticsAddress updateLogisticsAddress(LogisticsAddress logisticsAddress);

    /**
     * 更新LogisticsAddress
     **/
    LogisticsAddressListVo updateLogisticsAddress(LogisticsAddressBo logisticsAddress);

    /**
     * 删除LogisticsAddress
     **/
    void removeLogisticsAddressById(int logisticsAddressId);

    /**
     * 根据ID得到LogisticsAddressVo
     **/
    LogisticsAddressVo getLogisticsAddressVoById(int logisticsAddressId);

    /**
     * 根据ID得到LogisticsAddressListVo
     **/
    LogisticsAddressListVo getListVoById(int logisticsAddressId);

    /**
     * 分页查询: LogisticsAddress
     **/
    Page<LogisticsAddressListVo> queryListVo(Pagination<LogisticsAddress> query);


}
