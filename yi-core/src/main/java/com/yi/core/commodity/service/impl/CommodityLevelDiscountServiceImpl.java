package com.yi.core.commodity.service.impl;/*
											* Powered By [yihz-framework]
											* Web Site: yihz
											* Since 2018 - 2018
											*/

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

import com.yi.core.commodity.dao.CommodityLevelDiscountDao;
import com.yi.core.commodity.domain.bo.CommodityLevelDiscountBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount_;
import com.yi.core.commodity.domain.simple.CommodityLevelDiscountSimple;
import com.yi.core.commodity.domain.vo.CommodityLevelDiscountListVo;
import com.yi.core.commodity.domain.vo.CommodityLevelDiscountVo;
import com.yi.core.commodity.service.ICommodityLevelDiscountService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommodityLevelDiscountServiceImpl implements ICommodityLevelDiscountService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CommodityLevelDiscountServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CommodityLevelDiscountDao commodityLevelDiscountDao;

	private EntityListVoBoSimpleConvert<CommodityLevelDiscount, CommodityLevelDiscountBo, CommodityLevelDiscountVo, CommodityLevelDiscountSimple, CommodityLevelDiscountListVo> commodityLevelDiscountConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommodityLevelDiscount> query(Pagination<CommodityLevelDiscount> query) {
		query.setEntityClazz(CommodityLevelDiscount.class);
		Page<CommodityLevelDiscount> page = commodityLevelDiscountDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommodityLevelDiscountListVo> queryListVo(Pagination<CommodityLevelDiscount> query) {
		Page<CommodityLevelDiscount> pages = this.query(query);
		List<CommodityLevelDiscountListVo> vos = commodityLevelDiscountConvert.toListVos(pages.getContent());
		return new PageImpl<CommodityLevelDiscountListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	@Override
	public CommodityLevelDiscount getById(int commodityLevelDiscountId) {
		return commodityLevelDiscountDao.getOne(commodityLevelDiscountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommodityLevelDiscountVo getVoById(int commodityLevelDiscountId) {

		return commodityLevelDiscountConvert.toVo(this.commodityLevelDiscountDao.getOne(commodityLevelDiscountId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommodityLevelDiscountListVo getListVoById(int commodityLevelDiscountId) {
		return commodityLevelDiscountConvert.toListVo(this.commodityLevelDiscountDao.getOne(commodityLevelDiscountId));
	}

	@Override
	public CommodityLevelDiscount addCommodityLevelDiscount(CommodityLevelDiscount commodityLevelDiscount) {
		return commodityLevelDiscountDao.save(commodityLevelDiscount);
	}

	@Override
	public CommodityLevelDiscountListVo addCommodityLevelDiscount(CommodityLevelDiscountBo commodityLevelDiscountBo) {
		return commodityLevelDiscountConvert.toListVo(commodityLevelDiscountDao.save(commodityLevelDiscountConvert.toEntity(commodityLevelDiscountBo)));
	}

	@Override
	public CommodityLevelDiscount updateCommodityLevelDiscount(CommodityLevelDiscount commodityLevelDiscount) {
		CommodityLevelDiscount dbCommodityLevelDiscount = commodityLevelDiscountDao.getOne(commodityLevelDiscount.getId());
		AttributeReplication.copying(commodityLevelDiscount, dbCommodityLevelDiscount, CommodityLevelDiscount_.commodity, CommodityLevelDiscount_.memberLevel,
				CommodityLevelDiscount_.discount);
		return dbCommodityLevelDiscount;
	}

	@Override
	public CommodityLevelDiscountListVo updateCommodityLevelDiscount(CommodityLevelDiscountBo commodityLevelDiscountBo) {
		CommodityLevelDiscount dbCommodityLevelDiscount = commodityLevelDiscountDao.getOne(commodityLevelDiscountBo.getId());
		AttributeReplication.copying(commodityLevelDiscountBo, dbCommodityLevelDiscount, CommodityLevelDiscount_.guid, CommodityLevelDiscount_.commodity,
				CommodityLevelDiscount_.memberLevel, CommodityLevelDiscount_.discount);
		return commodityLevelDiscountConvert.toListVo(dbCommodityLevelDiscount);
	}

	@Override
	public void removeCommodityLevelDiscountById(int commodityLevelDiscountId) {
		commodityLevelDiscountDao.delete(commodityLevelDiscountDao.getOne(commodityLevelDiscountId));
	}

	@Override
	public void removeByMemberLevel(int memberLevelId) {
		List<CommodityLevelDiscount> dbCommodityLevelDiscounts = commodityLevelDiscountDao.findByMemberLevel_id(memberLevelId);
		commodityLevelDiscountDao.deleteAll(dbCommodityLevelDiscounts);
	}

	protected void initConvert() {
		this.commodityLevelDiscountConvert = new EntityListVoBoSimpleConvert<CommodityLevelDiscount, CommodityLevelDiscountBo, CommodityLevelDiscountVo, CommodityLevelDiscountSimple, CommodityLevelDiscountListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<CommodityLevelDiscount, CommodityLevelDiscountVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscount, CommodityLevelDiscountVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityLevelDiscountVo CommodityLevelDiscountVo, CommodityLevelDiscount CommodityLevelDiscount) {

					}
				};
			}

			@Override
			protected BeanConvert<CommodityLevelDiscount, CommodityLevelDiscountListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscount, CommodityLevelDiscountListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityLevelDiscountListVo CommodityLevelDiscountListVo, CommodityLevelDiscount CommodityLevelDiscount) {

					}
				};
			}

			@Override
			protected BeanConvert<CommodityLevelDiscount, CommodityLevelDiscountBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscount, CommodityLevelDiscountBo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityLevelDiscountBo CommodityLevelDiscountBo, CommodityLevelDiscount CommodityLevelDiscount) {

					}
				};
			}

			@Override
			protected BeanConvert<CommodityLevelDiscountBo, CommodityLevelDiscount> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscountBo, CommodityLevelDiscount>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CommodityLevelDiscount, CommodityLevelDiscountSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscount, CommodityLevelDiscountSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CommodityLevelDiscountSimple, CommodityLevelDiscount> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityLevelDiscountSimple, CommodityLevelDiscount>(beanConvertManager) {
					@Override
					public CommodityLevelDiscount convert(CommodityLevelDiscountSimple CommodityLevelDiscountSimple) throws BeansException {
						return commodityLevelDiscountDao.getOne(CommodityLevelDiscountSimple.getId());
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
	 * 根据商品id获取会员中间表
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommodityLevelDiscount> getByCommodity(int id) {
		return commodityLevelDiscountDao.findByCommodity_id(id);
	}

	/**
	 * 批量新增会员等级
	 */
	@Override
	public void batchAddLevelDiscount(Commodity commodity, List<CommodityLevelDiscount> commodityLevelDiscounts) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodityLevelDiscounts)) {
			commodityLevelDiscounts.forEach(e -> {
				e.setCommodity(commodity);
			});
			commodityLevelDiscountDao.saveAll(commodityLevelDiscounts);
		}
	}

	/**
	 * 批量修改会员等级
	 */
	@Override
	public void batchUpdateLevelDiscount(Commodity commodity, List<CommodityLevelDiscount> commodityLevelDiscounts) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodityLevelDiscounts)) {
			// 查询当前商品的会员等级折扣集合
			List<CommodityLevelDiscount> dbCommodityLevelDiscounts = commodityLevelDiscountDao.findByCommodity_id(commodity.getId());
			// 需要新增的数据
			Set<CommodityLevelDiscount> saveSets = commodityLevelDiscounts.stream().filter(e1 -> dbCommodityLevelDiscounts.stream().noneMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 需要更新的数据
			Set<CommodityLevelDiscount> updateSets = commodityLevelDiscounts.stream().filter(e1 -> dbCommodityLevelDiscounts.stream().anyMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 需要删除的数据
			Set<CommodityLevelDiscount> deleteSets = dbCommodityLevelDiscounts.stream().filter(e1 -> commodityLevelDiscounts.stream().noneMatch(e2 -> e1.getId() == e2.getId()))
					.collect(Collectors.toSet());
			// 保存数据
			saveSets.forEach(e -> {
				e.setCommodity(commodity);
			});
			commodityLevelDiscountDao.saveAll(saveSets);
			// 删除数据
			deleteSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					commodityLevelDiscountDao.delete(e);
				}
			});
			// 修改数据
			updateSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					e.setCommodity(commodity);
					this.updateCommodityLevelDiscount(e);
				}
			});
		}

	}

}
