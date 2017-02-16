package com.haoyu.wsts.shiro.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.google.common.collect.Maps;
import com.haoyu.sip.auth.realm.IAuthRealmHandler;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
import com.haoyu.wsts.workshop.utils.WorkshopUserState;

public class WorkshopUserTypeHandler implements IAuthRealmHandler{
	@Resource
	private IWorkshopUserService workshopUserService;

	@Override
	public void addAuthorize(SimpleAuthorizationInfo info, PrincipalCollection principals) {
		Subject subject = SecurityUtils.getSubject();
		List<Object> listPrincipals = subject.getPrincipals().asList();
		Map<String, String> attributes = (Map<String, String>) listPrincipals.get(1);
		String userId = attributes.get("id");
		
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("userId", userId);
		List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(parameter,null);
		if(CollectionUtils.isNotEmpty(workshopUsers)){
			for(WorkshopUser wu : workshopUsers){
				if(WorkshopUserState.PASS.equals(wu.getState())){
					info.addRole(wu.getRole() + "_" + wu.getWorkshopId());
				}
			}
		}
	}

}
