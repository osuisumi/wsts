package com.haoyu.wsts.workshop.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.sip.auth.realm.AuthenticationCasRealm;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.wsts.workshop.dao.IWorkshopUserDao;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;
import com.haoyu.wsts.workshop.event.ChangeWorkshopUserEvent;
import com.haoyu.wsts.workshop.event.CreateWorkshopUsersEvent;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

@Service
public class WorkshopUserService implements IWorkshopUserService{
	@Resource
	private IWorkshopUserDao workshopUserDao;
	@Resource
	private IWorkshopUserResultService workshopUserResultService;
	@Resource
	private AuthenticationCasRealm authRealm; 
	@Resource
	private ApplicationContext applicationContext;

	public Response createWorkshopUser(WorkshopUser workshopUser) {
		if(StringUtils.isEmpty(workshopUser.getId())){
			workshopUser.setId(WorkshopUser.getId(workshopUser.getWorkshopId(), workshopUser.getUser().getId()));
		}
		workshopUser.setDefaultValue();
		int count = 0;
		try{ 
			count = workshopUserDao.insertWorkshopUser(workshopUser);
		}catch(DuplicateKeyException e){
			WorkshopUser existWu = findWorkshopUserById(workshopUser.getId());
			if(existWu != null){
				//如果状态不是通过，直接更新
				return updateWorkshopUser(workshopUser);
			}
			return Response.failInstance().responseMsg("workshopUser already exist");
		}
		
		if(count>0){
			WorkshopUserResult workshopUserResult = new WorkshopUserResult();
			workshopUserResult.setWorkshopUser(workshopUser);
			workshopUserResultService.createWorkshopUserResult(workshopUserResult);
		}
		if(count>0){
			authRealm.clearUserCacheById(workshopUser.getUser().getId());
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshopUser",workshopUser);
			List<String> userIds = Lists.newArrayList();
			userIds.add(workshopUser.getUser().getId());
			eventSource.put("userIds",userIds);
			applicationContext.publishEvent(new ChangeWorkshopUserEvent(eventSource));
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	public Response updateWorkshopUser(WorkshopUser workshopUser) {
		workshopUser.setUpdateValue();
		int count = workshopUserDao.updateWorkshopUser(workshopUser);
		if(count>0){
			WorkshopUser wu = workshopUser;
			if(!(workshopUser.getUser()!=null && StringUtils.isNotEmpty(workshopUser.getUser().getId()))){
				wu = findWorkshopUserById(workshopUser.getId());
			}
			authRealm.clearUserCacheById(wu.getUser().getId());
			Map<String,Object> eventSource = Maps.newHashMap();
			if(workshopUser.getUser() == null || StringUtils.isEmpty(workshopUser.getUser().getId())){
				workshopUser = findWorkshopUserById(workshopUser.getId());
			}
			eventSource.put("workshopUser",workshopUser);
			List<String> userIds = Lists.newArrayList();
			userIds.add(workshopUser.getUser().getId());
			eventSource.put("userIds",userIds);
			applicationContext.publishEvent(new ChangeWorkshopUserEvent(eventSource));
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	public Response deleteWorkshopUser(WorkshopUser workshopUser) {
		workshopUser.setUpdateValue();
		if(workshopUser.getUser() == null || StringUtils.isEmpty(workshopUser.getUser().getId())){
			workshopUser = findWorkshopUserById(workshopUser.getId());
		}
		int count = workshopUserDao.deleteWorkshopUserByPhysics(workshopUser.getId());
		if(count>0){
			workshopUserResultService.deleteWorkshopUserResultByWorkshopUserId(workshopUser.getId());
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshopUser",workshopUser);
			List<String> userIds = Lists.newArrayList();
			userIds.add(workshopUser.getUser().getId());
			eventSource.put("userIds", userIds);
			applicationContext.publishEvent(new ChangeWorkshopUserEvent(eventSource));
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public WorkshopUser findWorkshopUserById(String id) {
		return workshopUserDao.selectWorkshopUserById(id);
	}

	@Override
	public List<WorkshopUser> findWorkshopUsers(WorkshopUser workshopUser, PageBounds pageBounds) {
		Map<String,Object> parameter = Maps.newHashMap();
		return workshopUserDao.findAll(parameter, pageBounds);
	}

	@Override
	public List<WorkshopUser> findWorkshopUsers(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopUserDao.findAll(parameter, pageBounds);
	}


	@Override
	public int count(Map<String, Object> parameter) {
		return workshopUserDao.count(parameter);
	}


	public Response batchSave(Workshop workshop) {
		if(CollectionUtils.isNotEmpty(workshop.getWorkshopUsers())){
			for(WorkshopUser wu:workshop.getWorkshopUsers()){
				wu.setWorkshopId(workshop.getId());
				createWorkshopUser(wu);
			}
			applicationContext.publishEvent(new CreateWorkshopUsersEvent(workshop));
		}
		return Response.successInstance();
	}

	@Override
	public List<WorkshopUser> findWorkshopUsersWithActionInfo(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopUserDao.findAllWithActionInfo(parameter, pageBounds);
	}

	public Response deleteWorkshopUserPhysics(WorkshopUser workshopUser) {
		if(workshopUser.getUser() == null || StringUtils.isEmpty(workshopUser.getUser().getId())){
			workshopUser = findWorkshopUserById(workshopUser.getId());
		}
		int count = workshopUserDao.deleteWorkshopUserByPhysics(workshopUser.getId());
		if(count>0){
			workshopUserResultService.deleteWorkshopUserResultByWorkshopUserId(workshopUser.getId());
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshopUser",workshopUser);
			List<String> userIds = Lists.newArrayList();
			userIds.add(workshopUser.getUser().getId());
			eventSource.put("userIds", userIds);
			applicationContext.publishEvent(new ChangeWorkshopUserEvent(eventSource));
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public WorkshopUser findWorkshopUserWithActionInfoById(String id) {
		return workshopUserDao.selectWorkshopUserWithActionInfoById(id);
	}

	public Response batchDelete(List<String> workshopUserIds,List<String> userIds) {
		int count = workshopUserDao.batchDelete(workshopUserIds);
		if(count>0){
			workshopUserResultService.deleteWorkshopUserResultByWorkshopUserIds(workshopUserIds);
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshopUserIds",workshopUserIds);
			if(CollectionUtils.isNotEmpty(userIds)){
				eventSource.put("userIds",userIds);
			}
			applicationContext.publishEvent(new ChangeWorkshopUserEvent(eventSource));
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public Map<String, List<WorkshopUser>> getWorkshopUserMap(Map<String,Object> parameter) {
		Map<String,List<WorkshopUser>> result = Maps.newHashMap();
		if(parameter.containsKey("workshopIds")){
			List<String> workshopIds = (List<String>) parameter.get("workshopIds");
			for(String workshopId:workshopIds){
				result.put(workshopId,Lists.newArrayList());
			}
			List<WorkshopUser> workshopUsers = findWorkshopUsers(parameter, null);
			for(WorkshopUser wu:workshopUsers){
				if(result.get(wu.getWorkshopId()) != null){
					result.get(wu.getWorkshopId()).add(wu);
				}
			}
		}
		return result;
	}

	@Override
	public Response updateWorkshopUsers(WorkshopUser workshopUser) {
		String ids = workshopUser.getId();
		if(StringUtils.isNotEmpty(ids)){
			String [] idsArray = ids.split(",");
			for(String id:idsArray){
				workshopUser.setId(id);
				updateWorkshopUser(workshopUser);
			}
			return Response.successInstance();
		}
		return Response.failInstance();
	}


}
