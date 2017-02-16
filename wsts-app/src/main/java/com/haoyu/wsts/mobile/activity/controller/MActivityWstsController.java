package com.haoyu.wsts.mobile.activity.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.activity.web.ActivityParam;
import com.haoyu.wsts.mobile.activity.service.IMActivityWstsService;

@RestController
@RequestMapping("**/m/activity/wsts")
public class MActivityWstsController extends AbstractBaseMobileController{
	@Resource
	private IMActivityWstsService mActivityWstsService;
	@Resource
	private IWstsActivityBizService wstsActivityBizService;
	@Resource
	private IActivityService activityService;
	
	@RequestMapping(value="{sectionId}")
	public Response listActivity(@PathVariable String sectionId){
		return mActivityWstsService.list(sectionId);
	}
	
	@RequestMapping("{actId}/view")
	public Response viewActivity(@PathVariable String actId){
		return mActivityWstsService.view(actId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Response create(ActivityParam activityParam){
		return mActivityWstsService.createActivity(activityParam);
	}
	
	@RequestMapping(value="{activity.id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(ActivityParam activityParam){
		return wstsActivityBizService.updateActivity(activityParam);
	}
	
	@RequestMapping(value="{actId}/view_for_update",method=RequestMethod.GET)
	public Response viewForUpdate(@PathVariable String actId){
		return mActivityWstsService.viewForUpdate(actId);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public Response delete(Activity activity){
		return activityService.deleteActivityByLogic(activity);
	}
	
	
}
