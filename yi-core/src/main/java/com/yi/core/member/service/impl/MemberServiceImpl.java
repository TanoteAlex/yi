/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.entity.Reward;
import com.yi.core.activity.service.IRewardService;
import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.domain.entity.Community;
import com.yi.core.basic.domain.entity.Community_;
import com.yi.core.basic.service.ICommunityService;
import com.yi.core.basic.service.IIntegralTaskService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.common.Deleted;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.dao.MemberDao;
import com.yi.core.member.domain.bo.MemberBo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.MemberCommission;
import com.yi.core.member.domain.entity.MemberLevel;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.domain.simple.MemberSimple;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.domain.vo.MemberListVo;
import com.yi.core.member.domain.vo.MemberVo;
import com.yi.core.member.service.IAccountService;
import com.yi.core.member.service.IDistributionLevelService;
import com.yi.core.member.service.ILoginRecordService;
import com.yi.core.member.service.IMemberCommissionService;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
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
import com.yihz.common.utils.date.DateUtils;

/**
 * 会员
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class MemberServiceImpl implements IMemberService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private MemberDao memberDao;

    @Resource
    private ICommunityService communityService;

    @Resource
    private IIntegralTaskService integralTaskService;

    @Resource
    private IMemberLevelService memberLevelService;

    @Resource
    private IAccountService accountService;

    // @Resource
    // private IAccountRecordService accountRecordService;

    @Resource
    private IDistributionLevelService distributionLevelService;

    @Resource
    private IMemberCommissionService memberCommissionService;

    @Resource
    private IRewardService rewardService;

    @Resource
    private ILoginRecordService loginRecordService;

    @Resource
    private ISaleOrderService saleOrderService;

    private EntityListVoBoSimpleConvert<Member, MemberBo, MemberVo, MemberSimple, MemberListVo> memberConvert;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Member> query(Pagination<Member> query) {
        query.setEntityClazz(Member.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE)));
            list1.add(criteriaBuilder.desc(root.get(Member_.createTime)));
        }));
        Page<Member> page = memberDao.findAll(query, query.getPageRequest());
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<MemberListVo> queryListVo(Pagination<Member> query) {
        Page<Member> pages = this.query(query);
        List<MemberListVo> vos = memberConvert.toListVos(pages.getContent());
        return new PageImpl<MemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<MemberListVo> queryListVoForCommunity(Pagination<Member> query) {
        query.setEntityClazz(Member.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE)));
            list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.state), MemberEnum.STATE_ENABLE.getCode())));
            list1.add(criteriaBuilder.desc(root.get(Member_.createTime)));
            // 小区
            Object communityId = query.getParams().get("community.id");
            if (communityId != null) {
                list.add(criteriaBuilder.equal(root.get(Member_.community).get(Community_.id), communityId));
            } else {
                list.add(criteriaBuilder.equal(root.get(Member_.community).get(Community_.id), 0));
            }
        }));
        Page<Member> pages = memberDao.findAll(query, query.getPageRequest());
        List<MemberListVo> vos = memberConvert.toListVos(pages.getContent());
        return new PageImpl<MemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    /**
     * 查询我的团队
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<MemberListVo> queryMyTeam(Pagination<Member> query, Integer teamType) {
        query.setEntityClazz(Member.class);
        query.setPredicate(((root, criteriaQuery, criteriaBuilder, predicates, orders) -> {
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE)));
            Object memberId = query.getParams().get("member.id");
            if (memberId != null) {
                // 查询一级团队
                if (MemberEnum.DISTRIBUTION_LEVEL_FIRST.getCode().equals(teamType)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.parent).get(Member_.id), memberId)));
                    // 查询二级团队
                } else if (MemberEnum.DISTRIBUTION_LEVEL_SECOND.getCode().equals(teamType)) {
                    Member dbMember = this.getMemberById((Integer) memberId);
                    if (dbMember != null) {
                        Path<Integer> path = root.get(Member_.parent).get(Member_.id);
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(path);
                        if (CollectionUtils.isNotEmpty(dbMember.getChildren())) {
                            dbMember.getChildren().forEach(e -> {
                                in.value(e.getId());
                            });
                        } else {
                            in.value(0);
                        }
                        predicates.add(criteriaBuilder.and(in));
                    }
                    // 不查询数据
                } else {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.id), null)));
                }
            } else {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Member_.id), null)));
            }
        }));
        Page<Member> pages = memberDao.findAll(query, query.getPageRequest());
        List<MemberListVo> vos = new ArrayList<>();
        for (Member member : pages.getContent()) {
            if (member != null) {
                MemberListVo memberListVo = memberConvert.toListVo(member);
                memberListVo.setPromotionNum(Optional.ofNullable(member.getChildren()).map(e -> e.size()).orElse(0));
                vos.add(memberListVo);
            }
        }
        return new PageImpl<MemberListVo>(vos, query.getPageRequest(), pages.getTotalElements());
    }

    // @Cacheable(value = "member", key = "#root.args[0]", unless = "#result eq
    // null")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Member getMemberById(Integer memberId) {
        if (memberDao.existsById(memberId)) {
            return memberDao.getOne(memberId);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public MemberVo getMemberVoById(Integer memberId) {
        Member dbMember = this.getMemberById(memberId);
        if (dbMember != null) {
            MemberVo memberVo = memberConvert.toVo(dbMember);
            if (memberVo.getParent() != null) {
                memberVo.setParentId(memberVo.getParent().getId());
                memberVo.setParentName(memberVo.getParent().getUsername());
            } else {
                memberVo.setParentName(null);
            }
            return memberVo;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public MemberVo getMemberVoByIdForApp(Integer memberId) {
        Member dbMember = this.getMemberById(memberId);
        if (dbMember != null && Deleted.DEL_FALSE.equals(dbMember.getDeleted())) {
            dbMember.setAccountRecords(null);
            MemberVo memberVo = memberConvert.toVo(dbMember);
            if (memberVo.getParent() != null) {
                memberVo.setParentName(memberVo.getParent().getUsername());
            } else {
                memberVo.setParentName(null);
            }
            return memberVo;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public MemberListVo getMemberListVoById(Integer memberId) {
        return memberConvert.toListVo(this.getMemberById(memberId));
    }

    /**
     * 注册会员
     */
    @Override
    public Member addMember(Member member) {
        if (member == null) {
            throw new BusinessException("提交数据不能为空");
        }
        List<Member> checkMembers = memberDao.findByUsername(member.getUsername());
        if (checkMembers.size() > 0) {
            throw new BusinessException("该账户已被注册");
        }
        return memberDao.save(member);
    }

    @Override
    public MemberListVo addMember(MemberBo memberBo) {
        return memberConvert.toListVo(memberDao.save(memberConvert.toEntity(memberBo)));
    }

    @Override
    public Member updateMember(Member member) {
        Member dbMember = this.getMemberById(member.getId());
        AttributeReplication.copying(member, dbMember, Member_.parent, Member_.memberLevel, Member_.community, Member_.province, Member_.city, Member_.district, Member_.address);
        return dbMember;
    }

    @Override
    public MemberListVo updateMember(MemberBo memberBo) {
        return memberConvert.toListVo(this.updateMember(memberConvert.toEntity(memberBo)));
    }

    @Override
    public MemberListVo resetPassword(MemberBo memberBo) {
        Member dbMember = this.getMemberById(memberBo.getId());
        if (dbMember != null) {
            dbMember.setPassword(dbMember.getPhone().substring(5));
        }
        return memberConvert.toListVo(dbMember);
    }

    @Override
    public void removeMemberById(int memberId) {
        Member dbMember = this.getMemberById(memberId);
        if (dbMember != null) {
            dbMember.setDeleted(Deleted.DEL_TRUE);
            dbMember.setDelTime(new Date());
        }
    }

    protected void initConvert() {
        this.memberConvert = new EntityListVoBoSimpleConvert<Member, MemberBo, MemberVo, MemberSimple, MemberListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<Member, MemberVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Member, MemberVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(MemberVo memberVo, Member member) {
                        memberVo.setPassword(null);
                        memberVo.setPayPassword(null);
                    }
                };
            }

            @Override
            protected BeanConvert<Member, MemberListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Member, MemberListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(MemberListVo memberListVo, Member member) {
                        memberListVo.setPassword(null);
                        memberListVo.setPayPassword(null);
                        memberListVo.setPromotionNum(member.getChildren().size());
                    }
                };
            }

            @Override
            protected BeanConvert<Member, MemberBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Member, MemberBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(MemberBo memberBo, Member member) {
                        memberBo.setPassword(null);
                        memberBo.setPayPassword(null);
                    }
                };
            }

            @Override
            protected BeanConvert<MemberBo, Member> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<MemberBo, Member>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<Member, MemberSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<Member, MemberSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<MemberSimple, Member> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert<MemberSimple, Member>(beanConvertManager) {
                    @Override
                    public Member convert(MemberSimple MemberSimple) throws BeansException {
                        return memberDao.getOne(MemberSimple.getId());
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
    public MemberVo loginByPassword(MemberBo memberBo) {
        if (StringUtils.isAnyBlank(memberBo.getPhone(), memberBo.getPassword())) {
            throw new BusinessException("手机号或密码不能为空");
        }
        Member dbMember = memberDao.findByPasswordAndPhoneAndDeleted(memberBo.getPassword(), memberBo.getPhone(), Deleted.DEL_FALSE);
        if (dbMember == null) {
            throw new BusinessException("手机号或密码错误");
        }
        if (MemberEnum.STATE_DISABLE.getCode().equals(dbMember.getState())) {
            throw new BusinessException("您已被禁用，请联系客服处理");
        }
        if (memberBo.isLoginByApp()) {
            dbMember.setMemberLevel(memberLevelService.getVipMemberLevel());
        }
        // 添加登录日志
        loginRecordService.addLoginRecordByLogin(dbMember, memberBo.getLoginSource());
        dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        return memberConvert.toVo(dbMember);
    }

    /**
     * 通过手机号和验证码登录
     */
    @Override
    public MemberVo loginBySms(MemberBo memberBo) {
        Member dbMember = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
        if (dbMember == null) {
            memberBo.setPassword("123456");
            // 如果未注册 自动注册
            return this.registerByWeChat(memberBo);
        }
        if (MemberEnum.STATE_DISABLE.getCode().equals(dbMember.getState())) {
            throw new BusinessException("您已被禁用，请联系客服处理");
        }
        if (memberBo.isLoginByApp()) {
            dbMember.setMemberLevel(memberLevelService.getVipMemberLevel());
        }
        // 添加登录日志
        loginRecordService.addLoginRecordByLogin(dbMember, memberBo.getLoginSource());
        dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        return memberConvert.toVo(dbMember);
    }

    @Override
    public MemberListVo changePhone(MemberBo memberBo) {
        Member member = this.getMemberById(memberBo.getId());
        member.setPhone(memberBo.getPhone());
        member.setUsername(memberBo.getPhone());
        return memberConvert.toListVo(member);
    }

    @Override
    public MemberListVo changeMember(MemberBo memberBo) {
        Member member = memberConvert.toEntity(memberBo);
        Member dbMember = this.getMemberById(member.getId());
        AttributeReplication.copying(member, dbMember, Member_.avater, Member_.nickname, Member_.sex, Member_.birthday, Member_.province, Member_.city, Member_.district,
                Member_.address);
        return memberConvert.toListVo(dbMember);
    }

    @Override
    public MemberVo changePwd(MemberBo memberBo) {
        Member member = this.getMemberById(memberBo.getId());
        if (MemberEnum.STATE_DISABLE.getCode().equals(member.getState())) {
            throw new BusinessException("您已被禁用，请联系客服处理");
        }
        member.setPassword(memberBo.getPassword());
        return memberConvert.toVo(member);
    }

    @Override
    public MemberVo forgetPassword(MemberBo memberBo) {
        Member member = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
        if (member == null) {
            throw new BusinessException("该账号还未注册，请注册");
        }
        if (MemberEnum.STATE_DISABLE.getCode().equals(member.getState())) {
            throw new BusinessException("您已被禁用，请联系客服处理");
        }
        member.setPassword(memberBo.getPassword());
        return memberConvert.toVo(member);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getMyTeamNum(int memberId) {
        int myTeamNum = 0;
        List<Member> myTeam = memberDao.findByParent_idAndDeleted(memberId, Deleted.DEL_FALSE);
        if (CollectionUtils.isNotEmpty(myTeam)) {
            myTeamNum += myTeam.size();
            for (Member tmp : myTeam) {
                if (tmp != null && CollectionUtils.isNotEmpty(tmp.getChildren())) {
                    myTeamNum += tmp.getChildren().size();
                }
            }
        }
        return myTeamNum;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public MemberVo getBalance(int memberId) {
        return memberConvert.toVo(this.getMemberById(memberId));
    }

    @Override
    public MemberVo updateState(int memberId) {
        Member member = this.getMemberById(memberId);
        Integer state = member.getState();
        if (MemberEnum.STATE_DISABLE.getCode().equals(state)) {
            member.setState(MemberEnum.STATE_ENABLE.getCode());
        } else if (MemberEnum.STATE_ENABLE.getCode().equals(state)) {
            member.setState(MemberEnum.STATE_DISABLE.getCode());
        }
        return memberConvert.toVo(member);
    }

    /**
     * 修改支付密码
     *
     * @param memberBo
     * @return
     */
    @Override
    public MemberVo modifyPayPassword(MemberBo memberBo) {
        Member member = this.getMemberById(memberBo.getId());
        if (member.getPayPassword() != null) {
            if (!member.getPayPassword().equalsIgnoreCase(memberBo.getPayPassword())) {
                throw new BusinessException("密码错误，请重新输入");
            }
            if (member.getPayPassword().equalsIgnoreCase(memberBo.getNewPayPassword())) {
                throw new BusinessException("新密码不能和旧密码一样");
            }
        }

        member.setPayPassword(memberBo.getNewPayPassword());
        return memberConvert.toVo(member);
    }

    @Override
    public MemberVo updataVipNo(int memberId) {
        Member member = this.getMemberById(memberId);
        member.setVip(MemberEnum.VIP_NO.getCode());
        return memberConvert.toVo(member);
    }

    @Override
    public MemberVo updataVipYes(int memberId) {
        Member member = this.getMemberById(memberId);
        member.setVip(MemberEnum.VIP_YES.getCode());
        return memberConvert.toVo(member);
    }

    @Override
    public MemberListVo updateCommunity(int communityId, int memberId) {
        Community community = communityService.getCommunityById(communityId);
        Member member = memberDao.getOne(memberId);
        member.setCommunity(community);
        member.setAddress(community.getAddress());
        return memberConvert.toListVo(member);
    }

    /**
     * 修改手机号
     *
     * @param memberBo
     * @return
     */
    @Override
    public MemberVo updataPhone(MemberBo memberBo) {
        Member member = memberDao.getOne(memberBo.getId());
        member.setPhone(memberBo.getPhone());
        return memberConvert.toVo(member);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getMemberNum() {
        return memberDao.countByDeleted(Deleted.DEL_FALSE);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate) {
        return memberDao.findDailyAddNumByDate(Deleted.DEL_FALSE, startDate, endDate);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Member checkMemberByWeChat(String unionId) {
        if (StringUtils.isAnyBlank(unionId)) {
            return null;
        }
        return memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
    }

    /**
     * 公众号授权登录
     */
    @Override
    public Member checkMemberByWeChatForSp(String unionId, String openId, String avater, String nickname) {
        if (StringUtils.isAnyBlank(unionId)) {
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
        if (dbMember != null) {
            if (StringUtils.isNotBlank(avater) && StringUtils.isBlank(dbMember.getAvater())) {
                dbMember.setAvater(avater);
            }
            if (StringUtils.isNotBlank(nickname) && StringUtils.isBlank(dbMember.getNickname())) {
                dbMember.setNickname(nickname);
            }
            if (StringUtils.isNotBlank(openId) && StringUtils.isBlank(dbMember.getSpOpenId())) {
                dbMember.setSpOpenId(openId);
            }
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "公众号");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    /**
     * 小程序授权登录
     */
    @Override
    public Object checkMemberByWeChatForMp(JSONObject userInfo, Integer parentId) {
        if (userInfo == null || StringUtils.isAnyBlank(userInfo.getString("unionId"), userInfo.getString("openId"))) {
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndDeleted(userInfo.getString("unionId"), Deleted.DEL_FALSE);
        if (dbMember != null) {
            if (StringUtils.isNotBlank(userInfo.getString("avatarUrl")) && StringUtils.isBlank(dbMember.getAvater())) {
                dbMember.setAvater(userInfo.getString("avatarUrl"));
            }
            if (StringUtils.isNotBlank(userInfo.getString("nickName")) && StringUtils.isBlank(dbMember.getNickname())) {
                dbMember.setNickname(EmojiParser.removeAllEmojis(userInfo.getString("nickName")));
            }
            if (StringUtils.isNotBlank(userInfo.getString("openId")) && StringUtils.isBlank(dbMember.getMpOpenId())) {
                dbMember.setMpOpenId(userInfo.getString("openId"));
            }
        } else {
            // 如果没有注册 先自动保存用户数据
            Member parent = this.getMemberById(parentId);
            Member member = new Member();
            member.setPassword("123456");
            member.setMpOpenId(userInfo.getString("openId"));
            member.setUnionId(userInfo.getString("unionId"));
            if (parent != null) {
                member.setParent(parent);
                member.setCommunity(parent.getCommunity());
            }
            member.setNickname(EmojiParser.removeAllEmojis(Optional.ofNullable(userInfo.getString("nickname")).orElse("")));
            member.setAvater(userInfo.getString("avatarUrl"));
            member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
            member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
            // 保存会员
            dbMember = memberDao.save(member);
            // 保存会员账户
            Account account = new Account();
            account.setMember(dbMember);
            account.setBalance(BigDecimal.ZERO);
            account.setConsumeAmount(BigDecimal.ZERO);
            account.setFreezeAmount(BigDecimal.ZERO);
            Account dbAccount = accountService.addAccount(account);
            // 修改会员账户
            member.setAccount(dbAccount);
            if (parent != null) {
                // 计算邀请人积分
                accountService.updateByTaskType(parent.getId(), BasicEnum.TASK_TYPE_INVITE);
                addInviteNumByMemberId(parent.getId());
            }
            // 发放注册奖励
            rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_REGISTER, member, null);
        }
        // 添加登录日志
        loginRecordService.addLoginRecordByLogin(dbMember, "小程序");
        dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        userInfo.put("memberId", dbMember.getId());
        userInfo.put("isLogin", Boolean.TRUE);
        userInfo.put("phone", dbMember.getPhone());
        return userInfo;
    }

    // /**
    // * 小程序授权登录
    // */
    // @Override
    // public Member checkMemberByWeChatForMp(String unionId, String openId, String
    // avater, String nickname) {
    // if (StringUtils.isAnyBlank(unionId)) {
    // return null;
    // }
    // Member dbMember = memberDao.findByUnionIdAndDeleted(unionId,
    // Deleted.DEL_FALSE);
    // if (dbMember != null) {
    // if (StringUtils.isNotBlank(avater) &&
    // StringUtils.isBlank(dbMember.getAvater())) {
    // dbMember.setAvater(avater);
    // }
    // if (StringUtils.isNotBlank(nickname) &&
    // StringUtils.isBlank(dbMember.getNickname())) {
    // dbMember.setNickname(nickname);
    // }
    // if (StringUtils.isNotBlank(openId) &&
    // StringUtils.isBlank(dbMember.getMpOpenId())) {
    // dbMember.setMpOpenId(openId);
    // }
    // } else {
    // // 如果没有注册 先自动保存用户数据
    //
    // }
    // return dbMember;
    // }

    /**
     * APP授权登录
     */
    @Override
    public Member checkMemberByWeChatForApp(String unionId, String openId, String avater, String nickname) {
        if (StringUtils.isAnyBlank(unionId)) {
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
        if (dbMember != null) {
            if (StringUtils.isNotBlank(avater) && StringUtils.isBlank(dbMember.getAvater())) {
                dbMember.setAvater(avater);
            }
            if (StringUtils.isNotBlank(nickname) && StringUtils.isBlank(dbMember.getNickname())) {
                dbMember.setNickname(nickname);
            }
            if (StringUtils.isNotBlank(openId) && StringUtils.isBlank(dbMember.getAppOpenId())) {
                dbMember.setAppOpenId(openId);
            }
            // APP登录自动升级vip
            dbMember.setMemberLevel(memberLevelService.getVipMemberLevel());
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "APP");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    /**
     * QQ 授权登录
     */
    @Override
    public Member checkMemberByQq(String unionId, String qqOpenId, String avater, String nickname) {
        if (StringUtils.isAnyBlank(unionId)) {
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndDeleted(unionId, Deleted.DEL_FALSE);
        if (dbMember != null) {
            if (StringUtils.isNotBlank(avater) && StringUtils.isBlank(dbMember.getAvater())) {
                dbMember.setAvater(avater);
            }
            if (StringUtils.isNotBlank(nickname) && StringUtils.isBlank(dbMember.getNickname())) {
                dbMember.setNickname(nickname);
            }
            if (StringUtils.isNotBlank(qqOpenId) && StringUtils.isBlank(dbMember.getQqOpenId())) {
                dbMember.setQqOpenId(qqOpenId);
            }
            // APP登录自动升级vip
            dbMember.setMemberLevel(memberLevelService.getVipMemberLevel());
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "APP");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    /**
     * 会员注册
     */
    @Override
    public MemberVo register(MemberBo memberBo) {
        return memberConvert.toVo(this.saveRegisterInfo(memberBo));
    }

    /***
     * 通过邀请过来的注册
     *
     * @param memberBo
     * @return
     */
    public MemberVo inviteRegister(MemberBo memberBo) {
        Member member = this.saveRegisterInfo(memberBo);

        return memberConvert.toVo(member);
    }

    private Member saveRegisterInfo(MemberBo memberBo) {
        int checkPhone = memberDao.countByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
        if (checkPhone > 0) {
            throw new BusinessException("该账户已被注册");
        }
        LOG.error("邀请人ID为：" + memberBo.getParentId());
        Member parent = this.getMemberById(memberBo.getParentId());
        Member member = new Member();
        member.setPhone(memberBo.getPhone());
        member.setUsername(memberBo.getPhone());
        member.setPassword(memberBo.getPassword());
        member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
        if (parent != null) {
            member.setParent(parent);
            member.setCommunity(parent.getCommunity());
        }
        if (StringUtils.isNotBlank(memberBo.getNickname())) {
            member.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
        } else {
            member.setNickname(memberBo.getPhone().substring(8) + "会员");
        }
        if (memberBo.isLoginByApp()) {
            member.setMemberLevel(memberLevelService.getVipMemberLevel());
        } else {
            member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
        }
        // 保存会员
        Member dbMember = memberDao.save(member);
        // 保存会员资金账户
        Account account = new Account();
        account.setMember(dbMember);
        account.setBalance(BigDecimal.ZERO);
        account.setConsumeAmount(BigDecimal.ZERO);
        account.setFreezeAmount(BigDecimal.ZERO);
        Account dbAccount = accountService.addAccount(account);
        // 修改 会员账户
        dbMember.setAccount(dbAccount);
        if (parent != null) {
            // 邀请送积分
            // accountService.updateByTaskType(parent.getId(), BasicEnum.TASK_TYPE_INVITE);
            addInviteNumByMemberId(parent.getId());
        }
        // 发放注册奖励
        rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_REGISTER, dbMember, null);
        return dbMember;
    }

    /**
     * 微信授权登录注册
     */
    @Override
    public MemberVo registerByWeChat(MemberBo memberBo) {
        if (memberBo == null || StringUtils.isAnyBlank(memberBo.getUnionId())) {
            LOG.error("registerByWeChat,提交数据为空");
            throw new BusinessException("提交数据不能为空");
        }
        LOG.error("邀请人ID为：" + memberBo.getParentId());
        // open_id为空
        if (StringUtils.isAllBlank(memberBo.getSpOpenId(), memberBo.getMpOpenId(), memberBo.getAppOpenId())) {
            LOG.error("registerByWeChat,提交数据为空");
            throw new BusinessException("请选择微信授权");
        }
        // 微信公众号授权
        if (StringUtils.isNotBlank(memberBo.getSpOpenId())) {
            // 查看当前微信账户是否存在
            Member checkMemberOpenId = memberDao.findByUnionIdAndSpOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getSpOpenId(), Deleted.DEL_FALSE);
            if (checkMemberOpenId != null) {
                LOG.error("registerByWeChat，微信open_id={}已被注册", memberBo.getSpOpenId());
                throw new BusinessException("该微信已注册");
            }
            // 微信小程序授权
        } else if (StringUtils.isNotBlank(memberBo.getMpOpenId())) {
            // 查看当前微信账户是否存在
            Member checkMemberMpOpenId = memberDao.findByUnionIdAndMpOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getMpOpenId(), Deleted.DEL_FALSE);
            if (checkMemberMpOpenId != null) {
                LOG.error("registerByWeChat，微信open_id={}已被注册", memberBo.getSpOpenId());
                throw new BusinessException("该微信已注册");
            }
            // 微信APP授权
        } else if (StringUtils.isNotBlank(memberBo.getAppOpenId())) {
            // 查看当前微信账户是否存在
            Member checkMemberAppOpenId = memberDao.findByUnionIdAndAppOpenIdAndDeleted(memberBo.getUnionId(), memberBo.getAppOpenId(), Deleted.DEL_FALSE);
            if (checkMemberAppOpenId != null) {
                LOG.error("registerByWeChat，微信app_open_id={}已被注册", memberBo.getAppOpenId());
                throw new BusinessException("该微信已注册");
            }
        }
        // 查询当前数据 是否存在相同的手机号
        Member checkMember = memberDao.findByPhoneAndDeleted(memberBo.getPhone(), Deleted.DEL_FALSE);
        // 绑定微信到当前账号
        if (checkMember != null) {
            // 禁用账户返回异常
            if (MemberEnum.STATE_DISABLE.getCode().equals(checkMember.getState())) {
                LOG.error("registerByWeChat，账号{}已被禁用", memberBo.getPhone());
                throw new BusinessException("该账号已禁用，请联系客服处理");
            }
            if (StringUtils.isNotBlank(memberBo.getSpOpenId()) && StringUtils.isBlank(checkMember.getSpOpenId())) {
                checkMember.setSpOpenId(memberBo.getSpOpenId());
            }
            if (StringUtils.isNotBlank(memberBo.getMpOpenId()) && StringUtils.isBlank(checkMember.getMpOpenId())) {
                checkMember.setMpOpenId(memberBo.getMpOpenId());
            }
            if (StringUtils.isNotBlank(memberBo.getAppOpenId()) && StringUtils.isBlank(checkMember.getAppOpenId())) {
                checkMember.setAppOpenId(memberBo.getAppOpenId());
            }
            if (StringUtils.isNotBlank(memberBo.getAvater()) && StringUtils.isBlank(checkMember.getAvater())) {
                checkMember.setAvater(memberBo.getAvater());
            }
            if (StringUtils.isNotBlank(memberBo.getNickname()) && StringUtils.isBlank(checkMember.getNickname())) {
                checkMember.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
            }
            if (StringUtils.isNotBlank(memberBo.getUnionId()) && StringUtils.isBlank(checkMember.getUnionId())) {
                checkMember.setUnionId(memberBo.getUnionId());
            }
            if (memberBo.isLoginByApp()) {
                checkMember.setMemberLevel(memberLevelService.getVipMemberLevel());
            } else {
                checkMember.setMemberLevel(memberLevelService.getDefaultMemberLevel());
            }
        } else {
            // 查询父类
            Member parent = this.getMemberById(memberBo.getParentId());
            // 根据当前数据注册会员
            Member member = new Member();
            member.setPhone(memberBo.getPhone());
            member.setUsername(memberBo.getPhone());
            member.setPassword(memberBo.getPassword());
            member.setSpOpenId(memberBo.getSpOpenId());
            member.setMpOpenId(memberBo.getMpOpenId());
            member.setAppOpenId(memberBo.getAppOpenId());
            member.setUnionId(memberBo.getUnionId());
            member.setAvater(memberBo.getAvater());
            member.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
            if (parent != null) {
                member.setParent(parent);
                member.setCommunity(parent.getCommunity());
            }
            if (StringUtils.isNotBlank(memberBo.getNickname())) {
                member.setNickname(EmojiParser.removeAllEmojis(memberBo.getNickname()));
            } else {
                member.setNickname(memberBo.getPhone().substring(8) + "会员");
            }
            if (memberBo.isLoginByApp()) {
                member.setMemberLevel(memberLevelService.getVipMemberLevel());
            } else {
                member.setMemberLevel(memberLevelService.getDefaultMemberLevel());
            }
            // 保存会员
            checkMember = memberDao.save(member);
            // 保存会员账户
            Account account = new Account();
            account.setMember(checkMember);
            account.setBalance(BigDecimal.ZERO);
            account.setConsumeAmount(BigDecimal.ZERO);
            account.setFreezeAmount(BigDecimal.ZERO);
            Account dbAccount = accountService.addAccount(account);
            // 更新 会员账户
            checkMember.setAccount(dbAccount);
            if (parent != null) {
                // 计算邀请人积分
                //accountService.updateByTaskType(parent.getId(), BasicEnum.TASK_TYPE_INVITE);
                addInviteNumByMemberId(parent.getId());
            }
            // 发放注册奖励
            rewardService.grantRewardByRewardType(ActivityEnum.REWARD_TYPE_REGISTER, checkMember, null);
        }
        // 添加登录日志
        loginRecordService.addLoginRecordByLogin(checkMember, memberBo.getLoginSource());
        checkMember.setLoginNum(Optional.ofNullable(checkMember.getLoginNum()).orElse(0) + 1);
        return memberConvert.toVo(checkMember);
    }

    /**
     * QQ授权注册
     */
    @Override
    public MemberVo registerByQq(MemberBo memberBo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Member getMemberByWeChatForSp(String unionId, String openId) {
        if (StringUtils.isAnyBlank(unionId, openId)) {
            LOG.error("参数（unionId={}，openId={}）为空", unionId, openId);
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndSpOpenIdAndStateAndDeleted(unionId, openId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
        if (dbMember != null) {
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "公众号");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    @Override
    public Member getMemberByWeChatForMp(String unionId, String mpOpenId) {
        if (StringUtils.isAnyBlank(unionId, mpOpenId)) {
            LOG.error("参数（unionId={}，mpOpenId={}）为空", unionId, mpOpenId);
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndMpOpenIdAndStateAndDeleted(unionId, mpOpenId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
        if (dbMember != null) {
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "小程序");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    @Override
    public Member getMemberByWeChatForApp(String unionId, String appOpenId) {
        if (StringUtils.isAnyBlank(unionId, appOpenId)) {
            LOG.error("参数（unionId={}，appOpenId={}）为空", unionId, appOpenId);
            return null;
        }
        Member dbMember = memberDao.findByUnionIdAndAppOpenIdAndStateAndDeleted(unionId, appOpenId, MemberEnum.STATE_ENABLE.getCode(), Deleted.DEL_FALSE);
        if (dbMember != null) {
            dbMember.setMemberLevel(memberLevelService.getVipMemberLevel());
            // 添加登录日志
            loginRecordService.addLoginRecordByLogin(dbMember, "APP");
            dbMember.setLoginNum(dbMember.getLoginNum() + 1);
        }
        return dbMember;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int checkByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return 0;
        }
        return memberDao.countByPhoneAndDeleted(phone, Deleted.DEL_FALSE);
    }

    /**
     * TODO 待完善
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public MemberVo getMemberLevelInfo(Integer memberId) {
        if (memberId == null) {
            LOG.error("getMemberLevelInfo，请求参数（memberId）为空");
            throw new BusinessException("请求参数不能为空");
        }
        Member dbMember = memberDao.getOne(memberId);
        if (dbMember == null) {
            LOG.error("getMemberLevelInfo，根据memberId={}获取数据为空", memberId);
            throw new BusinessException("系统异常，请稍后重试");
        }
        // 查询 会员等级数据
        List<MemberLevelListVo> memberLevels = memberLevelService.queryAll();
        if (CollectionUtils.isNotEmpty(memberLevels)) {

        }

        return null;
    }

    /**
     * 公众号 绑定微信
     */
    @Override
    public MemberVo bindWeChatForSp(String memberId, String unionId, String openId) {
        if (StringUtils.isAnyBlank(memberId, unionId, openId)) {
            LOG.error("bindWeChat，绑定微信参数为空memberId={}，unionId={}，openId={}", memberId, unionId, openId);
            throw new BusinessException("参数不能为空");
        }
        Member dbMember = this.getMemberById(Integer.valueOf(memberId));
        if (dbMember == null) {
            LOG.error("bindWeChatForSp，查询数据为空，memberId={}", memberId);
            throw new BusinessException("该账号还未注册，请您注册");
        }
        if (StringUtils.isBlank(dbMember.getUnionId())) {
            dbMember.setUnionId(unionId);
        }
        dbMember.setSpOpenId(openId);
        return memberConvert.toVo(dbMember);
    }

    /**
     * 微信小程序 绑定手机号
     */
    @Override
    public MemberListVo bindPhoneForMp(Integer memberId, String phone) {
        if (memberId != null && memberId.intValue() > 0 && StringUtils.isAnyBlank(phone)) {
            LOG.error("参数为空memberId={}，phone={}", memberId, phone);
            throw new BusinessException("提交数据不能为空");
        }
        Member dbMember = this.getMemberById(memberId);
        if (dbMember == null) {
            LOG.error("查询数据为空，memberId={}", memberId);
            throw new BusinessException("该账号还未注册，请您注册");
        }
        // 绑定手机号
        dbMember.setUsername(phone);
        dbMember.setPhone(phone);
        return memberConvert.toListVo(dbMember);
    }

    /**
     * APP 绑定微信
     */
    @Override
    public MemberVo bindWeChatForApp(String memberId, String unionId, String appOpenId) {
        if (StringUtils.isAnyBlank(memberId, unionId, appOpenId)) {
            LOG.error("bindWeChat，绑定微信参数为空memberId={}，unionId={}，appOpenId={}", memberId, unionId, appOpenId);
            throw new BusinessException("参数不能为空");
        }
        Member dbMember = this.getMemberById(Integer.valueOf(memberId));
        if (dbMember == null) {
            LOG.error("bindWeChatForApp，查询数据为空，memberId={}", memberId);
            throw new BusinessException("该账号还未注册，请您注册");
        }
        if (StringUtils.isBlank(dbMember.getUnionId())) {
            dbMember.setUnionId(unionId);
        }
        dbMember.setAppOpenId(appOpenId);
        return memberConvert.toVo(dbMember);
    }

    /**
     * 计算会员佣金 并记录</br>
     * 1.当前会员上级不为空 计算一级佣金 </br>
     * 2.当前会员上级的上级不为空 计算二级佣金
     *
     * @param saleOrder 订单
     * @param member    下单会员
     */
    @Override
    public void calculateCommissionForDistribution(SaleOrder saleOrder, Member member) {
        if (saleOrder != null && member != null) {
            // 计算佣金金额=支付金额-运费
            // BigDecimal commissionAmount =
            // saleOrder.getPayAmount().subtract(saleOrder.getFreight());
            BigDecimal commissionAmount = this.calculateEffectiveCommissionAmount(saleOrder);
            // 有效金额大于0 计算佣金
            if (commissionAmount.compareTo(BigDecimal.ZERO) > 0) {
                // TODO 屏蔽等级佣金
                // if (member.getParent() != null) {
                // Member parent = member.getParent();
                // // 非送礼订单 计算佣金
                // if (!OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
                // // 查询一级佣金比例
                // BigDecimal firstCommissionRate =
                // distributionLevelService.getFirstLevelCommissionRate();
                // BigDecimal firstCommission =
                // commissionAmount.multiply(firstCommissionRate).divide(BigDecimal.valueOf(100));
                // // 修改账户一级佣金并记录
                // accountService.updateMemberCommission(saleOrder, parent.getId(),
                // firstCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
                // // 保存佣金到会员佣金表
                // MemberCommission memberCommission1 = new MemberCommission();
                // memberCommission1.setMember(parent);
                // memberCommission1.setSaleOrder(saleOrder);
                // memberCommission1.setSubordinate(member);
                // memberCommission1.setCommissionType(MemberEnum.COMMISSION_TYPE_FIRST.getCode());
                // memberCommission1.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
                // memberCommission1.setCommissionAmount(firstCommission);
                // memberCommission1.setRemark("一级佣金");
                // memberCommissionService.addMemberCommission(memberCommission1);
                // // 计算二级佣金
                // if (parent.getParent() != null) {
                // // 查询二级佣金比例
                // BigDecimal secondCommissionRate =
                // distributionLevelService.getSecondLevelCommissionRate();
                // BigDecimal secondCommission =
                // commissionAmount.multiply(secondCommissionRate).divide(BigDecimal.valueOf(100));
                // // 修改账户二级佣金并记录
                // accountService.updateMemberCommission(saleOrder, parent.getParent().getId(),
                // secondCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
                // // 保存佣金到会员佣金表
                // MemberCommission memberCommission2 = new MemberCommission();
                // memberCommission2.setMember(parent.getParent());
                // memberCommission2.setSaleOrder(saleOrder);
                // memberCommission2.setSubordinate(member);
                // memberCommission2.setCommissionGrade(MemberEnum.COMMISSION_TYPE_SECOND.getCode());
                // memberCommission2.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
                // memberCommission2.setCommissionAmount(secondCommission);
                // memberCommission2.setRemark("二级佣金");
                // memberCommissionService.addMemberCommission(memberCommission2);
                // }
                // }
                // }
                // 会员小区存在且小区管理员存在 且非小区管理员下单
                if (member.getCommunity() != null && member.getCommunity().getMember() != null && member.getId() != member.getCommunity().getMember().getId()) {
                    // 小区佣金比例
                    BigDecimal communityCommissionRate = Optional.ofNullable(member.getCommunity().getCommissionRate()).orElse(BigDecimal.ZERO);
                    BigDecimal communityCommission = commissionAmount.multiply(communityCommissionRate).divide(BigDecimal.valueOf(100));
                    // 修改账户佣金并记录
                    accountService.updateMemberCommission(saleOrder, member.getCommunity().getMember().getId(), communityCommission, MemberEnum.TRADE_TYPE_COMMUNITY_COMMISSION,
                            member);
                    // 保存佣金到会员佣金表
                    MemberCommission memberCommission = new MemberCommission();
                    memberCommission.setMember(member.getCommunity().getMember());
                    memberCommission.setCommunity(member.getCommunity());
                    memberCommission.setSaleOrder(saleOrder);
                    memberCommission.setSubordinate(member);
                    memberCommission.setCommissionType(MemberEnum.COMMISSION_TYPE_COMMUNITY.getCode());
                    memberCommission.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
                    memberCommission.setCommissionAmount(communityCommission);
                    memberCommission.setRemark("小区管理员佣金");
                    memberCommissionService.addMemberCommission(memberCommission);
                }
            }
        }
    }

    // /**
    // * 计算会员佣金 并记录</br>
    // * 1.当前会员上级不为空 计算一级佣金 </br>
    // * 2.当前会员上级的上级不为空 计算二级佣金
    // *
    // * @param saleOrder
    // * @param member
    // */
    // @Override
    // public void calculateCommissionForCommodity(SaleOrder saleOrder, Member
    // member) {
    // if (saleOrder != null &&
    // CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems()) && member != null)
    // {
    // // 每个订单项 应该减去的优惠金额=优惠券金额-
    // BigDecimal couponAmount =
    // saleOrder.getCouponAmount().add(saleOrder.getVoucherAmount()).divide(BigDecimal.valueOf(saleOrder.getSaleOrderItems().size()));
    //
    // // 计算佣金
    // BigDecimal commission = saleOrder.getSaleOrderItems().stream().map(e ->
    // e.getSubtotal().multiply(e.getCommodity().getCommissionRate().divide(BigDecimal.valueOf(100))))
    // .reduce(BigDecimal.ZERO, BigDecimal::add);
    //
    // BigDecimal commissionAmount =
    // this.calculateEffectiveCommissionAmount(saleOrder);
    // // 有效金额大于0 计算佣金
    // if (commissionAmount.compareTo(BigDecimal.ZERO) > 0) {
    // if (member.getParent() != null) {
    // Member parent = member.getParent();
    // // 非送礼订单 计算佣金
    // if (!OrderEnum.ORDER_TYPE_GIFT.getCode().equals(saleOrder.getOrderType())) {
    // // 查询一级佣金比例
    // BigDecimal firstCommissionRate =
    // distributionLevelService.getFirstLevelCommissionRate();
    // BigDecimal firstCommission =
    // commissionAmount.multiply(firstCommissionRate).divide(BigDecimal.valueOf(100));
    // // 修改账户一级佣金并记录
    // accountService.updateMemberCommission(saleOrder, parent.getId(),
    // firstCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
    // // 保存佣金到会员佣金表
    // MemberCommission memberCommission1 = new MemberCommission();
    // memberCommission1.setMember(parent);
    // memberCommission1.setSaleOrder(saleOrder);
    // memberCommission1.setSubordinate(member);
    // memberCommission1.setCommissionGrade(MemberEnum.COMMISSION_TYPE_FIRST.getCode());
    // memberCommission1.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
    // memberCommission1.setCommissionAmount(firstCommission);
    // memberCommission1.setRemark("一级佣金");
    // memberCommissionService.addMemberCommission(memberCommission1);
    // // 计算二级佣金
    // if (parent.getParent() != null) {
    // // 查询二级佣金比例
    // BigDecimal secondCommissionRate =
    // distributionLevelService.getSecondLevelCommissionRate();
    // BigDecimal secondCommission =
    // commissionAmount.multiply(secondCommissionRate).divide(BigDecimal.valueOf(100));
    // // 修改账户二级佣金并记录
    // accountService.updateMemberCommission(saleOrder, parent.getParent().getId(),
    // secondCommission, MemberEnum.TRADE_TYPE_COMMISSION, member);
    // // 保存佣金到会员佣金表
    // MemberCommission memberCommission2 = new MemberCommission();
    // memberCommission2.setMember(parent.getParent());
    // memberCommission2.setSaleOrder(saleOrder);
    // memberCommission2.setSubordinate(member);
    // memberCommission2.setCommissionGrade(MemberEnum.COMMISSION_TYPE_SECOND.getCode());
    // memberCommission2.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
    // memberCommission2.setCommissionAmount(secondCommission);
    // memberCommission2.setRemark("二级佣金");
    // memberCommissionService.addMemberCommission(memberCommission2);
    // }
    // }
    // }
    // // 会员小区存在且小区管理员存在
    // if (member.getCommunity() != null && member.getCommunity().getMember() !=
    // null) {
    // // 小区佣金比例
    // BigDecimal communityCommissionRate =
    // Optional.ofNullable(member.getCommunity().getCommissionRate()).orElse(BigDecimal.ZERO);
    // BigDecimal communityCommission =
    // commissionAmount.multiply(communityCommissionRate).divide(BigDecimal.valueOf(100));
    // // 修改账户佣金并记录
    // accountService.updateMemberCommission(saleOrder,
    // member.getCommunity().getMember().getId(), communityCommission,
    // MemberEnum.TRADE_TYPE_COMMUNITY_COMMISSION,
    // member);
    // // 保存佣金到会员佣金表
    // MemberCommission memberCommission = new MemberCommission();
    // memberCommission.setMember(member.getCommunity().getMember());
    // memberCommission.setSaleOrder(saleOrder);
    // memberCommission.setSubordinate(member);
    // memberCommission.setCommissionGrade(MemberEnum.COMMISSION_TYPE_FIRST.getCode());
    // memberCommission.setSettlementState(MemberEnum.SETTLEMENT_STATE_UNSETTLED.getCode());
    // memberCommission.setCommissionAmount(communityCommission);
    // memberCommission.setRemark("小区管理员佣金");
    // memberCommissionService.addMemberCommission(memberCommission);
    // }
    // }
    // }
    // }

    /**
     * 计算有效的计算佣金的订单金额
     *
     * @param saleOrder
     */
    public BigDecimal calculateEffectiveCommissionAmount(SaleOrder saleOrder) {
        if (saleOrder == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal effectiveAmount = BigDecimal.ZERO;
        // 非分销商品不计算佣金
        if (CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems())) {
            for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
                if (tmpItem != null && tmpItem.getCommodity() != null && CommodityEnum.DISTRIBUTION_YES.getCode().equals(tmpItem.getCommodity().getDistribution())) {
                    effectiveAmount = effectiveAmount.add(tmpItem.getSubtotal());
                }
            }
        }
        // 有效金额-减去优惠金额-代金券金额-使用余额
        effectiveAmount = effectiveAmount.subtract(saleOrder.getCouponAmount()).subtract(saleOrder.getVoucherAmount()).subtract(saleOrder.getBalance());
        return effectiveAmount;
    }

    /**
     * 计算会员积分 并记录
     */
    @Override
    public void calculateOrderIntegral(SaleOrder saleOrder, Member member) {
        if (saleOrder != null && member != null) {
            BigDecimal integral = saleOrder.getSaleOrderItems().stream().map(e -> e.getSubtotal().multiply(e.getCommodity().getIntegralRate().divide(BigDecimal.valueOf(100))))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Account dbAccount = accountService.updateByOrder(member.getId(), integral.intValue());
            if (dbAccount != null) {
                // 计算会员等级
                MemberLevel dbMemberLevel = memberLevelService.calculateLevelByIntegral(dbAccount.getTotalIntegral());
                // 等级不为空且不是会员当前拥有的等级 升级会员
                if (dbMemberLevel != null && dbMemberLevel.getId() != member.getMemberLevel().getId()) {
                    member.setMemberLevel(dbMemberLevel);
                }
            }
        }
    }

    /**
     * 计算奖励积分
     */
    @Override
    public void calculateRewardIntegral(Integer integral, Member member) {
        if (integral != null && member != null) {
            Member dbMember = this.getMemberById(member.getId());
            if (dbMember != null) {
                Account dbAccount = accountService.updateByRewrd(dbMember.getId(), integral);
                if (dbAccount != null) {
                    // TODO 暂时屏蔽自动升级计算会员等级
                    // MemberLevel dbMemberLevel =
                    // memberLevelService.calculateLevelByIntegral(dbAccount.getIntegral());
                    // // 等级不为空且不是会员当前拥有的等级 升级会员
                    // if (dbMemberLevel != null && dbMemberLevel.getId() !=
                    // member.getMemberLevel().getId()) {
                    // member.setMemberLevel(dbMemberLevel);
                    // }
                }
            }
        }
    }

    /**
     * 根据时间 统计今日新增会员数
     *
     * @param date
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getMemberNumByDate(Date date) {
        if (date == null) {
            return 0;
        }
        return memberDao.countByDeletedAndCreateTime(Deleted.DEL_FALSE, date);
    }

    @Override
    public void updateConsumeNum(Integer memberId, Integer num) {
        if (memberId != null && num != null) {
            Member dbMember = this.getMemberById(memberId);
            if (dbMember != null) {
                dbMember.setConsumeNum(dbMember.getConsumeNum() + num);
            }
        }
    }

    @Override
    public void updateMemberTypeByUpdateCommunity(Integer memberId, Community community) {
        if (memberId != null) {
            Member dbMember = this.getMemberById(memberId);
            if (dbMember != null) {
                // 查询该小区的管理员 改为普通会员
                List<Member> members = memberDao.findByCommunity_idAndMemberTypeAndDeleted(community.getId(), MemberEnum.MEMBER_TYPE_ADMIN.getCode(), Deleted.DEL_FALSE);
                members.forEach(tmp -> {
                    tmp.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
                });
                dbMember.setCommunity(community);
                dbMember.setMemberType(MemberEnum.MEMBER_TYPE_ADMIN.getCode());
                dbMember.setAddress(community.getAddress());
            }
        }
    }

    @Override
    public void updateMemberTypeByDeleteCommunity(Integer memberId) {
        if (memberId != null) {
            Member dbMember = this.getMemberById(memberId);
            if (dbMember != null) {
                dbMember.setCommunity(null);
                dbMember.setMemberType(MemberEnum.MEMBER_TYPE_ORDINARY.getCode());
            }
        }
    }

    @Deprecated
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Integer> getInviteNum(Integer memberId) {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("inviteNum", 0);
        resultMap.put("totalNum", 0);
        resultMap.put("successNum", 0);
        resultMap.put("failureNum", 0);
        if (memberId != null) {
            List<Member> inviteMembers = memberDao.findByParent_idAndDeleted(memberId, Deleted.DEL_FALSE);
            if (CollectionUtils.isNotEmpty(inviteMembers)) {
                int inviteSuccessNum = saleOrderService.getInviteSuccessNum(inviteMembers.stream().map(Member::getId).collect(Collectors.toList()));
                resultMap.put("totalNum", inviteMembers.size());
                resultMap.put("successNum", inviteSuccessNum);
                resultMap.put("failureNum", inviteMembers.size() - inviteSuccessNum);
                Optional<Member> dbMember= memberDao.findById(memberId);
                if(dbMember.isPresent()){
                    resultMap.put("inviteNum", dbMember.get().getInviteNum());
                }
            }
        }
        // 查询奖励需要邀请的人数
        // List<Reward> dbRewards =
        // rewardService.getRewardByInviteNum(resultMap.get("successNum"));
        // if (CollectionUtils.isNotEmpty(dbRewards)) {
        // resultMap.put("inviteNum", dbRewards.get(0).getInviteNum());
        // }
        return resultMap;
    }

    /**
     * 查询邀请成功的订单和失败的会员
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, List<Object>> getInviteOrderAndMember(Integer memberId) {
        Map<String, List<Object>> resultMap = new HashMap<>();
        resultMap.put("successOrders", new ArrayList<Object>(0));
        resultMap.put("failureMembers", new ArrayList<Object>(0));
        if (memberId != null) {
            List<Member> inviteMembers = memberDao.findByParent_idAndDeleted(memberId, Deleted.DEL_FALSE);
            if (CollectionUtils.isNotEmpty(inviteMembers)) {
                // 邀请成功的查询订单
                List<SaleOrder> dbSaleOrders = saleOrderService.getInviteMemberOrders(inviteMembers.stream().map(Member::getId).collect(Collectors.toList()));
                Set<Integer> duplicateMembers = new HashSet<>();
                for (SaleOrder tmpOrder : dbSaleOrders) {
                    if (tmpOrder != null && tmpOrder.getMember() != null && !duplicateMembers.contains(tmpOrder.getMember().getId())) {
                        JSONObject tmpJson = new JSONObject();
                        tmpJson.put("nickname", tmpOrder.getMember().getNickname());
                        tmpJson.put("orderTime", DateUtils.getFormatDate(tmpOrder.getOrderTime()));
                        tmpJson.put("commodityName", "");
                        for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
                            if (tmpItem != null && tmpItem.getCommodity() != null && StringUtils.isBlank(tmpJson.getString("commodityName"))) {
                                tmpJson.put("commodityName", tmpItem.getCommodity().getCommodityName());
                            }
                        }
                        // 封装会员ID
                        duplicateMembers.add(tmpOrder.getMember().getId());
                        // 封装返回数据
                        resultMap.get("successOrders").add(tmpJson);
                    }
                }
                // 邀请失败的封装会员
                for (Member tmpMember : inviteMembers) {
                    if (tmpMember != null && !duplicateMembers.contains(tmpMember.getId())) {
                        JSONObject tmpJson = new JSONObject();
                        tmpJson.put("id", tmpMember.getId());
                        tmpJson.put("nickname", tmpMember.getNickname());
                        tmpJson.put("phone", tmpMember.getPhone());
                        // 封装会员ID
                        duplicateMembers.add(tmpMember.getId());
                        // 封装返回数据
                        resultMap.get("failureMembers").add(tmpJson);
                    }
                }
            }
        }
        return resultMap;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Member> getMembersForSendAll() {
        return memberDao.findByDeleted(Deleted.DEL_FALSE);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Member> getMembersForSendPart(List<MemberBo> members) {
        if (CollectionUtils.isNotEmpty(members)) {
            List<Integer> memberIds = members.stream().map(MemberBo::getId).collect(Collectors.toList());
            return memberDao.findAllById(memberIds);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long countInviteNumByMemberAndInviteTime(Integer memberId, Date startTime, Date endTime) {
        if (memberId != null && startTime != null && endTime != null) {
            return memberDao.count((root, query1, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get(Member_.deleted), Deleted.DEL_FALSE));
                predicates.add(criteriaBuilder.equal(root.get(Member_.state), MemberEnum.STATE_ENABLE.getCode()));
                predicates.add(criteriaBuilder.equal(root.get(Member_.parent).get(Member_.id), memberId));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Member_.createTime), startTime));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Member_.createTime), endTime));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            });
        }
        return 0L;
    }

    @Override
    public void addInviteNumByMemberId(int memberId) {
        if (memberId == 0) {
            throw new BusinessException("会员ID不可为空");
        }
        Member dbMember = memberDao.getOne(memberId);
        dbMember.setInviteNum(dbMember.getInviteNum() + 1);
        LOG.info("会员{} 邀请人数加1 当前人数{}", dbMember.getUsername(), dbMember.getInviteNum());
    }

    @Override
    public void subtractInviteNumByMemberId(int memberId, int num) {
        if (memberId == 0) {
            throw new BusinessException("会员ID不可为空");
        }
        if (num == 0) {
            throw new BusinessException("要扣减的人数必须大于1");
        }
        Member dbMember = memberDao.getOne(memberId);
        if(dbMember.getInviteNum() <= 0){
            throw new BusinessException("无可扣减的人数");
        }
        dbMember.setInviteNum(dbMember.getInviteNum() - num);
        LOG.info("会员{} 邀请人数扣减{} 当前人数{}", dbMember.getUsername(), num, dbMember.getInviteNum());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getInviteNumByMemberId(int memberId){
        Member dbMember = memberDao.getOne(memberId);
        return dbMember.getInviteNum();
    }

}
