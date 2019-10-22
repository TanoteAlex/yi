package com.yi.adminserver.web.auth.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yi.adminserver.web.auth.jwt.AdminToken;
import com.yi.adminserver.web.auth.jwt.JwtAdminToken;
import com.yi.adminserver.web.auth.jwt.SupplierToken;
import com.yi.adminserver.web.auth.service.ILoginService;
import com.yi.core.auth.AuthEnum;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.simple.RescSimple;
import com.yi.core.auth.domain.vo.ChildrenVo;
import com.yi.core.auth.domain.vo.ChildrensVo;
import com.yi.core.auth.domain.vo.MenuVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.model.RestLoginResult;
import com.yi.core.auth.model.SessionData;
import com.yi.core.auth.service.IUserService;
import com.yi.core.supplier.SupplierEnum;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yi.core.supplier.domain.vo.SupplierVo;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.json.Result;

/**
 * 登录
 *
 * @author Administrator
 *
 */
@Component
public class LoginService implements ILoginService {

	private final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	/**
	 * 缓存登录供应商
	 */
	private ConcurrentHashMap<Integer, SupplierVo> CACHE_SUPPLIER_MAP = new ConcurrentHashMap<Integer, SupplierVo>(10);

	@Autowired
	private IUserService userService;

	@Autowired
	private ISupplierService supplierService;

	/**
	 * 用户解析token
	 */
	@Override
	public SessionData getSessionDataByToken(AdminToken token) {
		final User dbUser = userService.getUserById(token.getId());
		if (dbUser == null) {
			return null;
		}
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		return sessionData;
	}

	/**
	 * 供应商解析token
	 */
	@Override
	public SessionData getSessionDataByToken(SupplierToken token) {
		final Supplier dbSupplier = supplierService.getSupplierById(token.getId());
		if (dbSupplier == null) {
			return null;
		}
		SessionData sessionData = new SessionData();
		sessionData.setId(dbSupplier.getId());
		sessionData.setUserName(dbSupplier.getPhone());
		return sessionData;
	}

