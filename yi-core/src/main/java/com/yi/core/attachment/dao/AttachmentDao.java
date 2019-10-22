package com.yi.core.attachment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.attachment.domain.entity.Attachment;
import com.yi.core.common.ObjectType;

public interface AttachmentDao extends JpaRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {
	/**
	 * 删除指定目录的附件
	 *
	 * @param objectPath
	 */
	@Modifying
	@Query("delete from Attachment t where t.objectPath = ?1")
	void deleteByPath(String objectPath);

	/**
	 * 删除指定业务对象的附件
	 *
	 * @param objectPath
	 */
	@Modifying
	@Query("delete from Attachment t where t.objectId = ?1")
	void deleteByObjectId(Integer objectId);

	/**
	 * 删除指定目录以及子目录的附件
	 *
	 * @param objectPathLike
	 */
	@Modifying
	@Query("delete from Attachment t where t.objectPath like ?1")
	void deleteByPathLike(String objectPathLike);

	/**
	 * 删除指定业务对象的附件
	 *
	 * @param objectPath
	 */
	@Modifying
	@Query("delete from Attachment t where t.objectId = ?1 and t.objectType = ?2")
	void deleteByObjectIdAndObjectType(Integer objectId, ObjectType objectType);

	/**
	 * 根据业务ID 获取业务附件集合
	 * 
	 * @param objectId
	 * @return
	 */
	List<Attachment> findByObjectIdAndObjectType(Integer objectId, ObjectType objectType);

	// List<Attachment> findByBusinessScenarioLike(String businessScenario);

	// @Modifying
	// void deleteByBusinessScenarioIn(List<String> businessScenario);
	//
	// @Modifying
	// void deleteByBusinessScenarioLike(String businessScenario);
}
