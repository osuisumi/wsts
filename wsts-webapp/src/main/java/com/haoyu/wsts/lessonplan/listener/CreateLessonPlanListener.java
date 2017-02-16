package com.haoyu.wsts.lessonplan.listener;

import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.aip.lessonplan.entity.LessonPlan;
import com.haoyu.aip.lessonplan.event.CreateLessonPlanEvent;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.utils.FileRelationType;

@Component
public class CreateLessonPlanListener implements ApplicationListener<CreateLessonPlanEvent>{
	@Resource
	private IFileResourceBizService fileResourceBizService;

	@Override
	public void onApplicationEvent(CreateLessonPlanEvent event) {
		LessonPlan lessonPlan = (LessonPlan) event.getSource();
		fileResourceBizService.initFileResourceCreate(lessonPlan.getLessonPlanRelations().get(0).getId(), FileRelationType.LESSON_PLAN_RELATION);
	}

}
