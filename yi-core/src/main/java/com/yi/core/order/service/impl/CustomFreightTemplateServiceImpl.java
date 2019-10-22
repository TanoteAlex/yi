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
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.CustomFreightTemplateDao;
import com.yi.core.order.domain.bo.CustomFreightTemplateBo;
import com.yi.core.order.domain.entity.CustomFreightTemplate;
import com.yi.core.order.domain.entity.CustomFreightTemplate_;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.simple.CustomFreightTemplateSimple;
import com.yi.core.order.domain.vo.CustomFreightTemplateListVo;
import com.yi.core.order.domain.vo.CustomFreightTemplateVo;
import com.yi.core.order.service.ICustomFreightTemplateService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CustomFreightTemplateServiceImpl implements ICustomFreightTemplateService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CustomFreightTemplateServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CustomFreightTemplateDao customFreightTemplateDao;

	@Resource
	private IRegionService regionService;

	private EntityListVoBoSimpleConvert<CustomFreightTemplate, CustomFreightTemplateBo, CustomFreightTemplateVo, CustomFreightTemplateSimple, CustomFreightTemplateListVo> customFreightTemplateConvert;

	/**
	 * 分页查询CustomFreightTemplate
	 **/
	@Override
	public Page<CustomFreightTemplate> query(Pagination<CustomFreightTemplate> query) {
		query.setEntityClazz(CustomFreightTemplate.class);
		Page<CustomFreightTemplate> page = customFreightTemplateDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: CustomFreightTemplate
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CustomFreightTemplateListVo> queryListVo(Pagination<CustomFreightTemplate> query) {
		Page<CustomFreightTemplate> pages = this.query(query);
		List<CustomFreightTemplateListVo> vos = customFreightTemplateConvert.toListVos(pages.getContent());
		return new PageImpl<CustomFreightTemplateListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建CustomFreightTemplate
	 **/
	@Override
	public CustomFreightTemplate addCustomFreightTemplate(CustomFreightTemplate customFreightTemplate) {
		if (customFreightTemplate == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return customFreightTemplateDao.save(customFreightTemplate);
	}

	/**
	 * 创建CustomFreightTemplate
	 **/
	@Override
	public CustomFreightTemplateVo addCustomFreightTemplate(CustomFreightTemplateBo customFreightTemplateBo) {
		return customFreightTemplateConvert.toVo(this.addCustomFreightTemplate(customFreightTemplateConvert.toEntity(customFreightTemplateBo)));
	}

	/**
	 * 更新CustomFreightTemplate
	 **/
	@Override
	public CustomFreightTemplate updateCustomFreightTemplate(CustomFreightTemplate customFreightTemplate) {
		if (customFreightTemplate == null || customFreightTemplate.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		CustomFreightTemplate dbCustomFreightTemplate = customFreightTemplateDao.getOne(customFreightTemplate.getId());
		if (dbCustomFreightTemplate != null) {
			AttributeReplication.copying(customFreightTemplate, dbCustomFreightTemplate, CustomFreightTemplate_.deliveryMode, CustomFreightTemplate_.defaulted,
					CustomFreightTemplate_.firstQuantity, CustomFreightTemplate_.firstFee, CustomFreightTemplate_.extraQuantity, CustomFreightTemplate_.extraFee);
			// 更新地区
			if (CollectionUtils.isNotEmpty(customFreightTemplate.getRegions())) {
				List<Integer> ids = customFreightTemplate.getRegions().stream().map(e -> e.getId()).collect(Collectors.toList());
				List<Region> tmpRegions = regionService.getRegionsByIds(ids);
				dbCustomFreightTemplate.setRegions(tmpRegions.stream().collect(Collectors.toSet()));
			}
		}
		return dbCustomFreightTemplate;
	}

	/**
	 * 更新CustomFreightTemplate
	 **/
	@Override
	public CustomFreightTemplateVo updateCustomFreightTemplate(CustomFreightTemplateBo customFreightTemplateBo) {
		return customFreightTemplateConvert.toVo(this.updateCustomFreightTemplate(customFreightTemplateConvert.toEntity(customFreightTemplateBo)));
	}

	/**
	 * 删除CustomFreightTemplate
	 **/
	@Override
	public void removeCustomFreightTemplateById(int id) {
		CustomFreightTemplate dbCustomFreightTemplate = customFreightTemplateDao.getOne(id);
		if (dbCustomFreightTemplate != null) {
			dbCustomFreightTemplate.setDeleted(Deleted.DEL_TRUE);
			dbCustomFreightTemplate.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到CustomFreightTemplateBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CustomFreightTemplate getById(int id) {
		return this.customFreightTemplateDao.getOne(id);
	}

	/**
	 * 根据ID得到CustomFreightTemplateVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CustomFreightTemplateVo getCustomFreightTemplateVoById(int id) {
		return customFreightTemplateConvert.toVo(this.customFreightTemplateDao.getOne(id));
	}

	/**
	 * 根据ID得到CustomFreightTemplateListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CustomFreightTemplateListVo getListVoById(int id) {
		return customFreightTemplateConvert.toListVo(this.customFreightTemplateDao.getOne(id));
	}

	/**
	 * 批量保存自定义运费
	 */
	@Override
	public void batchAddCustomFreightTemplate(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig != null && CollectionUtils.isNotEmpty(freightTemplateConfig.getCustomFreightTemplates())) {
			freightTemplateConfig.getCustomFreightTemplates().forEach(e -> {
				if (CollectionUtils.isEmpty(e.getRegions())) {
					e.setDefaulted(OrderEnum.DEFAULT_YES.getCode());
				} else {
					e.setDefaulted(OrderEnum.DEFAULT_NO.getCode());
				}
				e.setFreightTemplateConfig(freightTemplateConfig);
			});
			customFreightTemplateDao.saveAll(freightTemplateConfig.getCustomFreightTemplates());
		}
	}

	/**
	 * 批量更新自定义运费
	 */
	@Override
	public void batchUpdateCustomFreightTemplate(FreightTemplateConfig freightTemplateConfig) {
		if (freightTemplateConfig != null && CollectionUtils.isNotEmpty(freightTemplateConfig.getCustomFreightTemplates())) {
			freightTemplateConfig.getCustomFreightTemplates().forEach(e -> {
				if (CollectionUtils.isEmpty(e.getRegions())) {
					e.setDefaulted(OrderEnum.DEFAULT_YES.getCode());
				} else {
					e.setDefaulted(OrderEnum.DEFAULT_NO.getCode());
				}
				e.setFreightTemplateConfig(freightTemplateConfig);
			});
			// 查询该模板配置下的 自定义运费模板
			List<CustomFreightTemplate> dbCustomFreightTemplates = customFreightTemplateDao.findByFreightTemplateConfig_idAndDeleted(freightTemplateConfig.getId(),
					Deleted.DEL_FALSE);
			// 需要保存的数据
			List<CustomFreightTemplate> saveCustomFreightTemplates = freightTemplateConfig.getCustomFreightTemplates().stream()
					.filter(e1 -> dbCustomFreightTemplates.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toList());
			// 需要更新的数据
			Set<CustomFreightTemplate> updateCustomFreightTemplates = freightTemplateConfig.getCustomFreightTemplates().stream()
					.filter(e1 -> dbCustomFreightTemplates.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			List<CustomFreightTemplate> deleteCustomFreightTemplates = dbCustomFreightTemplates.stream()
					.filter(e1 -> freightTemplateConfig.getCustomFreightTemplates().stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toList());
			// 删除数据
			deleteCustomFreightTemplates.forEach(e -> {
				if (e != null) {
					e.setDeleted(Deleted.DEL_TRUE);
					e.setDelTime(new Date());
				}
			});
			updateCustomFreightTemplates.forEach(tmp -> {
				if (tmp != null) {
					this.updateCustomFreightTemplate(tmp);
				}
			});
			// 保存数据
			customFreightTemplateDao.saveAll(saveCustomFreightTemplates);
		}
	}

	/**
	 * 批量删除数据
	 */
	@Override
	public void batchDeleteCustomFreightTemplate(List<CustomFreightTemplate> customFreightTemplates) {
		if (CollectionUtils.isNotEmpty(customFreightTemplates)) {
			customFreightTemplates.forEach(e -> {
				e.setDeleted(Deleted.DEL_TRUE);
				e.setDelTime(new Date());
				// 删除中间表
				e.setRegions(null);
			});
		}
	}

	protected void initConvert() {
		this.customFreightTemplateConvert = new EntityListVoBoSimpleConvert<CustomFreightTemplate, CustomFreightTemplateBo, CustomFreightTemplateVo, CustomFreightTemplateSimple, CustomFreightTemplateListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<CustomFreightTemplate, CustomFreightTemplateVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplate, CustomFreightTemplateVo>(beanConvertManager) {
					@Override
					protected void postConvert(CustomFreightTemplateVo customFreightTemplateVo, CustomFreightTemplate customFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<CustomFreightTemplate, CustomFreightTemplateListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplate, CustomFreightTemplateListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CustomFreightTemplateListVo customFreightTemplateListVo, CustomFreightTemplate customFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<CustomFreightTemplate, CustomFreightTemplateBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplate, CustomFreightTemplateBo>(beanConvertManager) {
					@Override
					protected void postConvert(CustomFreightTemplateBo customFreightTemplateBo, CustomFreightTemplate customFreightTemplate) {
					}
				};
			}

			@Override
			protected BeanConvert<CustomFreightTemplateBo, CustomFreightTemplate> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplateBo, CustomFreightTemplate>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CustomFreightTemplate, CustomFreightTemplateSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplate, CustomFreightTemplateSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CustomFreightTemplateSimple, CustomFreightTemplate> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CustomFreightTemplateSimple, CustomFreightTemplate>(beanConvertManager) {
					@Override
					public CustomFreightTemplate convert(CustomFreightTemplateSimple customFreightTemplateSimple) throws BeansException {
						return customFreightTemplateDao.getOne(customFreightTemplateSimple.getId());
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
