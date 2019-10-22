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

import com.yi.core.gift.dao.GiftBagItemDao;
import com.yi.core.gift.domain.bo.GiftBagItemBo;
import com.yi.core.gift.domain.entity.GiftBagItem;
import com.yi.core.gift.domain.entity.GiftBagItem_;
import com.yi.core.gift.domain.simple.GiftBagItemSimple;
import com.yi.core.gift.domain.vo.GiftBagItemListVo;
import com.yi.core.gift.domain.vo.GiftBagItemVo;
import com.yi.core.gift.service.IGiftBagItemService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 礼包项
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GiftBagItemServiceImpl implements IGiftBagItemService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GiftBagItemServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GiftBagItemDao giftBagItemDao;

	private EntityListVoBoSimpleConvert<GiftBagItem, GiftBagItemBo, GiftBagItemVo, GiftBagItemSimple, GiftBagItemListVo> giftBagItemConvert;

	/**
	 * 分页查询GiftBagItem
	 **/
	@Override
	public Page<GiftBagItem> query(Pagination<GiftBagItem> query) {
		query.setEntityClazz(GiftBagItem.class);
		Page<GiftBagItem> page = giftBagItemDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GiftBagItem
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GiftBagItemListVo> queryListVo(Pagination<GiftBagItem> query) {
		Page<GiftBagItem> pages = this.query(query);
		List<GiftBagItemListVo> vos = giftBagItemConvert.toListVos(pages.getContent());
		return new PageImpl<GiftBagItemListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GiftBagItem
	 **/
	@Override
	public GiftBagItem addGiftBagItem(GiftBagItem giftBagItem) {
		return giftBagItemDao.save(giftBagItem);
	}

	/**
	 * 创建GiftBagItem
	 **/
	@Override
	public GiftBagItemVo addGiftBagItem(GiftBagItemBo giftBagItemBo) {
		return giftBagItemConvert.toVo(giftBagItemDao.save(giftBagItemConvert.toEntity(giftBagItemBo)));
	}

	/**
	 * 更新GiftBagItem
	 **/
	@Override
	public GiftBagItem updateGiftBagItem(GiftBagItem giftBagItem) {
		GiftBagItem dbGiftBagItem = giftBagItemDao.getOne(giftBagItem.getId());
		AttributeReplication.copying(giftBagItem, dbGiftBagItem, GiftBagItem_.guid, GiftBagItem_.giftBag, GiftBagItem_.member, GiftBagItem_.supplier, GiftBagItem_.commodity,
				GiftBagItem_.product, GiftBagItem_.price, GiftBagItem_.quantity, GiftBagItem_.subtotal, GiftBagItem_.invalidTime, GiftBagItem_.createTime, GiftBagItem_.deleted,
				GiftBagItem_.delTime);
		return dbGiftBagItem;
	}

	/**
	 * 更新GiftBagItem
	 **/
	@Override
	public GiftBagItemVo updateGiftBagItem(GiftBagItemBo giftBagItemBo) {
		GiftBagItem dbGiftBagItem = giftBagItemDao.getOne(giftBagItemBo.getId());
		AttributeReplication.copying(giftBagItemBo, dbGiftBagItem, GiftBagItem_.guid, GiftBagItem_.giftBag, GiftBagItem_.member, GiftBagItem_.supplier, GiftBagItem_.commodity,
				GiftBagItem_.product, GiftBagItem_.price, GiftBagItem_.quantity, GiftBagItem_.subtotal, GiftBagItem_.invalidTime, GiftBagItem_.createTime, GiftBagItem_.deleted,
				GiftBagItem_.delTime);
		return giftBagItemConvert.toVo(dbGiftBagItem);
	}

	/**
	 * 删除GiftBagItem
	 **/
	@Override
	public void removeGiftBagItemById(int id) {
		giftBagItemDao.deleteById(id);
	}

	/**
	 * 根据ID得到GiftBagItemBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagItemBo getGiftBagItemBoById(int id) {
		return giftBagItemConvert.toBo(this.giftBagItemDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftBagItemVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagItemVo getGiftBagItemVoById(int id) {
		return giftBagItemConvert.toVo(this.giftBagItemDao.getOne(id));
	}

	/**
	 * 根据ID得到GiftBagItemListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GiftBagItemListVo getListVoById(int id) {
		return giftBagItemConvert.toListVo(this.giftBagItemDao.getOne(id));
	}

	protected void initConvert() {
		this.giftBagItemConvert = new EntityListVoBoSimpleConvert<GiftBagItem, GiftBagItemBo, GiftBagItemVo, GiftBagItemSimple, GiftBagItemListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<GiftBagItem, GiftBagItemVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItem, GiftBagItemVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagItemVo giftBagItemVo, GiftBagItem giftBagItem) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBagItem, GiftBagItemListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItem, GiftBagItemListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagItemListVo giftBagItemListVo, GiftBagItem giftBagItem) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBagItem, GiftBagItemBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItem, GiftBagItemBo>(beanConvertManager) {
					@Override
					protected void postConvert(GiftBagItemBo giftBagItemBo, GiftBagItem giftBagItem) {
					}
				};
			}

			@Override
			protected BeanConvert<GiftBagItemBo, GiftBagItem> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItemBo, GiftBagItem>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GiftBagItem, GiftBagItemSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItem, GiftBagItemSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GiftBagItemSimple, GiftBagItem> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GiftBagItemSimple, GiftBagItem>(beanConvertManager) {
					@Override
					public GiftBagItem convert(GiftBagItemSimple giftBagItemSimple) throws BeansException {
						return giftBagItemDao.getOne(giftBagItemSimple.getId());
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
