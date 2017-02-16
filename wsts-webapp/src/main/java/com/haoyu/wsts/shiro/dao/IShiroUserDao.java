package com.haoyu.wsts.shiro.dao;

import java.util.List;
import java.util.Map;

import com.haoyu.sip.auth.entity.AuthUser;
import com.haoyu.wsts.shiro.entity.ShiroUser;

public interface IShiroUserDao {

	ShiroUser selectByUserName(String username);

	List<AuthUser> selectAuthUser(Map<String, Object> param);

}
