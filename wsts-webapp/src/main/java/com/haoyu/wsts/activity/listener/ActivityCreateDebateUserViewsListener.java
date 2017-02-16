package com.haoyu.wsts.activity.listener;

import java.math.BigDecimal;
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
import com.haoyu.aip.debate.entity.Debate;
import com.haoyu.aip.debate.entity.DebateRelation;
import com.haoyu.aip.debate.entity.DebateUser;
import com.haoyu.aip.debate.entity.DebateUserViews;
import com.haoyu.aip.debate.event.CreateDebateUserViewsEvent;
import com.haoyu.aip.debate.service.IDebateRelationService;
import com.haoyu.aip.debate.service.IDebateService;
import com.haoyu.aip.debate.service.IDebateUserService;
import com.haoyu.aip.debate.service.IDebateUserViewsService;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.debate.dao.IDebateBizDao;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

@Component
public class ActivityCreateDebateUserViewsListener implements ApplicationListener<CreateDebateUserViewsEvent>{
	@Resource
	private IDebateUserViewsService debateUserViewService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IDebateBizDao debateBizDao;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private IDebateService debateService;

	@Override
	public void onApplicationEvent(CreateDebateUserViewsEvent event) {
		String wsId = WsIdObject.getWsIdObject().getWsid();
		Subject subject = SecurityUtils.getSubject();
		if(StringUtils.isNotEmpty(wsId)&&subject.hasRole(RoleCodeConstant.STUDENT+"_"+wsId)){
			DebateUserViews debateUserViews = (DebateUserViews) event.getSource();
			Debate debate = debateBizDao.getDebateByDebateUserId(debateUserViews.getDebateUser().getId());
			debate = debateService.findDebateById(debate.getId());
			Map<String,Object> parameter = Maps.newHashMap();
			parameter.put("userId",ThreadContext.getUser().getId());
			parameter.put("relationId", debate.getDebateRelations().get(0).getRelation().getId());
			int viewsNum = debateUserViewService.getCount(parameter);
			Activity activity = activityService.getActivityByEntityId(debate.getId());
			int completeNum = 0;
			if(activity.getAttributeMap().containsKey("debate_view_num")){
				completeNum = StringUtils.isEmpty(activity.getAttributeMap().get("debate_view_num").getAttrValue())?0:Integer.valueOf(activity.getAttributeMap().get("debate_view_num").getAttrValue());
			}
			//
			ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), debate.getDebateRelations().get(0).getRelation().getId());
			Map<String, Object> result = Maps.newHashMap();
			result.put("debate_view_num", viewsNum);
			if(viewsNum>=completeNum){
				activityResult.setState(ActivityResultState.COMPLETE);
				result.put(ActivityAttributeName.COMPLETE_PCT, BigDecimal.valueOf(100));
				activityResult.setScore(BigDecimal.valueOf(100));
			}
			activityResult.setDetail(new JsonMapper().toJson(result));
			activityResultService.updateActivityResult(activityResult);
		}
	}

}
