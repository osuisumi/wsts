package com.haoyu.wsts.mobile.evaluate.service;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.evaluate.entity.EvaluateItem;

public interface IMEvaluateItemService {
	
	public Response create(EvaluateItem evaluateItem,String relationId);
	
}