	/**
	 * 通过TOKEN登录
	 */
	@Override
	public RestLoginResult loginByToken(String token) {
		AdminToken tk = JwtAdminToken.getToken(token);
		if (tk == null) {
			return new RestLoginResult(Result.FAILURE, "无法识别的JWT令牌内容");
		}
		final User dbUser = userService.getUserById(tk.getId());
		if (dbUser == null) {
			return new RestLoginResult(Result.FAILURE, "员工资料已经不存在,无法登陆");
		}
		// 封装sessionData
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbUser);
		restLoginResult.setLoginUser(dbUser);
		restLoginResult.setSessionData(sessionData);
		return restLoginResult;
	}

	/**
	 * 登录
	 */
	@Override
	public RestLoginResult login(String username, String password) {
		UserVo dbUser = userService.login(username, password);
		if (dbUser == null) {
			LOG.error("用户名或密码错误，username={}", username);
			return new RestLoginResult(Result.FAILURE, "用户名或密码错误");
		}
		if (AuthEnum.STATE_DISABLE.getCode().equals(dbUser.getState())) {
			LOG.error("用户已被禁用，username=", username);
			return new RestLoginResult(Result.FAILURE, "您已被禁用，请联系管理员处理");
		}

		// 初始化返回到页面的权限List
		List<RescSimple> rescVos = new ArrayList<RescSimple>();
		List<ChildrensVo> childrensVos = new ArrayList<ChildrensVo>();

		// 遍历每一个角色，并获取每个角色拥有的一级菜单保存到权限列表中
		dbUser.getRoles().forEach(roleSimple -> {
			roleSimple.getRescs().forEach(rescSimple -> {
				if (rescSimple.getParent() == null) {
					if (!rescVos.contains(rescSimple)) {
						rescVos.add(rescSimple);
						rescSimple.getChildren().removeAll(rescSimple.getChildren());
					}
				}
			});
		});

		// 初始化二级菜单
		// 同样遍历角色列表，拿出所有角色的二级子权限放入列表
		dbUser.getRoles().forEach(roleSimple -> {
			List<RescSimple> rescSimples = new ArrayList<>(roleSimple.getRescs());
			rescSimples.forEach(rescSimple -> {
				if (rescSimple.getParent() != null) {
					rescVos.forEach(resc -> {
						if (rescSimple.getParent().getId() == resc.getId()) {
							resc.getChildren().add(rescSimple);
						}
					});
				}
			});
		});
		// 创建前台权限菜单
		MenuVo menuVo = new MenuVo();
		menuVo.setGroup(true);
		menuVo.setHideInBreadcrumb(false);
		menuVo.setText("主导航");
		rescVos.sort(Comparator.comparing(RescSimple::getId));
		rescVos.forEach(e -> {
			e.getChildren().sort(Comparator.comparing(RescSimple::getId));
		});
		for (int i = 0; i < rescVos.size(); i++) {
			ChildrensVo children = new ChildrensVo();
			List<ChildrenVo> childrenVos = new ArrayList<ChildrenVo>();
			children.setText(rescVos.get(i).getName());
			children.setIcon(rescVos.get(i).getIcon());
			childrensVos.add(children);
			// 二级菜单拼接
			for (int j = 0; j < rescVos.get(i).getChildren().size(); j++) {
				ChildrenVo childrenVo = new ChildrenVo();
				childrenVo.setLink(rescVos.get(i).getChildren().get(j).getUrl());
				childrenVo.setText(rescVos.get(i).getChildren().get(j).getName());
				childrenVos.add(childrenVo);
			}
			children.setChildren(childrenVos);
		}
		menuVo.setChildren(childrensVos);
		// 根据Right类的sort字段将一级菜单列表由小到大进行排序

		dbUser.setMenuVo(menuVo);

		// 封装sessionData数据
		SessionData sessionData = new SessionData();
		sessionData.setId(dbUser.getId());
		sessionData.setUserName(dbUser.getUsername());
		sessionData.setAvatar(dbUser.getAvatar());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbUser);
		restLoginResult.setLoginUser(dbUser);
		restLoginResult.setSessionData(sessionData);
		return restLoginResult;
	}

	/**
	 * 供应商登录
	 */
	@Override
	public RestLoginResult loginForSupplier(String username, String password) {
		SupplierVo dbSupplier = supplierService.login(username, password);
		if (dbSupplier == null) {
			LOG.error("用户名或密码错误，username={}", username);
			return new RestLoginResult(Result.FAILURE, "用户名或密码错误");
		}
		if (SupplierEnum.STATE_DISABLE.getCode().equals(dbSupplier.getState())) {
			LOG.error("用户已被禁用，username=", username);
			return new RestLoginResult(Result.FAILURE, "您已被禁用，请联系管理员处理");
		}
		// 封装sessionData数据
		SessionData sessionData = new SessionData();
		sessionData.setId(dbSupplier.getId());
		sessionData.setUserName(dbSupplier.getPhone());
		// 封装返回数据
		RestLoginResult restLoginResult = new RestLoginResult(Result.SUCCESS, "登录成功", dbSupplier);
		restLoginResult.setLoginUser(dbSupplier);
		restLoginResult.setSessionData(sessionData);
		//
		CACHE_SUPPLIER_MAP.put(dbSupplier.getId(), dbSupplier);
		return restLoginResult;
	}

	/**
	 * 获取当前登录的 供应商
	 */
	@Override
	public SupplierVo getLoginSupplier(SupplierToken token) {
		if (CACHE_SUPPLIER_MAP.containsKey(token.getId())) {
			return CACHE_SUPPLIER_MAP.get(token.getId());
		}
		return supplierService.getSupplierVoById(token.getId());
	}

}
