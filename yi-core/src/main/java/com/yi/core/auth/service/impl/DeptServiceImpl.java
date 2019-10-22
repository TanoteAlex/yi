/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.auth.dao.DeptDao;
import com.yi.core.auth.domain.bo.DeptBo;
import com.yi.core.auth.domain.entity.Dept;
import com.yi.core.auth.domain.entity.Dept_;
import com.yi.core.auth.domain.simple.DeptSimple;
import com.yi.core.auth.domain.vo.DeptListVo;
import com.yi.core.auth.domain.vo.DeptVo;
import com.yi.core.auth.service.IDeptService;
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
 * 部门
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class DeptServiceImpl implements IDeptService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(DeptServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private DeptDao deptDao;

	private EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> deptConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Dept getDeptById(int deptId) {
		return deptDao.getOne(deptId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DeptVo getDeptVoById(int deptId) {
		return deptConvert.toVo(this.deptDao.getOne(deptId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DeptListVo getDeptListVoById(int deptId) {
		return deptConvert.toListVo(this.deptDao.getOne(deptId));
	}

	@Override
	public Dept addDept(Dept dept) {
		if (dept == null || StringUtils.isBlank(dept.getDeptName())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (StringUtils.isBlank(dept.getDeptNo())) {
			dept.setDeptNo(NumberGenerateUtils.generateDeptNo());
		}
		dept.setDeptName(dept.getDeptName().trim());
		// 校验名称
		int checkName = deptDao.countByParentAndDeptNameAndDeleted(dept.getParent(), dept.getDeptName(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("同级部门名称重复，deptName={}", dept.getDeptName());
			throw new BusinessException("同级部门名称不能重复");
		}
		return deptDao.save(dept);
	}

	@Override
	public DeptVo addDept(DeptBo deptBo) {
		return deptConvert.toVo(this.addDept(deptConvert.toEntity(deptBo)));
	}

	@Override
	public Dept updateDept(Dept dept) {
		if (dept == null || dept.getId() < 1 || StringUtils.isBlank(dept.getDeptName())) {
			throw new BusinessException("提交数据不能为空");
		}
		dept.setDeptName(dept.getDeptName().trim());
		// 校验名称
		int checkName = deptDao.countByParentAndDeptNameAndDeletedAndIdNot(dept.getParent(), dept.getDeptName(), Deleted.DEL_FALSE, dept.getId());
		if (checkName > 0) {
			LOG.error("同级部门名称重复，deptName={}", dept.getDeptName());
			throw new BusinessException("同级部门名称不能重复");
		}
		if (dept.getParent() != null) {
			if (dept.getId() == dept.getParent().getId()) {
				LOG.error("部门修改失败，选择自己节点为父类，deptId={}", dept.getId());
				throw new BusinessException("上级部门不能选择自己节点");
			}
		}
		Dept dbDept = deptDao.getOne(dept.getId());
		AttributeReplication.copying(dept, dbDept, Dept_.parent, Dept_.deptNo, Dept_.deptName, Dept_.description);
		return dbDept;
	}

	@Override
	public DeptVo updateDept(DeptBo deptBo) {
		return deptConvert.toVo(this.updateDept(deptConvert.toEntity(deptBo)));
	}

	@Override
	public void removeDeptById(int deptId) {
		Dept dbDept = deptDao.getOne(deptId);
		if (dbDept != null) {
			if (CollectionUtils.isNotEmpty(dbDept.getChildren())) {
				LOG.error("该部门有子部门，不能删除，deptId={}", deptId);
				throw new BusinessException("该部门有子部门，请处理相关数据后删除");
			}
			if (CollectionUtils.isNotEmpty(dbDept.getUsers())) {
				LOG.error("该部门已有用户使用，不能删除，deptId={}", deptId);
				throw new BusinessException("该部门已有用户使用，请处理相关数据后删除");
			}
			dbDept.setDeleted(Deleted.DEL_TRUE);
			dbDept.setDelTime(new Date());
		}
	}

	@Override
	public Page<Dept> query(Pagination<Dept> query) {
		query.setEntityClazz(Dept.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Dept_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Dept_.createTime)));
		}));
		Page<Dept> page = deptDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<DeptListVo> queryListVo(Pagination<Dept> query) {

		Page<Dept> pages = this.query(query);
		List<DeptListVo> vos = deptConvert.toListVos(pages.getContent());

		return new PageImpl<DeptListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	protected void initConvert() {
		this.deptConvert = new EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Dept, DeptVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Dept, DeptVo>(beanConvertManager) {
					@Override
					protected void postConvert(DeptVo DeptVo, Dept Dept) {

					}
				};
			}

			@Override
			protected BeanConvert<Dept, DeptListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Dept, DeptListVo>(beanConvertManager) {
					@Override
					protected void postConvert(DeptListVo DeptListVo, Dept Dept) {

					}
				};
			}

			@Override
			protected BeanConvert<Dept, DeptBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Dept, DeptBo>(beanConvertManager) {
					@Override
					protected void postConvert(DeptBo DeptBo, Dept Dept) {

					}
				};
			}

			@Override
			protected BeanConvert<DeptBo, Dept> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DeptBo, Dept>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Dept, DeptSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Dept, DeptSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<DeptSimple, Dept> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<DeptSimple, Dept>(beanConvertManager) {
					@Override
					public Dept convert(DeptSimple DeptSimple) throws BeansException {
						return deptDao.getOne(DeptSimple.getId());
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
	 * 获得dept 转换器
	 */
	@Override
	public EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> getDeptConvert() {
		return deptConvert;
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DeptListVo> getAll() {
		List<DeptListVo> depts = deptConvert.toListVos(deptDao.findAll().stream().filter(e -> e.getDeleted() == Deleted.DEL_FALSE).collect(Collectors.toList()));
		return depts;
	}

}
