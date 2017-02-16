package com.haoyu.wsts.workshop.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.workshop.entity.Workshop;

public interface IWorkshopService {
	Response createWorkshop(Workshop workshop);
	
	Response createExtendWorkshop(Workshop workshop,String relationId,TimePeriod timePeriod);
	
	Response updateWorkshop(Workshop workshop);
	
	Response deleteWorkshop(Workshop workshop);
	
	Workshop findWorkshopById(String id);
	
	Workshop findWorkshopById(String id,boolean getUser);
	
	Workshop findWorkshopByIdWithStat(Map<String,Object> parameter);
	
	List<Workshop> findWorkshops(Workshop workshop,PageBounds pageBounds);
	
	List<Workshop> findWorkshops(Map<String,Object> parameter,PageBounds pageBounds);
	
	List<Workshop> findWorkshopsWithStat(Map<String,Object> parameter,PageBounds pageBounds);
	
	int count(Map<String,Object> parameter);
	
	List<Workshop> selectInterestedWorkshop(Map<String,Object> parameter,PageBounds pageBounds);
	
	Map<String,Workshop> workshopIdKeyMap(Map<String,Object> parameter);
	
}
