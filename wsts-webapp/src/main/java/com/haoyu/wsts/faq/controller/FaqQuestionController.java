package com.haoyu.wsts.faq.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.tip.faq.entity.FaqQuestion;
import com.haoyu.tip.faq.service.IFaqQuestionService;
import com.haoyu.wsts.faq.service.impl.WstsFaqQuestionBizService;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/faq_question")
public class FaqQuestionController extends AbstractBaseController{
	
	@Resource
	private IFaqQuestionService faqQuestionService; 
	
	@Resource
	private WstsFaqQuestionBizService wstsFaqQuestionBizService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/faq/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(String relationId,String type ,String isStatePage,String id,String attentionalItemView,Model model){
		getPageBounds(10, true);
		model.addAttribute("id", id);
		model.addAttribute("isStatePage",isStatePage);
		model.addAttribute("relationId",relationId);
		model.addAttribute("type",type);
		model.addAttribute("attentionalItemView",attentionalItemView);
		return getLogicViewNamePerfix() + "list_faq_question";
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response create(FaqQuestion faqQuestion){
		return this.wstsFaqQuestionBizService.createWorkshopFaqQuestion(faqQuestion);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(FaqQuestion faqQuestion){		
		return this.faqQuestionService.deleteFaqQuestion(faqQuestion);
	}
	
	@RequestMapping(value="entities",method=RequestMethod.GET)
	@ResponseBody
	public List<FaqQuestion> entities(FaqQuestion faqQuestion){
		return faqQuestionService.listFaqQuestion(faqQuestion, getPageBounds(10, true));
	}
	
}
