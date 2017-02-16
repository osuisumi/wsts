package com.haoyu.wsts.workshop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;

public interface IWorkshopUserResultService {
	
	Response createWorkshopUserResult(WorkshopUserResult workshopResult);
	
	Response updateWorkshopUserResult(WorkshopUserResult workshopResult);
	
	Response deleteWorkshopUserResult(WorkshopUserResult workshopResult);
	
	Response deleteWorkshopUserResultByWorkshopUserId(String workshopUserId);
	
	Response deleteWorkshopUserResultByWorkshopUserIds(List<String> workshopUserIds);
	
	WorkshopUserResult findWorkshopUserResultById(String id);
	
	List<WorkshopUserResult> findWorkshopUserResults(WorkshopUserResult workshopResult,PageBounds pageBounds);
	
	List<WorkshopUserResult> findWorkshopUserResults(Map<String,Object> parameter,PageBounds pageBounds);
	
	public Response updatePoint(String relationId,String userId);
	
	Response batchUpdateWorkshopResult(List<String> workshopUserIds,String workshopResult);
	
}
