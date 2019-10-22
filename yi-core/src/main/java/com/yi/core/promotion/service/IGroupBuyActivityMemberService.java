/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.yi.core.promotion.domain.bo.GroupBuyActivityMemberBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityMember;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityMemberListVo;
import com.yi.core.promotion.domain.vo.GroupBuyActivityMemberVo;
import com.yihz.common.persistence.Pagination;

/**
 * *
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 */
public interface IGroupBuyActivityMemberService {

    Page<GroupBuyActivityMember> query(Pagination<GroupBuyActivityMember> query);

    /**
     * 创建GroupBuyActivityMember
     **/
    GroupBuyActivityMemberListVo addGroupBuyActivityMember(GroupBuyActivityMemberBo groupBuyActivityMember);

    /**
     * 根据entity
     * 更新GroupBuyActivityMember
     **/
    GroupBuyActivityMemberListVo updateGroupBuyActivityMember(GroupBuyActivityMember groupBuyActivityMember);

    /**
     * 更新GroupBuyActivityMember
     **/
    GroupBuyActivityMemberListVo updateGroupBuyActivityMember(GroupBuyActivityMemberBo groupBuyActivityMember);

    /**
     * 删除GroupBuyActivityMember
     **/
    void removeGroupBuyActivityMemberById(int groupBuyActivityMemberId);

    /**
     * 根据ID得到GroupBuyActivityMemberBo
     **/
    GroupBuyActivityMemberBo getGroupBuyActivityMemberBoById(int groupBuyActivityMemberId);

    /**
     * 根据ID得到GroupBuyActivityMemberVo
     **/
    GroupBuyActivityMemberVo getGroupBuyActivityMemberVoById(int groupBuyActivityMemberId);

    /**
     * 根据ID得到GroupBuyActivityMemberListVo
     **/
    GroupBuyActivityMemberListVo getListVoById(int groupBuyActivityMemberId);

    /**
     * 分页查询: GroupBuyActivityMember
     **/
    Page<GroupBuyActivityMemberListVo> queryListVo(Pagination<GroupBuyActivityMember> query);

    /**
     * 批量新增团购会员
     *
     * @param groupBuyActivity
     * @param groupBuyActivityMembers
     */
    void batchAddGroupBuyActivityMember(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityMember>
            groupBuyActivityMembers);

    /**
     * 批量修改团购会员
     *
     * @param groupBuyActivity
     * @param groupBuyActivityMember
     */
    void batchUpdateGroupBuyActivityMember(GroupBuyActivity groupBuyActivity, GroupBuyActivityMember groupBuyActivityMember);


}
