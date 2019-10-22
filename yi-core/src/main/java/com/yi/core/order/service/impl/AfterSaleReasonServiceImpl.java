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
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.AfterSaleReasonDao;
import com.yi.core.order.domain.bo.AfterSaleReasonBo;
import com.yi.core.order.domain.entity.AfterSaleReason;
import com.yi.core.order.domain.entity.AfterSaleReason_;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrder_;
import com.yi.core.order.domain.simple.AfterSaleReasonSimple;
import com.yi.core.order.domain.vo.AfterSaleReasonListVo;
import com.yi.core.order.domain.vo.AfterSaleReasonVo;
import com.yi.core.order.service.IAfterSaleReasonService;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
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
public class AfterSaleReasonServiceImpl implements IAfterSaleReasonService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AfterSaleReasonServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AfterSaleReasonDao afterSaleReasonDao;

	private EntityListVoBoSimpleConvert<AfterSaleReason, AfterSaleReasonBo, AfterSaleReasonVo, AfterSaleReasonSimple, AfterSaleReasonListVo> afterSaleReasonConvert;

	/**
	 * 分页查询AfterSaleReason
	 **/
	@Override
	public Page<AfterSaleReason> query(Pagination<AfterSaleReason> query) {
		query.setEntityClazz(AfterSaleReason.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AfterSaleReason_.deleted), Deleted.DEL_FALSE)));

		}));
		Page<AfterSaleReason> page = afterSaleReasonDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: AfterSaleReason
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AfterSaleReasonListVo> queryListVo(Pagination<AfterSaleReason> query) {
		Page<AfterSaleReason> pages = this.query(query);
		List<AfterSaleReasonListVo> vos = afterSaleReasonConvert.toListVos(pages.getContent());
		return new PageImpl<AfterSaleReasonListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	// /**
	// * 分页查询: AfterSaleReason
	// **/
	// @Override
	// @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	// public Page<AfterSaleReasonListVo>
	// queryListVoForApp(Pagination<AfterSaleReason> query) {
	// query.setEntityClazz(AfterSaleReason.class);
	// query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
	// list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(AfterSaleReason_.deleted),
	// Deleted.DEL_FALSE)));
	// list1.add(criteriaBuilder.asc(root.get(AfterSaleReason_.sort)));
	// }));
	// Page<AfterSaleReason> pages = afterSaleReasonDao.findAll(query,
	// query.getPageRequest());
	// List<AfterSaleReasonListVo> vos =
	// afterSaleReasonConvert.toListVos(pages.getContent());
	// return new PageImpl<AfterSaleReasonListVo>(vos, query.getPageRequest(),
	// pages.getTotalElements());
	// }

	/**
	 * 查询售后原因
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AfterSaleReasonListVo> queryAllForApp() {
		return afterSaleReasonConvert.toListVos(afterSaleReasonDao.findByStateAndDeletedOrderBySortAsc(OrderEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE));
	}

	/**
	 * 创建AfterSaleReason
	 **/
	@Override
	public AfterSaleReason addAfterSaleReason(AfterSaleReason afterSaleReason) {
		return afterSaleReasonDao.save(afterSaleReason);
	}

	/**
	 * 创建AfterSaleReason
	 **/
	@Override
	public AfterSaleReasonListVo addAfterSaleReason(AfterSaleReasonBo afterSaleReasonBo) {
		return afterSaleReasonConvert.toListVo(afterSaleReasonDao.save(afterSaleReasonConvert.toEntity(afterSaleReasonBo)));
	}

	/**
	 * 更新AfterSaleReason
	 **/
	@Override
	public AfterSaleReason updateAfterSaleReason(AfterSaleReason afterSaleReason) {
		AfterSaleReason dbAfterSaleReason = afterSaleReasonDao.getOne(afterSaleReason.getId());
		AttributeReplication.copying(afterSaleReason, dbAfterSaleReason, AfterSaleReason_.afterSaleType, AfterSaleReason_.reason, AfterSaleReason_.sort, AfterSaleReason_.state);
		return dbAfterSaleReason;
	}

	/**
	 * 更新AfterSaleReason
	 **/
	@Override
	public AfterSaleReasonListVo updateAfterSaleReason(AfterSaleReasonBo afterSaleReasonBo) {
		AfterSaleReason dbAfterSaleReason = this.updateAfterSaleReason(afterSaleReasonConvert.toEntity(afterSaleReasonBo));
		return afterSaleReasonConvert.toListVo(dbAfterSaleReason);
	}

	/**
	 * 删除AfterSaleReason
	 **/
	@Override
	public void removeAfterSaleReasonById(int id) {
		AfterSaleReason dbAfterSaleReason = afterSaleReasonDao.getOne(id);
		if (dbAfterSaleReason != null) {
			dbAfterSaleReason.setDeleted(Deleted.DEL_TRUE);
			dbAfterSaleReason.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到AfterSaleReasonBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleReason getById(int id) {
		return this.afterSaleReasonDao.getOne(id);
	}

	/**
	 * 根据ID得到AfterSaleReasonBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleReasonBo getBoById(int id) {
		return afterSaleReasonConvert.toBo(this.afterSaleReasonDao.getOne(id));
	}

	/**
	 * 根据ID得到AfterSaleReasonVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleReasonVo getVoById(int id) {
		return afterSaleReasonConvert.toVo(this.afterSaleReasonDao.getOne(id));
	}

	/**
	 * 根据ID得到AfterSaleReasonListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AfterSaleReasonListVo getListVoById(int id) {
		return afterSaleReasonConvert.toListVo(this.afterSaleReasonDao.getOne(id));
	}

	protected void initConvert() {
		this.afterSaleReasonConvert = new EntityListVoBoSimpleConvert<AfterSaleReason, AfterSaleReasonBo, AfterSaleReasonVo, AfterSaleReasonSimple, AfterSaleReasonListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<AfterSaleReason, AfterSaleReasonVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReason, AfterSaleReasonVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleReasonVo afterSaleReasonVo, AfterSaleReason afterSaleReason) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleReason, AfterSaleReasonListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReason, AfterSaleReasonListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleReasonListVo afterSaleReasonListVo, AfterSaleReason afterSaleReason) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleReason, AfterSaleReasonBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReason, AfterSaleReasonBo>(beanConvertManager) {
					@Override
					protected void postConvert(AfterSaleReasonBo afterSaleReasonBo, AfterSaleReason afterSaleReason) {
					}
				};
			}

			@Override
			protected BeanConvert<AfterSaleReasonBo, AfterSaleReason> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReasonBo, AfterSaleReason>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleReason, AfterSaleReasonSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReason, AfterSaleReasonSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AfterSaleReasonSimple, AfterSaleReason> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AfterSaleReasonSimple, AfterSaleReason>(beanConvertManager) {
					@Override
					public AfterSaleReason convert(AfterSaleReasonSimple afterSaleReasonSimple) throws BeansException {
						return afterSaleReasonDao.getOne(afterSaleReasonSimple.getId());
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
