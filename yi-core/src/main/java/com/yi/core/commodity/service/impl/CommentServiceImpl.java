/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.IRewardService;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.cms.CmsEnum;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.dao.CommentDao;
import com.yi.core.commodity.domain.bo.CommentBo;
import com.yi.core.commodity.domain.entity.Comment;
import com.yi.core.commodity.domain.entity.Comment_;
import com.yi.core.commodity.domain.entity.Commodity_;
import com.yi.core.commodity.domain.simple.CommentSimple;
import com.yi.core.commodity.domain.vo.CommentListVo;
import com.yi.core.commodity.domain.vo.CommentVo;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.common.ObjectType;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 商品评价
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommentServiceImpl implements ICommentService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CommentDao commentDao;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IIntegralTaskService integralTaskService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IRewardService rewardService;

	@Resource
	private IAccountService accountService;

	private EntityListVoBoSimpleConvert<Comment, CommentBo, CommentVo, CommentSimple, CommentListVo> commentConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Comment getCommentById(int commentId) {
		if (commentDao.existsById(commentId)) {
			return commentDao.getOne(commentId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentVo getCommentVoById(int commentId) {
		CommentVo dbCommentVo = commentConvert.toVo(this.getCommentById(commentId));
		if (dbCommentVo != null) {
			List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(dbCommentVo.getId(), ObjectType.COMMODITY_COMMENT);
			dbCommentVo.setAttachmentVos(attachmentVos);
			return dbCommentVo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentListVo getCommentListVoById(int commentId) {
		return commentConvert.toListVo(this.getCommentById(commentId));
	}

	@Override
	public Comment addComment(Comment comment) {
		comment.setCommentTime(new Date());
		return commentDao.save(comment);
	}

	@Override
	public CommentVo addComment(CommentBo comment) {
		Comment dbComment = this.addComment(commentConvert.toEntity(comment));
		if (CollectionUtils.isNotEmpty(comment.getAttachmentVos())) {
			for (AttachmentVo tmp : comment.getAttachmentVos()) {
				tmp.setObjectId(dbComment.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.COMMODITY_COMMENT);
				tmp.setDescription(comment.getCommentContent());
				tmp.setFilePath(tmp.getUrl());
			}
			attachmentService.saveAll(comment.getAttachmentVos());
		}
		return commentConvert.toVo(dbComment);
	}

	@Override
	public Comment updateComment(Comment comment) {
		Comment dbComment = commentDao.getOne(comment.getId());
		AttributeReplication.copying(comment, dbComment, Comment_.commodity, Comment_.commodityName, Comment_.member, Comment_.nickname, Comment_.serialNo, Comment_.commentStar,
				Comment_.commentContent, Comment_.replyContent, Comment_.display, Comment_.commentTime, Comment_.replyTime, Comment_.imgPath);
		return dbComment;
	}

	@Override
	public CommentVo updateComment(CommentBo commentBo) {
		Comment dbComment = this.updateComment(commentConvert.toEntity(commentBo));
		return commentConvert.toVo(dbComment);
	}

	@Override
	public void removeCommentById(int commentId) {
		Comment dbComment = this.getCommentById(commentId);
		if (dbComment != null) {
			dbComment.setDeleted(Deleted.DEL_TRUE);
			dbComment.setDelTime(new Date());
		}
	}

	@Override
	public Page<Comment> query(Pagination<Comment> query) {
		query.setEntityClazz(Comment.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Comment_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Comment_.commentTime)));
			Object commodityId = query.getParams().get("commodity.id");
			if (commodityId != null) {
				list.add(criteriaBuilder.equal(root.get(Comment_.commodity).get(Commodity_.id), commodityId));
			}
		}));
		Page<Comment> page = commentDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommentListVo> queryListVo(Pagination<Comment> query) {
		Page<Comment> pages = this.query(query);
		List<CommentListVo> vos = commentConvert.toListVos(pages.getContent());
		return new PageImpl<CommentListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommentListVo> queryListVoForApp(Pagination<Comment> query) {
		query.setEntityClazz(Comment.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Comment_.deleted), Deleted.DEL_FALSE)));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Comment_.display), CommodityEnum.DISPLAY_YES.getCode())));
			Object commodityId = query.getParams().get("commodity.id");
			list.add(criteriaBuilder.equal(root.get(Comment_.commodity).get(Commodity_.id), commodityId));
			list1.add(criteriaBuilder.asc(root.get(Comment_.sort)));
			list1.add(criteriaBuilder.desc(root.get(Comment_.commentTime)));
		}));
		Page<Comment> pages = commentDao.findAll(query, query.getPageRequest());
		List<CommentListVo> vos = commentConvert.toListVos(pages.getContent());
		return new PageImpl<CommentListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public List<CommentListVo> getCommentsByCommodityId(int commodityId) {
		return commentConvert.toListVos(commentDao.findByCommodity_id(commodityId));

	}

	protected void initConvert() {
		this.commentConvert = new EntityListVoBoSimpleConvert<Comment, CommentBo, CommentVo, CommentSimple, CommentListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Comment, CommentVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Comment, CommentVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommentVo CommentVo, Comment Comment) {

					}
				};
			}

			@Override
			protected BeanConvert<Comment, CommentListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Comment, CommentListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommentListVo CommentListVo, Comment Comment) {

					}
				};
			}

			@Override
			protected BeanConvert<Comment, CommentBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Comment, CommentBo>(beanConvertManager) {
					@Override
					protected void postConvert(CommentBo CommentBo, Comment Comment) {

					}
				};
			}

			@Override
			protected BeanConvert<CommentBo, Comment> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommentBo, Comment>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Comment, CommentSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Comment, CommentSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CommentSimple, Comment> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommentSimple, Comment>(beanConvertManager) {
					@Override
					public Comment convert(CommentSimple CommentSimple) throws BeansException {
						return commentDao.getOne(CommentSimple.getId());
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
	public void replyComment(int commentId, String replyContent) {
		Comment comment = commentDao.getOne(commentId);
		comment.setReplyContent(replyContent);
		comment.setReplyTime(new Date());
	}

	/**
	 * 显示爱生活评论
	 *
	 * @param id
	 * @param display
	 * @return
	 */
	@Override
	public CommentVo display(int id, int display) {
		Comment comment = commentDao.getOne(id);
		comment.setDisplay(CmsEnum.DISPLAY_YES.getCode());
		return commentConvert.toVo(comment);
	}

	/**
	 * 隐藏爱生活评论
	 *
	 * @param id
	 * @param display
	 * @return
	 */
	@Override
	public CommentVo hide(int id, int display) {
		Comment comment = commentDao.getOne(id);
		comment.setDisplay(CmsEnum.DISPLAY_NO.getCode());
		return commentConvert.toVo(comment);
	}

	/**
	 * TODO 敏感词过滤
	 */
	@Override
	public List<Object> commentOrder(List<CommentBo> commodityComments) {
		if (CollectionUtils.isEmpty(commodityComments)) {
			throw new BusinessException("评价数据不能为空");
		}
		// 当前评价的订单
		SaleOrder dbSaleOrder = saleOrderService.getOrderById(Optional.ofNullable(commodityComments.get(0).getSaleOrder()).map(e -> e.getId()).orElse(null));
		if (dbSaleOrder == null) {
			throw new BusinessException("评价订单不能为空");
		}
		// 订单待评价
		if (OrderEnum.COMMENT_STATE_WAIT.getCode().equals(dbSaleOrder.getCommentState())) {
			List<Comment> saveComments = new ArrayList<>();
			List<AttachmentVo> attachmentVos = new ArrayList<>();
			for (CommentBo tmpComment : commodityComments) {
				if (tmpComment != null) {
					Comment comment = commentConvert.toEntity(tmpComment);
					comment.setSerialNo(NumberGenerateUtils.generateCommentNo());
					comment.setCommentTime(new Date());
					comment.setDisplay(CommodityEnum.DISPLAY_NO.getCode());// 默认不显示评论
					Comment dbComment = commentDao.save(comment);
					saveComments.add(dbComment);
					// 计算商品评价数
					commodityService.updateCommodityByCommentQuantity(tmpComment.getCommodity().getId());
					// 评论图片
					if (CollectionUtils.isNotEmpty(tmpComment.getAttachmentVos())) {
						for (AttachmentVo tmpAttach : tmpComment.getAttachmentVos()) {
							tmpAttach.setObjectId(dbComment.getId());
							tmpAttach.setFileType(FileType.PICTURES);
							tmpAttach.setObjectType(ObjectType.COMMODITY_COMMENT);
							tmpAttach.setDescription(tmpComment.getCommentContent());
							tmpAttach.setFilePath(tmpAttach.getUrl());
							tmpAttach.setSupplierId(Optional.ofNullable(dbComment.getCommodity().getSupplier()).map(e -> e.getId()).orElse(null));
							tmpAttach.setSupplierName(Optional.ofNullable(dbComment.getCommodity().getSupplier()).map(e -> e.getSupplierName()).orElse(null));
							attachmentVos.add(tmpAttach);
						}
					}
				}
			}
			// 保存图片
			attachmentService.saveAll(attachmentVos);
			// 修改订单状态
			saleOrderService.updateSaleOrderByComment(dbSaleOrder.getId());
			// 更新积分 + 积分记录
			accountService.updateByTaskType(dbSaleOrder.getMember().getId(), BasicEnum.TASK_TYPE_COMMENT);
			// 评论奖励
			rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_COMMENT, dbSaleOrder.getMember(), null);
			// 分步发放代金券
			List<Object> grantAmounts = couponReceiveService.grantVoucherByStep(dbSaleOrder.getMember(), dbSaleOrder, ActivityEnum.GRANT_NODE_COMMENT);
			// 解冻代金券
			couponReceiveService.thawVoucherByStep(dbSaleOrder.getMember(), dbSaleOrder, ActivityEnum.GRANT_NODE_COMMENT);
			return grantAmounts;
		}
		return null;
	}

	/**
	 * 自动好评订单
	 */
	@Override
	public void autoCommentByOrders(Set<SaleOrder> needCommentOrders) {
		if (CollectionUtils.isNotEmpty(needCommentOrders)) {
			Set<Comment> needComments = new HashSet<>();
			for (SaleOrder tmpOrder : needCommentOrders) {
				if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
					for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
						if (tmpItem != null) {
							Comment tmpComment = new Comment();
							tmpComment.setCommodity(tmpItem.getCommodity());
							tmpComment.setSaleOrder(tmpOrder);
							tmpComment.setMember(tmpOrder.getMember());
							tmpComment.setSerialNo(NumberGenerateUtils.generateSerialNo());
							tmpComment.setCommentStar(CommodityEnum.COMMENT_STAR_FIVE.getCode());
							tmpComment.setCommentContent("系统默认好评");
							tmpComment.setCommentTime(new Date());
							tmpComment.setDisplay(CommodityEnum.DISPLAY_YES.getCode());
							needComments.add(tmpComment);
						}
					}
				}
			}
			if (CollectionUtils.isNotEmpty(needComments)) {
				commentDao.saveAll(needComments);
			}
		}
	}

}
