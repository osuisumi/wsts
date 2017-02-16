package com.haoyu.wsts.debate.dao;

import com.haoyu.aip.debate.entity.Debate;

public interface IDebateBizDao {
	
	Debate getDebateByDebateUserId(String id);

}
