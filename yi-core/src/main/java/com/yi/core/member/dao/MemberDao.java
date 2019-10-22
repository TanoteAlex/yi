/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.member.domain.entity.Member;

/**
 * 会员
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface MemberDao extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {

	/**
	 * 登录
	 * 
	 * @param password
	 * @param username
	 * @param deleted
	 * @return
	 */
	Member findByPasswordAndUsernameAndDeleted(String password, String username, Integer deleted);

	/**
	 * 查询会员
	 * 
	 * @param password
	 * @param phone
	 * @param deleted
	 * @return
	 */
	Member findByPasswordAndPhoneAndDeleted(String password, String phone, Integer deleted);

	/**
	 * 根据 unionId获取会员信息
	 *
	 * @param unionId
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndDeleted(String unionId, Integer deleted);

	/**
	 * 根据 unionId 和 spOpenId获取会员信息
	 *
	 * @param unionId
	 * @param spOpenId
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndSpOpenIdAndDeleted(String unionId, String spOpenId, Integer deleted);

	/**
	 * 根据 unionId 和 mpOpenId获取会员信息
	 *
	 * @param unionId
	 * @param mpOpenId
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndMpOpenIdAndDeleted(String unionId, String mpOpenId, Integer deleted);

	/**
	 * 根据 unionId和 appOpenId获取会员信息
	 *
	 * @param unionId
	 * @param appOpenId
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndAppOpenIdAndDeleted(String unionId, String appOpenId, Integer deleted);

	/**
	 * 根据微信unionId和spOpenId获取会员信息
	 *
	 * @param unionId
	 * @param spOpenId
	 * @param state
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndSpOpenIdAndStateAndDeleted(String unionId, String spOpenId, Integer state, Integer deleted);

	/**
	 * 根据微信unionId和mpOpenId获取会员信息
	 *
	 * @param unionId
	 * @param mpOpenId
	 * @param state
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndMpOpenIdAndStateAndDeleted(String unionId, String mpOpenId, Integer state, Integer deleted);

	/**
	 * 根据微信unionId和appOpenId获取会员信息
	 *
	 * @param unionId
	 * @param appOpenId
	 * @param state
	 * @param deleted
	 * @return
	 */
	Member findByUnionIdAndAppOpenIdAndStateAndDeleted(String unionId, String appOpenId, Integer state, Integer deleted);

	/**
	 * 统计我的团队人数
	 * 
	 * @param memberId
	 * @param deleted
	 * @return
	 */
	int countByParent_IdAndDeleted(Integer memberId, Integer deleted);

	/**
	 * 查询我的团队
	 * 
	 * @return
	 */
	List<Member> findByParent_idAndDeleted(Integer memberId, Integer deleted);

	Member findByPhoneAndDeleted(String phone, Integer deleted);

	List<Member> findByUsername(String username);

	/**
	 * 校验用户名是否注册
	 * 
	 * @param username
	 * @param deleted
	 * @return
	 */
	int countByUsernameAndDeleted(String username, Integer deleted);

	/**
	 * 校验 手机号是否注册
	 *
	 * @param phone
	 * @return
	 */
	int countByPhoneAndDeleted(String phone, Integer deleted);

	/**
	 * 统计会员数量
	 *
	 * @param deleted
	 * @return
	 */
	int countByDeleted(Integer deleted);

	/**
	 * 查询小区的管理员
	 * 
	 * @param communityId
	 * @param memberType
	 * @param deleted
	 * @return
	 */
	List<Member> findByCommunity_idAndMemberTypeAndDeleted(Integer communityId, Integer memberType, Integer deleted);

	/**
	 * 统计每天新增数量
	 *
	 * @param deleted
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Query(value = "select Date(createTime) as createTime, count(id) from Member" + " where deleted=?1 and Date(createTime)>=Date(?2) and Date(createTime)<=Date(?3)"
			+ " group by Date(createTime)")
	List<Object[]> findDailyAddNumByDate(Integer deleted, Date startDate, Date endDate);

	/**
	 * 根据时间 统计今日新增会员数
	 * 
	 * @param deleted
	 * @param date
	 * @return
	 */
	@Query(value = "select count(*) from Member where deleted=?1 and Date(createTime)=Date(?2)")
	int countByDeletedAndCreateTime(Integer deleted, Date date);

	/**
	 * 查询非删除会员
	 * 
	 * @param deleted
	 * @return
	 */
	List<Member> findByDeleted(Integer deleted);

	/**
	 * 统计邀请人数
	 * 
	 * @param parentId
	 * @param deleted
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int countByParent_idAndDeletedAndStateAndCreateTimeBetween(Integer parentId, Integer deleted, Integer state, Date startTime, Date endTime);

}