package com.haoyu.wsts.debate.dao.impl.mybatis;

import org.springframework.stereotype.Component;

import com.haoyu.aip.debate.entity.Debate;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.debate.dao.IDebateBizDao;

@Component
public class DebateBizDao extends MybatisDao implements IDebateBizDao{

	@Override
	public Debate getDebateByDebateUserId(String id) {
		return super.selectOne("getDebateByDebateUserId", id);
	}

}
