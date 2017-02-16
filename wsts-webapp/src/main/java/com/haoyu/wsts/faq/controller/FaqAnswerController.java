package com.haoyu.wsts.faq.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.tip.faq.entity.FaqAnswer;
import com.haoyu.tip.faq.service.IFaqAnswerService;

@Controller
@RequestMapping("**/faq_answer")
public class FaqAnswerController {
	
	@Resource
	private IFaqAnswerService faqAnswerService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(FaqAnswer faqAnswer){
		return this.faqAnswerService.create(faqAnswer);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(FaqAnswer faqAnswer){		
		return this.faqAnswerService.delete(faqAnswer.getId());
	}
	
}
