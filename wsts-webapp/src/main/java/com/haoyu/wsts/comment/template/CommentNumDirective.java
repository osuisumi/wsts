package com.haoyu.wsts.comment.template;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.haoyu.sip.attitude.service.IAttitudeService;
import com.haoyu.sip.core.freemarker.AbstractTemplateDirectiveModel;
import com.haoyu.sip.core.utils.ThreadContext;
import com.haoyu.wsts.comment.service.ICommentBizService;
import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class CommentNumDirective extends AbstractTemplateDirectiveModel{

	@Resource
	private ICommentBizService commentBizService;
	@Resource
	private IAttitudeService attitudeService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		int allCommentCount = 0;
		int toMeCommentCount = 0;
		Map<String,Object> paramerts = getSelectParam(params);
		String relationId = (String) paramerts.get("relationId");
		if (StringUtils.isNotEmpty(relationId)) {
			//获取所有自由交流的总数
			Map<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("relationId", relationId);
			paramMap.put("mainId","null");
			paramMap.put("creatorOrTarget", "1");
			paramMap.put("userId", ThreadContext.getUser().getId());
			allCommentCount = commentBizService.getCount(paramMap);
			paramMap.clear();
			paramMap.put("targetId",ThreadContext.getUser().getId());
			toMeCommentCount = commentBizService.getCount(paramMap);
		}
		env.setVariable("allCommentCount" , new DefaultObjectWrapper().wrap(allCommentCount));
		env.setVariable("toMeCommentCount" , new DefaultObjectWrapper().wrap(toMeCommentCount));
		body.render(env.getOut());
	}

}
