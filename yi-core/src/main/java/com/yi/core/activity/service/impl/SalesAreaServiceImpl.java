/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yi.core.activity.service.IAreaColumnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.dao.SalesAreaDao;
import com.yi.core.activity.domain.bo.SalesAreaBo;
import com.yi.core.activity.domain.entity.SalesArea;
import com.yi.core.activity.domain.entity.SalesArea_;
import com.yi.core.activity.domain.simple.SalesAreaSimple;
import com.yi.core.activity.domain.vo.SalesAreaListVo;
import com.yi.core.activity.domain.vo.SalesAreaVo;
import com.yi.core.activity.service.ISalesAreaCommodityService;
import com.yi.core.activity.service.ISalesAreaService;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SalesAreaServiceImpl implements ISalesAreaService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(SalesAreaServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private SalesAreaDao salesAreaDao;

    @Resource
    private IAttachmentService attachmentService;

    @Resource
    private ISalesAreaCommodityService salesAreaCommodityService;

    @Resource
    private IAreaColumnService areaColumnService;

    private EntityListVoBoSimpleConvert<SalesArea, SalesAreaBo, SalesAreaVo, SalesAreaSimple, SalesAreaListVo> salesAreaConvert;

    /**
     * 分页查询SalesArea
     **/
    @Override
    public Page<SalesArea> query(Pagination<SalesArea> query) {
        query.setEntityClazz(SalesArea.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SalesArea_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(SalesArea_.createTime)));
        }));
        Page<SalesArea> page = salesAreaDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 分页查询: SalesArea
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<SalesAreaListVo> queryListVo(Pagination<SalesArea> query) {
        query.setEntityClazz(SalesArea.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SalesArea_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(SalesArea_.createTime)));
        }));
        Page<SalesArea> pages = salesAreaDao.findAll(query, query.getPageRequest());
        List<SalesAreaListVo> vos = salesAreaConvert.toListVos(pages.getContent());
        return new PageImpl<SalesAreaListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 分页查询: SalesArea
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<SalesAreaListVo> queryListVoForApp(Pagination<SalesArea> query) {
        query.setEntityClazz(SalesArea.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SalesArea_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(SalesArea_.createTime)));
        }));
        Page<SalesArea> pages = salesAreaDao.findAll(query, query.getPageRequest());
        pages.getContent().forEach(tmp -> {
            tmp.getAreaColumns().forEach(tmp2 -> {
                tmp2.setAreaColumnCommodities(null);
                tmp2.setSalesArea(null);
            });
            tmp.getSalesAreaCommodities().forEach(tmp3 -> {
                tmp3.setSalesAreaCommodityId(null);
                tmp3.getCommodity().setCategory(null);
                tmp3.getCommodity().setSupplier(null);
                tmp3.getCommodity().setComments(null);
            });
        });
        List<SalesAreaListVo> vos = salesAreaConvert.toListVos(pages.getContent());
        return new PageImpl<SalesAreaListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 查询所有活动专区
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<SalesAreaListVo> getAll() {
        List<SalesArea> results = salesAreaDao.findAll((root, query, criteriaBuilder) -> {
            criteriaBuilder.and(criteriaBuilder.equal(root.get(SalesArea_.deleted), Deleted.DEL_FALSE));
            criteriaBuilder.desc(root.get(SalesArea_.createTime));
            return criteriaBuilder.conjunction();
        });
        return salesAreaConvert.toListVos(results);
    }

    /**
     * 创建SalesArea
     **/
    @Override
    public SalesArea addSalesArea(SalesArea salesArea) {
        if (salesArea == null) {
            LOG.error("提交数据不能为空");
            throw new BusinessException("提交数据不能为空");
        }
        SalesArea dbSalesArea = salesAreaDao.save(salesArea);
        // 保存销售专区商品
        salesAreaCommodityService.batchAddSalesAreaCommodity(dbSalesArea, salesArea.getSalesAreaCommodities());
        return dbSalesArea;
    }

    /**
     * 创建SalesArea
     **/
    @Override
    public SalesAreaListVo addSalesArea(SalesAreaBo salesAreaBo) {
        SalesAreaListVo dbSalesAreaListVo = salesAreaConvert.toListVo(this.addSalesArea(salesAreaConvert.toEntity(salesAreaBo)));
        return dbSalesAreaListVo;
    }

    /**
     * 更新SalesArea
     **/
    @Override
    public SalesArea updateSalesArea(SalesArea salesArea) {
        if (salesArea == null) {
            LOG.error("提交数据不能为空");
            throw new BusinessException("提交数据不能为空");
        }
        SalesArea dbSalesArea = salesAreaDao.getOne(salesArea.getId());
        AttributeReplication.copying(salesArea, dbSalesArea, SalesArea_.title, SalesArea_.banner, SalesArea_.showMode, SalesArea_.state);
        // 更新销售专区商品
        salesAreaCommodityService.batchUpdateSalesAreaCommodity(dbSalesArea, salesArea.getSalesAreaCommodities());
        return dbSalesArea;
    }

    /**
     * 更新SalesArea
     **/
    @Override
    public SalesAreaListVo updateSalesArea(SalesAreaBo salesAreaBo) {
        SalesArea dbSalesArea = this.updateSalesArea(salesAreaConvert.toEntity(salesAreaBo));
        return salesAreaConvert.toListVo(dbSalesArea);
    }

    /**
     * 删除SalesArea
     **/
    @Override
    public void removeSalesAreaById(java.lang.Integer id) {
        SalesArea dbSalesArea = this.getSalesAreaById(id);
        if (dbSalesArea != null) {
            dbSalesArea.setDeleted(Deleted.DEL_TRUE);
            dbSalesArea.setDelTime(new Date());
            // 删除销售专区商品
            salesAreaCommodityService.removeBySalesArea(id);
        }
    }

    /**
     * 根据ID得到SalesArea
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SalesArea getSalesAreaById(java.lang.Integer id) {
        return this.salesAreaDao.getOne(id);
    }

    /**
     * 根据ID得到SalesAreaBo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SalesAreaBo getSalesAreaBoById(java.lang.Integer id) {
        return salesAreaConvert.toBo(this.salesAreaDao.getOne(id));
    }

    /**
     * 根据ID得到SalesAreaVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SalesAreaVo getSalesAreaVoById(java.lang.Integer id) {
        return salesAreaConvert.toVo(this.salesAreaDao.getOne(id));
    }

    /**
     * 根据ID得到SalesAreaListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SalesAreaListVo getListVoById(java.lang.Integer id) {
        return salesAreaConvert.toListVo(this.salesAreaDao.getOne(id));
    }

    protected void initConvert() {
        this.salesAreaConvert = new EntityListVoBoSimpleConvert<SalesArea, SalesAreaBo, SalesAreaVo, SalesAreaSimple, SalesAreaListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<SalesArea, SalesAreaVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesArea, SalesAreaVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SalesAreaVo salesAreaVo, SalesArea salesArea) {
                        salesAreaVo.setAreaColumns(areaColumnService.getAreaColumnConvert().toSimples(salesArea.getAreaColumns()));
                        salesAreaVo.setSalesAreaCommodities(salesAreaCommodityService.getSalesAreaCommodityConvert().toSimples(salesArea.getSalesAreaCommodities()));
                    }
                };
            }

            @Override
            protected BeanConvert<SalesArea, SalesAreaListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesArea, SalesAreaListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SalesAreaListVo salesAreaListVo, SalesArea salesArea) {
                    }
                };
            }

            @Override
            protected BeanConvert<SalesArea, SalesAreaBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesArea, SalesAreaBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(SalesAreaBo salesAreaBo, SalesArea salesArea) {
                    }
                };
            }

            @Override
            protected BeanConvert<SalesAreaBo, SalesArea> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesAreaBo, SalesArea>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<SalesArea, SalesAreaSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesArea, SalesAreaSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<SalesAreaSimple, SalesArea> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<SalesAreaSimple, SalesArea>(beanConvertManager) {
                    @Override
                    public SalesArea convert(SalesAreaSimple salesAreaSimple) throws BeansException {
                        return salesAreaDao.getOne(salesAreaSimple.getId());
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
