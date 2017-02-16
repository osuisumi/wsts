package com.haoyu.wsts.shiro.service.imple;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.haoyu.sip.auth.entity.AuthUser;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.shiro.dao.IShiroUserDao;
import com.haoyu.wsts.shiro.entity.ShiroUser;
import com.haoyu.wsts.shiro.service.IShiroUserService;

public class ShiroUserServiceImpl implements IShiroUserService{
	
	private IShiroUserDao shiroUserDao;

	public void setShiroUserDao(IShiroUserDao shiroUserDao) {
		this.shiroUserDao = shiroUserDao;
	}

	@Override
	public ShiroUser queryShiroUserByUserName(String username) {
		return shiroUserDao.selectByUserName(username);
	}

	@Override
	public AuthUser findAuthUserByUsername(String username) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("username", username);
		List<AuthUser> authUsers = shiroUserDao.selectAuthUser(param);
		if (Collections3.isNotEmpty(authUsers)) {
			return authUsers.get(0);
		}
		return null;
	}

	@Override
	public AuthUser findAuthUserById(String id) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("id", id);
		List<AuthUser> authUsers = shiroUserDao.selectAuthUser(param);
		if (Collections3.isNotEmpty(authUsers)) {
			return authUsers.get(0);
		}
		return null;
	}

	@Override
	public List<AuthUser> findAuthUserByIds(List<String> ids) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("ids", ids);
		return shiroUserDao.selectAuthUser(param);
	}

}
