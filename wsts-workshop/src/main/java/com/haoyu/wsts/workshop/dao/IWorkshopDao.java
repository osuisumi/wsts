package com.haoyu.wsts.workshop.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.workshop.entity.Workshop;

public interface IWorkshopDao {
	Workshop selectWorkshopById(String id);

	int insertWorkshop(Workshop workshop);

	int updateWorkshop(Map<String,Object> parameter);

	int deleteWorkshopByLogic(Map<String,Object> parameter);

	int deleteWorkshopByPhysics(String id);

	List<Workshop> findAll(Map<String, Object> parameter, PageBounds pageBounds);
	
	List<Workshop> findAllWithStat(Map<String,Object> parameter,PageBounds pageBounds);
	
	int count(Map<String,Object> parameter);
	
	List<Workshop> selectInterestedWorkshop(Map<String,Object> parameter,PageBounds pageBounds);
	
	Map<String,Workshop> workshopIdKeyMap(Map<String,Object> parameter);

}
