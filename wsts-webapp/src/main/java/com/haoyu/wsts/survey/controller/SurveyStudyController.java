package com.haoyu.wsts.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/study/survey")
public class SurveyStudyController extends AbstractBaseController{
	
	private String getLogicalViewNamePrefix(){
		return TemplateUtils.getTemplatePath("/study/survey/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String view(String surveyId,String userId,String relationId,Model model){
		model.addAttribute("surveyId", surveyId);
		model.addAttribute("userId", userId);
		model.addAttribute("relationId", relationId);
		return getLogicalViewNamePrefix() + "view_survey";
	}
	
}
