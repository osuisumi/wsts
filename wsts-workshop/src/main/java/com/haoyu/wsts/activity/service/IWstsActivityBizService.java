package com.haoyu.wsts.activity.service;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.discussion.entity.DiscussionPost;
import com.haoyu.sip.core.service.Response;
import com.haoyu.wsts.activity.web.ActivityParam;

public interface IWstsActivityBizService {
	
	Response createActivity(ActivityParam activityParam);
	
	Response updateActivity(ActivityParam activityParam);
	
	void doDiscussionActivity(DiscussionPost discussionPost);
	
	Response createExtendActivity(Activity activity, String sectionId, String workshopId, String origWorkshopId);
	
	Response createSystemActivity(String workshopId);

}
