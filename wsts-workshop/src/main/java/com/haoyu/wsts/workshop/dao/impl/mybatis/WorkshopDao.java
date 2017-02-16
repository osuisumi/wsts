package com.haoyu.wsts.workshop.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.workshop.dao.IWorkshopDao;
import com.haoyu.wsts.workshop.entity.Workshop;

@Repository
public class WorkshopDao extends MybatisDao implements IWorkshopDao{

	@Override
	public Workshop selectWorkshopById(String id) {
		return super.selectByPrimaryKey(id);
	}

	@Override
	public int insertWorkshop(Workshop workshop) {
		return super.insert(workshop);
	}

	@Override
	public int updateWorkshop(Map<String,Object> parameter) {
		return super.update("updateByPrimaryKey", parameter);
	}

	@Override
	public int deleteWorkshopByLogic(Map<String,Object> parameter) {
		return super.deleteByLogic(parameter);
	}

	@Override
	public int deleteWorkshopByPhysics(String id) {
		return super.deleteByPhysics(id);
	}

	@Override
	public List<Workshop> findAll(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("select", parameter, pageBounds);
	}

	@Override
	public List<Workshop> findAllWithStat(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("selectWithStat", parameter, pageBounds);
	}

	@Override
	public int count(Map<String, Object> parameter) {
		return super.selectOne("getCount", parameter);
	}

	@Override
	public List<Workshop> selectInterestedWorkshop(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("selectInterestedWorkshop", parameter, pageBounds);
	}

	@Override
	public Map<String, Workshop> workshopIdKeyMap(Map<String, Object> parameter) {
		return super.selectMap("selectWithStat", parameter, "id");
	}

}
