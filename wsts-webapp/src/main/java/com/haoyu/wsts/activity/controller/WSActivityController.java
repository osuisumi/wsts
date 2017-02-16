package com.haoyu.wsts.activity.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityType;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.activity.web.ActivityParam;
import com.haoyu.wsts.utils.TemplateUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;

@Controller
@RequestMapping("**/activity")
public class WSActivityController extends AbstractBaseController{
	
	@Resource
	private IWstsActivityBizService wstsActivityBizService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IWorkshopService workshopService;
	
	private String getLogicalViewNamePrefix(){
		return TemplateUtils.getTemplatePath("/make/activity/");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Activity activity, Model model){
		model.addAttribute("activity", activity);
		return getLogicalViewNamePrefix() + "list_activity";
	}
	
	@RequestMapping(value="{id}" ,method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(Activity activity){
		return activityService.deleteActivityByLogic(activity);
	}
	
	@RequestMapping(value="create", method = RequestMethod.GET)
	public String create(Activity activity, Model model){
		model.addAttribute("activity", activity);
		model.addAllAttributes(request.getParameterMap());
		if (ActivityType.DISCUSSION.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "discussion/edit_discussion";
		}else if (ActivityType.VIDEO.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "video/edit_video";
		}else if (ActivityType.HTML.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "text/edit_html";
		}else if (ActivityType.ASSIGNMENT.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "assignment/edit_assignment";
		}else if(ActivityType.SURVEY.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "survey/edit_survey";
		}else if(ActivityType.LESSON_PLAN.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "lessonPlan/edit_lesson_plan";
		}else if(ActivityType.DEBATE.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "debate/edit_debate";
		}else if(ActivityType.LCEC.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "lcec/edit_lcec";
		}
		return getLogicalViewNamePrefix() + activity.getType()+"/edit_"+activity.getType();
	}
	
	@RequestMapping(value="{id}/edit", method = RequestMethod.GET)
	public String edit(Activity activity, Model model){
		activity = activityService.getActivity(activity.getId());
		model.addAttribute("activity", activity);
		model.addAllAttributes(request.getParameterMap());
		if (ActivityType.DISCUSSION.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "discussion/edit_discussion";
		}else if (ActivityType.VIDEO.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "video/edit_video";
		}else if (ActivityType.HTML.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "text/edit_html";
		}else if (ActivityType.ASSIGNMENT.equals(activity.getType())) {
			return getLogicalViewNamePrefix() + "assignment/edit_assignment";
		}else if(ActivityType.SURVEY.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "survey/edit_survey";
		}else if(ActivityType.LESSON_PLAN.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "lessonPlan/edit_lesson_plan";
		}else if(ActivityType.DEBATE.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "debate/edit_debate";
		}else if(ActivityType.LCEC.equals(activity.getType())){
			return getLogicalViewNamePrefix() + "lcec/edit_lcec";
		}
		return getLogicalViewNamePrefix() + activity.getType()+"/edit_"+activity.getType();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Response create(ActivityParam activityParam){
		return wstsActivityBizService.createActivity(activityParam);
	}
	
	@RequestMapping(value="{activity.id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(ActivityParam activityParam){
		return wstsActivityBizService.updateActivity(activityParam);
	}
	
	@RequestMapping(value="{id}/update", method = RequestMethod.PUT)
	@ResponseBody
	public Response updateActivity(Activity activity){
		return activityService.updateActivity(activity, false);
	}
	
	@RequestMapping(value="{id}/view", method = RequestMethod.GET)
	public String view(Activity activity, Model model){
		model.addAttribute("activity", activityService.getActivity(activity.getId()));
		model.addAttribute("workshop", workshopService.findWorkshopById(WsIdObject.getWsIdObject().getWsid()));
		model.addAllAttributes(request.getParameterMap());
		getPageBounds(10, true);
		return TemplateUtils.getTemplatePath("/study/activity/") + "view_activity";
	}
	
	@RequestMapping(value="createSystemActivity/{workshopId}",method=RequestMethod.GET)
	@ResponseBody
	public Response createSystemActivity(@PathVariable("workshopId") String workshopId){
		return wstsActivityBizService.createSystemActivity(workshopId);
	}
	
//	@RequestMapping(value="updateBatch", method = RequestMethod.PUT)
//	@ResponseBody
//	public Response updateBatch(Section section){
//		if (Collections3.isNotEmpty(section.getActivities())) {
//			for (Activity activity : section.getActivities()) {
//				activityService.updateActivity(activity, false);
//			}
//		}
//		return Response.successInstance();
//	}

}
