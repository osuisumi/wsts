package com.haoyu.wsts.mobile.lcec.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.aip.courseware.entity.Courseware;
import com.haoyu.aip.courseware.service.ICoursewareService;
import com.haoyu.sip.comment.service.ICommentService;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.BeanUtils;
import com.haoyu.sip.core.web.SearchParam;
import com.haoyu.sip.evaluate.entity.Evaluate;
import com.haoyu.sip.evaluate.entity.EvaluateItemSubmission;
import com.haoyu.sip.evaluate.entity.EvaluateRelation;
import com.haoyu.sip.evaluate.entity.EvaluateSubmission;
import com.haoyu.sip.evaluate.service.IEvaluateItemSubmissionService;
import com.haoyu.sip.evaluate.service.IEvaluateRelationService;
import com.haoyu.sip.evaluate.service.IEvaluateService;
import com.haoyu.sip.evaluate.service.IEvaluateSubmissionService;
import com.haoyu.sip.evaluate.utils.EvaluateSubmissionState;
import com.haoyu.sip.file.entity.FileResource;
import com.haoyu.sip.file.service.IFileResourceService;
import com.haoyu.sip.mobile.file.entity.MFileInfo;
import com.haoyu.wsts.mobile.lcec.entity.MEvaluateItem;
import com.haoyu.wsts.mobile.lcec.entity.MEvaluateSubmission;
import com.haoyu.wsts.mobile.lcec.entity.MLcec;
import com.haoyu.wsts.mobile.lcec.service.IMLcecService;

import freemarker.template.DefaultObjectWrapper;

@Service
public class MLcecService implements IMLcecService{
	@Resource
	private ICoursewareService coursewareService;
	@Resource
	private IFileResourceService fileResourceService;
	@Resource
	private ICommentService commentService;
	@Resource
	private IEvaluateSubmissionService evaluateSubmissionService;
	@Resource
	private IEvaluateRelationService evaluateRelationService;
	@Resource
	private IEvaluateService evaluateService;
	@Resource
	private IEvaluateItemSubmissionService evaluateItemSubmissionService;

//	@Override
//	public Response view(String lcecId) {
//		Courseware courseware = coursewareService.get(lcecId);
//		SearchParam searchParam = new SearchParam();
//		searchParam.getParamMap().put("relationId", lcecId);
//		List<FileResource> fileResources = fileResourceService.list(searchParam, null);
//		
//		MLcec mLcec = new MLcec();
//		List<MFileInfo> mFileInfos = Lists.newArrayList();
//		
//		BeanUtils.copyProperties(courseware, mLcec);
//		
//		if(CollectionUtils.isNotEmpty(fileResources)){
//			for(FileResource fr:fileResources){
//				if(fr.getNewestFile()!=null){
//					MFileInfo mFileInfo = new MFileInfo();
//					BeanUtils.copyProperties(fr.getNewestFile(), mFileInfo);
//					mFileInfos.add(mFileInfo);
//				}
//			}
//		}
//		
//		
//		Map<String,Object> result = Maps.newHashMap();
//		result.put("lcec",mLcec);
//		result.put("mFileInfos",mFileInfos);
//		
//		return Response.successInstance().responseData(result);
//		
//		
//	}

	@Override
	public Response evaluate(String lcecId) {
		EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
		List<MEvaluateItem> mEvaluateItems = Lists.newArrayList();
		Map<String,Object> result = Maps.newHashMap();
		if(evaluateRelation != null){
			Evaluate evaluate =evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
			EvaluateSubmission evaluateSubmission = evaluateSubmissionService.createEvaluateSubmissionIfNotExists(evaluate.getId(),lcecId);
			if(CollectionUtils.isNotEmpty(evaluate.getEvaluateItems())){
				mEvaluateItems = BeanUtils.getCopyList(evaluate.getEvaluateItems(), MEvaluateItem.class);
			}
			result.put("submissionId", evaluateSubmission.getId());
			result.put("submissionRelationId", evaluateSubmission.getEvaluateRelation().getId());
		}
		result.put("mEvaluateItems", mEvaluateItems);
		return Response.successInstance().responseData(result);
	}

	@Override
	public Response evaluateResult(String lcecId) {
		EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
		List<MEvaluateItem> mEvaluateItems = Lists.newArrayList();
		if(evaluateRelation != null){
			Evaluate evaluate =evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
			Map<String,Float> avgScoreMap = evaluateItemSubmissionService.mapEvaluateItemScore(evaluate.getId(), lcecId);
			if(CollectionUtils.isNotEmpty(evaluate.getEvaluateItems())){
				mEvaluateItems = BeanUtils.getCopyList(evaluate.getEvaluateItems(), MEvaluateItem.class);
			}
			if(CollectionUtils.isNotEmpty(mEvaluateItems)){
				for(MEvaluateItem mi:mEvaluateItems){
					if(avgScoreMap.containsKey(mi.getId())){
						mi.setAvgScore(avgScoreMap.get(mi.getId())==null?0l:avgScoreMap.get(mi.getId()).doubleValue());
					}
				}
			}
		}
		return Response.successInstance().responseData(mEvaluateItems);
	}

	@Override
	public Response evaluateSubmissions(String lcecId,PageBounds pageBounds) {
		EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
		
		List<MEvaluateSubmission> mEvaluateSubmissions = Lists.newArrayList();
		
		Map<String,Object> result = Maps.newHashMap();
		
		if(evaluateRelation!=null){
			Evaluate evaluate =evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
			Map<String,Object> selectParam = Maps.newHashMap();
			selectParam.put("evaluateRelationId", EvaluateRelation.getId(evaluate.getId(),lcecId));
			selectParam.put("state", EvaluateSubmissionState.SUBMITED);
			List<EvaluateSubmission> evaluateSubmissions = evaluateSubmissionService.findEvaluateSubmissions(selectParam, pageBounds);
			
			if(CollectionUtils.isNotEmpty(evaluateSubmissions)){
				mEvaluateSubmissions = BeanUtils.getCopyList(evaluateSubmissions, MEvaluateSubmission.class);
				if(evaluateSubmissions instanceof PageList){
					PageList pageList =  (PageList) evaluateSubmissions;
					result.put("paginator", pageList.getPaginator());
				}
			}
			
		}
		result.put("mEvaluateSubmissions", mEvaluateSubmissions);
		return Response.successInstance().responseData(result);
	}

	@Override
	public Response itemScoreDetail(String lcecId, String itemId) {
		EvaluateRelation evaluateRelation = evaluateRelationService.getEvaluateRelationByRelationId(lcecId);
		Map<String,Object> result = Maps.newHashMap();
		result.put("totalSubmission", 0);
		List<BigDecimal> scoreDetail = Lists.newArrayList();
		result.put("scoreDetail", scoreDetail);
		if(evaluateRelation != null){
			result.put("totalSubmission", evaluateRelation.getSubmitNum());
			Evaluate evaluate =evaluateService.getEvaluate(evaluateRelation.getEvaluate().getId());
			Map<String,Object> selectParam = Maps.newHashMap();
			selectParam.put("evaluateRelationId", EvaluateRelation.getId(evaluate.getId(), lcecId));
			selectParam.put("evaluateItemId", itemId);
			List<EvaluateItemSubmission> evaluateItemSubmissions = evaluateItemSubmissionService.findEvaluateItemSubmissions(selectParam, null);
			if(CollectionUtils.isNotEmpty(evaluateItemSubmissions)){
				for(EvaluateItemSubmission es:evaluateItemSubmissions){
					scoreDetail.add(es.getScore());
				}
			}
		}
		return Response.successInstance().responseData(result);
	}

}
