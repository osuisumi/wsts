package com.haoyu.wsts.activity.listener;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.haoyu.aip.debate.entity.DebateUserViews;
import com.haoyu.aip.debate.event.DeleteDebateUserViewsEvent;
import com.haoyu.aip.debate.service.IDebateService;
import com.haoyu.aip.debate.service.IDebateUserViewsService;
import com.haoyu.sip.core.mapper.JsonMapper;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.debate.dao.IDebateBizDao;

@Component
public class ActivityDeleteDebateUserViewsListener implements ApplicationListener<DeleteDebateUserViewsEvent>{
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
	public void onApplicationEvent(DeleteDebateUserViewsEvent event) {
		if(event.getSource() instanceof DebateUserViews){
			DebateUserViews debateUserViews = (DebateUserViews) event.getSource();
			if(debateUserViews.getCreator()!=null && StringUtils.isNotEmpty(debateUserViews.getCreator().getId())){
				Debate debate = debateBizDao.getDebateByDebateUserId(debateUserViews.getDebateUser().getId());
				debate = debateService.findDebateById(debate.getId());
				Map<String,Object> parameter = Maps.newHashMap();
				parameter.put("userId",debateUserViews.getCreator().getId());
				parameter.put("relationId", debate.getDebateRelations().get(0).getRelation().getId());
				int viewsNum = debateUserViewService.getCount(parameter);
				Activity activity = activityService.getActivityByEntityId(debate.getId());
				int completeNum = 0;
				if(activity.getAttributeMap().containsKey("debate_view_num")){
					completeNum = StringUtils.isEmpty(activity.getAttributeMap().get("debate_view_num").getAttrValue())?0:Integer.valueOf(activity.getAttributeMap().get("debate_view_num").getAttrValue());
				}
				ActivityResult activityResult = activityResultService.getActivityResult(ActivityResult.getId(activity.getId(), debate.getDebateRelations().get(0).getRelation().getId(), debateUserViews.getCreator().getId()));
				if(activityResult!=null){
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
	}

}
