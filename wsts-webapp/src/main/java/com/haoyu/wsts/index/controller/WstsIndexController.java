package com.haoyu.wsts.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.utils.TemplateUtils;

@RequestMapping("wsts")
@Controller
public class WstsIndexController extends AbstractBaseController{
	
	private String getLogicalViewNamePrefix(){
		return TemplateUtils.getTemplatePath("/");
	}
	
	@RequestMapping("/index")
	public String index(){
		return getLogicalViewNamePrefix() + "index";
	}

}
