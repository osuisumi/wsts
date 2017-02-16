package com.haoyu.wsts.mobile.evaluate.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.web.AbstractBaseMobileController;
import com.haoyu.sip.evaluate.entity.EvaluateItem;
import com.haoyu.sip.evaluate.service.IEvaluateItemService;
import com.haoyu.wsts.mobile.evaluate.service.IMEvaluateItemService;

@RestController
@RequestMapping("**/m/evaluate_item")
public class MEvaluateItemController extends AbstractBaseMobileController{
	@Resource
	private IMEvaluateItemService mEvaluateItemService;
	@Resource
	private IEvaluateItemService evaluateItemService;
	
	
	@RequestMapping(method=RequestMethod.POST)
	public Response create(EvaluateItem evaluateItem,String aid){
		return mEvaluateItemService.create(evaluateItem,aid);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public Response deleteEvaluateItem(EvaluateItem evaluateItem){
		return evaluateItemService.deleteEvaluateItemByLogic(evaluateItem);
	}

}
