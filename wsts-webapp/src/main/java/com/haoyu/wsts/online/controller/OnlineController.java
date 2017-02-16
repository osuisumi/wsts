package com.haoyu.wsts.online.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.online.service.IOnlineService;

@Controller
@RequestMapping("**/online")
public class OnlineController extends AbstractBaseController{

	@Resource
	private IOnlineService onlineService;
	
	@RequestMapping(value="incOnlineTime", method=RequestMethod.PUT)
	@ResponseBody
	public Response incOnlineTime(){
		return onlineService.incOnlineTime();
	}
	
}
