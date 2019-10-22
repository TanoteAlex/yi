/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.Collection;
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

import com.yi.core.commodity.dao.AttributeDao;
import com.yi.core.commodity.domain.bo.AttributeBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.AttributeGroup;
import com.yi.core.commodity.domain.entity.Attribute_;
import com.yi.core.commodity.domain.simple.AttributeSimple;
import com.yi.core.commodity.domain.vo.AttributeListVo;
import com.yi.core.commodity.domain.vo.AttributeVo;
import com.yi.core.commodity.service.IAttributeService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 属性组
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AttributeServiceImpl implements IAttributeService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AttributeServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AttributeDao attributeDao;

	private EntityListVoBoSimpleConvert<Attribute, AttributeBo, AttributeVo, AttributeSimple, AttributeListVo> attributeConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Attribute> query(Pagination<Attribute> query) {
		query.setEntityClazz(Attribute.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Attribute_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Attribute> page = attributeDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AttributeListVo> queryListVo(Pagination<Attribute> query) {
		Page<Attribute> pages = this.query(query);
		List<AttributeListVo> vos = attributeConvert.toListVos(pages.getContent());
		return new PageImpl<AttributeListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	public Attribute addAttribute(Attribute attribute) {
		if (attribute == null || attribute.getAttributeGroup() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return attributeDao.save(attribute);
	}

	@Override
	public AttributeVo addAttribute(AttributeBo attributeBo) {
		return attributeConvert.toVo(this.addAttribute(attributeConvert.toEntity(attributeBo)));
	}

	@Override
	public Attribute updateAttribute(Attribute attribute) {
		if (attribute == null || attribute.getId() < 1 || attribute.getAttributeGroup() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		Attribute dbAttribute = attributeDao.getOne(attribute.getId());
		AttributeReplication.copying(attribute, dbAttribute, Attribute_.attrName, Attribute_.attrValue, Attribute_.imgPath, Attribute_.showMode, Attribute_.sort,
				Attribute_.remark);
		return dbAttribute;
	}

	@Override
	public AttributeVo updateAttribute(AttributeBo attributeBo) {
		return attributeConvert.toVo(this.updateAttribute(attributeConvert.toEntity(attributeBo)));
	}

	@Override
	public void removeAttributeById(int attributeId) {
		Attribute dbAttribute = attributeDao.getOne(attributeId);
		if (dbAttribute != null) {
			dbAttribute.setDeleted(Deleted.DEL_TRUE);
			dbAttribute.setDelTime(new Date());
			// 删除中间表
			dbAttribute.setProducts(null);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Attribute getById(int attributeId) {
		return attributeDao.getOne(attributeId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeVo getVoById(int attributeId) {
		return attributeConvert.toVo(attributeDao.getOne(attributeId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeListVo getListVoById(int attributeId) {
		return attributeConvert.toListVo(attributeDao.getOne(attributeId));
	}

	@Override
	public void batchAddAttribute(AttributeGroup attributeGroup, Collection<Attribute> attributes) {
		if (attributeGroup != null && CollectionUtils.isNotEmpty(attributes)) {
			attributes.forEach(tmpAttr -> {
				if (tmpAttr != null) {
					tmpAttr.setAttributeGroup(attributeGroup);
					tmpAttr.setAttrName(attributeGroup.getGroupName());
					tmpAttr.setShowMode(attributeGroup.getShowMode());
				}
			});
			attributeDao.saveAll(attributes);
		}
	}

	@Override
	public void batchUpdateAttribute(AttributeGroup attributeGroup, Collection<Attribute> attributes) {
		if (attributeGroup != null && CollectionUtils.isNotEmpty(attributes)) {
			// 查询该属性组属性集合
			Set<Attribute> dbAttributes = attributeDao.findByAttributeGroup_idAndDeleted(attributeGroup.getId(), Deleted.DEL_FALSE);
			// 需要保存的数据
			Set<Attribute> saveSets = attributes.stream().filter(e1 -> dbAttributes.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<Attribute> deleteSets = dbAttributes.stream().filter(e1 -> attributes.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要更新的数据
			Set<Attribute> updateSets = attributes.stream().filter(e1 -> dbAttributes.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 赋值AttributeGroup
			saveSets.forEach(e -> {
				e.setAttributeGroup(attributeGroup);
				e.setShowMode(attributeGroup.getShowMode());
				e.setAttrName(attributeGroup.getGroupName());
			});
			// 删除数据
			deleteSets.forEach(tmpAttr -> {
				if (tmpAttr != null) {
					tmpAttr.setDeleted(Deleted.DEL_TRUE);
					tmpAttr.setDelTime(new Date());
					// 删除中间表
					tmpAttr.setProducts(null);
				}
			});
			// 更新数据
			updateSets.forEach(e -> {
				if (e != null) {
					e.setAttributeGroup(attributeGroup);
					e.setAttrName(attributeGroup.getGroupName());
					e.setShowMode(attributeGroup.getShowMode());
					this.updateAttribute(e);
				}
			});
			// 保存数据
			attributeDao.saveAll(saveSets);

		}
	}

	@Override
	public void batchDeleteAttribute(Collection<Attribute> attributes) {
		if (attributes != null) {
			// 删除数据
			attributes.forEach(tmpAttr -> {
				if (tmpAttr != null) {
					tmpAttr.setDeleted(Deleted.DEL_TRUE);
					tmpAttr.setDelTime(new Date());
					// 删除中间表
					tmpAttr.setProducts(null);
				}
			});
		}
	}

	protected void initConvert() {
		this.attributeConvert = new EntityListVoBoSimpleConvert<Attribute, AttributeBo, AttributeVo, AttributeSimple, AttributeListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Attribute, AttributeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Attribute, AttributeVo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeVo attributeVo, Attribute attribute) {

					}
				};
			}

			@Override
			protected BeanConvert<Attribute, AttributeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Attribute, AttributeListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeListVo attributeListVo, Attribute attribute) {

					}
				};
			}

			@Override
			protected BeanConvert<Attribute, AttributeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Attribute, AttributeBo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeBo attributeBo, Attribute attribute) {

					}
				};
			}

			@Override
			protected BeanConvert<AttributeBo, Attribute> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeBo, Attribute>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Attribute, AttributeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Attribute, AttributeSimple>(beanConvertManager) {

				};
			}

			@Override
			protected BeanConvert<AttributeSimple, Attribute> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeSimple, Attribute>(beanConvertManager) {
					@Override
					public Attribute convert(AttributeSimple AttributeSimple) throws BeansException {
						return attributeDao.getOne(AttributeSimple.getId());
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
