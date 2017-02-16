package com.haoyu.wsts.workshop.dao.impl.mybatis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.jdbc.MybatisDao;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.workshop.dao.IWorkshopSectionDao;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
@Repository
public class WorkshopSectionDao extends MybatisDao implements IWorkshopSectionDao{
	
	private final static Logger  logger   = LoggerFactory.getLogger(WorkshopSectionDao.class);
	
	@Override
	public WorkshopSection selectWorkshopSectionById(String id) {
		return super.selectByPrimaryKey(id);
	}

	@Override
	public int insertWorkshopSection(WorkshopSection workshopSection) {
		return super.insert("insert",workshopSection);
	}

	@Override
	public int updateWorkshopSection(WorkshopSection workshopSection) {
		logger.info("update workshopSection" + "======userId:"+ThreadContext.getUser().getId() + "======sectionId:" + workshopSection.getId() + "=====title:"+workshopSection.getTitle());
		return super.update("update", workshopSection);
	}

	@Override
	public int deleteWorkshopSectionByLogic(WorkshopSection workshopSection) {
		return super.deleteByLogic(workshopSection);
	}

	@Override
	public int deleteWorkshopSectionByPhysics(String id) {
		return super.deleteByPhysics(id);
	}

	@Override
	public List<WorkshopSection> findAll(Map<String, Object> parameter, PageBounds pageBounds) {
		return super.selectList("select", parameter, pageBounds);
	}

}
