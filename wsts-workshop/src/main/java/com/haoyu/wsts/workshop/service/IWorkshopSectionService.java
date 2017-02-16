package com.haoyu.wsts.workshop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.workshop.entity.WorkshopSection;

public interface IWorkshopSectionService {
	
	Response createWorkshopSection(WorkshopSection workshopSection);
	
	Response updateWorkshopSection(WorkshopSection workshopSection);
	
	Response deleteWorkshopSection(WorkshopSection workshopSection);
	
	WorkshopSection findWorkshopSectionById(String id);
	
	List<WorkshopSection> findWorkshopSections(WorkshopSection workshopSection,boolean isGetChildren,PageBounds pageBounds);
	
	List<WorkshopSection> findWorkshopSections(Map<String,Object> parameter,boolean isGetChildren,PageBounds pageBounds);

}
