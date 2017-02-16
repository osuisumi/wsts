package com.haoyu.wsts.point.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.event.CreatePointRecordEvent;
import com.haoyu.wsts.workshop.service.IWorkshopUserResultService;

@Component
public class CreatePointRecordListener implements ApplicationListener<CreatePointRecordEvent>{
	@Resource
	private IWorkshopUserResultService workshopUserResultService;

	@Override
	public void onApplicationEvent(CreatePointRecordEvent event) {
		PointRecord pointRecord = (PointRecord) event.getSource();
		workshopUserResultService.updatePoint(pointRecord.getRelationId(), pointRecord.getUserId());
	}

}
