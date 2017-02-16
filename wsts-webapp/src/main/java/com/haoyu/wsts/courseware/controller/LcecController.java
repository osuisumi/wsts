package com.haoyu.wsts.courseware.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;


@Controller
@RequestMapping("**/lcec")
public class LcecController extends AbstractBaseController{
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/study/activity/lcec/");
	}
	
	@RequestMapping(value="{id}/evaluate",method=RequestMethod.GET)
	public String editEvaluate(@PathVariable("id") String lcecId,String aid,Model model){
		model.addAttribute("lcecId",lcecId);
		model.addAttribute("aid", aid);
		return  getLogicViewNamePerfix() + "edit_evaluate";
	}
	
	@RequestMapping(value="{id}/evaluateResult",method=RequestMethod.GET)
	public String viewEvaluateResult(@PathVariable("id") String lcecId,String aid,Model model){
		getPageBounds(10, true);
		model.addAttribute("lcecId",lcecId);
		model.addAttribute("aid", aid);
		return getLogicViewNamePerfix() + "view_evaluate_result";
	}
	
	@RequestMapping(value="{lcecId}/{itemId}/evaluateResultDetail")
	public String viewEvaluateResultDetail(@PathVariable("lcecId") String lcecId,@PathVariable("itemId") String itemId,Model model){
		model.addAttribute("lcecId", lcecId);
		model.addAttribute("itemId", itemId);
		return getLogicViewNamePerfix() + "view_evaluate_result_detail";
	}
	
	@RequestMapping(value="listLcecEvaluateSubmissions",method=RequestMethod.GET)
	public String listEvaluateSubmissions(String lcecId,Model model){
		getPageBounds(10,true);
		model.addAttribute("lcecId", lcecId);
		return getLogicViewNamePerfix() + "list_evaluate_submissions";
	}
	
}
