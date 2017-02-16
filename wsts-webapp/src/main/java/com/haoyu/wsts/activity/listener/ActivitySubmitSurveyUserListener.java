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
import com.haoyu.aip.survey.entity.SurveyUser;
import com.haoyu.aip.survey.event.SubmitSurveyUserEvent;
import com.haoyu.aip.survey.service.ISurveyUserService;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

@Component
public class ActivitySubmitSurveyUserListener implements ApplicationListener<SubmitSurveyUserEvent>{

	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private ISurveyUserService surveyUserService;
	
	@Override
	public void onApplicationEvent(SubmitSurveyUserEvent event) {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole(RoleCodeConstant.STUDENT+"_" + WsIdObject.getWsIdObject().getWsid())){
			SurveyUser surveyUser = (SurveyUser) event.getSource();
			surveyUser = surveyUserService.getSurveyUser(surveyUser.getId());
			String relationId = surveyUser.getRelation().getId();
			String surveyId = surveyUser.getSurvey().getId();
			Activity activity = activityService.getActivityByEntityId(surveyId);
			ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), relationId);
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
