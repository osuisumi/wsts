package com.haoyu.wsts.debate.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.aip.debate.entity.DebateUser;
import com.haoyu.aip.debate.service.IDebateUserService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;

@RequestMapping("**/debateUser")
@Controller
public class DebateUserController extends AbstractBaseController{
	@Resource
	private IDebateUserService debateUserService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(DebateUser debateUser){
		return debateUserService.createDebateUser(debateUser);
	}

}
