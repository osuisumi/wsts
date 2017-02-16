package com.haoyu.wsts.shiro.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import com.haoyu.sip.auth.entity.AuthUser;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.shiro.dao.IShiroUserDao;
import com.haoyu.wsts.shiro.entity.ShiroUser;

public class ShiroUserDao extends MybatisDao implements IShiroUserDao{

	@Override
	public ShiroUser selectByUserName(String username) {
		return this.selectOne("selectByUserName",username);
	}

	@Override
	public List<AuthUser> selectAuthUser(Map<String, Object> param) {
		return this.selectList("selectAuthUser", param);
	}

}
