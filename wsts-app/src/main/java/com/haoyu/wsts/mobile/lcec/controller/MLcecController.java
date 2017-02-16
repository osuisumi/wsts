package com.haoyu.wsts.mobile.lcec.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.wsts.mobile.lcec.service.IMLcecService;

@RestController
@RequestMapping("**/m/lcec")
public class MLcecController extends AbstractBaseMobileController{
	@Resource
	private IMLcecService mLcecService;
	
	@RequestMapping(value="{lcecId}/edit_evaluate")
	public Response editEvaluate(@PathVariable String lcecId){
		return mLcecService.evaluate(lcecId);
	}
	
	@RequestMapping(value="{lcecId}/result")
	public Response EvaluateResult(@PathVariable String lcecId){
		return mLcecService.evaluateResult(lcecId);
	}
	
	@RequestMapping(value="{lcecId}/submissions")
	public Response evaluateSubmissions(@PathVariable String lcecId){
		PageBounds pageBounds = getPageBounds(5, true);
		return mLcecService.evaluateSubmissions(lcecId,pageBounds);
	}
	
	@RequestMapping(value="{lcecId}/{itemId}/score_detail")
	public Response itemScoreDetail(@PathVariable String lcecId,@PathVariable String itemId){
		return mLcecService.itemScoreDetail(lcecId, itemId);
	}

}
