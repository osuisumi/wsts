package com.haoyu.wsts.mobile.evaluate.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.haoyu.aip.activity.entity.Activity;
import com.haoyu.aip.activity.service.IActivityService;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.evaluate.entity.Evaluate;
import com.haoyu.sip.evaluate.entity.EvaluateItem;
import com.haoyu.sip.evaluate.entity.EvaluateRelation;
import com.haoyu.sip.evaluate.service.IEvaluateItemService;
import com.haoyu.sip.evaluate.service.IEvaluateRelationService;
import com.haoyu.sip.evaluate.service.IEvaluateService;
import com.haoyu.wsts.mobile.evaluate.service.IMEvaluateItemService;
import com.haoyu.wsts.mobile.lcec.entity.MEvaluateItem;

@Service
public class MEvaluateItemService implements IMEvaluateItemService{
	@Resource
	private IEvaluateItemService evaluateItemService;
	@Resource
	private IEvaluateRelationService evaluateRelationService;
	@Resource
	private IEvaluateService evaluateService;
	@Resource
	private IActivityService activityService;

	@Override
	public Response create(EvaluateItem evaluateItem, String aid) {
		if(StringUtils.isEmpty(aid)){
			return Response.failInstance().responseMsg("aid is empty");
		}
		Activity act = activityService.getActivity(aid);
		String relationId = act.getEntityId();
		EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(relationId);
		//顺带创建evaluate
		if (evaluateRelation == null) {
			Evaluate evaluate = new Evaluate();
			Response response = evaluateService.createEvaluate(evaluate);
			if (response.isSuccess()) {
				evaluateRelation = new EvaluateRelation();
				evaluateRelation.setEvaluate(evaluate);
				evaluateRelation.setRelation(new Relation(relationId));
				String id = EvaluateRelation.getId(evaluate.getId(), relationId);
				evaluateRelation.setId(id);
				evaluateRelation.setType("lcec");
				evaluateRelationService.createEvaluateRelation(evaluateRelation);
			}                     
		}
		evaluateItem.setEvaluate(evaluateRelation.getEvaluate());
		Response response = evaluateItemService.createEvaluateItem(evaluateItem);
		if(response.isSuccess() && response.getResponseData()!=null &&response.getResponseData() instanceof EvaluateItem){
			EvaluateItem ei = (EvaluateItem) response.getResponseData();
			MEvaluateItem  mei = new MEvaluateItem();
			BeanUtils.copyProperties(ei, mei);
			response.setResponseData(mei);
		}
		return response;
	}

}
