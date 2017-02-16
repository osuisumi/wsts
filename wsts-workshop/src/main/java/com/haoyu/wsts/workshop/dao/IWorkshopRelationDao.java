package com.haoyu.wsts.workshop.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.workshop.entity.WorkshopRelation;

public interface IWorkshopRelationDao {
	
	WorkshopRelation selectWorkshopRelationById(String id);

	int insertWorkshopRelation(WorkshopRelation workshopRelation);

	int updateWorkshopRelation(WorkshopRelation workshopRelation);
	
	int batchUpdate(Map<String,Object> parameter);
	
	int updateWorkshopRelationByWorkshopId(WorkshopRelation workshopRelation);

	int deleteWorkshopRelationByLogic(WorkshopRelation workshopRelation);

	int deleteWorkshopRelationByPhysics(String id);

	List<WorkshopRelation> findAll(Map<String, Object> parameter, PageBounds pageBounds);

}
