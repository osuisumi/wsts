package com.haoyu.wsts.workshop.dao.impl.mybatis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.wsts.workshop.dao.IWorkshopUserDao;
import com.haoyu.wsts.workshop.entity.WorkshopUser;
@Repository
public class WorkshopUserDao extends MybatisDao implements IWorkshopUserDao{

	@Override
	public WorkshopUser selectWorkshopUserById(String id) {
		return super.selectByPrimaryKey(id);
	}

	@Override
	public int insertWorkshopUser(WorkshopUser workshopUser) {
		return super.insert(workshopUser);
	}

	@Override
	public int updateWorkshopUser(WorkshopUser workshopUser) {
		return super.update(workshopUser);
	}

	@Override
	public int deleteWorkshopUserByLogic(WorkshopUser workshopUser) {
		return super.deleteByLogic(workshopUser);
	}

	@Override
	public int deleteWorkshopUserByPhysics(String id) {
		return super.deleteByPhysics(id);
	}

	@Override
	public List<WorkshopUser> findAll(Map<String, Object> parameter, PageBounds pageBounds) {
		if(parameter.containsKey("states") && parameter.get("states") !=null && parameter.get("states") instanceof String){
			String states = String.valueOf(parameter.get("states"));
			List<String> list = Lists.newArrayList();
			list.addAll(Arrays.asList(states.split(",")));
			parameter.put("states", list);
		}
		return super.selectList("select", parameter, pageBounds);
	}

	@Override
	public int count(Map<String, Object> parameter) {
		return super.selectOne("count", parameter);
	}

	@Override
	public int batchDelete(List<String> ids) {
		return super.delete("batchDelete",ids);
	}

	@Override
	public List<WorkshopUser> findAllWithActionInfo(Map<String, Object> parameter, PageBounds pageBounds) {
		if(parameter.containsKey("states") && parameter.get("states") !=null && parameter.get("states") instanceof String){
			String states = String.valueOf(parameter.get("states"));
			List<String> list = Lists.newArrayList();
			list.addAll(Arrays.asList(states.split(",")));
			parameter.put("states", list);
		}
		return super.selectList("selectWithActionInfo", parameter, pageBounds);
	}

	@Override
	public WorkshopUser selectWorkshopUserWithActionInfoById(String id) {
		return super.selectOne("selectWithActionInfoByPrimaryKey", id);
	}

}
