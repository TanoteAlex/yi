/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service.impl;

import java.util.List;

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

import com.yi.core.auth.dao.RescDao;
import com.yi.core.auth.dao.RoleDao;
import com.yi.core.auth.domain.bo.RescBo;
import com.yi.core.auth.domain.entity.Resc;
import com.yi.core.auth.domain.entity.Resc_;
import com.yi.core.auth.domain.simple.RescSimple;
import com.yi.core.auth.domain.vo.RescListVo;
import com.yi.core.auth.domain.vo.RescVo;
import com.yi.core.auth.service.IRescService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 资源
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RescServiceImpl implements IRescService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RescServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RescDao rescDao;

	@Resource
	private RoleDao roleDao;

	private EntityListVoBoSimpleConvert<Resc, RescBo, RescVo, RescSimple, RescListVo> rescConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Resc getRescById(int rescId) {
		return rescDao.getOne(rescId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RescVo getRescVoById(int rescId) {
		return rescConvert.toVo(this.rescDao.getOne(rescId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RescListVo getRescListVoById(int rescId) {
		return rescConvert.toListVo(this.rescDao.getOne(rescId));
	}

	@Override
	public Resc addResc(Resc resc) {
		if (resc == null || StringUtils.isAnyBlank(resc.getName(), resc.getUrl())) {
			throw new BusinessException("提交数据不能为空");
		}
		resc.setName(resc.getName().trim());
		// 校验名称
		int checkName = rescDao.countByParentAndNameAndDeleted(resc.getParent(), resc.getName(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("同级资源名称重复，name={}", resc.getName());
			throw new BusinessException("同级资源名称不能重复");
		}
		return rescDao.save(resc);
	}

	@Override
	public RescVo addResc(RescBo rescBo) {
		return rescConvert.toVo(this.addResc(rescConvert.toEntity(rescBo)));
	}

	@Override
	public Resc updateResc(Resc resc) {
		if (resc == null || resc.getId() < 1 || StringUtils.isAnyBlank(resc.getName(), resc.getUrl())) {
			throw new BusinessException("提交数据不能为空");
		}
		resc.setName(resc.getName().trim());
		// 校验名称
		int checkName = rescDao.countByParentAndNameAndDeletedAndIdNot(resc.getParent(), resc.getName(), Deleted.DEL_FALSE, resc.getId());
		if (checkName > 0) {
			LOG.error("同级资源名称重复，name={}", resc.getName());
			throw new BusinessException("同级资源名称不能重复");
		}
		if (resc.getParent() != null) {
			if (resc.getId() == resc.getParent().getId()) {
				LOG.error("上级资源不能选择自己节点，rescId={}", resc.getId());
				throw new BusinessException("上级资源不能选择自己节点");
			}
		}
		Resc dbResc = rescDao.getOne(resc.getId());
		AttributeReplication.copying(resc, dbResc, Resc_.parent, Resc_.code, Resc_.name, Resc_.url);
		return dbResc;
	}

	@Override
	public RescVo updateResc(RescBo rescBo) {
		return rescConvert.toVo(this.updateResc(rescConvert.toEntity(rescBo)));
	}

	@Override
	public void removeRescById(int rescId) {
		Resc dbResc = rescDao.getOne(rescId);
		if (dbResc != null) {
			if (CollectionUtils.isNotEmpty(dbResc.getChildren())) {
				LOG.error("该资源有子资源，不能删除，rescId={}", rescId);
				throw new BusinessException("该资源有子资源，请处理相关数据后删除");
			}
			// 自动删除 资源角色中间表数据
			dbResc.setRoles(null);
		}
	}

	@Override
	public Page<Resc> query(Pagination<Resc> query) {
		query.setEntityClazz(Resc.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.isNull(root.get(Resc_.parent))));
		}));
		Page<Resc> page = rescDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RescListVo> queryListVo(Pagination<Resc> query) {

		Page<Resc> pages = this.query(query);

		List<RescListVo> vos = rescConvert.toListVos(pages.getContent());
		return new PageImpl<RescListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.rescConvert = new EntityListVoBoSimpleConvert<Resc, RescBo, RescVo, RescSimple, RescListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Resc, RescVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Resc, RescVo>(beanConvertManager) {
					@Override
					protected void postConvert(RescVo RescVo, Resc Resc) {

					}
				};
			}

			@Override
			protected BeanConvert<Resc, RescListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Resc, RescListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RescListVo RescListVo, Resc Resc) {

					}
				};
			}

			@Override
			protected BeanConvert<Resc, RescBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Resc, RescBo>(beanConvertManager) {
					@Override
					protected void postConvert(RescBo RescBo, Resc Resc) {

					}
				};
			}

			@Override
			protected BeanConvert<RescBo, Resc> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RescBo, Resc>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Resc, RescSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Resc, RescSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RescSimple, Resc> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RescSimple, Resc>(beanConvertManager) {
					@Override
					public Resc convert(RescSimple RescSimple) throws BeansException {
						return rescDao.getOne(RescSimple.getId());
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
	public List<RescListVo> getRescTree() {
		return rescConvert.toListVos(rescDao.findByParentAndDeleted(null, Deleted.DEL_FALSE));
	}
}
