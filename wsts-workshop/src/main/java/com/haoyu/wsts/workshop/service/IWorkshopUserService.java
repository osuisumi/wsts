package com.haoyu.wsts.workshop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopUser;

public interface IWorkshopUserService {
	
	Response createWorkshopUser(WorkshopUser workshop);
	
	Response batchSave(Workshop workshop);
	
	Response updateWorkshopUser(WorkshopUser workshop);
	
	Response updateWorkshopUsers(WorkshopUser workshopUser);
	
	Response deleteWorkshopUser(WorkshopUser workshop);
	
	Response deleteWorkshopUserPhysics(WorkshopUser workshopUser);
	
	Response batchDelete(List<String> workshopUserIds,List<String> userIds);
	
	WorkshopUser findWorkshopUserById(String id);
	
	WorkshopUser findWorkshopUserWithActionInfoById(String id);
	
	List<WorkshopUser> findWorkshopUsers(WorkshopUser workshop,PageBounds pageBounds);
	
	List<WorkshopUser> findWorkshopUsers(Map<String,Object> parameter,PageBounds pageBounds);
	
	List<WorkshopUser> findWorkshopUsersWithActionInfo(Map<String,Object> parameter,PageBounds pageBounds);
	
	Map<String,List<WorkshopUser>> getWorkshopUserMap(Map<String,Object> parameter);
	
//	Map<String,WorkshopUser> getWorkshopRole(String workshopId,List<String> userIds);
	
	int count(Map<String,Object> parameter);
	

}
