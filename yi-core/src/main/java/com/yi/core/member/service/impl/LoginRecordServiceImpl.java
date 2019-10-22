/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.member.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yi.core.member.dao.LoginRecordDao;
import com.yi.core.member.domain.bo.LoginRecordBo;
import com.yi.core.member.domain.entity.LoginRecord;
import com.yi.core.member.domain.entity.LoginRecord_;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.simple.LoginRecordSimple;
import com.yi.core.member.domain.vo.LoginRecordListVo;
import com.yi.core.member.domain.vo.LoginRecordVo;
import com.yi.core.member.service.ILoginRecordService;
import com.yi.core.utils.NetUtils;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 登录记录
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class LoginRecordServiceImpl implements ILoginRecordService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(LoginRecordServiceImpl.class);

	@Autowired
	private HttpServletRequest request;

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private LoginRecordDao loginRecordDao;

	private EntityListVoBoSimpleConvert<LoginRecord, LoginRecordBo, LoginRecordVo, LoginRecordSimple, LoginRecordListVo> loginRecordConvert;

	/**
	 * 分页查询LoginRecord
	 **/
	@Override
	public Page<LoginRecord> query(Pagination<LoginRecord> query) {
		query.setEntityClazz(LoginRecord.class);
		Page<LoginRecord> page = loginRecordDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: LoginRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LoginRecordListVo> queryListVo(Pagination<LoginRecord> query) {
		query.setEntityClazz(LoginRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(LoginRecord_.loginTime)));
		}));
		Page<LoginRecord> pages = loginRecordDao.findAll(query, query.getPageRequest());
		List<LoginRecordListVo> vos = loginRecordConvert.toListVos(pages.getContent());
		return new PageImpl<LoginRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 分页查询: LoginRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LoginRecordListVo> queryListVoForApp(Pagination<LoginRecord> query) {
		query.setEntityClazz(LoginRecord.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.desc(root.get(LoginRecord_.loginTime)));
		}));
		Page<LoginRecord> pages = loginRecordDao.findAll(query, query.getPageRequest());
		List<LoginRecordListVo> vos = loginRecordConvert.toListVos(pages.getContent());
		return new PageImpl<LoginRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建LoginRecord
	 **/
	@Override
	public LoginRecord addLoginRecord(LoginRecord loginRecord) {
		return loginRecordDao.save(loginRecord);
	}

	/**
	 * 创建LoginRecord
	 **/
	@Override
	public LoginRecordListVo addLoginRecord(LoginRecordBo loginRecordBo) {
		return loginRecordConvert.toListVo(loginRecordDao.save(loginRecordConvert.toEntity(loginRecordBo)));
	}

	/**
	 * 更新LoginRecord
	 **/
	@Override
	public LoginRecord updateLoginRecord(LoginRecord loginRecord) {
		LoginRecord dbLoginRecord = loginRecordDao.getOne(loginRecord.getId());
		AttributeReplication.copying(loginRecord, dbLoginRecord, LoginRecord_.guid, LoginRecord_.member, LoginRecord_.loginIp, LoginRecord_.loginSource, LoginRecord_.sessionId,
				LoginRecord_.loginTime, LoginRecord_.logoutTime);
		return dbLoginRecord;
	}

	/**
	 * 更新LoginRecord
	 **/
	@Override
	public LoginRecordListVo updateLoginRecord(LoginRecordBo loginRecordBo) {
		LoginRecord dbLoginRecord = loginRecordDao.getOne(loginRecordBo.getId());
		AttributeReplication.copying(loginRecordBo, dbLoginRecord, LoginRecord_.guid, LoginRecord_.member, LoginRecord_.loginIp, LoginRecord_.loginSource, LoginRecord_.sessionId,
				LoginRecord_.loginTime, LoginRecord_.logoutTime);
		return loginRecordConvert.toListVo(dbLoginRecord);
	}

	/**
	 * 删除LoginRecord
	 **/
	@Override
	public void removeLoginRecordById(int id) {
		loginRecordDao.deleteById(id);
	}

	/**
	 * 根据ID得到LoginRecord
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecord getLoginRecordById(int id) {
		return this.loginRecordDao.getOne(id);
	}

	/**
	 * 根据ID得到LoginRecordBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecordBo getLoginRecordBoById(int id) {
		return loginRecordConvert.toBo(this.loginRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到LoginRecordVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecordVo getLoginRecordVoById(int id) {
		return loginRecordConvert.toVo(this.loginRecordDao.getOne(id));
	}

	/**
	 * 根据ID得到LoginRecordListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LoginRecordListVo getListVoById(int id) {
		return loginRecordConvert.toListVo(this.loginRecordDao.getOne(id));
	}

	protected void initConvert() {
		this.loginRecordConvert = new EntityListVoBoSimpleConvert<LoginRecord, LoginRecordBo, LoginRecordVo, LoginRecordSimple, LoginRecordListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<LoginRecord, LoginRecordVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordVo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordVo loginRecordVo, LoginRecord loginRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordListVo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordListVo loginRecordListVo, LoginRecord loginRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordBo>(beanConvertManager) {
					@Override
					protected void postConvert(LoginRecordBo loginRecordBo, LoginRecord loginRecord) {
					}
				};
			}

			@Override
			protected BeanConvert<LoginRecordBo, LoginRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecordBo, LoginRecord>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<LoginRecord, LoginRecordSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecord, LoginRecordSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<LoginRecordSimple, LoginRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<LoginRecordSimple, LoginRecord>(beanConvertManager) {
					@Override
					public LoginRecord convert(LoginRecordSimple loginRecordSimple) throws BeansException {
						return loginRecordDao.getOne(loginRecordSimple.getId());
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
	 * 登录时 添加记录
	 */
	@Override
	public void addLoginRecordByLogin(Member member, String loginSource) {
		if (member != null && member.getId() > 0) {
			LoginRecord loginRecord = new LoginRecord();
			loginRecord.setMember(member);
			loginRecord.setLoginIp(NetUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
			loginRecord.setLoginSource(loginSource);
			loginRecord.setLoginTime(new Date());
			this.addLoginRecord(loginRecord);
		}
	}
}
