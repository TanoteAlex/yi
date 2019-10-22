/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.gift.service.impl;

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
import com.yi.core.gift.dao.BlessWordDao;
import com.yi.core.gift.domain.bo.BlessWordBo;
import com.yi.core.gift.domain.entity.BlessWord;
import com.yi.core.gift.domain.entity.BlessWord_;
import com.yi.core.gift.domain.simple.BlessWordSimple;
import com.yi.core.gift.domain.vo.BlessWordListVo;
import com.yi.core.gift.domain.vo.BlessWordVo;
import com.yi.core.gift.service.IBlessWordService;
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
public class BlessWordServiceImpl implements IBlessWordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BlessWordServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private BlessWordDao blessWordDao;

	private EntityListVoBoSimpleConvert<BlessWord, BlessWordBo, BlessWordVo, BlessWordSimple, BlessWordListVo> blessWordConvert;

	/**
	 * 分页查询BlessWord
	 **/
	@Override
	public Page<BlessWord> query(Pagination<BlessWord> query) {
		query.setEntityClazz(BlessWord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(BlessWord_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<BlessWord> page = blessWordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: BlessWord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlessWordListVo> queryListVo(Pagination<BlessWord> query) {
		Page<BlessWord> pages = this.query(query);
		List<BlessWordListVo> vos = blessWordConvert.toListVos(pages.getContent());
		return new PageImpl<BlessWordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建BlessWord
	 **/
	@Override
	public BlessWord addBlessWord(BlessWord blessWord) {
		return blessWordDao.save(blessWord);
	}

	/**
	 * 创建BlessWord
	 **/
	@Override
	public BlessWordVo addBlessWord(BlessWordBo blessWordBo) {
		return blessWordConvert.toVo(blessWordDao.save(blessWordConvert.toEntity(blessWordBo)));
	}

	/**
	 * 更新BlessWord
	 **/
	@Override
	public BlessWord updateBlessWord(BlessWord blessWord) {
		BlessWord dbBlessWord = blessWordDao.getOne(blessWord.getId());
		AttributeReplication.copying(blessWord, dbBlessWord, BlessWord_.guid, BlessWord_.word, BlessWord_.state, BlessWord_.remark, BlessWord_.createTime, BlessWord_.deleted,
				BlessWord_.delTime);
		return dbBlessWord;
	}

	/**
	 * 更新BlessWord
	 **/
	@Override
	public BlessWordVo updateBlessWord(BlessWordBo blessWordBo) {
		BlessWord dbBlessWord = blessWordDao.getOne(blessWordBo.getId());
		AttributeReplication.copying(blessWordBo, dbBlessWord, BlessWord_.guid, BlessWord_.word, BlessWord_.state, BlessWord_.remark, BlessWord_.createTime, BlessWord_.deleted,
				BlessWord_.delTime);
		return blessWordConvert.toVo(dbBlessWord);
	}

	/**
	 * 删除BlessWord
	 **/
	@Override
	public void removeBlessWordById(int id) {
		blessWordDao.deleteById(id);
	}

	/**
	 * 根据ID得到BlessWordBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlessWordBo getBlessWordBoById(int id) {
		return blessWordConvert.toBo(this.blessWordDao.getOne(id));
	}

	/**
	 * 根据ID得到BlessWordVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlessWordVo getBlessWordVoById(int id) {
		return blessWordConvert.toVo(this.blessWordDao.getOne(id));
	}

	/**
	 * 根据ID得到BlessWordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlessWordListVo getListVoById(int id) {
		return blessWordConvert.toListVo(this.blessWordDao.getOne(id));
	}

	protected void initConvert() {
		this.blessWordConvert = new EntityListVoBoSimpleConvert<BlessWord, BlessWordBo, BlessWordVo, BlessWordSimple, BlessWordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<BlessWord, BlessWordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWord, BlessWordVo>(beanConvertManager) {
					@Override
					protected void postConvert(BlessWordVo blessWordVo, BlessWord blessWord) {
					}
				};
			}

			@Override
			protected BeanConvert<BlessWord, BlessWordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWord, BlessWordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(BlessWordListVo blessWordListVo, BlessWord blessWord) {
					}
				};
			}

			@Override
			protected BeanConvert<BlessWord, BlessWordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWord, BlessWordBo>(beanConvertManager) {
					@Override
					protected void postConvert(BlessWordBo blessWordBo, BlessWord blessWord) {
					}
				};
			}

			@Override
			protected BeanConvert<BlessWordBo, BlessWord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWordBo, BlessWord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BlessWord, BlessWordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWord, BlessWordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<BlessWordSimple, BlessWord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<BlessWordSimple, BlessWord>(beanConvertManager) {
					@Override
					public BlessWord convert(BlessWordSimple blessWordSimple) throws BeansException {
						return blessWordDao.getOne(blessWordSimple.getId());
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
