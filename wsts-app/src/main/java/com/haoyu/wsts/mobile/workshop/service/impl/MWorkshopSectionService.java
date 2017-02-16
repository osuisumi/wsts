package com.haoyu.wsts.mobile.workshop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.wsts.mobile.workshop.entity.MWorkshopSection;
import com.haoyu.wsts.mobile.workshop.service.IMWorkshopSectionService;
import com.haoyu.wsts.workshop.entity.WorkshopSection;
import com.haoyu.wsts.workshop.service.IWorkshopSectionService;

@Service
public class MWorkshopSectionService implements IMWorkshopSectionService{
	@Resource
	private IWorkshopSectionService workshopSectionService;

	@Override
	public Response create(WorkshopSection workshopSection) {
		Response response = workshopSectionService.createWorkshopSection(workshopSection);
		if(response.getResponseData()!=null && response.getResponseData() instanceof WorkshopSection){
			WorkshopSection ws = (WorkshopSection) response.getResponseData();
			MWorkshopSection ms = new MWorkshopSection();
			BeanUtils.copyProperties(ws, ms);
			response.setResponseData(ms);
		}
		return response;
	}

}
