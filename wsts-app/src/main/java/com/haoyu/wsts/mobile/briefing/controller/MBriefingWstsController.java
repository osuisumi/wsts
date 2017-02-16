package com.haoyu.wsts.mobile.briefing.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.tip.announcement.entity.Announcement;
import com.haoyu.tip.announcement.mobile.service.IMAnnouncementService;
import com.haoyu.tip.announcement.service.IAnnouncementService;

@RestController
@RequestMapping("**/m/briefing")
public class MBriefingWstsController extends AbstractBaseMobileController{

	@Resource
	private IAnnouncementService announcementService;
	@Resource
	private IMAnnouncementService announcementMobileService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Response list(Announcement announcement){
		return announcementMobileService.listAnnouncement(announcement,getPageBounds(10,true),"true".equals(request.getParameter("getContent"))?true:false);
	}
	
	@RequestMapping(value="view/{id}",method=RequestMethod.GET)
	public Response view(Announcement announcement){
		return announcementMobileService.viewAnnouncement(announcement);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Response create(Announcement announcement){
		return announcementMobileService.createAnnouncement(announcement);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	public Response update(Announcement announcement){
		return announcementMobileService.updateAnnouncement(announcement);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public Response delete(Announcement announcement){
		return announcementService.deleteAnnouncement(announcement);
	}
	
}
