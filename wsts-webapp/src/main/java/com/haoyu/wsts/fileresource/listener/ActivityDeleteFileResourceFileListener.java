package com.haoyu.wsts.fileresource.listener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityAttributeName;
import com.haoyu.aip.activity.utils.ActivityResultState;
import com.haoyu.aip.lessonplan.entity.LessonPlanRelation;
import com.haoyu.aip.lessonplan.service.ILessonPlanRelationService;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.event.DeleteFileResourceEvent;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.utils.FileRelationType;
import com.haoyu.wsts.utils.WsIdObject;
/*
 * 删除备课文件事件
 */
@Component
@Async
public class ActivityDeleteFileResourceFileListener implements ApplicationListener<DeleteFileResourceEvent>{
	@Resource
	private IFileResourceBizService fileResourceBizService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private IActivityService activityService;
	@Resource
	private ILessonPlanRelationService lessonPlanRelationService;

	@Override
	public void onApplicationEvent(DeleteFileResourceEvent event) {
		String wsId = WsIdObject.getWsIdObject().getWsid();
		FileResource fileResource = (FileResource) event.getSource();
		if(CollectionUtils.isNotEmpty(fileResource.getFileRelations())){
			if(FileRelationType.LESSON_PLAN_RELATION.equals(fileResource.getFileRelations().get(0).getRelation().getType())){
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("relationId", fileResource.getFileRelations().get(0).getRelation().getId());
				parameter.put("creator",fileResource.getCreator().getId());
				int count = fileResourceBizService.getFileResourceFileCount(parameter);
				LessonPlanRelation lessonPlanRelation = lessonPlanRelationService.get(fileResource.getFileRelations().get(0).getRelation().getId());
				Activity activity = activityService.getActivityByEntityId(lessonPlanRelation.getLessonPlan().getId());
				ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), lessonPlanRelation.getId(),fileResource.getCreator().getId());
				//
				Map<String, Object> result = Maps.newHashMap();
				result.put("upload_num", count);
				if(activity.getAttributeMap().containsKey("upload_num")){
					int completeNum = StringUtils.isEmpty(activity.getAttributeMap().get("upload_num").getAttrValue())?0:Integer.valueOf(activity.getAttributeMap().get("upload_num").getAttrValue());
					if(count >= completeNum){
						activityResult.setState(ActivityResultState.COMPLETE);
						activityResult.setScore(BigDecimal.valueOf(100));
						result.put(ActivityAttributeName.COMPLETE_PCT, BigDecimal.valueOf(100));
					}else if(count < completeNum){
						activityResult.setState(ActivityResultState.IN_PROGRESS);
					}
					activityResult.setDetail(new JsonMapper().toJson(result));
					activityResultService.updateActivityResult(activityResult);
				}
			}
		}
		
	}

}
