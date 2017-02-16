package com.haoyu.wsts.activity.listener;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.aip.discussion.entity.DiscussionPost;
import com.haoyu.aip.discussion.event.DeleteDiscussionPostEvent;
import com.haoyu.wsts.activity.service.IWstsActivityBizService;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

@Component
public class ActivityDeleteDiscussionPostListener implements ApplicationListener<DeleteDiscussionPostEvent>{

	@Resource
	private IWstsActivityBizService wstsActivityBizService;
	
	@Override
	public void onApplicationEvent(DeleteDiscussionPostEvent event) {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole(RoleCodeConstant.STUDENT+"_" + WsIdObject.getWsIdObject().getWsid())){
			DiscussionPost discussionPost = (DiscussionPost) event.getSource();
			wstsActivityBizService.doDiscussionActivity(discussionPost);
		}
	}
}
