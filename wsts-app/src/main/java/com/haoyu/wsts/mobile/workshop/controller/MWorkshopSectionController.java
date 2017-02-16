package com.haoyu.wsts.mobile.workshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.sip.utils.TimeUtils;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopSectionService;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
import com.haoyu.wsts.workshop.utils.DateUtils;

@RestController
@RequestMapping("**/m/workshop_section")
public class MWorkshopSectionController extends AbstractBaseMobileController{
	@Resource
	private IWorkshopSectionService workshopSectionService;
	@Resource
	private IMWorkshopSectionService mWorkshopSectionService;
	
	@RequestMapping(method=RequestMethod.POST)
	public Response create(WorkshopSection workshopSection,String startTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			if(StringUtils.isNotEmpty(startTime)){
				Date start = sdf.parse(startTime);
				start = TimeUtils.getFirstDayOfMonth(start);
				workshopSection.getTimePeriod().setStartTime(DateUtils.getDayBegin(start));
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date end = sdf.parse(endTime);
				end = TimeUtils.getLastDayOfMonth(end);
				workshopSection.getTimePeriod().setEndTime(DateUtils.getDayEnd(end));
			}
		}catch(ParseException e){
			return Response.failInstance().responseMsg("startTime or endTime wrong");
		}

		return mWorkshopSectionService.create(workshopSection);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	public Response update(WorkshopSection workshopSection,String startTime,String endTime){
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(StringUtils.isNotEmpty(wsid)  ){
			workshopSection.setWorkshopId(wsid);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try{
			if(StringUtils.isNotEmpty(startTime)){
				Date start = sdf.parse(startTime);
				start = TimeUtils.getFirstDayOfMonth(start);
				workshopSection.getTimePeriod().setStartTime(DateUtils.getDayBegin(start));
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date end = sdf.parse(endTime);
				end = TimeUtils.getLastDayOfMonth(end);
				workshopSection.getTimePeriod().setEndTime(DateUtils.getDayEnd(end));
			}
		}catch(ParseException e){
			throw new RuntimeException(e.getMessage());
		}
		return workshopSectionService.updateWorkshopSection(workshopSection);
		
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(WorkshopSection workshopSection){
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(StringUtils.isNotEmpty(wsid)){
			workshopSection.setWorkshopId(wsid);
		}
		return workshopSectionService.deleteWorkshopSection(workshopSection);
	}
	
	
	

}
