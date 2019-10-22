/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.Date;
import java.util.List;

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

import com.yi.core.commodity.dao.CategoryDao;
import com.yi.core.commodity.domain.bo.CategoryBo;
import com.yi.core.commodity.domain.entity.Category;
import com.yi.core.commodity.domain.entity.Category_;
import com.yi.core.commodity.domain.simple.CategorySimple;
import com.yi.core.commodity.domain.vo.CategoryListVo;
import com.yi.core.commodity.domain.vo.CategoryVo;
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
 * 商品分类
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CategoryDao categoryDao;

	private EntityListVoBoSimpleConvert<Category, CategoryBo, CategoryVo, CategorySimple, CategoryListVo> categoryConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Category getCategoryById(int categoryId) {
		return categoryDao.getOne(categoryId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryVo getCategoryVoById(int categoryId) {
		return categoryConvert.toVo(this.categoryDao.getOne(categoryId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryListVo getCategoryListVoById(int categoryId) {
		return categoryConvert.toListVo(this.categoryDao.getOne(categoryId));
	}

	@Override
	public Category addCategory(Category category) {
		if (category == null) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		if (category.getParent() == null || category.getParent().getId() == 0) {
			category.setParent(null);
		}
		// 校验名称
		int checkName = categoryDao.countByParentAndCategoryNameAndDeleted(category.getParent(), category.getCategoryName().trim(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			throw new BusinessException("同级商品分类名称不能重复");
		}
		category.setCategoryName(category.getCategoryName().trim());
		category.setCategoryNo(NumberGenerateUtils.generateCategoryNo());
		return categoryDao.save(category);
	}

	@Override
	public CategoryVo addCategory(CategoryBo categoryBo) {
		return categoryConvert.toVo(this.addCategory(categoryConvert.toEntity(categoryBo)));
	}

	@Override
	public Category updateCategory(Category category) {
		if (category == null || category.getId() < 1) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		category.setCategoryName(category.getCategoryName().trim());
		if (category.getParent() != null && category.getParent().getId() > 0) {
			// 上级分类不能选择自己
			if (category.getParent().getId() == category.getId()) {
				LOG.error("分类修改失败，上级分类不能选择自己节点，categoryId={}", category.getId());
				throw new BusinessException("上级分类不能选择自己");
			}
		} else {
			category.setParent(null);
		}
		// 校验名称
		int checkName = categoryDao.countByParentAndCategoryNameAndDeletedAndIdNot(category.getParent(), category.getCategoryName().trim(), Deleted.DEL_FALSE, category.getId());
		if (checkName > 0) {
			throw new BusinessException("同级商品分类名称不能重复");
		}
		category.setCategoryName(category.getCategoryName().trim());
		Category dbCategory = categoryDao.getOne(category.getId());
		AttributeReplication.copying(category, dbCategory, Category_.categoryNo, Category_.categoryName, Category_.imgPath, Category_.sort, Category_.remark);
		if (category.getParent() != null) {
			dbCategory.setParent(category.getParent());
		}
		return dbCategory;
	}

	@Override
	public CategoryVo updateCategory(CategoryBo categoryBo) {
		return categoryConvert.toVo(this.updateCategory(categoryConvert.toEntity(categoryBo)));
	}

	@Override
	public void removeCategoryById(int categoryId) {
		Category dbCategory = categoryDao.getOne(categoryId);
		if (dbCategory != null) {
			if (CollectionUtils.isNotEmpty(dbCategory.getChildren())) {
				throw new BusinessException("该分类有子分类，请处理相关数据后删除");
			}
			if (CollectionUtils.isNotEmpty(dbCategory.getCommodities())) {
				throw new BusinessException("该分类下有商品，请处理相关数据后删除");
			}
			dbCategory.setDeleted(Deleted.DEL_TRUE);
			dbCategory.setDelTime(new Date());
		}
	}

	@Override
	public Page<Category> query(Pagination<Category> query) {
		query.setEntityClazz(Category.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// list.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get(Category_.parent))));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Category_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Category> page = categoryDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CategoryListVo> queryListVo(Pagination<Category> query) {
		Page<Category> pages = this.query(query);
		List<CategoryListVo> vos = categoryConvert.toListVos(pages.getContent());
		return new PageImpl<CategoryListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	public List<CategoryVo> getCommodityCategory() {
		return categoryConvert.toVos(categoryDao.findByParentIsNullAndDeleted(Deleted.DEL_FALSE));
	}

	@Override
	public List<CategoryVo> getAll() {
		// return categoryConvert.toVos(categoryDao.findByParentIsNull());
		return categoryConvert.toVos(categoryDao.findByDeletedOrderBySortAsc(Deleted.DEL_FALSE));
	}

	protected void initConvert() {
		this.categoryConvert = new EntityListVoBoSimpleConvert<Category, CategoryBo, CategoryVo, CategorySimple, CategoryListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Category, CategoryVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Category, CategoryVo>(beanConvertManager) {
					@Override
					protected void postConvert(CategoryVo categoryVo, Category category) {
						if (category.getParent() != null) {
							categoryVo.setParentId(category.getParent().getId());
							categoryVo.setParentName(category.getParent().getCategoryName());
						}
					}
				};
			}

			@Override
			protected BeanConvert<Category, CategoryListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Category, CategoryListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CategoryListVo categoryListVo, Category category) {
						if (category.getParent() != null) {
							categoryListVo.setParentId(category.getParent().getId());
							categoryListVo.setParentName(category.getParent().getCategoryName());
						}
					}
				};
			}

			@Override
			protected BeanConvert<Category, CategoryBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Category, CategoryBo>(beanConvertManager) {
					@Override
					protected void postConvert(CategoryBo categoryBo, Category category) {

					}
				};
			}

			@Override
			protected BeanConvert<CategoryBo, Category> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CategoryBo, Category>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Category, CategorySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Category, CategorySimple>(beanConvertManager) {
					@Override
					protected void postConvert(CategorySimple categorySimple, Category category) {

					}
				};
			}

			@Override
			protected BeanConvert<CategorySimple, Category> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CategorySimple, Category>(beanConvertManager) {
					@Override
					public Category convert(CategorySimple categorySimple) throws BeansException {
						return categoryDao.getOne(categorySimple.getId());
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
