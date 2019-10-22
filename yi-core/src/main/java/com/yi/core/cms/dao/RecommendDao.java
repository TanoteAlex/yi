/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.cms.domain.entity.Recommend;

/**
 * 推荐位
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface RecommendDao extends JpaRepository<Recommend, Integer>, JpaSpecificationExecutor<Recommend> {

	/**
	 * 查询推荐数据
	 *
	 * @param positionId
	 * @param state
	 * @param deleted
	 * @param city
	 * @return
	 */
	@Query("select t1 from Recommend t1 left join t1.recommendCommodities t2 where t1.position.positionType=?1 and t1.state=?2 and t1.deleted=?3 "
			+ " and t2.commodity.deleted=?3 and t2.commodity.shelfState=?4 and t2.commodity.auditState=?5"
			+ " group by t1.id order by t1.sort asc")
	List<Recommend> findByPosition(Integer positionType, Integer state, Integer deleted, Integer shelfState, Integer auditState);

//	/**
//	 * 查询推荐数据
//	 *
//	 * @param positionId
//	 * @param state
//	 * @param deleted
//	 * @param city
//	 * @return
//	 */
//	@Query("select t1 from Recommend t1 left join t1.recommendCommodities t2 left join t2.commodity.regions t3"
//			+ " where t1.position.positionType=?1 and t1.state=?2 and t1.deleted=?3 and t2.commodity.deleted=?3 and t2.commodity.shelfState=?4 andt2.commodity.auditState=?5"
//			+ " and t3.area.name like%?6% group by t1.id order by t1.sort asc")
//	List<Recommend> findByPositionAndCity(Integer positionType, Integer state, Integer deleted, Integer shelfState, Integer auditState, String city);

	// @EntityGraph(attributePaths = { "recommendCommodities" })
	List<Recommend> findByPosition_IdAndStateAndDeleted(Integer positionId, Integer state, Integer deleted);

	// /**
	// * 查询推荐数据
	// *
	// * @param positionId
	// * @param state
	// * @param deleted
	// * @param city
	// * @return
	// */
	// @Query("select t1 from Recommend t1 left join t1.commodities t2 " + " where
	// t1.position.positionType=?1 and t1.state=?2 and t1.deleted=?3 "
	// + " and t2.deleted=?3 and t2.shelfState=?4 and t2.auditState=?5 group by
	// t1.id order by t1.sort asc")
	// List<Recommend> findByPosition(Integer positionType, Integer state, Integer
	// deleted, Integer shelfState, Integer auditState);
	//
	// /**
	// * 查询推荐数据
	// *
	// * @param positionId
	// * @param state
	// * @param deleted
	// * @param city
	// * @return
	// */
	// @Query("select t1 from Recommend t1 left join t1.commodities t2 left join
	// t2.regions t3 " + " where t1.position.positionType=?1 and t1.state=?2 and
	// t1.deleted=?3 "
	// + " and t2.deleted=?3 and t2.shelfState=?4 and t2.auditState=?5 and
	// t3.area.name like %?6% group by t1.id order by t1.sort asc")
	// List<Recommend> findByPositionAndCity(Integer positionType, Integer state,
	// Integer deleted, Integer shelfState, Integer auditState, String city);

	// @EntityGraph(attributePaths = { "commodities" })
	// List<Recommend> findByPosition_IdAndStateAndDeleted(Integer positionId,
	// Integer state, Integer deleted);

	/**
	 * 新增时校验位置是否重复
	 * 
	 * @param positionId
	 * @param deleted
	 * @return
	 */
	int countByPosition_idAndDeleted(Integer positionId, Integer deleted);

	/**
	 * 修改时校验位置是否重复
	 * 
	 * @param positionId
	 * @param deleted
	 * @param id
	 * @return
	 */
	int countByPosition_idAndDeletedAndIdNot(Integer positionId, Integer deleted, Integer id);

	/**
	 * 修改时校验位置是否重复
	 * 
	 * @param linkType
	 * @param id
	 * @return
	 */
	List<Recommend> findByLinkIdAndLinkType(Integer id, Integer linkType);
}