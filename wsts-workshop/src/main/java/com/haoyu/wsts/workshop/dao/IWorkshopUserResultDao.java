package com.haoyu.wsts.workshop.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;

public interface IWorkshopUserResultDao {
	
	WorkshopUserResult selectWorkshopUserResultById(String id);

	int insertWorkshopUserResult(WorkshopUserResult workshopUserResult);

	int updateWorkshopUserResult(WorkshopUserResult workshopUserResult);

	int deleteWorkshopUserResultByLogic(WorkshopUserResult workshopUserResult);

	int deleteWorkshopUserResultByPhysics(String id);
	
	int deleteByWorkshopUserIds(List<String> workshopUserIds);

	List<WorkshopUserResult> findAll(Map<String, Object> parameter, PageBounds pageBounds);
	
	int batchUpdateWorkshopResult(List<String> workshopUserIds,String workshopResult);

}
