/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service.impl;

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

import com.yi.core.cms.CmsEnum;
import com.yi.core.cms.dao.PositionDao;
import com.yi.core.cms.domain.bo.PositionBo;
import com.yi.core.cms.domain.entity.Position;
import com.yi.core.cms.domain.entity.Position_;
import com.yi.core.cms.domain.simple.PositionSimple;
import com.yi.core.cms.domain.vo.PositionListVo;
import com.yi.core.cms.domain.vo.PositionVo;
import com.yi.core.cms.service.IPositionService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 位置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class PositionServiceImpl implements IPositionService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(PositionServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private PositionDao positionDao;

	private EntityListVoBoSimpleConvert<Position, PositionBo, PositionVo, PositionSimple, PositionListVo> positionConvert;

	/**
	 * 分页查询Position
	 **/
	@Override
	public Page<Position> query(Pagination<Position> query) {
		query.setEntityClazz(Position.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Position_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Position> page = positionDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 创建Position
	 **/
	@Override
	public PositionVo addPosition(PositionBo positionBo) {
		if (positionBo == null) {
			LOG.error("addPosition,提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 校验位置类型是否重复
		Position checkPosition = positionDao.findByPositionTypeAndDeleted(positionBo.getPositionType(),
				Deleted.DEL_FALSE);
		if (checkPosition != null) {
			if (CmsEnum.STATE_DISABLE.getCode().equals(checkPosition.getState())) {
				LOG.error("addPosition，该位置类型{}已被禁用，无需重复添加", positionBo.getPositionType());
				throw new BusinessException("该位置已被禁用，请启用");
			}
			LOG.error("addPosition，位置类型{}重复", positionBo.getPositionType());
			throw new BusinessException("位置不能重复");
		}
		return positionConvert.toVo(positionDao.save(positionConvert.toEntity(positionBo)));
	}

	/**
	 * 更新Position
	 **/
	@Override
	public PositionVo updatePosition(Position position) {
		if (position == null) {

		}
		// 校验位置类型是否重复
		Position checkPosition = positionDao.findByPositionTypeAndDeletedAndIdNot(position.getPositionType(),
				Deleted.DEL_FALSE, position.getId());
		if (checkPosition != null) {
			if (CmsEnum.STATE_DISABLE.getCode().equals(checkPosition.getState())) {
				LOG.error("updatePosition，该位置类型{}已被禁用，无需重复添加", checkPosition.getPositionType());
				throw new BusinessException("该位置已被禁用，请启用");
			}
			LOG.error("updatePosition，位置类型{}重复", checkPosition.getPositionType());
			throw new BusinessException("位置不能重复");
		}
		Position dbPosition = positionDao.getOne(position.getId());
		AttributeReplication.copying(position, dbPosition, Position_.name, Position_.positionType, Position_.remark,
				Position_.state);
		return positionConvert.toVo(dbPosition);
	}

	/**
	 * 删除Position
	 **/
	@Override
	public void removePositionById(int id) {
		Position dbPosition = positionDao.getOne(id);
		if (dbPosition != null) {
			dbPosition.setDeleted(Deleted.DEL_TRUE);
			dbPosition.setDelTime(new Date());
		}
	}

	/**
	 * 根据ID得到Position
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PositionVo getPositionVoById(int id) {
		return positionConvert.toVo(this.positionDao.getOne(id));
	}

	/**
	 * 根据ID得到PositionListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PositionListVo getListVoById(int id) {
		return positionConvert.toListVo(this.positionDao.getOne(id));
	}

	/**
	 * 分页查询: Position
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PositionListVo> queryListVo(Pagination<Position> query) {

		Page<Position> pages = this.query(query);

		List<PositionListVo> vos = positionConvert.toListVos(pages.getContent());
		return new PageImpl<PositionListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.positionConvert = new EntityListVoBoSimpleConvert<Position, PositionBo, PositionVo, PositionSimple, PositionListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<Position, PositionVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Position, PositionVo>(beanConvertManager) {
					@Override
					protected void postConvert(PositionVo PositionVo, Position Position) {

					}
				};
			}

			@Override
			protected BeanConvert<Position, PositionListVo> createEntityToListVoConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Position, PositionListVo>(beanConvertManager) {
					@Override
					protected void postConvert(PositionListVo PositionListVo, Position Position) {

					}
				};
			}

			@Override
			protected BeanConvert<Position, PositionBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Position, PositionBo>(beanConvertManager) {
					@Override
					protected void postConvert(PositionBo PositionBo, Position Position) {

					}
				};
			}

			@Override
			protected BeanConvert<PositionBo, Position> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PositionBo, Position>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Position, PositionSimple> createEntityToSimpleConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Position, PositionSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<PositionSimple, Position> createSimpleToEntityConvert(
					BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<PositionSimple, Position>(beanConvertManager) {
					@Override
					public Position convert(PositionSimple PositionSimple) throws BeansException {
						return positionDao.getOne(PositionSimple.getId());
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
	 **/
	@Override
	public PositionVo updateStateDisable(int id) {
		Position position = positionDao.getOne(id);
		position.setState(CmsEnum.STATE_DISABLE.getCode());
		return positionConvert.toVo(position);
	}

	/**
	 * 启用
	 **/
	@Override
	public PositionVo updateStateEnable(int id) {
		Position position = positionDao.getOne(id);
		position.setState(CmsEnum.STATE_ENABLE.getCode());
		return positionConvert.toVo(position);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Position getPositionByPositionType(Integer positionType) {
		if (positionType == null) {
			LOG.error("getPositionByPositionType，参数（positionType）为空");
			throw new BusinessException("请求参数不能为空");
		}
		return positionDao.findByPositionTypeAndStateAndDeleted(positionType, CmsEnum.STATE_ENABLE.getCode(),
				Deleted.DEL_FALSE);
	}

}
