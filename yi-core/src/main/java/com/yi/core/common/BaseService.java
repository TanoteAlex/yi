package com.yi.core.common;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.UserBo;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.simple.UserSimple;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yihz.common.persistence.Pagination;

public abstract class BaseService implements IBaseService<User, UserSimple, UserVo, UserListVo, UserBo, Integer> {

	@Override
	public Page<User> query(Pagination<User> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserListVo> queryListVo(Pagination<User> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserListVo> queryListVoForSupplier(Pagination<User> query) {
		// TODO Auto-generated method stub
		return IBaseService.super.queryListVoForSupplier(query);
	}

	@Override
	public Page<UserListVo> queryListVoForApp(Pagination<User> query) {
		// TODO Auto-generated method stub
		return IBaseService.super.queryListVoForApp(query);
	}

	@Override
	public User add(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserListVo addByBo(UserBo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserListVo updateByBo(UserBo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(User entity) {
		// TODO Auto-generated method stub
		IBaseService.super.remove(entity);
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBo getBoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSimple getSimpleById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo getVoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserListVo getListVoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}