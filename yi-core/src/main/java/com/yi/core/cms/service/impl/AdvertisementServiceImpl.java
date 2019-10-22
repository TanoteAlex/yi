/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.ArrayList;
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

import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.AdvertisementDao;
import com.yi.core.cms.domain.bo.AdvertisementBo;
import com.yi.core.cms.domain.entity.Advertisement;
import com.yi.core.cms.domain.entity.Advertisement_;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.simple.AdvertisementSimple;
import com.yi.core.cms.domain.vo.AdvertisementListVo;
import com.yi.core.cms.domain.vo.AdvertisementVo;
import com.yi.core.cms.service.IAdvertisementService;
import com.yi.core.cms.service.IPositionService;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.ObjectType;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 广告位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class AdvertisementServiceImpl implements IAdvertisementService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private AdvertisementDao advertisementDao;

	@Resource
	private IPositionService positionService;

	@Resource
	private IAttachmentService attachmentService;

	private EntityListVoBoSimpleConvert<Advertisement, AdvertisementBo, AdvertisementVo, AdvertisementSimple, AdvertisementListVo> advertisementConvert;

	@Override
	public Advertisement getAdvertisementById(int advertisementId) {
		return advertisementDao.getOne(advertisementId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdvertisementVo getAdvertisementVoById(int advertisementId) {

		return advertisementConvert.toVo(this.advertisementDao.getOne(advertisementId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AdvertisementListVo getAdvertisementListVoById(int advertisementId) {
		return advertisementConvert.toListVo(this.advertisementDao.getOne(advertisementId));
	}

	@Override
	public Advertisement addAdvertisement(Advertisement advertisement) {
		if (advertisement == null || advertisement.getPosition() == null) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能空");
		}
		if (!CmsEnum.POSITION_TYPE_BANNER.getCode().equals(advertisement.getPosition().getPositionType())) {
			LOG.error("位置选择错误");
			throw new BusinessException("位置只能选择首页轮播图");
		}
		return advertisementDao.save(advertisement);
	}

	@Override
	public AdvertisementVo addAdvertisement(AdvertisementBo advertisementBo) {
		Advertisement dbAdvertisement = this.addAdvertisement(advertisementConvert.toEntity(advertisementBo));
		// 图片
		if (CollectionUtils.isNotEmpty(advertisementBo.getAttachmentVos())) {
			for (AttachmentVo tmp : advertisementBo.getAttachmentVos()) {
				tmp.setObjectId(dbAdvertisement.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.ADVERTISEMENT);
				tmp.setDescription(dbAdvertisement.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(advertisementBo.getAttachmentVos());
		}
		return advertisementConvert.toVo(dbAdvertisement);
	}

	@Override
	public Advertisement updateAdvertisement(Advertisement advertisement) {
		if (advertisement == null || advertisement.getId() < 1 || advertisement.getPosition() == null) {
			LOG.error("提交数据为空");
			throw new BusinessException("提交数据不能空");
		}
		if (!CmsEnum.POSITION_TYPE_BANNER.getCode().equals(advertisement.getPosition().getPositionType())) {
			LOG.error("位置选择错误");
			throw new BusinessException("位置只能选择首页轮播图");
		}
		Advertisement dbAdvertisement = advertisementDao.getOne(advertisement.getId());
		AttributeReplication.copying(advertisement, dbAdvertisement, Advertisement_.position, Advertisement_.title, Advertisement_.content, Advertisement_.imgPath,
				Advertisement_.url, Advertisement_.linkId, Advertisement_.linkType, Advertisement_.sort, Advertisement_.state);
		return dbAdvertisement;
	}

	@Override
	public AdvertisementVo updateAdvertisement(AdvertisementBo advertisementBo) {
		Advertisement dbAdvertisement = this.updateAdvertisement(advertisementConvert.toEntity(advertisementBo));
		// 图片
		if (CollectionUtils.isNotEmpty(advertisementBo.getAttachmentVos())) {
			// 删除图片 重新添加
			attachmentService.deleteByObjectIdAndObjectType(dbAdvertisement.getId(), ObjectType.ADVERTISEMENT);
			for (AttachmentVo tmp : advertisementBo.getAttachmentVos()) {
				tmp.setAttachId(null);
				tmp.setObjectId(dbAdvertisement.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.ADVERTISEMENT);
				tmp.setDescription(dbAdvertisement.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(advertisementBo.getAttachmentVos());
		}
		return advertisementConvert.toVo(dbAdvertisement);
	}

	@Override
	public void removeAdvertisementById(int advertisementId) {
		Advertisement dbAdvertisement = advertisementDao.getOne(advertisementId);
		if (dbAdvertisement != null) {
			dbAdvertisement.setDeleted(Deleted.DEL_TRUE);
			dbAdvertisement.setDelTime(new Date());
		}
	}

	@Override
	public Page<Advertisement> query(Pagination<Advertisement> query) {
		query.setEntityClazz(Advertisement.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Advertisement_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Advertisement> page = advertisementDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<AdvertisementListVo> queryListVo(Pagination<Advertisement> query) {

		Page<Advertisement> pages = this.query(query);

		List<AdvertisementListVo> vos = advertisementConvert.toListVos(pages.getContent());
		return new PageImpl<AdvertisementListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.advertisementConvert = new EntityListVoBoSimpleConvert<Advertisement, AdvertisementBo, AdvertisementVo, AdvertisementSimple, AdvertisementListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Advertisement, AdvertisementVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Advertisement, AdvertisementVo>(beanConvertManager) {
					@Override
					protected void postConvert(AdvertisementVo AdvertisementVo, Advertisement Advertisement) {

					}
				};
			}

			@Override
			protected BeanConvert<Advertisement, AdvertisementListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Advertisement, AdvertisementListVo>(beanConvertManager) {
					@Override
					protected void postConvert(AdvertisementListVo AdvertisementListVo, Advertisement Advertisement) {

					}
				};
			}

			@Override
			protected BeanConvert<Advertisement, AdvertisementBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Advertisement, AdvertisementBo>(beanConvertManager) {
					@Override
					protected void postConvert(AdvertisementBo AdvertisementBo, Advertisement Advertisement) {

					}
				};
			}

			@Override
			protected BeanConvert<AdvertisementBo, Advertisement> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AdvertisementBo, Advertisement>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Advertisement, AdvertisementSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Advertisement, AdvertisementSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<AdvertisementSimple, Advertisement> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<AdvertisementSimple, Advertisement>(beanConvertManager) {
					@Override
					public Advertisement convert(AdvertisementSimple AdvertisementSimple) throws BeansException {
						return advertisementDao.getOne(AdvertisementSimple.getId());
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
	 * 禁用
	 *
	 * @return
	 */
	@Override
	public AdvertisementVo updateStateDisable(int advertisementId) {
		Advertisement advertisement = advertisementDao.getOne(advertisementId);
		advertisement.setState(CmsEnum.STATE_DISABLE.getCode());
		return advertisementConvert.toVo(advertisement);
	}

	/**
	 * 启用
	 *
	 * @return
	 */
	@Override
	public AdvertisementVo updateStateEnable(int advertisementId) {
		Advertisement advertisement = advertisementDao.getOne(advertisementId);
		advertisement.setState(CmsEnum.STATE_ENABLE.getCode());
		return advertisementConvert.toVo(advertisement);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AdvertisementListVo> getAdvertisementListVoForApp(Integer positionType) {
		Position dbPosition = positionService.getPositionByPositionType(positionType);
		if (dbPosition == null) {
			LOG.error("getAdvertisementListVoForApp，查询首页轮播图时，位置为空");
			return new ArrayList<AdvertisementListVo>(0);
		}
		return advertisementConvert.toListVos(advertisementDao.findByPosition_idAndStateAndDeleted(dbPosition.getId(), CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE));
	}
}
