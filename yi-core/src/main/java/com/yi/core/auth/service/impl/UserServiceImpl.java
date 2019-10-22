/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.auth.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
import com.yi.core.auth.dao.UserDao;
import com.yi.core.auth.domain.bo.UserBo;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.entity.User_;
import com.yi.core.auth.domain.simple.UserSimple;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.service.IUserService;
import com.yi.core.common.Deleted;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 用户
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class UserServiceImpl implements IUserService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private UserDao userDao;

	private EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> userConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User getUserById(int userId) {
		return userDao.getOne(userId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserVo getUserVoById(int userId) {
		return userConvert.toVo(this.userDao.getOne(userId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserListVo getUserListVoById(int userId) {
		return userConvert.toListVo(this.userDao.getOne(userId));
	}

	@Override
	public User addUser(User user) {
		if (user == null || StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
			throw new BusinessException("提交数据不能为空");
		}
		user.setUsername(user.getUsername().trim());
		// 校验名称
		int checkName = userDao.countByUsernameAndDeletedAndIdNot(user.getUsername(), Deleted.DEL_FALSE, user.getId());
		if (checkName > 0) {
			LOG.error("用户名重复，username={}", user.getUsername());
			throw new BusinessException("用户名不能重复");
		}
		return userDao.save(user);
	}

	@Override
	public UserVo addUser(UserBo userBo) {
		return userConvert.toVo(this.addUser(userConvert.toEntity(userBo)));
	}

	@Override
	public User updateUser(User user) {
		if (user == null || user.getId() < 1 || StringUtils.isAnyBlank(user.getUsername(), user.getPassword())) {
			throw new BusinessException("提交数据不能为空");
		}
		user.setUsername(user.getUsername().trim());
		// 校验名称
		int checkName = userDao.countByUsernameAndDeletedAndIdNot(user.getUsername(), Deleted.DEL_FALSE, user.getId());
		if (checkName > 0) {
			LOG.error("用户名重复，username={}", user.getUsername());
			throw new BusinessException("用户名不能重复");
		}
		User dbUser = userDao.getOne(user.getId());
		AttributeReplication.copying(user, dbUser, User_.dept,User_.state, User_.username, User_.password, User_.fullName, User_.phone, User_.email, User_.jobNo, User_.avatar);
		dbUser.setRoles(user.getRoles());
		return dbUser;
	}

	@Override
	public UserVo updateUser(UserBo userBo) {
		return userConvert.toVo(this.updateUser(userConvert.toEntity(userBo)));
	}

	@Override
	public void removeUserById(int userId) {
		User dbUser = userDao.getOne(userId);
		if (dbUser != null) {
			dbUser.setDeleted(Deleted.DEL_TRUE);
			dbUser.setDelTime(new Date());
			// 清空用户权限
			dbUser.setRoles(null);
		}
	}

	@Override
	public Page<User> query(Pagination<User> query) {
		query.setEntityClazz(User.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(User_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(User_.createTime)));
		}));
		Page<User> page = userDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<UserListVo> queryListVo(Pagination<User> query) {

		Page<User> pages = this.query(query);

		List<UserListVo> vos = userConvert.toListVos(pages.getContent());
		return new PageImpl<UserListVo>(vos, query.getPageRequest(), pages.getTotalElements());

	}

	protected void initConvert() {
		this.userConvert = new EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<User, UserVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<User, UserVo>(beanConvertManager) {
					@Override
					protected void postConvert(UserVo UserVo, User User) {

					}
				};
			}

			@Override
			protected BeanConvert<User, UserListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<User, UserListVo>(beanConvertManager) {
					@Override
					protected void postConvert(UserListVo UserListVo, User User) {

					}
				};
			}

			@Override
			protected BeanConvert<User, UserBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<User, UserBo>(beanConvertManager) {
					@Override
					protected void postConvert(UserBo UserBo, User User) {

					}
				};
			}

			@Override
			protected BeanConvert<UserBo, User> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<UserBo, User>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<User, UserSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<User, UserSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<UserSimple, User> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<UserSimple, User>(beanConvertManager) {
					@Override
					public User convert(UserSimple UserSimple) throws BeansException {
						return userDao.getOne(UserSimple.getId());
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
	public EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> getUserConvert() {
		return userConvert;
	}

	@Override
	public UserVo login(String username, String password) {
		User byUsernameAndPasswordAndDeleted = userDao.findByUsernameAndPasswordAndDeleted(username, password, Deleted.DEL_FALSE);
		return userConvert.toVo(byUsernameAndPasswordAndDeleted);
	}

	/**
	 * 禁用
	 */
	@Override
	public UserVo updateDisable(int userId) {
		User dbUser = userDao.getOne(userId);
		if (dbUser != null) {
			dbUser.setState(AuthEnum.STATE_DISABLE.getCode());
			return userConvert.toVo(dbUser);
		}
		return null;
	}

	/**
	 * 启用
	 */
	@Override
	public UserVo updateEnable(int userId) {
		User dbUser = userDao.getOne(userId);
		if (dbUser != null) {
			dbUser.setState(AuthEnum.STATE_ENABLE.getCode());
			return userConvert.toVo(dbUser);
		}
		return null;
	}

}
