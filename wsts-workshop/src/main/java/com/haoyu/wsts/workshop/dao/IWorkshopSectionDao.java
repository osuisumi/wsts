package com.haoyu.wsts.workshop.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.wsts.workshop.entity.WorkshopSection;

public interface IWorkshopSectionDao {
	WorkshopSection selectWorkshopSectionById(String id);

	int insertWorkshopSection(WorkshopSection workshopSection);

	int updateWorkshopSection(WorkshopSection workshopSection);

	int deleteWorkshopSectionByLogic(WorkshopSection workshopSection);

	int deleteWorkshopSectionByPhysics(String id);

	List<WorkshopSection> findAll(Map<String, Object> parameter, PageBounds pageBounds);

}
