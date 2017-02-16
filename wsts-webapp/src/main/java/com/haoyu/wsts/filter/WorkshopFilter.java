package com.haoyu.wsts.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

public class WorkshopFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 获取wsid
		String uri = ((HttpServletRequest) request).getRequestURI();
		String wsid = this.getId(uri);
		if (wsid != null) {
			WsIdObject.bind(new WsIdObject(wsid));
		}
//		if (StringUtils.isNotEmpty(wsid)) {
//			if (uri.contains("study")) {
//				Subject currentUser = SecurityUtils.getSubject();
//				if (!currentUser.hasRole(WorkshopUserRole.STUDENT+"_"+wsid)) {
//					return false;
//				}else{
//					if(!validateCourseTime()){
//						return false;
//					}
//				}
//			}else if(uri.contains("teach")){
//				Subject currentUser = SecurityUtils.getSubject();
//				if (!currentUser.hasRole(RoleCodeConstant.COURSE_TEACHER+"_"+csaIdObject.getCid())) {
//					return false;
//				}else{
//					if(!validateCourseTime()){
//						return false;
//					}
//				}
//			}
//		}
		return true;
		

//		return true;
	}
	
	private String getId(String uri) {
		for (String roleCode : RoleCodeConstant.getRoleCodeConstants()) {
			if (uri.indexOf(roleCode) >= 0) {
				String id = StringUtils.substringBetween(uri, "/" + roleCode + "_", "/");
				if (StringUtils.isNotEmpty(id)) {
					return id;
				}
			}
		}
		return null;
	}

}
