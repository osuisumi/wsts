package com.haoyu.wsts.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;

@Controller
@RequestMapping("m/wsts")
public class MWstsIndexController {
	
	@RequestMapping(value="index")
	@ResponseBody
	public Response index(){
		return Response.successInstance();
	}

}
