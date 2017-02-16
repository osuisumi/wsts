package com.haoyu.wsts.announcement.controller;

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
@RequestMapping("**/announcement")
public class AnnouncementController extends AbstractBaseController{
	@Resource
	private IAnnouncementService announcementService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/announcement/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(String relationId,Model model){
		model.addAttribute("relationId", relationId);
		getPageBounds(10, true);
		return getLogicViewNamePerfix() + "list_announcement";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String relationId,Model model){
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix()+ "edit_announcement";
	}
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String edit(@PathVariable String id,Model model){
		model.addAttribute("announcement", announcementService.get(id));
		return getLogicViewNamePerfix() + "edit_announcement";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(Announcement announcement){
		return announcementService.createAnnouncement(announcement);
	}
	
	@RequestMapping(value="update",method=RequestMethod.PUT)
	@ResponseBody
	public Response update(Announcement announcement){
		return announcementService.updateAnnouncement(announcement);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(Announcement announcement){
		return announcementService.deleteAnnouncement(announcement);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public String view(Announcement announcement,Model model){
		model.addAttribute("announcement", announcementService.get(announcement.getId()));
		return getLogicViewNamePerfix() + "view_announcement";
	}

}
