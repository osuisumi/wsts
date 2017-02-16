package com.haoyu.wsts.workshop.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.user.entity.UserInfo;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.utils.TemplateUtils;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
@Controller
@RequestMapping("workshopUser")
public class WorkshopUserController extends AbstractBaseController{
	@Resource
	private IWorkshopUserService workshopUserService;
	@Resource
	private IWorkshopService workshopService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/workshop/user/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(String workshopId,String realName,String deptName,Model model){
		model.addAttribute("workshopId", workshopId);
		model.addAttribute("realName", realName);
		model.addAttribute("deptName", deptName);
		getPageBounds(10, true);
		return getLogicViewNamePerfix() + "list_workshop_user";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String workshopId,Model model){
		model.addAttribute("workshopId", workshopId);
		return getLogicViewNamePerfix() + "edit_workshop_user";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(WorkshopUser workshopUser){
		return workshopUserService.createWorkshopUser(workshopUser);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Response update(WorkshopUser workshopUser){
		return workshopUserService.updateWorkshopUsers(workshopUser);
	}
	
	@RequestMapping(value="batchSave",method=RequestMethod.POST)
	@ResponseBody
	public Response saveWorkshopUsers(Workshop workshop){
		return workshopUserService.batchSave(workshop);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(WorkshopUser workshopUser){
		return workshopUserService.deleteWorkshopUserPhysics(workshopUser);
	}
	
	@RequestMapping(value="isAllow",method=RequestMethod.GET)
	@ResponseBody
	public Response isAllow(String userId,String workshopId){
		Map<String,Object> parameter  = Maps.newHashMap();
		parameter.put("userId", userId);
		parameter.put("workshopId", workshopId);
		List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(parameter, null);
		if(CollectionUtils.isNotEmpty(workshopUsers)){
			return Response.failInstance().responseMsg("该用户已经在该工作坊内了");
		}else{
			return Response.successInstance();
		}
	}
	
	@RequestMapping(value="/{wid}/{uid}/entity",method=RequestMethod.GET)
	@ResponseBody
	public WorkshopUser entity(@PathVariable("wid")String wid,@PathVariable("uid")String uid){
		return workshopUserService.findWorkshopUserById(WorkshopUser.getId(wid, uid));
	}
	
	@RequestMapping(value="entities",method=RequestMethod.GET)
	@ResponseBody
	public List<UserInfo> workshopUserEntities(SearchParam searchParam){
		List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(searchParam.getParamMap(),getPageBounds(10, true));
		if(CollectionUtils.isNotEmpty(workshopUsers)){
			List<UserInfo> result = Collections3.extractToList(workshopUsers, "userInfo");
			return result;
		}
		return null;
	}
	
}
