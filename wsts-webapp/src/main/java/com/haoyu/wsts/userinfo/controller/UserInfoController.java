package com.haoyu.wsts.userinfo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseController;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.login.Loginer;
import com.haoyu.sip.user.entity.UserInfo;
import com.haoyu.sip.user.service.IUserInfoService;
import com.haoyu.wsts.shiro.entity.ShiroUser;
import com.haoyu.wsts.utils.TemplateUtils;
@Controller
@RequestMapping("**/userInfo")
public class UserInfoController extends AbstractBaseController{
	@Resource
	private IUserInfoService userInfoService;
	
	private String getLogicViewNamePerfic(){
		return TemplateUtils.getTemplatePath("/userInfo/");
	}
	
	@RequestMapping(value="entities",method=RequestMethod.GET)
	@ResponseBody
	public List<UserInfo> listEntity(SearchParam searchParam){
		return userInfoService.selectUserInfoFromBaseUserView(searchParam.getParamMap(),null);
	}
	
	@RequestMapping(value="choose",method=RequestMethod.GET)
	public String ChooseUser(){
		return getLogicViewNamePerfic() + "choose_user";
	}
	
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(UserInfo user,Model model){	
		return getLogicViewNamePerfic() + "edit_user";
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT)
	@ResponseBody
	public Response update(UserInfo user){
		Response response = userInfoService.updateUser(user);
		if (response.isSuccess()) {
			Loginer shiroUser = (Loginer) request.getSession().getAttribute("loginer");
			if (StringUtils.isNotEmpty(user.getRealName())) {
				shiroUser.setRealName(user.getRealName());
			}
			if (StringUtils.isNotEmpty(user.getAvatar())) {
				shiroUser.getAttributes().put("avatar", user.getAvatar());
			}
		}
		return response;
	}

}
