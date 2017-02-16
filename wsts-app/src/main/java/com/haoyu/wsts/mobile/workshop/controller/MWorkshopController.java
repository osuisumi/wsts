package com.haoyu.wsts.mobile.workshop.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopService;

@RestController
@RequestMapping("**/m/workshop")
public class MWorkshopController extends AbstractBaseMobileController{
	@Resource
	private IMWorkshopService mWorkshopService;
	
	@RequestMapping(method=RequestMethod.GET)
	public Response list(String type){
		PageBounds pageBounds = getPageBounds(10, true);
		return mWorkshopService.list(type, pageBounds);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public Response view(@PathVariable String id){
		return mWorkshopService.view(id);
	}
	
	@RequestMapping(value="{id}/detail",method=RequestMethod.GET)
	public Response detail(@PathVariable String id){
		PageBounds pageBounds = getPageBounds(4, true);
		return mWorkshopService.detail(id, pageBounds);
	}
	
	@RequestMapping(value="{id}/study_progress",method=RequestMethod.GET)
	public Response studyProgress(@PathVariable String id){
		return mWorkshopService.studyPregress(id);
	}
	
	

}
