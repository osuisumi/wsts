package com.haoyu.wsts.comment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.comment.entity.Comment;
import com.haoyu.sip.comment.service.ICommentService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.wsts.comment.service.ICommentBizService;
import com.haoyu.wsts.utils.TemplateUtils;

@Controller
@RequestMapping("**/comment")
public class CommentWstsController extends AbstractBaseController{
	
	@Resource
	private ICommentService commentService;
	@Resource
	private ICommentBizService commentBizService;
	
	private String getLogicViewNamePerfix(){
		return TemplateUtils.getTemplatePath("/comment/");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Comment comment,String type,Model model){
		getPageBounds(10,true);
		model.addAllAttributes(request.getParameterMap());
		model.addAttribute("comment",comment);
		return getLogicViewNamePerfix() + "list_comment";
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Response create(Comment comment){
		return this.commentService.createComment(comment);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Response update(Comment comment){
		return this.commentService.updateComment(comment);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public Response delete(Comment comment){		
		return this.commentService.deleteComment(comment);
	}
	
	
	@RequestMapping(value="activity/{relationId}")
	public String listActivityComment(@PathVariable String relationId,Model model){
		getPageBounds(10,true);
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix() + "list_activity_comment";
	}
	
	@RequestMapping(value="activity/child")
	public String listActivityChildComent(String mainId,String relationId, Model model){
		model.addAttribute("mainId", mainId);
		model.addAttribute("relationId", relationId);
		return getLogicViewNamePerfix() + "list_activity_child_comment";
	}
	
	@RequestMapping(value="entities",method=RequestMethod.GET)
	@ResponseBody
	public List<Comment> entities(SearchParam searchParam){
		List<Comment> comments = commentService.list(searchParam.getParamMap(), getPageBounds(10, true));
		return comments;
	}
	
}
