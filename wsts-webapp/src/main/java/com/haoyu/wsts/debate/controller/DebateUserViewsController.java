package com.haoyu.wsts.debate.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.aip.debate.entity.DebateUser;
import com.haoyu.aip.debate.entity.DebateUserViews;
import com.haoyu.aip.debate.service.IDebateUserViewsService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;

@RequestMapping("**/debateUserViews")
@Controller
public class DebateUserViewsController extends AbstractBaseController{
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/study/activity/debate/");
	}

	@Resource
	private IDebateUserViewsService debateUserViewsService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(DebateUserViews debateUserViews){
		return debateUserViewsService.createDebateUserViews(debateUserViews);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(DebateUser debateUser,Model model){
		getPageBounds(10,true);
		model.addAttribute("debateUser", debateUser);
		return getLogicViewNamePerfix() + "list_debate_user_views";
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="delete/{id}")
	@ResponseBody
	public Response delete(DebateUserViews debateUserViews){
		return debateUserViewsService.deleteDebateUserViews(debateUserViews);
	}
}
