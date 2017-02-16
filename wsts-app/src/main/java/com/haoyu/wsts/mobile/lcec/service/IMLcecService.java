package com.haoyu.wsts.mobile.lcec.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;

public interface IMLcecService {
	
	public Response evaluate(String lcecId);
	
	public Response evaluateResult(String lcecId);
	
	public Response evaluateSubmissions(String lcecId,PageBounds pageBounds);
	
	public Response itemScoreDetail(String lcecId,String itemId);

}
