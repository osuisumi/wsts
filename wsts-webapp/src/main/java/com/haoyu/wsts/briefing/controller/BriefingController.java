package com.haoyu.wsts.briefing.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.tip.announcement.entity.Announcement;
import com.haoyu.tip.announcement.service.IAnnouncementService;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/briefing")
public class BriefingController extends AbstractBaseController {

	@Resource
	private IAnnouncementService announcementService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/briefing/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(String relationId,Model model){
		model.addAttribute("relationId", relationId);
		model.addAttribute("page", request.getParameter("page"));
		return getLogicViewNamePerfix() + "list_briefing";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String relationId,Model model){
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix()+ "edit_briefing";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(Announcement announcement){
		return announcementService.createAnnouncement(announcement);
	}
	
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String edit(Announcement announcement,Model model){	
		model.addAttribute("briefing",announcementService.get(announcement.getId()));
		return getLogicViewNamePerfix() + "edit_briefing";
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Response update(Announcement announcement){
		return announcementService.update(announcement);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(Announcement announcement){		
		return announcementService.deleteAnnouncement(announcement);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public String view(@PathVariable("id") String id,Model model){
		model.addAttribute("briefing",announcementService.get(id));
		return this.getLogicViewNamePerfix() + "view_briefing";
	}
	
}
