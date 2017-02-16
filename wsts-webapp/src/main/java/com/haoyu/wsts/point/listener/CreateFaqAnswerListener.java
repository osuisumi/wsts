package com.haoyu.wsts.point.listener;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.tip.faq.entity.FaqAnswer;
import com.haoyu.tip.faq.event.CreateFaqAnswerEvent;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;

@Component
public class CreateFaqAnswerListener implements ApplicationListener<CreateFaqAnswerEvent>{
	@Resource
	private IWorkshopUserResultService workshopUserResultService;
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;

	@Override
	public void onApplicationEvent(CreateFaqAnswerEvent event) {
		String workshopId = WsIdObject.getWsIdObject().getWsid();
		Subject subject = SecurityUtils.getSubject();
		if(StringUtils.isNotEmpty(workshopId)&&subject.hasRole(RoleCodeConstant.STUDENT+"_"+workshopId)){
			if(workshopTimeUtils.isWorkshopOnGoing(workshopId)){
				FaqAnswer faqAnswer = (FaqAnswer) event.getSource();
				PointRecord pointRecord = new PointRecord();
				pointRecord.setPointStrategy(PointStrategy.getMd5IdInstance(PointType.CREATE_FAQ_ANSWER, "wsts"));
				pointRecord.setUserId(ThreadContext.getUser().getId());
				pointRecord.setRelationId(workshopId);
				pointRecord.setEntityId(faqAnswer.getId());
				pointRecordService.createPointRecord(pointRecord);
			}
		}
	}

}
