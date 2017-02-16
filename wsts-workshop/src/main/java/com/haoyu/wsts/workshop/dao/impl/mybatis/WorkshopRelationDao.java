package com.haoyu.wsts.workshop.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.workshop.dao.IWorkshopRelationDao;
import com.haoyu.wsts.workshop.entity.WorkshopRelation;
@Repository
public class WorkshopRelationDao extends MybatisDao implements IWorkshopRelationDao{

	@Override
	public WorkshopRelation selectWorkshopRelationById(String id) {
		return super.selectByPrimaryKey(id);
	}

	@Override
	public int insertWorkshopRelation(WorkshopRelation workshopRelation) {
		return super.insert(workshopRelation);
	}

	@Override
	public int updateWorkshopRelation(WorkshopRelation workshopRelation) {
		return super.update("update",workshopRelation);
	}

	@Override
	public int deleteWorkshopRelationByLogic(WorkshopRelation workshopRelation) {
		return super.deleteByLogic(workshopRelation);
	}

	@Override
	public int deleteWorkshopRelationByPhysics(String id) {
		return super.deleteByPhysics(id);
	}

	@Override
	public List<WorkshopRelation> findAll(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("select", parameter, pageBounds);
	}

	@Override
	public int updateWorkshopRelationByWorkshopId(WorkshopRelation workshopRelation) {
		return super.update("updateByWorkshopId", workshopRelation);
	}

	@Override
	public int batchUpdate(Map<String, Object> parameter) {
		return super.update("batchUpdate", parameter);
	}

}
