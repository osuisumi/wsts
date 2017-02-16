package com.haoyu.wsts.workshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.sip.utils.Identities;
import com.haoyu.wsts.workshop.dao.IWorkshopUserResultDao;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;

@Service
public class WorkshopUserResultService implements IWorkshopUserResultService{
	@Resource
	private IWorkshopUserResultDao workshopUserResultDao;
	@Resource
	private IPointRecordService pointRecordService;
	
	@Override
	public Response createWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		if(StringUtils.isEmpty(workshopUserResult.getId())){
			workshopUserResult.setId(Identities.uuid2());
		}
		workshopUserResult.setDefaultValue();
		return workshopUserResultDao.insertWorkshopUserResult(workshopUserResult)>0?Response.successInstance():Response.failInstance();
	}

	@Override
	@CacheEvict(value="userCenterIndexPage",allEntries=true)
	public Response updateWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		workshopUserResult.setUpdateValue();
		return workshopUserResultDao.updateWorkshopUserResult(workshopUserResult)>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public Response deleteWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		workshopUserResult.setUpdateValue();
		return workshopUserResultDao.deleteWorkshopUserResultByLogic(workshopUserResult)>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public WorkshopUserResult findWorkshopUserResultById(String id) {
		return workshopUserResultDao.selectWorkshopUserResultById(id);
	}

	@Override
	public List<WorkshopUserResult> findWorkshopUserResults(WorkshopUserResult workshopUserResult, PageBounds pageBounds) {
		return null;
	}

	@Override
	public List<WorkshopUserResult> findWorkshopUserResults(Map<String, Object> parameter, PageBounds pageBounds) {
		return workshopUserResultDao.findAll(parameter, pageBounds);
	}

	@Override
	@CacheEvict(value="userCenterIndexPage",key="#userId")
	public Response updatePoint(String relationId,String userId) {
		Float sumPoint = pointRecordService.findUserPoint(userId, relationId,"wsts");
		WorkshopUserResult workshopUserResult = new WorkshopUserResult(DigestUtils.md5Hex(relationId+userId));
		workshopUserResult.setPoint(sumPoint);
		return this.updateWorkshopUserResult(workshopUserResult);
	}

	@Override
	public Response batchUpdateWorkshopResult(List<String> workshopUserIds, String workshopResult) {
		return this.workshopUserResultDao.batchUpdateWorkshopResult(workshopUserIds, workshopResult)>0?Response.successInstance():Response.failInstance();
	}

	@Override
	public Response deleteWorkshopUserResultByWorkshopUserId(String workshopUserId) {
		if(StringUtils.isNotEmpty(workshopUserId)){
			List<String> workshopUserIds = Lists.newArrayList();
			workshopUserIds.add(workshopUserId);
			return deleteWorkshopUserResultByWorkshopUserIds(workshopUserIds);
		}
		return Response.failInstance();
	}

	@Override
	public Response deleteWorkshopUserResultByWorkshopUserIds(List<String> workshopUserIds) {
		return this.workshopUserResultDao.deleteByWorkshopUserIds(workshopUserIds)>0?Response.successInstance():Response.failInstance();
	}
	

}
