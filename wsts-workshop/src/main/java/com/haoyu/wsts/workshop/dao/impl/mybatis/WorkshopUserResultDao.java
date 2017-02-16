package com.haoyu.wsts.workshop.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.workshop.dao.IWorkshopUserResultDao;
import com.haoyu.wsts.workshop.entity.WorkshopUserResult;

@Repository
public class WorkshopUserResultDao extends MybatisDao implements IWorkshopUserResultDao{

	@Override
	public WorkshopUserResult selectWorkshopUserResultById(String id) {
		return super.selectByPrimaryKey(id);
	}

	@Override
	public int insertWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		return super.insert("insert", workshopUserResult);
	}

	@Override
	public int updateWorkshopUserResult(WorkshopUserResult workshopUserResult) {
		return super.update("update", workshopUserResult);
	}

	@Override
	public int deleteWorkshopUserResultByLogic(WorkshopUserResult workshopUserResult) {
		return super.deleteByLogic(workshopUserResult);
	}

	@Override
	public int deleteWorkshopUserResultByPhysics(String id) {
		return super.deleteByPhysics(id);
	}

	@Override
	public List<WorkshopUserResult> findAll(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("select", parameter, pageBounds);
	}

	@Override
	public int batchUpdateWorkshopResult(List<String> workshopUserIds, String workshopResult) {
		Map<String,Object> parameter = Maps.newHashMap();
		parameter.put("workshopUserIds", workshopUserIds);
		parameter.put("workshopResult", workshopResult);
		parameter.put("workshopResultCreator", ThreadContext.getUser().getId());
		return super.update("batchUpdateWorkshopResult", parameter);
	}

	@Override
	public int deleteByWorkshopUserIds(List<String> workshopUserIds) {
		return super.delete("deleteByWorkshopUserIds", workshopUserIds);
	}

}
