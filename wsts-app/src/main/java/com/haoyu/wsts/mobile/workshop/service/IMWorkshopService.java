package com.haoyu.wsts.mobile.workshop.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;

public interface IMWorkshopService {
	
	public Response list(String type,PageBounds pageBounds);
	
	public Response view(String id);
	
	public Response detail(String id,PageBounds wuPageBounds);
	
	public Response studyPregress(String id);

}
