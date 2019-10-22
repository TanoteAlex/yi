/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.service.IRegionService;
import com.yi.core.common.Deleted;
import com.yi.core.order.dao.FreeFreightTemplateDao;
import com.yi.core.order.domain.bo.FreeFreightTemplateBo;
import com.yi.core.order.domain.entity.FreeFreightTemplate;
import com.yi.core.order.domain.entity.FreeFreightTemplate_;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.simple.FreeFreightTemplateSimple;
import com.yi.core.order.domain.vo.FreeFreightTemplateListVo;
import com.yi.core.order.domain.vo.FreeFreightTemplateVo;
import com.yi.core.order.service.IFreeFreightTemplateService;
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
public class FreeFreightTemplateServiceImpl implements IFreeFreightTemplateService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(FreeFreightTemplateServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private FreeFreightTemplateDao freeFreightTemplateDao;

	@Resource
	private IRegionService regionService;

	private EntityListVoBoSimpleConvert<FreeFreightTemplate, FreeFreightTemplateBo, FreeFreightTemplateVo, FreeFreightTemplateSimple, FreeFreightTemplateListVo> freeFreightTemplateConvert;

	/**
	 * 分页查询FreeFreightTemplate
	 **/
	@Override
	public Page<FreeFreightTemplate> query(Pagination<FreeFreightTemplate> query) {
		query.setEntityClazz(FreeFreightTemplate.class);
		Page<FreeFreightTemplate> page = freeFreightTemplateDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: FreeFreightTemplate
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<FreeFreightTemplateListVo> queryListVo(Pagination<FreeFreightTemplate> query) {
		Page<FreeFreightTemplate> pages = this.query(query);
		List<FreeFreightTemplateListVo> vos = freeFreightTemplateConvert.toListVos(pages.getContent());
		return new PageImpl<FreeFreightTemplateListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建FreeFreightTemplate
	 **/
	@Override
	public FreeFreightTemplate addFreeFreightTemplate(FreeFreightTemplate freeFreightTemplate) {
		if (freeFreightTemplate == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return freeFreightTemplateDao.save(freeFreightTemplate);
	}

	/**
	 * 创建FreeFreightTemplate
	 **/
	@Override
	public FreeFreightTemplateVo addFreeFreightTemplate(FreeFreightTemplateBo freeFreightTemplateBo) {
		return freeFreightTemplateConvert.toVo(this.addFreeFreightTemplate(freeFreightTemplateConvert.toEntity(freeFreightTemplateBo)));
	}

	/**
	 * 更新FreeFreightTemplate
	 **/
	@Override
	public FreeFreightTemplate updateFreeFreightTemplate(FreeFreightTemplate freeFreightTemplate) {
		if (freeFreightTemplate == null || freeFreightTemplate.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		FreeFreightTemplate dbFreeFreightTemplate = freeFreightTemplateDao.getOne(freeFreightTemplate.getId());
		if (dbFreeFreightTemplate != null) {
			LOG.error("FreeFreightTemplate更新失败，根据id={}查询数据为空", freeFreightTemplate.getId());
			AttributeReplication.copying(freeFreightTemplate, dbFreeFreightTemplate, FreeFreightTemplate_.deliveryMode, FreeFreightTemplate_.freeType,
					FreeFreightTemplate_.fullQuantity, FreeFreightTemplate_.fullAmount);
			// 更新地区
			if (CollectionUtils.isNotEmpty(freeFreightTemplate.getRegions())) {
				List<Integer> ids = freeFreightTemplate.getRegions().stream().map(e -> e.getId()).collect(Collectors.toList());
				List<Region> tmpRegions = regionService.getRegionsByIds(ids);
				dbFreeFreightTemplate.setRegions(tmpRegions.stream().collect(Collectors.toSet()));
			}
		}
		return dbFreeFreightTemplate;
	}

	/**
	 * 更新FreeFreightTemplate
	 **/
	@Override
	public FreeFreightTemplateVo updateFreeFreightTemplate(FreeFreightTemplateBo freeFreightTemplateBo) {
		return freeFreightTemplateConvert.toVo(this.updateFreeFreightTemplate(freeFreightTemplateConvert.toEntity(freeFreightTemplateBo)));
	}

	/**
	 * 删除FreeFreightTemplate
	 **/
	@Override
	public void removeFreeFreightTemplateById(int id) {
		freeFreightTemplateDao.deleteById(id);
	}

	/**
	 * 根据ID得到FreeFreightTemplate
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreeFreightTemplate getById(int id) {
		return this.freeFreightTemplateDao.getOne(id);
	}

	/**
	 * 根据ID得到FreeFreightTemplateVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreeFreightTemplateVo getVoById(int id) {
		return freeFreightTemplateConvert.toVo(this.freeFreightTemplateDao.getOne(id));
	}

	/**
	 * 根据ID得到FreeFreightTemplateListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FreeFreightTemplateListVo getListVoById(int id) {
		return freeFreightTemplateConvert.toListVo(this.freeFreightTemplateDao.getOne(id));
	}

	/**
	 * 批量保存指定条件包邮
	 */
	@Override
	public void batchAddFreeFreightTemplate(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig != null && CollectionUtils.isNotEmpty(freightTemplateConfig.getFreeFreightTemplates())) {
			freightTemplateConfig.getFreeFreightTemplates().forEach(e -> {
				e.setFreightTemplateConfig(freightTemplateConfig);
			});
			freeFreightTemplateDao.saveAll(freightTemplateConfig.getFreeFreightTemplates());
		}
	}

	/**
	 * 批量更新指定条件包邮
	 */
	@Override
	public void batchUpdateFreeFreightTemplate(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig != null && CollectionUtils.isNotEmpty(freightTemplateConfig.getFreeFreightTemplates())) {
			freightTemplateConfig.getFreeFreightTemplates().forEach(e -> {
				e.setFreightTemplateConfig(freightTemplateConfig);
			});
			// 查询该模板配置下的 包邮运费模板
			List<FreeFreightTemplate> dbFreeFreightTemplates = freeFreightTemplateDao.findByFreightTemplateConfig_idAndDeleted(freightTemplateConfig.getId(), Deleted.DEL_FALSE);
			// 需要保存的数据
			List<FreeFreightTemplate> saveFreeFreightTemplates = freightTemplateConfig.getFreeFreightTemplates().stream()
					.filter(e1 -> dbFreeFreightTemplates.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toList());
			// 需要更新的数据
			Set<FreeFreightTemplate> updateFreeFreightTemplates = freightTemplateConfig.getFreeFreightTemplates().stream()
					.filter(e1 -> dbFreeFreightTemplates.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			List<FreeFreightTemplate> deleteFreeFreightTemplates = dbFreeFreightTemplates.stream()
					.filter(e1 -> freightTemplateConfig.getFreeFreightTemplates().stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toList());
			// 删除数据
			deleteFreeFreightTemplates.forEach(e -> {
				if (e != null) {
					e.setDeleted(Deleted.DEL_TRUE);
					e.setDelTime(new Date());
				}
			});
			updateFreeFreightTemplates.forEach(tmp -> {
				if (tmp != null) {
					this.updateFreeFreightTemplate(tmp);
				}
			});
			// 保存数据
			freeFreightTemplateDao.saveAll(saveFreeFreightTemplates);
		}
	}

	/**
	 * 批量删除指定条件包邮
	 */
	@Override
	public void batchDeleteFreeFreightTemplate(List<FreeFreightTemplate> freeFreightTemplates) {
		if (CollectionUtils.isNotEmpty(freeFreightTemplates)) {
			freeFreightTemplates.forEach(e -> {
				e.setDeleted(Deleted.DEL_TRUE);
				e.setDelTime(new Date());
				// 删除中间表
				e.setRegions(null);
			});
		}

	}

	protected void initConvert() {
		this.freeFreightTemplateConvert = new EntityListVoBoSimpleConvert<FreeFreightTemplate, FreeFreightTemplateBo, FreeFreightTemplateVo, FreeFreightTemplateSimple, FreeFreightTemplateListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<FreeFreightTemplate, FreeFreightTemplateVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplate, FreeFreightTemplateVo>(beanConvertManager) {
					@Override
					protected void postConvert(FreeFreightTemplateVo freeFreightTemplateVo, FreeFreightTemplate freeFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<FreeFreightTemplate, FreeFreightTemplateListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplate, FreeFreightTemplateListVo>(beanConvertManager) {
					@Override
					protected void postConvert(FreeFreightTemplateListVo freeFreightTemplateListVo, FreeFreightTemplate freeFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<FreeFreightTemplate, FreeFreightTemplateBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplate, FreeFreightTemplateBo>(beanConvertManager) {
					@Override
					protected void postConvert(FreeFreightTemplateBo freeFreightTemplateBo, FreeFreightTemplate freeFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<FreeFreightTemplateBo, FreeFreightTemplate> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplateBo, FreeFreightTemplate>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<FreeFreightTemplate, FreeFreightTemplateSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplate, FreeFreightTemplateSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<FreeFreightTemplateSimple, FreeFreightTemplate> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<FreeFreightTemplateSimple, FreeFreightTemplate>(beanConvertManager) {
					@Override
					public FreeFreightTemplate convert(FreeFreightTemplateSimple freeFreightTemplateSimple) throws BeansException {
						return freeFreightTemplateDao.getOne(freeFreightTemplateSimple.getId());
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
