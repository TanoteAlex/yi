/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import com.yi.core.order.dao.SaleOrderItemDao;
import com.yi.core.order.domain.bo.SaleOrderItemBo;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.entity.SaleOrderItem_;
import com.yi.core.order.domain.simple.SaleOrderItemSimple;
import com.yi.core.order.domain.vo.SaleOrderItemListVo;
import com.yi.core.order.domain.vo.SaleOrderItemVo;
import com.yi.core.order.service.ISaleOrderItemService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.beans.factory.InitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yihz.common.convert.BeanConvertManager;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SaleOrderItemServiceImpl implements ISaleOrderItemService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(SaleOrderItemServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private SaleOrderItemDao saleOrderItemDao;

    private EntityListVoBoSimpleConvert<SaleOrderItem, SaleOrderItemBo, SaleOrderItemVo, SaleOrderItemSimple, SaleOrderItemListVo> saleOrderItemConvert;

    /**
    * 分页查询SaleOrderItem
    **/
     @Override
    public Page<SaleOrderItem> query(Pagination<SaleOrderItem> query) {
        query.setEntityClazz(SaleOrderItem.class);
        Page<SaleOrderItem> page = saleOrderItemDao.findAll(query, query.getPageRequest());
        return page;
    }
    
   /**
    * 分页查询: SaleOrderItem
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page <SaleOrderItemListVo> queryListVo(Pagination<SaleOrderItem> query){
        Page<SaleOrderItem> pages = this.query(query);
        List<SaleOrderItemListVo> vos = saleOrderItemConvert.toListVos(pages.getContent());
        return new PageImpl<SaleOrderItemListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }
    
    /**
    * 分页查询: SaleOrderItem
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page <SaleOrderItemListVo> queryListVoForApp(Pagination<SaleOrderItem> query){
    	query.setEntityClazz(SaleOrderItem.class);
        Page<SaleOrderItem> pages = saleOrderItemDao.findAll(query, query.getPageRequest());
        List<SaleOrderItemListVo> vos = saleOrderItemConvert.toListVos(pages.getContent());
        return new PageImpl<SaleOrderItemListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
    * 创建SaleOrderItem
    **/
    @Override
    public SaleOrderItem addSaleOrderItem(SaleOrderItem saleOrderItem){
        return saleOrderItemDao.save(saleOrderItem);
    }
    
    /**
    * 创建SaleOrderItem
    **/
    @Override
    public SaleOrderItemListVo addSaleOrderItem(SaleOrderItemBo saleOrderItemBo){
        return saleOrderItemConvert.toListVo(saleOrderItemDao.save(saleOrderItemConvert.toEntity(saleOrderItemBo)));
    }
    
    /**
    * 更新SaleOrderItem
    **/
    @Override
    public SaleOrderItem updateSaleOrderItem(SaleOrderItem saleOrderItem){
        SaleOrderItem dbSaleOrderItem=saleOrderItemDao.getOne(saleOrderItem.getId());
        AttributeReplication.copying(saleOrderItem,dbSaleOrderItem,
                SaleOrderItem_.saleOrder,
                SaleOrderItem_.member,
                SaleOrderItem_.supplier,
                SaleOrderItem_.commodity,
                SaleOrderItem_.product,
                SaleOrderItem_.price,
                SaleOrderItem_.quantity,
                SaleOrderItem_.discount,
                SaleOrderItem_.subtotal
         );
        return dbSaleOrderItem;
    }

    /**
    * 更新SaleOrderItem
    **/
    @Override
    public SaleOrderItemListVo updateSaleOrderItem(SaleOrderItemBo saleOrderItemBo){
        SaleOrderItem dbSaleOrderItem=saleOrderItemDao.getOne(saleOrderItemBo.getId());
        AttributeReplication.copying(saleOrderItemBo,dbSaleOrderItem,
                SaleOrderItem_.saleOrder,
                SaleOrderItem_.member,
                SaleOrderItem_.supplier,
                SaleOrderItem_.commodity,
                SaleOrderItem_.product,
                SaleOrderItem_.price,
                SaleOrderItem_.quantity,
                SaleOrderItem_.discount,
                SaleOrderItem_.subtotal
         );
        return saleOrderItemConvert.toListVo(dbSaleOrderItem);
    }

    /**
    * 删除SaleOrderItem
    **/
    @Override
    public void removeSaleOrderItemById(int id) {
        saleOrderItemDao.deleteById(id);
    }
    
  /**
    * 根据ID得到SaleOrderItem
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SaleOrderItem getSaleOrderItemById(int id) {
        return this.saleOrderItemDao.getOne(id);
    }

    /**
    * 根据ID得到SaleOrderItemBo
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SaleOrderItemBo getSaleOrderItemBoById(int id) {
        return saleOrderItemConvert.toBo(this.saleOrderItemDao.getOne(id));
    }

    /**
    * 根据ID得到SaleOrderItemVo
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SaleOrderItemVo getSaleOrderItemVoById(int id) {
        return saleOrderItemConvert.toVo(this.saleOrderItemDao.getOne(id));
    }

    /**
    * 根据ID得到SaleOrderItemListVo
    **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SaleOrderItemListVo getListVoById(int id) {
        return saleOrderItemConvert.toListVo(this.saleOrderItemDao.getOne(id));
    }

    @Override
    public List<Object[]> getSaleStatisticsGrouping(String startTime, String endTime){
        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            return	saleOrderItemDao.getSaleStatisticsGrouping(startTime,endTime);
        }
        return saleOrderItemDao.getSaleStatisticsGrouping();
    }


    protected void initConvert() {
        this.saleOrderItemConvert = new EntityListVoBoSimpleConvert<SaleOrderItem, SaleOrderItemBo, SaleOrderItemVo, SaleOrderItemSimple, SaleOrderItemListVo>
        (beanConvertManager) {
            @Override
            protected BeanConvert<SaleOrderItem, SaleOrderItemVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SaleOrderItem, SaleOrderItemVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SaleOrderItemVo saleOrderItemVo, SaleOrderItem saleOrderItem) {}
                };
            }

            @Override
            protected BeanConvert<SaleOrderItem, SaleOrderItemListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SaleOrderItem, SaleOrderItemListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SaleOrderItemListVo saleOrderItemListVo, SaleOrderItem saleOrderItem) {}
                };
            }

            @Override
            protected BeanConvert<SaleOrderItem, SaleOrderItemBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SaleOrderItem, SaleOrderItemBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SaleOrderItemBo saleOrderItemBo, SaleOrderItem saleOrderItem) {}
                };
            }

            @Override
            protected BeanConvert <SaleOrderItemBo, SaleOrderItem> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                 return new AbstractBeanConvert <SaleOrderItemBo , SaleOrderItem>(beanConvertManager) {};
            }

            @Override
            protected BeanConvert<SaleOrderItem, SaleOrderItemSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SaleOrderItem, SaleOrderItemSimple>(beanConvertManager) {};
            }

            @Override
            protected BeanConvert <SaleOrderItemSimple, SaleOrderItem> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert <SaleOrderItemSimple , SaleOrderItem>(beanConvertManager) {
                    @Override
                    public SaleOrderItem convert(SaleOrderItemSimple saleOrderItemSimple) throws BeansException {
                        return saleOrderItemDao.getOne(saleOrderItemSimple.getId());
                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Object[]> getTotalSupplierSaleData(Integer state) {
        return saleOrderItemDao.findTotalSupplierSaleData(state);
    }

    @Override
    public List<Object[]> getSupplierSaleDataByDate(Pageable pageable, Integer state, Date startDate, Date endDate) {
        if (startDate == null && endDate == null) {
            return saleOrderItemDao.getSupplierSaleDataByDate(pageable, state);
        }
        return saleOrderItemDao.getSupplierSaleDataByDate(pageable, state, startDate, endDate);
    }
}
