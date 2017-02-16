package com.haoyu.wsts.workshop.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.workshop.entity.WorkshopUser;

public interface IWorkshopUserDao {
	WorkshopUser selectWorkshopUserById(String id);
	
	WorkshopUser selectWorkshopUserWithActionInfoById(String id);

	int insertWorkshopUser(WorkshopUser workshopUser);

	int updateWorkshopUser(WorkshopUser workshopUser);

	int deleteWorkshopUserByLogic(WorkshopUser workshopUser);

	int deleteWorkshopUserByPhysics(String id);

	List<WorkshopUser> findAll(Map<String, Object> parameter, PageBounds pageBounds);
	
	List<WorkshopUser> findAllWithActionInfo(Map<String,Object> parameter,PageBounds pageBounds);
	
	int count(Map<String,Object> parameter);
	
	int batchDelete(List<String> ids);

}
