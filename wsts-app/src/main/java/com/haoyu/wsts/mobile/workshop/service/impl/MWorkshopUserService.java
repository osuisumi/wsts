package com.haoyu.wsts.mobile.workshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.excel.utils.StringUtils;
import com.haoyu.wsts.mobile.utils.MEntityUtils;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshopUser;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopUserService;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
import com.haoyu.wsts.workshop.utils.WorkshopResultType;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;


@Service
public class MWorkshopUserService implements IMWorkshopUserService{
	@Resource
	private IWorkshopUserService workshopUserService;
	@Resource
	private IWorkshopService workshopService;

	@Override
	public Response listExcellents(String wid,PageBounds pageBounds) {
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("role", WorkshopUserRole.STUDENT);
		parameter.put("workshopId", wid);
		parameter.put("workshopResult", WorkshopResultType.EXCELLENT);
		Workshop workshop = workshopService.findWorkshopById(wid);
		parameter.put("minPoint", workshop.getQualifiedPoint() == null?0:workshop.getQualifiedPoint());
		
		List<WorkshopUser> excellendUsers = workshopUserService.findWorkshopUsers(parameter, pageBounds);
		
		List<MWorkshopUser> mWorkshopUsers = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(excellendUsers)){
			for(WorkshopUser wu:excellendUsers){
				MWorkshopUser mwu = MEntityUtils.getMWorkshopUser(wu);
				mWorkshopUsers.add(mwu);
			}
		}
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("mWorkshopUsers", mWorkshopUsers);
		if(excellendUsers instanceof PageList){
			PageList pageList = (PageList) excellendUsers;
			result.put("paginator", pageList.getPaginator());
		}else{
			result.put("paginator", null);
		}
		return Response.successInstance().responseData(result);
	}

	@Override
	public Response listStudent(String wid, PageBounds pageBounds) {
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("workshopId", wid);
		parameter.put("role", WorkshopUserRole.STUDENT);
		List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(parameter, pageBounds);
		List<MWorkshopUser> students = MEntityUtils.getMWorkshopUserArray(workshopUsers);
		
		
		Map<String,Object> result = Maps.newHashMap();
		if(workshopUsers instanceof PageList){
			PageList pageList = (PageList) workshopUsers;
			result.put("paginator",pageList.getPaginator());
		}
		result.put("workshopUsers", students);
		return Response.successInstance().responseData(result);
	}

	@Override
	public Response listMember(String wid, PageBounds pageBounds,String realName) {
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("workshopId", wid);
		parameter.put("role", WorkshopUserRole.MEMBER);
		if(StringUtils.isNotEmpty(realName)){
			parameter.put("realName", realName);
		}
		List<WorkshopUser> workshopUsers = workshopUserService.findWorkshopUsers(parameter, pageBounds);
		List<MWorkshopUser> students = MEntityUtils.getMWorkshopUserArray(workshopUsers);
		Map<String,Object> result = Maps.newHashMap();
		if(workshopUsers instanceof PageList){
			PageList pageList = (PageList) workshopUsers;
			result.put("paginator",pageList.getPaginator());
		}
		result.put("workshopUsers", students);
		return Response.successInstance().responseData(result);
	}

}
