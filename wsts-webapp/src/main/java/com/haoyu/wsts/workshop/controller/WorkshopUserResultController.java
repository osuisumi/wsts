package com.haoyu.wsts.workshop.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;
@Controller
@RequestMapping("**/workshopUserResult")
public class WorkshopUserResultController extends AbstractBaseController{
	@Resource
	private IWorkshopUserResultService workshopUserResultService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/workshop/userResult/");
	}
	

	@RequestMapping(method=RequestMethod.GET)
	public String list(String workshopId,String realName,String deptName,Model model){
		model.addAttribute("realName",realName);
		model.addAttribute("deptName",deptName);
		model.addAttribute("workshopId", workshopId);
		getPageBounds(10,true);
		return getLogicViewNamePerfix() + "list_workshop_user_result"; 
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String editWorkshopUserResults(String workshopId,String workshopUserIds,Model model){
		model.addAttribute("workshopId", workshopId);
		model.addAttribute("workshopUserIds", workshopUserIds);
		return getLogicViewNamePerfix() + "edit_workshop_user_result";
	}
	
	@RequestMapping(value="evaluate",method=RequestMethod.POST)
	@ResponseBody
	public Response evaluate(String workshopUserIds,String workshopResult){
		return workshopUserResultService.batchUpdateWorkshopResult(Arrays.asList(workshopUserIds.split(",")), workshopResult);
	}
	
	@RequestMapping(value="editMessage",method=RequestMethod.GET)
	public String editMessage(String userIds,Model model){
		model.addAttribute("userIds", userIds);
		return getLogicViewNamePerfix() + "edit_message";
		
	}
	
}
