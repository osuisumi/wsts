package com.haoyu.wsts.utils;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.haoyu.sip.utils.TimeUtils;
import com.haoyu.tip.train.entity.Train;
import com.haoyu.tip.train.service.ITrainService;
import com.haoyu.wsts.workshop.entity.Workshop;
import com.haoyu.wsts.workshop.service.IWorkshopService;

@Component
public class WorkshopTimeUtils {

	@Resource
	private IWorkshopService workshopService;

	@Resource
	private ITrainService trainService;

	public boolean isWorkshopOnGoing(String workshopId) {
		Workshop workshop = workshopService.findWorkshopById(workshopId);
		boolean result = true;
		Train train = null;
		if (workshop != null) {
			if (workshop.getTimePeriod() != null) {
				if (workshop.getTimePeriod().getStartTime() != null) {
					if (!TimeUtils.hasBegun(workshop.getTimePeriod().getStartTime())) {
						result = false;
					}
				} else {
					if (workshop.getWorkshopRelation() != null && workshop.getWorkshopRelation().getRelation() != null && workshop.getWorkshopRelation().getRelation().getId() != null) {
						train = trainService.findTrainById(workshop.getWorkshopRelation().getRelation().getId());
						if (train != null) {
							if (train.getTrainingTime() != null && train.getTrainingTime().getStartTime() != null) {
								if (!TimeUtils.hasBegun(train.getTrainingTime().getStartTime())) {
									result = false;
								}
							}
						}
					}

				}

				if (workshop.getTimePeriod().getEndTime() != null) {
					if (TimeUtils.hasEnded(workshop.getTimePeriod().getEndTime())) {
						result = false;
					}
				} else {
					if (train == null) {
						if (workshop.getWorkshopRelation() != null && workshop.getWorkshopRelation().getRelation() != null && workshop.getWorkshopRelation().getRelation().getId() != null) {
							train = trainService.findTrainById(workshop.getWorkshopRelation().getRelation().getId());
						}
					}
					if (train != null) {
						if (train.getTrainingTime() != null && train.getTrainingTime().getEndTime() != null) {
							if (TimeUtils.hasEnded(train.getTrainingTime().getEndTime())) {
								result = false;
							}
						}
					}
				}
			}
		}
		
		return result;

	}

}
