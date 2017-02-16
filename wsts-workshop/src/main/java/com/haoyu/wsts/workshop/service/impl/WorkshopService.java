package com.haoyu.wsts.workshop.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.file.entity.FileInfo;
import com.haoyu.sip.file.entity.FileRelation;
import com.haoyu.sip.file.service.IFileRelationService;
import com.haoyu.sip.file.service.IFileService;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.workshop.dao.IWorkshopDao;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopRelation;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
import com.haoyu.wsts.workshop.event.ChangeWorkshopEvent;
import com.haoyu.wsts.workshop.event.UpdateWorkshopEvent;
import com.haoyu.wsts.workshop.service.IWorkshopRelationService;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserService;
import com.haoyu.wsts.workshop.utils.WorkshopState;
import com.haoyu.wsts.workshop.utils.WorkshopUserRole;

@Service
public class WorkshopService implements IWorkshopService {
	@Resource
	private IWorkshopDao workshopDao;
	@Resource
	private IWorkshopRelationService workshopRelationService;
	@Resource
	private IWorkshopUserService workshopUserService;
	@Resource
	private IFileService fileService;
	@Resource
	private IWorkshopSectionService workshopSectionService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IWstsActivityBizService wstsActivityBizService;
	@Resource
	private IFileRelationService fileRelationService;
	@Resource
	private ApplicationContext applicationContext;
	
	@Override
	public Response createWorkshop(Workshop workshop) {
		if (workshop == null) {
			return Response.failInstance().responseMsg("create workshop fail! workshop is null");
		}
		if (StringUtils.isEmpty(workshop.getId())) {
			workshop.setId(Identities.uuid2());
		}
		workshop.setDefaultValue();
		if (workshop.getImage() != null) {
			Response response = fileService.createFile(workshop.getImage(), workshop.getId(), "workshop_image");
			if (response.isSuccess()) {
				workshop.setImageUrl(workshop.getImage().getUrl());
			}
		}
		if(CollectionUtils.isNotEmpty(workshop.getSolutions())){
			fileService.createFileList(workshop.getSolutions(), workshop.getId(), "workshop_solution");
		}
		int count = workshopDao.insertWorkshop(workshop);
		if (count > 0) {
			if (workshop.getWorkshopRelation() != null) {
				workshop.getWorkshopRelation().setWorkshopId(workshop.getId());
				workshopRelationService.createWorkshopRelation(workshop.getWorkshopRelation());
			}

			if (CollectionUtils.isNotEmpty(workshop.getMasters())) {
				for (WorkshopUser ma : workshop.getMasters()) {
					ma.setWorkshopId(workshop.getId());
					ma.setRole(WorkshopUserRole.MASSTER);
					workshopUserService.createWorkshopUser(ma);
				}
			}

			if (CollectionUtils.isNotEmpty(workshop.getMembers())) {
				for (WorkshopUser mem : workshop.getMembers()) {
					mem.setWorkshopId(workshop.getId());
					mem.setRole(WorkshopUserRole.MEMBER);
					workshopUserService.createWorkshopUser(mem);
				}
			}
			return Response.successInstance();
		}
		return Response.failInstance();
	}

	public Response updateWorkshop(Workshop workshop) {
		if (workshop == null) {
			return Response.failInstance().responseMsg("update workshop fail,workshop is null");
		}
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("ids", Arrays.asList(workshop.getId().split(",")));
		workshop.setUpdateValue();
		parameter.put("entity", workshop);
		int count = 0;
		if(workshop.getImage() != null){
			Response response = fileService.createFile(workshop.getImage(), workshop.getId(), "workshop_image");
			if (response.isSuccess()) {
				workshop.setImageUrl(workshop.getImage().getUrl());
			}
		}
		count = workshopDao.updateWorkshop(parameter);
		if (count > 0) {
			if (workshop.getWorkshopRelation() != null) {
				WorkshopRelation wr = workshop.getWorkshopRelation();
				wr.setWorkshopId(workshop.getId());
				workshopRelationService.updateWorkshopRelationByWorkshopId(wr);
			}
			applicationContext.publishEvent(new UpdateWorkshopEvent(workshop));
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshop", workshop);
			applicationContext.publishEvent(new ChangeWorkshopEvent(eventSource));
		}
		if(CollectionUtils.isNotEmpty(workshop.getSolutions())){
			fileService.updateFileList(workshop.getSolutions(), fileService.listFileInfoByRelation(new Relation(workshop.getId(), "workshop_solution")), workshop.getId(),"workshop_solution");
		}
		return count > 0 ? Response.successInstance() : Response.failInstance();
	}

