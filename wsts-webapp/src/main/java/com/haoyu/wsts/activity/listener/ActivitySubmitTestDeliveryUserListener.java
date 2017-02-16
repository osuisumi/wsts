package com.haoyu.wsts.activity.listener;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.entity.ActivityResult;
import com.haoyu.aip.activity.service.IActivityResultService;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.aip.activity.utils.ActivityResultState;
import com.haoyu.aip.qti.entity.TestDeliveryUser;
import com.haoyu.aip.qti.event.SubmitTestDeliveryUserEvent;
import com.haoyu.aip.qti.service.ITestDeliveryUserService;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WsIdObject;

@Component
public class ActivitySubmitTestDeliveryUserListener implements ApplicationListener<SubmitTestDeliveryUserEvent>{

	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityResultService activityResultService;
	@Resource
	private ITestDeliveryUserService testDeliveryService;
	
	@Override
	public void onApplicationEvent(SubmitTestDeliveryUserEvent event) {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole(RoleCodeConstant.STUDENT+"_" + WsIdObject.getWsIdObject().getWsid())){
			TestDeliveryUser testDeliveryUser = (TestDeliveryUser) event.getSource();
			String relationId = testDeliveryUser.getTestDelivery().getRelationId();
			String testId = testDeliveryUser.getTestDelivery().getTest().getId();
			Activity activity = activityService.getActivityByEntityId(testId);
			ActivityResult activityResult = activityResultService.createIfNotExists(activity.getId(), relationId);
			activityResult.setState(ActivityResultState.COMPLETE);
			//获取分数百分比
			double score = testDeliveryUser.getTestDelivery().getTest().getScore();
			double sumScore = testDeliveryUser.getSumScore();
			if(score<=0){
				activityResult.setScore(BigDecimal.valueOf(100));
			}else{
				if(sumScore<=0){
					activityResult.setScore(BigDecimal.valueOf(0));
				}else{
					BigDecimal  scorePercent = BigDecimal.valueOf(sumScore).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(score) ,1, BigDecimal.ROUND_HALF_UP);
					if(scorePercent.doubleValue()>100){
						activityResult.setScore(BigDecimal.valueOf(100));
					}else{
						activityResult.setScore(scorePercent);
					}
					
				}
			}
			activityResult.setActivity(activity);
			activityResultService.updateActivityResult(activityResult);
		}
	}
}
