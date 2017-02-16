package com.haoyu.wsts.workshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.entity.TimePeriod;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.utils.Collections3;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.workshop.dao.IWorkshopRelationDao;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.entity.WorkshopRelation;
import com.haoyu.wsts.workshop.service.IWorkshopRelationService;
import com.haoyu.wsts.workshop.service.IWorkshopService;

@Service
public class WorkshopRelationService implements IWorkshopRelationService {
	@Resource
	private IWorkshopRelationDao workshopRelationDao;
	@Resource
	private IWorkshopService workshopService;

	@Override
	public Response createWorkshopRelation(WorkshopRelation workshopRelation) {
		if (workshopRelation == null || StringUtils.isEmpty(workshopRelation.getWorkshopId())) {
			return Response.failInstance().responseMsg("create workshopRelation fail,workshopRelation or workshopId is null");
		}
		if (StringUtils.isEmpty(workshopRelation.getId())) {
			workshopRelation.setId(Identities.uuid2());
		}
		workshopRelation.setDefaultValue();
		return workshopRelationDao.insertWorkshopRelation(workshopRelation) > 0 ? Response.successInstance() : Response.failInstance();
	}

	@Override
	public Response updateWorkshopRelation(WorkshopRelation workshopRelation) {
		if (workshopRelation == null) {
			return Response.failInstance().responseMsg("update fail,workshopRelation is null");
		}
		workshopRelation.setUpdateValue();
		return workshopRelationDao.updateWorkshopRelation(workshopRelation) > 0 ? Response.successInstance() : Response.failInstance();
	}

	@Override
	public Response deleteWorkshopRelation(WorkshopRelation workshopRelation) {
		workshopRelation.setUpdateValue();
		return workshopRelationDao.deleteWorkshopRelationByLogic(workshopRelation) > 0 ? Response.successInstance() : Response.failInstance();
	}

	@Override
	public WorkshopRelation findWorkshopRelationById(String id) {
		return workshopRelationDao.selectWorkshopRelationById(id);
	}

	@Override
	public List<WorkshopRelation> findWorkshopRelations(WorkshopRelation workshopRelation, PageBounds pageBounds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkshopRelation> findWorkshopRelations(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopRelationDao.findAll(parameter, pageBounds);
	}

	@Override
	public Response updateWorkshopRelationByWorkshopId(WorkshopRelation workshopRelation) {
		return workshopRelationDao.updateWorkshopRelationByWorkshopId(workshopRelation) > 0 ? Response.successInstance() : Response.failInstance();
	}

	/*
	 * relationId:培训id
	 * workshopIds:传入的模板ids
	 */
	@Override
	public Response updateWorkshopRelations(String relationId, List<String> workshopIds,TimePeriod timePeriod) {
		Response response = Response.failInstance();
		// 查询该培训之前已经配置的模板
		// 对比本次传入待配置的模板，找出新增的和删除的
		// 复制新增加的，配置给培训
		// 删除的   删除掉sourceId 和relationid匹配的
		
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("relationId",relationId);
		List<Workshop> hasWorkshops = workshopService.findWorkshops(parameter, null);
		List<Workshop> hasTemplate = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(hasWorkshops)){
			List<String> hasTemplateIds = Collections3.extractToList(hasWorkshops, "sourceId");
			parameter.clear();
			parameter.put("ids", hasTemplateIds);
			hasTemplate = workshopService.findWorkshops(parameter, null);
		}
		
		
		List<String> prepareAddIds = Lists.newArrayList(workshopIds);//待添加的模板
		List<String> prepareRemoveIds = Lists.newArrayList();//待移除的模板
		if(CollectionUtils.isNotEmpty(hasTemplate)){
			for(Workshop w:hasTemplate){
				if(prepareAddIds.contains(w.getId())){
					prepareAddIds.remove(w.getId());
				}else{
					prepareRemoveIds.add(w.getId());
				}
			}
		}
		
		//处理待添加的模板
		if(CollectionUtils.isNotEmpty(prepareAddIds)){
			parameter.clear();
			parameter.put("isTemplate", "Y");
			List<Workshop> modelWorkshops = workshopService.findWorkshops(parameter, null);
			Map<String, Workshop> modelWorkshopsMap = Collections3.extractToMap(modelWorkshops, "id", null);
				for(String prepareAddId:prepareAddIds){
					if (modelWorkshopsMap.containsKey(prepareAddId)) {
					Workshop workshop = modelWorkshopsMap.get(prepareAddId);
					response = workshopService.createExtendWorkshop(workshop,relationId,timePeriod);
					}
				}
		}
		
		//处理待删除的模板
		if(CollectionUtils.isNotEmpty(prepareRemoveIds)){
			List<String> wids = Lists.newArrayList();
			for(Workshop w:hasWorkshops){
				if(prepareRemoveIds.contains(w.getSourceId())){
					wids.add(w.getId());
				}
			}
			Workshop workshop = new Workshop();
			String ids = "";
			for(String id:wids){
				if(StringUtils.isEmpty(ids)){
					ids = id;
				}else{
					ids = ids + "," + id;
				}
			}
			workshop.setId(ids);
			workshopService.deleteWorkshop(workshop);
		}
		return Response.successInstance();

	}

}
