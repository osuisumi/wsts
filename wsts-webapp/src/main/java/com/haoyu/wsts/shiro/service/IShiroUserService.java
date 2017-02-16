package com.haoyu.wsts.shiro.service;

import java.util.List;

import com.haoyu.sip.auth.entity.AuthUser;
import com.haoyu.wsts.shiro.entity.ShiroUser;

public interface IShiroUserService {

	ShiroUser queryShiroUserByUserName(String userName);

	AuthUser findAuthUserByUsername(String username);

	AuthUser findAuthUserById(String id);

	List<AuthUser> findAuthUserByIds(List<String> ids);

}
