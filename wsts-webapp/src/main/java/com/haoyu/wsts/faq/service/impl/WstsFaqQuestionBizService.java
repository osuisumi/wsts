package com.haoyu.wsts.faq.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.haoyu.sip.core.service.Response;
import com.haoyu.sip.core.utils.PropertiesLoader;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.sip.point.entity.PointRecord;
import com.haoyu.sip.point.entity.PointStrategy;
import com.haoyu.sip.point.service.IPointRecordService;
import com.haoyu.sip.point.service.IPointStrategyService;
import com.haoyu.tip.faq.entity.FaqQuestion;
import com.haoyu.tip.faq.service.IFaqQuestionService;
import com.haoyu.wsts.point.utils.PointType;
import com.haoyu.wsts.utils.RoleCodeConstant;
import com.haoyu.wsts.utils.WorkshopTimeUtils;
import com.haoyu.wsts.utils.WsIdObject;
import com.haoyu.wsts.workshop.service.IWorkshopService;
import com.haoyu.wsts.workshop.utils.DateUtils;

@Service
public class WstsFaqQuestionBizService {
	@Resource
	private IFaqQuestionService faqQuestionService;
	@Resource
	private IPointRecordService pointRecordService;
	@Resource
	private IPointStrategyService pointStrategyService;
	@Resource
	private IWorkshopService workshopService;
	@Resource
	private WorkshopTimeUtils workshopTimeUtils;
	
	public Response createWorkshopFaqQuestion(FaqQuestion faqQuestion){
		//获取本人在此工作坊内当天的提问数
		//-》如果还没有提问，直接保存问题
		//-》如果提问超过
		//-》-》获取用户当前的积分
		//-》-》-》如果积分足够，保存并创建扣分记录
		//-》-》-》如果积分不足，不保存提问，返回
		Subject subject = SecurityUtils.getSubject();
		String wsid = WsIdObject.getWsIdObject().getWsid();
		if(subject.hasRole(RoleCodeConstant.STUDENT + "_" + wsid) && workshopTimeUtils.isWorkshopOnGoing(wsid)){
			Map<String,Object> parameter = Maps.newHashMap();
			parameter.put("relation", faqQuestion.getRelation());
			Date now = new Date();
			parameter.put("minCreateTime",DateUtils.getDayBegin(now).getTime());
			parameter.put("maxCreateTime", DateUtils.getDayEnd(now).getTime());
			parameter.put("creator", ThreadContext.getUser());
			int count = faqQuestionService.count(parameter);
			int freeQuestionNum = Integer.parseInt(PropertiesLoader.get("point.workshop.faqQuestion.freeNumPerday"));
			Response response = null;
			if(count<freeQuestionNum){
				response = faqQuestionService.createFaqQuestion(faqQuestion);
			}else{
				Float nowPoint = pointRecordService.findUserPoint(ThreadContext.getUser().getId(),faqQuestion.getRelation().getId(),"wsts");
				PointStrategy pointStrategy = pointStrategyService.findPointStrategyById(PointStrategy.getId(PointType.EXTRA_WORKSHOP_FAQ_QUESTION,"wsts"));
				if(pointStrategy!=null && (nowPoint + pointStrategy.getPoint())<0){
					response= new Response("03");
					response.setResponseMsg("提问需要扣除"+Math.abs(pointStrategy.getPoint())+"个积分,您当前的积分不足");
				}else{
					response = faqQuestionService.createFaqQuestion(faqQuestion);
					PointRecord pointRecord = new PointRecord();
					pointRecord.setEntityId(faqQuestion.getId());
					pointRecord.setPointStrategy(pointStrategy);
					pointRecord.setRelationId(faqQuestion.getRelation().getId());
					pointRecord.setUserId(ThreadContext.getUser().getId());
					pointRecordService.createPointRecord(pointRecord);
				}
			}
			return response;
		}else{
			return faqQuestionService.createFaqQuestion(faqQuestion);
		}
	}

}
