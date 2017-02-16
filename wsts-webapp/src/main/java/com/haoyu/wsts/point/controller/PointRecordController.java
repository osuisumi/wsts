package com.haoyu.wsts.point.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/pointRecord")
public class PointRecordController extends AbstractBaseController{
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/point/");
	}
	
	@RequestMapping
	public String list(String workshopId,Model model){
		model.addAttribute("workshopId", workshopId);
		return getLogicViewNamePerfix() + "list_point_record";
	}

}
