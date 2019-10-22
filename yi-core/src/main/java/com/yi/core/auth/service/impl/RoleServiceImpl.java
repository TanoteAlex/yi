/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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

import com.yi.core.auth.AuthEnum;
import com.yi.core.auth.dao.RoleDao;
import com.yi.core.auth.domain.bo.RoleBo;
import com.yi.core.auth.domain.entity.Role;
import com.yi.core.auth.domain.entity.Role_;
import com.yi.core.auth.domain.simple.RoleSimple;
import com.yi.core.auth.domain.vo.RoleListVo;
import com.yi.core.auth.domain.vo.RoleVo;
import com.yi.core.auth.service.IRoleService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 角色
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RoleServiceImpl implements IRoleService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private RoleDao roleDao;

	private EntityListVoBoSimpleConvert<Role, RoleBo, RoleVo, RoleSimple, RoleListVo> roleConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Role getRoleById(int roleId) {
		return roleDao.getOne(roleId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RoleVo getRoleVoById(int roleId) {
		return roleConvert.toVo(this.roleDao.getOne(roleId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RoleListVo getRoleListVoById(int roleId) {
		return roleConvert.toListVo(this.roleDao.getOne(roleId));
	}

	@Override
	public Role addRole(Role role) {
		if (role == null || StringUtils.isAnyBlank(role.getName())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (CollectionUtils.isEmpty(role.getRescs())) {
			throw new BusinessException("角色资源不能为空");
		}
		// 去掉空格
		role.setName(role.getName().trim());
		// 校验重复
		int checkName = roleDao.countByNameAndDeleted(role.getName(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("角色名称{}重复", role.getName());
			throw new BusinessException("角色名称不能重复");
		}
		return roleDao.save(role);
	}

	@Override
	public RoleVo addRole(RoleBo roleBo) {
		return roleConvert.toVo(this.addRole(roleConvert.toEntity(roleBo)));
	}

	@Override
	public Role updateRole(Role role) {
		if (role == null || role.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		if (CollectionUtils.isEmpty(role.getRescs())) {
			throw new BusinessException("角色资源不能为空");
		}
		// 去掉空格
		role.setName(role.getName().trim());
		int checkName = roleDao.countByNameAndIdNotAndDeleted(role.getName(), role.getId(), Deleted.DEL_FALSE);
		if (checkName > 0) {
			LOG.error("角色名称{}重复", role.getName());
			throw new BusinessException("角色名称不能重复");
		}
		Role dbRole = roleDao.getOne(role.getId());
		AttributeReplication.copying(role, dbRole,Role_.state,Role_.name, Role_.description);
		dbRole.setRescs(role.getRescs());
		return dbRole;
	}

	@Override
	public RoleListVo updateRole(RoleBo roleBo) {
		return roleConvert.toListVo(this.updateRole(roleConvert.toEntity(roleBo)));
	}

	@Override
	public void removeRoleById(int roleId) {
		Role dbRole = roleDao.getOne(roleId);
		if (dbRole != null) {
			if (CollectionUtils.isNotEmpty(dbRole.getUsers())) {
				LOG.error("角色{}删除失败", roleId);
				throw new BusinessException("该角色已有用户使用，请处理相关数据后删除");
			}
			dbRole.setRescs(null);
			dbRole.setDeleted(Deleted.DEL_TRUE);
			dbRole.setDelTime(new Date());
		}
	}

	@Override
	public Page<Role> query(Pagination<Role> query) {
		query.setEntityClazz(Role.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Role_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Role_.createTime)));
		}));
		Page<Role> page = roleDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RoleListVo> queryListVo(Pagination<Role> query) {
		Page<Role> pages = this.query(query);
		List<RoleListVo> vos = roleConvert.toListVos(pages.getContent());
		return new PageImpl<RoleListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.roleConvert = new EntityListVoBoSimpleConvert<Role, RoleBo, RoleVo, RoleSimple, RoleListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Role, RoleVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Role, RoleVo>(beanConvertManager) {
					@Override
					protected void postConvert(RoleVo RoleVo, Role Role) {

					}
				};
			}

			@Override
			protected BeanConvert<Role, RoleListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Role, RoleListVo>(beanConvertManager) {
					@Override
					protected void postConvert(RoleListVo RoleListVo, Role Role) {

					}
				};
			}

			@Override
			protected BeanConvert<Role, RoleBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Role, RoleBo>(beanConvertManager) {
					@Override
					protected void postConvert(RoleBo RoleBo, Role Role) {

					}
				};
			}

			@Override
			protected BeanConvert<RoleBo, Role> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RoleBo, Role>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Role, RoleSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Role, RoleSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<RoleSimple, Role> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<RoleSimple, Role>(beanConvertManager) {
					@Override
					public Role convert(RoleSimple RoleSimple) throws BeansException {
						return roleDao.getOne(RoleSimple.getId());
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
	public Role jurisdiction(Role role) {
		Role rolei = roleDao.getOne(role.getId());
		rolei.setRescs(role.getRescs());
		/* System.err.println(role); */
		return rolei;
	}

	/**
	 * 禁用
	 */
	@Override
	public RoleVo updateDisable(int roleId) {
		Role role = roleDao.getOne(roleId);
		role.setState(AuthEnum.STATE_DISABLE.getCode());
		return roleConvert.toVo(role);
	}

	/**
	 * 启用
	 */
	@Override
	public RoleVo updateEnable(int roleId) {
		Role role = roleDao.getOne(roleId);
		role.setState(AuthEnum.STATE_ENABLE.getCode());
		return roleConvert.toVo(role);
	}
}
