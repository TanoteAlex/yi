/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import com.yi.core.order.dao.LogisticsAddressDao;
import com.yi.core.order.domain.bo.LogisticsAddressBo;
import com.yi.core.order.domain.entity.LogisticsAddress;
import com.yi.core.order.domain.entity.LogisticsAddress_;
import com.yi.core.order.domain.simple.LogisticsAddressSimple;
import com.yi.core.order.domain.vo.LogisticsAddressListVo;
import com.yi.core.order.domain.vo.LogisticsAddressVo;
import com.yi.core.order.service.ILogisticsAddressService;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.data.domain.Page;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.beans.factory.InitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class LogisticsAddressServiceImpl implements ILogisticsAddressService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(LogisticsAddressServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private LogisticsAddressDao logisticsAddressDao;

    private EntityListVoBoSimpleConvert<LogisticsAddress, LogisticsAddressBo, LogisticsAddressVo,
            LogisticsAddressSimple, LogisticsAddressListVo> logisticsAddressConvert;

    /**
     * 分页查询LogisticsAddress
     **/
    @Override
    public Page<LogisticsAddress> query(Pagination<LogisticsAddress> query) {
        query.setEntityClazz(LogisticsAddress.class);
        Page<LogisticsAddress> page = logisticsAddressDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建LogisticsAddress
     **/
    @Override
    public LogisticsAddressListVo addLogisticsAddress(LogisticsAddressBo logisticsAddressBo) {
        return logisticsAddressConvert.toListVo(logisticsAddressDao.save(logisticsAddressConvert.toEntity
                (logisticsAddressBo)));
    }

    @Override
    public LogisticsAddress updateLogisticsAddress(LogisticsAddress logisticsAddress) {
        LogisticsAddress dbLogisticsAddress = logisticsAddressDao.getOne(logisticsAddress.getId());
        AttributeReplication.copying(logisticsAddress, dbLogisticsAddress, LogisticsAddress_.addressType, LogisticsAddress_.contact,
                LogisticsAddress_.contactPhone, LogisticsAddress_.province, LogisticsAddress_.city, LogisticsAddress_.district,
                LogisticsAddress_.address, LogisticsAddress_.state
        );
        return dbLogisticsAddress;
    }

    /**
     * 更新LogisticsAddress
     **/
    @Override
    public LogisticsAddressListVo updateLogisticsAddress(LogisticsAddressBo logisticsAddressBo) {
        return logisticsAddressConvert.toListVo(this.updateLogisticsAddress(logisticsAddressConvert.toEntity(logisticsAddressBo)));
    }

    /**
     * 删除LogisticsAddress
     **/
    @Override
    public void removeLogisticsAddressById(int id) {
        logisticsAddressDao.deleteById(id);
    }

    /**
     * 根据ID得到LogisticsAddress
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LogisticsAddressVo getLogisticsAddressVoById(int id) {

        return logisticsAddressConvert.toVo(this.logisticsAddressDao.getOne(id));
    }

    /**
     * 根据ID得到LogisticsAddressListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LogisticsAddressListVo getListVoById(int id) {
        return logisticsAddressConvert.toListVo(this.logisticsAddressDao.getOne(id));
    }

    /**
     * 分页查询: LogisticsAddress
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page
            <LogisticsAddressListVo> queryListVo(Pagination<LogisticsAddress> query) {

        Page<LogisticsAddress> pages = this.query(query);

        List
                <LogisticsAddressListVo> vos = logisticsAddressConvert.toListVos(pages.getContent());
        return new PageImpl
                <LogisticsAddressListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.logisticsAddressConvert = new EntityListVoBoSimpleConvert<LogisticsAddress, LogisticsAddressBo,
                LogisticsAddressVo, LogisticsAddressSimple, LogisticsAddressListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<LogisticsAddress, LogisticsAddressVo> createEntityToVoConvert(BeanConvertManager
                                                                                                        beanConvertManager) {
                return new AbstractBeanConvert<LogisticsAddress, LogisticsAddressVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(LogisticsAddressVo LogisticsAddressVo, LogisticsAddress
                            LogisticsAddress) {

                    }
                };
            }


            @Override
            protected BeanConvert<LogisticsAddress, LogisticsAddressListVo> createEntityToListVoConvert
                    (BeanConvertManager
                             beanConvertManager) {
                return new AbstractBeanConvert<LogisticsAddress, LogisticsAddressListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(LogisticsAddressListVo LogisticsAddressListVo, LogisticsAddress
                            LogisticsAddress) {

                    }
                };
            }

            @Override
            protected BeanConvert<LogisticsAddress, LogisticsAddressBo> createEntityToBoConvert(BeanConvertManager
                                                                                                        beanConvertManager) {
                return new AbstractBeanConvert<LogisticsAddress, LogisticsAddressBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(LogisticsAddressBo LogisticsAddressBo, LogisticsAddress
                            LogisticsAddress) {

                    }
                };
            }

            @Override
            protected BeanConvert
                    <LogisticsAddressBo
                            , LogisticsAddress> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <LogisticsAddressBo
                                , LogisticsAddress>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<LogisticsAddress, LogisticsAddressSimple> createEntityToSimpleConvert
                    (BeanConvertManager
                             beanConvertManager) {
                return new AbstractBeanConvert<LogisticsAddress, LogisticsAddressSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert
                    <LogisticsAddressSimple
                            , LogisticsAddress> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <LogisticsAddressSimple
                                , LogisticsAddress>(beanConvertManager) {
                    @Override
                    public LogisticsAddress convert(LogisticsAddressSimple LogisticsAddressSimple) throws
                            BeansException {
                        return logisticsAddressDao.getOne(LogisticsAddressSimple.getId());
                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }
}
