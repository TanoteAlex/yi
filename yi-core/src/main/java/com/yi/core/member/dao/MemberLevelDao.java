/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.member.domain.entity.MemberLevel;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface MemberLevelDao extends JpaRepository<MemberLevel, Integer>, JpaSpecificationExecutor<MemberLevel> {

	int countByNameAndDeleted(String name, int deleted);

	MemberLevel findByNameAndDeleted(String name, Integer deleted);

	List<MemberLevel> findByNameAndDeletedAndIdNot(String name, Integer deleted, Integer id);

	List<MemberLevel> findByRankAndDeletedAndIdNot(Integer rank, Integer deleted, Integer id);

	/**
	 * 查询VIP的等级
	 * 
	 * @param deleted
	 * @return
	 */
	List<MemberLevel> findByDeletedOrderByRankDesc(Integer deleted);

	/**
	 * 查询默认的等级
	 * 
	 * @param defauled
	 * @param deleted
	 * @return
	 */
	List<MemberLevel> findByDefaultedAndDeleted(Integer defauled, Integer deleted);

	/**
	 * 查询默认的等级
	 * 
	 * @param defauled
	 * @param deleted
	 * @param id
	 * @return
	 */
	List<MemberLevel> findByDefaultedAndDeletedAndIdNot(Integer defauled, Integer deleted, Integer id);

	/**
	 * 根据名称或级别 校验重复
	 * 
	 * @param name
	 * @param rank
	 * @param deleted
	 * @param id
	 * @return
	 */
	@Query("select count(*) from MemberLevel t1 where (t1.name=?1 or t1.rank=?2) and t1.deleted=?3 and t1.id<>?4")
	int countByNameOrRankAndDeletedAndIdNot(String name, Integer rank, Integer deleted, Integer id);

	/**
	 * 根据名称或级别 校验重复
	 * 
	 * @param name
	 * @param rank
	 * @param deleted
	 * @return
	 */
	@Query("select count(*) from MemberLevel t1 where (t1.name=?1 or t1.rank=?2) and t1.deleted=?3")
	int countByNameOrRankAndDeleted(String name, Integer rank, Integer deleted);

	/**
	 * 查询所有会员等级数据
	 * 
	 * @param deleted
	 * @return
	 */
	List<MemberLevel> findByDeleted(Integer deleted);

	/**
	 * 查询小于或等于当前积分的等级或默认等级 并按成长值倒序
	 * 
	 * @param integral
	 * @param deleted
	 * @return
	 */
	List<MemberLevel> findByGrowthValueLessThanEqualOrDefaultedAndDeletedOrderByGrowthValueDesc(Integer integral, Integer defaulted, Integer deleted);

}