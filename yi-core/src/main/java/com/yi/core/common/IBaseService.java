package com.yi.core.common;

import org.springframework.data.domain.Page;

import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.Pagination;

/**
 * 基础方法接口
 * 
 * @author xuyh
 *
 * @param <ENTITY>
 * @param <SIMPLE>
 * @param <VO>
 * @param <LISTVO>
 * @param <BO>
 * @param <ID>
 */
public interface IBaseService<ENTITY, SIMPLE, VO, LISTVO, BO, ID> {

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	Page<ENTITY> query(Pagination<ENTITY> query);

	Page<LISTVO> queryListVo(Pagination<ENTITY> query);

	default Page<LISTVO> queryListVoForSupplier(Pagination<ENTITY> query) {
		return null;
	};

	default Page<LISTVO> queryListVoForApp(Pagination<ENTITY> query) {
		return null;
	};

	ENTITY add(ENTITY entity);

	LISTVO addByBo(BO b);

	ENTITY update(ENTITY entity);

	LISTVO updateByBo(BO b);

	void removeById(ID id);

	default void remove(ENTITY entity) {
	};

	ENTITY getById(ID id);

	BO getBoById(ID id);

	SIMPLE getSimpleById(ID id);

	VO getVoById(ID id);

	LISTVO getListVoById(ID id);

}
