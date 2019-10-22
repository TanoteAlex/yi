/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.order.dao.ExpressTemplateDao;
import com.yi.core.order.domain.bo.ExpressTemplateBo;
import com.yi.core.order.domain.entity.ExpressTemplate;
import com.yi.core.order.domain.entity.ExpressTemplate_;
import com.yi.core.order.domain.simple.ExpressTemplateSimple;
import com.yi.core.order.domain.vo.ExpressTemplateListVo;
import com.yi.core.order.domain.vo.ExpressTemplateVo;
import com.yi.core.order.service.IExpressTemplateService;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;

/**
 * 快递模板
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class ExpressTemplateServiceImpl implements IExpressTemplateService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(ExpressTemplateServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private ExpressTemplateDao expressTemplateDao;

	private EntityListVoBoSimpleConvert<ExpressTemplate, ExpressTemplateBo, ExpressTemplateVo, ExpressTemplateSimple, ExpressTemplateListVo> expressTemplateConvert;

	@Override
	public ExpressTemplate getExpressTemplateById(int expressTemplateId) {
		return expressTemplateDao.getOne(expressTemplateId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpressTemplateVo getExpressTemplateVoById(int expressTemplateId) {

		return expressTemplateConvert.toVo(this.expressTemplateDao.getOne(expressTemplateId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExpressTemplateListVo getExpressTemplateListVoById(int expressTemplateId) {
		return expressTemplateConvert.toListVo(this.expressTemplateDao.getOne(expressTemplateId));
	}

	@Override
	public List<ExpressTemplateListVo> addExpressTemplate(List<ExpressTemplateBo> expressTemplate, Integer supplierId) {
		List<ExpressTemplate> list = expressTemplateConvert.toEntities(expressTemplate);
		Supplier supplier = supplierService.getSupplierById(supplierId);
		list.forEach(e -> {
			e.setSupplier(supplier);
			e.setTemplateNo(NumberGenerateUtils.generateExpressTemplateNo());
		});
		return expressTemplateConvert.toListVos(expressTemplateDao.saveAll(list));
	}

	@Override
	public ExpressTemplateListVo addExpressTemplateBo(ExpressTemplateBo expressTemplateBo) {
		expressTemplateBo.setCreateTime(new Date());
		expressTemplateBo.setGuid(ValueUtils.generateGUID());
		return expressTemplateConvert
				.toListVo(expressTemplateDao.save(expressTemplateConvert.toEntity(expressTemplateBo)));
	}

	@Override
	public ExpressTemplateVo updateExpressTemplate(ExpressTemplate expressTemplate) {
		ExpressTemplate dbExpressTemplate = expressTemplateDao.getOne(expressTemplate.getId());
		AttributeReplication.copying(expressTemplate, dbExpressTemplate,
				ExpressTemplate_.templateName, ExpressTemplate_.printWidth, ExpressTemplate_.printHigh,
				ExpressTemplate_.state);
		return expressTemplateConvert.toVo(dbExpressTemplate);
	}

	@Override
	public ExpressTemplateVo updateExpressTemplate(ExpressTemplateBo expressTemplateBo) {
		ExpressTemplate dbExpressTemplate = expressTemplateDao.getOne(expressTemplateBo.getId());
		AttributeReplication.copying(expressTemplateConvert.toEntity(expressTemplateBo), dbExpressTemplate,
				ExpressTemplate_.templateName, ExpressTemplate_.printWidth, ExpressTemplate_.printHigh,
				ExpressTemplate_.state);
		return expressTemplateConvert.toVo(dbExpressTemplate);
	}

	@Override
	public void removeExpressTemplateById(int expressTemplateId) {
		ExpressTemplate dbExpressTemplate = expressTemplateDao.getOne(expressTemplateId);
		if (dbExpressTemplate == null) {
			LOG.error("快递模板删除失败，快递模板数据不存在，expressTemplateId={}", expressTemplateId);
			throw new BusinessException("请选择需要删除的数据");
		}
		dbExpressTemplate.setDeleted(Deleted.DEL_TRUE);
		dbExpressTemplate.setDelTime(new Date());
	}

	@Override
	public Page<ExpressTemplate> query(Pagination<ExpressTemplate> query) {
		query.setEntityClazz(ExpressTemplate.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(ExpressTemplate_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(ExpressTemplate_.createTime)));
			// 供应商
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(ExpressTemplate_.supplier).get(Supplier_.id), supplierId));
			}
		}));
		Page<ExpressTemplate> page = expressTemplateDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ExpressTemplateListVo> queryListVo(Pagination<ExpressTemplate> query) {

		Page<ExpressTemplate> pages = this.query(query);

		List<ExpressTemplateListVo> vos = expressTemplateConvert.toListVos(pages.getContent());
		return new PageImpl<ExpressTemplateListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.expressTemplateConvert = new EntityListVoBoSimpleConvert<ExpressTemplate, ExpressTemplateBo, ExpressTemplateVo, ExpressTemplateSimple, ExpressTemplateListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<ExpressTemplate, ExpressTemplateVo> createEntityToVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplate, ExpressTemplateVo>(beanConvertManager) {
					@Override
					protected void postConvert(ExpressTemplateVo ExpressTemplateVo, ExpressTemplate ExpressTemplate) {

					}
				};
			}

			@Override
			protected BeanConvert<ExpressTemplate, ExpressTemplateListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplate, ExpressTemplateListVo>(beanConvertManager) {
					@Override
					protected void postConvert(ExpressTemplateListVo ExpressTemplateListVo,
							ExpressTemplate ExpressTemplate) {

					}
				};
			}

			@Override
			protected BeanConvert<ExpressTemplate, ExpressTemplateBo> createEntityToBoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplate, ExpressTemplateBo>(beanConvertManager) {
					@Override
					protected void postConvert(ExpressTemplateBo ExpressTemplateBo, ExpressTemplate ExpressTemplate) {

					}
				};
			}

			@Override
			protected BeanConvert<ExpressTemplateBo, ExpressTemplate> createBoToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplateBo, ExpressTemplate>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ExpressTemplate, ExpressTemplateSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplate, ExpressTemplateSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ExpressTemplateSimple, ExpressTemplate> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ExpressTemplateSimple, ExpressTemplate>(beanConvertManager) {
					@Override
					public ExpressTemplate convert(ExpressTemplateSimple ExpressTemplateSimple) throws BeansException {
						return expressTemplateDao.getOne(ExpressTemplateSimple.getId());
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
