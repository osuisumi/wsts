package com.haoyu.wsts.workshop.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.sip.utils.TimeUtils;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.TemplateUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
import com.haoyu.wsts.workshop.utils.DateUtils;

@Controller
@RequestMapping("**/workshopSection")
public class WorkshopSectionController extends AbstractBaseController{
	
	private final static Logger  logger   = LoggerFactory.getLogger(WorkshopSectionController.class);
	
	@Resource
	private IWorkshopSectionService workshopSectionService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/workshopSection/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String workshopSection(String workshopId,Model model){
		model.addAttribute("workshopId",workshopId);
		return getLogicViewNamePerfix() + "workshop_section";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String listworkshopSection(String workshopId,Model model){
		if(SecurityUtils.getSubject().getSession().getAttribute(workshopId) == null){
			SecurityUtils.getSubject().getSession().setAttribute(workshopId, "first");
		}else{
			SecurityUtils.getSubject().getSession().setAttribute(workshopId, "notFirst");
		};
		
		model.addAttribute("workshopId",workshopId);
		return getLogicViewNamePerfix() + "list_workshop_section";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(WorkshopSection workshopSection,String startTime,String endTime){
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

		return workshopSectionService.createWorkshopSection(workshopSection);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Response update(WorkshopSection workshopSection,String startTime,String endTime){
		String source = request.getParameter("source");
		if("workshop_section_js".equals(source)){
			logger.info("normal update workshopSection" + "======userId:"+ThreadContext.getUser().getId() + "======sectionId:" + workshopSection.getId() + "=====title:"+workshopSection.getTitle());
		}else{
			logger.error("error update workshopSection" + "======userId:" + ThreadContext.getUser().getId() + "======sectionId:" + workshopSection.getId() + "=====title:"+workshopSection.getTitle());
		}
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(StringUtils.isNotEmpty(wsid) && (SecurityUtils.getSubject().hasRole(RoleCodeConstant.MASTER+"_"+wsid)||SecurityUtils.getSubject().hasRole(RoleCodeConstant.MEMBER+"_"+wsid)) ){
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
		}else{
			return Response.failInstance();
		}
		
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(WorkshopSection workshopSection){
		return workshopSectionService.deleteWorkshopSection(workshopSection);
	}

}