	public Response deleteWorkshop(Workshop workshop) {
		if(StringUtils.isEmpty(workshop.getId())){
			return Response.failInstance().responseMsg("id is empty");
		}
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("ids", Arrays.asList(workshop.getId().split(",")));
		workshop.setUpdateValue();
		parameter.put("entity",workshop);
		int count = workshopDao.deleteWorkshopByLogic(parameter);
		if(count>0){
			Map<String,Object> eventSource = Maps.newHashMap();
			eventSource.put("workshop", workshop);
			applicationContext.publishEvent(new ChangeWorkshopEvent(eventSource));
		}
		return  count> 0 ? Response.successInstance() : Response.failInstance();
	}

	@Override
	public Workshop findWorkshopById(String id) {
		return workshopDao.selectWorkshopById(id);
	}

	@Override
	public List<Workshop> findWorkshops(Workshop workshop, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Workshop> findWorkshops(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopDao.findAll(parameter, pageBounds);
	}

	@Override
	public Workshop findWorkshopById(String id, boolean getUser) {
		return null;
	}

	public List<Workshop> findWorkshopsWithStat(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopDao.findAllWithStat(parameter, pageBounds);
	}

	@Override
	public Workshop findWorkshopByIdWithStat(Map<String, Object> parameter) {
		if (parameter.containsKey("workshopId") && StringUtils.isNotEmpty(parameter.get("workshopId").toString())) {
			List<Workshop> workshops = findWorkshopsWithStat(parameter, null);
			if (CollectionUtils.isNotEmpty(workshops)) {
				return workshops.get(0);
			}
		}
		return null;
	}

	@Override
	public Response createExtendWorkshop(Workshop workshop,String relationId,TimePeriod timePeriod) {
		String extendId = workshop.getId();
		Workshop source = findWorkshopById(extendId);
		String title = workshop.getTitle();
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(workshop, source);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringUtils.isNotEmpty(title)){
			workshop.setTitle(title);
		}
		if(StringUtils.isNotEmpty(source.getSourceId())){
			workshop.setSourceId(source.getSourceId());
		}else{
			workshop.setSourceId(extendId);
		}
		workshop.setId(null);
		workshop.setState(WorkshopState.PUBLISHED);
		workshop.setIsTemplate("N");
		workshop.getWorkshopRelation().setId(Identities.uuid2());
		if(StringUtils.isNotEmpty(relationId)){
			workshop.getWorkshopRelation().setRelation(new Relation(relationId));
		}
		if(timePeriod != null){
			workshop.setTimePeriod(timePeriod);	
		}
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(Integer.MAX_VALUE);
		pageBounds.setOrders(Order.formString("SORT_NUM,CREATE_TIME"));
		// 创建工作坊
		Response response = createWorkshop(workshop);
		//复制工作坊的方案
		List<FileInfo> fileInfos =fileService.listFileInfoByRelation(new Relation(extendId, "workshop_solution"));
		if (Collections3.isNotEmpty(fileInfos)) {
			for (FileInfo fileInfo : fileInfos) {
				FileRelation fileRelation = new FileRelation();
				fileRelation.setFileId(fileInfo.getId());
				fileRelation.setRelation(new Relation(workshop.getId()));
				fileRelation.setType("workshop_solution");
				fileRelationService.create(fileRelation);
			}
		}
		if (response.isSuccess()) {
			Map<String, Object> param = Maps.newHashMap();
			param.put("workshopId", extendId);
			List<WorkshopSection> workshopSections = workshopSectionService.findWorkshopSections(param, true, pageBounds);
			if (Collections3.isNotEmpty(workshopSections)) {
				for (WorkshopSection section : workshopSections) {
					String oldSectionId = section.getId();
					section.setId(Identities.uuid2());
					section.setWorkshopId(workshop.getId());
					// 复制阶段
					response = workshopSectionService.createWorkshopSection(section);
					if(response.isSuccess()){
						param = Maps.newHashMap();
						param.put("relationId", oldSectionId);
						pageBounds.setOrders(Order.formString("SORT_NO,CREATE_TIME"));
						List<Activity> activities = activityService.listActivity(param, true, pageBounds);
						if (Collections3.isNotEmpty(activities)) {
							for (Activity activity : activities) {
								wstsActivityBizService.createExtendActivity(activity, section.getId(), workshop.getId(), source.getId());
							}
						}
						
					}
				}
			}
		}
		return response;
	}

	@Override
	public int count(Map<String, Object> parameter) {
		return workshopDao.count(parameter);
	}

	@Override
	public List<Workshop> selectInterestedWorkshop(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopDao.selectInterestedWorkshop(parameter, pageBounds);
	}

	@Override
	public Map<String, Workshop> workshopIdKeyMap(Map<String, Object> parameter) {
		return workshopDao.workshopIdKeyMap(parameter);
	}
	

}
