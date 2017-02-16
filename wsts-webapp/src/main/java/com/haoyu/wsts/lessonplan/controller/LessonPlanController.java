package com.haoyu.wsts.lessonplan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.aip.lessonplan.entity.LessonPlanRecord;
import com.haoyu.aip.lessonplan.service.ILessonPlanRecordService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;

@RequestMapping("**/lessonPlan")
@Controller
public class LessonPlanController extends AbstractBaseController{
	@Resource
	private ILessonPlanRecordService lessonPlanRecordService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/study/activity/lessonPlan/");
	}
	
	@RequestMapping(value="editRecord",method=RequestMethod.GET)
	public String editRecord(String lessonPlanId,Model model){
		model.addAttribute("lessonPlanId", lessonPlanId);
		return getLogicViewNamePerfix() + "edit_lesson_plan_record";
	}
	
	@RequestMapping(value="saveRecord",method=RequestMethod.POST)
	@ResponseBody
	public Response saveRecord(LessonPlanRecord lessonPlanRecord){
		Response response = lessonPlanRecordService.create(lessonPlanRecord);
		if(response.isSuccess()){
			response.setResponseData(lessonPlanRecord);
		}
		return response;
	}
	
	@RequestMapping(value="viewRecord",method=RequestMethod.GET)
	public String viewRecord(String content,Model model){
		model.addAttribute("content", content);
		return getLogicViewNamePerfix() + "view_record";
	}
	
	@RequestMapping(value="deleteLessonPlanRecord/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Response deleteRecord(LessonPlanRecord lessonPlanRecord){
		return lessonPlanRecordService.delete(lessonPlanRecord.getId());
	}
	

}
