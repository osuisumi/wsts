package com.haoyu.wsts.mobile.activity.service;


import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.activity.web.ActivityParam;

public interface IMActivityWstsService {
	
	public Response list(String sectionId);
	
	public Response view(String actId);
	
	public Response createActivity(ActivityParam activityParam);
	
	public Response viewForUpdate(String actId);

}
