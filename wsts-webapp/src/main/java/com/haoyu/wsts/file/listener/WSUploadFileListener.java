package com.haoyu.wsts.file.listener;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityAttributeName;
import com.haoyu.aip.activity.utils.ActivityResultState;
import com.haoyu.aip.lessonplan.entity.LessonPlanRecord;
import com.haoyu.aip.lessonplan.entity.LessonPlanRelation;
import com.haoyu.aip.lessonplan.service.ILessonPlanRelationService;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.file.entity.FileRelation;
import com.haoyu.sip.file.event.UploadFileEvent;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.sip.point.service.IPointStrategyService;
import com.haoyu.wsts.fileresource.service.IFileResourceBizService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.FileRelationType;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;
/*
 * 上传备课文件 上传工作坊资源 事件
 */
@Component
public class WSUploadFileListener implements ApplicationListener<UploadFileEvent>{
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IFileResourceBizService fileResourceBizService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private ILessonPlanRelationService lessonPlanRelationService;
	@Resource
	private IPointStrategyService pointStrategyService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;

	@Override
	public void onApplicationEvent(UploadFileEvent event) {
		FileRelation fileRelation = (FileRelation) event.getSource();
		//判断是不是学员
		String wsid = WsIdObject.getWsIdObject().getWsid();
		Subject subject = SecurityUtils.getSubject();
		if(subject.hasRole(RoleCodeConstant.STUDENT+"_"+wsid)){
			if(FileRelationType.WORKSHOP_RESOURCE.equals(fileRelation.getType())){
				//上传工作坊文件
					//加积分
				if(workshopTimeUtils.isWorkshopOnGoing(wsid)){
					PointRecord pointRecord = new PointRecord();
					pointRecord.setEntityId(fileRelation.getFileId());
					pointRecord.setRelationId(wsid);
					pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.CREATE_WORKSHOP_FILE_RESOURCE_FILE, "wsts"));
					pointRecord.setUserId(ThreadContext.getUser().getId());
					pointRecordService.createPointRecord(pointRecord);
				}
			}else if(FileRelationType.LESSON_PLAN_RELATION.equals(fileRelation.getType())){
				//上传备课文件
					//更新活动状态
				if(fileRelation != null){
					if (FileRelationType.LESSON_PLAN_RELATION.equals(fileRelation.getType())) {
						LessonPlanRelation lessonPlanRelation = lessonPlanRelationService.get(fileRelation.getRelation().getId());
						Map<String, Object> parameter = new HashMap<String, Object>();
						parameter.put("relationId", fileRelation.getRelation().getId());
						parameter.put("creator", ThreadContext.getUser().getId());
						int count = fileResourceBizService.getFileResourceFileCount(parameter);
						Activity activity = activityService.getActivityByEntityId(lessonPlanRelation.getLessonPlan().getId());
						ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), lessonPlanRelation.getRelation().getId());
						//
						Map<String, Object> result = Maps.newHashMap();
						result.put("upload_num", count);
						int completeNum = 0;
						if(activity.getAttributeMap().containsKey("upload_num")){
							completeNum = StringUtils.isEmpty(activity.getAttributeMap().get("upload_num").getAttrValue())?0:Integer.valueOf(activity.getAttributeMap().get("upload_num").getAttrValue());
						}
						if(count >= completeNum){
							activityResult.setState(ActivityResultState.COMPLETE);
							activityResult.setScore(BigDecimal.valueOf(100));
							result.put(ActivityAttributeName.COMPLETE_PCT, BigDecimal.valueOf(100));
						}
						activityResult.setDetail(new JsonMapper().toJson(result));
						activityResultService.updateActivityResult(activityResult);
							
					}
				}
			}
			
		}
		
	}

}
