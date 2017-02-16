package com.haoyu.wsts.workshop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.DateUtils;
import com.haoyu.wsts.workshop.utils.WorkshopType;

@Controller
@RequestMapping("workshop")
public class WorkshopController extends AbstractBaseController{
	@Resource
	private IWorkshopService workshopService;
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/workshop/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		getPageBounds(10, true);
		return getLogicViewNamePerfix() + "list_workshop";
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public String view(@PathVariable String id,Model model){
		model.addAttribute("id",id);
		return getLogicViewNamePerfix() + "view_workshop";
	}
	
	@RequestMapping(value="manage/index")
	public String manageIndex(String workshopId,Model model){
		model.addAttribute("workshopId", workshopId);
		return getLogicViewNamePerfix() + "manage_index";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(){
		return getLogicViewNamePerfix() + "edit_workshop";
	}
	
	@RequestMapping(value="edit/{id}",method=RequestMethod.GET)
	public String edit(@PathVariable("id")String id,Model model){
		model.addAttribute("id", id);
		return getLogicViewNamePerfix() + "edit_workshop";
	}
	
	@RequestMapping(value="update/{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Response update(Workshop workshop){
		return workshopService.updateWorkshop(workshop);
	}
	

	
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public Response save(Workshop workshop){
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("creator",ThreadContext.getUser().getId());
		parameter.put("type",WorkshopType.PERSONAL);
		List<Workshop> workshops = workshopService.findWorkshops(parameter, null);
		if(CollectionUtils.isNotEmpty(workshops)){
			return Response.failInstance().responseMsg("您已经创建过个人工作坊");
		}else{
			return workshopService.createWorkshop(workshop);
		}
	}
	
	@RequestMapping(value="achievement",method=RequestMethod.GET)
	public String achievement(String workshopId,Model model){
		model.addAttribute("workshopId", workshopId);
		return getLogicViewNamePerfix() + "workshop_achievement";
	}
	
	@RequestMapping(value="{id}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("id")String id,Model model){
		model.addAttribute("id", id);
		return getLogicViewNamePerfix() + "workshop_detail";
	}
	
	@RequestMapping(value="{id}/{fieldName}/editSummary",method=RequestMethod.GET)
	public String editWorkshopSummary(@PathVariable("fieldName") String fieldName,@PathVariable("id") String id,Model model){
		model.addAttribute("fieldName", fieldName);
		model.addAttribute("id", id);
		return getLogicViewNamePerfix() + "edit_workshop_summary";
	}
	
	@RequestMapping(value="{id}/update")
	@ResponseBody
	public Response update(Workshop workshop,String startTime,String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TimePeriod timePeriod = new TimePeriod();
		try{
			if(StringUtils.isNotEmpty(startTime)){
				Date start = DateUtils.getDayBegin(sdf.parse(startTime));
				timePeriod.setStartTime(start);
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date end = DateUtils.getDayEnd(sdf.parse(endTime));
				timePeriod.setEndTime(end);
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		workshop.setTimePeriod(timePeriod);
		return workshopService.updateWorkshop(workshop);
	}
	
	@RequestMapping(value="{id}/studyState",method=RequestMethod.GET)
	public String myStudyStat(@PathVariable("id") String id,Model model){
		model.addAttribute("id",id);
		return getLogicViewNamePerfix() + "my_study_state";
	}
	
}
