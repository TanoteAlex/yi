/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.PrizeDao;
import com.yi.core.activity.domain.bo.PrizeBo;
import com.yi.core.activity.domain.entity.Prize;
import com.yi.core.activity.domain.entity.Prize_;
import com.yi.core.activity.domain.simple.PrizeSimple;
import com.yi.core.activity.domain.vo.PrizeListVo;
import com.yi.core.activity.domain.vo.PrizeVo;
import com.yi.core.activity.service.IPrizeService;
import com.yi.core.cms.CmsEnum;
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
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class PrizeServiceImpl implements IPrizeService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(PrizeServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private PrizeDao prizeDao;

	private EntityListVoBoSimpleConvert<Prize, PrizeBo, PrizeVo, PrizeSimple, PrizeListVo> prizeConvert;

	/**
	 * 分页查询Prize
	 **/
	@Override
	public Page<Prize> query(Pagination<Prize> query) {
		query.setEntityClazz(Prize.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Prize_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Prize_.createTime)));
		}));
		Page<Prize> page = prizeDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: Prize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PrizeListVo> queryListVo(Pagination<Prize> query) {
		Page<Prize> pages = this.query(query);
		List<PrizeListVo> vos = prizeConvert.toListVos(pages.getContent());
		return new PageImpl<PrizeListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: Prize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PrizeListVo> queryListVoForApp(Pagination<Prize> query) {
		query.setEntityClazz(Prize.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Prize_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Prize_.createTime)));
		}));
		Page<Prize> pages = prizeDao.findAll(query, query.getPageRequest());
		List<PrizeListVo> vos = prizeConvert.toListVos(pages.getContent());
		return new PageImpl<PrizeListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建Prize
	 **/
	@Override
	public Prize addPrize(Prize prize) {
		if (prize == null) {
			throw new BusinessException("提交数据不能为空");
		}
		if (ActivityEnum.PRIZE_TYPE_INTEGRAL.getCode().equals(prize.getPrizeType()) && prize.getIntegral() == null) {
			throw new BusinessException("积分不能为空");
		}
		if (ActivityEnum.PRIZE_TYPE_COMMODITY.getCode().equals(prize.getPrizeType()) && prize.getCommodity() == null) {
			throw new BusinessException("商品不能为空");
		}
		if (ActivityEnum.PRIZE_TYPE_COUPON.getCode().equals(prize.getPrizeType()) && prize.getCoupon() == null) {
			throw new BusinessException("优惠券不能为空");
		}
		prize.setCode(NumberGenerateUtils.generatePrizeNo());
		return prizeDao.save(prize);
	}

	/**
	 * 创建Prize
	 **/
	@Override
	public PrizeListVo addPrize(PrizeBo prizeBo) {
		return prizeConvert.toListVo(this.addPrize(prizeConvert.toEntity(prizeBo)));
	}

	/**
	 * 更新Prize
	 **/
	@Override
	public Prize updatePrize(Prize prize) {
		Prize dbPrize = this.getPrizeById(prize.getId());
		AttributeReplication.copying(prize, dbPrize, Prize_.name, Prize_.prizeType, Prize_.integral, Prize_.commodity, Prize_.coupon, Prize_.state, Prize_.remark, Prize_.reminder);
		return dbPrize;
	}

	/**
	 * 更新Prize
	 **/
	@Override
	public PrizeListVo updatePrize(PrizeBo prizeBo) {
		Prize dbPrize = this.updatePrize(prizeConvert.toEntity(prizeBo));
		return prizeConvert.toListVo(dbPrize);
	}

	/**
	 * 删除Prize
	 **/
	@Override
	public void removePrizeById(int id) {
		Prize dbPrize = this.getPrizeById(id);
		if (dbPrize != null) {
			dbPrize.setDeleted(Deleted.DEL_TRUE);
			dbPrize.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到Prize
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Prize getPrizeById(int id) {
		if (prizeDao.existsById(id)) {
			return this.prizeDao.getOne(id);
		}
		return null;
	}

	/**
	 * 根据ID得到PrizeBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PrizeBo getPrizeBoById(int id) {
		return prizeConvert.toBo(this.getPrizeById(id));
	}

	/**
	 * 根据ID得到PrizeVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PrizeVo getPrizeVoById(int id) {
		return prizeConvert.toVo(this.getPrizeById(id));
	}

	/**
	 * 根据ID得到PrizeListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PrizeListVo getListVoById(int id) {
		return prizeConvert.toListVo(this.getPrizeById(id));
	}

	/**
	 * 根据prizeId改变禁用启用状态
	 * 
	 * @param prizeId
	 * @return
	 */
	@Override
	public PrizeVo updateState(int prizeId) {
		Prize prize = prizeDao.getOne(prizeId);
		if (ActivityEnum.STATE_DISABLE.getCode().equals(prize.getState())) {
			prize.setState(CmsEnum.STATE_ENABLE.getCode());
		} else if (ActivityEnum.STATE_ENABLE.getCode().equals(prize.getState())) {
			prize.setState(CmsEnum.STATE_DISABLE.getCode());
		}
		return prizeConvert.toVo(prize);
	}

	protected void initConvert() {
		this.prizeConvert = new EntityListVoBoSimpleConvert<Prize, PrizeBo, PrizeVo, PrizeSimple, PrizeListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Prize, PrizeVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Prize, PrizeVo>(beanConvertManager) {
					@Override
					protected void postConvert(PrizeVo prizeVo, Prize prize) {
					}
				};
			}

			@Override
			protected BeanConvert<Prize, PrizeListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Prize, PrizeListVo>(beanConvertManager) {
					@Override
					protected void postConvert(PrizeListVo prizeListVo, Prize prize) {
					}
				};
			}

			@Override
			protected BeanConvert<Prize, PrizeBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Prize, PrizeBo>(beanConvertManager) {
					@Override
					protected void postConvert(PrizeBo prizeBo, Prize prize) {
					}
				};
			}

			@Override
			protected BeanConvert<PrizeBo, Prize> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PrizeBo, Prize>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Prize, PrizeSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Prize, PrizeSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PrizeSimple, Prize> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PrizeSimple, Prize>(beanConvertManager) {
					@Override
					public Prize convert(PrizeSimple prizeSimple) throws BeansException {
						return prizeDao.getOne(prizeSimple.getId());
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
