/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;

import com.yi.core.commodity.domain.entity.Commodity_;
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

import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.OperateCategoryDao;
import com.yi.core.cms.domain.bo.OperateCategoryBo;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.cms.domain.entity.OperateCategory_;
import com.yi.core.cms.domain.simple.OperateCategorySimple;
import com.yi.core.cms.domain.vo.OperateCategoryListVo;
import com.yi.core.cms.domain.vo.OperateCategoryVo;
import com.yi.core.cms.service.IOperateCategoryService;
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
 * 运营分类
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class OperateCategoryServiceImpl implements IOperateCategoryService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(OperateCategoryServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private OperateCategoryDao operateCategoryDao;

	private EntityListVoBoSimpleConvert<OperateCategory, OperateCategoryBo, OperateCategoryVo, OperateCategorySimple, OperateCategoryListVo> operateCategoryConvert;

	/**
	 * 分页查询OperateCategory
	 **/
	@Override
	public Page<OperateCategory> query(Pagination<OperateCategory> query) {
		query.setEntityClazz(OperateCategory.class);
		Page<OperateCategory> page = operateCategoryDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: OperateCategory
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<OperateCategoryListVo> queryListVo(Pagination<OperateCategory> query) {
		query.setEntityClazz(OperateCategory.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(OperateCategory_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(OperateCategory_.sort)));
			list1.add(criteriaBuilder.desc(root.get(OperateCategory_.createTime)));
		}));
		Page<OperateCategory> pages = operateCategoryDao.findAll(query, query.getPageRequest());
		List<OperateCategoryListVo> vos = operateCategoryConvert.toListVos(pages.getContent());
		return new PageImpl<OperateCategoryListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	/**
	 * 创建OperateCategory
	 **/
	@Override
	public OperateCategoryListVo addOperateCategory(OperateCategoryBo operateCategoryBo) {
		if (operateCategoryBo == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (operateCategoryBo.getParent() == null || operateCategoryBo.getParent().getId() == 0) {
			operateCategoryBo.setParent(null);
		}
		// 校验名称
		int checkName = operateCategoryDao.countByParentAndCategoryNameAndDeleted(operateCategoryConvert.toEntity(operateCategoryBo.getParent()),
				operateCategoryBo.getCategoryName().trim(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			throw new BusinessException("同级运营分类名称不能重复");
		}
		operateCategoryBo.setCategoryNo(NumberGenerateUtils.generateCategoryNo());
		operateCategoryBo.setCategoryName(operateCategoryBo.getCategoryName().trim());
		return operateCategoryConvert.toListVo(operateCategoryDao.save(operateCategoryConvert.toEntity(operateCategoryBo)));
	}

	/**
	 * 更新OperateCategory
	 **/
	@Override
	public OperateCategoryVo updateOperateCategory(OperateCategoryBo operateCategoryBo) {
		if (operateCategoryBo == null) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}

		if (operateCategoryBo.getParent() != null && operateCategoryBo.getParent().getId() > 0) {
			// 上级分类不能选择自己
			if (operateCategoryBo.getParent().getId() == operateCategoryBo.getId()) {
				LOG.error("分类修改失败，上级分类不能选择自己节点，categoryId={}", operateCategoryBo.getId());
				throw new BusinessException("上级分类不能选择自己");
			}
		} else {
			operateCategoryBo.setParent(null);
		}
		// 校验名称
		int checkName = operateCategoryDao.countByParentAndCategoryNameAndDeletedAndIdNot(operateCategoryConvert.toEntity(operateCategoryBo.getParent()),
				operateCategoryBo.getCategoryName().trim(), Deleted.DEL_FALSE, operateCategoryBo.getId());
		if (checkName > 0) {
			throw new BusinessException("同级运营分类名称不能重复");
		}
		operateCategoryBo.setCategoryName(operateCategoryBo.getCategoryName().trim());
		OperateCategory dbOperateCategory = operateCategoryDao.getOne(operateCategoryBo.getId());
		AttributeReplication.copying(operateCategoryConvert.toEntity(operateCategoryBo), dbOperateCategory, OperateCategory_.parent, OperateCategory_.categoryNo,
				OperateCategory_.showName, OperateCategory_.linkType, OperateCategory_.linkId, OperateCategory_.categoryName, OperateCategory_.state, OperateCategory_.imgPath,
				OperateCategory_.sort, OperateCategory_.remark);
		dbOperateCategory.setCommodities(operateCategoryConvert.toEntity(operateCategoryBo).getCommodities());
		return operateCategoryConvert.toVo(dbOperateCategory);
	}

	/**
	 * 删除OperateCategory
	 **/
	@Override
	public void removeOperateCategoryById(int id) {
		OperateCategory dbOperateCategory = operateCategoryDao.getOne(id);
		if (dbOperateCategory != null) {
			if (CollectionUtils.isNotEmpty(dbOperateCategory.getChildren())) {
				throw new BusinessException("该分类有子分类，请处理相关数据后删除");
			}
			dbOperateCategory.setDeleted(Deleted.DEL_TRUE);
			dbOperateCategory.setDelTime(new Date());
			// 删除 商品中间表数据
			dbOperateCategory.setCommodities(null);
		}
	}

	/**
	 * 根据ID得到OperateCategory
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OperateCategory getOperateCategoryById(int id) {
		return this.operateCategoryDao.getOne(id);
	}

	/**
	 * 根据ID得到OperateCategory
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OperateCategoryVo getOperateCategoryVoById(int id) {

		return operateCategoryConvert.toVo(this.operateCategoryDao.getOne(id));
	}

	/**
	 * 根据ID得到OperateCategoryListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OperateCategoryVo getListVoById(int id) {
		return operateCategoryConvert.toVo(this.operateCategoryDao.getOne(id));
	}

	protected void initConvert() {
		this.operateCategoryConvert = new EntityListVoBoSimpleConvert<OperateCategory, OperateCategoryBo, OperateCategoryVo, OperateCategorySimple, OperateCategoryListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<OperateCategory, OperateCategoryVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategory, OperateCategoryVo>(beanConvertManager) {
					@Override
					protected void postConvert(OperateCategoryVo OperateCategoryVo, OperateCategory OperateCategory) {

					}
				};
			}

			@Override
			protected BeanConvert<OperateCategory, OperateCategoryListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategory, OperateCategoryListVo>(beanConvertManager) {
					@Override
					protected void postConvert(OperateCategoryListVo OperateCategoryListVo, OperateCategory OperateCategory) {

					}
				};
			}

			@Override
			protected BeanConvert<OperateCategory, OperateCategoryBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategory, OperateCategoryBo>(beanConvertManager) {
					@Override
					protected void postConvert(OperateCategoryBo OperateCategoryBo, OperateCategory OperateCategory) {

					}
				};
			}

			@Override
			protected BeanConvert<OperateCategoryBo, OperateCategory> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategoryBo, OperateCategory>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OperateCategory, OperateCategorySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategory, OperateCategorySimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<OperateCategorySimple, OperateCategory> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<OperateCategorySimple, OperateCategory>(beanConvertManager) {
					@Override
					public OperateCategory convert(OperateCategorySimple OperateCategorySimple) throws BeansException {
						return operateCategoryDao.getOne(OperateCategorySimple.getId());
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
	public List<OperateCategoryListVo> getAll() {
		List<OperateCategory> dbOperateCategories = operateCategoryDao.findByStateAndDeleted(CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
		if(CollectionUtils.isNotEmpty(dbOperateCategories)){
			dbOperateCategories.stream().forEach(operateCategory -> {
				operateCategory.setCommodities(null);
			});
		}
		return operateCategoryConvert.toListVos(dbOperateCategories);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OperateCategoryListVo> getOperateCategoryListVosForApp() {
		return operateCategoryConvert.toListVos(operateCategoryDao.findByParentAndDeletedOrderBySortAsc(null, Deleted.DEL_FALSE));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OperateCategory> getOperateCategoriesByIds(List<Integer> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			return operateCategoryDao.findAllById(ids);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OperateCategory> getOperateCategoriesByCommodityId(Integer id) {
		return operateCategoryDao.findAll((root, query, cb) -> {
			return cb.equal(root.join(OperateCategory_.commodities, JoinType.LEFT).get(Commodity_.id),id);
		});
	}
}
