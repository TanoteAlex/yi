/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.SetJoin;

import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.order.domain.entity.FreightTemplateConfig;
import com.yi.core.order.domain.simple.FreightTemplateConfigSimple;
import com.yi.core.supplier.domain.simple.SupplierSimple;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.yi.core.cms.dao.RecommendDao;
import com.yi.core.cms.domain.bo.RecommendBo;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.entity.Position_;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.entity.RecommendCommodity;
import com.yi.core.cms.domain.entity.RecommendCommodity_;
import com.yi.core.cms.domain.entity.Recommend_;
import com.yi.core.cms.domain.simple.RecommendSimple;
import com.yi.core.cms.domain.vo.RecommendListVo;
import com.yi.core.cms.domain.vo.RecommendVo;
import com.yi.core.cms.service.IPositionService;
import com.yi.core.cms.service.IRecommendCommodityService;
import com.yi.core.cms.service.IRecommendService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.entity.Commodity_;
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
 * 推荐位
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RecommendServiceImpl implements IRecommendService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RecommendServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RecommendDao recommendDao;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private IPositionService positionService;

	@Resource
	private IRecommendCommodityService recommendCommodityService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	private CommodityDao commodityDao;

	private EntityListVoBoSimpleConvert<Recommend, RecommendBo, RecommendVo, RecommendSimple, RecommendListVo> recommendConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Recommend> query(Pagination<Recommend> query) {
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));
		}));
		Page<Recommend> page = recommendDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RecommendListVo> queryListVo(Pagination<Recommend> query) {
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));
		}));
		Page<Recommend> pages = recommendDao.findAll(query, query.getPageRequest());
		List<RecommendListVo> vos = recommendConvert.toListVos(pages.getContent());
		return new PageImpl<RecommendListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RecommendVo> queryVosForApp(Pagination<Recommend> query) {
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.state), CmsEnum.STATE_ENABLE.getCode())));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));

