package com.haoyu.wsts.faq.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoyu.sip.core.entity.Relation;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.tip.faq.entity.FaqQuestion;
import com.haoyu.tip.faq.service.IFaqAnswerService;
import com.haoyu.tip.faq.service.IFaqQuestionService;
import com.haoyu.wsts.faq.utils.FaqQuestionRelationType;

import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class FaqDataDirective extends AbstractTemplateDirectiveModel {

	@Resource
	private IFaqQuestionService faqQuestionService;
	@Resource
	private IFaqAnswerService faqAnswerService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String relationId = "";
		String type = "";
		String id = "";
		int myQuestionCount = 0;
		int allQuestionCount = 0;
		List<FaqQuestion> faqQuestions = Lists.newArrayList();
		
		PageBounds pageBounds = getPageBounds(params);
		Map<String,Object> paramerts = getSelectParam(params);
		relationId = (String) paramerts.get("relationId");
		type = (String) paramerts.get("type");
		if(paramerts.containsKey("id")){
			id = (String) paramerts.get("id");
		}
		if (StringUtils.isNotEmpty(relationId)) {
			FaqQuestion faqQuestion  = new FaqQuestion();
			Relation relation = new Relation();
			relation.setId(relationId);
			relation.setType(FaqQuestionRelationType.WORKSHOP_QUESTION);
			if(StringUtils.isNotEmpty(id)){
				faqQuestion.setId(id);
			}
			faqQuestion.setRelation(relation);
			if("my".equals(type)){
				faqQuestion.setCreator(ThreadContext.getUser());
			}
			faqQuestions = faqQuestionService.listFaqQuestion(faqQuestion, pageBounds);
			Map<String, Object> parameter = Maps.newHashMap();
			parameter.put("relation",relation);
			allQuestionCount = faqQuestionService.count(parameter);
			parameter.put("creator",ThreadContext.getUser());
			myQuestionCount = faqQuestionService.count(parameter);
		}
		env.setVariable("type" , new DefaultObjectWrapper().wrap(type));
		env.setVariable("myQuestionCount" , new DefaultObjectWrapper().wrap(myQuestionCount));
		env.setVariable("allQuestionCount" , new DefaultObjectWrapper().wrap(allQuestionCount));
		env.setVariable("faqQuestions" , new DefaultObjectWrapper().wrap(faqQuestions));
		if (pageBounds != null && pageBounds.isContainsTotalCount()) {
			PageList pageList = (PageList) faqQuestions;
			env.setVariable("paginator", new DefaultObjectWrapper().wrap(pageList.getPaginator()));
		}
		body.render(env.getOut());
	}

}
