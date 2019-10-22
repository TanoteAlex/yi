/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.basic.service.impl;

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
import org.springframework.validation.annotation.Validated;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.dao.IntegralCashDao;
import com.yi.core.basic.domain.bo.IntegralCashBo;
import com.yi.core.basic.domain.entity.IntegralCash;
import com.yi.core.basic.domain.entity.IntegralCash_;
import com.yi.core.basic.domain.simple.IntegralCashSimple;
import com.yi.core.basic.domain.vo.IntegralCashListVo;
import com.yi.core.basic.domain.vo.IntegralCashVo;
import com.yi.core.basic.service.IIntegralCashService;
import com.yi.core.common.Deleted;
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
public class IntegralCashServiceImpl implements IIntegralCashService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(IntegralCashServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private IntegralCashDao integralCashDao;

	private EntityListVoBoSimpleConvert<IntegralCash, IntegralCashBo, IntegralCashVo, IntegralCashSimple, IntegralCashListVo> integralCashConvert;

	/**
	 * 分页查询IntegralCash
	 **/
	@Override
	public Page<IntegralCash> query(Pagination<IntegralCash> query) {
		query.setEntityClazz(IntegralCash.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(IntegralCash_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(IntegralCash_.createTime)));
		}));
		Page<IntegralCash> page = integralCashDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: IntegralCash
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<IntegralCashListVo> queryListVo(Pagination<IntegralCash> query) {
		query.setEntityClazz(IntegralCash.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(IntegralCash_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(IntegralCash_.createTime)));
		}));
		Page<IntegralCash> pages = integralCashDao.findAll(query, query.getPageRequest());
		List<IntegralCashListVo> vos = integralCashConvert.toListVos(pages.getContent());
		return new PageImpl<IntegralCashListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: IntegralCash
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<IntegralCashListVo> queryListVoForApp(Pagination<IntegralCash> query) {
		query.setEntityClazz(IntegralCash.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(IntegralCash_.deleted), Deleted.DEL_FALSE)));
			// list1.add(criteriaBuilder.desc(root.get(IntegralCash_.createTime)));
		}));
		Page<IntegralCash> pages = integralCashDao.findAll(query, query.getPageRequest());
		List<IntegralCashListVo> vos = integralCashConvert.toListVos(pages.getContent());
		return new PageImpl<IntegralCashListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建IntegralCash
	 **/
	@Override
	public IntegralCash addIntegralCash(@Validated IntegralCash integralCash) {
		if (integralCash.getState() == null) {
			integralCash.setState(BasicEnum.STATE_ENABLE.getCode());
		}
		return integralCashDao.save(integralCash);
	}

	/**
	 * 创建IntegralCash
	 **/
	@Override
	public IntegralCashListVo addIntegralCash(IntegralCashBo integralCashBo) {
		integralCashBo.setCreateTime(new Date());
		return integralCashConvert.toListVo(this.addIntegralCash(integralCashConvert.toEntity(integralCashBo)));
	}

	/**
	 * 更新IntegralCash
	 **/
	@Override
	public IntegralCash updateIntegralCash(IntegralCash integralCash) {
		IntegralCash dbIntegralCash = integralCashDao.getOne(integralCash.getId());

		AttributeReplication.copying(integralCash, dbIntegralCash, IntegralCash_.integral, IntegralCash_.cash,IntegralCash_.limitCash, IntegralCash_.state);
		return dbIntegralCash;
	}

	/**
	 * 更新IntegralCash
	 **/
	@Override
	public IntegralCashListVo updateIntegralCash(IntegralCashBo integralCashBo) {
		IntegralCash dbIntegralCash = this.updateIntegralCash(integralCashConvert.toEntity(integralCashBo));
		return integralCashConvert.toListVo(dbIntegralCash);
	}

	/**
	 * 删除IntegralCash
	 **/
	@Override
	public void removeIntegralCashById(java.lang.Integer id) {
		if (integralCashDao.existsById(id)) {
			IntegralCash dbIntegralCash = this.getIntegralCashById(id);
			dbIntegralCash.setDeleted(Deleted.DEL_TRUE);
			dbIntegralCash.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到IntegralCash
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCash getIntegralCashById(java.lang.Integer id) {
		return this.integralCashDao.getOne(id);
	}

	/**
	 * 根据ID得到IntegralCashBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCashBo getIntegralCashBoById(java.lang.Integer id) {
		return integralCashConvert.toBo(this.integralCashDao.getOne(id));
	}

	/**
	 * 根据ID得到IntegralCashVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCashVo getIntegralCashVoById(java.lang.Integer id) {
		return integralCashConvert.toVo(this.integralCashDao.getOne(id));
	}

	/**
	 * 根据ID得到IntegralCashListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCashListVo getListVoById(java.lang.Integer id) {
		return integralCashConvert.toListVo(this.integralCashDao.getOne(id));
	}

	/**
	 * 获取最新的积分抵现规则
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCash getLatestIntegralCash() {
		return integralCashDao.findFirstByStateAndDeletedOrderByCreateTimeDesc(BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 获取最新的积分抵现规则
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IntegralCashSimple getLatestIntegralCashSimple() {
		return integralCashConvert.toSimple(integralCashDao.findFirstByStateAndDeletedOrderByCreateTimeDesc(BasicEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE));
	}

	protected void initConvert() {
		this.integralCashConvert = new EntityListVoBoSimpleConvert<IntegralCash, IntegralCashBo, IntegralCashVo, IntegralCashSimple, IntegralCashListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<IntegralCash, IntegralCashVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCash, IntegralCashVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralCashVo integralCashVo, IntegralCash integralCash) {
					}
				};
			}

			@Override
			protected BeanConvert<IntegralCash, IntegralCashListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCash, IntegralCashListVo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralCashListVo integralCashListVo, IntegralCash integralCash) {
					}
				};
			}

			@Override
			protected BeanConvert<IntegralCash, IntegralCashBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCash, IntegralCashBo>(beanConvertManager) {
					@Override
					protected void postConvert(IntegralCashBo integralCashBo, IntegralCash integralCash) {
					}
				};
			}

			@Override
			protected BeanConvert<IntegralCashBo, IntegralCash> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCashBo, IntegralCash>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralCash, IntegralCashSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCash, IntegralCashSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<IntegralCashSimple, IntegralCash> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<IntegralCashSimple, IntegralCash>(beanConvertManager) {
					@Override
					public IntegralCash convert(IntegralCashSimple integralCashSimple) throws BeansException {
						return integralCashDao.getOne(integralCashSimple.getId());
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
