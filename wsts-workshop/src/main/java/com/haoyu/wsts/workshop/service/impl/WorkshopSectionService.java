package com.haoyu.wsts.workshop.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.workshop.dao.IWorkshopSectionDao;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;
@Service
public class WorkshopSectionService implements IWorkshopSectionService{
	
	private final static Logger  logger   = LoggerFactory.getLogger(WorkshopSectionService.class);
	@Resource
	private IWorkshopSectionDao workshopSectionDao;
	@Resource
	private RedisTemplate redisTemplate;
	
	private String cacheKey(String workshopId){
		return PropertiesLoader.get("redis.app.key") + ":workshop:workshopSections:" + workshopId;
	}
	
	private String allKey(){
		return PropertiesLoader.get("redis.app.key") + ":workshop:workshopSections:*";
	}
	
	@Override
	public Response createWorkshopSection(WorkshopSection workshopSection) {
		if(workshopSection == null || StringUtils.isEmpty(workshopSection.getWorkshopId())){
			return Response.failInstance().responseMsg("create fail,workshopSection or workshopId is null ");
		}
		if(StringUtils.isEmpty(workshopSection.getId())){
			workshopSection.setId(Identities.uuid2());
		}
		workshopSection.setDefaultValue();
		int count = workshopSectionDao.insertWorkshopSection(workshopSection);
		Response response = Response.failInstance();
		if(count>0){
			response = Response.successInstance();
			response.setResponseData(workshopSection);
			if(StringUtils.isNotEmpty(workshopSection.getWorkshopId())){
				redisTemplate.delete(cacheKey(workshopSection.getWorkshopId()));
			}else{
				redisTemplate.delete(allKey());
			}
		}
		return response;
	}

	@Override
	public Response updateWorkshopSection(WorkshopSection workshopSection) {
		if(workshopSection == null){
			return Response.failInstance().responseMsg("update fail,workshopSection is null");
		}
		workshopSection.setUpdateValue();
		logger.info("update workshopSection" + "======userId:"+ThreadContext.getUser().getId() + "======sectionId:" + workshopSection.getId() + "=====title:"+workshopSection.getTitle());
		int count = workshopSectionDao.updateWorkshopSection(workshopSection);
		if(StringUtils.isNotEmpty(workshopSection.getWorkshopId())){
			redisTemplate.delete(cacheKey(workshopSection.getWorkshopId()));
		}else{
			redisTemplate.delete(allKey());
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public Response deleteWorkshopSection(WorkshopSection workshopSection) {
		int count = workshopSectionDao.deleteWorkshopSectionByLogic(workshopSection);
		if(count>0){
			if(StringUtils.isNotEmpty(workshopSection.getWorkshopId())){
				redisTemplate.delete(cacheKey(workshopSection.getWorkshopId()));
			}else{
				redisTemplate.delete(allKey());
			}
		}
		return count>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public WorkshopSection findWorkshopSectionById(String id) {
		return workshopSectionDao.selectWorkshopSectionById(id);
	}

	@Override
	public List<WorkshopSection> findWorkshopSections(WorkshopSection workshopSection,boolean isGetChildren, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopSection> findWorkshopSections(Map<String, Object> parameter,boolean isGetChildren, PageBounds pageBounds) {
		List<WorkshopSection> workshopSections = Lists.newArrayList();
		if(parameter.containsKey("workshopId")&&StringUtils.isNotEmpty(parameter.get("workshopId").toString())){
			String workshopId = parameter.get("workshopId").toString();
			String key = cacheKey(workshopId);
			ValueOperations<String, List<WorkshopSection>> valueOper = redisTemplate.opsForValue();
			if(redisTemplate.hasKey(key)){
				workshopSections = valueOper.get(key);
			}else{
				workshopSections = workshopSectionDao.findAll(parameter, pageBounds);
				if(workshopSections!=null&&!workshopSections.isEmpty()){
					valueOper.set(key, workshopSections);
					redisTemplate.expire(key, 10, TimeUnit.DAYS);//缓存十天过期
				}
			}
		}else{
			workshopSections = workshopSectionDao.findAll(parameter, pageBounds);
		}
		Collections.sort(workshopSections);
		return workshopSections;
	}
	
}
