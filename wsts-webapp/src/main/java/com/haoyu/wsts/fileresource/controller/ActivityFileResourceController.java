package com.haoyu.wsts.fileresource.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/activity/file_resource")
public class ActivityFileResourceController extends AbstractBaseController{
	@Resource
	private IFileResourceBizService fileResourceBizService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/fileResource/activityFileResource/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String listFileResource(String relationId,String parentId,String type,Model model){
		model.addAttribute("parentId", parentId);
		model.addAttribute("relationId", relationId);
		model.addAttribute("type", type);
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("relationId", relationId);
		model.addAttribute("fileCount",fileResourceBizService.getFileResourceFileCount(parameter));
		return getLogicViewNamePerfix() + "list_file_resource";
	}
	
	@RequestMapping(value="listUserFileCount",method=RequestMethod.GET)
	public String listUserFileCount(String relationId,String realName,Model model){
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("relationId", relationId);
		if(StringUtils.isNotEmpty(realName)){
			parameter.put("realName", realName);
			model.addAttribute("realName", realName);
		}
		List result = fileResourceBizService.listUserFileCount(parameter);
		model.addAttribute("userFileCounts",result);
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix() + "list_user_file_count";
	}
	
	@RequestMapping(value="more",method=RequestMethod.GET)
	public String listMore(String relationId,String parentId,String showType,String type,Model model){
		model.addAttribute("parentId", parentId);
		model.addAttribute("relationId", relationId);
		model.addAttribute("showType", showType);
		model.addAttribute("type", type);
		getPageBounds(Integer.MAX_VALUE, true);
		return getLogicViewNamePerfix() + "list_more_file_resource";
	}

}
