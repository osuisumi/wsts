package com.haoyu.wsts.point.listener;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.event.UpdateActivityResultEvent;
import com.haoyu.aip.activity.utils.ActivityResultState;
import com.haoyu.aip.activity.utils.ActivityType;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;

@Component
@Async
public class CompleteActivityListener implements ApplicationListener<UpdateActivityResultEvent> {
	
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;

	@Override
	public void onApplicationEvent(UpdateActivityResultEvent event) {
		String wsid = WsIdObject.getWsIdObject().getWsid();
		Subject subject = SecurityUtils.getSubject();
		if (StringUtils.isNotEmpty(wsid) && subject.hasRole(RoleCodeConstant.STUDENT + "_" + wsid)) {
			ActivityResult activityResult = (ActivityResult) event.getSource();
			String type = null;
			if (activityResult.getActivity() != null && ActivityType.SURVEY.equals(activityResult.getActivity().getType())) {
				type = PointType.SURVEY_COMPLETE;
			}else{
				type = PointType.ACTIVITY_COMPLETE;
			}
			if (ActivityResultState.COMPLETE.equals(activityResult.getState())) {
				if(canCreatePointRecord(type, ThreadContext.getUser().getId(), wsid, activityResult.getActivity().getId(), "plus")){
					if(workshopTimeUtils.isWorkshopOnGoing(wsid)){
						PointRecord pointRecord = new PointRecord();
						pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(type, "wsts"));
						pointRecord.setUserId(ThreadContext.getUser().getId());
						pointRecord.setRelationId(wsid);
						pointRecord.setEntityId(activityResult.getActivity().getId());
						pointRecordService.createPointRecord(pointRecord);
					}
				}
			}else{
				if(canCreatePointRecord(type, activityResult.getCreator().getId(), wsid, activityResult.getActivity().getId(), "minus")){
					if(workshopTimeUtils.isWorkshopOnGoing(wsid)){
						PointRecord pointRecord = new PointRecord();
						pointRecord.setDetail(activityResult.getActivity().getTitle());
						pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.ACTIVITY_BECOME_IN_PROGRESS, "wsts"));
						pointRecord.setUserId(activityResult.getCreator().getId());
						pointRecord.setRelationId(wsid);
						pointRecord.setEntityId(activityResult.getActivity().getId());
						pointRecordService.createPointRecord(pointRecord);
					}
				}
			}
		}
	}
	
	private boolean canCreatePointRecord(String type,String userId,String relationId,String entityId,String opration){
		boolean result = false;
		if(type.equals(PointType.SURVEY_COMPLETE)){
			result = true;
		}else{
			Map<String,Object> parameter = Maps.newHashMap();
			parameter.put("pointStrategyId",PointStrategy.getMd5IdInstance(PointType.ACTIVITY_COMPLETE, "wsts").getId());
			parameter.put("userId",userId);
			parameter.put("relationId", relationId);
			parameter.put("entityId", entityId);
			List<PointRecord> plusList = pointRecordService.findPointRecords(parameter, null);
			
			
			parameter.put("pointStrategyId",PointStrategy.getMd5IdInstance(PointType.ACTIVITY_BECOME_IN_PROGRESS, "wsts").getId());
			List<PointRecord> minusList =  pointRecordService.findPointRecords(parameter, null);
			
			int plusCount = plusList == null?0:plusList.size();
			int minusCount = minusList == null?0:minusList.size();
			if("plus".equals(opration)){
				return plusCount == minusCount;
			}else if("minus".equals(opration)){
				return plusCount - minusCount >=1;
			}
		}
		return result;
	}
	

}
