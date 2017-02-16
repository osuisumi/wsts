package com.haoyu.wsts.activity.listener;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

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
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.evaluate.event.SubmitEvaluateSubmissionEvent;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

@Component
public class ActivityCreateLcecEvaluateSubmissionListener implements ApplicationListener<SubmitEvaluateSubmissionEvent>{
	@Resource
	private IActivityService activityService;
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IActivityResultService activityResultService;
	
	@Override
	public void onApplicationEvent(SubmitEvaluateSubmissionEvent event) {
		Map<String,Object> source = (Map<String, Object>) event.getSource();
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(source.containsKey("relationId")){
			Subject currentUser = SecurityUtils.getSubject();
			if(currentUser.hasRole(RoleCodeConstant.STUDENT+"_" + WsIdObject.getWsIdObject().getWsid())){
				String relationId = source.get("relationId").toString();
				Activity activity = activityService.getActivityByEntityId(relationId);
				ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), wsid);
				activityResult.setState(ActivityResultState.COMPLETE);
				activityResult.setScore(BigDecimal.valueOf(100));
				Map<String, Object> result = Maps.newHashMap();
				result.put(ActivityAttributeName.COMPLETE_PCT, BigDecimal.valueOf(100));
				activityResult.setDetail(new JsonMapper().toJson(result));
				activityResult.setActivity(activity);
				activityResultService.updateActivityResult(activityResult);
			}
		}
	}

}
