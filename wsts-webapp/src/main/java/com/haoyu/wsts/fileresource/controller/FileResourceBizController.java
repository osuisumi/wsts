package com.haoyu.wsts.fileresource.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.utils.TemplateUtils;


@RequestMapping("**/fileResource")
@Controller
public class FileResourceBizController extends AbstractBaseController{
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private IFileResourceBizService fileResourceBizService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/fileResource/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(String relationId,String parentId,String isStatePage,Model model){
		model.addAttribute("isStatePage", isStatePage);
		model.addAttribute("parentId", parentId);
		model.addAttribute("relationId", relationId);
		getPageBounds(12, true);
		model.addAttribute("showType",request.getParameter("showType"));
		return getLogicViewNamePerfix() + "list_file_resource";
	}
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String parentId,String isFolder,String relationId,String relationType,Model model){
		model.addAttribute("relationId",relationId);
		model.addAttribute("parentId",parentId);
		model.addAttribute("isFolder",isFolder);
		model.addAttribute("relationType", relationType);
		return getLogicViewNamePerfix() + "edit_file_resource";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response save(FileResource fileResource){
		return fileResourceService.createFileResource(fileResource);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(FileResource fileResource){		
		return fileResourceService.delete(fileResource.getId());
	}
	
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String edit(FileResource fileResource,Model model){	
		model.addAttribute("fileResource",fileResourceService.get(fileResource.getId()));
		model.addAttribute("isFolder", fileResource.getIsFolder());
		return getLogicViewNamePerfix() + "edit_file_resource";
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Response update(FileResource fileResource){
		return fileResourceService.update(fileResource);
	}
	
	@RequestMapping(value="moveFile",method=RequestMethod.GET)
	public String moveFile(String id,String relationId,Model model){
		model.addAttribute("id", id);
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix() + "move_file";
	}
	
}
