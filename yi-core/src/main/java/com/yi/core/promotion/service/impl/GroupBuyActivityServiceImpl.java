/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.yi.core.cms.domain.entity.RecommendCommodity_;
import com.yi.core.cms.domain.entity.Recommend_;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.entity.Commodity_;
import com.yi.core.promotion.dao.GroupBuyActivityProductDao;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.simple.GroupBuyActivityProductSimple;
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

import com.yi.core.auth.dao.UserDao;
import com.yi.core.commodity.dao.ProductDao;
import com.yi.core.common.Deleted;
import com.yi.core.member.domain.bo.MemberLevelBo;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.simple.MemberLevelSimple;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberLevelVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.promotion.PromotionEnum;
import com.yi.core.promotion.dao.GroupBuyActivityDao;
import com.yi.core.promotion.domain.bo.GroupBuyActivityBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityTime;
import com.yi.core.promotion.domain.entity.GroupBuyActivity_;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityListVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivitySimple;
import com.yi.core.promotion.domain.vo.GroupBuyActivityVo;
import com.yi.core.promotion.service.IGroupBuyActivityMemberService;
import com.yi.core.promotion.service.IGroupBuyActivityProductService;
import com.yi.core.promotion.service.IGroupBuyActivityRuleService;
import com.yi.core.promotion.service.IGroupBuyActivityService;
import com.yi.core.promotion.service.IGroupBuyActivityTimeService;
import com.yi.core.promotion.service.IGroupBuyOrderService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 团购活动
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GroupBuyActivityServiceImpl implements IGroupBuyActivityService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private GroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private GroupBuyActivityProductDao groupBuyActivityProductDao;

    @Resource
    private ProductDao productDao;

    @Resource
    private UserDao userDao;

    @Resource
    private IMemberLevelService memberLevelService;

    @Resource
    private IGroupBuyActivityMemberService groupBuyActivityMemberService;

    @Resource
    private IGroupBuyActivityTimeService groupBuyActivityTimeService;

    @Resource
    private IGroupBuyActivityRuleService groupBuyActivityRuleService;

    @Resource
    private IGroupBuyActivityProductService groupBuyActivityProductService;

    @Resource
    private IGroupBuyOrderService groupBuyOrderService;

    private EntityListVoBoSimpleConvert<GroupBuyActivity, GroupBuyActivityBo, GroupBuyActivityVo, GroupBuyActivitySimple, GroupBuyActivityListVo> groupBuyActivityConvert;

    private EntityListVoBoSimpleConvert<MemberLevel, MemberLevelBo, MemberLevelVo, MemberLevelSimple, MemberLevelListVo> memberLevelConvert;

    /**
     * 分页查询GroupBuyActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<GroupBuyActivity> query(Pagination<GroupBuyActivity> query) {
        query.setEntityClazz(GroupBuyActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(GroupBuyActivity_.createTime)));
        }));
        Page<GroupBuyActivity> page = groupBuyActivityDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 分页查询: GroupBuyActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<GroupBuyActivityListVo> queryListVo(Pagination<GroupBuyActivity> query) {
        query.setEntityClazz(GroupBuyActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(GroupBuyActivity_.createTime)));
        }));
        Page<GroupBuyActivity> pages = groupBuyActivityDao.findAll(query, query.getPageRequest());
        List<GroupBuyActivityListVo> vos = groupBuyActivityConvert.toListVos(pages.getContent());
        return new PageImpl<GroupBuyActivityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 分页查询: GroupBuyActivity
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<GroupBuyActivityListVo> queryListVoForApp(Pagination<GroupBuyActivity> query) {
        query.setEntityClazz(GroupBuyActivity.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.deleted), Deleted.DEL_FALSE)));
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.audited), PromotionEnum.AUDIT_STATE_AUDITED.getCode())));
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.published), PromotionEnum.PUBLISH_STATE_PUBLISHED.getCode())));
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(GroupBuyActivity_.finished), PromotionEnum.FINISH_STATE_UNFINISHED.getCode())));
            list1.add(criteriaBuilder.asc(root.get(GroupBuyActivity_.priority)));
            list1.add(criteriaBuilder.desc(root.get(GroupBuyActivity_.createTime)));

        }));
        Page<GroupBuyActivity> pages = groupBuyActivityDao.findAll(query, query.getPageRequest());
        pages.getContent().forEach(tmp -> {
            tmp.setAuditUser(null);
            tmp.setPublishUser(null);
            tmp.getGroupBuyActivityProducts().forEach(tmp2 -> {
                tmp2.getCommodity().setSupplier(null);
                tmp2.getCommodity().setCategory(null);
                tmp2.getCommodity().setComments(null);
                tmp2.getCommodity().setFreightTemplate(null);
            });
        });
        List<GroupBuyActivityListVo> vos = groupBuyActivityConvert.toListVos(pages.getContent());
        vos.forEach(tmp -> {
            tmp.getGroupBuyActivityProducts().forEach(tmp2 -> {
                tmp2.setOpenGroupBuys(groupBuyOrderService.getByGroupBuyProductId(tmp2.getId()));
            });
        });
        return new PageImpl<GroupBuyActivityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 创建GroupBuyActivity
     **/
    @Override
    public GroupBuyActivity addGroupBuyActivity(GroupBuyActivity groupBuyActivity) {
        if (groupBuyActivity == null || CollectionUtils.isEmpty(groupBuyActivity.getGroupBuyActivityProducts())
                || CollectionUtils.isEmpty(groupBuyActivity.getGroupBuyActivityTimes())) {
            throw new BusinessException("提交数据不能为空");
        }
        if (groupBuyActivity.getAudited() == null) {
            groupBuyActivity.setAudited(PromotionEnum.AUDIT_STATE_UNAUDITED.getCode());
        }
        if (groupBuyActivity.getPublished() == null) {
            groupBuyActivity.setPublished(PromotionEnum.PUBLISH_STATE_UNPUBLISHED.getCode());
        }
        if (groupBuyActivity.getFinished() == null) {
            groupBuyActivity.setFinished(PromotionEnum.FINISH_STATE_UNFINISHED.getCode());
        }
        GroupBuyActivity dbGroupBuyActivity = groupBuyActivityDao.save(groupBuyActivity);
        // 保存活动商品
        groupBuyActivityProductService.batchAddGroupBuyActivityProduct(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityProducts());
        // 保存活动规则
        groupBuyActivityRuleService.batchAddGroupBuyActivityRule(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityRules());
        // 保存活动时间
        groupBuyActivityTimeService.batchAddGroupBuyActivityTime(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityTimes());
        // // 保存活动会员
        // groupBuyActivity.getGroupBuyActivityMember().setGroupBuyActivity(groupBuyActivity);
        groupBuyActivityMemberService.batchAddGroupBuyActivityMember(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityMembers());
        return dbGroupBuyActivity;
    }

    /**
     * 创建GroupBuyActivity
     **/
    @Override
    public GroupBuyActivityListVo addGroupBuyActivity(GroupBuyActivityBo groupBuyActivityBo) {
        return groupBuyActivityConvert.toListVo(this.addGroupBuyActivity(groupBuyActivityConvert.toEntity(groupBuyActivityBo)));
    }

    /**
     * 更新GroupBuyActivity
     **/
    @Override
    public GroupBuyActivity updateGroupBuyActivity(GroupBuyActivity groupBuyActivity) {
        if (groupBuyActivity == null || CollectionUtils.isEmpty(groupBuyActivity.getGroupBuyActivityProducts())
                || CollectionUtils.isEmpty(groupBuyActivity.getGroupBuyActivityTimes())) {
            throw new BusinessException("提交数据不能为空");
        }
        if (groupBuyActivity.getAudited() == null) {
            groupBuyActivity.setAudited(PromotionEnum.AUDIT_STATE_UNAUDITED.getCode());
        }
        if (groupBuyActivity.getPublished() == null) {
            groupBuyActivity.setPublished(PromotionEnum.PUBLISH_STATE_UNPUBLISHED.getCode());
        }
        if (groupBuyActivity.getFinished() == null) {
            groupBuyActivity.setFinished(PromotionEnum.FINISH_STATE_UNFINISHED.getCode());
        }
        GroupBuyActivity dbGroupBuyActivity = groupBuyActivityDao.getOne(groupBuyActivity.getId());
        AttributeReplication.copying(groupBuyActivity, dbGroupBuyActivity, GroupBuyActivity_.activityName, GroupBuyActivity_.priority, GroupBuyActivity_.type,
                GroupBuyActivity_.promotionType, GroupBuyActivity_.sponsor, GroupBuyActivity_.coverUrl, GroupBuyActivity_.hasPost, GroupBuyActivity_.hasCoupon,
                GroupBuyActivity_.stockState, GroupBuyActivity_.remark);

        // 修改活动商品
        groupBuyActivityProductService.batchUpdateGroupBuyActivityProduct(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityProducts());
        // 修改活动规则
        groupBuyActivityRuleService.batchUpdateGroupBuyActivityRule(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityRules());
        // 修改活动时间
        groupBuyActivityTimeService.batchUpdateGroupBuyActivityTime(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityTimes());
        // 修改活动会员
        groupBuyActivityMemberService.batchUpdateGroupBuyActivityMember(dbGroupBuyActivity, groupBuyActivity.getGroupBuyActivityMember());
        return dbGroupBuyActivity;
    }

    /**
     * 更新GroupBuyActivity
     **/
    @Override
    public GroupBuyActivityListVo updateGroupBuyActivity(GroupBuyActivityBo groupBuyActivityBo) {
        GroupBuyActivity dbGroupBuyActivity = this.updateGroupBuyActivity(groupBuyActivityConvert.toEntity(groupBuyActivityBo));
        return groupBuyActivityConvert.toListVo(dbGroupBuyActivity);
    }

    /**
     * 删除GroupBuyActivity
     **/
    @Override
    public void removeGroupBuyActivityById(int id) {
        GroupBuyActivity groupBuyActivity = this.getById(id);
        if (groupBuyActivity != null) {
            groupBuyActivity.setDeleted(Deleted.DEL_TRUE);
            groupBuyActivity.setDelTime(new Date());
        }
    }

    /**
     * 根据ID得到GroupBuyActivityBo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GroupBuyActivity getById(int id) {
        if (groupBuyActivityDao.existsById(id)) {
            return this.groupBuyActivityDao.getOne(id);
        }
        return null;
    }

    /**
     * 根据ID得到GroupBuyActivityBo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GroupBuyActivityBo getBoById(int id) {
        return groupBuyActivityConvert.toBo(this.groupBuyActivityDao.getOne(id));
    }

    /**
     * 根据ID得到GroupBuyActivityVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GroupBuyActivityVo getVoById(int id) {
        GroupBuyActivity dbGroupBuyActivity = this.getById(id);
        if (dbGroupBuyActivity != null) {
            dbGroupBuyActivity.getGroupBuyActivityProducts().forEach(tmp -> {
                tmp.getCommodity().setCategory(null);
                tmp.getCommodity().setSupplier(null);
                tmp.getCommodity().setProducts(null);
                tmp.getCommodity().setCouponGrantConfig(null);
                tmp.getCommodity().setCommodityLevelDiscounts(null);
                tmp.getCommodity().setComments(null);
                tmp.getCommodity().setFreightTemplate(null);
                tmp.getProduct().setAttributes(null);
                tmp.getProduct().setCommodity(null);
            });
            return groupBuyActivityConvert.toVo(dbGroupBuyActivity);
        }
        return null;
    }

    /**
     * 根据ID得到GroupBuyActivityListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GroupBuyActivityListVo getListVoById(int id) {
        return groupBuyActivityConvert.toListVo(this.groupBuyActivityDao.getOne(id));
    }

    /**
     * 团购活动审批
     *
     * @param groupBuyActivityId
     * @param userId
     * @return
     */
    @Override
    public GroupBuyActivityVo auditingGroupBuyActivity(int groupBuyActivityId, int userId) {
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.getOne(groupBuyActivityId);
        groupBuyActivity.setAudited(PromotionEnum.AUDIT_STATE_AUDITED.getCode());
        groupBuyActivity.setAuditUser(userDao.getOne(userId));
        groupBuyActivity.setAuditDate(new Date());
        GroupBuyActivity dbGroupBuyActivity = groupBuyActivityDao.getOne(groupBuyActivityId);
        AttributeReplication.copying(groupBuyActivity, dbGroupBuyActivity, GroupBuyActivity_.activityName, GroupBuyActivity_.priority, GroupBuyActivity_.type,
                GroupBuyActivity_.promotionType, GroupBuyActivity_.sponsor, GroupBuyActivity_.coverUrl, GroupBuyActivity_.hasPost, GroupBuyActivity_.hasCoupon,
                GroupBuyActivity_.stockState, GroupBuyActivity_.remark, GroupBuyActivity_.audited, GroupBuyActivity_.auditUser, GroupBuyActivity_.auditDate);
        return groupBuyActivityConvert.toVo(groupBuyActivity);
    }

    protected void initConvert() {
        this.groupBuyActivityConvert = new EntityListVoBoSimpleConvert<GroupBuyActivity, GroupBuyActivityBo, GroupBuyActivityVo, GroupBuyActivitySimple, GroupBuyActivityListVo>(
                beanConvertManager) {
            @Override
            protected BeanConvert<GroupBuyActivity, GroupBuyActivityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivity, GroupBuyActivityVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(GroupBuyActivityVo groupBuyActivityVo, GroupBuyActivity groupBuyActivity) {
                    }
                };
            }

            @Override
            protected BeanConvert<GroupBuyActivity, GroupBuyActivityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivity, GroupBuyActivityListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(GroupBuyActivityListVo groupBuyActivityListVo, GroupBuyActivity groupBuyActivity) {
                        // 检查当前活动的团购
                        if (CollectionUtils.isNotEmpty(groupBuyActivity.getGroupBuyActivityTimes())) {
                            for (GroupBuyActivityTime tmpTime : groupBuyActivity.getGroupBuyActivityTimes()) {
                                if (tmpTime.getStartTime().before(new Date()) && tmpTime.getEndTime().after(new Date())) {
                                    groupBuyActivityListVo.setStartTime(tmpTime.getStartTime());
                                    groupBuyActivityListVo.setEndTime(tmpTime.getEndTime());
                                    break;
                                }
                            }
                        }
                    }
                };
            }

            @Override
            protected BeanConvert<GroupBuyActivity, GroupBuyActivityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivity, GroupBuyActivityBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(GroupBuyActivityBo groupBuyActivityBo, GroupBuyActivity groupBuyActivity) {
                    }
                };
            }

            @Override
            protected BeanConvert<GroupBuyActivityBo, GroupBuyActivity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivityBo, GroupBuyActivity>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<GroupBuyActivity, GroupBuyActivitySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivity, GroupBuyActivitySimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<GroupBuyActivitySimple, GroupBuyActivity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<GroupBuyActivitySimple, GroupBuyActivity>(beanConvertManager) {
                    @Override
                    public GroupBuyActivity convert(GroupBuyActivitySimple groupBuyActivitySimple) throws BeansException {
                        return groupBuyActivityDao.getOne(groupBuyActivitySimple.getId());
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
     * 定时发布团购活动
     */
    @Override
    public void autoPublishGroupBuyActivityByTask() {
        // 查询未发布的 团购活动
        List<GroupBuyActivity> dbGroupBuyActivities = groupBuyActivityDao.findByAuditedAndPublishedAndFinishedAndDeleted(PromotionEnum.AUDIT_STATE_AUDITED.getCode(),
                PromotionEnum.PUBLISH_STATE_UNPUBLISHED.getCode(), PromotionEnum.FINISH_STATE_UNFINISHED.getCode(), Deleted.DEL_FALSE);
        if (CollectionUtils.isEmpty(dbGroupBuyActivities)) {
            LOG.info("没有需要发布的活动");
        }
        for (GroupBuyActivity tmp : dbGroupBuyActivities) {
            if (tmp != null && CollectionUtils.isNotEmpty(tmp.getGroupBuyActivityTimes())) {
                boolean flag = false;
                for (GroupBuyActivityTime tmp2 : tmp.getGroupBuyActivityTimes()) {
                    if (tmp2.getStartTime().before(new Date()) && tmp2.getEndTime().after(new Date())) {
                        flag = true;
                        break;
                    }
                }
                // 时间到 发布活动
                if (flag) {
                    tmp.setPublishDate(new Date());
                    tmp.setPublished(PromotionEnum.PUBLISH_STATE_PUBLISHED.getCode());
                }
            }
        }
    }

    @Override
    public boolean manualPublishActivity(int activityId) {
        Optional<GroupBuyActivity> groupBuyActivity = groupBuyActivityDao.findOne((root, query, cb) -> {
            return cb.and(
                    cb.equal(root.get(GroupBuyActivity_.deleted), Deleted.DEL_FALSE),
                    cb.equal(root.get(GroupBuyActivity_.audited), PromotionEnum.AUDIT_STATE_AUDITED.getCode()),
                    cb.equal(root.get(GroupBuyActivity_.published), PromotionEnum.PUBLISH_STATE_UNPUBLISHED.getCode()),
                    cb.equal(root.get(GroupBuyActivity_.finished), PromotionEnum.FINISH_STATE_UNFINISHED.getCode()),
                    cb.equal(root.get(GroupBuyActivity_.id), activityId)
            );
        });
        boolean published=false;
        if (groupBuyActivity.isPresent()) {
            GroupBuyActivity activity = groupBuyActivity.get();
            if (CollectionUtils.isNotEmpty(activity.getGroupBuyActivityTimes())) {
                boolean flag = false;
                for (GroupBuyActivityTime tmp2 : activity.getGroupBuyActivityTimes()) {
                    if (tmp2.getStartTime().before(new Date()) && tmp2.getEndTime().after(new Date())) {
                        flag = true;
                        break;
                    }
                }
                // 时间到 发布活动
                if (flag) {
                    activity.setPublishDate(new Date());
                    activity.setPublished(PromotionEnum.PUBLISH_STATE_PUBLISHED.getCode());
                    published = true;
                }
            }
        }
        return published;
    }

    /**
     * 定时终结活动
     */
    @Override
    public void autoFinishGroupBuyActivityByTask() {
        // 查询未发布的 团购活动
        List<GroupBuyActivity> dbGroupBuyActivities = groupBuyActivityDao.findByAuditedAndPublishedAndFinishedAndDeleted(PromotionEnum.AUDIT_STATE_AUDITED.getCode(),
                PromotionEnum.PUBLISH_STATE_PUBLISHED.getCode(), PromotionEnum.FINISH_STATE_UNFINISHED.getCode(), Deleted.DEL_FALSE);
        if (CollectionUtils.isEmpty(dbGroupBuyActivities)) {
            LOG.info("没有需要终结的活动");
        }
        for (GroupBuyActivity tmp : dbGroupBuyActivities) {
            if (tmp != null && CollectionUtils.isNotEmpty(tmp.getGroupBuyActivityTimes())) {
                boolean flag = true;
                for (GroupBuyActivityTime tmp2 : tmp.getGroupBuyActivityTimes()) {
                    if (tmp2.getStartTime().before(new Date()) && tmp2.getEndTime().after(new Date())) {
                        flag = false;
                        break;
                    }
                }
                // 团购时间结束 终结活动
                if (flag) {
                    tmp.setFinished(PromotionEnum.FINISH_STATE_END.getCode());
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public GroupBuyActivityListVo getLatestGroupBuyActivity() {
        GroupBuyActivityListVo groupBuyActivityListVo = groupBuyActivityConvert.toListVo(this.getLatestActivity());
        if (null != groupBuyActivityListVo) {
            List<GroupBuyActivityProductSimple> productSimples = this.getActivityProductsByActivityId(groupBuyActivityListVo.getId());
            groupBuyActivityListVo.setGroupBuyActivityProducts(new HashSet<>(productSimples));
        }
        return groupBuyActivityListVo;
    }

    private GroupBuyActivity getLatestActivity() {
        List<GroupBuyActivity> groupBuyActivities = groupBuyActivityDao.findAll((root, query, cb) -> {
            query.orderBy(cb.asc(root.get(GroupBuyActivity_.priority)), cb.desc(root.get(GroupBuyActivity_.createTime)));
            return cb.and(
                    cb.equal(root.get(GroupBuyActivity_.deleted), Deleted.DEL_FALSE),
                    cb.equal(root.get(GroupBuyActivity_.audited), PromotionEnum.AUDIT_STATE_AUDITED.getCode()),
                    cb.equal(root.get(GroupBuyActivity_.published), PromotionEnum.PUBLISH_STATE_PUBLISHED.getCode()),
                    cb.equal(root.get(GroupBuyActivity_.finished), PromotionEnum.FINISH_STATE_UNFINISHED.getCode())
            );
        });
        return CollectionUtils.isNotEmpty(groupBuyActivities) ? groupBuyActivities.get(0) : null;
    }


    private List<GroupBuyActivityProductSimple> getActivityProductsByActivityId(int activityId) {
        List<GroupBuyActivityProduct> products = groupBuyActivityProductDao.findByGroupBuyActivity_Id(activityId);
        if (CollectionUtils.isNotEmpty(products) && products.size() > 3) {
            products = products.subList(0, 3);
        }
        List<GroupBuyActivityProductSimple> productSimples = groupBuyActivityProductService.getGroupBuyActivityProductConvert().toSimples(products);
        return productSimples;
    }
}
