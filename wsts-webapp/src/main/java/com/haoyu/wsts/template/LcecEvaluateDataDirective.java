package com.haoyu.wsts.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Maps;
import com.haoyu.sip.evaluate.entity.Evaluate;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.evaluate.entity.EvaluateItemSubmission;
import com.haoyu.sip.evaluate.entity.EvaluateRelation;
import com.haoyu.sip.evaluate.entity.EvaluateSubmission;
import com.haoyu.sip.evaluate.service.IEvaluateItemSubmissionService;
import com.haoyu.sip.evaluate.service.IEvaluateRelationService;
import com.haoyu.sip.evaluate.service.IEvaluateService;
import com.haoyu.sip.evaluate.service.IEvaluateSubmissionService;
import com.haoyu.sip.evaluate.utils.EvaluateSubmissionState;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class LcecEvaluateDataDirective extends AbstractTemplateDirectiveModel{
	@Resource
	private IEvaluateItemSubmissionService evaluateItemSubmissionService;
	@Resource
	private IEvaluateSubmissionService evaluateSubmissionService;
	@Resource
	private IEvaluateRelationService evaluateRelationService;
	@Resource
	private IEvaluateService evaluateService;
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String,Object> parameter = getSelectParam(params);
		if(parameter.containsKey("lcecId")){
			String lcecId = parameter.get("lcecId").toString();
			EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
			if(evaluateRelation != null){
				Evaluate evaluate =evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
				if(parameter.containsKey("getEvaluate")){
					env.setVariable("lcecEvaluate", new DefaultObjectWrapper().wrap(evaluate));
				}
				
				if(parameter.containsKey("getEvaluateItemSubmission") &&parameter.containsKey("evaluateItemId")){
					Map<String,Object> selectParam = Maps.newHashMap();
					selectParam.put("evaluateRelationId", EvaluateRelation.getId(evaluate.getId(), parameter.get("lcecId").toString()));
					selectParam.put("evaluateItemId", parameter.get("evaluateItemId").toString());
					List<EvaluateItemSubmission> evaluateItemSubmissions = evaluateItemSubmissionService.findEvaluateItemSubmissions(selectParam, null);
					env.setVariable("evaluateItemSubmissions", new DefaultObjectWrapper().wrap(evaluateItemSubmissions));
				}
				
				if(parameter.containsKey("getEvaluateRelation")&&parameter.containsKey("lcecId")){
					env.setVariable("evaluateRelation", new DefaultObjectWrapper().wrap(evaluateRelation));
				}
				
				if(parameter.containsKey("getEvaluateSubmission")&&parameter.containsKey("lcecId")){
					EvaluateSubmission evaluateSubmission = evaluateSubmissionService.createEvaluateSubmissionIfNotExists(evaluate.getId(),parameter.get("lcecId").toString());
					env.setVariable("evaluateSubmission", new DefaultObjectWrapper().wrap(evaluateSubmission));
				}
				
				if(parameter.containsKey("getEvaluateSubmissions")&&parameter.containsKey("lcecId")){
					PageBounds pageBounds = getPageBounds(params);
					Map<String,Object> selectParam = Maps.newHashMap();
					selectParam.put("evaluateRelationId", EvaluateRelation.getId(evaluate.getId(),parameter.get("lcecId").toString()));
					selectParam.put("state", EvaluateSubmissionState.SUBMITED);
					List<EvaluateSubmission> evaluateSubmissions = evaluateSubmissionService.findEvaluateSubmissions(selectParam, pageBounds);
					env.setVariable("evaluateSubmissions", new DefaultObjectWrapper().wrap(evaluateSubmissions));
					if (pageBounds != null && pageBounds.isContainsTotalCount()) {
						PageList pageList = (PageList) evaluateSubmissions;
						env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
					}
				}
				
				if(parameter.containsKey("getEvaluateItemAvgScoreMap")&&parameter.containsKey("lcecId")){
					Map<String,Float> avgScoreMap = evaluateItemSubmissionService.mapEvaluateItemScore(evaluate.getId(), parameter.get("lcecId").toString());
					env.setVariable("avgScoreMap", new DefaultObjectWrapper().wrap(avgScoreMap));
				}
				
			}
		}
		
		
		
		body.render(env.getOut());
		
	}

}
