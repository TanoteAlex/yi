/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;

import com.yi.core.activity.domain.simple.CouponSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.member.dao.MemberDao;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.activity.domain.entity.InvitePrize;
import com.yi.core.activity.domain.entity.InvitePrize_;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yihz.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Hibernate;
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

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.InviteActivityDao;
import com.yi.core.activity.domain.bo.InviteActivityBo;
import com.yi.core.activity.domain.entity.InviteActivity;
import com.yi.core.activity.domain.entity.InviteActivity_;
import com.yi.core.activity.domain.simple.InviteActivitySimple;
import com.yi.core.activity.domain.simple.InvitePrizeSimple;
import com.yi.core.activity.domain.vo.InviteActivityListVo;
import com.yi.core.activity.domain.vo.InviteActivityVo;
import com.yi.core.activity.service.IInviteActivityService;
import com.yi.core.activity.service.IInvitePrizeService;
import com.yi.core.common.Deleted;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class InviteActivityServiceImpl implements IInviteActivityService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(InviteActivityServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private MemberDao memberDao;

    @Resource
    private InviteActivityDao inviteActivityDao;

    @Resource
    private IInvitePrizeService invitePrizeService;

    @Resource
    private IMemberService memberService;

    @Resource
    private ISaleOrderService saleOrderService;

    @Resource
    private IIntegralRecordService integralRecordService;

    @Resource
    private ICouponReceiveService couponReceiveService;

    private EntityListVoBoSimpleConvert<InviteActivity, InviteActivityBo, InviteActivityVo, InviteActivitySimple, InviteActivityListVo> inviteActivityConvert;

    /**
     * 分页查询InviteActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<InviteActivity> query(Pagination<InviteActivity> query) {
        query.setEntityClazz(InviteActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InviteActivity_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(InviteActivity_.createTime)));
        }));
        Page<InviteActivity> page = inviteActivityDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 分页查询: InviteActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<InviteActivityListVo> queryListVo(Pagination<InviteActivity> query) {
        query.setEntityClazz(InviteActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InviteActivity_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(InviteActivity_.createTime)));
        }));
        Page<InviteActivity> pages = inviteActivityDao.findAll(query, query.getPageRequest());
        List<InviteActivityListVo> vos = inviteActivityConvert.toListVos(pages.getContent());
        return new PageImpl<InviteActivityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 分页查询: InviteActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<InviteActivityListVo> queryListVoForApp(Pagination<InviteActivity> query) {
        query.setEntityClazz(InviteActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(InviteActivity_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(InviteActivity_.createTime)));
        }));
        Page<InviteActivity> pages = inviteActivityDao.findAll(query, query.getPageRequest());
        List<InviteActivityListVo> vos = inviteActivityConvert.toListVos(pages.getContent());
        return new PageImpl<InviteActivityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 创建InviteActivity
     **/
    @Override
    public InviteActivity addInviteActivity(InviteActivity inviteActivity) {
        if (inviteActivity.getInviteFlag() == null) {
            inviteActivity.setInviteFlag(ActivityEnum.INVITE_FLAG_REGISTER.getCode());
        }
        InviteActivity dbInviteActivity = inviteActivityDao.save(inviteActivity);
        // 保存邀请奖励
        // invitePrizeService.batchAddInvitePrize(dbInviteActivity,
        // inviteActivity.getInvitePrizes());
        return dbInviteActivity;
    }

    /**
     * 创建InviteActivity
     **/
    @Override
    public InviteActivityListVo addInviteActivity(InviteActivityBo inviteActivityBo) {
        return inviteActivityConvert.toListVo(inviteActivityDao.save(inviteActivityConvert.toEntity(inviteActivityBo)));
    }

    /**
     * 更新InviteActivity
     **/
    @Override
    public InviteActivity updateInviteActivity(InviteActivity inviteActivity) {
        InviteActivity dbInviteActivity = inviteActivityDao.getOne(inviteActivity.getId());
        AttributeReplication.copying(inviteActivity, dbInviteActivity, InviteActivity_.title, InviteActivity_.banner, InviteActivity_.rule, InviteActivity_.startTime,
                InviteActivity_.endTime, InviteActivity_.inviteFlag, InviteActivity_.state);
        // 更新邀请奖励
        // invitePrizeService.batchUpdateInvitePrize(dbInviteActivity,
        // inviteActivity.getInvitePrizes());
        return dbInviteActivity;
    }

    /**
     * 更新InviteActivity
     **/
    @Override
    public InviteActivityListVo updateInviteActivity(InviteActivityBo inviteActivityBo) {
        InviteActivity dbInviteActivity = this.updateInviteActivity(inviteActivityConvert.toEntity(inviteActivityBo));
        return inviteActivityConvert.toListVo(dbInviteActivity);
    }

    /**
     * 删除InviteActivity
     **/
    @Override
    public void removeInviteActivityById(java.lang.Integer id) {
        if (inviteActivityDao.existsById(id)) {
            InviteActivity dbInviteActivity = this.getInviteActivityById(id);
            dbInviteActivity.setDeleted(Deleted.DEL_TRUE);
            dbInviteActivity.setDelTime(new Date());
            // 删除邀请奖励
            invitePrizeService.batchDeletedInvitePrize(dbInviteActivity.getInvitePrizes());
        }
    }

    /**
     * 根据ID得到InviteActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public InviteActivity getInviteActivityById(java.lang.Integer id) {
        return this.inviteActivityDao.getOne(id);
    }

    /**
     * 根据ID得到InviteActivityBo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public InviteActivityBo getInviteActivityBoById(java.lang.Integer id) {
        return inviteActivityConvert.toBo(this.inviteActivityDao.getOne(id));
    }

    /**
     * 根据ID得到InviteActivityVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public InviteActivityVo getInviteActivityVoById(java.lang.Integer id) {
        return inviteActivityConvert.toVo(this.inviteActivityDao.getOne(id));
    }

    /**
     * 根据ID得到InviteActivityListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public InviteActivityListVo getListVoById(java.lang.Integer id) {
        return inviteActivityConvert.toListVo(this.inviteActivityDao.getOne(id));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public InviteActivityVo getLatestInviteActivity(Integer memberId) {
        Optional<InviteActivity> dbInviteActivity = inviteActivityDao.findOne((root, query, cb) -> {
            ListJoin prizeListJoin =root.join(InviteActivity_.invitePrizes, JoinType.LEFT);
            query.orderBy(cb.desc(root.get(InviteActivity_.createTime)),cb.asc(prizeListJoin.get(InvitePrize_.inviteNum)));
            return cb.and(
                    cb.equal(prizeListJoin.get(InvitePrize_.deleted), Deleted.DEL_FALSE),
                    cb.equal(prizeListJoin.get(InvitePrize_.state), BasicEnum.STATE_ENABLE.getCode()),
                    cb.equal(root.get(InviteActivity_.deleted), Deleted.DEL_FALSE),
                    cb.equal(root.get(InviteActivity_.state), BasicEnum.STATE_ENABLE.getCode()),
                    cb.lessThanOrEqualTo(root.get(InviteActivity_.startTime), new Date()),
                    cb.greaterThanOrEqualTo(root.get(InviteActivity_.endTime), new Date())
            );
        });
        if (dbInviteActivity.isPresent()) {
            // 查询邀请人数
//            long inviteNum = 0L;
//            if (ActivityEnum.INVITE_FLAG_REGISTER.getCode().equals(dbInviteActivity.get().getInviteFlag())) {
//                inviteNum = memberService.countInviteNumByMemberAndInviteTime(memberId, dbInviteActivity.get().getStartTime(), dbInviteActivity.get().getEndTime());
//            } else if (ActivityEnum.INVITE_FLAG_BUY.getCode().equals(dbInviteActivity.get().getInviteFlag())) {
//                Member dbMember = memberService.getMemberById(memberId);
//                if (dbMember != null && CollectionUtils.isNotEmpty(dbMember.getChildren())) {
//                    List<Integer> inviteMemberIds = dbMember.getChildren().stream().map(Member::getId).collect(Collectors.toList());
//                    inviteNum = saleOrderService.countInviteNumByMemberAndInviteTime(inviteMemberIds, dbInviteActivity.get().getStartTime(), dbInviteActivity.get().getEndTime());
//                }
//            }
            InviteActivity inviteActivity = dbInviteActivity.get();
            InviteActivityVo inviteActivityVo = new InviteActivityVo();

            if (inviteActivity!=null){
                List<InvitePrize> invitePrizes = inviteActivity.getInvitePrizes();
                ArrayList<InvitePrizeSimple> invitePrizeSimples = new ArrayList<>();
                if (invitePrizes!=null&&invitePrizes.size()>0){
                    for (InvitePrize invite:invitePrizes) {
                        InvitePrizeSimple invitePrizeSimple = new InvitePrizeSimple();  BeanUtils.copyProperties(invite,invitePrizeSimple);
                        if (invite.getCoupon()!=null){ CouponSimple couponSimple = new CouponSimple();  BeanUtils.copyProperties(invite.getCoupon(),couponSimple); invitePrizeSimple.setCoupon(couponSimple); }
                        if (invite.getCommodity()!=null){ CommoditySimple commoditySimple = new CommoditySimple();BeanUtils.copyProperties(invite.getCommodity(),commoditySimple);invitePrizeSimple.setCommodity(commoditySimple); }
                        if (invite.getProduct()!=null){ ProductSimple productSimple = new ProductSimple();BeanUtils.copyProperties(invite.getProduct(),productSimple);invitePrizeSimple.setProduct(productSimple); }
                        if (invite.getInviteActivity()!=null){ InviteActivitySimple inviteActivitySimple = new InviteActivitySimple(); BeanUtils.copyProperties(invite.getInviteActivity(),inviteActivitySimple);invitePrizeSimple.setInviteActivity(inviteActivitySimple); }
                        invitePrizeSimples.add(invitePrizeSimple);
                    }
                }
                BeanUtils.copyProperties(inviteActivity,inviteActivityVo);
                    inviteActivityVo.setInvitePrizes(invitePrizeSimples);
            }else {
                 inviteActivityVo = inviteActivityConvert.toVo(inviteActivity);
            }
            Member dbMember = memberService.getMemberById(memberId);
            List<InvitePrizeSimple> prizeSimples=new ArrayList<>();
            // 遍历奖品 检验是否可以领取
            for (InvitePrizeSimple tmp : inviteActivityVo.getInvitePrizes()) {
                if(tmp.getState() == ActivityEnum.STATE_ENABLE.getCode()){
                    if (tmp.getInviteNum() <= dbMember.getInviteNum()) {
                        tmp.setReceive(Boolean.TRUE);
                    }
                    switch (tmp.getPrizeType()) {
                        case 1:
                            boolean integralReceived = integralRecordService.checkReceivePrize(memberId, tmp.getIntegral(), tmp.getId());
                            tmp.setReceived(false);             // tmp.setReceived(integralReceived);
                            break;
                        case 2:
                            boolean couponCommodity = saleOrderService.checkReceivePrizeForProduct(memberId, tmp.getProduct().getId());
                            tmp.setReceived(false);                                  //tmp.setReceived(couponCommodity);
                            break;
                        case 3:
                            boolean couponReceived = couponReceiveService.checkReceivePrizeForCoupon(memberId, tmp.getCoupon().getId());
                            tmp.setReceived(false);          //     tmp.setReceived(couponReceived);
                            break;
                    }
                    prizeSimples.add(tmp);
                }
            }
            inviteActivityVo.setInvitePrizes(prizeSimples);
            return inviteActivityVo;
        }
        return null;
    }

    public EntityListVoBoSimpleConvert<InviteActivity, InviteActivityBo, InviteActivityVo, InviteActivitySimple, InviteActivityListVo> getInviteActivityConvert() {
        return inviteActivityConvert;
    }

    protected void initConvert() {
        this.inviteActivityConvert = new EntityListVoBoSimpleConvert<InviteActivity, InviteActivityBo, InviteActivityVo, InviteActivitySimple, InviteActivityListVo>(
                beanConvertManager) {
            @Override
            protected BeanConvert<InviteActivity, InviteActivityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivity, InviteActivityVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(InviteActivityVo inviteActivityVo, InviteActivity inviteActivity) {
                    }
                };
            }

            @Override
            protected BeanConvert<InviteActivity, InviteActivityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivity, InviteActivityListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(InviteActivityListVo inviteActivityListVo, InviteActivity inviteActivity) {
                    }
                };
            }

            @Override
            protected BeanConvert<InviteActivity, InviteActivityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivity, InviteActivityBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(InviteActivityBo inviteActivityBo, InviteActivity inviteActivity) {
                    }
                };
            }

            @Override
            protected BeanConvert<InviteActivityBo, InviteActivity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivityBo, InviteActivity>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<InviteActivity, InviteActivitySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivity, InviteActivitySimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<InviteActivitySimple, InviteActivity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<InviteActivitySimple, InviteActivity>(beanConvertManager) {
                    @Override
                    public InviteActivity convert(InviteActivitySimple inviteActivitySimple) throws BeansException {
                        return inviteActivityDao.getOne(inviteActivitySimple.getId());
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
