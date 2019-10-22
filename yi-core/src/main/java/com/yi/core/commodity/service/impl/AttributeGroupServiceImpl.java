/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.simple.AttributeSimple;
import com.yi.core.commodity.domain.simple.CategorySimple;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.commodity.dao.AttributeGroupDao;
import com.yi.core.commodity.domain.bo.AttributeGroupBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.AttributeGroup;
import com.yi.core.commodity.domain.entity.AttributeGroup_;
import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.domain.simple.AttributeGroupSimple;
import com.yi.core.commodity.domain.vo.AttributeGroupListVo;
import com.yi.core.commodity.domain.vo.AttributeGroupVo;
import com.yi.core.commodity.service.IAttributeGroupService;
import com.yi.core.commodity.service.IAttributeService;
import com.yi.core.commodity.service.ICategoryService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
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
public class AttributeGroupServiceImpl implements IAttributeGroupService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AttributeGroupServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AttributeGroupDao attributeGroupDao;

	@Resource
	private IAttributeService attributeService;

	@Resource
	private ICategoryService categoryService;

	private EntityListVoBoSimpleConvert<AttributeGroup, AttributeGroupBo, AttributeGroupVo, AttributeGroupSimple, AttributeGroupListVo> attributeGroupConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AttributeGroup> query(Pagination<AttributeGroup> query) {
		query.setEntityClazz(AttributeGroup.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AttributeGroup_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<AttributeGroup> page = attributeGroupDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AttributeGroupListVo> queryListVo(Pagination<AttributeGroup> query) {
		query.setEntityClazz(AttributeGroup.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AttributeGroup_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<AttributeGroup> pages = attributeGroupDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.getAttributes().forEach(tmp2 -> {
				tmp2.setProducts(null);
			});
		});
		List<AttributeGroupListVo> vos = attributeGroupConvert.toListVos(pages.getContent());
		return new PageImpl<AttributeGroupListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public AttributeGroup addAttributeGroup(AttributeGroup attributeGroup) {
		if (attributeGroup == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (attributeGroup.getCategory() == null || attributeGroup.getCategory().getId() < 1) {
			throw new BusinessException("商品分类不能为空");
		}
		if (CollectionUtils.isEmpty(attributeGroup.getAttributes())) {
			throw new BusinessException("商品属性不能为空");
		}
		// 校验重复- 同分类下
		attributeGroup.setGroupName(attributeGroup.getGroupName().trim());
		System.out.println("请求id= "+ attributeGroup.getCategory().getId()+"---------------"+attributeGroup.getGroupName().trim());
		int checkName = attributeGroupDao.countByGroupNameAndCategory_idAndDeleted(attributeGroup.getGroupName().trim(), attributeGroup.getCategory().getId(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			throw new BusinessException("属性名称不能重复");
		}
		attributeGroup.setGroupNo(NumberGenerateUtils.generateAttributeGroupNo());
		AttributeGroup dbAttributeGroup = attributeGroupDao.save(attributeGroup);
		// 批量保存属性
		attributeService.batchAddAttribute(dbAttributeGroup, attributeGroup.getAttributes());
		return dbAttributeGroup;
	}

	@Override
	public AttributeGroupVo addAttributeGroup(AttributeGroupBo attributeGroupBo) {
		return attributeGroupConvert.toVo(this.addAttributeGroup(attributeGroupConvert.toEntity(attributeGroupBo)));
	}

	@Override
	public AttributeGroup updateAttributeGroup(AttributeGroup attributeGroup) {
		if (attributeGroup == null || attributeGroup.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		if (attributeGroup.getCategory() == null || attributeGroup.getCategory().getId() < 1) {
			throw new BusinessException("商品分类不能为空");
		}
		if (CollectionUtils.isEmpty(attributeGroup.getAttributes())) {
			throw new BusinessException("商品属性不能为空");
		}
		// 校验重复- 同分类下
		attributeGroup.setGroupName(attributeGroup.getGroupName().trim());
		int checkName = attributeGroupDao.countByGroupNameAndCategory_idAndDeletedAndIdNot(attributeGroup.getGroupName().trim(), attributeGroup.getCategory().getId(),
				Deleted.DEL_FALSE, attributeGroup.getId());
		if (checkName > 0) {
			throw new BusinessException("属性名称不能重复");
		}
		AttributeGroup dbAttributeGroup = attributeGroupDao.getOne(attributeGroup.getId());
		AttributeReplication.copying(attributeGroup, dbAttributeGroup, AttributeGroup_.category, AttributeGroup_.groupName, AttributeGroup_.imgPath, AttributeGroup_.showMode,
				AttributeGroup_.sort, AttributeGroup_.remark);
		// 批量更新属性
		attributeService.batchUpdateAttribute(dbAttributeGroup, attributeGroup.getAttributes());
		return dbAttributeGroup;
	}

	@Override
	public AttributeGroupVo updateAttributeGroup(AttributeGroupBo attributeGroupBo) {
		return attributeGroupConvert.toVo(this.updateAttributeGroup(attributeGroupConvert.toEntity(attributeGroupBo)));
	}

	@Override
	public void removeAttributeGroupById(int attributeGroupId) {
		AttributeGroup dbAttributeGroup = attributeGroupDao.getOne(attributeGroupId);
		if (dbAttributeGroup != null) {
			dbAttributeGroup.setDeleted(Deleted.DEL_TRUE);
			dbAttributeGroup.setDelTime(new Date());
			// 删除属性
			attributeService.batchDeleteAttribute(dbAttributeGroup.getAttributes());
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeGroup getById(int attributeGroupId) {
		if (attributeGroupDao.existsById(attributeGroupId)) {
			return attributeGroupDao.getOne(attributeGroupId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeGroupVo getVoById(int attributeGroupId) {
		AttributeGroup dbAttributeGroup = this.getById(attributeGroupId);
		if (dbAttributeGroup != null) {
			dbAttributeGroup.getAttributes().forEach(tmp -> {
				tmp.setProducts(null);
			});
			return attributeGroupConvert.toVo(dbAttributeGroup);
		}
		return null;

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AttributeGroupListVo getListVoById(int attributeGroupId) {
		return attributeGroupConvert.toListVo(this.getById(attributeGroupId));
	}

	@Override
	public List<AttributeGroup> batchAddAttributeGroup(Collection<AttributeGroupBo> attributeGroups) {
		List<AttributeGroup> dbAttributeGroups = new ArrayList<AttributeGroup>(0);
		if (CollectionUtils.isNotEmpty(attributeGroups)) {
			for (AttributeGroupBo attributeGroupBo : attributeGroups) {
				if (attributeGroupBo != null) {
					AttributeGroup dbAttributeGroup = this.addAttributeGroup(attributeGroupConvert.toEntity(attributeGroupBo));
					dbAttributeGroups.add(dbAttributeGroup);
				}
			}
		}
		return dbAttributeGroups;
	}

	protected void initConvert() {
		this.attributeGroupConvert = new EntityListVoBoSimpleConvert<AttributeGroup, AttributeGroupBo, AttributeGroupVo, AttributeGroupSimple, AttributeGroupListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<AttributeGroup, AttributeGroupVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroup, AttributeGroupVo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeGroupVo attributeGroupVo, AttributeGroup attributeGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<AttributeGroup, AttributeGroupListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroup, AttributeGroupListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeGroupListVo attributeGroupListVo, AttributeGroup attributeGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<AttributeGroup, AttributeGroupBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroup, AttributeGroupBo>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeGroupBo attributeGroupBo, AttributeGroup attributeGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<AttributeGroupBo, AttributeGroup> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroupBo, AttributeGroup>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AttributeGroup, AttributeGroupSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroup, AttributeGroupSimple>(beanConvertManager) {
					@Override
					protected void postConvert(AttributeGroupSimple attributeGroupBo, AttributeGroup attributeGroup) {

					}
				};
			}

			@Override
			protected BeanConvert<AttributeGroupSimple, AttributeGroup> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AttributeGroupSimple, AttributeGroup>(beanConvertManager) {
					@Override
					public AttributeGroup convert(AttributeGroupSimple attributeGroupSimple) throws BeansException {
						return attributeGroupDao.getOne(attributeGroupSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 该商品分类属性组
	 * 
	 * @param categoryId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AttributeGroupListVo> getAttrGroupsByCategory(int categoryId) {
		Category dbCategory = categoryService.getCategoryById(categoryId);
		List<AttributeGroup> attributeGroups = attributeGroupDao.findByCategoryIdAndDeleted(dbCategory.getId(), Deleted.DEL_FALSE);
		// 如果当前 分类的属性组为空 查询父级的属性组集合
		if (CollectionUtils.isEmpty(attributeGroups)) {
			if (dbCategory != null && dbCategory.getParent() != null) {
				attributeGroups = attributeGroupDao.findByCategoryIdAndDeleted(dbCategory.getParent().getId(), Deleted.DEL_FALSE);
			}
		}
		List<AttributeGroupListVo> attributeGroupListVos = new ArrayList<>();

		if (attributeGroups!=null&&attributeGroups.size()>0){
			for (AttributeGroup att:attributeGroups) {
				AttributeGroupListVo attributeGroupListVo = new AttributeGroupListVo();
				CategorySimple categorySimple = new CategorySimple();
				BeanUtils.copyProperties(att,attributeGroupListVo);  if (att.getCategory()!=null){ BeanUtils.copyProperties(att.getCategory(),categorySimple); attributeGroupListVo.setCategory(categorySimple);}
            if (att.getAttributes()!=null&&att.getAttributes().size()>0) {
				List<Attribute> attributes = att.getAttributes();
				List<AttributeSimple> attributeSimples = new ArrayList<>();
				for (Attribute attribute:attributes) {
					AttributeSimple attributeSimple = new AttributeSimple();
					if (attribute.getAttributeGroup()!=null){	attribute.setAttributeGroupId(attribute.getAttributeGroup().getId());}
				BeanUtils.copyProperties(attribute,attributeSimple);  attributeSimples.add(attributeSimple);
				}
				attributeGroupListVo.setAttributes(attributeSimples);
			}
			attributeGroupListVos.add(attributeGroupListVo);
			}
		}else {
			attributeGroupListVos = attributeGroupConvert.toListVos(attributeGroups);

		}



		return attributeGroupListVos;
	}

	/**
	 * 商品增删时 保存商品属性
	 * 
	 * @param category
	 * @param attributeGroups
	 * @return
	 */
	@Override
	public Map<String, Attribute> addAttributeGroupByCommodity(Category category, Collection<AttributeGroup> attributeGroups) {
		if (category != null && CollectionUtils.isNotEmpty(attributeGroups)) {
			// 属性值 属性
			Map<String, Attribute> attributeMap = new HashMap<>();
			for (AttributeGroup tmpGroup : attributeGroups) {
				if (tmpGroup != null && CollectionUtils.isNotEmpty(tmpGroup.getAttributes())) {
					tmpGroup.setCategory(category);
					if (tmpGroup.getId() < 1) {
						// 保存 属性组数据
						AttributeGroup dbAttributeGroup = this.addAttributeGroup(tmpGroup);
						tmpGroup.setId(dbAttributeGroup.getId());
						dbAttributeGroup.getAttributes().forEach(tmpAttr -> {
							if (tmpAttr != null) {
								attributeMap.put(tmpAttr.getAttrValue(), tmpAttr);
							}
						});
					} else {
						tmpGroup.getAttributes().forEach(tmpAttr -> {
							if (tmpAttr != null) {
								tmpAttr.setAttributeGroup(tmpGroup);
								tmpAttr.setAttrName(tmpGroup.getGroupName());
								tmpAttr.setShowMode(tmpGroup.getShowMode());
								if (tmpAttr.getId() < 1) {
									// 保存 属性数据
									Attribute dbAttribute = attributeService.addAttribute(tmpAttr);
									tmpAttr.setId(dbAttribute.getId());
								}
								attributeMap.put(tmpAttr.getAttrValue(), tmpAttr);
							}
						});
					}
				}
			}
			return attributeMap;
		}
		return null;
	}

}
