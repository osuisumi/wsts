package com.haoyu.wsts.mobile.workshop.service;


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;

public interface IMWorkshopUserService {
	
	public Response listExcellents(String wid,PageBounds pageBounds);
	
	public Response listStudent(String wid,PageBounds pageBounds);
	
	public Response listMember(String wid,PageBounds pageBounds,String realName);

}