//			// 商品需 上架和审核通过
//			SetJoin<Recommend, RecommendCommodity> commodityJoin = root.join(Recommend_.recommendCommodities,JoinType.LEFT);
//			list.add(criteriaBuilder.equal(commodityJoin.get(RecommendCommodity_.commodity).get(Commodity_.shelfState), CommodityEnum.SHELF_STATE_ON.getCode()));
//			list.add(criteriaBuilder.equal(commodityJoin.get(RecommendCommodity_.commodity).get(Commodity_.auditState), CommodityEnum.AUDIT_STATE_PASS.getCode()));
//			criteriaQuery.distinct(true);

			Object city = query.getParams().get("city");
			if (city != null) {
				
			}
		}));

		Page<Recommend> pages = recommendDao.findAll(query, query.getPageRequest());

		LOG.info(String.valueOf(pages));
		List<RecommendVo> vos = recommendConvert.toVos(pages.getContent());

		vos.forEach(recommend -> {
			int recommendId=recommend.getId();
			EntityListVoBoSimpleConvert<Commodity, CommodityBo, CommodityVo, CommoditySimple, CommodityListVo> commodityConvert = commodityService.getCommodityConvert();
			List<Commodity> commodities = findCommoditiesByRecommend(recommendId);
			if(CollectionUtils.isNotEmpty(commodities)){
				commodities.forEach(commodity -> {
					commodity.setCategory(null);
					commodity.setSupplier(null);
				});
			//	List<CommodityListVo> commodityListVos = commodityConvert.toListVos(commodities);
				List<CommodityListVo> commodityListVos = new ArrayList<>();

				for (Commodity comm:commodities) {
					FreightTemplateConfig freightTemplate = comm.getFreightTemplate();
					comm.setFreightTemplate(null);
					CommodityListVo commodityListVo = commodityConvert.toListVo(comm);

			/**		//转换FreightTemplate
					if (freightTemplate!=null) {
						FreightTemplateConfigSimple freightTemplateConfigSimple = new FreightTemplateConfigSimple();
						System.out.println(freightTemplate.getChargeMode()+"--------------------------------------");
						BeanUtils.copyProperties(freightTemplate, freightTemplateConfigSimple);
						if (comm.getFreightTemplate().getSupplier()!= null){
							SupplierSimple supplierSimple = new SupplierSimple();
							BeanUtils.copyProperties(freightTemplate.getSupplier(),supplierSimple);
							freightTemplateConfigSimple.setSupplier(supplierSimple);
						}
						commodityListVo.setFreightTemplate(freightTemplateConfigSimple);
					}
*/
					commodityListVos.add(commodityListVo);
				}

				recommend.setCommodities(commodityListVos);
			}

		});
		return new PageImpl<RecommendVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> queryVoForApp(Integer positionType, String city) {
		Position dbPosition = positionService.getPositionByPositionType(positionType);
		if (dbPosition == null) {
			LOG.error("queryVoForApp，根据位置查询推荐数据时，位置为空");
			return new ArrayList<RecommendVo>(0);
		}
		Pagination<Recommend> query = new Pagination<>();
		query.setEntityClazz(Recommend.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.state), CmsEnum.STATE_ENABLE.getCode())));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.position).get(Position_.id), dbPosition.getId())));
			list1.add(criteriaBuilder.asc(root.get(Recommend_.sort)));

			// 根据定位城市查询商品
			// if (StringUtils.isNotBlank(city)) {
			// ListJoin<Recommend, Commodity> commodityJoin =
			// root.join(root.getModel().getDeclaredList("commodities", Commodity.class),
			// JoinType.LEFT);
			// // SetJoin<Recommend, Region> regionJoin =
			// //
			// root.join(commodityJoin.getModel().getDeclaringType().getDeclaredSet("regions",
			// // Region.class), JoinType.LEFT);
			// Predicate deletedPredicate =
			// criteriaBuilder.equal(commodityJoin.get(Commodity_.deleted),
			// Deleted.DEL_FALSE);
			// Predicate shelfPredicate =
			// criteriaBuilder.equal(commodityJoin.get(Commodity_.shelf),
			// CommodityEnum.SHELF_STATE_ON.getCode());
			// // Predicate cityPredicate =
			// // criteriaBuilder.like(regionJoin.get(Region_.area).get(Area_.name), "%" +
			// city
			// // + "%");
			// list.add(deletedPredicate);
			// list.add(shelfPredicate);
			// // list.add(cityPredicate);
			// }
		}));
		Page<Recommend> pages = recommendDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setPosition(null);
			tmp.getRecommendCommodities().forEach(tmp1 -> {
				tmp1.setRecommendCommodityId(null);
				tmp1.getCommodity().setCategory(null);
				tmp1.getCommodity().setSupplier(null);
				tmp1.getCommodity().setComments(null);
			});
			tmp.setRecommendCommodities(tmp.getRecommendCommodities().stream().sorted(Comparator.comparing(RecommendCommodity::getSort)).collect(Collectors.toSet()));
		});

		List<RecommendVo> vos = recommendConvert.toVos(pages.getContent());
		return vos;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Recommend getRecommendById(int recommendId) {
		if (recommendDao.existsById(recommendId)) {
			return recommendDao.getOne(recommendId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendVo getRecommendVoById(int recommendId) {
		Recommend dbRecommend = this.recommendDao.getOne(recommendId);
		// dbRecommend.setRecommendCommodities(recommend.getCommodities().stream()
		// .filter(e ->
		// CommodityEnum.AUDIT_STATE_PASS.getCode().equals(e.getAuditState()) &&
		// CommodityEnum.SHELF_STATE_ON.getCode().equals(e.getShelfState()))
		// .collect(Collectors.toList()));
		dbRecommend.setRecommendCommodities(dbRecommend.getRecommendCommodities().stream().sorted(Comparator.comparing(RecommendCommodity::getSort)).collect(Collectors.toSet()));
		RecommendVo recommendVo = recommendConvert.toVo(dbRecommend);
		recommendVo.setAttachmentVos(attachmentService.findByObjectIdAndObjectType(recommendId, ObjectType.RECOMMEND));
		return recommendVo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RecommendVo getRecommendListVoById(int recommendId) {
		return recommendConvert.toVo(this.recommendDao.getOne(recommendId));
	}

	@Override
	public Recommend addRecommend(Recommend recommend) {
		if (recommend == null || recommend.getPosition() == null || CollectionUtils.isEmpty(recommend.getRecommendCommodities())) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 非楼层推荐 校验位置是否重复
		if (!CmsEnum.POSITION_TYPE_FLOOR.getCode().equals(recommend.getPosition().getPositionType())) {
			int checkCount = recommendDao.countByPosition_idAndDeleted(recommend.getPosition().getId(), Deleted.DEL_FALSE);
			if (checkCount > 0) {
				LOG.error("位置不能重复");
				throw new BusinessException("位置不能重复");
			}
		}
		Recommend dbRecommend = recommendDao.save(recommend);
		// 保存推荐商品
		recommendCommodityService.batchAddRecommendCommodity(dbRecommend, recommend.getRecommendCommodities());
		return dbRecommend;
	}

	@Override
	public RecommendListVo addRecommend(RecommendBo recommendBo) {
		RecommendListVo dbRecommendListVo = recommendConvert.toListVo(this.addRecommend(recommendConvert.toEntity(recommendBo)));
		// 图片
		if (CollectionUtils.isNotEmpty(recommendBo.getAttachmentVos())) {
			for (AttachmentVo tmp : recommendBo.getAttachmentVos()) {
				tmp.setObjectId(dbRecommendListVo.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.RECOMMEND);
				tmp.setDescription(dbRecommendListVo.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(recommendBo.getAttachmentVos());
		}
		return dbRecommendListVo;
	}

	@Override
	public Recommend updateRecommend(Recommend recommend) {
		if (recommend == null || recommend.getPosition() == null || CollectionUtils.isEmpty(recommend.getRecommendCommodities())) {
			LOG.error("提交数据不能为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 非楼层推荐 校验位置是否重复
		if (!CmsEnum.POSITION_TYPE_FLOOR.getCode().equals(recommend.getPosition().getPositionType())) {
			// 校验位置是否重复
			int checkCount = recommendDao.countByPosition_idAndDeletedAndIdNot(recommend.getPosition().getId(), Deleted.DEL_FALSE, recommend.getId());
			if (checkCount > 0) {
				LOG.error("位置不能重复");
				throw new BusinessException("位置不能重复");
			}
		}
		Recommend dbRecommend = recommendDao.getOne(recommend.getId());
		AttributeReplication.copying(recommend, dbRecommend, Recommend_.title, Recommend_.recommendType, Recommend_.sort, Recommend_.showMode, Recommend_.imgPath, Recommend_.state,
				Recommend_.position, Recommend_.linkType, Recommend_.linkId, Recommend_.showBanner, Recommend_.showTitle);
		// 更新推荐商品
		recommendCommodityService.batchUpdateRecommendCommodity(dbRecommend, recommend.getRecommendCommodities());
		return dbRecommend;
	}

	@Override
	public RecommendListVo updateRecommend(RecommendBo recommendBo) {
		Recommend dbRecommend = this.updateRecommend(recommendConvert.toEntity(recommendBo));
		// 图片
		if (CollectionUtils.isNotEmpty(recommendBo.getAttachmentVos())) {
			// 删除图片 重新添加
			attachmentService.deleteByObjectIdAndObjectType(dbRecommend.getId(), ObjectType.RECOMMEND);
			for (AttachmentVo tmp : recommendBo.getAttachmentVos()) {
				tmp.setAttachId(null);
				tmp.setObjectId(dbRecommend.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.RECOMMEND);
				tmp.setDescription(dbRecommend.getTitle());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(recommendBo.getAttachmentVos());
		}
		return recommendConvert.toListVo(dbRecommend);
	}

	@Override
	public void removeRecommendById(int recommendId) {
		Recommend dbRecommend = this.getRecommendById(recommendId);
		if (dbRecommend != null) {
			dbRecommend.setDeleted(Deleted.DEL_TRUE);
			dbRecommend.setDelTime(new Date());
			// 删除推荐商品
			// dbRecommend.setCommodities(null);
			recommendCommodityService.removeByRecommend(recommendId);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Commodity> findCommoditiesByRecommend(int recommendId) {
		List<Commodity> commodities = commodityDao.findAll((root, query, cb) -> {
			SetJoin<Commodity, RecommendCommodity> recommendCommoditiesJoin = root.join(Commodity_.recommendCommodities, JoinType.INNER);
			query.orderBy(cb.asc(recommendCommoditiesJoin.get(RecommendCommodity_.sort)));
			return cb.and(
					cb.equal(recommendCommoditiesJoin.get(RecommendCommodity_.recommend).get(Recommend_.id), recommendId),
					cb.equal(root.get(Commodity_.deleted), Deleted.DEL_FALSE),
					cb.equal(root.get(Commodity_.shelfState), CommodityEnum.SHELF_STATE_ON.getCode()),
					cb.equal(root.get(Commodity_.auditState), CommodityEnum.AUDIT_STATE_PASS.getCode())
			);
		});
		return commodities;
	}

	protected void initConvert() {
		this.recommendConvert = new EntityListVoBoSimpleConvert<Recommend, RecommendBo, RecommendVo, RecommendSimple, RecommendListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Recommend, RecommendVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendVo recommendVo, Recommend recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendListVo RecommendListVo, Recommend Recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendBo>(beanConvertManager) {
					@Override
					protected void postConvert(RecommendBo RecommendBo, Recommend Recommend) {

					}
				};
			}

			@Override
			protected BeanConvert<RecommendBo, Recommend> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendBo, Recommend>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Recommend, RecommendSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Recommend, RecommendSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RecommendSimple, Recommend> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RecommendSimple, Recommend>(beanConvertManager) {
					@Override
					public Recommend convert(RecommendSimple RecommendSimple) throws BeansException {
						return recommendDao.getOne(RecommendSimple.getId());
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
	public void updateStateDisable(int recommendId) {
		Recommend recommend = recommendDao.getOne(recommendId);
		recommend.setState(CmsEnum.STATE_DISABLE.getCode());
	}

	/**
	 * 启用
	 * 
	 * @return
	 */
	@Override
	public void updateStateEnable(int recommendId) {
		Recommend recommend = recommendDao.getOne(recommendId);
		recommend.setState(CmsEnum.STATE_ENABLE.getCode());
	}

	/**
	 * 根据位置获取推荐数据
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> getRecommendsByPositionType(Integer positionType, String city) {
		Position dbPosition = positionService.getPositionByPositionType(positionType);
		if (dbPosition == null) {
			LOG.error("getRecommendsByPositionType，根据位置查询推荐数据时，位置为空");
			return new ArrayList<RecommendVo>(0);
		}
		List<Recommend> dbRecommends = recommendDao.findByPosition_IdAndStateAndDeleted(dbPosition.getId(), CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
		// dbRecommends.stream().map(e -> {
		// e.setCommodities(e.getCommodities().stream().filter(e1 -> {
		// if (Deleted.DEL_FALSE.equals(e1.getDeleted()) &&
		// CommodityEnum.SHELF_STATE_ON.getCode().equals(e1.getShelfState())) {
		// return true;
		// }
		// return false;
		// }).collect(Collectors.toList()));
		// return e;
		// }).collect(Collectors.toList());

		return recommendConvert.toVos(dbRecommends);

	}

	/**
	 * 根据商品id获取推荐位
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> getRecommendsByCommodityId(Integer id, Integer type) {
		return recommendConvert.toVos(recommendDao.findByLinkIdAndLinkType(id, type));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RecommendVo> queryRecommends(Integer positionType, String city) {
		// Pagination<Recommend> query = new Pagination<Recommend>();
		// query.setEntityClazz(Recommend.class);
		// query.setPredicate(((root, criteriaQuery, criteriaBuilder, predicates,
		// orders) -> {
		// predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.deleted),
		// Deleted.DEL_FALSE)));
		// predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.state),
		// CmsEnum.STATE_ENABLE.getCode())));
		// // 推荐位
		// if (positionType != null) {
		// predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Recommend_.position).get(Position_.positionType),
		// positionType)));
		// }
		// // 上架商品
		// ListJoin<Recommend, Commodity> orgHospReportJoin =
		// root.join(root.getModel().getDeclaredList("commodities", Commodity.class),
		// JoinType.LEFT);
		// predicates.add(criteriaBuilder.equal(orgHospReportJoin.get(Commodity_.deleted).as(Integer.class),
		// Deleted.DEL_FALSE));
		// predicates.add(criteriaBuilder.equal(orgHospReportJoin.get(Commodity_.state).as(Integer.class),
		// CommodityEnum.STATE_AGREE.getCode()));
		// predicates.add(criteriaBuilder.equal(orgHospReportJoin.get(Commodity_.shelf).as(Integer.class),
		// CommodityEnum.SHELF_ON.getCode()));
		//
		// }));
		// //分组
		//
		// return recommendConvert.toVos(recommendDao.findAll(query));
		List<Recommend> dbRecommends = new ArrayList<Recommend>();
		if (StringUtils.isNotBlank(city)) {
			// dbRecommends = recommendDao.findByPositionAndCity(positionType,
			// CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE,
			// CommodityEnum.SHELF_STATE_ON.getCode(),
			// CommodityEnum.AUDIT_STATE_PASS.getCode(), city);
		} else {

		}
		dbRecommends = recommendDao.findByPosition(positionType, CmsEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE, CommodityEnum.SHELF_STATE_ON.getCode(),
				CommodityEnum.AUDIT_STATE_PASS.getCode());
		dbRecommends.forEach(tmp -> {
			tmp.setPosition(null);
			tmp.getRecommendCommodities().forEach(tmp1 -> {
				tmp1.setRecommendCommodityId(null);
				tmp1.getCommodity().setCategory(null);
				tmp1.getCommodity().setSupplier(null);
				tmp1.getCommodity().setComments(null);
			});
			tmp.setRecommendCommodities(tmp.getRecommendCommodities().stream().sorted(Comparator.comparing(RecommendCommodity::getSort)).collect(Collectors.toSet()));
		});

		return recommendConvert.toVos(dbRecommends);
	}

}
