package com.haoyu.wsts.workshop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.workshop.entity.WorkshopRelation;

public interface IWorkshopRelationService {
	
	Response createWorkshopRelation(WorkshopRelation workshopRelation);
	
	Response updateWorkshopRelation(WorkshopRelation workshopRelation);
	
	Response updateWorkshopRelationByWorkshopId(WorkshopRelation workshopRelation);
	
	Response updateWorkshopRelations(String relationId,List<String> workshopIds,TimePeriod timePeriod);
	
	Response deleteWorkshopRelation(WorkshopRelation workshopRelation);
	
	WorkshopRelation findWorkshopRelationById(String id);
	
	List<WorkshopRelation> findWorkshopRelations(WorkshopRelation workshopRelation,PageBounds pageBounds);
	
	List<WorkshopRelation> findWorkshopRelations(Map<String,Object> parameter,PageBounds pageBounds);

}
