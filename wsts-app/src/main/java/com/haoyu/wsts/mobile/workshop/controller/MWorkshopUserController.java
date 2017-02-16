package com.haoyu.wsts.mobile.workshop.controller;


import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.sip.message.service.IMessageService;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopUserService;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;

@RestController
@RequestMapping("**/m/workshop_user")
public class MWorkshopUserController extends AbstractBaseMobileController{
	@Resource
	private IMWorkshopUserService mWorkshopUserService;
	@Resource
	private IWorkshopUserResultService workshopUserResultService;
	@Resource
	private IMessageService messageService;
	@Resource
	private IWorkshopUserService workshopUserService;
	
	
	@RequestMapping(value="{wid}/excellent_users",method=RequestMethod.GET)
	public Response listExcellents(@PathVariable String wid){
		PageBounds pageBounds = getPageBounds(3, true);
		return mWorkshopUserService.listExcellents(wid,pageBounds);
	}
	
	@RequestMapping(value="{wid}/students",method=RequestMethod.GET)
	public Response listStudent(@PathVariable String wid){
		PageBounds pageBounds = getPageBounds(10, true);
		return mWorkshopUserService.listStudent(wid, pageBounds);
	}
	
	@RequestMapping(value="evaluate")
	public Response saveEvaluate(String workshopUserIds,String workshopResult){
		return workshopUserResultService.batchUpdateWorkshopResult(Arrays.asList(workshopUserIds.split(",")), workshopResult);
	}
	
	@RequestMapping(value="{wid}/members",method=RequestMethod.GET)
	public Response listMember(@PathVariable String wid,String realName){
		PageBounds pageBounds = getPageBounds(10,true);
		return mWorkshopUserService.listMember(wid, pageBounds,realName);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public Response delete(WorkshopUser workshopUser){
		return workshopUserService.deleteWorkshopUserPhysics(workshopUser);
	}

}
